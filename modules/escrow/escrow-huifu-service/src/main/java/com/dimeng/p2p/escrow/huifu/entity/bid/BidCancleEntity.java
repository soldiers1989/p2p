package com.dimeng.p2p.escrow.huifu.entity.bid;

import java.io.Serializable;

public class BidCancleEntity implements Serializable {

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
	 * 订单id
	 */
	public String ordId;
	/**
	 * 订单时间
	 */
	public String ordDate;
	/**
	 * 交易金额
	 */
	public String transAmt;
	/**
	 * 用户客户号
	 */
	public String usrCustId;
	/**
	 * 前台返回地址
	 */
	public String retUrl;
	/**
	 * 商户后台应答地址
	 */
	public String bgRetUrl;
	/**
	 * 校验值
	 */
	public String chkValue;
	
}
