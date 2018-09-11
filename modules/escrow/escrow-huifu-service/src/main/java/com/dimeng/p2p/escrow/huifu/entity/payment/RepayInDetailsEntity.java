package com.dimeng.p2p.escrow.huifu.entity.payment;

import java.io.Serializable;
import java.util.List;

public class RepayInDetailsEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 还款订单号
     */
    public String OrdId;
    
    /**
     * 原投资订单号
     */
    public String SubOrdId;
    
    /**
     * 入账客户号
     */
    public String InCustId;
    
    /**
     * 入账子账号
     */
    public String InAcctId;
    
    /**
     * 交易金额
     */
    public String TransAmt;
    
    /**
     * 被垫资人客户号  
     */
    public String DzBorrCustId;
    
    /**
     * 手续费收取对象标志 I/O    
     */
    public String FeeObjFlag;
    
    /**
     * 扣款手续费    
     */
    public String Fee;
    
    /**
     * 分账账户串    
     */
    public List<RepayDivDetailsEntity> DivDetails;
}