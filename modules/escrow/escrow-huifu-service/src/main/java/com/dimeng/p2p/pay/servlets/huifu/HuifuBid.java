/*
 * 文 件 名:  HuifuBid.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月20日
 */
package com.dimeng.p2p.pay.servlets.huifu;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import com.dimeng.p2p.S61.entities.T6103;
import com.dimeng.p2p.S63.entities.T6342;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.enums.T6504_F07;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.common.enums.FrontLogType;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.BidCond;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.modules.bid.pay.service.TenderManage;
import com.dimeng.p2p.order.TenderOrderExecutor;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;
import com.google.gson.Gson;

/**
 * 投标servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月20日]
 */
public class HuifuBid extends AbstractHuifuServlet
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
        TenderManage tenderManage = serviceSession.getService(TenderManage.class);
        final int loanId = IntegerParser.parse(request.getParameter("loanId"));
        String userReward = request.getParameter("userReward");
        if (!StringHelper.isEmpty(userReward))
        {
          /*  //用户体验金
            T6103 t6103 = tenderManage.getT6103(loanId);
            //用户活动
            T6342 t6342 = tenderManage.getT6342(loanId);*/
            /*if (null != t6103 || null != t6342)
            {
                throw new LogicalException("只能使用一次我的奖励投此标");
            }*/
        }
        final BigDecimal amount = BigDecimalParser.parse(request.getParameter("amount"));
        String tranPwd = request.getParameter("tranPwd");
        final ConfigureProvider configureProvider = getResourceProvider().getResource(ConfigureProvider.class);
        String myRewardType = request.getParameter("myRewardType");
        String hbRule = request.getParameter("hbRule");
        String jxqRule = request.getParameter("jxqRule");
        Map<String, String> parMap = new HashMap<String, String>();
        parMap.put("source", T6504_F07.PC.name());
        final Map<String, String> rtnMap =
            tenderManage.bid(loanId, amount, userReward, tranPwd, myRewardType, hbRule, jxqRule, parMap);
        rtnMap.put("userReward", userReward);
        rtnMap.put("myRewardType", myRewardType);
        rtnMap.put("hbRule", hbRule);
        rtnMap.put("jxqRule", jxqRule);
        final int orderId = IntegerParser.parse(rtnMap.get("orderId"));
        TenderOrderExecutor executor = getResourceProvider().getResource(TenderOrderExecutor.class);
        executor.submit(orderId, rtnMap);
        
        //主动投标订单
        final Gson gson = new Gson();
        BidManage bidManage = serviceSession.getService(BidManage.class);
        //获取红包请求参数
        final String reqExt = bidManage.bidRewardHB(rtnMap.get("hbOrdId"), amount);
        //获取投资人第三方账号
        final String usrCustId = bidManage.getUserCustId();
        //查询订单信息
        final T6501 t6501 = bidManage.selectT6501(Integer.parseInt(rtnMap.get("orderId")));
        //获取借款人信息
        final String borrowerDetails = bidManage.borrowerDetails(loanId, amount);
        //拼装投标参数
        String location = bidManage.createBidUrI(new BidCond()
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
            public String retUrl()
            {
                try
                {
                    return getConfigureProvider().format(HuifuVariable.HF_TG_BID);
                }
                catch (Exception e)
                {
                }
                return null;
            }
            
            @Override
            public String ordId()
            {
                return String.valueOf(rtnMap.get("orderId"));
            }
            
            @Override
            public String ordDate()
            {
                return DateParser.format(t6501.F04, "yyyyMMdd");
            }
            
            @Override
            public String maxTenderRate()
            {
                return configureProvider.getProperty(HuifuVariable.HF_MAXTENDERRATE);
            }
            
            @Override
            public String borrowerDetails()
            {
                return borrowerDetails;
            }
            
            @Override
            public String isFreeze()
            {
                return HuiFuConstants.IsFreeze.YES;
            }
            
            @Override
            public String freezeOrdId()
            {
                return null;
            }
            
            @SuppressWarnings("unused")
            @Override
            public String merPriv()
            {
                //活动信息当扩展参数传入，confirm中需要用到
                if (null != rtnMap)
                {
                    rtnMap.remove("hbOrdId");
                    rtnMap.remove("orderId");
                    return gson.toJson(rtnMap);
                }
                return null;
            }
            
            @Override
            public String reqExt()
            {
                return reqExt;
            }
            
        });
        
        logger.info("汇付 - 主动投资订单请求参数：" + location);
        tenderManage.writeFrontLog(FrontLogType.SDTB.getName(), "前台手动投资");
        
        sendRedirect(request, response, location);
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        getResourceProvider().log(throwable);
        int loanId = IntegerParser.parse(request.getParameter("loanId"));
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
        StringBuilder url = new StringBuilder(configureProvider.format(URLVariable.FINANCING_SBTZ_XQ));
        url.append(Integer.toString(loanId)).append(resourceProvider.getSystemDefine().getRewriter().getViewSuffix());
        return url.toString();
    }
    
}
