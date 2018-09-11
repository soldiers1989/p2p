package com.dimeng.p2p.escrow.huifu.cond;

public interface WithdrawCond
{
    /**
     * （必须），订单id
     */
    public String ordId();
    
    /**
     * （必须），用户商户号
     */
    public String usrCustId();
    
    /**
     * （必填）， 交易金额
     */
    public String transAmt();
    
    /**
     * （可选），商户收取服务费金额
     */
    public String servFee();
    
    /**
     * （可选），商户子账户号
     */
    public String servFeeAccId();
    
    /**
     * （可选），开户银行账号（银行卡号）
     */
    public String openAcctId();
    
    /**
     * （可选），返回地址
     */
    public String retUrl();
    
    /**
     * （可选），扩展字段
     */
    public String merPriv();
    
}
