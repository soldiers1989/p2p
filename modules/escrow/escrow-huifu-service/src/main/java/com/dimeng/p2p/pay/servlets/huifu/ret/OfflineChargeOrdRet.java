/*
 * 文 件 名:  OfflineChargeOrderret.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S71.enums.T7150_F05;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.HFOfflineChargeManage;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.p2p.order.OfflineChargeOrderExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 线下充值审核通过前台回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public class OfflineChargeOrdRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 线下充值审核通过后台回调开始 ----------");
        TransferManage transferManage = serviceSession.getService(TransferManage.class);
        TransferEntity entity = transferManage.transferReturnDecoder(request);
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                try
                {
                    synchronized (OfflineChargeOrdRet.class)
                    {
                        OfflineChargeOrderExecutor executor =
                            getResourceProvider().getResource(OfflineChargeOrderExecutor.class);
                        executor.confirm(IntegerParser.parse(entity.ordId), null);
                        //HFOfflineChargeManage chargeManage = serviceSession.getService(HFOfflineChargeManage.class);
                        //线下充值成功后，增加平台资金流水记录以及处理账户金额
                        //chargeManage.updatePtRecord(IntegerParser.parse(entity.ordId));
                    }
                }
                catch (Exception e)
                {
                    logger.error(String.format("线下充值审核通过%s异常", entity.ordId), e);
                }
            }
            else
            {
                logger.info("线下充值失败，返回码：" + entity.respCode + " 返回描述：" + entity.respDesc);
                HFOfflineChargeManage chargeManage = serviceSession.getService(HFOfflineChargeManage.class);
                //第三方失败，平台数据状态更改
                chargeManage.updateT7150(IntegerParser.parse(entity.ordId), T7150_F05.YQX);
            }
        }
        else
        {
            logger.info("线下充值失败，验签失败！");
        }
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
