/*
 * 文 件 名:  charge.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月18日
 */
package com.dimeng.p2p.console.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.modules.bid.console.service.AddBidManage;

/**
 * 标信息补录
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月6日]
 */
public class BidEnterAgain extends AbstractHuifuServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void processGet(HttpServletRequest request,
			HttpServletResponse response, ServiceSession serviceSession)
			throws Throwable {
		// TODO Auto-generated method stub
		this.processPost(request, response, serviceSession);
	}

	@Override
	protected void processPost(HttpServletRequest request,
			HttpServletResponse response, ServiceSession serviceSession)
			throws Throwable {
		String proId = request.getParameter("proId");
		AddBidManage addBidManage = serviceSession
				.getService(AddBidManage.class);
		String location = addBidManage.bidEnterAgain(proId);
		logger.info("汇付 - 标信息补录请求第三方参数：" + location);
		sendRedirect(request, response, location);
	}

}
