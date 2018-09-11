/*
 * 文 件 名:  BindCard.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  绑卡servlet
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月20日
 */
package com.dimeng.p2p.user.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.S61.entities.T6119;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;
import com.dimeng.p2p.escrow.huifu.service.UserManage;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.IntegerParser;

/**
 * 绑卡servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月20日]
 */
public class BindCard extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        BankCardManage manage = serviceSession.getService(BankCardManage.class);
        UserManage userManage = serviceSession.getService(UserManage.class);
        ConfigureProvider configurableProvider = getConfigureProvider();
        //查询用户在平台的银行卡数量
        int count = manage.getBankCardCount();
        //校验最多添加银行卡数量
        if (count >= IntegerParser.parse(configurableProvider.getProperty(SystemVariable.MAX_BANKCARD_COUNT)))
        {
            sendRedirect(request, response, configurableProvider.format(URLVariable.CARD_MANAGE));
        }
        
        final T6119 t6119 = userManage.selectT6119();
        //拼装参数请求第三方
        String location = manage.createBindCardUrI(t6119.F03);
        
        logger.info("汇付 - 绑定银行卡请求参数：" + location);
        
        sendRedirect(request, response, location);
    }
    
}
