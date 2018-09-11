package com.dimeng.p2p.escrow.huifu.cond;

public interface CancleAutoBidCond {
	/**
	 * 用户客户号
	 */
	public String usrCustId();
	/**
	 * 返回地址
	 */
	public String retUrl();
	/**
	 * 扩展字段 
	 */
	public String merPriv();
}
