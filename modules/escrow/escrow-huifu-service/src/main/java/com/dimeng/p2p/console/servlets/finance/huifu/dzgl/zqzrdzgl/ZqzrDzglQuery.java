package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.zqzrdzgl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
import com.dimeng.p2p.escrow.huifu.entity.query.BidExchangeQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.BidReconciliationEntity;
import com.dimeng.p2p.escrow.huifu.enumeration.OrderStatus;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.QueryManage;
import com.dimeng.p2p.order.TenderExchangeExecutor;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 债权转让状态查询
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUZQZRQUERY", isMenu = true, name = "去对账", moduleId = "P2P_C_HUIFU_ZQZRDZGL", order = 1)
public class ZqzrDzglQuery extends AbstractDzglServlet
{
    
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("OrdId", ordId);
        map.put("BeginDate", orderDate);
        map.put("EndDate", orderDate);
        map.put("PageNum", "1");
        map.put("PageSize", "1");
        BidExchangeQueryEntity entity = queryManage.queryZqzrStatus(map);
        if (entity == null)
        {
            bidManage.updateOrderStatus(IntegerParser.parse(ordId));
            processRequest(request, response, "该订单尚未提交到第三方平台！");
            return;
        }
        else
        {
            List<BidReconciliationEntity> list = entity.BidCaReconciliationList;
            BidReconciliationEntity bidReEntity = list.get(0);
            if ("000".equals(entity.RespCode) && T6501_F03.CG == t6501.F03)
            {
                processRequest(request, response, "OK订单已成功，无需对账！订单第三方状态："
                    + OrderStatus.valueOf(bidReEntity.TransStat).getDescription());
                return;
            }
            else if ("000".equals(entity.RespCode))
            {
                if (OrderStatus.S.name().equals(bidReEntity.TransStat))
                {
                    TenderExchangeExecutor executor = getResourceProvider().getResource(TenderExchangeExecutor.class);
                    executor.confirm(IntegerParser.parse(ordId), null);
                    processRequest(request, response, "对账成功！");
                    return;
                }
                else if (OrderStatus.I.name().equals(bidReEntity.TransStat))
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
                logger.info("对账失败！原因:" + entity.RespDesc + "！");
                processRequest(request, response, "对账失败！");
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
