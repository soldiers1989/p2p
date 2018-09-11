/*
 * 文 件 名:  Prepayment.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  zhoulantao
 * 修改时间:  2015年12月14日
 */
package com.dimeng.p2p.app.servlets.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.app.servlets.AbstractSecureServlet;
import com.dimeng.p2p.app.servlets.platinfo.ExceptionCode;
import com.dimeng.p2p.escrow.fuyou.executor.FYPrepaymentExecutor;
import com.dimeng.p2p.escrow.fuyou.service.PrepaymentManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提前还款
 * 
 * @author  zhoulantao
 * @version  [版本号, 2015年12月14日]
 */
public class Prepayment extends AbstractSecureServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 8967279058042949929L;
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("APP富友托管-提前还款-开始——IP:" + request.getRemoteAddr());
        int loanId = IntegerParser.parse(getParameter(request, "loanId"));
        
        // 当前期数
        int currentTerm = IntegerParser.parse(getParameter(request, "currentTerm"));
        // TenderPrepaymentManageImpl
        PrepaymentManage manage = serviceSession.getService(PrepaymentManage.class);
        Map<String, String> params = new HashMap<String, String>();
        // 提前还款<本期利息+1，本金，提前还款违约金，提前还款手续费>
        int[] orderIds = manage.prepayment(loanId, currentTerm, params);
        if (orderIds == null)
        {
            setReturnMsg(request, response, ExceptionCode.SUCCESS, "没有需要还款的订单");
            return;
        }
        String orderIdsStr = "";
        for (int i = 0; i < orderIds.length; i++)
        {
            if (i == orderIds.length - 1)
            {
                orderIdsStr = orderIdsStr + orderIds[i];
            }
            else
            {
                orderIdsStr = orderIdsStr + orderIds[i] + ",";
            }
        }
        params.put("orderIdsStr", orderIdsStr);
        try
        {
            FYPrepaymentExecutor executor = getResourceProvider().getResource(FYPrepaymentExecutor.class);
            if (orderIds != null && orderIds.length > 0)
            {
                params.put("hint", "succeed");
                logger.info("标ID：" + loanId + "-提前还款总数：" + orderIds.length);
                int i = 0;
                boolean flag = true;
                for (int orderId : orderIds)
                {
                    i++;
                    logger.info("第" + i + "条提前还款");
                    if ("false".equals(params.get("flag")))
                    {
                        manage.selectT6501(orderId, serviceSession, params);
                        flag = false;
                    }
                    if (flag)
                    {
                        executor.submit(orderId, params);
                        // 如果转账失败，则执行下一个
                        if (!"true".equals(params.get("success")))
                        {
                            logger.info("第" + i + "条提前还款-失败");
                            params.put("hint", "fail");
                            // 失败时将还款记录改回未还
                            manage.updateT6252(loanId, currentTerm);
                            continue;
                        }
                        else
                        {
                            logger.info("第" + i + "条提前还款-成功");
                        }
                        executor.confirm(orderId, params);
                        logger.info("第" + i + "条提前还款-确认成功");
                    }
                    else
                    {
                        switch (params.get("state"))
                        {
                            case "DTJ":
                                executor.submit(orderId, params);
                                if (!"true".equals(params.get("success")))
                                {
                                    logger.info("第" + i + "条提前还款-失败");
                                    params.put("hint", "fail");
                                    // 失败时将还款记录改回未还
                                    manage.updateT6252(loanId, currentTerm);
                                    break;
                                }
                                logger.info("第" + i + "条提前还款-成功");
                                executor.confirm(orderId, params);
                                break;
                            case "DQR":
                                // 第三方成功，平台未更新
                                executor.confirm(orderId, params);
                                logger.info("第" + i + "条提前还款-确认成功");
                                break;
                            case "CG":
                                logger.info("第" + i + "条提前还款-已确认成功");
                                break;
                            default:
                                executor.confirm(orderId, params);
                                logger.info("第" + i + "条提前还款-确认成功");
                                break;
                        }
                    }
                }
                logger.info("标ID：" + loanId + "-提前还款-调用结束");
            }
        }
        catch (Throwable e)
        {
            // 失败时将还款记录改回未还
            manage.updateT6252(loanId, currentTerm);
            throw e;
        }
        if ("fail".equals(params.get("hint")))
        {
            setReturnMsg(request, response, ExceptionCode.ERROR, "提前还款失败");
            logger.error("提前还款失败-标Id:" + loanId);
            return;
        }
        else
        {
            setReturnMsg(request, response, ExceptionCode.SUCCESS, "提前还款成功");
            logger.error("提前还款成功-标Id:" + loanId);
            return;
        }
    }
    
    @Override
    protected void processGet(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
}