package com.dimeng.p2p.escrow.huifu.entity.freeze;

import java.io.Serializable;

public class UnFreezeEntity implements Serializable
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
     * 订单流水号
     */
    public String trxId;
    
    /**
     * 后台返回地址
     */
    public String bgRetUrl;
    
    /**
    * 商户私有域
    */
    public String merPriv;
    
    /**
     * 校验值
     */
    public String chkValue;
}
