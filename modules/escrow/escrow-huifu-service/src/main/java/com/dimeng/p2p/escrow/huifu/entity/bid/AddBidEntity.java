package com.dimeng.p2p.escrow.huifu.entity.bid;

import java.io.Serializable;

public class AddBidEntity implements Serializable {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 6144843603971628704L;

	/**
	 * 项目ID 16位 必须 标的的唯一标识，为英文和数字组合
	 */
	public String ProId;

	/**
	 * 标的名称 50位 必须
	 */
	public String BidName;

	/**
	 * 标的类型 2位 必须 01：信用 02：抵押 03：债权转让 99：其他
	 */
	public String BidType;

	/**
	 * 发标金额 14位 必须 单位为元，精确到分，例如1000.01
	 */
	public String BorrTotAmt;

	/**
	 * 发标年化利率 14位 必须 百分比，保留2位小数，例如24.55
	 */
	public String YearRate;

	/**
	 * 应还款总利息 16位 必须 单位为元，精确到分，例如1000.01（商户可传根据发标金额和利率计算的值）
	 */
	public String RetInterest;

	/**
	 * 最后还款日期 8位 必须 格式yyyymmdd
	 */
	public String LastRetDate;

	/**
	 * 计划投标开始日期 14位 必须 格式yyyyMMddHHmmss
	 */
	public String BidStartDate;

	/**
	 * 计划投标截止日期 14位 必须 格式yyyyMMddHHmmss
	 */
	public String BidEndDate;

	/**
	 * 借款期限 20位 必须 例如：XX天、XX月、XX年
	 */
	public String LoanPeriod;

	/**
	 * 还款方式 2位 必须 01：一次还本付息 02：等额本金 03：等额本息 04：按期付息到期还本 99：其他
	 */
	public String RetType;

	/**
	 * 应还款日期 8位 必须 格式yyyymmdd
	 */
	public String RetDate;

	/**
	 * 本息保障 2位 可选 01：保本保息 02：保本不保息 03：不保本不保息
	 */
	public String GuarantType;

	/**
	 * 标的产品类型 2位 必须 01：房贷类 02：车贷类 03：收益权转让类 04：信用贷款类 05：股票配资类 06：银行承兑汇票
	 * 07：商业承兑汇票 08：消费贷款类 09：供应链类 99：其他
	 */
	public String BidProdType;

	/**
	 * 风险控制方式 2位 可选 01：抵（质）押 02：共管账户 03：担保 04：信用无担保 99：其他
	 */
	public String RiskCtlType;

	/**
	 * 推荐机构 150位 可选 文本
	 */
	public String Recommer;

	/**
	 * 限定最低投标份数 7位 可选 整数
	 */
	public String LimitMinBidAmt;

	/**
	 * 限定每份投标金额 16位 可选 单位为元，精确到分，例如1000.01
	 */
	public String LimitBidSum;

	/**
	 * 限定最多投标金额 16位 可选 单位为元，精确到分，例如1000.01
	 */
	public String LimitMaxBidSum;

	/**
	 * 限定最少投标金额 16位 可选 单位为元，精确到分，例如1000.01
	 */
	public String LimitMinBidSum;

	/**
	 * 逾期概率 5位 可选 保留2位小数
	 */
	public String OverdueProba;

	/**
	 * 逾期是否垫资 1位 可选 1：是 2：否
	 */
	public String BidPayforState;

	/**
	 * 借款人类型 1位 必须 01：个人 02：企业
	 */
	public String BorrType;

	/**
	 * 借款人ID 16位 必须 借款人的唯一标识
	 */
	public String BorrCustId;

	/**
	 * 借款人名称 50位 必须 文本，借款人真实姓名或者借款企业名称
	 */
	public String BorrName;

	/**
	 * 借款企业营业执照编号 30位 可选 借款人类型为企业时为必填
	 */
	public String BorrBusiCode;

	/**
	 * 借款人证件类型 2位 可选 00：身份证 （暂只支持身份证） 借款人类型为“01：个人”时为必须参数
	 */
	public String BorrCertType;

	/**
	 * 借款人证件号码 18位 可选 借款人类型为“01：个人”时为必须参数
	 */
	public String BorrCertId;

