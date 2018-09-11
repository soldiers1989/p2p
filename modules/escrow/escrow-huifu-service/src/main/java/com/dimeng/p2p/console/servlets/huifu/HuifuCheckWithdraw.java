/*
 * 文 件 名:  HuifuCheckWithdraw.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月22日
 */
package com.dimeng.p2p.console.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.enums.T6130_F09;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.p2p.modules.account.console.service.UserWithdrawalsManage;
import com.dimeng.p2p.modules.account.console.service.entity.UserWithdrawals;
import com.dimeng.p2p.order.WithdrawExecutor;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提现放款
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月22日]
 */
@Right(id = "P2P_C_FINANCE_CHECKTXGLFK", name = "放款", moduleId = "P2P_C_FINANCE_ZJGL_TXGL", order = 2)
public class HuifuCheckWithdraw extends AbstractHuifuServlet
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
        
        int id = IntegerParser.parse(request.getParameter("id"));
        String check_reason = request.getParameter("check_reason");
        T6130_F09 status = EnumParser.parse(T6130_F09.class, request.getParameter("status"));
        try
        {
            ResourceProvider resourceProvider = getResourceProvider();
            WithdrawManage withdrawManage = serviceSession.getService(WithdrawManage.class);
            int[] orderIds = withdrawManage.orderIds(status, check_reason, id);
            if (orderIds != null && orderIds.length > 0)
            {
                WithdrawExecutor executor = resourceProvider.getResource(WithdrawExecutor.class);
                if (status == T6130_F09.YFK)
                {
                    for (int orderId : orderIds)
                    {
                        executor.submit(orderId, null);
                    }
                }
                else
                {
                    String respCode = withdrawManage.refuse(id);
                    if (HuiFuConstants.Common.SUCCESS_CODE.equals(respCode))
                    {
                        //取现审核不通过
                        withdrawManage.cancle(id);
                    }
                    //处理资金流
                    withdrawManage.trade(id);
                }
            }
            if (status == T6130_F09.YFK)
            {
                sendRedirect(request, response, "/console/finance/zjgl/txgl/txcg.htm");
                return;
            }
            else
            {
                sendRedirect(request, response, "/console/finance/zjgl/txgl/txsb.htm");
                return;
            }
        }
        catch (Throwable throwable)
        {
            logger.error(throwable, throwable);
            if (throwable instanceof ParameterException || throwable instanceof LogicalException)
            {
                getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
                sendRedirect(request, response, "/console/finance/zjgl/txgl/txcg.htm");
                //                processGet(request, response, serviceSession);
            }
            else
            {
                super.onThrowable(request, response, throwable);
            }
        }
    }
}
