package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 投标前台回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class BidTgRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 主动投标前台回调开始 ----------");
        BidManage manage = serviceSession.getService(BidManage.class);
        BidEntity entity = manage.bidReturnDecoder(request);
        int orderId = Integer.parseInt(request.getParameter("OrdId"));
        T6504 t6504 = manage.selectT6504(orderId);
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode)
                || HuiFuConstants.Common.BID_SUCCESS.equals(entity.respCode))
            {
                try
                {
                    //进行标验证
                    manage.checkBidInfo(IntegerParser.parse(entity.ordId));
                    getController().prompt(request, response, PromptLevel.INFO, "订单处理中！");
                }
                catch (Exception e)
                {
                    logger.error("主动投标前台回调异常：" + e);
                    getController().prompt(request, response, PromptLevel.WARRING, e.getMessage());
                }
            }
            else
            {
                getController().prompt(request, response, PromptLevel.WARRING, entity.respDesc);
            }
        }
        else
        {
            getController().prompt(request, response, PromptLevel.WARRING, "很抱歉，投资失败！原因：验签失败！");
        }
        
        sendRedirect(request, response, getURL(t6504.F03));
    }
    
    protected String getURL(int loanId)
        throws IOException
    {
        ResourceProvider resourceProvider = getResourceProvider();
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        StringBuilder url = new StringBuilder(configureProvider.format(URLVariable.FINANCING_SBTZ_XQ));
        url.append(Integer.toString(loanId)).append(resourceProvider.getSystemDefine().getRewriter().getViewSuffix());
        return url.toString();
    }
    
}
