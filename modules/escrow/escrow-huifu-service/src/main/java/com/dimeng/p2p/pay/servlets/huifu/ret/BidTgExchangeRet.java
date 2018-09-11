package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bidexchange.BidExchangeEntity;
import com.dimeng.p2p.escrow.huifu.service.BidExchangeManage;
import com.dimeng.p2p.variables.defines.URLVariable;

/**
 * 
 * 债权转让提前回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class BidTgExchangeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 债权转让页面回调开始 ----------");
        BidExchangeManage manage = serviceSession.getService(BidExchangeManage.class);
        BidExchangeEntity entity = manage.bidExchangeReturnDecoder(request);
        String location = getResourceProvider().getResource(ConfigureProvider.class).format(URLVariable.USER_ZQYZR);
        int desc = 0;
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            desc = 1;
        }
        sendRedirect(request, response, location + "?desc=" + desc);
    }
    
}
