package com.dimeng.p2p.console.servlets.account.vipmanage.qyxx.jscl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.console.servlets.account.AbstractAccountServlet;
import com.dimeng.p2p.modules.account.console.service.QyManage;
import com.dimeng.p2p.modules.account.console.service.entity.XyrzTotal;
import com.dimeng.util.parser.IntegerParser;

@Right(id = "P2P_C_ACCOUNT_QYXX_VIEW", name = "查看", moduleId = "P2P_C_ACCOUNT_QYXX", order = 1)
public class ViewJscl extends AbstractAccountServlet
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        QyManage manage = serviceSession.getService(QyManage.class);
        int id = IntegerParser.parse(request.getParameter("id"));
        XyrzTotal xyrzTotal = manage.getXyrzTotal(id);
        request.setAttribute("xyrzTotal", xyrzTotal);
        request.setAttribute("info", manage.getJscl(id));
        forwardView(request, response, getClass());
    }
    
}
