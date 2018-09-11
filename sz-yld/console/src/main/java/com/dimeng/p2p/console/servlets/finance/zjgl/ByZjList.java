package com.dimeng.p2p.console.servlets.finance.zjgl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.common.enums.FundAccountType;
import com.dimeng.p2p.console.servlets.finance.AbstractFinanceServlet;
import com.dimeng.p2p.modules.account.console.service.FundsManage;
import com.dimeng.p2p.modules.account.console.service.entity.FundsView;
import com.dimeng.p2p.modules.account.console.service.query.FundsQuery;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;

/*@Right(id = "P2P_C_FINANCE_BYZJGLLIST", isMenu = true, name = "备用资金管理")*/
public class ByZjList extends AbstractFinanceServlet {
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
		FundsManage fundsManage = serviceSession.getService(FundsManage.class);

		FundAccountType type = EnumParser.parse(FundAccountType.class,
				request.getParameter("fundAccountType"));
		PagingResult<FundsView> fundsViewList = fundsManage.bYSearch(
				new FundsQuery() {

					@Override
					public String getLoginName() {
						return request.getParameter("loginName");
					}

					@Override
					public String getUserName() {
						return request.getParameter("userName");
					}

					@Override
					public FundAccountType getFundAccountType() {
						FundAccountType fundAccountType = EnumParser.parse(
								FundAccountType.class,
								request.getParameter("fundAccountType"));
						return fundAccountType;
					}

				}, new Paging() {

					@Override
					public int getSize() {
						return 10;

					}

					@Override
					public int getCurrentPage() {
						return IntegerParser.parse(request
								.getParameter(PAGING_CURRENT));

					}
				});
		if (type == null) {
			type = FundAccountType.GR;
		}
		request.setAttribute("searchType", type.getName());
		request.setAttribute("fundsViewList", fundsViewList);
		forwardView(request, response, getClass());

	}

}