	/**
	 * 借款人手机号码 11位 必须
	 */
	public String BorrMobiPhone;
	/**
	 * 借款人固定电话 12位 可选
	 */
	public String BorrPhone;

	/**
	 * 借款人工作单位 150位 可选 文本
	 */
	public String BorrWork;

	/**
	 * 借款人工作年限 3位 可选 单位为年，整数
	 */
	public String BorrWorkYear;

	/**
	 * 借款人税后月收入 16位 可选 单位为元，保留2位小数
	 */
	public String BorrIncome;

	/**
	 * 借款人学历 20位 可选 文本
	 */
	public String BorrEdu;

	/**
	 * 借款人婚姻状况 1位 可选 Y：已婚 N：未婚
	 */
	public String BorrMarriage;

	/**
	 * 借款人地址 150位 可选 文本
	 */
	public String BorrAddr;

	/**
	 * 借款人电子邮箱 30位 可选 文本
	 */
	public String BorrEmail;

	/**
	 * 借款用途 150位 必须 文本
	 */
	public String BorrPurpose;

	/**
	 * 商户后台应答地址 128位 必须
	 * 通过后台异步通知，商户网站都应在应答接收页面输出RECV_ORD_ID字样的字符串，表明商户已经收到该笔交易结果。
	 * 注意：1.使用时请注意不要包含中文 2.URL中请不要包含“附件三：BGRETURL中禁 止的字符串列表”的字符串 3.必须是外网地址
	 */
	public String BgRetUrl;

	/**
	 * 商户私有域 120位 可选 为商户的自定义字段，该字段在交易完成后由本平台原样返回。
	 * 注意：如果该字段中包含了中文字符请对该字段的数据进行Base64加密后再使用。
	 */
	public String MerPriv;

	/**
	 * 入参扩展域 512位 可选 用于扩展请求参数
	 */
	public String ReqExt;

	public String getProId() {
		return ProId;
	}

	public void setProId(String proId) {
		ProId = proId;
	}

	public String getBidName() {
		return BidName;
	}

	public void setBidName(String bidName) {
		BidName = bidName;
	}

	public String getBidType() {
		return BidType;
	}

	public void setBidType(String bidType) {
		BidType = bidType;
	}

	public String getBorrTotAmt() {
		return BorrTotAmt;
	}

	public void setBorrTotAmt(String borrTotAmt) {
		BorrTotAmt = borrTotAmt;
	}

	public String getYearRate() {
		return YearRate;
	}

	public void setYearRate(String yearRate) {
		YearRate = yearRate;
	}

	public String getRetInterest() {
		return RetInterest;
	}

	public void setRetInterest(String retInterest) {
		RetInterest = retInterest;
	}

	public String getLastRetDate() {
		return LastRetDate;
	}

	public void setLastRetDate(String lastRetDate) {
		LastRetDate = lastRetDate;
	}

	public String getBidStartDate() {
		return BidStartDate;
	}

	public void setBidStartDate(String bidStartDate) {
		BidStartDate = bidStartDate;
	}

	public String getBidEndDate() {
		return BidEndDate;
	}

	public void setBidEndDate(String bidEndDate) {
		BidEndDate = bidEndDate;
	}

	public String getLoanPeriod() {
		return LoanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		LoanPeriod = loanPeriod;
	}

	public String getRetType() {
		return RetType;
	}

	public void setRetType(String retType) {
		RetType = retType;
	}

	public String getRetDate() {
		return RetDate;
	}

	public void setRetDate(String retDate) {
		RetDate = retDate;
	}

	public String getGuarantType() {
		return GuarantType;
	}

	public void setGuarantType(String guarantType) {
		GuarantType = guarantType;
	}

	public String getBidProdType() {
		return BidProdType;
	}

	public void setBidProdType(String bidProdType) {
		BidProdType = bidProdType;
	}

	public String getRiskCtlType() {
		return RiskCtlType;
	}

	public void setRiskCtlType(String riskCtlType) {
		RiskCtlType = riskCtlType;
	}

	public String getRecommer() {
		return Recommer;
	}

	public void setRecommer(String recommer) {
		Recommer = recommer;
	}

