package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidCancleEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.order.TenderCancelExecutor;

/**
 * 
 * 投标取消回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class BidCancleRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 投标取消回调开始 ----------");
        BidManage manage = serviceSession.getService(BidManage.class);
        BidCancleEntity entity = manage.bidCancleReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (BidCancleRet.class)
                {
                    TenderCancelExecutor executor = getResourceProvider().getResource(TenderCancelExecutor.class);
                    executor.confirm(Integer.parseInt(entity.ordId), null);
                }
            }
            catch (Exception e)
            {
                logger.error("投标撤销异常" + e);
            }
        }
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}