package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.p2p.order.ExperTenderRepaymentExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 体验金返还回调
 * @author nieliang
 *
 */
public class BidExperienceRepaymentRet extends AbstractHuifuRetServlet
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("汇付 - 体验金利息返还回调开始");
        TransferManage manage = serviceSession.getService(TransferManage.class);
        TransferEntity entity = manage.transferReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (BidExperienceRepaymentRet.class)
                {
                    ExperTenderRepaymentExecutor executor =
                        getResourceProvider().getResource(ExperTenderRepaymentExecutor.class);
                    executor.confirm(IntegerParser.parse(entity.ordId), null);
                }
            }
            catch (Exception e)
            {
                logger.error("体验金利息返还异常：" + e);
            }
            
            logger.info("体验金利息返还结束【转账】，订单号：" + entity.ordId);
        }
        
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
