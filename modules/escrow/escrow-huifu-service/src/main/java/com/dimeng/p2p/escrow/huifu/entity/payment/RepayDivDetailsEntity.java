package com.dimeng.p2p.escrow.huifu.entity.payment;

import java.io.Serializable;

public class RepayDivDetailsEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分账商户号 
     */
    public String DivCustId;
    /**
     * 分账账户号
     */
    public String DivAcctId;
    /**
     * 分账金额
     */
    public String DivAmt;
    
    public RepayDivDetailsEntity(String DivCustId, String DivAcctId, String DivAmt)
    {
        this.DivCustId = DivCustId;
        this.DivAcctId = DivAcctId;
        this.DivAmt = DivAmt;

    }
}
