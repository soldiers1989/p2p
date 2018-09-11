package com.dimeng.p2p.escrow.huifu.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.escrow.huifu.cond.UnFreezeCond;
import com.dimeng.p2p.escrow.huifu.entity.freeze.UnFreezeEntity;

public abstract interface UnFreezeManage extends Service {
	/**
	 * 订单返回参数解析
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public abstract UnFreezeEntity unFreezeReturnDecoder(
			HttpServletRequest request) throws Throwable;
	/**
	 * 发送解冻
	 * @param cond
	 * @throws Throwable
	 */
	public abstract UnFreezeEntity sendUnFreeze(UnFreezeCond cond) throws Throwable;
	/**
	 * 获取冻结订单流水
	 * @param ordId 解冻订单id
	 * @return
	 * @throws Throwable
	 */
	public abstract String getTrxId(int ordId) throws Throwable;
	/**
	 * 新增解冻订单
	 * @param ordId 解冻订单id
	 * @return
	 * @throws Throwable
	 */
	public abstract T6501 addUnfreezeOrder(int ordId) throws Throwable;
	/**
	 * 更新订单
	 * @param trxId
	 * @param orderId
	 * @throws Throwable
	 */
	public void updateTrxId(String trxId, int orderId) throws Throwable;
	/**
	 * 生成解冻订单
	 * @param ordId
	 * @param amount
	 * @return
	 * @throws Throwable
	 */
	public abstract T6501 addUnfreezeOrder(int ordId, BigDecimal amount) throws Throwable;
	/**
	 * 更新订单状态为失败
	 * @param ordId
	 * @throws Throwable
	 */
	public abstract void updateOrderFailure(int ordId, String trxId) throws Throwable;
}
