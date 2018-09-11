package com.dimeng.p2p.escrow.huifu.cond;

public interface LoansCond {
	/**
	 * （必填），订单id
	 */
	public String ordId();
	/**
	 * （必填），订单时间
	 */
	public String ordDate();
	/**
	 * （必填），出账账户客户号
	 */
	public String outCustId();
	/**
	 * （必填），交易金额
	 */
	public String transAmt();
	/**
	 * （必填），交易手续费
	 */
	public String fee();
	/**
	 * （必填），子订单id
	 */
	public String subOrdId();
	/**
	 * （必填），子订单时间
	 */
	public String subOrdDate();
	/**
	 * （必填），入账客户号
	 */
	public String inCustId();
	/**
	 * （必填），是否取现到银行卡
	 */
	public String isDefault();
}
