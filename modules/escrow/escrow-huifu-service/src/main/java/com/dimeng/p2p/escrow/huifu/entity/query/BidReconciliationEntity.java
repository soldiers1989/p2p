package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;

public class BidReconciliationEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String OrdId;
    
    public String OrdDate;
    
    /**
     * 转让人客户号
     */
    public String SellCustId;
    
    /**
     * 转让金额
     */
    public String CreditAmt;
    
    /**
     * 承接金额
     */
    public String CreditDealAmt;
    
    /**
     * 手续费
     */
    public String Fee;
    
    /**
     * 承接人客户号
     */
    public String BuyCustId;
    
    /**
     * 交易状态 S-- 成功 F-- 失败 I--初始化
     */
    public String TransStat;
    
    /**
     * 汇付交易日期
     */
    public String PnrDate;
    
    /**
     * 汇付交易流水
     */
    public String PnrSeqId;
    
    public String getOrdId()
    {
        return OrdId;
    }
    
    public void setOrdId(String ordId)
    {
        OrdId = ordId;
    }
    
    public String getOrdDate()
    {
        return OrdDate;
    }
    
    public void setOrdDate(String ordDate)
    {
        OrdDate = ordDate;
    }
    
    public String getSellCustId()
    {
        return SellCustId;
    }
    
    public void setSellCustId(String sellCustId)
    {
        SellCustId = sellCustId;
    }
    
    public String getCreditAmt()
    {
        return CreditAmt;
    }
    
    public void setCreditAmt(String creditAmt)
    {
        CreditAmt = creditAmt;
    }
    
    public String getCreditDealAmt()
    {
        return CreditDealAmt;
    }
    
    public void setCreditDealAmt(String creditDealAmt)
    {
        CreditDealAmt = creditDealAmt;
    }
    
    public String getFee()
    {
        return Fee;
    }
    
    public void setFee(String fee)
    {
        Fee = fee;
    }
    
    public String getBuyCustId()
    {
        return BuyCustId;
    }
    
    public void setBuyCustId(String buyCustId)
    {
        BuyCustId = buyCustId;
    }
    
    public String getTransStat()
    {
        return TransStat;
    }
    
    public void setTransStat(String transStat)
    {
        TransStat = transStat;
    }
    
    public String getPnrDate()
    {
        return PnrDate;
    }
    
    public void setPnrDate(String pnrDate)
    {
        PnrDate = pnrDate;
    }
    
    public String getPnrSeqId()
    {
        return PnrSeqId;
    }
    
    public void setPnrSeqId(String pnrSeqId)
    {
        PnrSeqId = pnrSeqId;
    }
}
