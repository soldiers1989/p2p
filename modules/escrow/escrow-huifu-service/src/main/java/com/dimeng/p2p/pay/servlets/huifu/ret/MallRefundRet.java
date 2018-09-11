/*
 * 文 件 名:  MallRefundRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月6日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.order.MallRefundExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 商城退款后台回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月6日]
 */
public class MallRefundRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 商城退款后台回调开始  ----------");
        TransferManage manage = serviceSession.getService(TransferManage.class);
        TransferEntity entity = manage.transferReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                MallRefundExecutor executor = getResourceProvider().getResource(MallRefundExecutor.class);
                Map<String, String> params = new HashMap<String, String>();
                params.put("reason", entity.merPriv != null ? HttpClientHandler.getBase64Decode(entity.merPriv) : "");
                executor.confirm(IntegerParser.parse(entity.ordId), params);
            }
            catch (Exception e)
            {
                logger.error("商城退款后台回调处理失败" + e);
            }
        }
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
