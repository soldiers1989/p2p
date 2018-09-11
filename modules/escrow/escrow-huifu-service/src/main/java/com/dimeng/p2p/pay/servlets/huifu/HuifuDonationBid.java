/*
 * 文 件 名:  HuiFuDonationBid.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.pay.servlets.huifu;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.common.RSAUtils;
import com.dimeng.p2p.escrow.huifu.cond.UsrAcctPayCond;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.modules.bid.pay.service.DonationService;
import com.dimeng.p2p.order.DonationExecutor;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.p2p.variables.defines.pays.PayVariavle;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 公益标入口servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public class HuifuDonationBid extends AbstractHuifuServlet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        if (!FormToken.verify(serviceSession.getSession(), request.getParameter(FormToken.parameterName())))
        {
            throw new LogicalException("请不要重复提交请求！");
        }
        final BigDecimal amount = BigDecimalParser.parse(request.getParameter("amount1"));
        int loanId = IntegerParser.parse(request.getParameter("loanId1"));
        String tranPwd = request.getParameter("tranPwd");
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        boolean isOpenWsd =
            BooleanParser.parseObject(configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD));
        if (isOpenWsd)
        {
            tranPwd = RSAUtils.decryptStringByJs(tranPwd);
            tranPwd = UnixCrypt.crypt(tranPwd, DigestUtils.sha256Hex(tranPwd));
        }
        DonationService tenderManage = serviceSession.getService(DonationService.class);
        final int orderId = tenderManage.bid(loanId, amount, tranPwd);
        
        DonationExecutor executor = getResourceProvider().getResource(DonationExecutor.class);
        executor.submit(orderId, null);
        
        HFUsrAcctPayManage manage = serviceSession.getService(HFUsrAcctPayManage.class);
        final String usrCustId = manage.getUserCustId(serviceSession.getSession().getAccountId());
        String location = manage.createUsrAcctPayUrI(new UsrAcctPayCond()
        {
            
            @Override
            public String usrCustId()
            {
                return usrCustId;
            }
            
            @Override
            public String transAmt()
            {
                return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String ordId()
            {
                return String.valueOf(orderId);
            }
            
            @Override
            public String merCustId()
            {
                return null;
            }
            
            @Override
            public String inAcctType()
            {
                return null;
            }
            
            @Override
            public String inAcctId()
            {
                return null;
            }
            
            @Override
            public String retUrl()
            {
                try
                {
                    return configureProvider.format(HuifuVariable.HF_DONATION_TG_RET);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
            
            @Override
            public String bgRetUrl()
            {
                try
                {
                    return configureProvider.format(HuifuVariable.HF_DONATION_RET);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        });
        
        logger.info("汇付 - 公益标捐款请求参数：" + location);
        
        sendRedirect(request, response, location);
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        getResourceProvider().log(throwable);
        int loanId = IntegerParser.parse(request.getParameter("loanId1"));
        if (throwable instanceof ParameterException || throwable instanceof SQLException)
        {
            getController().prompt(request, response, PromptLevel.ERROR, "系统繁忙，请您稍后再试");
            sendRedirect(request, response, getURL(loanId));
        }
        else if (throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, getURL(loanId));
        }
        else
        {
            super.onThrowable(request, response, throwable);
        }
    }
    
    protected String getURL(int loanId)
        throws IOException
    {
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        StringBuilder url = new StringBuilder(configureProvider.format(URLVariable.GYB_BDXQ));
        url.append(Integer.toString(loanId)).append(resourceProvider.getSystemDefine().getRewriter().getViewSuffix());
        return url.toString();
    }
    
}
