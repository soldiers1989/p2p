/*
 * 文 件 名:  BidExchange.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月19日
 */
package com.dimeng.p2p.pay.servlets.huifu;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6507;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.common.enums.FrontLogType;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.BidExchangeCond;
import com.dimeng.p2p.escrow.huifu.service.BidExchangeManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.TenderExchangeExecutor;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 债权转让servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月19日]
 */
public class BidExchange extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        if (!FormToken.verify(serviceSession.getSession(), request.getParameter(FormToken.parameterName())))
        {
            throw new LogicalException("请不要重复提交请求！");
        }
        final int zcbId = IntegerParser.parse(request.getParameter("zqzrId"));
        BidExchangeManage manage = serviceSession.getService(BidExchangeManage.class);
        final int orderId = manage.purchase(zcbId);
        final T6507 t6507 = manage.selectT6507(orderId);
        final T6501 t6501 = manage.selectT6501(orderId);
        final String buyCustId = manage.getUserCustId(t6507.F03);
        final String bidDetails = manage.bidDetails(orderId);
        
        TenderExchangeExecutor executor = getResourceProvider().getResource(TenderExchangeExecutor.class);
        executor.submit(orderId, null);
        //拼装请求参数
        String location = manage.createBidExchangeUri(new BidExchangeCond()
        {
            
            @Override
            public String retUrl()
            {
                try
                {
                    return getConfigureProvider().format(HuifuVariable.HF_TG_EXCHAGE);
                }
                catch (Exception e)
                {
                    logger.error("汇付，债权转让前台返回地址异常：" + e);
                }
                return null;
            }
            
            @Override
            public String ordId()
            {
                return String.valueOf(orderId);
            }
            
            @Override
            public String ordDate()
            {
                return DateParser.format(t6501.F04, "yyyyMMdd");
            }
            
            @Override
            public String fee()
            {
                return t6507.F06.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String divDetails()
            {
                StringBuilder builder = new StringBuilder();
                builder.append("[{\"DivAcctId\":\"" + HuiFuConstants.AcctId.ACCT_ID + "\",\"DivAmt\":\"");
                builder.append(t6507.F06.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                builder.append("\"}]");
                return builder.toString();
            }
            
            @Override
            public String creditDealAmt()
            {
                return t6507.F05.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String creditAmt()
            {
                return t6507.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String buyCustId()
            {
                return buyCustId;
            }
            
            @Override
            public String bidDetails()
            {
                return bidDetails;
            }
            
            @Override
            public String merPriv()
            {
                return String.valueOf(zcbId);
            }
        }, t6507);
        
        logger.info("汇付 - 债权转让请求参数" + location);
        manage.writeFrontLog(FrontLogType.GMZQ.getName(), "前台购买债权");
        
        sendRedirect(request, response, location);
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        getResourceProvider().log(throwable);
        if (throwable instanceof ParameterException || throwable instanceof SQLException)
        {
            getController().prompt(request, response, PromptLevel.ERROR, "系统繁忙，请您稍后再试");
            sendRedirect(request, response, configureProvider.format(URLVariable.FINANCING_ZQZR));
        }
        else if (throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, configureProvider.format(URLVariable.FINANCING_ZQZR));
        }
        else
        {
            super.onThrowable(request, response, throwable);
        }
    }
    
}
