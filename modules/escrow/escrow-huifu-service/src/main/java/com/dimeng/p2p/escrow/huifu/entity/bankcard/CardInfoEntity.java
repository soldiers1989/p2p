package com.dimeng.p2p.escrow.huifu.entity.bankcard;

import java.io.Serializable;

public class CardInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public String MerCustId;
	public String UsrCustId;
	public String UsrName;
	public String CertId;
	public String BankId;
	public String CardId;
	public String RealFlag;
	public String UpdDateTime;
	public String ProvId;
	public String AreaId;
	public String IsDefault;
	public String ExpressFlag;

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

	public String getUsrName() {
		return UsrName;
	}

	public void setUsrName(String usrName) {
		UsrName = usrName;
	}

	public String getCertId() {
		return CertId;
	}

	public void setCertId(String certId) {
		CertId = certId;
	}

	public String getBankId() {
		return BankId;
	}

	public void setBankId(String bankId) {
		BankId = bankId;
	}

	public String getCardId() {
		return CardId;
	}

	public void setCardId(String cardId) {
		CardId = cardId;
	}

	public String getRealFlag() {
		return RealFlag;
	}

	public void setRealFlag(String realFlag) {
		RealFlag = realFlag;
	}

	public String getUpdDateTime() {
		return UpdDateTime;
	}

	public void setUpdDateTime(String updDateTime) {
		UpdDateTime = updDateTime;
	}

	public String getProvId() {
		return ProvId;
	}

	public void setProvId(String provId) {
		ProvId = provId;
	}

	public String getAreaId() {
		return AreaId;
	}

	public void setAreaId(String areaId) {
		AreaId = areaId;
	}

	public String getIsDefault() {
		return IsDefault;
	}

	public void setIsDefault(String isDefault) {
		IsDefault = isDefault;
	}

	public String getExpressFlag() {
		return ExpressFlag;
	}

	public void setExpressFlag(String expressFlag) {
		ExpressFlag = expressFlag;
	}

}
