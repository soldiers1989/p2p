package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.freeze.FreezeEntity;
import com.dimeng.p2p.escrow.huifu.service.FreezeManage;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提现冻结订单回调
 * @author nieliang
 *
 */
public class WithdrawFreezeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("--------- 提现冻结回调开始 ------------");
        FreezeManage manage = serviceSession.getService(FreezeManage.class);
        FreezeEntity entity = manage.freezeRetDecoder(request);
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
        
        WithdrawManage withdrawManage = serviceSession.getService(WithdrawManage.class);
        if (entity != null)
        {
            //通过冻结订单号找到提现订单号
            int orderId = manage.selectT6515(IntegerParser.parse(entity.ordId));
            
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                try
                {
                    //更新提现数据
                    withdrawManage.doConfim(orderId);
                    //更新冻结订单状态、流水号
                    manage.updateTrxId(entity.trxId, IntegerParser.parse(entity.ordId));
                }
                catch (Exception e)
                {
                    logger.error("提现冻结订单回调异常：" + e);
                }
            }
            else
            {
                //解冻平台金额
                withdrawManage.unFreze(orderId);
            }
        }
        else
        {
            logger.error("提现冻结回调失败，原因：验签失败！");
        }
    }
}
