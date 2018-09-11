/*
 * 文 件 名:  HuifuBuy.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月6日
 */
package com.dimeng.p2p.pay.servlets.huifu;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S65.entities.T6555;
import com.dimeng.p2p.escrow.huifu.cond.UsrAcctPayCond;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.MallChangeExecutor;
import com.dimeng.p2p.repeater.score.MallChangeManage;
import com.dimeng.p2p.repeater.score.entity.OrderGoods;
import com.dimeng.util.StringHelper;

/**
 * 商品购买入口servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月6日]
 */
public class HuifuBuy extends AbstractHuifuServlet
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
    
    @SuppressWarnings({"unchecked", "static-access"})
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        response.setContentType("text/html;charset=utf-8");
        MallChangeManage manage = serviceSession.getService(MallChangeManage.class);
        
        String type = request.getParameter("payType");
        String jsonStr = request.getParameter("goodsList");
        String address = request.getParameter("addressId");
        int addressId = StringHelper.isEmpty(address) ? 0 : Integer.parseInt(address);
        final ConfigureProvider configureProvider = getResourceProvider().getResource(ConfigureProvider.class);
        
        JSONArray array = JSONArray.fromObject(jsonStr);
        List<OrderGoods> orders = (List<OrderGoods>)array.toCollection(array, OrderGoods.class);
        if ("score".equals(type))
        {
            manage.toChangeByScore(orders, type, addressId);
            PrintWriter out = response.getWriter();
            String msg = "score".equals(type) ? "兑换成功！" : "购买成功！";
            out.write("[{result:1,msg:'" + msg + "'}]");
            out.close();
        }
        else
        {
            MallChangeExecutor executor = getResourceProvider().getResource(MallChangeExecutor.class);
            final int orderId = manage.toChangeByBalance(orders, type, addressId);
            executor.submit(orderId, null);
            HFUsrAcctPayManage usrAcctPayManage = serviceSession.getService(HFUsrAcctPayManage.class);
            //查询当前用户第三方客户号
            final String usrCustId = usrAcctPayManage.getUserCustId(serviceSession.getSession().getAccountId());
            //查询商品购买订单
            final T6555 t6555 = usrAcctPayManage.selectT6555(orderId);
            //组装请求参数
            String location = usrAcctPayManage.createUsrAcctPayUrI(new UsrAcctPayCond()
            {
                
                @Override
                public String usrCustId()
                {
                    return usrCustId;
                }
                
                @Override
                public String transAmt()
                {
                    return t6555.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                }
                
                @Override
                public String ordId()
                {
                    return String.valueOf(t6555.F01);
                }
                
                @Override
                public String merCustId()
                {
                    return null;
                }
                
                @Override
                public String inAcctType()
                {
                    return null;
                }
                
                @Override
                public String inAcctId()
                {
                    return null;
                }
                
                @Override
                public String retUrl()
                {
                    try
                    {
                        return configureProvider.format(HuifuVariable.HF_BUY_TG_RET);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    return null;
                }
                
                @Override
                public String bgRetUrl()
                {
                    try
                    {
                        return configureProvider.format(HuifuVariable.HF_BUY_RET);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            
            logger.info("汇付 - 商品购买请求参数：" + location);
            
            sendRedirect(request, response, location);
        }
        
    }
    
    @SuppressWarnings({"unchecked", "static-access"})
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        getResourceProvider().log(throwable);
        String retUrl = null;
        String type = request.getParameter("payType");
        if (!"score".equals(type))
        {
            String source = request.getParameter("source");
            String jsonStr = request.getParameter("goodsList");
            JSONArray array = JSONArray.fromObject(jsonStr);
            List<OrderGoods> orders = (List<OrderGoods>)array.toCollection(array, OrderGoods.class);
            if (StringHelper.isEmpty(source) && StringHelper.isEmpty(throwable.getMessage()))
            {
                retUrl = "/mall/ptscXq/" + orders.get(0).getGoodsId() + ".html";
            }
            else if (!StringHelper.isEmpty(throwable.getMessage()))
            {
                retUrl = "/mall/mallIndex.htm";
            }
            else
            {
                retUrl = "/mall/carList.htm";
            }
        }
        PrintWriter out = response.getWriter();
        if (throwable instanceof ParameterException || throwable instanceof SQLException)
        {
            if (!StringHelper.isEmpty(throwable.getMessage()))
            {
                getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            }
            else
            {
                getController().prompt(request, response, PromptLevel.ERROR, "系统繁忙，请您稍后再试！");
            }
            
            if (!"score".equals(type))
            {
                sendRedirect(request, response, retUrl);
            }
            else
            {
                out.write("[{result:0,msg:'" + throwable.getMessage() + "'}]");
            }
        }
        else if (throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            if (!"score".equals(type))
            {
                sendRedirect(request, response, retUrl);
            }
            else
            {
                out.write("[{result:0,msg:'" + throwable.getMessage() + "'}]");
            }
        }
        else
        {
            super.onThrowable(request, response, throwable);
            if (!"score".equals(type))
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else
            {
                out.write("[{result:0,msg:'" + throwable.getMessage() + "'}]");
            }
        }
    }
}
