package com.dimeng.p2p.escrow.huifu.cond;

public interface BgBindCardCond {
	/**
	 * （必填），用户客户号 
	 */
	public String usrCustId();
	/**
	 * （必填），银行卡号
	 */
	public String openAcctId();
	/**
	 * （必须），开户银行代号
	 */
	public String openBankId();
	/**
	 * （必须），开户银行所在省份
	 */
	public String openProvId();
	/**
	 * （必须），开户银行所在地区
	 */
	public String openAreaId();
	/**
	 * （选填），取现银行支行名称
	 */
	public String openBranchName();
	/**
	 * （必须），是否为默认取现卡 Y/N
	 */
	public String isDefault();
}
