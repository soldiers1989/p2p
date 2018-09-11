package com.dimeng.p2p.escrow.huifu.cond;


public interface ChargeCond {
	
	/**
	 * （必填），用户账户号
	 */
	public String usrCustId();
	/**
	 * （必填），订单id
	 */
	public String ordId();
	/**
	 * （必填）， 订单时间，格式“20140630”
	 */
	public String ordDate();
	/**
	 * （可选），网关的细分业务类型，如 网关的细分业务类型，如 B2C B2C、B2B B2B、WH 等参见附录 三
	 */
	public String GateBusiId();
	/**
	 * （可选），开户银行代号
	 */
	public String OpenBankId();
	/**
	 * （可选），借贷记标记  D:借记，储蓄卡； C：贷记，信用卡
	 */
	public String dcFlag();
	/**
	 * （必填）， 交易金额
	 */
	public String transAmt();
	/**
	 * （可选），返回地址
	 */
	public String retUrl();
}
