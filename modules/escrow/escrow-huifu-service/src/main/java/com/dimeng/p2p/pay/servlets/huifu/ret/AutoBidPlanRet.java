package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S62.enums.T6280_F10;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bid.AutoBidEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 自动投标计划回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月10日]
 */
public class AutoBidPlanRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 自动投标计划回调  ----------");
        BidManage manage = serviceSession.getService(BidManage.class);
        AutoBidEntity entity = manage.autoBidRetDecoder(request);
        if (entity != null
            && (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode) || HuiFuConstants.Common.BID_SUCCESS.equals(entity.respCode)))
        {
            manage.updateAutoBidStatus(IntegerParser.parse(entity.merPriv), T6280_F10.QY);
            sendRedirect(request,
                response,
                getResourceProvider().getResource(ConfigureProvider.class).format(URLVariable.AUTO_BID_INDEX));
        }
    }
}
