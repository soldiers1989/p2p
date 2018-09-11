package com.dimeng.p2p.escrow.huifu.cond;

public interface BidCancleCond
{
    
    /**
     * （必填），订单id
     */
    public String ordId();
    
    /**
     * （必填）， 订单时间，格式“20140630”
     */
    public String ordDate();
    
    /**
     * （必填）， 交易金额
     */
    public String transAmt();
    
    /**
     * （必填），用户账户号
     */
    public String usrCustId();
    
    /**
    * （必填），是否解冻（Y-解冻,N-不解冻）
    */
    public String isUnFreeze();
    
    /**
     * 解冻订单号，isUnFreeze 等于 Y 该字段必填
     */
    public String unFreezeOrdId();
    
    /**
     * （可选），解冻标识
     */
    public String FreezeTrxId();
    
    /**
     * （可选），返回地址
     */
    public String retUrl();
}
