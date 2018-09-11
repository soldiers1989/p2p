package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S62.enums.T6280_F10;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bid.CancleAutoBidEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 取消自动投标回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class CancleAutoBidRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 取消自动投标回调开始 ----------");
        
        BidManage manage = serviceSession.getService(BidManage.class);
        CancleAutoBidEntity entity = manage.cancleAutoBidRetDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            manage.updateAutoBidStatus(IntegerParser.parse(entity.merPriv), T6280_F10.TY);
            sendRedirect(request,
                response,
                getResourceProvider().getResource(ConfigureProvider.class).format(URLVariable.AUTO_BID_INDEX));
        }
        
    }
    
}
