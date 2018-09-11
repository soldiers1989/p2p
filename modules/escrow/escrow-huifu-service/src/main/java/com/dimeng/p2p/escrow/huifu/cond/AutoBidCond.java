package com.dimeng.p2p.escrow.huifu.cond;

public interface AutoBidCond {
	/**
	 * 用户商户号
	 */
	public String usrCustId();
	/**
	 * 计划类型
	 */
	public String tenderPlanType();
	/**
	 * 交易金额
	 */
	public String transAmt();
	/**
	 * 返回地址
	 */
	public String retUrl();
	/**
	 * 附加字段
	 */
	public String merPriv();
}
