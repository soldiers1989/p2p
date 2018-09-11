package com.dimeng.p2p.escrow.huifu.entity.withdraw;

import java.io.Serializable;

public class WithdrawItem implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String OrdId;
    
    public String MerCustId;
    
    public String UsrCustId;
    
    public String CardId;
    
    public String TransAmt;
    
    /**
     * 交易状态 S-- 成功， F-- 失败， I-- 初始， H-- 经办， R--拒绝
     */
    public String TransStat;
    
    public String PnrDate;
    
    public String PnrSeqId;
    
    public String ServFee;
    
    public String ServFeeAcctId;
    
    public String FeeAmt;
    
    public String FeeCustId;
    
    public String FeeAcctId;
    
    public String getOrdId()
    {
        return OrdId;
    }
    
    public void setOrdId(String ordId)
    {
        OrdId = ordId;
    }
    
    public String getMerCustId()
    {
        return MerCustId;
    }
    
    public void setMerCustId(String merCustId)
    {
        MerCustId = merCustId;
    }
    
    public String getUsrCustId()
    {
        return UsrCustId;
    }
    
    public void setUsrCustId(String usrCustId)
    {
        UsrCustId = usrCustId;
    }
    
    public String getCardId()
    {
        return CardId;
    }
    
    public void setCardId(String cardId)
    {
        CardId = cardId;
    }
    
    public String getTransAmt()
    {
        return TransAmt;
    }
    
    public void setTransAmt(String transAmt)
    {
        TransAmt = transAmt;
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
    
    public String getServFee()
    {
        return ServFee;
    }
    
    public void setServFee(String servFee)
    {
        ServFee = servFee;
    }
    
    public String getServFeeAcctId()
    {
        return ServFeeAcctId;
    }
    
    public void setServFeeAcctId(String servFeeAcctId)
    {
        ServFeeAcctId = servFeeAcctId;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    public String getFeeAmt()
    {
        return FeeAmt;
    }
    
    public void setFeeAmt(String feeAmt)
    {
        FeeAmt = feeAmt;
    }
    
    public String getFeeCustId()
    {
        return FeeCustId;
    }
    
    public void setFeeCustId(String feeCustId)
    {
        FeeCustId = feeCustId;
    }
    
    public String getFeeAcctId()
    {
        return FeeAcctId;
    }
    
    public void setFeeAcctId(String feeAcctId)
    {
        FeeAcctId = feeAcctId;
    }
    
}
