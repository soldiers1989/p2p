/*
 * 文 件 名:  Payment.java
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
import com.dimeng.p2p.escrow.fuyou.entity.enums.FuyouTypeEnum;
import com.dimeng.p2p.escrow.fuyou.executor.FYRepaymentExecutor;
import com.dimeng.p2p.escrow.fuyou.service.PublicManage;
import com.dimeng.p2p.modules.bid.user.service.TenderRepaymentManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 手工还款
 * 
 * @author  zhoulantao
 * @version  [版本号, 2015年12月14日]
 */
public class Payment extends AbstractSecureServlet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -9043714174462748459L;
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("APP富友托管-还款-开始——IP:" + request.getRemoteAddr());
        int loanId = IntegerParser.parse(getParameter(request, "loanId"));
        
        // 当前期数
        int currentTerm = IntegerParser.parse(getParameter(request, "currentTerm"));
        
        PublicManage publicManage = serviceSession.getService(PublicManage.class);
        int[] orderIds = null;
        orderIds = publicManage.selectPayment(loanId, currentTerm);
        TenderRepaymentManage manage = serviceSession.getService(TenderRepaymentManage.class);
        if (orderIds == null)
        {
            orderIds = manage.repayment(loanId, currentTerm);
        }
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            FYRepaymentExecutor executor = getResourceProvider().getResource(FYRepaymentExecutor.class);
            
            if (orderIds != null && orderIds.length > 0)
            {
                publicManage.updtateT6501F10(orderIds, FuyouTypeEnum.SDHK.name());
                logger.info("标ID：" + loanId + "-还款总数：" + orderIds.length);
                int i = 0;
                params.put("hint", "success");
                for (int orderId : orderIds)
                {
                    i++;
                    publicManage.searchT6501(serviceSession, orderId, params, true);
                    switch (params.get("state"))
                    {
                        case "DTJ":
                            executor.submit(orderId, params);
                            if (!"true".equals(params.get("success")))
                            {
                                logger.info("第" + i + "条还款-失败");
                                params.put("hint", "fail");
                                // 失败时将垫付记录改回未还
                                manage.updateT6252(loanId, currentTerm);
                                break;
                            }
                            logger.info("第" + i + "条还款-成功");
                            executor.confirm(orderId, params);
                            break;
                        case "DQR":
                            // 第三方成功，平台未更新
                            executor.confirm(orderId, params);
                            logger.info("第" + i + "条还款-确认成功");
                            break;
                        case "CG":
                            logger.info("第" + i + "条还款-已确认成功");
                            break;
                        default:
                            executor.confirm(orderId, params);
                            logger.info("第" + i + "条还款-确认成功");
                            break;
                    }
                }
                logger.info("标ID：" + loanId + "-第" + --i + "条还款-还款调用结束。");
            }
            logger.info(" 前台手动还款-标Id:" + loanId);
        }
        catch (Throwable e)
        {
            manage.updateT6252(loanId, currentTerm);
            throw e;
        }
        
        if ("success".equals(params.get("hint")))
        {
            setReturnMsg(request, response, ExceptionCode.SUCCESS, "还款成功");
            return;
        }
        else
        {
            setReturnMsg(request, response, ExceptionCode.ERROR, "还款失败");
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