package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;
import java.util.List;

public class BidExchangeQueryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public String CmdId;
	public String RespCode;
	public String RespDesc;
	public String MerCustId;
	public String OrdId;
	public String BeginDate;
	public String EndDate;
	public String SellCustId;
	public String BuyCustId;
	public String PageNum;
	public String PageSize;
	public String TotalItems;
	public List<BidReconciliationEntity> BidCaReconciliationList;
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

	public String getOrdId() {
		return OrdId;
	}

	public void setOrdId(String ordId) {
		OrdId = ordId;
	}

	public String getBeginDate() {
		return BeginDate;
	}

	public void setBeginDate(String beginDate) {
		BeginDate = beginDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getSellCustId() {
		return SellCustId;
	}

	public void setSellCustId(String sellCustId) {
		SellCustId = sellCustId;
	}

	public String getBuyCustId() {
		return BuyCustId;
	}

	public void setBuyCustId(String buyCustId) {
		BuyCustId = buyCustId;
	}

	public String getPageNum() {
		return PageNum;
	}

	public void setPageNum(String pageNum) {
		PageNum = pageNum;
	}

	public String getPageSize() {
		return PageSize;
	}

	public void setPageSize(String pageSize) {
		PageSize = pageSize;
	}

	public String getTotalItems() {
		return TotalItems;
	}

	public void setTotalItems(String totalItems) {
		TotalItems = totalItems;
	}

	public List<BidReconciliationEntity> getBidCaReconciliationList() {
		return BidCaReconciliationList;
	}

	public void setBidCaReconciliationList(
			List<BidReconciliationEntity> bidCaReconciliationList) {
		BidCaReconciliationList = bidCaReconciliationList;
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
