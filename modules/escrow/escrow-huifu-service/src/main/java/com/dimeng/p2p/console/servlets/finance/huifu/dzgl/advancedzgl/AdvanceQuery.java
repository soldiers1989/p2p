package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.advancedzgl;

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
import com.dimeng.p2p.S65.entities.T6514;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.TransQueryEntity;
import com.dimeng.p2p.escrow.huifu.enumeration.OrderStatus;
import com.dimeng.p2p.escrow.huifu.enumeration.QueryMode;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.QueryManage;
import com.dimeng.p2p.order.AdvanceExecutor;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 垫付对账servlet
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年07月07日]
 */

@Right(id = "P2P_C_HUIFUDZGL_DFDZGL_QUERY", isMenu = true, name = "垫付对账操作", moduleId = "P2P_C_HUIFUDZGL_DFDZGL", order = 2)
public class AdvanceQuery extends AbstractDzglServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
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
        logger.info("--------- 垫付对账开始 -----------");
        QueryManage queryManage = serviceSession.getService(QueryManage.class);
        String ordId = request.getParameter("ordId");
        
        BidManage bidManage = serviceSession.getService(BidManage.class);
        
        T6501 t6501 = bidManage.selectT6501(Integer.parseInt(ordId));
        String orderDate = DateParser.format(t6501.F04, "yyyyMMdd");
        TransQueryEntity entity = queryManage.queryTranStatus(ordId, orderDate, QueryMode.REPAYMENT.name());
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
                processRequest(request, response, "OK订单已成功，无需对账！");
                return;
            }
            else if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode))
            {
                if (OrderStatus.P.name().equals(entity.TransStat))
                {
                    if (t6501.F03 != T6501_F03.CG)
                    {
                        //更新订单状态
                        bidManage.updateT6501(IntegerParser.parse(ordId), T6501_F03.DQR);
                        T6514 t6514 = new T6514();
                        try
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            //垫付期号
                            t6514 = bidManage.selectT6514(null, IntegerParser.parse(ordId));
                            params.put("advanceNum", String.valueOf(t6514.F08));
                            
                            AdvanceExecutor executor = getResourceProvider().getResource(AdvanceExecutor.class);
                            executor.confirm(IntegerParser.parse(ordId), params);
                            //更新不良债权购买订单
                            bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.CG);
                            logger.info("还款订单：" + ordId + " 对账成功！");
                            
                            processRequest(request, response, "OK对账成功！");
                            return;
                        }
                        catch (Exception e)
                        {
                            //购买失败，更新还款计划为未还
                            bidManage.updateAdvanceFailT6252(IntegerParser.parse(ordId), t6514.F08);
                            //更新对账状态
                            bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                            
                            logger.error("AdvanceQuery.processPost" + e);
                            processRequest(request, response, "对账失败！失败原因：" + e.getMessage());
                            return;
                        }
                    }
                    else
                    {
                        processRequest(request, response, "OK订单是成功状态，无需对账！");
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
                    bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                    processRequest(request, response, "OK对账成功，订单处于失败状态！");
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