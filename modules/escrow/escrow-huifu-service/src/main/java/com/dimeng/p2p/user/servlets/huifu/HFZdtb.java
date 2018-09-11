/*
 * 文 件 名:  HFzdtb.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月29日
 */
package com.dimeng.p2p.user.servlets.huifu;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.account.user.service.UserInfoManage;
import com.dimeng.p2p.escrow.huifu.cond.AutoBidCond;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.modules.bid.user.service.ZdtbManage;
import com.dimeng.p2p.modules.bid.user.service.entity.AutoBidSet;
import com.dimeng.p2p.modules.bid.user.service.query.AutoBidQuery;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 自动投标
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月29日]
 */
public class HFZdtb extends AbstractHuifuServlet
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2258438940746128554L;
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        ZdtbManage autoUtilFinacingManage = serviceSession.getService(ZdtbManage.class);
        UserInfoManage userInfoManage = serviceSession.getService(UserInfoManage.class);
        final BigDecimal timeMoney;
        T6110 userInfo = userInfoManage.getUserInfo(serviceSession.getSession().getAccountId());
        if (userInfo == null || T6110_F06.FZRR == userInfo.F06)
        {
            getController().prompt(request, response, PromptLevel.INFO, "企业用户不能设置自动投标计划");
            sendRedirect(request,
                response,
                getResourceProvider().getResource(ConfigureProvider.class).format(URLVariable.AUTO_BID_INDEX));
            return;
        }
        //查询用户当前是否已有 未开启的投标计划
        List<AutoBidSet> autoBidList = autoUtilFinacingManage.search();
        if (autoBidList == null)
        {
            timeMoney = BigDecimalParser.parse(request.getParameter("timeMoney")).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        else
        {
            timeMoney = autoBidList.get(0).timeMoney;
        }
        
        if (autoBidList == null)
        {
            autoUtilFinacingManage.save(new AutoBidQuery()
            {
                @Override
                public BigDecimal getTimeMoney()
                {
                    return StringHelper.isEmpty(request.getParameter("timeMoney")) ? BigDecimal.ZERO : new BigDecimal(
                        request.getParameter("timeMoney"));
                }
                
                @Override
                public BigDecimal getSaveMoney()
                {
                    return StringHelper.isEmpty(request.getParameter("saveMoney")) ? BigDecimal.ZERO : new BigDecimal(
                        request.getParameter("saveMoney"));
                }
                
                @Override
                public BigDecimal getRateStart()
                {
                    return new BigDecimal(request.getParameter("rateStart")).divide(new BigDecimal(100));
                }
                
                @Override
                public BigDecimal getRateEnd()
                {
                    return new BigDecimal(request.getParameter("rateEnd")).divide(new BigDecimal(100));
                }
                
                @Override
                public int getLevelStart()
                {
                    return IntegerParser.parse(request.getParameter("levelStart"));
                }
                
                @Override
                public int getLevelEnd()
                {
                    return IntegerParser.parse(request.getParameter("levelEnd"));
                }
                
                @Override
                public int getJkqxStart()
                {
                    return IntegerParser.parse(request.getParameter("jkqxStart"));
                }
                
                @Override
                public int getJkqxEnd()
                {
                    return IntegerParser.parse(request.getParameter("jkqxEnd"));
                }
                
                @Override
                public int mctbje()
                {
                    return StringHelper.isEmpty(request.getParameter("mctbje")) ? 1
                        : Integer.parseInt(request.getParameter("mctbje"));
                }
            });
        }
        
        BidManage bidManage = serviceSession.getService(BidManage.class);
        final String usrCustId = bidManage.getUserCustId();
        //拼装 自动投标计划请求参数
        String location = bidManage.createAutoBidUri(new AutoBidCond()
        {
            
            @Override
            public String usrCustId()
            {
                return usrCustId;
            }
            
            @Override
            public String transAmt()
            {
                return String.valueOf(timeMoney);
            }
            
            @Override
            public String tenderPlanType()
            {
                try
                {
                    return getResourceProvider().getResource(ConfigureProvider.class)
                        .format(HuifuVariable.ANTO_BIDING_PLAN_TYPE);
                }
                catch (Exception e)
                {
                    logger.error(e);
                }
                return null;
            }
            
            @Override
            public String retUrl()
            {
                try
                {
                    return getResourceProvider().getResource(ConfigureProvider.class)
                        .format(HuifuVariable.HF_RETURL_AUTOBID);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
            
            @Override
            public String merPriv()
            {
                return String.valueOf(serviceSession.getSession().getAccountId());
            }
        });
        
        logger.info("汇付 - 【自动投标计划】请求参数：" + location);
        
        sendRedirect(request, response, location);
    }
}
