package com.dimeng.p2p.console.servlets.base.jjr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S51.entities.T5128;
import com.dimeng.p2p.console.servlets.base.AbstractBaseServlet;
import com.dimeng.p2p.modules.base.console.service.JjrManage;
import com.dimeng.util.parser.IntegerParser;

//@Right(id = "P2P_C_BASE_JJRLIST", isMenu = true, name = "节假日管理")
public class JjrList extends AbstractBaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void processGet(HttpServletRequest request,
            HttpServletResponse response, ServiceSession serviceSession)
            throws Throwable {
        processPost(request, response, serviceSession);
    }

    @Override
    protected void processPost(final HttpServletRequest request,
            final HttpServletResponse response,
            final ServiceSession serviceSession) throws Throwable {
        JjrManage jjrManage = serviceSession.getService(JjrManage.class);
        PagingResult<T5128> result = jjrManage.srarch(new Paging() {

            @Override
            public int getSize() {
                return 10;
            }

            @Override
            public int getCurrentPage() {
                return IntegerParser.parse(request.getParameter(PAGING_CURRENT));
            }
        });
        request.setAttribute("result", result);
        forwardView(request, response, getClass());
    }
}
