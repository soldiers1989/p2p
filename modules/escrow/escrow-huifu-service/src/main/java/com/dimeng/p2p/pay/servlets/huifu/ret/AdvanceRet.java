package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.payment.PaymentEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.order.AdvanceExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 垫付回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class AdvanceRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("汇付 - 垫付回调开始");
        BidManage bidManage = serviceSession.getService(BidManage.class);
        PaymentEntity entity = bidManage.payment(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (AdvanceRet.class)
                {
                    Gson gson = new Gson();
                    Map<String, String> params = new HashMap<>();
                    if (!StringHelper.isEmpty(entity.merPriv))
                    {
                        params = gson.fromJson(entity.merPriv, new TypeToken<Map<String, String>>()
                        {
                        }.getType());
                    }
                    AdvanceExecutor executor = getResourceProvider().getResource(AdvanceExecutor.class);
                    executor.confirm(IntegerParser.parse(entity.ordId), params);
                }
            }
            catch (Exception e)
            {
                logger.error(String.format("垫付异常订单：%s", entity.ordId), e);
            }
        }
        else
        {
            //更新订单状态为失败
            bidManage.updateOrderStatus(IntegerParser.parse(request.getParameter("OrdId")));
        }
        
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
}
