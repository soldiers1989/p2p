/*
 * 文 件 名:  PlatformCharge.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年12月08日
 */
package com.dimeng.p2p.console.servlets.huifu;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.common.ResourceProviderUtil;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeOrder;
import com.dimeng.p2p.escrow.huifu.service.ChargeManage;
import com.dimeng.p2p.escrow.huifu.service.HuifuChargeManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.DateParser;

/**
 * 【用于测试环境商户充值】
 * 平台充值servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年12月08日]
 */
public class PlatformCharge extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        
        final BigDecimal amount = BigDecimalParser.parse("100000000.00");
        ChargeManage manage = serviceSession.getService(ChargeManage.class);
        final ChargeOrder order = manage.addOrder(amount, PaymentInstitution.CHINAPNR.getInstitutionCode(), "PT");
        
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        
        HuifuChargeManage hfManage = serviceSession.getService(HuifuChargeManage.class);
        String location = hfManage.createChargeUrI(new ChargeCond()
        {
            @Override
            public String usrCustId()
            {
                return configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
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
        
        logger.info("汇付 - 平台充值请求第三方参数：" + location);
        
        sendRedirect(request, response, location);
    }
    
    @Override
    protected boolean mustAuthenticated()
    {
        return true;
    }
}
