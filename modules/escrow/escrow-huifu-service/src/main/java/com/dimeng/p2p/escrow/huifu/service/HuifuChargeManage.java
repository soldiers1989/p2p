package com.dimeng.p2p.escrow.huifu.service;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeEntity;

public abstract interface HuifuChargeManage extends Service {
	/**
	 * 
	 * @param cond
	 * @return
	 * @throws Throwable
	 */
	public abstract String createChargeUrI(ChargeCond cond) throws Throwable;
	/**
	 * 解析充值返回参数
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public abstract ChargeEntity chrageRetDecode(HttpServletRequest request) throws Throwable;
}
