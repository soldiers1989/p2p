package com.dimeng.p2p.escrow.huifu.entity.loans;

import java.io.Serializable;

public class LoansEntity implements Serializable {

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
	 * 出账客户号
	 */
	public String outCustId;
	/**
	 * 出账账户
	 */
	public String outAcctId;
	/**
	 * 交易金额
	 */
	public String transAmt;
	/**
	 * 交易手续费
	 */
	public String fee;
	/**
	 * 入账客户号
	 */
	public String inCustId;
	/**
	 * 入账账户
	 */
	public String inAcctId;
	/**
	 * 子订单id
	 */
	public String subOrdId;
	/**
	 * 子订单提交时间
	 */
	public String subOrdDate;
	/**
	 * 
	 */
	public String feeObjFlag;
	/**
	 * 是否取现到银行卡
	 */
	public String isDefault;
	
	public String isUnFreeze;
	
	public String unFreezeOrdId;
	
	public String freezeTxId;
	/**
	 * 后台返回地址
	 */
	public String bgRetUrl;
	/**
	 * 开户银行代号
	 */
	public String openBankId;
	/**
	 * 银行卡号
	 */
	public String openAcctId;
	/**
	 * 签名
	 */
	public String chkValue;
	/**
	 * 扩展参数
	 */
	public String respExt;
	
}
