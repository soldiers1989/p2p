package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.service.AccountPayManage;

public class AcctPayRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        AccountPayManage manage = serviceSession.getService(AccountPayManage.class);
        int ordId = manage.retDecoder(request);
        printMark(response, String.valueOf(ordId));
    }
    
}
