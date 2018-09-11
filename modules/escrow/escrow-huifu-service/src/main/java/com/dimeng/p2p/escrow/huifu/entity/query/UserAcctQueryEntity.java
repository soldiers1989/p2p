package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;

public class UserAcctQueryEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String CmdId;
    
    public String RespCode;
    
    public String RespDesc;
    
    public String ChkValue;
    
    public String Version;
    
    public String AvlBal;
    
    public String AcctBal;
    
    public String FrzBal;
    
    public String MerCustId;
    
    public String UsrCustId;
    
    public String PlainStr;
    
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
    
    public String getChkValue()
    {
        return ChkValue;
    }
    
    public void setChkValue(String chkValue)
    {
        ChkValue = chkValue;
    }
    
    public String getVersion()
    {
        return Version;
    }
    
    public void setVersion(String version)
    {
        Version = version;
    }
    
    public String getAvlBal()
    {
        return AvlBal;
    }
    
    public void setAvlBal(String avlBal)
    {
        AvlBal = avlBal;
    }
    
    public String getAcctBal()
    {
        return AcctBal;
    }
    
    public void setAcctBal(String acctBal)
    {
        AcctBal = acctBal;
    }
    
    public String getFrzBal()
    {
        return FrzBal;
    }
    
    public void setFrzBal(String frzBal)
    {
        FrzBal = frzBal;
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
    
    public String getPlainStr()
    {
        return PlainStr;
    }
    
    public void setPlainStr(String plainStr)
    {
        PlainStr = plainStr;
    }
}
