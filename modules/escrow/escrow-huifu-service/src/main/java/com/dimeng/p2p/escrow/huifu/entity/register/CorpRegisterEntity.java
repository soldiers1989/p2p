package com.dimeng.p2p.escrow.huifu.entity.register;

import java.io.Serializable;

public class CorpRegisterEntity implements Serializable {

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
	 * 用户真实姓名
	 */
	public String usrName;
	/**
	 * 用户客户号
	 */
	public String usrCustId;
	/**
	 * 审核状态，I：初始，T：提交，P：审核中，R：审核拒绝，F：开户失败，K：开户中，Y：开户成功
	 */
	public String auditStat;
	/**
	 * 审核状态描述
	 */
	public String auditDesc;
	/**
	 * 扩展字段
	 */
	public String merPriv;
	/**
	 * 交易唯一标识
	 */
	public String trxId;
	/**
	 * 银行编号
	 */
	public String openBankId;
	/**
	 * 卡号
	 */
	public String cardId;

	/**
	 * 商户后台应答地址
	 */
	public String bgRetUrl;
	/**
	 * 校验值
	 */
	public String chkValue;
}