	public String getLimitMinBidAmt() {
		return LimitMinBidAmt;
	}

	public void setLimitMinBidAmt(String limitMinBidAmt) {
		LimitMinBidAmt = limitMinBidAmt;
	}

	public String getLimitBidSum() {
		return LimitBidSum;
	}

	public void setLimitBidSum(String limitBidSum) {
		LimitBidSum = limitBidSum;
	}

	public String getLimitMaxBidSum() {
		return LimitMaxBidSum;
	}

	public void setLimitMaxBidSum(String limitMaxBidSum) {
		LimitMaxBidSum = limitMaxBidSum;
	}

	public String getLimitMinBidSum() {
		return LimitMinBidSum;
	}

	public void setLimitMinBidSum(String limitMinBidSum) {
		LimitMinBidSum = limitMinBidSum;
	}

	public String getOverdueProba() {
		return OverdueProba;
	}

	public void setOverdueProba(String overdueProba) {
		OverdueProba = overdueProba;
	}

	public String getBidPayforState() {
		return BidPayforState;
	}

	public void setBidPayforState(String bidPayforState) {
		BidPayforState = bidPayforState;
	}

	public String getBorrType() {
		return BorrType;
	}

	public void setBorrType(String borrType) {
		BorrType = borrType;
	}

	public String getBorrCustId() {
		return BorrCustId;
	}

	public void setBorrCustId(String borrCustId) {
		BorrCustId = borrCustId;
	}

	public String getBorrName() {
		return BorrName;
	}

	public void setBorrName(String borrName) {
		BorrName = borrName;
	}

	public String getBorrBusiCode() {
		return BorrBusiCode;
	}

	public void setBorrBusiCode(String borrBusiCode) {
		BorrBusiCode = borrBusiCode;
	}

	public String getBorrCertType() {
		return BorrCertType;
	}

	public void setBorrCertType(String borrCertType) {
		BorrCertType = borrCertType;
	}

	public String getBorrCertId() {
		return BorrCertId;
	}

	public void setBorrCertId(String borrCertId) {
		BorrCertId = borrCertId;
	}

	public String getBorrMobiPhone() {
		return BorrMobiPhone;
	}

	public void setBorrMobiPhone(String borrMobiPhone) {
		BorrMobiPhone = borrMobiPhone;
	}

	public String getBorrPhone() {
		return BorrPhone;
	}

	public void setBorrPhone(String borrPhone) {
		BorrPhone = borrPhone;
	}

	public String getBorrWork() {
		return BorrWork;
	}

	public void setBorrWork(String borrWork) {
		BorrWork = borrWork;
	}

	public String getBorrWorkYear() {
		return BorrWorkYear;
	}

	public void setBorrWorkYear(String borrWorkYear) {
		BorrWorkYear = borrWorkYear;
	}

	public String getBorrIncome() {
		return BorrIncome;
	}

	public void setBorrIncome(String borrIncome) {
		BorrIncome = borrIncome;
	}

	public String getBorrEdu() {
		return BorrEdu;
	}

	public void setBorrEdu(String borrEdu) {
		BorrEdu = borrEdu;
	}

	public String getBorrMarriage() {
		return BorrMarriage;
	}

	public void setBorrMarriage(String borrMarriage) {
		BorrMarriage = borrMarriage;
	}

	public String getBorrAddr() {
		return BorrAddr;
	}

	public void setBorrAddr(String borrAddr) {
		BorrAddr = borrAddr;
	}

	public String getBorrEmail() {
		return BorrEmail;
	}

	public void setBorrEmail(String borrEmail) {
		BorrEmail = borrEmail;
	}

	public String getBorrPurpose() {
		return BorrPurpose;
	}

	public void setBorrPurpose(String borrPurpose) {
		BorrPurpose = borrPurpose;
	}

	public String getBgRetUrl() {
		return BgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {
		BgRetUrl = bgRetUrl;
	}

	public String getMerPriv() {
		return MerPriv;
	}

	public void setMerPriv(String merPriv) {
		MerPriv = merPriv;
	}

	public String getReqExt() {
		return ReqExt;
	}

	public void setReqExt(String reqExt) {
		ReqExt = reqExt;
	}

}
