package com.dimeng.p2p.escrow.huifu.cond;

public interface FreezeCond {
	
	/**
	 * 用户商户号
	 */
	public String usrCustId();
	/**
	 * 子账户类型
	 */
	public String subAcctType();
	/**
	 * 子账户
	 */
	public String subAcctId();
	/**
	 * 订单id
	 */
	public String ordId();
	/**
	 * 订单时间
	 */
	public String ordDate();
	/**
	 * 交易金额
	 */
	public String transAmt();
	/**
	 * 前台返回地址
	 */
	public String retUrl();
	/**
	 * 后台返回地址
	 */
	public String bgRetUrl();
	
}
