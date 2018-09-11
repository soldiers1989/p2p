/*
 * 文 件 名:  OpenEscrowGuide.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月18日
 */
package com.dimeng.p2p.user.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.entities.T6119;
import com.dimeng.p2p.escrow.huifu.entity.register.OpenEscrowGuideEntity;
import com.dimeng.p2p.escrow.huifu.service.UserManage;
import com.dimeng.p2p.variables.defines.URLVariable;

/**
 * 
 * 托管引导页管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月19日]
 */
public class OpenEscrowGuide extends AbstractHuifuServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        UserManage manage = serviceSession.getService(UserManage.class);
        ConfigureProvider configureProvider = this.getResourceProvider().getResource(ConfigureProvider.class);
        /*
         * 防止已经注册第三方的用户通过浏览器直接输入地址进入引导页
         * 判断存在托管账号就重定向到用户中心页面
         */
        T6119 t6119 = manage.selectT6119();
        if (t6119 != null && StringUtils.isNotEmpty(t6119.F03))
        {
            sendRedirect(request, response, configureProvider.format(URLVariable.USER_INDEX));
            return;
        }
        OpenEscrowGuideEntity entity = manage.getOpenEscrowGuideEntity();
        request.setAttribute("openEscrowGuideEntity", entity);
        forwardView(request, response, getClass());
        
    }
    
}
