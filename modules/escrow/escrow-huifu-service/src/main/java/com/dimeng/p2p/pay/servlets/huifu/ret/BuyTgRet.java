/*
 * 文 件 名:  BuyTgRet.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月6日
 */
package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.usracctpay.UsrAcctPayEntity;
import com.dimeng.p2p.escrow.huifu.service.HFMallChangeManage;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;

/**
 * 商品后面前台页面回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月6日]
 */
public class BuyTgRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        HFUsrAcctPayManage manage = serviceSession.getService(HFUsrAcctPayManage.class);
        UsrAcctPayEntity entity = manage.usrAcctPayDecoder(request);
        String enRetUrl = getResourceProvider().getResource(ConfigureProvider.class).format(URLVariable.MYORDER_URL);
        String noticeMessage = "";
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                HFMallChangeManage mcManage = serviceSession.getService(HFMallChangeManage.class);
                noticeMessage = mcManage.mallChangeTgReturn(IntegerParser.parse(entity.ordId));
                if (StringHelper.isEmpty(noticeMessage))
                {
                    noticeMessage = "恭喜您，购买成功！";
                }
            }
            else
            {
                noticeMessage = "很抱歉，购买失败！提示：" + entity.respDesc;
            }
        }
        else
        {
            noticeMessage = "很抱歉，购买失败！提示：验签失败！";
        }
        
        createNoticeMessagePage(enRetUrl, noticeMessage, response);
    }
}
