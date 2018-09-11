package com.dimeng.p2p.escrow.huifu.entity.register;

import java.io.Serializable;

public class UserRegisterEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 消息类型
	 */
	public String cmdId;
	/**
	 * 应答返回码
	 */
	public String respCode;
	/**
	 * 应答描述
	 */
	public String respDesc;
	/**
	 * 商户客户号
	 */
	public String merCustId;
	/**
	 * 用户id
	 */
	public String usrId;
	/**
	 * 用户客户号
	 */
	public String usrCustId;
	/**
	 * 商户后台应答地址
	 */
	public String bgRetUrl;
	/**
	 * 交易唯一标识
	 */
	public String trxId;
	/**
	 * 页面返回地址
	 */
	public String retUrl;
	/**
	 * 扩展字段
	 */
	public String merPriv;
	/**
	 * 证件类型
	 */
	public String idType;
	/**
	 * 证件号
	 */
	public String idNo;
	/**
	 * 用户手机号
	 */
	public String usrMp;
	/**
	 * 用户邮箱
	 */
	public String usrEmail;
	/**
	 * 用户真实姓名
	 */
	public String usrName;
	/**
	 * 校验值
	 */
	public String chkValue;
}
