package com.dimeng.p2p.escrow.huifu.entity.withdraw;

import java.io.Serializable;

public class WithdrawEntity implements Serializable {

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
	 * 用户客户号
	 */
	public String usrCustId;
	/**
	 * 交易金额
	 */
	public String transAmt;
	/**
	 * 银行卡号
	 */
	public String openAcctId;
	/**
	 * 银行代号
	 */
	public String openBankId;
	/**
	 * 手续费金额
	 */
	public String feeAmt;
	/**
	 * 手续费扣款商户号
	 */
	public String feeCustId;
	/**
	 * 手续费扣款客户号
	 */
	public String feeAcctId;
	/**
	 * 商户收取服务费金额
	 */
	public String servFee;
	/**
	 * 商户子账户号
	 */
	public String servFeeAcctId;
	/**
	 * 页面返回地址
	 */
	public String retUrl;
	/**
	 * 后台返回地址
	 */
	public String bgRetUrl;
	/**
	 * 签名
	 */
	public String chkValue;
	/**
	 * 实际到账金额
	 */
	public String realTransAmt;
	   /**
     * 用于扩展字段
     */
    public String respExt;
    /**
     * （可选），扩展字段
     */
    public String merPriv;
}	
