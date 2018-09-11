package com.dimeng.p2p.console.servlets.account.vipmanage.qyxx.cwzk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S61.entities.T6163;
import com.dimeng.p2p.console.servlets.account.AbstractAccountServlet;
import com.dimeng.p2p.console.servlets.account.vipmanage.qyxx.QyList;
import com.dimeng.p2p.console.servlets.account.vipmanage.qyxx.lxxx.UpdateLxxx;
import com.dimeng.p2p.modules.account.console.service.QyManage;
import com.dimeng.p2p.modules.account.console.service.entity.Cwzk;
import com.dimeng.util.parser.IntegerParser;

@Right(id = "P2P_C_ACCOUNT_QYXX_UPDATE", name = "修改",moduleId="P2P_C_ACCOUNT_QYXX",order=3)
public class UpdateCwzk extends AbstractAccountServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void processGet(HttpServletRequest request,
			HttpServletResponse response, ServiceSession serviceSession)
			throws Throwable {
		QyManage manage = serviceSession.getService(QyManage.class);
		int id = IntegerParser.parse(request.getParameter("id"));
		T6163[] info = manage.getCwzk(id);
		request.setAttribute("info", info);
		forwardView(request, response, getClass());
	}

	@Override
	protected void processPost(HttpServletRequest request,
			HttpServletResponse response, ServiceSession serviceSession)
			throws Throwable {
		QyManage manage = serviceSession.getService(QyManage.class);
		int id = IntegerParser.parse(request.getParameter("id"));
		Cwzk entity = new Cwzk();
		entity.parse(request);
		manage.updateCwzk(entity);
		if("submit".equals(request.getParameter("entryType"))){
            sendRedirect(request, response, getController().getURI(request, QyList.class));
            return;
        }
		sendRedirect(request, response, getController()
				.getURI(request, UpdateLxxx.class)+"?id="+id);
	}

}
