package com.dimeng.p2p.escrow.huifu.entity.dzgl;

import java.io.Serializable;

/**
 * 充值对账返回实体
 * 
 * @author nieliang
 *
 */
public class CzdzQueryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public String CmdId;
	public String RespCode;
	public String RespDesc;
	public String MerCustId;
	public String UsrCustId;
	public String OrdId;
	public String OrdDate;
	public String QueryTransType;
	public String TransAmt;
	public String TransStat;
	public String FeeAmt;
	public String FeeCustId;
	public String FeeAcctId;
	public String GateBusiId;
	public String RespExt;
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

	public String getOrdId() {
		return OrdId;
	}

	public void setOrdId(String ordId) {
		OrdId = ordId;
	}

	public String getOrdDate() {
		return OrdDate;
	}

	public void setOrdDate(String ordDate) {
		OrdDate = ordDate;
	}

	public String getQueryTransType() {
		return QueryTransType;
	}

	public void setQueryTransType(String queryTransType) {
		QueryTransType = queryTransType;
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

	public String getFeeAmt() {
		return FeeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		FeeAmt = feeAmt;
	}

	public String getFeeCustId() {
		return FeeCustId;
	}

	public void setFeeCustId(String feeCustId) {
		FeeCustId = feeCustId;
	}

	public String getFeeAcctId() {
		return FeeAcctId;
	}

	public void setFeeAcctId(String feeAcctId) {
		FeeAcctId = feeAcctId;
	}

	public String getGateBusiId() {
		return GateBusiId;
	}

	public void setGateBusiId(String gateBusiId) {
		GateBusiId = gateBusiId;
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
