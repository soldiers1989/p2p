package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.biddzgl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.escrow.huifu.cond.BidCancleCond;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidCancleReturn;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 投标撤销
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年12月7日]
 */
public class BidCancle extends AbstractHuifuServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------------- 投标撤销开始 --------------");
        BidManage bidManage = serviceSession.getService(BidManage.class);
        final int ordId = IntegerParser.parse(request.getParameter("ordId"));
        int freezeOrdId = IntegerParser.parse(request.getParameter("freezeOrdId"));
        BidCancleReturn bidReturn = bidManage.cancel(ordId);
        final int orderId = bidReturn.bidOrderIds;
        String experienceOrderIds = bidReturn.experOrderIds;
        // 标的撤销，投资金额返还
        if (orderId > 0)
        {
            Map<String, String> paraMap = new HashMap<String, String>();
            
            if (!StringHelper.isEmpty(experienceOrderIds))
            {
                paraMap.put("experOrderId", experienceOrderIds);
            }
            final T6501 freeze = bidManage.selectT6501(freezeOrdId);
            final T6504 t6504 = bidManage.selectT6504(ordId);
            //拼装投标撤销参数
            String location = bidManage.CreateBidCancleUri(new BidCancleCond()
            {
                @Override
                public String usrCustId()
                {
                    return String.valueOf(t6504.F02);
                }
                
                @Override
                public String transAmt()
                {
                    return String.valueOf(t6504.F04);
                }
                
                @Override
                public String retUrl()
                {
                    return null;
                }
                
                @Override
                public String ordId()
                {
                    return String.valueOf(ordId);
                }
                
                @Override
                public String ordDate()
                {
                    return DateParser.format(freeze.F05, "yyyyMMdd");
                }
                
                @Override
                public String isUnFreeze()
                {
                    return "Y";
                }
                
                @Override
                public String unFreezeOrdId()
                {
                    return String.valueOf(orderId);
                }
                
                @Override
                public String FreezeTrxId()
                {
                    return freeze.F10;
                }
            });
            
            logger.info("汇付 - 投资撤销订单请求参数：" + location);
            
            sendRedirect(request, response, location);
        }
        
    }
    
}
