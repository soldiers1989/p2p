package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;

public class CorpQueryEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 消息类型
     */
    public String CmdId;
    
    /**
     * 返回码
     */
    public String RespCode;
    
    /**
     * 应答描述
     */
    public String RespDesc;
    
    /**
     * 商户客户号
     */
    public String MerCustId;
    
    /**
     * 用户客户号
     */
    public String UsrCustId;
    
    /**
     * 用户号，商户下的平台账号
     */
    public String UsrId;
    
    /**
     * 审核状态I：初始，T：提交，P：审核中，R：审核拒绝，F：开户失败，K：开户中，Y：开户成功
     */
    public String AuditStat;
    
    /**
     * 营业执照编码
     */
    public String BusiCode;
    
    /**
     * 参数扩展
     */
    public String RespExt;
    
    /**
     * 签名
     */
    public String ChkValue;

    public String getCmdId() {
        return CmdId;
    }

    public void setCmdId(String cmdId) {
        CmdId = cmdId;
    }

    public String getRespCode() {
        return RespCode;
    }

    public void setRespCode(String respCode) {
        RespCode = respCode;
    }

    public String getRespDesc() {
        return RespDesc;
    }

    public void setRespDesc(String respDesc) {
        RespDesc = respDesc;
    }

    public String getMerCustId() {
        return MerCustId;
    }

    public void setMerCustId(String merCustId) {
        MerCustId = merCustId;
    }

    public String getUsrCustId() {
        return UsrCustId;
    }

    public void setUsrCustId(String usrCustId) {
        UsrCustId = usrCustId;
    }

    public String getUsrId() {
        return UsrId;
    }

    public void setUsrId(String usrId) {
        UsrId = usrId;
    }

    public String getAuditStat() {
        return AuditStat;
    }

    public void setAuditStat(String auditStat) {
        AuditStat = auditStat;
    }

    public String getBusiCode() {
        return BusiCode;
    }

    public void setBusiCode(String busiCode) {
        BusiCode = busiCode;
    }

    public String getRespExt() {
        return RespExt;
    }

    public void setRespExt(String respExt) {
        RespExt = respExt;
    }

    public String getChkValue() {
        return ChkValue;
    }

    public void setChkValue(String chkValue) {
        ChkValue = chkValue;
    }
}
