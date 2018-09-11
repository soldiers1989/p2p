package com.dimeng.p2p.escrow.huifu.cond;

public interface PaymentCond {
	/**
	 * （必填），订单id
	 */
	public String ordId();
	/**
	 * （必填）， 订单时间，格式“20140630”
	 */
	public String ordDate();
	/**
	 * （必填）， 出账账户客户号
	 */
	public String outCustId();
	/**
	 * （必填）， 子订单id
	 */
	public String subOrdId();
	/**
	 * （必填）， 子订单时间
	 */
	public String subOrdDate();
	/**
	 * （必填）， 交易金额
	 */
	public String transAmt();
	/**
	 * （必填），手续费
	 */
	public String fee();
	/**
	 * （必填）， 入账账户客户号
	 */
	public String inCustId();
	
}
