package com.dimeng.p2p.escrow.huifu.entity.transfer;

import java.io.Serializable;

public class TransStatQueryEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String CmdId;
    
    public String RespCode;
    
    public String RespDesc;
    
    public String ChkValue;
    
    public String Version;
    
    public String MerCustId;
    
    public String TransStat;
    
    public String OrdId;
    
    public String OrdDate;
    
    public String QueryTransType;
    
    public String PlainStr;
}
