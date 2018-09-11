package com.dimeng.p2p.escrow.huifu.cond;

public interface BidExchangeCond
{
    /**
     * （必填），转让金额
     */
    public String creditAmt();
    
    /**
     * （必填），承接金额
     */
    public String creditDealAmt();
    
    /**
     * （必填），债权转让明细
     */
    public String bidDetails();
    
    /**
     * （必填），手续费
     */
    public String fee();
    
    /**
     * （必填），分账账户串
     */
    public String divDetails();
    
    /**
     * （必填），承接人客户号
     */
    public String buyCustId();
    
    /**
     * （必填），订单id
     */
    public String ordId();
    
    /**
     * （必填），订单时间
     */
    public String ordDate();
    
    /**
     * （可选），前台页面返回地址
     */
    public String retUrl();
    
    /**
    * （可选），私有域
    */
    public String merPriv();
}
