/*
 * 文 件 名:  charge.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月18日
 */
package com.dimeng.p2p.pay.servlets.huifu;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S61.entities.T6119;
import com.dimeng.p2p.common.enums.FrontLogType;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeOrder;
import com.dimeng.p2p.escrow.huifu.service.ChargeManage;
import com.dimeng.p2p.escrow.huifu.service.HuifuChargeManage;
import com.dimeng.p2p.escrow.huifu.service.UserManage;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.DateParser;

/**
 * 
 * 充值servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class Charge extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        
        final BigDecimal amount = BigDecimalParser.parse(request.getParameter("amount"));
        ChargeManage manage = serviceSession.getService(ChargeManage.class);
        final ChargeOrder order = manage.addOrder(amount, PaymentInstitution.CHINAPNR.getInstitutionCode(), null);
        
        UserManage userManage = serviceSession.getService(UserManage.class);
        final T6119 t6119 = userManage.selectT6119();
        HuifuChargeManage hfManage = serviceSession.getService(HuifuChargeManage.class);
        String location = hfManage.createChargeUrI(new ChargeCond()
        {
            
            @Override
            public String usrCustId()
            {
                return t6119.F03;
            }
            
            @Override
            public String transAmt()
            {
                return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            
            @Override
            public String retUrl()
            {
                return null;
            }
            
            @Override
            public String ordId()
            {
                return String.valueOf(order.id);
            }
            
            @Override
            public String ordDate()
            {
                return DateParser.format(order.orderTime, "yyyyMMdd");
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
        
        logger.info("汇付 - 充值请求第三方参数：" + location);
        manage.writeFrontLog(FrontLogType.CZ.getName(), "前台充值");
        
        sendRedirect(request, response, location);
    }
    
    @Override
    protected boolean mustAuthenticated()
    {
        return true;
    }
}
