package com.dimeng.p2p.escrow.huifu.entity.freeze;

import java.io.Serializable;

public class FreezeEntity implements Serializable {

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
	 * 用户商户号
	 */
	public String usrCustId;
	/**
	 * 子账户类型
	 */
	public String subAcctType;
	/**
	 * 子账号
	 */
	public String subAcctId;
	/**
	 * 子订单
	 */
	public String ordId;
	/**
	 * 子订单时间
	 */
	public String ordDate;
	/**
	 * 交易金额
	 */
	public String transAmt;
	/**
	 * 前台返回地址
	 */
	public String retUrl;
	/**
	 * 流水号
	 */
	public String trxId;
	/**
	 * 校验值
	 */
	public String chkValue;
	/**
	 * 后台返回地址
	 */
	public String bgRetUrl;

}
