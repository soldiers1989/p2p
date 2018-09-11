package com.dimeng.p2p.escrow.huifu.service;

import java.util.Set;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidItem;
import com.dimeng.p2p.escrow.huifu.entity.loans.LoansAmend;
import com.dimeng.p2p.escrow.huifu.entity.payment.RepaymentAmend;

public abstract interface BidQueryManage extends Service {
	
	/**
	 * 投资对账查询
	 * @param cond
	 * @throws Throwable
	 */
	public abstract PagingResult<BidItem> query(QueryCond cond, String queryTransType) throws Throwable;
	/**
	 * 设置页号
	 * @param pageNum
	 */
	public abstract void setPageNum(int pageNum);
	/**
	 * 查询订单状态
	 * @param orderId
	 * @return
	 * @throws Throwable
	 */
	public T6501_F03 getOrderStat(int orderId) throws Throwable;
	/**
	 * 更新订单状态
	 * @param orderId
	 * @param f03
	 * @throws Throwable
	 */
	public void updateOrderStat(int orderId, T6501_F03 f03) throws Throwable;
	/**
	 * 获取放款修正订单列表
	 * @param ids
	 * @return
	 * @throws Throwable
	 */
	public LoansAmend[] loansAmends(Set<Integer> ids) throws Throwable;
	/**
	 * 获取还款修正订单列表
	 * @param ids
	 * @return
	 * @throws Throwable
	 */
	public RepaymentAmend[] repaymentAmends(Set<Integer> ids) throws Throwable;
}