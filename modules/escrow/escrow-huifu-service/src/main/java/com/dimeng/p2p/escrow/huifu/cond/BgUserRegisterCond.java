package com.dimeng.p2p.escrow.huifu.cond;

public interface BgUserRegisterCond {
	/**
	 * （必须），平台用户id
	 * @return
	 */
	public abstract String usrId();
	/**
	 * （必须），用户的真实姓名
	 * @return
	 */
	public abstract String usrName();
	/**
	 * （必须），登录密码
	 * @return
	 */
	public abstract String loginPwd();
	
	/**
	 * （必须），交易密码 
	 * @return
	 */
	public abstract String transPwd();
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
	 * （可选），用户手机号
	 * @return
	 */
	public abstract String usrEmail();
}
