package com.dimeng.p2p.escrow.huifu.entity.withdraw;

import java.io.Serializable;

public class CashAuditEntity implements Serializable {

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
	 * 审核状态
	 */
	public String auditFlag;
	/**
	 * 手续费金额
	 */
	public String feeAmt;
	/**
	 * 收取手续费商户号
	 */
	public String feeCustId;
	/**
	 * 收取手续费账号id
	 */
	public String feeAcctId;
	/**
	 * 后台返回地址
	 */
	public String bgRetUrl;
   /**
     * 前台返回参数
     */
    public String retUrl;
	/**
	 * 签名
	 */
	public String chkValue;
	
	/**
	 * 实际到账金额
	 */
	public String realTransAmt;
   /**
     * 异步返回的消息类型
     */
    public String respType;
    /**
     * 扩展字段
     */
    public String merPriv;
	
}
