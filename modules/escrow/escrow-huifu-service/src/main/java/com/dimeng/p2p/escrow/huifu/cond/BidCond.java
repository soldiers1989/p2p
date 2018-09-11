package com.dimeng.p2p.escrow.huifu.cond;

public interface BidCond
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
     * （必填），最大投资手续费率
     */
    public String maxTenderRate();
    
    /**
     * （必填），借款人信息
     */
    public String borrowerDetails();
    
    /**
     * （可选），返回地址
     */
    public String retUrl();
    
    /**
     * （可选），是否资金冻结
     */
    public String isFreeze();
    
    /**
     * （可选），冻结订单id
     */
    public String freezeOrdId();
    
    /**
      * （可选）商户私有域
      */
    public String merPriv();
    
    /**
     * 入参扩展域
     */
    public String reqExt();
}
