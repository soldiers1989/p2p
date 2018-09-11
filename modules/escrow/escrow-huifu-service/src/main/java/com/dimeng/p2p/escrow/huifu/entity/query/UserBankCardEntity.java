package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;
import java.util.List;

import com.dimeng.p2p.escrow.huifu.entity.bankcard.CardInfoEntity;

public class UserBankCardEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String CmdId;
    
    public String RespCode;
    
    public String RespDesc;
    
    public String MerCustId;
    
    public String UsrCustId;
    
    public String CardId;
    
    public List<CardInfoEntity> UsrCardInfolist;
    
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
    
    public List<CardInfoEntity> getUsrCardInfolist()
    {
        return UsrCardInfolist;
    }
    
    public void setUsrCardInfolist(List<CardInfoEntity> usrCardInfolist)
    {
        UsrCardInfolist = usrCardInfolist;
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
