package com.dimeng.p2p.escrow.huifu.entity.meracct;

import java.io.Serializable;

public class MerAcctItem implements Serializable {

    private static final long serialVersionUID = 1L;
    public String AcctType;
    public String SubAcctId;
    public String AvlBal;
    public String AcctBal;
    public String FrzBal;
    public String getAcctType() {
        return AcctType;
    }
    public void setAcctType(String acctType) {
        AcctType = acctType;
    }
    public String getSubAcctId() {
        return SubAcctId;
    }
    public void setSubAcctId(String subAcctId) {
        SubAcctId = subAcctId;
    }
    public String getAvlBal() {
        return AvlBal;
    }
    public void setAvlBal(String avlBal) {
        AvlBal = avlBal;
    }
    public String getAcctBal() {
        return AcctBal;
    }
    public void setAcctBal(String acctBal) {
        AcctBal = acctBal;
    }
    public String getFrzBal() {
        return FrzBal;
    }
    public void setFrzBal(String frzBal) {
        FrzBal = frzBal;
    }
}
