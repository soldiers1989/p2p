package com.dimeng.p2p.escrow.huifu.entity.query;

import java.io.Serializable;
import java.util.List;

import com.dimeng.p2p.escrow.huifu.entity.bid.BidItem;

public class BidQueryEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String CmdId;
	public String RespCode;
	public String RespDesc;
	public String ChkValue;
	public String Version;
	public String MerCustId;
	public String QueryTransType;
	public String BeginDate;
	public String EndDate;
	public int PageNum;
	public int PageSize;
	public int TotalItems;
	public List<BidItem> ReconciliationDtoList;
	public String PlainText;
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
	public String getQueryTransType() {
		return QueryTransType;
	}
	public void setQueryTransType(String queryTransType) {
		QueryTransType = queryTransType;
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
	public int getPageNum() {
		return PageNum;
	}
	public void setPageNum(int pageNum) {
		PageNum = pageNum;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public int getTotalItems() {
		return TotalItems;
	}
	public void setTotalItems(int totalItems) {
		TotalItems = totalItems;
	}
	public List<BidItem> getReconciliationDtoList() {
		return ReconciliationDtoList;
	}
	public void setReconciliationDtoList(List<BidItem> reconciliationDtoList) {
		ReconciliationDtoList = reconciliationDtoList;
	}
	public String getPlainText() {
		return PlainText;
	}
	public void setPlainText(String plainText) {
		PlainText = plainText;
	}
}
