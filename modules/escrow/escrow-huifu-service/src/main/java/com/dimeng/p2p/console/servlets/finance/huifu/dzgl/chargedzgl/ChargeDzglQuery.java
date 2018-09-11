package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.chargedzgl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F11;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.ChargeQueryManage;
import com.dimeng.p2p.order.ChargeOrderExecutor;
import com.dimeng.util.parser.IntegerParser;

/**
 * 充值对账处理类
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUCHARGEQUERY", isMenu = true, name = "去对账", moduleId = "P2P_C_HUIFU_CZDZGL", order = 1)
public class ChargeDzglQuery extends AbstractDzglServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1906209440880270351L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        ChargeQueryManage chargeManage = serviceSession.getService(ChargeQueryManage.class);
        BidManage bidManage = serviceSession.getService(BidManage.class);
        final String ordId = request.getParameter("ordId");
        CzdzQueryEntity entity = chargeManage.query(new ChargeCond()
        {
            
            @Override
            public String usrCustId()
            {
                return null;
            }
            
            @Override
            public String transAmt()
            {
                return null;
            }
            
            @Override
            public String retUrl()
            {
                return null;
            }
            
            @Override
            public String ordId()
            {
                return ordId;
            }
            
            @Override
            public String ordDate()
            {
                return null;
            }
            
            @Override
            public String dcFlag()
            {
                return null;
            }
            
            @Override
            public String OpenBankId()
            {
                return null;
            }
            
            @Override
            public String GateBusiId()
            {
                return null;
            }
        });
        final T6501 t6501 = bidManage.selectT6501(Integer.parseInt(ordId));
        if (entity == null)
        {
            bidManage.updateOrderStatus(IntegerParser.parse(ordId));
            processRequest(request, response, "该订单尚未提交到第三方平台！请确认！");
            return;
        }
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode))
            {
                // 成功
                if ("S".equals(entity.TransStat))
                {
                    if (t6501.F03 != T6501_F03.CG && T6501_F11.F == t6501.F11)
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("feeAmt", entity.FeeAmt);
                        bidManage.updateT6501(IntegerParser.parse(entity.OrdId), T6501_F03.DQR);
                        ChargeOrderExecutor chargeOrderExecutor =
                            getResourceProvider().getResource(ChargeOrderExecutor.class);
                        chargeOrderExecutor.confirm(IntegerParser.parse(entity.OrdId), params);
                    }
                    processRequest(request, response, "OK");
                    return;
                }
                else
                {
                    processRequest(request, response, "对账失败！原因:交易状态失败！");
                    bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                    return;
                }
            }
            else
            {
                logger.info("对账失败！原因:" + entity.RespDesc + "！");
                processRequest(request, response, "对账失败！");
                bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
                return;
            }
        }
    }
}
