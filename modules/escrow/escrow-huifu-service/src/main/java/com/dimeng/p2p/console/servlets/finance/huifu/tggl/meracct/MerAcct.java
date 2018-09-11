package com.dimeng.p2p.console.servlets.finance.huifu.tggl.meracct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.entity.query.MerAcctQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.MerAcctQueryManage;

@Right(id = "P2P_C_FINANCE_HUIFUMERACCT", isMenu = true, name = "商户账户信息", moduleId = "P2P_C_HUIFU_MERINFO", order = 0)
public class MerAcct extends AbstractDzglServlet
{
    
    private static final long serialVersionUID = 1L;
    
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
        MerAcctQueryManage manage = serviceSession.getService(MerAcctQueryManage.class);
        MerAcctQueryEntity entity = manage.query();
        request.setAttribute("entity", entity);
        forward(request, response, getController().getViewURI(request, getClass()));
    }
    
}
