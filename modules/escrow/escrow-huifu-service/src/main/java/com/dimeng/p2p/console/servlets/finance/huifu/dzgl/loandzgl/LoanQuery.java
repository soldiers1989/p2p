package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.loandzgl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.TransQueryEntity;
import com.dimeng.p2p.escrow.huifu.enumeration.OrderStatus;
import com.dimeng.p2p.escrow.huifu.enumeration.QueryMode;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.QueryManage;
import com.dimeng.p2p.escrow.huifu.service.UnFreezeManage;
import com.dimeng.p2p.order.TenderConfirmExecutor;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 放款对账servlet
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年07月07日]
 */

@Right(id = "P2P_C_HUIFUDZGL_FKDZGL_QUERY", isMenu = true, name = "放款对账操作", moduleId = "P2P_C_HUIFUDZGL_FKDZGL", order = 2)
public class LoanQuery extends AbstractDzglServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1906209440880270351L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("--------- 放款对账开始 -----------");
        QueryManage queryManage = serviceSession.getService(QueryManage.class);
        String ordId = request.getParameter("ordId");
        
        BidManage bidManage = serviceSession.getService(BidManage.class);
        UnFreezeManage manage = serviceSession.getService(UnFreezeManage.class);
        
        T6501 t6501 = bidManage.selectT6501(Integer.parseInt(ordId));
        String orderDate = DateParser.format(t6501.F04, "yyyyMMdd");
        TransQueryEntity entity = queryManage.queryTranStatus(ordId, orderDate, QueryMode.LOANS.name());
        if (entity == null)
        {
            bidManage.updateOrderStatus(IntegerParser.parse(ordId));
            processRequest(request, response, "该订单尚未提交到第三方平台！");
            return;
        }
        else
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode) && T6501_F03.CG == t6501.F03)
            {
                processRequest(request, response, "OK订单已成功，无需对账！订单第三方状态："
                    + OrderStatus.valueOf(entity.TransStat).getDescription());
                return;
            }
            else if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode))
            {
                //成功
                if (OrderStatus.P.name().equals(entity.TransStat))
                {
                    if (t6501.F03 != T6501_F03.CG)
                    {
                        //通过放款订单号查找冻结订单号
                        List<T6501> ufOrders = bidManage.queryUnFreezeOrder(Integer.parseInt(ordId));
                        for (T6501 ufOrder : ufOrders)
                        {
                            //查询第三方解冻订单状态
                            TransQueryEntity ufEntity =
                                queryManage.queryTranStatus(String.valueOf(ufOrder.F01),
                                    DateParser.format(ufOrder.F04, "yyyyMMdd"),
                                    QueryMode.FREEZE.name());
                            if (ufEntity != null)
                            {
                                if (HuiFuConstants.Common.SUCCESS_CODE.equals(ufEntity.RespCode))
                                {
                                    //无需解冻或成功
                                    if (OrderStatus.N.name().equals(ufEntity.TransStat))
                                    {
                                        bidManage.updateT6501(t6501.F01, T6501_F03.DQR);
                                        try
                                        {
                                            TenderConfirmExecutor executor =
                                                getResourceProvider().getResource(TenderConfirmExecutor.class);
                                            executor.confirm(Integer.parseInt(ordId), null);
                                            //更新解冻订单状态
                                            manage.updateTrxId(ufEntity.TrxId, IntegerParser.parse(ufEntity.OrdId));
                                            //更新放款订单状态
                                            bidManage.updateOrderDZStatus(Integer.parseInt(ordId), T6501_F03.CG);
                                            logger.info("放款订单：" + ordId + " 对账成功！");
                                            
                                            processRequest(request, response, "OK对账成功！");
                                        }
                                        catch (Exception e)
                                        {
                                            logger.error("LoanQuery.processPost" + e);
                                            bidManage.updateOrderDZStatus(Integer.parseInt(ordId), T6501_F03.SB);
                                            processRequest(request, response, e.getMessage());
                                        }
                                    }
                                    
                                }
                            }
                            bidManage.updateOrderDZStatus(Integer.parseInt(ordId), null);
                        }
                    }
                    else
                    {
                        processRequest(request, response, "该订单已经对账！");
                        return;
                    }
                }
                else
                {
                    bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                    processRequest(request, response, "对账失败，交易失败！");
                    return;
                }
            }
            else
            {
                logger.info("对账失败！原因:" + entity.RespDesc + "！");
                bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                processRequest(request, response, "对账失败！");
                return;
            }
        }
        
        logger.info("--------- 放款对账结束 -----------");
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        logger.error(throwable, throwable);
        if (throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.INFO, throwable.getMessage());
            processRequest(request, response, "对账失败！原因:" + throwable.getMessage() + "！");
        }
        if (throwable instanceof SQLException)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}