package com.dimeng.p2p.escrow.huifu.cond;

public abstract interface UnFreezeCond {
	/**
	 * 订单id
	 */
	public String ordId();
	/**
	 * 订单时间
	 */
	public String ordDate();
	/**
	 * 流水号
	 */
	public String trxId();
	/**
	 * 返回地址
	 */
	public String retUrl();
}
