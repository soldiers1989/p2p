/*
 * 文 件 名:  DonationRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.usracctpay.UsrAcctPayEntity;
import com.dimeng.p2p.escrow.huifu.service.DonationManage;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;
import com.dimeng.p2p.order.DonationExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 公益标后台回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public class DonationRet extends AbstractHuifuRetServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 公益标捐款后台回调开始 ----------");
        HFUsrAcctPayManage manage = serviceSession.getService(HFUsrAcctPayManage.class);
        UsrAcctPayEntity entity = manage.usrAcctPayDecoder(request);
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                try
                {
                    DonationExecutor executor = getResourceProvider().getResource(DonationExecutor.class);
                    executor.confirm(IntegerParser.parse(entity.ordId), null);
                }
                catch (Exception e)
                {
                    logger.error("公益标捐款后台回调异常！" + e);
                    //捐款失败，资金返还捐款用户
                    DonationManage donationManage = serviceSession.getService(DonationManage.class);
                    donationManage.failCapitalBack(IntegerParser.parse(entity.ordId));
                }
            }
            else
            {
                logger.info("公益标捐款失败，返回码：" + entity.respCode + " 返回描述：" + entity.respDesc);
            }
            
        }
        else
        {
            logger.info("公益标捐款后台回调，验签失败！");
        }
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
}
