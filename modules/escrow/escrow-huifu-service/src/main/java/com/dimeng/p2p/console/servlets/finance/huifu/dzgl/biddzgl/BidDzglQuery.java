package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.biddzgl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import com.dimeng.p2p.order.TenderOrderExecutor;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 投标状态查询
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUBIDQUERY", isMenu = true, name = "去对账", moduleId = "P2P_C_HUIFU_TBDZGL", order = 1)
public class BidDzglQuery extends AbstractDzglServlet
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
        QueryManage queryManage = serviceSession.getService(QueryManage.class);
        String ordId = request.getParameter("ordId");
        
        BidManage bidManage = serviceSession.getService(BidManage.class);
        
        T6501 t6501 = bidManage.selectT6501(Integer.parseInt(ordId));
        String orderDate = DateParser.format(t6501.F04, "yyyyMMdd");
        TransQueryEntity entity = queryManage.queryTranStatus(ordId, orderDate, QueryMode.TENDER.name());
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
                if (OrderStatus.N.name().equals(entity.TransStat))
                {
                    // 再次查询对应的冻结订单记录
                    String freeOrdId = request.getParameter("freezeOrdId");
                    T6501 freeze = bidManage.selectT6501(Integer.parseInt(freeOrdId));
                    String freeOorderDate = DateParser.format(freeze.F04, "yyyyMMdd");
                    TransQueryEntity freeEntity =
                        queryManage.queryTranStatus(freeOrdId, freeOorderDate, QueryMode.FREEZE.name());
                    if (HuiFuConstants.Common.SUCCESS_CODE.equals(freeEntity.RespCode))
                    {
                        if (OrderStatus.F.name().equals(freeEntity.TransStat))
                        {
                            TenderOrderExecutor executor = getResourceProvider().getResource(TenderOrderExecutor.class);
                            Map<String, String> map = new HashMap<>();
                            map.put("trxId", "");
                            map.put("freezeOrdId", freeOrdId);
                            map.put("freezeTrxId", freeEntity.TrxId);
                            executor.confirm(IntegerParser.parse(ordId), map);
                            processRequest(request, response, "OK对账成功！");
                            return;
                        }
                        else
                        {
                            logger.info("对账失败！原因:" + entity.RespDesc + "！");
                            processRequest(request, response, "对账失败！");
                            bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                            bidManage.updateOrderDZStatus(IntegerParser.parse(freeOrdId), T6501_F03.SB);
                            return;
                        }
                    }
                    else
                    {
                        logger.info("对账失败！原因:" + entity.RespDesc + "！");
                        processRequest(request, response, "对账失败！");
                        bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                        bidManage.updateOrderDZStatus(IntegerParser.parse(freeOrdId), T6501_F03.SB);
                        return;
                    }
                }
                else if (OrderStatus.I.name().equals(entity.TransStat))
                {
                    processRequest(request, response, "订单处于初始化状态，无法对账！");
                    return;
                }
                else
                {
                    processRequest(request, response, "OK对账成功，订单处于失败状态！");
                    bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                    return;
                }
            }
            else
            {
                processRequest(request, response, "对账失败！原因:" + entity.RespDesc + "！");
                bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                return;
            }
        }
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
