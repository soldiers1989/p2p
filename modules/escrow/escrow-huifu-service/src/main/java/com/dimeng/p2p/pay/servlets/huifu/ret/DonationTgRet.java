/*
 * 文 件 名:  DonationTgRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月5日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S65.entities.T6554;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.usracctpay.UsrAcctPayEntity;
import com.dimeng.p2p.escrow.huifu.service.DonationManage;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月5日]
 */
public class DonationTgRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 公益标捐款前台回调开始 ----------");
        HFUsrAcctPayManage manage = serviceSession.getService(HFUsrAcctPayManage.class);
        UsrAcctPayEntity entity = manage.usrAcctPayDecoder(request);
        int orderId = Integer.parseInt(entity != null ? entity.ordId : request.getParameter("OrdId"));
        String returnMassage = "";
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                DonationManage donationManage = serviceSession.getService(DonationManage.class);
                returnMassage = donationManage.donationTgReturn(orderId);
                if (StringHelper.isEmpty(returnMassage))
                {
                    getController().prompt(request, response, PromptLevel.INFO, "恭喜您，捐赠成功！");
                }
                else
                {
                    getController().prompt(request, response, PromptLevel.WARRING, returnMassage);
                }
            }
            else
            {
                getController().prompt(request, response, PromptLevel.WARRING, "很抱歉，捐赠失败！提示：" + entity.respDesc);
            }
        }
        else
        {
            getController().prompt(request, response, PromptLevel.WARRING, "很抱歉，捐赠失败！提示：返回数据验签失败！");
        }
        
        T6554 t6554 = manage.selectT6554(orderId);
        sendRedirect(request, response, getURL(t6554.F03));
    }
    
    protected String getURL(int loanId)
        throws IOException
    {
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        StringBuilder url = new StringBuilder(configureProvider.format(URLVariable.GYB_BDXQ));
        url.append(Integer.toString(loanId)).append(resourceProvider.getSystemDefine().getRewriter().getViewSuffix());
        return url.toString();
    }
    
}
