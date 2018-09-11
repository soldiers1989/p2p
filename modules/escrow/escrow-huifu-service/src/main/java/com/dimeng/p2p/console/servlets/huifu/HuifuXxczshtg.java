/*
 * 文 件 名:  HuifuXxczshtg.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.console.servlets.huifu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S71.entities.T7150;
import com.dimeng.p2p.S71.enums.T7150_F05;
import com.dimeng.p2p.escrow.huifu.executor.HFOfflineChargeOrdExecutor;
import com.dimeng.p2p.escrow.huifu.service.HFOfflineChargeManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.IntegerParser;

/**
 * 线下充值审核通过入口servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
@Right(id = "P2P_C_FINANCE_XXCZSH", name = "审核通过", moduleId = "P2P_C_FINANCE_ZJGL_XXCZGL", order = 2)
public class HuifuXxczshtg extends AbstractHuifuServlet
{
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
        HFOfflineChargeManage chargeManage = serviceSession.getService(HFOfflineChargeManage.class);
        int orderId = chargeManage.checkCharge(IntegerParser.parse(request.getParameter("id")), true);
        Map<String, String> params = new HashMap<String, String>();
        
        HFOfflineChargeOrdExecutor executor = getResourceProvider().getResource(HFOfflineChargeOrdExecutor.class);
        executor.submit(orderId, params);
        
        if ("success".equals(params.get("result")))
        {
            logger.info(String.format("------------> 线下充值成功，订单号%s:", orderId));
        }
        else
        {
            getController().prompt(request, response, PromptLevel.WARRING, "线下充值失败!");
        }
        //后台异步回调修改数据有时慢于前台页面跳转， 导致的前台展示的数据不是成功的，通过线程延迟一两秒跳转页面，出现的几率小很多
        T7150 t7150 = chargeManage.selectT7150(IntegerParser.parse(request.getParameter("id")));
        if (t7150.F05 != T7150_F05.YCZ)
        {
            Thread.sleep(1000);
        }
        sendRedirect(request, response, getConfigureProvider().format(URLVariable.XXCZGLLIST_URL));
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        if (throwable instanceof LogicalException || throwable instanceof ParameterException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, getConfigureProvider().format(URLVariable.XXCZGLLIST_URL));
        }
        else
        {
            super.onThrowable(request, response, throwable);
        }
    }
}
