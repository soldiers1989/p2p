package com.dimeng.p2p.escrow.huifu.entity.charge;

import java.io.Serializable;

public class ChargeEntity implements Serializable {

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
	 * 交易唯一标识
	 */
	public String trxId;
	/**
	 * 前台返回地址
	 */
	public String retUrl;
	/**
	 * 商户后台应答地址
	 */
	public String bgRetUrl;
	/**
	 * 扩展字段
	 */
	public String merPriv;
	/**
	 * 
	 */
	public String gateBusiId;
	/**
	 * 
	 */
	public String gateBankId;
	/**
	 * 校验值
	 */
	public String chkValue;
	/**
	 * 
	 */
	public String feeAmt;
	/**
	 * 
	 */
	public String feeCustId;
	/**
	 * 
	 */
	public String feeAcctId;
	/**
     * 银行卡号
     */
    public String cardId;
}
