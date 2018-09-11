package com.dimeng.p2p.escrow.huifu.cond;

public abstract interface UserRegisterCond {
	/**
	 * （可选），前台返回地址，用户开户成功之后前台返回的地址
	 * 
	 * @return
	 */
	public abstract String retUrl();
	/**
	 * （可选），平台用户id
	 * @return
	 */
	public abstract String usrId();
	/**
	 * （可选），用户的真实姓名
	 * @return
	 */
	public abstract String usrName();
	/**
	 * （可选），用户身份证号
	 * @return
	 */
	public abstract String idNo();
	/**
	 * （可选），用户手机号
	 * @return
	 */
	public abstract String usrMp();
	/**
	 * （可选），用户邮箱
	 * @return
	 */
	public abstract String usrEmail();
}
