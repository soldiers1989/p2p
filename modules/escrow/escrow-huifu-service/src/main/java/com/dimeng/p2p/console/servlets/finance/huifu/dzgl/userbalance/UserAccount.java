package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.userbalance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.entity.query.UserAcctQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 用户余额查询
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUUSERACCOUNT", isMenu = true, name = "余额查询", moduleId = "P2P_C_HUIFU_USERBALANCEGL", order = 1)
public class UserAccount extends AbstractDzglServlet
{
    
    private static final long serialVersionUID = -8763942853365053754L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        this.processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        int id = IntegerParser.parse(request.getParameter("id"));
        UserAcctQueryManage manage = serviceSession.getService(UserAcctQueryManage.class);
        UserAcctQueryEntity entity = manage.accountInfo(id);
        request.setAttribute("entity", entity);
        request.setAttribute("isZrr", request.getParameter("isZrr"));
        forward(request, response, getController().getViewURI(request, getClass()));
    }
}
