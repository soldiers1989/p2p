/*
 * 文 件 名:  HuifuCheck.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月27日
 */
package com.dimeng.p2p.console.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.enums.T6130_F09;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.service.HFUserWithdrawalsManage;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.p2p.modules.account.console.service.UserWithdrawalsManage;
import com.dimeng.p2p.modules.account.console.service.entity.UserWithdrawals;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提现审核
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月27日]
 */
@Right(id = "P2P_C_FINANCE_CHECKTXGL", name = "审核", moduleId = "P2P_C_FINANCE_ZJGL_TXGL", order = 1)
public class HuifuCheck extends AbstractHuifuServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        UserWithdrawalsManage extraction = serviceSession.getService(UserWithdrawalsManage.class);
        int id = IntegerParser.parse(request.getParameter("id"));
        UserWithdrawals txglRecord = extraction.get(id);
        request.setAttribute("txglRecord", txglRecord);
        super.processGet(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        
        HFUserWithdrawalsManage extraction = serviceSession.getService(HFUserWithdrawalsManage.class);
        int id = IntegerParser.parse(request.getParameter("id"));
        String check_reason = request.getParameter("check_reason");
        T6130_F09 status = EnumParser.parse(T6130_F09.class, request.getParameter("status"));
        try
        {
            extraction.check(status == T6130_F09.DFK, check_reason, id);
            if (status == T6130_F09.DFK)
            {
                sendRedirect(request, response, "/console/finance/zjgl/txgl/shtg.htm");
            }
            else
            {
                WithdrawManage withdrawManage = serviceSession.getService(WithdrawManage.class);
                //解冻
                String respCode = withdrawManage.refuse(id);
                if (HuiFuConstants.Common.SUCCESS_CODE.equals(respCode))
                {
                    //取现审核不通过
                    withdrawManage.cancle(id);
                }
                sendRedirect(request, response, "/console/finance/zjgl/txgl/txsb.htm");
            }
        }
        catch (Throwable throwable)
        {
            if (throwable instanceof ParameterException || throwable instanceof LogicalException)
            {
                getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
                sendRedirect(request, response, "/console/finance/zjgl/txgl/shtg.htm");
                //                processGet(request, response, serviceSession);
            }
            else
            {
                super.onThrowable(request, response, throwable);
            }
        }
    }
}
