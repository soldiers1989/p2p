package com.dimeng.p2p.escrow.huifu.service;

import java.util.Set;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawAmend;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawItem;

public abstract interface WithdrawQueryManage extends Service {
	/**
	 * 提现对账查询
	 * 
	 * @param cond
	 * @throws Throwable
	 */
	public abstract PagingResult<WithdrawItem> query(QueryCond cond)
			throws Throwable;
	/**
	 * 设置页号
	 * @param pageNum
	 */
	public abstract void setPageNum(int pageNum);
	
	/**
	 * 查询平台订单状态
	 * @param orderId
	 * @return
	 * @throws Throwable
	 */
	public abstract T6501_F03 getOrderStat(int orderId) throws Throwable;
	/**
	 * 更新订单状态
	 * @param orderId
	 * @throws Throwable
	 */
	public abstract void updateOrderStat(int orderId, T6501_F03 f03) throws Throwable;
	/**
	 * 提现修正信息 
	 * @param ids
	 * @throws Throwable
	 */
	public abstract WithdrawAmend[] withdrawInfo(Set<Integer> ids) throws Throwable;
}
