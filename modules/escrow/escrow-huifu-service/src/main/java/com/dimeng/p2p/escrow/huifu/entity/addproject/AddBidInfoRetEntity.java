package com.dimeng.p2p.escrow.huifu.entity.addproject;

import java.io.Serializable;

public class AddBidInfoRetEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String Version;
    
    public String CmdId;// 消息类型
    
    public String RespCode;// 应答返回码 000：调用成功 其他返回码见“附件七：返回码表”
    
    public String RespDesc;// 应答描述返回码的对应中文描述
    
    public String MerCustId;// 商户客户号商户的唯一标识
    
    public String ProId;// 项目ID标的的唯一标识
    
    public String RespExt;// 返参扩展域用于扩展返回参数
    
    // 签名 CmdId+RespCode+MerCustId+ProId+AuditStat+BgRetUrl+MerPriv+RespExt
    public String ChkValue;
    
    public String BorrCustId;
    
    public String BorrTotAmt;
    
    public String GuarCompId;
    
    public String BgRetUrl;
    
    public String MerPriv;
    
    /**
     * 审核状态 01：通过 02：拒绝 03：待上传证照 04：待审核 05：待审核证照 06：状态异常
     * 根据恒丰返回的：审核通过、审核拒绝，待审核相应返回该字段
     */
    public String AuditStat;
    
    public String Status;
    
    public String AuditDesc;
    
    public String PlainStr;
    
    public String PlainStr2;
    
    public String getVersion()
    {
        return Version;
    }
    
    public void setVersion(String version)
    {
        Version = version;
    }
    
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
    
    public String getProId()
    {
        return ProId;
    }
    
    public void setProId(String proId)
    {
        ProId = proId;
    }
    
    public String getChkValue()
    {
        return ChkValue;
    }
    
    public void setChkValue(String chkValue)
    {
        ChkValue = chkValue;
    }
    
    public String getBorrCustId()
    {
        return BorrCustId;
    }
    
    public void setBorrCustId(String borrCustId)
    {
        BorrCustId = borrCustId;
    }
    
    public String getBorrTotAmt()
    {
        return BorrTotAmt;
    }
    
    public void setBorrTotAmt(String borrTotAmt)
    {
        BorrTotAmt = borrTotAmt;
    }
    
    public String getGuarCompId()
    {
        return GuarCompId;
    }
    
    public void setGuarCompId(String guarCompId)
    {
        GuarCompId = guarCompId;
    }
    
    public String getBgRetUrl()
    {
        return BgRetUrl;
    }
    
    public void setBgRetUrl(String bgRetUrl)
    {
        BgRetUrl = bgRetUrl;
    }
    
    public String getMerPriv()
    {
        return MerPriv;
    }
    
    public void setMerPriv(String merPriv)
    {
        MerPriv = merPriv;
    }
    
    public String getAuditStat()
    {
        return AuditStat;
    }
    
    public void setAuditStat(String auditStat)
    {
        AuditStat = auditStat;
    }
    
    public String getRespExt()
    {
        return RespExt;
    }
    
    public void setRespExt(String respExt)
    {
        RespExt = respExt;
    }
    
    public String getAuditDesc()
    {
        return AuditDesc;
    }
    
    public void setAuditDesc(String auditDesc)
    {
        AuditDesc = auditDesc;
    }
    
    public String getPlainStr()
    {
        return PlainStr;
    }
    
    public void setPlainStr(String plainStr)
    {
        PlainStr = plainStr;
    }
    
    public String getPlainStr2()
    {
        return PlainStr2;
    }
    
    public void setPlainStr2(String plainStr2)
    {
        PlainStr2 = plainStr2;
    }
    
    public String getStatus()
    {
        return Status;
    }
    
    public void setStatus(String status)
    {
        Status = status;
    }
    
}
