/*
 * 文 件 名:  BidActivityRepaymentRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月28日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.p2p.order.ActivityRepaymentExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 加息券返还回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月28日]
 */
public class BidActivityRepaymentRet extends AbstractHuifuRetServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("汇付 - 加息券利息返还回调开始");
        TransferManage manage = serviceSession.getService(TransferManage.class);
        TransferEntity entity = manage.transferReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (BidActivityRepaymentRet.class)
                {
                    ActivityRepaymentExecutor executor =
                        getResourceProvider().getResource(ActivityRepaymentExecutor.class);
                    executor.confirm(IntegerParser.parse(entity.ordId), null);
                }
            }
            catch (Exception e)
            {
                logger.error("加息券利息返还异常：" + e);
            }
            
            logger.info("加息券利息返还结束【转账】，订单号：" + entity.ordId);
        }
        
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
