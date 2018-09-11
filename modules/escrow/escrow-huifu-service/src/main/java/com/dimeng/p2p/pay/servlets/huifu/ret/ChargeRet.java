package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeEntity;
import com.dimeng.p2p.escrow.huifu.executor.HFChargeOrderExecutor;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;
import com.dimeng.p2p.escrow.huifu.service.HuifuChargeManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 充值回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class ChargeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 充值回调开始 ----------");
        HuifuChargeManage hfManage = serviceSession.getService(HuifuChargeManage.class);
        BankCardManage manage = serviceSession.getService(BankCardManage.class);
        ChargeEntity entity = hfManage.chrageRetDecode(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            Map<String, String> params = new HashMap<>();
            //第三方交易唯一标识
            params.put("paymentOrderId", entity.trxId);
            //充值手续费
            params.put("feeAmt", entity.feeAmt);
            //手续费扣款商户号
            params.put("feeCustId", entity.feeCustId);
            //如果开通的是快捷支付，则保存返回的银行卡信息
            if (!"".equals(entity.cardId) && entity.cardId != null)
            {
                logger.info("---该笔充值已绑定快捷卡---");
                manage.addQuickPayCardId(entity);
            }
            
            HFChargeOrderExecutor executor = getResourceProvider().getResource(HFChargeOrderExecutor.class);
            
            executor.submit(IntegerParser.parse(entity.ordId), null);
            
            executor.confirm(IntegerParser.parse(entity.ordId), params);
        }
        
        printMark(response, entity != null ? entity.trxId : request.getParameter("TrxId"));
    }
    
    @Override
    protected boolean mustAuthenticated()
    {
        return false;
    }
    
}
