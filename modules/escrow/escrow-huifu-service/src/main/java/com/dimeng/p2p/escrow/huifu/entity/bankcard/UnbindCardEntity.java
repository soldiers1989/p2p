package com.dimeng.p2p.escrow.huifu.entity.bankcard;

import java.io.Serializable;

public class UnbindCardEntity implements Serializable {

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
	 * 银行卡号
	 */
	public String cardId;
	/**
	 * 校验值
	 */
	public String chkValue;
   /**
     * 汇付流水号
     */
    public String trxId;
    /**
     * 银行卡号
     */
    public String bankId;
    /**
     * 快捷卡标志
     */
    public String expressFlag;
    /**
     * 商户后台应答地址
     */
    public String bgRetUrl;
	
}
