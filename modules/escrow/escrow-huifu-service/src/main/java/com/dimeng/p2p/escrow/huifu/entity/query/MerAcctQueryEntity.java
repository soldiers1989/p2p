package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;
import java.util.List;

import com.dimeng.p2p.escrow.huifu.entity.meracct.MerAcctItem;

public class MerAcctQueryEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String CmdId;
	public String RespCode;
	public String RespDesc;
	public String ChkValue;
	public String Version;
	public String MerCustId;
	public List<MerAcctItem>  AcctDetails;
	public String PlaintStr;
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
	public String getChkValue() {
		return ChkValue;
	}
	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getMerCustId() {
		return MerCustId;
	}
	public void setMerCustId(String merCustId) {
		MerCustId = merCustId;
	}
	public List<MerAcctItem> getAcctDetails() {
		return AcctDetails;
	}
	public void setAcctDetails(List<MerAcctItem> acctDetails) {
		AcctDetails = acctDetails;
	}
	public String getPlaintStr() {
		return PlaintStr;
	}
	public void setPlaintStr(String plaintStr) {
		PlaintStr = plaintStr;
	}
}
