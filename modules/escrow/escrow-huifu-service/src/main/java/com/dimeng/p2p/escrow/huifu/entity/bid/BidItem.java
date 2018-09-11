package com.dimeng.p2p.escrow.huifu.entity.bid;

import java.io.Serializable;

public class BidItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public int OrdId;
    public String OrdDate;
    public String MerCustId;
    public String InvestCustId;
    public String BorrCustId;
    public String TransAmt;
    public String TransStat;
    public String PnrDate;
    public String PnrSeqId;
    public int getOrdId() {
        return OrdId;
    }
    public void setOrdId(int ordId) {
        OrdId = ordId;
    }
    public String getOrdDate() {
        return OrdDate;
    }
    public void setOrdDate(String ordDate) {
        OrdDate = ordDate;
    }
    public String getMerCustId() {
        return MerCustId;
    }
    public void setMerCustId(String merCustId) {
        MerCustId = merCustId;
    }
    public String getInvestCustId() {
        return InvestCustId;
    }
    public void setInvestCustId(String investCustId) {
        InvestCustId = investCustId;
    }
    public String getBorrCustId() {
        return BorrCustId;
    }
    public void setBorrCustId(String borrCustId) {
        BorrCustId = borrCustId;
    }
    public String getTransAmt() {
        return TransAmt;
    }
    public void setTransAmt(String transAmt) {
        TransAmt = transAmt;
    }
    public String getTransStat() {
        return TransStat;
    }
    public void setTransStat(String transStat) {
        TransStat = transStat;
    }
    public String getPnrDate() {
        return PnrDate;
    }
    public void setPnrDate(String pnrDate) {
        PnrDate = pnrDate;
    }
    public String getPnrSeqId() {
        return PnrSeqId;
    }
    public void setPnrSeqId(String pnrSeqId) {
        PnrSeqId = pnrSeqId;
    }
    
}
