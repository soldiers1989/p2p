package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.payment.PaymentEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.order.TenderRepaymentExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 还款回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class TenderRepaymentRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("------ 还款回调开始 ----------");
        BidManage bidManage = serviceSession.getService(BidManage.class);
        PaymentEntity entity = bidManage.payment(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (TenderRepaymentRet.class)
                {
                    getResourceProvider().getResource(TenderRepaymentExecutor.class)
                        .confirm(IntegerParser.parse(entity.ordId), null);
                }
            }
            catch (Exception e)
            {
                logger.error(String.format("还款订单%s执行异常", entity.ordId), e);
                bidManage.updatePayFailT6252(IntegerParser.parse(entity.ordId));
            }
        }
        else
        {
            bidManage.updatePayFailT6252(IntegerParser.parse(request.getParameter("OrdId")));
        }
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
