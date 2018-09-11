/*
 * 文 件 名:  withdraw.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月18日
 */
package com.dimeng.p2p.pay.servlets.huifu;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.S65.entities.T6503;
import com.dimeng.p2p.account.user.service.UserInfoManage;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.common.enums.FrontLogType;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.WithdrawCond;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 提现servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月19日]
 */
public class Withdraw extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(final HttpServletRequest request, HttpServletResponse response,
        ServiceSession serviceSession)
        throws Throwable
    {
        if (!FormToken.verify(serviceSession.getSession(), request.getParameter(FormToken.parameterName())))
        {
            throw new LogicalException("请不要重复提交请求！");
        }
        UserInfoManage uManage = serviceSession.getService(UserInfoManage.class);
        T6110 t6110 = uManage.getUserInfo(serviceSession.getSession().getAccountId());
        if (T6110_F07.HMD == t6110.F07)
        {
            throw new LogicalException("账户已被拉黑，不能进行提现！");
        }
        getController().prompt(request, response, PromptLevel.WARRING, request.getParameter("amount"));
        final String withdrawWay = request.getParameter("withdrawWay");
        if (StringHelper.isEmpty(request.getParameter("amount")))
        {
            throw new LogicalException("提现金额不能为空");
        }
        
        final int cardId = IntegerParser.parse(request.getParameter("cardId"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        
        getController().clearPrompts(request, response, PromptLevel.WARRING);
        
        WithdrawManage manage = serviceSession.getService(WithdrawManage.class);
        final String bankCard = StringHelper.decode(manage.getBankCard(cardId));
        final String usrCustId = manage.getUserCustId();
        final T6503 t6503 = manage.addOrder(bankCard, amount);
        String location = manage.createWithdrawUri(new WithdrawCond()
        {
            
            @Override
            public String usrCustId()
            {
                return usrCustId;
            }
            
            @Override
            public String transAmt()
            {
                return t6503.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String servFeeAccId()
            {
                return HuiFuConstants.AcctId.ACCT_ID;
            }
            
            @Override
            public String servFee()
            {
                return t6503.F10.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String retUrl()
            {
                try
                {
                    return getConfigureProvider().format(HuifuVariable.HF_TG_WITHDRAW);
                }
                catch (Exception e)
                {
                }
                return null;
            }
            
            @Override
            public String ordId()
            {
                return String.valueOf(t6503.F01);
            }
            
            @Override
            public String openAcctId()
            {
                return bankCard;
            }
            
            @Override
            public String merPriv()
            {
                return withdrawWay;
            }
        });
        
        logger.info("汇付 - 提现申请请求参数：" + location);
        manage.writeFrontLog(FrontLogType.TX.getName(), "前台提现");
        
        sendRedirect(request, response, location);
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        if (throwable instanceof SQLException)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else if (throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.INFO, throwable.getMessage());
            sendRedirect(request, response, getConfigureProvider().format(URLVariable.USER_WITHDRAW));
            //            this.forward(request, response, getConfigureProvider().format(URLVariable.USER_WITHDRAW));
        }
        else
        {
            getController().prompt(request, response, PromptLevel.INFO, throwable.getMessage());
            sendRedirect(request, response, getConfigureProvider().format(URLVariable.USER_WITHDRAW));
        }
    }
    
}
