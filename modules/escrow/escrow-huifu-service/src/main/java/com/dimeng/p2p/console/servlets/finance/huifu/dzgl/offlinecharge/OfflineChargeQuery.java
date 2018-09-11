package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.offlinecharge;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F11;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TrfItem;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.HFOfflineChargeManage;
import com.dimeng.p2p.escrow.huifu.service.TrfQueryManage;
import com.dimeng.p2p.order.OfflineChargeOrderExecutor;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 线下充值对账servlet
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年07月07日]
 */

@Right(id = "P2P_C_HUIFUDZGL_XXCZDZGL_QUERY", isMenu = true, name = "线下充值对账操作", moduleId = "P2P_C_HUIFUDZGL_XXCZDZGL", order = 2)
public class OfflineChargeQuery extends AbstractDzglServlet
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
        logger.info("--------- 线下充值对账开始 -----------");
        TrfQueryManage manage = serviceSession.getService(TrfQueryManage.class);
        String ordId = request.getParameter("ordId");
        
        BidManage bidManage = serviceSession.getService(BidManage.class);
        final T6501 t6501 = bidManage.selectT6501(Integer.parseInt(ordId));
        //订单已经是成功状态，无需对账
        if (T6501_F03.CG == t6501.F03)
        {
            processRequest(request, response, "OK订单已经是成功状态，无需对账！");
            return;
        }
        final int pageNum = IntegerParser.parse(request.getParameter(PAGING_CURRENT));
        PagingResult<TrfItem> pagingResult = manage.queryTrfReconcilia(new QueryCond()
        {
            
            @Override
            public String pageSize()
            {
                return String.valueOf(1000);
            }
            
            @Override
            public String pageNum()
            {
                return pageNum > 0 ? String.valueOf(pageNum) : "1";
            }
            
            @Override
            public String beginDate()
            {
                return DateParser.format(t6501.F04, "yyyyMMdd");
            }
            
            //结束时间 - 创建时间 加一天
            @Override
            public String endDate()
            {
                return DateParser.format(DateUtils.addDays(t6501.F04, 1), "yyyyMMdd");
            }
        });
        
        TrfItem[] trfItems = pagingResult.getItems();
        
        //校验回调回来的第三方数据中是否包含了此订单
        if (trfItems != null)
        {
            for (TrfItem trfItem : trfItems)
            {
                if (ordId.equals(trfItem.OrdId))
                {
                    //交易状态 S--成功，F--失败，I--初始
                    if ("S".equals(trfItem.TransStat) && T6501_F11.F == t6501.F11)
                    {
                        bidManage.updateT6501(t6501.F01, T6501_F03.DQR);
                        try
                        {
                            OfflineChargeOrderExecutor executor =
                                getResourceProvider().getResource(OfflineChargeOrderExecutor.class);
                            executor.confirm(Integer.parseInt(ordId), null);
                            //线下充值成功后，增加平台资金流水记录以及处理账户金额
                            HFOfflineChargeManage offlineCharge =
                                serviceSession.getService(HFOfflineChargeManage.class);
                            offlineCharge.updatePtRecord(IntegerParser.parse(ordId));
                            //更新线下充值订单状态
                            bidManage.updateOrderDZStatus(Integer.parseInt(ordId), T6501_F03.CG);
                            logger.info("线下充值订单：" + ordId + " 对账成功！");
                            
                            processRequest(request, response, "OK对账成功！");
                            return;
                        }
                        catch (Exception e)
                        {
                            bidManage.updateOrderDZStatus(Integer.parseInt(ordId), T6501_F03.SB);
                            logger.error("线下充值 - OfflineChargeQuery.processPost" + e);
                        }
                    }
                    else if ("I".equals(trfItem.TransStat))
                    {
                        logger.info("订单处于初始化状态，无法对账！");
                        processRequest(request, response, "订单处于初始化状态，无法对账！");
                        return;
                    }
                    else if ("F".equals(trfItem.TransStat))
                    {
                        logger.info("对账成功，第三方订单处于失败状态！");
                        bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                        processRequest(request, response, "OK对账成功，第三方订单处于失败状态！");
                        return;
                    }
                }
            }
        }
        
        bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
        processRequest(request, response, "OK对账成功，第三方无此订单数据！");
        logger.info("--------- 线下充值对账结束 -----------");
        
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