/*
 * 文 件 名:  BuyRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月6日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.usracctpay.UsrAcctPayEntity;
import com.dimeng.p2p.escrow.huifu.service.HFMallChangeManage;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;
import com.dimeng.p2p.order.MallChangeExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 商品购买后台回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月6日]
 */
public class BuyRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 商品购买后台回调开始  ----------");
        HFUsrAcctPayManage manage = serviceSession.getService(HFUsrAcctPayManage.class);
        UsrAcctPayEntity entity = manage.usrAcctPayDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                MallChangeExecutor executor = getResourceProvider().getResource(MallChangeExecutor.class);
                executor.confirm(IntegerParser.parse(entity.ordId), null);
            }
            catch (Exception e)
            {
                logger.error("商品购买后台回调处理失败" + e);
                HFMallChangeManage mcManage = serviceSession.getService(HFMallChangeManage.class);
                mcManage.mallChangeFailBack(IntegerParser.parse(entity.ordId));
            }
        }
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
