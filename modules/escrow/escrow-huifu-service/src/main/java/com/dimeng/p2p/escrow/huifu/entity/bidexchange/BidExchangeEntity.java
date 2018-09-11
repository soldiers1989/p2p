package com.dimeng.p2p.escrow.huifu.entity.bidexchange;

import java.io.Serializable;

public class BidExchangeEntity implements Serializable
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
     * 转出者客户号
     */
    public String sellCustId;
    
    /**
     * 转让金额
     */
    public String creditAmt;
    
    /**
     * 承接金额
     */
    public String creditDealAmt;
    
    /**k
     * 手续费
     */
    public String fee;
    
    /**
     * 承接者客户号
     */
    public String buyCustId;
    
    /**
     * 订单id
     */
    public String ordId;
    
    /**
     * 订单时间
     */
    public String ordDate;
    
    /**
     * 前台返回地址
     */
    public String retUrl;
    
    /**
     * 后台返回地址
     */
    public String bgRetUrl;
    
    /**
    * 私有域
    */
    public String merPriv;
    
    /**
     * 校验值
     */
    public String chkValue;
}
