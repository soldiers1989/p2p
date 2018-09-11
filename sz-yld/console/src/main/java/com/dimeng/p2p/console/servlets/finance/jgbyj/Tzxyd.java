package com.dimeng.p2p.console.servlets.finance.jgbyj;

import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.modules.finance.console.service.JgbyjglManage;
import com.dimeng.p2p.console.servlets.finance.AbstractFinanceServlet;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.IntegerParser;

@Right(id = "P2P_C_FINANCE_TZXYD", name = "机构风险备用金调整信用额度")
public class Tzxyd extends AbstractFinanceServlet {
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
		JgbyjglManage jgbyjglManage = serviceSession
				.getService(JgbyjglManage.class);
		int id = IntegerParser.parse(request.getParameter("id"));
		BigDecimal ammount = BigDecimalParser.parse(request
				.getParameter("ammount"));
		String message = "";
		try {
			jgbyjglManage.setJgxyed(id, ammount);
		} catch (Throwable t) {
			if (t instanceof ParameterException
					|| t instanceof LogicalException) {
				message = t.getMessage();
			} else {
				super.onThrowable(request, response, t);
				return;
			}
		}
		StringBuffer sb = new StringBuffer(getController().getURI(request,
				CreditRecord.class));
		sb.append("?id=" + id);
		if (!StringHelper.isEmpty(message)) {
			sb.append("&message=" + URLEncoder.encode(message, "UTF-8"));
		}
		sendRedirect(request, response, sb.toString());
	}
}
