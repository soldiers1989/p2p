package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;

public class TransQueryEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String CmdId;
    
    public String RespCode;
    
    public String RespDesc;
    
    public String MerCustId;
    
    public String OrdId;
    
    public String OrdDate;
    
    public String QueryTran;
    
    public String TransStat;
    
    public String TransAmt;
    
    public String TrxId;
    
    public String ChkValue;
    
    public String getCmdId()
    {
        return CmdId;
    }
    
    public void setCmdId(String cmdId)
    {
        CmdId = cmdId;
    }
    
    public String getRespCode()
    {
        return RespCode;
    }
    
    public void setRespCode(String respCode)
    {
        RespCode = respCode;
    }
    
    public String getRespDesc()
    {
        return RespDesc;
    }
    
    public void setRespDesc(String respDesc)
    {
        RespDesc = respDesc;
    }
    
    public String getMerCustId()
    {
        return MerCustId;
    }
    
    public void setMerCustId(String merCustId)
    {
        MerCustId = merCustId;
    }
    
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
    
    public String getQueryTran()
    {
        return QueryTran;
    }
    
    public void setQueryTran(String queryTran)
    {
        QueryTran = queryTran;
    }
    
    public String getTransStat()
    {
        return TransStat;
    }
    
    public void setTransStat(String transStat)
    {
        TransStat = transStat;
    }
    
    public String getTransAmt()
    {
        return TransAmt;
    }
    
    public void setTransAmt(String transAmt)
    {
        TransAmt = transAmt;
    }
    
    public String getTrxId()
    {
        return TrxId;
    }
    
    public void setTrxId(String trxId)
    {
        TrxId = trxId;
    }
    
    public String getChkValue()
    {
        return ChkValue;
    }
    
    public void setChkValue(String chkValue)
    {
        ChkValue = chkValue;
    }
    
}
