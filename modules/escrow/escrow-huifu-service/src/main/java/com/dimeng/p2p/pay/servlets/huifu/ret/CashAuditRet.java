package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.CashAuditEntity;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.p2p.order.WithdrawExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 取现复核回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class CashAuditRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 取现复核回调开始 ----------");
        WithdrawManage manage = serviceSession.getService(WithdrawManage.class);
        CashAuditEntity entity = new CashAuditEntity();
        String cmdId = request.getParameter("CmdId");
        //只有在即时取现时才会异步回调，其他取现方式都是同步回调
        if (cmdId != null && !"".equals(cmdId))
        {
            //同步回调参数解析
            logger.info("取现复核同步结果回调！");
            entity = manage.cashAuditRetDecoder(request);
        }
        else
        {
            //异步回调参数解析
            logger.info("取现复核异步结果回调！");
            entity = manage.cashAuditRetAsynDecoder(request);
        }
        
        if (entity != null
            && (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode) || HuiFuConstants.Common.PROCESSING.equals(entity.respCode)))
        {
            try
            {
                if ("S".equals(entity.auditFlag))
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("feeAmt", entity.feeAmt);
                    params.put("check_reason", request.getParameter("check_reason"));
                    
                    //处理提现
                    WithdrawExecutor executor = getResourceProvider().getResource(WithdrawExecutor.class);
                    executor.confirm(IntegerParser.parse(entity.ordId), params);
                }
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
        else if (null != entity
            && !(HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode) || HuiFuConstants.Common.PROCESSING.equals(entity.respCode)))
        {//取现审核失败
         //取现审核不通过
            int txReqID = manage.updateTxStatus(IntegerParser.parse(entity.ordId));
            //处理资金流
            manage.trade(txReqID);
        }
        else
        {
            logger.error("取现审核异常,订单号：" + request.getParameter("OrdId"));
            //取现审核不通过
            int txReqID = manage.updateTxStatus(IntegerParser.parse(entity.ordId));
            //处理资金流
            manage.trade(txReqID);
        }
        
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
