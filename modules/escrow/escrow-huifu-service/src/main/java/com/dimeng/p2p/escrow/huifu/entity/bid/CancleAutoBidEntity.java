package com.dimeng.p2p.escrow.huifu.entity.bid;

import java.io.Serializable;

public class CancleAutoBidEntity implements Serializable {

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
	 * 用户客户号
	 */
	public String usrCustId;
	/**
	 * 返回地址
	 */
	public String retUrl;
	/**
	 * 用户私有域
	 */
	public String merPriv;
	/**
	 * 校验值
	 */
	public String chkValue;
}
