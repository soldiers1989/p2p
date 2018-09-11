package com.dimeng.p2p.escrow.huifu.cond;

public interface CorpRegisterCond {
	
	/**
	 * （可选），用户id
	 */
	public String usrId();
	/**
	 * （可选），真实名称
	 */
	public String usrName();
	/**
	 * （可选），组织机构代码 
	 */
	public String instuCode();
	/**
	 * （必须），营业执照编号
	 */
	public String busiCode();
	/**
	 * （可选），税务登记号
	 */
	public String taxCode();
	/**
	 * 是否担保企业
	 */
	public String GuarType();
}
