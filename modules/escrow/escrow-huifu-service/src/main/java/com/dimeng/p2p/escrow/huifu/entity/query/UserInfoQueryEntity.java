package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;

public class UserInfoQueryEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String CmdId;
    
    public String RespCode;
    
    public String RespDesc;
    
    public String ChkValue;
    
    public String Version;
    
    public String UsrId;
    
    public String CertId;
    
    /**
     * ‘N’ – 正常 ‘A’ – 待激活 ，登录后需要用户‘C’ – 被关闭，临时不能登陆 被关闭，临时不能登陆 ‘D’ – 销户
     */
    public String UsrStat;
    
    public String MerCustId;
    
    public String UsrCustId;
    
    public String RespExt;
    
    /**
     * 汇付交易状态 N—正常 C—关
     */
    public String TransStat;
    
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
    
    public String getUsrId()
    {
        return UsrId;
    }
    
    public void setUsrId(String usrId)
    {
        UsrId = usrId;
    }
    
    public String getCertId()
    {
        return CertId;
    }
    
    public void setCertId(String certId)
    {
        CertId = certId;
    }
    
    public String getUsrStat()
    {
        return UsrStat;
    }
    
    public void setUsrStat(String usrStat)
    {
        UsrStat = usrStat;
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
    
    public String getRespExt()
    {
        return RespExt;
    }
    
    public void setRespExt(String respExt)
    {
        RespExt = respExt;
    }
    
    public String getTransStat()
    {
        return TransStat;
    }
    
    public void setTransStat(String transStat)
    {
        TransStat = transStat;
    }
    
}
