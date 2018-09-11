package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.p2p.order.TransferExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 转账回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class TransferRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("---------- 转账回调开始 -----------");
        TransferManage manage = serviceSession.getService(TransferManage.class);
        TransferEntity entity = manage.transferReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (TransferRet.class)
                {
                    TransferExecutor executor = getResourceProvider().getResource(TransferExecutor.class);
                    executor.confirm(IntegerParser.parse(entity.ordId), null);
                }
            }
            catch (Exception e)
            {
                logger.error(String.format("转账代扣订单%s异常", entity.ordId), e);
            }
        }
        //        manage.decoder(request);
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
