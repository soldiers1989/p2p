package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.config.Envionment;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6141;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.enums.T6230_F10;
import com.dimeng.p2p.S62.enums.T6230_F11;
import com.dimeng.p2p.S62.enums.T6230_F12;
import com.dimeng.p2p.S62.enums.T6230_F13;
import com.dimeng.p2p.S62.enums.T6230_F14;
import com.dimeng.p2p.S62.enums.T6230_F15;
import com.dimeng.p2p.S62.enums.T6230_F16;
import com.dimeng.p2p.S62.enums.T6230_F17;
import com.dimeng.p2p.S62.enums.T6230_F20;
import com.dimeng.p2p.S62.enums.T6230_F27;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.addproject.AddBidInfoRetEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.AddBidEntity;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.modules.bid.console.service.AddBidManage;
import com.dimeng.p2p.variables.defines.LetterVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.TimestampParser;

/**
 * 汇付标信息操作业务实现类
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月21日]
 */
public class AddBidManageImpl extends AbstractEscrowService implements AddBidManage
{
    
    public AddBidManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public List<T6230> selectT6230s()
        throws Throwable
    {
        List<T6230> list = new ArrayList<T6230>();
        try (Connection connection = this.getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25, F26, F27 FROM S62.T6230 WHERE T6230.F20 = 'DFB'"))
            {
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        T6230 record = new T6230();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getString(3);
                        record.F04 = resultSet.getInt(4);
                        list.add(record);
                    }
                }
            }
        }
        return list;
    }
    
    public T6230 selectT6230(int F01)
        throws Throwable
    {
        T6230 record = null;
        try (Connection connection = this.getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25, F26, F27 FROM S62.T6230 WHERE T6230.F01 = ?"))
            {
                pstmt.setInt(1, F01);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6230();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getString(3);
                        record.F04 = resultSet.getInt(4);
                        record.F05 = resultSet.getBigDecimal(5);
                        record.F06 = resultSet.getBigDecimal(6);
                        record.F07 = resultSet.getBigDecimal(7);
                        record.F08 = resultSet.getInt(8);
                        record.F09 = resultSet.getInt(9);
                        record.F10 = T6230_F10.parse(resultSet.getString(10));
                        record.F11 = T6230_F11.parse(resultSet.getString(11));
                        record.F12 = T6230_F12.parse(resultSet.getString(12));
                        record.F13 = T6230_F13.parse(resultSet.getString(13));
                        record.F14 = T6230_F14.parse(resultSet.getString(14));
                        record.F15 = T6230_F15.parse(resultSet.getString(15));
                        record.F16 = T6230_F16.parse(resultSet.getString(16));
                        record.F17 = T6230_F17.parse(resultSet.getString(17));
                        record.F18 = resultSet.getInt(18);
                        record.F19 = resultSet.getInt(19);
                        record.F20 = T6230_F20.parse(resultSet.getString(20));
                        record.F21 = resultSet.getString(21);
                        record.F22 = resultSet.getTimestamp(22);
                        record.F23 = resultSet.getInt(23);
                        record.F24 = resultSet.getTimestamp(24);
                        record.F25 = resultSet.getString(25);
                        record.F26 = resultSet.getBigDecimal(26);
                        record.F27 = T6230_F27.parse(resultSet.getString(27));
                    }
                }
            }
        }
        return record;
    }
    
    @Override
    public Map<String, String> addBidInfo(int loanId)
        throws Throwable
    {
        
        // TODO Auto-generated method stub
        try (Connection connection = getConnection())
        {
            T6230 t6230 = this.selectT6230(loanId);
            AddBidEntity addBidInfo = new AddBidEntity();
            addBidInfo.setProId(t6230.F25); // 项目ID
            addBidInfo.setBidName(t6230.F03);// 标的名称
            // 标的类型 必填 01：信用 02：抵押 03：债权转让 99：其他
            addBidInfo.setBidType("01");
            // 发标金额
            addBidInfo.setBorrTotAmt(t6230.F05.setScale(2, RoundingMode.HALF_UP).toString());
            // 发标年化利率
            addBidInfo.setYearRate(t6230.F06.setScale(2, RoundingMode.HALF_UP).toString());
            // 应还款总利息
            addBidInfo.setRetInterest(t6230.F05.multiply(t6230.F06).setScale(2, RoundingMode.HALF_UP).toString());
            // 最后还款日期 必填 格式yyyymmdd
            addBidInfo.setLastRetDate(DateParser.format(DateUtils.addYears(new Date(), 1), "yyyyMMdd"));
            // 计划投标开始日期 格式yyyyMMddHHmmss
            addBidInfo.setBidStartDate(DateParser.format(DateUtils.addMonths(new Date(), 1), "yyyyMMddHHmmss"));
            // 计划投标截止日期 格式yyyyMMddHHmmss
            addBidInfo.setBidEndDate(DateParser.format(DateUtils.addMonths(new Date(), 2), "yyyyMMddHHmmss"));
            // 借款期限
            addBidInfo.setLoanPeriod(t6230.F09 + "月");
            // 还款方式 必填 01：一次还本付息 02：等额本金 03：等额本息 04：按期付息到期还本 99：其他
            addBidInfo.setRetType("99");
            // 应还款日期 格式yyyymmdd
            addBidInfo.setRetDate(DateParser.format(DateUtils.addMonths(new Date(), 3), "yyyyMMdd"));
            // 本息保障 可选 01：保本保息 02：保本不保息 03：不保本不保息
            addBidInfo.setGuarantType("01");
            // 标的产品类型 必填 01：房贷类 02：车贷类 03：收益权转让类 04：信用贷款类 05：股票配资类
            // 06：银行承兑汇票 07：商业承兑汇票 08：消费贷款类 09：供应链类 99：其他
            addBidInfo.setBidProdType("04");
            
            // 风险控制方式 必填 可选 01：抵（质）押 02：共管账户 03：担保 04：信用无担保 99：其他
            addBidInfo.setRiskCtlType("");
            addBidInfo.setRecommer(""); // 推荐机构 可选
            addBidInfo.setLimitMinBidAmt("");// 限定最低投标份数 可选
            addBidInfo.setLimitBidSum(""); // 限定每份投标金额 可选 单位为元，精确到分，例如1000.01
            addBidInfo.setLimitMinBidSum("");// 限定每份投标金额 可选
            addBidInfo.setLimitMinBidSum("");// 限定最少投标金额 可选
            addBidInfo.setOverdueProba("");// 逾期概率 可选
            addBidInfo.setBidPayforState("");// 逾期是否垫资 可选 1：是 2：否
            
            T6110 t6110 = this.selectT6110(connection, t6230.F02);
            T6141 t6141 = this.selectT6141(connection, t6230.F02);
            T6161 t6161 = this.selectT6161(connection, t6230.F02);
            String userAccount = userCustId(t6110.F01);
            // 借款人类型 必填01：个人 02：企业
            addBidInfo.setBorrType(t6110.F06 == T6110_F06.ZRR ? "01" : "02");
            // 借款人ID 必填 借款人的唯一标识
            addBidInfo.setBorrCustId(userAccount);
            // 借款人名称 必填 借款人真实姓名或者借款企业名称
            addBidInfo.setBorrName(t6161 == null ? t6141.F02 : t6161.F04);
            // 借款企业营业执照编号 可选 借款人类型为企业时为必填
            if (t6161 != null)
            {
                if ("N".equals(t6161.F20))
                {
                    //非三合一证取F03 营业执照登记注册号
                    addBidInfo.setBorrBusiCode(t6161.F03);
                }
                else
                {
                    //三合一证取F19 社会信用代码
                    addBidInfo.setBorrBusiCode(t6161.F19);
                }
            }
            else
            {
                addBidInfo.setBorrBusiCode("");
            }
            
            // 借款人证件类型 可选 00：身份证 （暂只支持身份证） 借款人类型为“01：个人”时为必须参数
            addBidInfo.setBorrCertType(t6110.F06 == T6110_F06.ZRR ? "00" : "");
            // 借款人证件号码 可选 借款人类型为“01：个人”时为必须参数
            addBidInfo.setBorrCertId(t6110.F06 == T6110_F06.ZRR ? t6141.F07 : "");
            addBidInfo.setBorrMobiPhone(t6110.F04);// 借款人手机号码 必填
            
            addBidInfo.setBorrPhone("");// 借款人固定电话 可选
            addBidInfo.setBorrWork("");// 借款人工作单位 可选
            addBidInfo.setBorrWorkYear("");// 借款人工作年限 可选 单位为年，整数
            addBidInfo.setBorrIncome("");// 借款人税后月收入 可选 单位为元，保留2位小数
            addBidInfo.setBorrEdu("");// 借款人学历 可选
            addBidInfo.setBorrMarriage("");// 借款人婚姻状况 可选 Y：已婚 N：未婚
            addBidInfo.setBorrAddr("");// 借款人地址 可选
            addBidInfo.setBorrEmail("");// 借款人电子邮箱 可选
            
            addBidInfo.setBorrPurpose("做生意"); // 借款用途 必填
            addBidInfo.setMerPriv("");// 商户私有域 可选
            addBidInfo.setReqExt("");// 入参扩展域 可选 用于扩展请求参数
            
            AddBidInfoRetEntity entity = addBidInfoUrI(addBidInfo);
            return updateT6230_20(entity, connection, t6230);
        }
    }
    
    public AddBidInfoRetEntity addBidInfoUrI(AddBidEntity addBidInfo)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "AddBidInfo";
        String MerCustId = merCustId;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_ADD_BID);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(addBidInfo.getProId());
        params.add(addBidInfo.getBidType());
        params.add(addBidInfo.getBorrTotAmt());
        params.add(addBidInfo.getYearRate());
        params.add(addBidInfo.getRetInterest());
        params.add(addBidInfo.getLastRetDate());
        params.add(addBidInfo.getBidStartDate());
        params.add(addBidInfo.getBidEndDate());
        params.add(addBidInfo.getRetType());
        params.add(addBidInfo.getRetDate());
        // params.add(addBidInfo.getGuarantType());
        params.add(addBidInfo.getBidProdType());
        params.add(addBidInfo.getRiskCtlType());
        params.add(addBidInfo.getLimitMinBidAmt());
        params.add(addBidInfo.getLimitBidSum());
        params.add(addBidInfo.getLimitMaxBidSum());
        params.add(addBidInfo.getLimitMinBidSum());
        params.add(addBidInfo.getBidPayforState());
        params.add(addBidInfo.getBorrType());
        params.add(addBidInfo.getBorrCustId());
        params.add(addBidInfo.getBorrBusiCode());
        params.add(addBidInfo.getBorrCertType());
        params.add(addBidInfo.getBorrCertId());
        params.add(addBidInfo.getBorrMobiPhone());
        params.add(addBidInfo.getBorrPhone());
        params.add(addBidInfo.getBorrWorkYear());
        params.add(addBidInfo.getBorrIncome());
        params.add(addBidInfo.getBorrMarriage());
        params.add(addBidInfo.getBorrEmail());
        params.add(charSet);
        params.add(BgRetUrl);
        params.add(addBidInfo.getMerPriv());
        params.add(addBidInfo.getReqExt());
        
        // Version+CmdId+MerCustId+ProId+BidType+BorrTotAmt+YearRate+RetInterest+LastRetDate+BidStartDate+
        // BidEndDate+RetType+RetDate+GuarantType+BidProdType+RiskCtlType+LimitMinBidAmt+LimitBidSum+LimitMaxBidSum+
        // LimitMinBidSum+BidPayforState+BorrType+BorrCustId+BorrBusiCode+BorrCertType+BorrCertId+BorrMobiPhone+BorrPhone+
        // BorrWorkYear+BorrIncome+BorrMarriage+BorrEmail+CharSet+BgRetUrl+MerPriv+ReqExt
        // 先对此明文串做 md5md5 加密，再将 md5md5 加密 后的文做汇付的 RSA RSA加
        String ChkValue = chkValueNew(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version); // 版本号 必填
        map.put("CmdId", CmdId); // 消息类型 必填
        map.put("MerCustId", MerCustId); // 商户客户号 必填
        map.put("ProId", addBidInfo.getProId()); // 项目ID 必填 标的的唯一标识，为英文和数字组合
        map.put("BidName", HttpClientHandler.getBase64Encode(addBidInfo.getBidName()));// 标的名称
                                                                                       // 必填
        // 标的类型 必填 01：信用 02：抵押 03：债权转让 99：其他
        map.put("BidType", addBidInfo.getBidType());
        // 发标金额 必填 单位为元，精确到分，例如1000.01
        map.put("BorrTotAmt", addBidInfo.getBorrTotAmt());
        // 发标年化利率 必填
        // 百分比，保留2位小数，例如24.55
        map.put("YearRate", addBidInfo.getYearRate());
        // 应还款总利息 必填 单位为元，精确到分，例如1000.01 （商户可传根据发标金额和利率计算的值）
        map.put("RetInterest", addBidInfo.getRetInterest());
        // 最后还款日期 必填 格式yyyymmdd
        map.put("LastRetDate", addBidInfo.getLastRetDate());
        // 计划投标开始日期 必填 格式yyyyMMddHHmmss
        map.put("BidStartDate", addBidInfo.getBidStartDate());
        // 计划投标截止日期 必填 格式yyyyMMddHHmmss
        map.put("BidEndDate", addBidInfo.getBidEndDate());
        // 借款期限 必填 例如：XX天、XX月、XX年
        map.put("LoanPeriod", addBidInfo.getLoanPeriod());
        // 还款方式 必填 01：一次还本付息 02：等额本金 03：等额本息 04：按期付息到期还本 99：其他
        map.put("RetType", addBidInfo.getRetType());
        // 应还款日期 必填 格式yyyymmdd
        map.put("RetDate", addBidInfo.getRetDate());
        // 本息保障 可选 01：保本保息 02：保本不保息 03：不保本不保息
        // map.put("GuarantType", addBidInfo.getGuarantType());
        // 标的产品类型 必填 01：房贷类 02：车贷类 03：收益权转让类 04：信用贷款类 05：股票配资类
        // 06：银行承兑汇票 07：商业承兑汇票 08：消费贷款类 09：供应链类 99：其他
        map.put("BidProdType", addBidInfo.getBidProdType());
        // 风险控制方式 必填 可选 01：抵（质）押 02：共管账户 03：担保 04：信用无担保 99：其他
        // map.put("RiskCtlType", addBidInfo.getRiskCtlType());
        // // 推荐机构 可选
        // map.put("Recommer", addBidInfo.getRecommer());
        // // 限定最低投标份数 可选
        // map.put("LimitMinBidAmt", addBidInfo.getLimitMinBidAmt());
        // // 限定每份投标金额 可选 单位为元，精确到分，例如1000.01
        // map.put("LimitBidSum", addBidInfo.getLimitBidSum());
        // // 限定最多投标金额 可选 单位为元，精确到分，例如1000.01
        // map.put("LimitMaxBidSum", addBidInfo.getLimitMaxBidSum());
        // // 限定最少投标金额 可选 单位为元，精确到分，例如1000.01
        // map.put("LimitMinBidSum", addBidInfo.getLimitMinBidSum());
        // 逾期概率 可选 保留2位小数
        // map.put("OverdueProba", addBidInfo.getOverdueProba());
        // 逾期是否垫资 可选 1：是 2：否
        // map.put("BidPayforState", addBidInfo.getBidPayforState());
        
        // 借款人类型 必填01：个人 02：企业
        map.put("BorrType", addBidInfo.getBorrType());
        // 借款人ID 必填 借款人的唯一标识
        map.put("BorrCustId", addBidInfo.getBorrCustId());
        // 借款人名称 必填 借款人真实姓名或者借款企业名称
        map.put("BorrName", addBidInfo.getBorrName());
        // 借款企业营业执照编号 可选 借款人类型为企业时为必填
        if ("02".equals(addBidInfo.getBorrType()))
        {
            map.put("BorrBusiCode", addBidInfo.getBorrBusiCode());
        }
        // 借款人证件类型 可选 00：身份证 （暂只支持身份证） 借款人类型为“01：个人”时为必须参数
        map.put("BorrCertType", addBidInfo.getBorrCertType());
        // 借款人证件号码 可选 借款人类型为“01：个人”时为必须参数
        map.put("BorrCertId", addBidInfo.getBorrCertId());
        // 借款人手机号码 必填
        map.put("BorrMobiPhone", addBidInfo.getBorrMobiPhone());
        // 借款人固定电话 可选
        // map.put("BorrPhone", addBidInfo.getBorrPhone());
        // // 借款人工作单位 可选
        // map.put("BorrWork", addBidInfo.getBorrWork());
        // // 借款人工作年限 可选 单位为年，整数
        // map.put("BorrWorkYear", addBidInfo.getBorrWorkYear());
        // // 借款人税后月收入 可选 单位为元，保留2位小数
        // map.put("BorrIncome", addBidInfo.getBorrIncome());
        // // 借款人学历 可选
        // map.put("BorrEdu", addBidInfo.getBorrEdu());
        // 借款人婚姻状况 可选 Y：已婚 N：未婚
        // map.put("BorrMarriage", addBidInfo.getBorrMarriage());
        // 借款人地址 可选
        // map.put("BorrAddr", addBidInfo.getBorrAddr());
        // 借款人电子邮箱 可选
        // map.put("BorrEmail", addBidInfo.getBorrEmail());
        // 借款用途 必填
        map.put("BorrPurpose", HttpClientHandler.getBase64Encode(addBidInfo.getBorrPurpose()));
        
        // 编码集 必填 加签验签的时候，商户需告知汇付其系统编码，汇付在验签的时候进行相应的编码转换验签。
        // 因字段中有中文，应为：GBK或者UTF-8 如果是空，默认UTF-8
        map.put("CharSet", charSet);
        // 商户后台应答地址 必填 通过后台异步通知，商户网站都应在应答接收页面输出RECV_ORD_ID字样的字符串，表明商户已经收到该笔交易结果。
        // 注意： 1）使用时请注意不要包含中文 2）URL中请不要包含“附件三：BGRETURL中禁止的字符串列表”的字符串 3）必须是外网地址
        map.put("BgRetUrl", BgRetUrl);
        // 商户私有域 可选
        // 为商户的自定义字段，该字段在交易完成后由本平台原样返回。注意：如果该字段中包含了中文字符请对该字段的数据进行Base64加密后再使用。
        // map.put("MerPriv", addBidInfo.getMerPriv());
        // 入参扩展域 可选 用于扩展请求参数
        // map.put("ReqExt", addBidInfo.getReqExt());
        map.put("ChkValue", ChkValue); // 签名 必填
        
        String result = doPostNew(map);
        logger.info("汇付建标返回信息：" + urlDecoder(result));
        AddBidInfoRetEntity entity = jsonReflectParser(urlDecoder(result), AddBidInfoRetEntity.class);
        
        return entity;
    }
    
    private T6141 selectT6141(Connection connection, int id)
        throws Throwable
    {
        T6141 recorde = null;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT F01,F02,F07 FROM S61.T6141 WHERE F01= ?  "))
        {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    recorde = new T6141();
                    recorde.F01 = resultSet.getInt(1);
                    recorde.F02 = resultSet.getString(2);
                    recorde.F07 = StringHelper.decode(resultSet.getString(3));
                }
            }
        }
        return recorde;
    }
    
    private T6161 selectT6161(Connection connection, int id)
        throws Throwable
    {
        T6161 recorde = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01,F02,F03,F04,F05,F06,F11,F13,F19,F20,F21 FROM S61.T6161 WHERE F01= ?"))
        {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    recorde = new T6161();
                    recorde.F01 = resultSet.getInt(1);
                    recorde.F02 = resultSet.getString(2);
                    recorde.F03 = resultSet.getString(3);
                    recorde.F04 = resultSet.getString(4);
                    recorde.F05 = resultSet.getString(5);
                    recorde.F06 = resultSet.getString(6);
                    recorde.F11 = resultSet.getString(7);
                    recorde.F13 = StringHelper.decode(resultSet.getString(8));
                    recorde.F19 = resultSet.getString(9);
                    recorde.F20 = resultSet.getString(10);
                    recorde.F21 = resultSet.getString(11);
                }
            }
        }
        return recorde;
    }
    
    @Override
    public Map<String, String> checkBidInfo(int loanId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6230 t6230 = this.selectT6230(loanId);
            String Version = HuiFuConstants.Version.VERSION_10;
            String CmdId = "QueryBidInfo";
            String MerCustId = merCustId;
            String ProId = t6230.F25;
            
            List<String> params = new ArrayList<>();
            params.add(Version);
            params.add(CmdId);
            params.add(MerCustId);
            params.add(ProId);
            
            // Version+ CmdId+MerCustId + ProId + ReqExt+ MerPriv
            // 先对此明文串做 md5md5 加密，再将 加密，再将 md5md5 加密后的 文做汇付RSARSA 加 签
            String ChkValue = chkValueNew(params);
            
            Map<String, String> map = new HashMap<>();
            map.put("Version", Version); // 版本号 必填
            map.put("CmdId", CmdId); // 消息类型 必填
            map.put("MerCustId", MerCustId); // 商户客户号 必填
            map.put("ProId", ProId); // 项目ID 必填 标的的唯一标识，为英文和数字组合
            
            map.put("ChkValue", ChkValue); // 签名 必填
            
            String result = doPostNew(map);
            logger.info("汇付查询标状态返回信息：" + urlDecoder(result));
            AddBidInfoRetEntity entity = jsonReflectParser(urlDecoder(result), AddBidInfoRetEntity.class);
            entity.AuditStat = entity.Status;
            return updateT6230_20(entity, connection, t6230);
        }
    }
    
    @Override
    public String bidEnterAgain(String proId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            String Version = HuiFuConstants.Version.VERSION_10;
            String CmdId = "AddBidAttachInfo";
            String MerCustId = merCustId;
            String RetUrl = configureProvider.format(HuifuVariable.HF_ADD_BID);
            String BgRetUrl = configureProvider.format(HuifuVariable.HF_ADD_BID);
            
            List<String> params = new ArrayList<>();
            params.add(Version);
            params.add(CmdId);
            params.add(MerCustId);
            params.add(proId);
            params.add(RetUrl);
            params.add(BgRetUrl);
            
            // Version+ CmdId+MerCustId + ProId + ReqExt+ MerPriv
            // 先对此明文串做 md5md5 加密，再将 加密，再将 md5md5 加密后的 文做汇付RSARSA 加 签
            String ChkValue = chkValueNew(params);
            
            Map<String, String> map = new HashMap<>();
            map.put("Version", Version); // 版本号 必填
            map.put("CmdId", CmdId); // 消息类型 必填
            map.put("MerCustId", MerCustId); // 商户客户号 必填
            map.put("ProId", proId); // 项目ID 必填 标的的唯一标识，为英文和数字组合
            map.put("RetUrl", RetUrl);
            map.put("BgRetUrl", BgRetUrl);
            
            map.put("ChkValue", ChkValue); // 签名 必填
            
            return concatUrl(map);
        }
    }
    
    /**
     * 根据汇付返回审核结果修改标状态
     * 
     * @param entity
     * @param connection
     * @param t6230
     * @throws Throwable
     */
    private Map<String, String> updateT6230_20(AddBidInfoRetEntity entity, Connection connection, T6230 t6230)
        throws Throwable
    {
        Map<String, String> map = null;
        if (entity != null
            && (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode) || "395".equals(entity.RespCode)))
        {
            map = new HashMap<String, String>();
            if (null == entity.AuditStat || "".equals(entity.AuditStat) || "null".equals(entity.AuditStat))
            {
                map.put("stat", HuiFuConstants.AuditStatus.SUCCESS);
            }
            else
            {
                map.put("stat", entity.AuditStat);
            }
            map.put("desc", "汇付建标成功！");
            String sql = "UPDATE S62.T6230 SET F20 = ?, F24 = ? WHERE F01 = ?";
            if (HuiFuConstants.AuditStatus.SUCCESS.equals(entity.AuditStat))
            {
                // “01”通过状态，直接将标状态改为投资中，进入标投资流程
                execute(connection, sql, T6230_F20.TBZ, getCurrentTimestamp(connection), t6230.F01);
                
                map.put("desc", "汇付审核通过！");
            }
            else if (HuiFuConstants.AuditStatus.REFUSING.equals(entity.AuditStat)
                || HuiFuConstants.AuditStatus.ERROR.equals(entity.AuditStat))
            {
                // “02”拒绝和“06”异常状态，直接将标状态改为已作废，执行作废流程
                execute(connection, sql, T6230_F20.YZF, getCurrentTimestamp(connection), t6230.F01);
                
                // 发站内信你
                ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
                Envionment envionment = configureProvider.createEnvionment();
                envionment.set("datetime", TimestampParser.format(t6230.F24));
                envionment.set("title", t6230.F03);
                envionment.set("reason", entity.AuditDesc);
                String content = configureProvider.format(LetterVariable.LOAN_CHECKED_FAILD, envionment);
                sendLetter(connection, t6230.F02, "借款申请审核不通过", content);
                
                map.put("desc", "汇付审核拒绝！拒绝原因：" + entity.AuditDesc);
            }
            else if (HuiFuConstants.AuditStatus.NEED_UPLOAD.equals(entity.AuditStat))
            {
                // “03”待上传证照状态，直接将标状态改为待补录，进去待补录流程
                //                execute(connection, sql, T6230_F20.DBL, getCurrentTimestamp(connection), t6230.F01);
                execute(connection, sql, "DBL", getCurrentTimestamp(connection), t6230.F01);
                
                map.put("desc", "标状态为待上传证照，请进行补录操作！");
            }
            else
            {
                // 其余状态下直接将标状态改为待发布，进入自动扫描审核状态流程
                execute(connection, sql, T6230_F20.DFB, getCurrentTimestamp(connection), t6230.F01);
                
                map.put("desc", "汇付正在审核中，请耐心等待！");
            }
            return map;
        }
        else
        {
            throw new LogicalException(entity.RespDesc);
        }
    }
    
}
