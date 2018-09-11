/*
 * 文 件 名:  BidUnFreezeRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  流标资金解冻回调
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月18日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.freeze.UnFreezeEntity;
import com.dimeng.p2p.escrow.huifu.service.UnFreezeManage;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.order.TenderCancelExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 流标资金解冻回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月18日]
 */
public class BidUnFreezeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("---------- 流标资金解冻回调开始  ---------");
        UnFreezeManage manage = serviceSession.getService(UnFreezeManage.class);
        UnFreezeEntity entity = manage.unFreezeReturnDecoder(request);
        if (null != entity)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                Gson gson = new Gson();
                Map<String, String> params = new HashMap<>();
                if (!StringHelper.isEmpty(entity.merPriv))
                {
                    //获取原T6501主订单
                    params = gson.fromJson(entity.merPriv, new TypeToken<Map<String, String>>()
                    {
                    }.getType());
                    
                    TenderCancelExecutor executor = getResourceProvider().getResource(TenderCancelExecutor.class);
                    if (params != null)
                    {
                        params.put("des", HttpClientHandler.getBase64Decode(params.get("des")));
                    }
                    executor.confirm(IntegerParser.parse(params.get("orderId")), params);
                }
                //更新解冻订单状态、流水号
                manage.updateTrxId(entity.trxId, IntegerParser.parse(entity.ordId));
            }
            else
            {
                logger.info(String.format("流标资金解冻回调订单：%s 第三方不成功！原因：%s", entity.ordId, entity.respDesc));
            }
        }
        else
        {
            logger.info("流标资金解冻回调失败，原因：验签失败！");
        }
        
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
