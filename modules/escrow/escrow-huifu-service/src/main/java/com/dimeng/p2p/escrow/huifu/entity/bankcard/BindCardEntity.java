package com.dimeng.p2p.escrow.huifu.entity.bankcard;

import java.io.Serializable;

public class BindCardEntity implements Serializable {
	
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
	 * 银行卡号
	 */
	public String openAcctId;
	/**
	 * 开户银行代号
	 */
	public String openBankId;
	/**
	 * 用户客户号
	 */
	public String usrCustId;
	/**
	 * 交易唯一标识
	 */
	public String trxId;
	/**
	 * 商户后台应答地址
	 */
	public String bgRetUrl;
	/**
	 * 校验值
	 */
	public String chkValue;
	/**
	 * 扩展字段
	 */
	public String merPriv;
	
}
