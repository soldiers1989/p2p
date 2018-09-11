/*
 * 文 件 名:  HuifuPayBadClaim.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.user.servlets.huifu;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S62.entities.T6265;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.common.ResourceProviderUtil;
import com.dimeng.p2p.common.entities.Dzxy;
import com.dimeng.p2p.common.enums.FrontLogType;
import com.dimeng.p2p.order.BadClaimAdvanceExecutor;
import com.dimeng.p2p.repeater.claim.SubscribeBadClaimManage;
import com.dimeng.p2p.service.ContractManage;
import com.dimeng.p2p.user.servlets.thread.BadClaimContractPreservationThread;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 不良债权购买入口servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public class HuifuPayBadClaim extends AbstractHuifuServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        if (!FormToken.verify(serviceSession.getSession(), request.getParameter(FormToken.parameterName())))
        {
            throw new LogicalException("请不要重复提交请求！");
        }
        //债权ID
        int blzqId = IntegerParser.parse(request.getParameter("blzqId"));
        
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        SubscribeBadClaimManage service = serviceSession.getService(SubscribeBadClaimManage.class);
        
        BadClaimAdvanceExecutor badClaimExecutor = getResourceProvider().getResource(BadClaimAdvanceExecutor.class);
        Boolean tg = BooleanParser.parseObject(configureProvider.getProperty(SystemVariable.SFZJTG));
        //添加不良债权转让订单
        List<Integer> orderIds = service.addOrder(blzqId);
        //债权价值
        BigDecimal creditPrice = service.getCreditPrice(blzqId);
        //逾期天数
        int overdueDays = service.getOverdueDays(blzqId);
        
        List<String> list = new ArrayList<String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("blzqId", String.valueOf(blzqId));
        params.put("creditPrice", String.valueOf(creditPrice));
        params.put("overdueDays", String.valueOf(overdueDays));
        params.put("orderIdNum", String.valueOf(orderIds.size()));
        
        if (orderIds != null && orderIds.size() > 0)
        {
            logger.info("不良债权购买的订单数量：" + orderIds.size());
            
            for (Integer orderId : orderIds)
            {
                
                logger.info("不良债权购买的订单号：" + orderId);
                
                badClaimExecutor.submit(orderId, params);
                
                if (!tg)
                {
                    badClaimExecutor.confirm(orderId, params);
                }
                list.add(params.get("result"));
            }
        }
        
        String pageRetUrl = configureProvider.format(URLVariable.USER_BLZQZR).concat("?type=2");
        
        String noticeMessage = "";
        if (list.contains("success") && !list.contains("fail"))
        {
            noticeMessage = "恭喜您，不良债权购买成功！";
            saveAdvanceContract(request, serviceSession, blzqId);
        }
        else if (list.contains("fail") && list.contains("success"))
        {
            noticeMessage = "不良债权购买部分成功，可重新发起购买！";
        }
        else
        {
            noticeMessage = "很抱歉，不良债权购买失败！";
        }
        
        service.writeFrontLog(FrontLogType.GMBLZQ.getName(), "购买不良债权");
        
        createNoticeMessagePage(pageRetUrl, noticeMessage, response);
    }
    
    /**
     * 不良债权转让生成合同保全
     * @param request
     * @param configureProvider
     * @param serviceSession
     * @param id
     * @throws Throwable 
     */
    public void saveAdvanceContract(HttpServletRequest request, ServiceSession serviceSession, int id)
        throws Throwable
    {
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        //生成不良债权转让合同PDF并保全
        if (Boolean.parseBoolean(configureProvider.getProperty(SystemVariable.IS_SAVE_ADVANCE_CONTRACT)))
        {
            int blzqId = IntegerParser.parse(id);
            ContractManage contractManage = serviceSession.getService(ContractManage.class);
            T6265 t6265 = contractManage.selectT6265(blzqId);
            if (t6265 != null)
            {
                Dzxy dzxy = contractManage.getBlzqzr(t6265.F01, 0, "");
                if (dzxy != null)
                {
                    int blzqzrId = t6265.F01;
                    //执行合同保全线程
                    BadClaimContractPreservationThread bccpThread = new BadClaimContractPreservationThread(blzqzrId);
                    new Thread(bccpThread).start();
                }
            }
        }
    }
    
    protected String getURL(int loanId)
        throws IOException
    {
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        StringBuilder url = new StringBuilder(configureProvider.format(URLVariable.FINANCING_SBTZ_XQ));
        return url.toString();
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        ResourceProvider resourceProvider = getResourceProvider();
        final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        getResourceProvider().log(throwable);
        if (throwable instanceof SQLException)
        {
            getController().prompt(request, response, PromptLevel.ERROR, "系统繁忙，请您稍后再试");
            sendRedirect(request, response, configureProvider.format(URLVariable.USER_BLZQZR));
        }
        else if (throwable instanceof ParameterException || throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, configureProvider.format(URLVariable.USER_BLZQZR));
        }
        else
        {
            super.onThrowable(request, response, throwable);
        }
    }
    
}
