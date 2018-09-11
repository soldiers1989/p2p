package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.UnFreezeCond;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.UnFreezeManage;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.order.TenderOrderExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.DateTimeParser;
import com.dimeng.util.parser.IntegerParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 投标后台回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class BidRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 投标后台回调开始 ----------");
        BidManage manage = serviceSession.getService(BidManage.class);
        BidEntity entity = manage.bidReturnDecoder(request);
        if (entity != null
            && (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode) || HuiFuConstants.Common.BID_SUCCESS.equals(entity.respCode)))
        {
            final String freezeTrxId = entity.freezeTrxId;
            try
            {
                
                TenderOrderExecutor executor = getResourceProvider().getResource(TenderOrderExecutor.class);
                Gson gson = new Gson();
                Map<String, String> params = new HashMap<>();
                if (!StringHelper.isEmpty(entity.merPriv))
                {
                    params =
                        gson.fromJson(HttpClientHandler.getBase64Decode(entity.merPriv),
                            new TypeToken<Map<String, String>>()
                            {
                            }.getType());
                }
                
                params.put("trxId", entity.trxId);
                params.put("freezeOrdId", entity.freezeOrdId);
                params.put("freezeTrxId", entity.freezeTrxId);
                executor.confirm(IntegerParser.parse(entity.ordId), params);
            }
            catch (Exception e)
            {
                logger.error(String.format("%s投资处理异常：%s，冻结订单号：%s，冻结流水：%s",
                    DateTimeParser.format(new Date(System.currentTimeMillis())),
                    e.getMessage(),
                    entity.freezeOrdId,
                    entity.freezeTrxId));
                UnFreezeManage unFreezeManage = serviceSession.getService(UnFreezeManage.class);
                unFreezeManage.updateOrderFailure(IntegerParser.parse(entity.freezeOrdId), entity.freezeTrxId);
                final T6501 t6501 =
                    unFreezeManage.addUnfreezeOrder(IntegerParser.parse(entity.freezeOrdId),
                        BigDecimalParser.parse(entity.transAmt));
                unFreezeManage.sendUnFreeze(new UnFreezeCond()
                {
                    
                    @Override
                    public String trxId()
                    {
                        return freezeTrxId;
                    }
                    
                    @Override
                    public String retUrl()
                    {
                        return null;
                    }
                    
                    @Override
                    public String ordId()
                    {
                        return String.valueOf(t6501.F01);
                    }
                    
                    @Override
                    public String ordDate()
                    {
                        return DateParser.format(t6501.F04, "yyyyMMdd");
                    }
                });
            }
        }
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}