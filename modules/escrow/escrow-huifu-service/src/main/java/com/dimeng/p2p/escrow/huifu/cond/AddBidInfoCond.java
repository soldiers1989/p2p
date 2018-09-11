package com.dimeng.p2p.escrow.huifu.cond;

public interface AddBidInfoCond
{
    /**
     * （必须），项目id
     */
    public String proId();
    
    /**
     * （必须），借款人id
     */
    public String borrCustId();
    
    /**
     * （必填）， 借款总金额
     */
    public String borrTotAmt();
    
    /**
     * （必填），年化利率
     */
    public String yearRate();
    
    /**
     * （必填），还款方式
     */
    public String retType();
    
    /**
     * （必填），投资开始时间
     */
    public String bidStartDate();
    
    /**
     * （必填），投资截止时间
     */
    public String bidEndDate();
    
    /**
     * （必填），应还款总金额
     */
    public String retAmt();
    
    /**
     * （必填），应还款日期
     */
    public String retDate();
    
    /**
     * （必填），担保公司id
     */
    public String guarCompId();
    
    /**
     * （必填），担保金额
     */
    public String guarAmt();
    
    /**
     * （必填），项目所在地
     */
    public String proArea();
    
    /**
     * （选填），前台返回地址
     */
    public String retUrl();
    
}