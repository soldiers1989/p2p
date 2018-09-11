/*
 * 文 件 名:  UserRegister.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月18日
 */
package com.dimeng.p2p.user.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6110_F10;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.CorpRegisterCond;
import com.dimeng.p2p.escrow.huifu.cond.UserRegisterCond;
import com.dimeng.p2p.escrow.huifu.service.UserManage;
import com.dimeng.util.StringHelper;

/**
 * 用户注册servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class UserRegister extends AbstractHuifuServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("汇付托管注册开始——IP:" + request.getRemoteAddr());
        response.setContentType("text/html; charset=utf-8");
        ConfigureProvider configureProvider = this.getResourceProvider().getResource(ConfigureProvider.class);
        UserManage manage = serviceSession.getService(UserManage.class);
        final T6110 t6110 = manage.selectT6110();
        String url = null;
        if (t6110.F06 == T6110_F06.FZRR)
        {
            //非自然人请求参数拼装
            final T6161 t6161 = manage.selectT6161();
            url = corpRegister(configureProvider, t6161, manage, t6110);
        }
        else
        {
            //个人用户请求参数拼装
            url = userRegister(configureProvider, manage, t6110, request);
        }
        if (StringHelper.isEmpty(url))
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        
        logger.info("汇付 - 开户请求参数：" + url);
        
        sendRedirect(request, response, url);
    }
    
    /**
     * 个人用户开户请求参数拼装
     * <功能详细描述>
     * @param manage
     * @param t6110
     * @param t
     * @param request
     * @return
     * @throws Throwable
     */
    protected String userRegister(final ConfigureProvider configureProvider, UserManage manage, final T6110 t6110,
        final HttpServletRequest request)
        throws Throwable
    {
        return manage.createRegisterUri(new UserRegisterCond()
        {
            
            @Override
            public String usrName()
            {
                return null;
                //return request.getParameter("realName");
            }
            
            @Override
            public String usrMp()
            {
                return request.getParameter("mobile");
            }
            
            @Override
            public String usrId()
            {
                return HuiFuConstants.UserCode.USER_CODE.concat(String.valueOf(t6110.F01));
            }
            
            @Override
            public String usrEmail()
            {
                return t6110 == null ? null : t6110.F05;
            }
            
            @Override
            public String retUrl()
            {
                return null;
            }
            
            @Override
            public String idNo()
            {
                return request.getParameter("identificationNo");
            }
        });
    }
    
    /**
     * 企业开户请求参数拼装
     * <功能详细描述>
     * @param t6161
     * @param manage
     * @param t6110
     * @param t
     * @return
     * @throws Throwable
     */
    protected String corpRegister(ConfigureProvider configureProvider, final T6161 t6161, UserManage manage,
        final T6110 t6110)
        throws Throwable
    {
        return manage.createCorpRegisterUri(new CorpRegisterCond()
        {
            
            @Override
            public String usrName()
            {
                return null;
            }
            
            @Override
            public String usrId()
            {
                return HuiFuConstants.UserCode.USER_CODE.concat(String.valueOf(t6110.F01));
            }
            
            @Override
            public String taxCode()
            {
                if ("N".equals(t6161.F20))
                {
                    return t6161.F05;
                }
                return "111111111111111111111111111111";//三证合一填满数字1
            }
            
            @Override
            public String instuCode()
            {
                if ("N".equals(t6161.F20))
                {
                    return t6161.F06;
                }
                return "111111111";//三证合一填满数字1
            }
            
            @Override
            public String busiCode()
            {
                if ("N".equals(t6161.F20))
                {
                    return t6161.F03;
                }
                return t6161.F19;
            }
            
            @Override
            public String GuarType()
            {
                if (t6110.F10 == T6110_F10.S)
                {
                    return "Y";
                }
                return null;
            }
        });
    }
    
}
