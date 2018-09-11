package com.dimeng.p2p.escrow.huifu.cond;

import java.math.BigDecimal;

public interface TransferCond {
	
	/**
	 * （必填），订单id
	 */
	public String ordId();
	/**
	 * （必填），出账客户号
	 */
	public String outCustId();
	/**
	 * （必填），出账账户id
	 */
	public String outAcctId();
	/**
	 * （必填），交易金额
	 */
	public BigDecimal transAmt();
	
	/**
	 * （必填），进账客户号
	 */
	public String inCustId();
	/**
	 * （选填），进账账号
	 */
	public String inAcctId();
	/**
	 * （选填），前台返回地址
	 */
	public String retUrl();
	
}
