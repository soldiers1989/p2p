package com.dimeng.p2p.escrow.huifu.entity.payment;

import java.io.Serializable;

public class PaymentEntity implements Serializable
{
    
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
     * 子订单id
     */
    public String subOrdId;
    
    /**
     * 子订单提交时间
     */
    public String subOrdDate;
    
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
     * 
     */
    public String feeObjFlag;
    
    /**
     * 后台返回地址
     */
    public String bgRetUrl;
    
    /**
     * 扩展域
     */
    public String respExt;
    
    /**
     * 商户私有域
     */
    public String merPriv;
    
    /**
     * 签名
     */
    public String chkValue;
}
