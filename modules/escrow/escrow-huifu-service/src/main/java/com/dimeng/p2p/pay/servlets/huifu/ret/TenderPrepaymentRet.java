package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.payment.PaymentEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.order.TenderPrepaymentExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提前还款
 * @author nieliang
 *
 */
public class TenderPrepaymentRet extends AbstractHuifuRetServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("汇付 - 提前还款回调开始");
        BidManage bidManage = serviceSession.getService(BidManage.class);
        PaymentEntity entity = bidManage.payment(request);
        
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            Map<String, String> params = new HashMap<String, String>();
            try
            {
                synchronized (TenderPrepaymentRet.class)
                {
                    getResourceProvider().getResource(TenderPrepaymentExecutor.class)
                        .confirm(IntegerParser.parse(entity.ordId), params);
                }
                
            }
            catch (Exception e)
            {
                logger.error(String.format("还款订单%s执行异常", entity.ordId), e);
                bidManage.updatePreFailT6252(IntegerParser.parse(entity.ordId));
            }
        }
        else
        {
            bidManage.updatePreFailT6252(IntegerParser.parse(request.getParameter("OrdId")));
        }
        
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
