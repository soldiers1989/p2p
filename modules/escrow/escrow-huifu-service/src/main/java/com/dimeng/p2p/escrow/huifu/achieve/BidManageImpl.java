package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6102_F10;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.entities.T6231;
import com.dimeng.p2p.S62.entities.T6238;
import com.dimeng.p2p.S62.entities.T6240;
import com.dimeng.p2p.S62.entities.T6250;
import com.dimeng.p2p.S62.entities.T6251;
import com.dimeng.p2p.S62.entities.T6252;
import com.dimeng.p2p.S62.entities.T6286;
import com.dimeng.p2p.S62.entities.T6288;
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
import com.dimeng.p2p.S62.enums.T6230_F28;
import com.dimeng.p2p.S62.enums.T6231_F19;
import com.dimeng.p2p.S62.enums.T6231_F21;
import com.dimeng.p2p.S62.enums.T6250_F07;
import com.dimeng.p2p.S62.enums.T6250_F08;
import com.dimeng.p2p.S62.enums.T6251_F08;
import com.dimeng.p2p.S62.enums.T6252_F09;
import com.dimeng.p2p.S62.enums.T6280_F10;
import com.dimeng.p2p.S62.enums.T6286_F06;
import com.dimeng.p2p.S62.enums.T6286_F07;
import com.dimeng.p2p.S62.enums.T6288_F06;
import com.dimeng.p2p.S62.enums.T6288_F07;
import com.dimeng.p2p.S62.enums.T6288_F08;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6506;
import com.dimeng.p2p.S65.entities.T6514;
import com.dimeng.p2p.S65.entities.T6521;
import com.dimeng.p2p.S65.entities.T6529;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.S65.enums.T6501_F11;
import com.dimeng.p2p.S65.enums.T6504_F06;
import com.dimeng.p2p.S65.enums.T6504_F07;
import com.dimeng.p2p.S65.enums.T6514_F07;
import com.dimeng.p2p.S65.enums.T6529_F08;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.AddBidInfoCond;
import com.dimeng.p2p.escrow.huifu.cond.AutoBidCond;
import com.dimeng.p2p.escrow.huifu.cond.BidCancleCond;
import com.dimeng.p2p.escrow.huifu.cond.BidCond;
import com.dimeng.p2p.escrow.huifu.cond.CancleAutoBidCond;
import com.dimeng.p2p.escrow.huifu.entity.addproject.AddBidInfoEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.AutoBidEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidCancleEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidCancleReturn;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.CancleAutoBidEntity;
import com.dimeng.p2p.escrow.huifu.entity.loans.LoansEntity;
import com.dimeng.p2p.escrow.huifu.entity.payment.PaymentEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.util.DateHelper;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 投标管理 <功能详细描述>
 * 
 * @author lingyuanjie
 * @version [版本号, 2016年7月21日]
 */
public class BidManageImpl extends AbstractEscrowService implements BidManage
{
    
    protected static final int DECIMAL_SCALE = 9;
    
    public BidManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class BidManageFactory implements ServiceFactory<BidManage>
    {
        
        @Override
        public BidManage newInstance(ServiceResource serviceResource)
        {
            return new BidManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public String createBidUrI(BidCond cond)
        throws Throwable
    {
        T6501 t6501 = new T6501();
        try (Connection connection = getConnection())
        {
            Timestamp times = getCurrentTimestamp(connection);
            
            t6501.F02 = OrderType.FREEZE.orderType();
            t6501.F03 = T6501_F03.DQR;
            t6501.F04 = times;
            t6501.F05 = times;
            t6501.F07 = T6501_F07.YH;
            t6501.F08 = serviceResource.getSession().getAccountId();
            t6501.F09 = getPTID(connection);
            t6501.F13 = BigDecimalParser.parse(cond.transAmt());
            t6501.F01 = insertT6501(connection, t6501);
            
            insertT6515(connection,
                t6501.F01,
                IntegerParser.parse(cond.ordId()),
                BigDecimalParser.parse(cond.transAmt()));
        }
        
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "InitiativeTender";
        String MerCustId = merCustId;
        String OrdId = cond.ordId();
        String OrdDate = cond.ordDate();
        String TransAmt = cond.transAmt();
        String UsrCustId = cond.usrCustId();
        String MaxTenderRate = cond.maxTenderRate();
        String BorrowerDetails = cond.borrowerDetails();
        String IsFreeze = cond.isFreeze();
        String FreezeOrdId = String.valueOf(t6501.F01);
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_BID);
        String MerPriv = HttpClientHandler.getBase64Encode(cond.merPriv());
        String reqExt = cond.reqExt();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(TransAmt);
        params.add(UsrCustId);
        params.add(MaxTenderRate);
        params.add(BorrowerDetails);
        params.add(IsFreeze);
        params.add(FreezeOrdId);
        params.add(RetUrl);
        params.add(BgRetUrl);
        params.add(MerPriv);
        params.add(reqExt);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("TransAmt", TransAmt);
        map.put("UsrCustId", UsrCustId);
        map.put("MaxTenderRate", MaxTenderRate);
        map.put("BorrowerDetails", BorrowerDetails);
        map.put("IsFreeze", IsFreeze);
        map.put("FreezeOrdId", FreezeOrdId);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("MerPriv", MerPriv);
        map.put("ReqExt", reqExt);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public BidEntity bidReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        BidEntity entity = new BidEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.transAmt = request.getParameter("TransAmt");
        entity.trxId = request.getParameter("TrxId");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.isFreeze = request.getParameter("IsFreeze");
        entity.freezeOrdId = request.getParameter("FreezeOrdId");
        entity.freezeTrxId = request.getParameter("FreezeTrxId");
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        entity.respExt = urlDecoder(request.getParameter("RespExt"));
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.transAmt);
        params.add(entity.usrCustId);
        params.add(entity.trxId);
        params.add(entity.isFreeze);
        params.add(entity.freezeOrdId);
        params.add(entity.freezeTrxId);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        params.add(entity.respExt);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public BidEntity bidTgReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        BidEntity entity = new BidEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.transAmt = request.getParameter("TransAmt");
        entity.trxId = request.getParameter("TrxId");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.isFreeze = request.getParameter("IsFreeze");
        entity.freezeOrdId = request.getParameter("FreezeOrdId");
        entity.freezeTrxId = request.getParameter("FreezeTrxId");
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        entity.respExt = urlDecoder(request.getParameter("RespExt"));
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.transAmt);
        params.add(entity.usrCustId);
        params.add(entity.trxId);
        params.add(entity.isFreeze);
        params.add(entity.freezeOrdId);
        params.add(entity.freezeTrxId);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        params.add(entity.respExt);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public String CreateBidCancleUri(BidCancleCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "TenderCancle";
        String MerCustId = merCustId;
        String OrdId = cond.ordId();
        String OrdDate = cond.ordDate();
        String TransAmt = cond.transAmt();
        String UsrCustId = cond.usrCustId();
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_BID_CANCLE);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(TransAmt);
        params.add(UsrCustId);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("TransAmt", TransAmt);
        map.put("UsrCustId", UsrCustId);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public BidCancleEntity bidCancleReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        BidCancleEntity entity = new BidCancleEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = request.getParameter("RetUrl");
        entity.transAmt = request.getParameter("TransAmt");
        entity.usrCustId = request.getParameter("UsrCustId");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.transAmt);
        params.add(entity.usrCustId);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public PaymentEntity payment(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        PaymentEntity entity = new PaymentEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.fee = request.getParameter("Fee");
        entity.inAcctId = request.getParameter("InAcctId");
        entity.inCustId = request.getParameter("InCustId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.outAcctId = request.getParameter("OutAcctId");
        entity.outCustId = request.getParameter("OutCustId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.subOrdDate = request.getParameter("SubOrdDate");
        entity.subOrdId = request.getParameter("SubOrdId");
        entity.transAmt = request.getParameter("TransAmt");
        entity.feeObjFlag = request.getParameter("FeeObjFlag");
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        
        List<String> ps = new ArrayList<>();
        ps.add(entity.cmdId);
        ps.add(entity.respCode);
        ps.add(entity.merCustId);
        ps.add(entity.ordId);
        ps.add(entity.ordDate);
        ps.add(entity.outCustId);
        ps.add(entity.subOrdId);
        ps.add(entity.subOrdDate);
        ps.add(entity.outAcctId);
        ps.add(entity.transAmt);
        ps.add(entity.fee);
        ps.add(entity.inCustId);
        ps.add(entity.inAcctId);
        ps.add(entity.feeObjFlag);
        ps.add(entity.bgRetUrl);
        if (entity.merPriv != null)
        {
            ps.add(entity.merPriv);
        }
        
        String str = forEncryptionStr(ps);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public LoansEntity loans(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        LoansEntity entity = new LoansEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordId = request.getParameter("OrdId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.outCustId = request.getParameter("OutCustId");
        entity.outAcctId = request.getParameter("OutAcctId");
        entity.transAmt = request.getParameter("TransAmt");
        entity.fee = request.getParameter("Fee");
        entity.inCustId = request.getParameter("InCustId");
        entity.inAcctId = request.getParameter("InAcctId");
        entity.subOrdId = request.getParameter("SubOrdId");
        entity.subOrdDate = request.getParameter("SubOrdDate");
        entity.feeObjFlag = request.getParameter("FeeObjFlag");
        entity.isDefault = request.getParameter("IsDefault");
        entity.unFreezeOrdId = request.getParameter("UnFreezeOrdId");
        entity.isUnFreeze = request.getParameter("IsUnFreeze");
        entity.freezeTxId = request.getParameter("FreezeTrxId");
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.respExt = request.getParameter("RespExt");
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> ps = new ArrayList<>();
        ps.add(entity.cmdId);
        ps.add(entity.respCode);
        ps.add(entity.merCustId);
        ps.add(entity.ordId);
        ps.add(entity.ordDate);
        ps.add(entity.outCustId);
        ps.add(entity.outAcctId);
        ps.add(entity.transAmt);
        ps.add(entity.fee);
        ps.add(entity.inCustId);
        ps.add(entity.inAcctId);
        ps.add(entity.subOrdId);
        ps.add(entity.subOrdDate);
        ps.add(entity.feeObjFlag);
        ps.add(entity.isDefault);
        ps.add(entity.isUnFreeze);
        ps.add(entity.unFreezeOrdId);
        ps.add(entity.freezeTxId);
        ps.add(entity.bgRetUrl);
        ps.add(entity.respExt);
        
        String str = forEncryptionStr(ps);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public String getUserCustId()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            return usrCustId(connection, serviceResource.getSession().getAccountId());
        }
    }
    
    private String usrCustId(Connection connection, int accoutId)
        throws SQLException
    {
        try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?"))
        {
            ps.setInt(1, accoutId);
            try (ResultSet resultSet = ps.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getString(1);
                }
            }
        }
        return null;
    }
    
    @Override
    public T6501 selectT6501(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6501 record = null;
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12 FROM S65.T6501 WHERE T6501.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6501();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = T6501_F03.parse(resultSet.getString(3));
                        record.F04 = resultSet.getTimestamp(4);
                        record.F05 = resultSet.getTimestamp(5);
                        record.F06 = resultSet.getTimestamp(6);
                        record.F07 = T6501_F07.parse(resultSet.getString(7));
                        record.F08 = resultSet.getInt(8);
                        record.F09 = resultSet.getInt(9);
                        record.F10 = resultSet.getString(10);
                        record.F11 = T6501_F11.parse(resultSet.getString(11));
                        record.F12 = resultSet.getString(12);
                        
                    }
                }
            }
            return record;
        }
    }
    
    @Override
    public T6504 selectT6504(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6504 record = null;
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05 FROM S65.T6504 WHERE T6504.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6504();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F05 = resultSet.getInt(5);
                    }
                }
            }
            return record;
        }
    }
    
    @Override
    public String borrowerDetails(int loanId, BigDecimal amount)
        throws Throwable
    {
        BigDecimal borrowRate = BigDecimalParser.parse(configureProvider.getProperty(HuifuVariable.HF_BORROWERRATE));
        try (Connection connection = getConnection())
        {
            T6230 t6230 = selectT6230(connection, loanId);
            StringBuilder builder = new StringBuilder();
            builder.append("[{\"BorrowerCustId\":\"");
            builder.append(usrCustId(connection, t6230.F02));
            builder.append("\",\"BorrowerAmt\":\"");
            builder.append(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            builder.append("\",\"BorrowerRate\":\"");
            builder.append(borrowRate.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            builder.append("\"}]");
            return builder.toString();
        }
    }
    
    private T6230 selectT6230(Connection connection, int F01)
        throws SQLException
    {
        T6230 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25, F26, F27 FROM S62.T6230 WHERE T6230.F01 = ? LIMIT 1"))
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
        return record;
    }
    
    private void insertT6515(Connection connection, int F01, int F02, BigDecimal F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6515 SET F01 = ?, F02 = ?, F03 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F02);
            pstmt.setBigDecimal(3, F03);
            pstmt.execute();
        }
    }
    
    @Override
    public void updatePayFailT6252(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6506 t6506 = selectT6506(connection, orderId);
            //            updateT6252(connection, T6252_F09.WH, t6506.F07, t6506.F05, t6506.F04);
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S62.T6252 SET F09 = ? WHERE F05 = ? AND F06 = ? AND F11 = ? AND F09 = ?"))
            {
                pstmt.setString(1, T6252_F09.WH.name());
                pstmt.setInt(2, t6506.F07);
                pstmt.setInt(3, t6506.F05);
                pstmt.setInt(4, t6506.F04);
                pstmt.setString(5, T6252_F09.HKZ.name());
                pstmt.execute();
            }
        }
    }
    
    /**
     * 查询还款订单
     * <功能详细描述>
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    private T6506 selectT6506(Connection connection, int F01)
        throws SQLException
    {
        T6506 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S65.T6506 WHERE T6506.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6506();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getInt(7);
                }
            }
        }
        return record;
    }
    
    @Override
    public PaymentEntity repayment(int ordId)
        throws Throwable
    {
        T6504 t6504 = selectT6504(ordId);
        try (Connection connection = getConnection())
        {
            if (t6504 == null)
            {
                throw new LogicalException("投资订单不存在");
            }
            T6230 t6230 = selectT6230(connection, t6504.F03);
            if (t6230 == null)
            {
                throw new LogicalException("标记录不存在");
            }
            T6501 yT6501 = selectT6501(ordId);
            T6251 t6251 = selectT6251(connection, t6504.F05);
            
            serviceResource.openTransactions(connection);
            try
            {
                if (t6251 != null)
                {
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S62.T6251 SET F07 = ? WHERE F01 = ?"))
                    {
                        ps.setBigDecimal(1, new BigDecimal(0));
                        ps.setInt(2, t6251.F01);
                        ps.executeUpdate();
                    }
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S62.T6252 SET F09 = ?, F10 = ? WHERE F11 = ?"))
                    {
                        ps.setString(1, T6252_F09.YH.name());
                        ps.setTimestamp(2, getCurrentTimestamp(connection));
                        ps.setInt(3, t6251.F01);
                        ps.executeUpdate();
                    }
                }
                
                Timestamp times = getCurrentTimestamp(connection);
                // 生成还款订单
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.BID_REPAYMENT.orderType();
                t6501.F03 = T6501_F03.DQR;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.HT;
                t6501.F08 = yT6501.F08;
                t6501.F09 = getPTID(connection);
                t6501.F13 = t6504.F04;
                t6501.F01 = insertT6501(connection, t6501);
                
                //生成还款订单
                T6506 t6506 = new T6506();
                t6506.F01 = t6501.F01;
                t6506.F02 = t6504.F02;
                t6506.F03 = t6504.F03;
                t6506.F04 = t6251 != null ? t6251.F01 : 0;
                t6506.F05 = 1;
                t6506.F06 = t6504.F04;
                t6506.F07 = FeeCode.TZ_BJ;
                insertT6506(connection, t6506);
                
                BigDecimal fee = new BigDecimal(0);
                String Version = HuiFuConstants.Version.VERSION_20;
                String CmdId = "Repayment";
                String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
                String OrdId = String.valueOf(t6506.F01);
                String OrdDate = DateParser.format(new Date(System.currentTimeMillis()), "yyyyMMdd");
                String OutCustId = usrCustId(connection, t6230.F02);
                String SubOrdId = String.valueOf(yT6501.F01);
                String SubOrdDate = DateParser.format(yT6501.F04, "yyyyMMdd");
                String TransAmt = t6506.F06.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                String Fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                String InCustId = usrCustId(connection, t6506.F02);
                String DivDetails = null;
                if (fee.compareTo(new BigDecimal(0)) > 0)
                {
                    StringBuilder builder = new StringBuilder();
                    builder.append("[{\"DivCustId\":\"");
                    builder.append(configureProvider.getProperty(HuifuVariable.HF_CUST_ID));
                    builder.append("\",\"DivAcctId\":\"" + HuiFuConstants.AcctId.ACCT_ID + "\",\"DivAmt\":\"");
                    builder.append(Fee);
                    builder.append("\"}]");
                    DivDetails = builder.toString();
                }
                String FeeObjFlag = "I";
                String BgRetUrl = configureProvider.format(HuifuVariable.HF_BG_REPAYMENT);
                
                List<String> params = new ArrayList<>();
                params.add(Version);
                params.add(CmdId);
                params.add(MerCustId);
                params.add(OrdId);
                params.add(OrdDate);
                params.add(OutCustId);
                params.add(SubOrdId);
                params.add(SubOrdDate);
                params.add(TransAmt);
                params.add(Fee);
                params.add(InCustId);
                params.add(DivDetails);
                params.add(FeeObjFlag);
                params.add(BgRetUrl);
                
                String ChkValue = chkValue(params);
                Map<String, String> map = new HashMap<>();
                map.put("Version", Version);
                map.put("CmdId", CmdId);
                map.put("MerCustId", MerCustId);
                map.put("OrdId", OrdId);
                map.put("OrdDate", OrdDate);
                map.put("OutCustId", OutCustId);
                map.put("SubOrdId", SubOrdId);
                map.put("SubOrdDate", SubOrdDate);
                map.put("TransAmt", TransAmt);
                map.put("Fee", Fee);
                map.put("InCustId", InCustId);
                map.put("DivDetails", DivDetails);
                map.put("FeeObjFlag", FeeObjFlag);
                map.put("BgRetUrl", BgRetUrl);
                map.put("ChkValue", ChkValue);
                
                String result = doPost(map);
                
                Map<String, String> jsonMap = jsonParse(result);
                
                PaymentEntity entity = new PaymentEntity();
                entity.bgRetUrl = urlDecoder(jsonMap.get("BgRetUrl"));
                entity.chkValue = jsonMap.get("ChkValue");
                entity.cmdId = jsonMap.get("CmdId");
                entity.fee = jsonMap.get("Fee");
                entity.feeObjFlag = jsonMap.get("FeeObjFlag");
                entity.inAcctId = jsonMap.get("InAcctId");
                entity.inCustId = jsonMap.get("InCustId");
                entity.merCustId = jsonMap.get("MerCustId");
                entity.ordDate = jsonMap.get("OrdDate");
                entity.ordId = jsonMap.get("OrdId");
                entity.outAcctId = jsonMap.get("OutAcctId");
                entity.outCustId = jsonMap.get("OutCustId");
                entity.respCode = jsonMap.get("RespCode");
                entity.respDesc = urlDecoder(jsonMap.get("RespDesc"));
                entity.subOrdDate = jsonMap.get("SubOrdDate");
                entity.subOrdId = jsonMap.get("SubOrdId");
                entity.transAmt = jsonMap.get("TransAmt");
                serviceResource.commit(connection);
                return entity;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    private void insertT6506(Connection connection, T6506 entity)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6506 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?"))
        {
            pstmt.setInt(1, entity.F01);
            pstmt.setInt(2, entity.F02);
            pstmt.setInt(3, entity.F03);
            pstmt.setInt(4, entity.F04);
            pstmt.setInt(5, entity.F05);
            pstmt.setBigDecimal(6, entity.F06);
            pstmt.setInt(7, entity.F07);
            pstmt.execute();
        }
    }
    
    private T6251 selectT6251(Connection connection, int F11)
        throws SQLException
    {
        T6251 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12 FROM S62.T6251 WHERE T6251.F11 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F11);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6251();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getString(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = T6251_F08.parse(resultSet.getString(8));
                    record.F09 = resultSet.getDate(9);
                    record.F10 = resultSet.getDate(10);
                    record.F11 = resultSet.getInt(11);
                    record.F12 = resultSet.getInt(12);
                }
            }
        }
        return record;
    }
    
    @Override
    public void bgRepaymented(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                T6506 t6506 = selectT6506(connection, ordId);
                if (t6506 == null)
                {
                    throw new LogicalException("还款订单不存在");
                }
                T6230 t6230 = selectT6230(connection, t6506.F03);
                if (t6230 == null)
                {
                    throw new LogicalException("标记录不存在");
                }
                T6101 jkzh = selectT6101(connection, t6230.F02, T6101_F03.WLZH);
                if (jkzh == null)
                {
                    throw new LogicalException("借款用户往来账户不存在");
                }
                T6101 tzzh = selectT6101(connection, t6506.F02, T6101_F03.WLZH);
                if (tzzh == null)
                {
                    throw new LogicalException("借款用户往来账户不存在");
                }
                {
                    jkzh.F06 = jkzh.F06.subtract(t6506.F06);
                    T6102 t6102 = new T6102();
                    t6102.F02 = jkzh.F01;
                    t6102.F03 = FeeCode.TZ_BJ;
                    t6102.F04 = tzzh.F01;
                    t6102.F07 = t6506.F06;
                    t6102.F08 = jkzh.F06;
                    t6102.F09 = "后台还款";
                    t6102.F10 = T6102_F10.WDZ;
                    insertT6102(connection, t6102);
                    updateT6101(connection, jkzh.F06, jkzh.F01);
                }
                try (PreparedStatement ps =
                    connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F06 = ? WHERE F01 = ?"))
                {
                    ps.setString(1, T6501_F03.CG.name());
                    ps.setTimestamp(2, getCurrentTimestamp(connection));
                    ps.setInt(3, ordId);
                    ps.executeUpdate();
                }
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
        
    }
    
    private void updateT6101(Connection connection, BigDecimal F01, int F02)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6101 SET F06 = ?, F07 = ? WHERE F01 = ?"))
        {
            pstmt.setBigDecimal(1, F01);
            pstmt.setTimestamp(2, getCurrentTimestamp(connection));
            pstmt.setInt(3, F02);
            pstmt.execute();
        }
    }
    
    protected T6101 selectT6101(Connection connection, int F02, T6101_F03 F03)
        throws SQLException
    {
        T6101 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S61.T6101 WHERE T6101.F02 = ? AND T6101.F03 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F02);
            pstmt.setString(2, F03.name());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6101();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = T6101_F03.parse(resultSet.getString(3));
                    record.F04 = resultSet.getString(4);
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getTimestamp(7);
                }
            }
        }
        return record;
    }
    
    @Override
    public String createAutoBidUri(AutoBidCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "AutoTenderPlan";
        String MerCustId = merCustId;
        String UsrCustId = cond.usrCustId();
        String TenderPlanType = cond.tenderPlanType();
        String TransAmt = cond.transAmt();
        String RetUrl = cond.retUrl();
        String MerPriv = cond.merPriv();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(TenderPlanType);
        params.add(TransAmt);
        params.add(RetUrl);
        params.add(MerPriv);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("TenderPlanType", TenderPlanType);
        map.put("TransAmt", TransAmt);
        map.put("RetUrl", RetUrl);
        map.put("MerPriv", MerPriv);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public AutoBidEntity autoBidRetDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        AutoBidEntity entity = new AutoBidEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.merPriv = request.getParameter("MerPriv");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.tenderPlanType = request.getParameter("TenderPlanType");
        entity.transAmt = request.getParameter("TransAmt");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> ps = new ArrayList<>();
        ps.add(entity.cmdId);
        ps.add(entity.respCode);
        ps.add(entity.merCustId);
        ps.add(entity.usrCustId);
        ps.add(entity.tenderPlanType);
        ps.add(entity.transAmt);
        ps.add(entity.retUrl);
        ps.add(entity.merPriv);
        
        String str = forEncryptionStr(ps);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public void updateAutoBidStatus(int accountId, T6280_F10 status)
        throws Throwable
    {
        if (accountId <= 0)
        {
            return;
        }
        try (Connection conn = getConnection())
        {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE S62.T6280 SET F10 = ? WHERE F12 = ?"))
            {
                ps.setString(1, status.name());
                ps.setInt(2, accountId);
                ps.execute();
            }
        }
        
    }
    
    @Override
    public String createCancleAutoBidUri(CancleAutoBidCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "AutoTenderPlanClose";
        String MerCustId = merCustId;
        String UsrCustId = cond.usrCustId();
        String RetUrl = cond.retUrl();
        String MerPriv = cond.merPriv();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(RetUrl);
        params.add(MerPriv);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("RetUrl", RetUrl);
        map.put("MerPriv", MerPriv);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public CancleAutoBidEntity cancleAutoBidRetDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        CancleAutoBidEntity entity = new CancleAutoBidEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.merPriv = request.getParameter("MerPriv");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> ps = new ArrayList<>();
        ps.add(entity.cmdId);
        ps.add(entity.respCode);
        ps.add(entity.merCustId);
        ps.add(entity.usrCustId);
        ps.add(entity.retUrl);
        ps.add(entity.merPriv);
        String str = forEncryptionStr(ps);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public void confirmRepair(int loanId)
        throws Throwable
    {
        if (loanId <= 0)
        {
            return;
        }
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                // 查询标信息
                T6230 t6230 = selectT6230(connection, loanId);
                if (t6230 == null)
                {
                    throw new LogicalException("投信息不存在");
                }
                // 判断是否全部投资均已成功放款,是则更新标状态为已流标
                int count = 0;
                try (PreparedStatement pstmt =
                    connection.prepareStatement("SELECT COUNT(*) FROM S62.T6250 WHERE F02 = ? AND F07 = 'F' AND F08 = 'F'"))
                {
                    pstmt.setInt(1, t6230.F01);
                    try (ResultSet resultSet = pstmt.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            count = resultSet.getInt(1);
                        }
                    }
                }
                if (count == 0)
                {
                    logger.info("=============放款完成");
                    // 生成还款计划
                    hkjh(connection, t6230);
                    // 更新标状态为还款中
                    updateT6230(connection, t6230.F01);
                    // 更新标扩展信息，放款时间
                    updateT6231(connection, t6230.F01);
                    // 收成交服务费
                    T6238 t6238 = selectT6238(connection, t6230.F01);
                    if (t6238 != null && t6238.F02.compareTo(BigDecimal.ZERO) > 0)
                    {
                        logger.info("==============计算成交服务费");
                        BigDecimal fwf =
                            t6238.F02.multiply(t6230.F05.subtract(t6230.F07)).setScale(2, RoundingMode.HALF_UP);
                        int pid = getPTID(connection); // 平台用户id
                        if (pid <= 0)
                        {
                            throw new LogicalException("平台账号不存在");
                        }
                        // 平台往来账户信息
                        T6101 ptwlzh = selectT6101(connection, pid, T6101_F03.WLZH);
                        if (ptwlzh == null)
                        {
                            throw new LogicalException("平台往来账户不存在");
                        }
                        // 锁定借款人往来账户
                        T6101 wlzh = null;
                        if (t6230.F27 == T6230_F27.S)
                        {
                            T6240 t6240 = selectT6240(connection, t6230.F01);
                            wlzh = selectT6101(connection, t6240.F02, T6101_F03.WLZH);
                        }
                        else
                        {
                            wlzh = selectT6101(connection, t6230.F02, T6101_F03.WLZH);
                        }
                        if (wlzh == null)
                        {
                            throw new LogicalException("借款人往来账户不存在");
                        }
                        {
                            // 平台资金增加
                            ptwlzh.F06 = ptwlzh.F06.add(fwf);
                            updateT6101(connection, ptwlzh.F06, ptwlzh.F01);
                            T6102 t6102 = new T6102();
                            t6102.F02 = ptwlzh.F01;
                            t6102.F03 = FeeCode.CJFWF;
                            t6102.F04 = wlzh.F01;
                            t6102.F06 = fwf;
                            t6102.F08 = ptwlzh.F06;
                            t6102.F09 = String.format("散标成交服务费:%s", t6230.F25);
                            t6102.F10 = T6102_F10.WDZ;
                            insertT6102(connection, t6102);
                        }
                        {
                            // 借款人账户减少
                            wlzh.F06 = wlzh.F06.subtract(fwf);
                            updateT6101(connection, wlzh.F06, wlzh.F01);
                            T6102 t6102 = new T6102();
                            t6102.F02 = wlzh.F01;
                            t6102.F03 = FeeCode.CJFWF;
                            t6102.F04 = ptwlzh.F01;
                            t6102.F07 = fwf;
                            t6102.F08 = wlzh.F06;
                            t6102.F09 = String.format("散标成交服务费:%s", t6230.F25);
                            t6102.F10 = T6102_F10.WDZ;
                            insertT6102(connection, t6102);
                        }
                    }
                    
                }
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
        
    }
    
    private void hkjh(Connection connection, T6230 t6230)
        throws Throwable
    {
        logger.info("==============生成还款计划");
        final Date currentDate = getCurrentDate(connection); // 数据库当前日期
        final Date interestDate = new Date(currentDate.getTime() + t6230.F19 * 86400000);
        final Date endDate = new Date(DateHelper.rollMonth(interestDate.getTime(), t6230.F09));
        T6251[] t6251s = selectT6251s(connection, t6230.F01);
        if (t6230.F17 == T6230_F17.ZRY)
        {// 自然月
            switch (t6230.F10)
            {
                case DEBX:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calZRY_DEBX(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                case MYFX:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calZRY_MYFX(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                case YCFQ:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calYCFQ(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                case DEBJ:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calZRY_DEBJ(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                default:
                    throw new LogicalException("不支持的还款方式");
            }
        }
        else if (t6230.F17 == T6230_F17.GDR)
        {// 固定付息日
            switch (t6230.F10)
            {
                case DEBX:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calGDR_DEBX(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                case MYFX:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calGDR_MYFX(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                case YCFQ:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calYCFQ(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                case DEBJ:
                {
                    for (T6251 t6251 : t6251s)
                    {
                        insertT6252s(connection, calGDR_DEBJ(connection, t6230, t6251, interestDate, endDate));
                    }
                    break;
                }
                default:
                    throw new LogicalException("不支持的还款方式");
            }
        }
        else
        {
            throw new LogicalException("不支持的付息方式");
        }
    }
    
    @Override
    protected Date getCurrentDate(Connection connection)
        throws Throwable
    {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT CURRENT_DATE()"))
        {
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getDate(1);
                }
            }
        }
        return null;
    }
    
    private T6251[] selectT6251s(Connection connection, int id)
        throws Throwable
    {
        ArrayList<T6251> list = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S62.T6251 WHERE T6251.F03 = ? AND F08 = 'F' FOR UPDATE"))
        {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                while (resultSet.next())
                {
                    T6251 record = new T6251();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getString(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = T6251_F08.parse(resultSet.getString(8));
                    record.F09 = resultSet.getDate(9);
                    record.F10 = resultSet.getDate(10);
                    if (list == null)
                    {
                        list = new ArrayList<>();
                    }
                    list.add(record);
                }
            }
        }
        return ((list == null || list.size() == 0) ? null : list.toArray(new T6251[list.size()]));
    }
    
    private ArrayList<T6252> calZRY_DEBX(Connection connection, T6230 t6230, T6251 t6251, Date interestDate,
        Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====自然月等额本息====开始");
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6231 SET F02 = ?, F03 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setInt(1, t6230.F09);
            pstmt.setInt(2, t6230.F09);
            pstmt.setDate(3, new java.sql.Date(DateHelper.rollMonth(interestDate.getTime(), 1)));
            pstmt.setInt(4, t6230.F01);
            pstmt.execute();
        }
        ArrayList<T6252> t6252s = new ArrayList<>();
        BigDecimal monthRate =
            t6230.F06.setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12),
                DECIMAL_SCALE,
                BigDecimal.ROUND_HALF_UP);
        BigDecimal remainTotal = t6251.F07;
        BigDecimal monthPayTotal = debx(t6251.F07, monthRate, t6230.F09);
        for (int term = 1; term <= t6230.F09; term++)
        {
            Date date = new Date(DateHelper.rollMonth(interestDate.getTime(), term));
            BigDecimal interest = remainTotal.multiply(monthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (1 == term)
            {
                updateT6231(connection, date, t6230.F01);
            }
            {
                // 利息
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_LX;
                t6252.F06 = term;
                if (t6230.F09 == term)
                {
                    t6252.F07 = monthPayTotal.subtract(remainTotal);
                }
                else
                {
                    t6252.F07 = interest;
                }
                t6252.F08 = date;
                t6252.F09 = T6252_F09.WH;
                t6252.F10 = null;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            {
                // 本金
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_BJ;
                t6252.F06 = term;
                if (t6230.F09 == term)
                {
                    t6252.F07 = remainTotal;
                }
                else
                {
                    t6252.F07 = monthPayTotal.subtract(interest);
                }
                remainTotal = remainTotal.subtract(t6252.F07);
                t6252.F08 = date;
                t6252.F09 = T6252_F09.WH;
                t6252.F10 = null;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
        }
        logger.info("====生成还款计划=====自然月等额本息====结束");
        return t6252s;
    }
    
    private BigDecimal debx(BigDecimal total, BigDecimal monthRate, int terms)
    {
        BigDecimal tmp = monthRate.add(new BigDecimal(1)).pow(terms);
        return total.multiply(monthRate)
            .multiply(tmp)
            .divide(tmp.subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP);
    }
    
    protected void updateT6231(Connection connection, Date F01, int F02)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S62.T6231 SET F06 = ? WHERE F01 = ?"))
        {
            pstmt.setDate(1, F01);
            pstmt.setInt(2, F02);
            pstmt.execute();
        }
    }
    
    private void insertT6252s(Connection connection, List<T6252> entities)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S62.T6252 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?, F10 = ?, F11 = ?"))
        {
            for (T6252 entity : entities)
            {
                pstmt.setInt(1, entity.F02);
                pstmt.setInt(2, entity.F03);
                pstmt.setInt(3, entity.F04);
                pstmt.setInt(4, entity.F05);
                pstmt.setInt(5, entity.F06);
                pstmt.setBigDecimal(6, entity.F07);
                pstmt.setDate(7, entity.F08);
                pstmt.setString(8, entity.F09.name());
                pstmt.setTimestamp(9, entity.F10);
                pstmt.setInt(10, entity.F11);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
    
    private ArrayList<T6252> calZRY_MYFX(Connection connection, T6230 t6230, T6251 t6251, Date interestDate,
        Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====自然月每月付息到期还本====开始");
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6231 SET F02 = ?, F03 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setInt(1, t6230.F09);
            pstmt.setInt(2, t6230.F09);
            pstmt.setDate(3, new Date(DateHelper.rollMonth(interestDate.getTime(), 1)));
            pstmt.setInt(4, t6230.F01);
            pstmt.execute();
        }
        ArrayList<T6252> t6252s = new ArrayList<>();
        BigDecimal monthes = new BigDecimal(12);
        for (int term = 1; term <= t6230.F09; term++)
        {
            Date date = new Date(DateHelper.rollMonth(interestDate.getTime(), term));
            if (1 == term)
            {
                updateT6231(connection, date, t6230.F01);
            }
            {
                // 利息
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_LX;
                t6252.F06 = term;
                t6252.F07 = t6251.F07.setScale(9, BigDecimal.ROUND_HALF_UP);
                t6252.F07 = t6252.F07.multiply(t6230.F06).divide(monthes, DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
                t6252.F08 = date;
                t6252.F09 = T6252_F09.WH;
                t6252.F10 = null;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            if (term == t6230.F09)
            {
                // 本金
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_BJ;
                t6252.F06 = term;
                t6252.F07 = t6251.F07;
                t6252.F08 = date;
                t6252.F09 = T6252_F09.WH;
                t6252.F10 = null;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
        }
        updateT6231(connection, t6230.F09, t6230.F01);
        logger.info("====生成还款计划=====自然月每月付息到期还本====结束");
        return t6252s;
    }
    
    private void updateT6231(Connection connection, int F01, int F02)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6231 SET F02 = ?, F03 = ? WHERE F01 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F01);
            pstmt.setInt(3, F02);
            pstmt.execute();
        }
    }
    
    private ArrayList<T6252> calYCFQ_BY_DAYS(Connection connection, T6230 t6230, T6251 t6251, Date interestDate,
        Date endDate, int days)
        throws Throwable
    {
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        // 更新下个还款日
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6231 SET F02 = ?, F03 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setInt(1, 1);
            pstmt.setInt(2, 1);
            pstmt.setDate(3, endDate);
            pstmt.setInt(4, t6230.F01);
            pstmt.execute();
        }
        ArrayList<T6252> t6252s = new ArrayList<>();
        {
            // 利息
            T6252 t6252 = new T6252();
            t6252.F02 = t6230.F01;
            t6252.F03 = t6230.F02;
            t6252.F04 = t6251.F04;
            t6252.F05 = FeeCode.TZ_LX;
            t6252.F06 = 1;
            t6252.F07 = t6251.F07.setScale(9, BigDecimal.ROUND_HALF_UP);
            t6252.F07 =
                t6252.F07.multiply(t6230.F06)
                    .multiply(new BigDecimal(days))
                    .divide(new BigDecimal(configureProvider.getProperty(SystemVariable.REPAY_DAYS_OF_YEAR)),
                        DECIMAL_SCALE,
                        BigDecimal.ROUND_HALF_UP);
            t6252.F08 = endDate;
            t6252.F09 = T6252_F09.WH;
            t6252.F10 = null;
            t6252.F11 = t6251.F01;
            t6252s.add(t6252);
        }
        {
            // 本金
            T6252 t6252 = new T6252();
            t6252.F02 = t6230.F01;
            t6252.F03 = t6230.F02;
            t6252.F04 = t6251.F04;
            t6252.F05 = FeeCode.TZ_BJ;
            t6252.F06 = 1;
            t6252.F07 = t6251.F07;
            t6252.F08 = endDate;
            t6252.F09 = T6252_F09.WH;
            t6252.F10 = null;
            t6252.F11 = t6251.F01;
            t6252s.add(t6252);
        }
        return t6252s;
    }
    
    private ArrayList<T6252> calYCFQ(Connection connection, T6230 t6230, T6251 t6251, Date interestDate, Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====自然月本息一次性付清====开始");
        int days = 0; // 借款天数
        T6231_F21 dayOrMonth = T6231_F21.F; // 借款方式
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F21, F22 FROM S62.T6231 WHERE F01 = ? LIMIT 1 FOR UPDATE"))
        {
            pstmt.setInt(1, t6230.F01);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    dayOrMonth = T6231_F21.parse(rs.getString(1));
                    days = IntegerParser.parse(rs.getInt(2));
                }
            }
        }
        
        // 分支逻辑：按天计算还款计划
        if (T6231_F21.S == dayOrMonth)
        {
            if (days <= 0)
            {
                throw new LogicalException("按天借款，其天数必须大于0");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(endDate.getTime());
            cal.add(Calendar.DATE, days);
            endDate = new java.sql.Date(cal.getTimeInMillis());
            return calYCFQ_BY_DAYS(connection, t6230, t6251, interestDate, endDate, days);
        }
        
        // 更新下个还款日
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6231 SET F02 = ?, F03 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setInt(1, 1);
            pstmt.setInt(2, 1);
            pstmt.setDate(3, endDate);
            pstmt.setInt(4, t6230.F01);
            pstmt.execute();
        }
        
        ArrayList<T6252> t6252s = new ArrayList<>();
        {
            // 利息
            T6252 t6252 = new T6252();
            t6252.F02 = t6230.F01;
            t6252.F03 = t6230.F02;
            t6252.F04 = t6251.F04;
            t6252.F05 = FeeCode.TZ_LX;
            t6252.F06 = 1;
            t6252.F07 = t6251.F07.setScale(9, BigDecimal.ROUND_HALF_UP);
            t6252.F07 =
                t6252.F07.multiply(t6230.F06)
                    .multiply(new BigDecimal(t6230.F09))
                    .divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
            t6252.F08 = endDate;
            t6252.F09 = T6252_F09.WH;
            t6252.F10 = null;
            t6252.F11 = t6251.F01;
            t6252s.add(t6252);
        }
        {
            // 本金
            T6252 t6252 = new T6252();
            t6252.F02 = t6230.F01;
            t6252.F03 = t6230.F02;
            t6252.F04 = t6251.F04;
            t6252.F05 = FeeCode.TZ_BJ;
            t6252.F06 = 1;
            t6252.F07 = t6251.F07;
            t6252.F08 = endDate;
            t6252.F09 = T6252_F09.WH;
            t6252.F10 = null;
            t6252.F11 = t6251.F01;
            t6252s.add(t6252);
        }
        logger.info("====生成还款计划=====自然月本息一次性付清====结束");
        return t6252s;
    }
    
    private ArrayList<T6252> calZRY_DEBJ(Connection connection, T6230 t6230, T6251 t6251, Date interestDate,
        Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====自然月等额本息====开始");
        {
            Calendar c = Calendar.getInstance();
            c.setTime(interestDate);
            c.add(Calendar.MONTH, t6230.F09);
            endDate = new Date(c.getTimeInMillis());
        }
        // 借款金额
        BigDecimal x = t6251.F07;
        // 月利率
        BigDecimal y = t6230.F06.divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
        // 借款期限
        int terms = t6230.F09;
        // 月还本金
        BigDecimal monthAmount = x.divide(new BigDecimal(terms), 2, BigDecimal.ROUND_HALF_UP);
        // 还本金总额
        BigDecimal total = BigDecimal.ZERO;
        ArrayList<T6252> t6252s = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(interestDate);
        for (int i = 1; i <= terms; i++)
        {
            calendar.add(Calendar.MONTH, 1);
            BigDecimal interest = BigDecimal.ZERO;
            if (1 == i)
            {
                updateT6231(connection, new Date(calendar.getTimeInMillis()), t6230.F01);
            }
            if (i == terms)
            {
                BigDecimal bj = x.subtract(total).setScale(2, BigDecimal.ROUND_HALF_UP);
                interest = bj.multiply(y).setScale(2, BigDecimal.ROUND_HALF_UP);
                { // 本金
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_BJ;
                    t6252.F06 = i;
                    t6252.F07 = bj;
                    t6252.F08 = endDate;
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                { // 利息
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_LX;
                    t6252.F06 = i;
                    t6252.F07 = interest;
                    t6252.F08 = endDate;
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                continue;
            }
            interest = x.subtract(total).multiply(y).setScale(2, BigDecimal.ROUND_HALF_UP);
            { // 本金
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_BJ;
                t6252.F06 = i;
                t6252.F07 = monthAmount;
                t6252.F08 = new Date(calendar.getTimeInMillis());
                t6252.F09 = T6252_F09.WH;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            { // 利息
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_LX;
                t6252.F06 = i;
                t6252.F07 = interest;
                t6252.F08 = new Date(calendar.getTimeInMillis());
                t6252.F09 = T6252_F09.WH;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            total = total.add(monthAmount);
        }
        logger.info("====生成还款计划=====自然月等额本息====结束");
        return t6252s;
    }
    
    private ArrayList<T6252> calGDR_DEBX(Connection connection, T6230 t6230, T6251 t6251, Date interestDate,
        Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====固定日等额本息====开始");
        Calendar calendar = Calendar.getInstance();
        final int inserestStartDay;// 起息日
        {
            calendar.setTime(interestDate);
            inserestStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        {
            Calendar c = Calendar.getInstance();
            c.setTime(interestDate);
            c.add(Calendar.MONTH, t6230.F09);
            endDate = new Date(c.getTimeInMillis());
        }
        if (inserestStartDay == t6230.F18)
        {
            // 自然月还款
            return calZRY_DEBX(connection, t6230, t6251, interestDate, endDate);
        }
        if (t6230.F18 > 28)
        {
            throw new LogicalException("固定付息日不能大于28");
        }
        // 月利率
        BigDecimal x = t6230.F06.divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        // 借款金额
        BigDecimal y = t6251.F07;
        // 日利率
        BigDecimal z =
            t6230.F06.divide(new BigDecimal(
                IntegerParser.parse(configureProvider.format(SystemVariable.REPAY_DAYS_OF_YEAR))),
                DECIMAL_SCALE,
                BigDecimal.ROUND_HALF_UP);
        // 借款期限
        int n = t6230.F09;
        // 首期天数
        int f = 0;
        // 首期付款日
        Calendar ca = Calendar.getInstance();
        if (inserestStartDay > t6230.F18)
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
            ca.setTimeInMillis(calendar.getTimeInMillis());
        }
        else
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
            ca.setTimeInMillis(calendar.getTimeInMillis());
        }
        f = (int)Math.floor((calendar.getTimeInMillis() - interestDate.getTime()) / DateHelper.DAY_IN_MILLISECONDS);
        // 尾期天数
        calendar.setTime(endDate);
        if (calendar.get(Calendar.DAY_OF_MONTH) > t6230.F18)
        {
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
        }
        else
        {
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
        }
        int l = (int)((endDate.getTime() - calendar.getTimeInMillis()) / (24 * 3600 * 1000));
        int terms = t6230.F09 + 1;
        ArrayList<T6252> t6252s = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        // 月供
        BigDecimal amount =
            x.multiply(y)
                .multiply(x.add(new BigDecimal(1)).pow(n))
                .divide(x.add(new BigDecimal(1)).pow(n).subtract(new BigDecimal(1)),
                    DECIMAL_SCALE,
                    BigDecimal.ROUND_HALF_UP);
        for (int i = 1; i <= terms; i++)
        {
            // 利息
            BigDecimal interest = BigDecimal.ZERO;
            // 本金
            BigDecimal bj = BigDecimal.ZERO;
            if (1 == i)
            {
                BigDecimal fAmount =
                    amount.multiply(new BigDecimal(f)).divide(new BigDecimal(f).add(new BigDecimal(l)),
                        DECIMAL_SCALE,
                        BigDecimal.ROUND_HALF_UP);
                interest = y.multiply(z).multiply(new BigDecimal(f)).setScale(2, BigDecimal.ROUND_HALF_UP);
                bj = fAmount.subtract(interest).setScale(2, BigDecimal.ROUND_HALF_UP);
                total = total.add(bj);
                { // 本金
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_BJ;
                    t6252.F06 = i;
                    t6252.F07 = bj;
                    t6252.F08 = new Date(ca.getTimeInMillis());
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                { // 利息
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_LX;
                    t6252.F06 = i;
                    t6252.F07 = interest;
                    t6252.F08 = new Date(ca.getTimeInMillis());
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                updateT6231(connection, new Date(ca.getTimeInMillis()), t6230.F01);
                ca.add(Calendar.MONTH, 1);
                continue;
            }
            else if (i == terms)
            {
                BigDecimal lAmount =
                    amount.multiply(new BigDecimal(l)).divide(new BigDecimal(f).add(new BigDecimal(l)),
                        DECIMAL_SCALE,
                        BigDecimal.ROUND_HALF_UP);
                bj = y.subtract(total).setScale(2, BigDecimal.ROUND_HALF_UP);
                interest = lAmount.subtract(bj).setScale(2, BigDecimal.ROUND_HALF_UP);
                total = total.add(bj);
                { // 本金
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_BJ;
                    t6252.F06 = i;
                    t6252.F07 = bj;
                    t6252.F08 = endDate;
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                { // 利息
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_LX;
                    t6252.F06 = i;
                    t6252.F07 = interest;
                    t6252.F08 = endDate;
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                
                continue;
            }
            interest = y.subtract(total).multiply(x).setScale(2, BigDecimal.ROUND_HALF_UP);
            bj = amount.subtract(interest).setScale(2, BigDecimal.ROUND_HALF_UP);
            total = total.add(bj);
            { // 本金
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_BJ;
                t6252.F06 = i;
                t6252.F07 = bj;
                t6252.F08 = new Date(ca.getTimeInMillis());
                t6252.F09 = T6252_F09.WH;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            { // 利息
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_LX;
                t6252.F06 = i;
                t6252.F07 = interest;
                t6252.F08 = new Date(ca.getTimeInMillis());
                t6252.F09 = T6252_F09.WH;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            ca.add(Calendar.MONTH, 1);
        }
        updateT6231(connection, terms, t6230.F01);
        logger.info("====生成还款计划=====固定日等额本息====结束");
        return t6252s;
    }
    
    private ArrayList<T6252> calGDR_MYFX(Connection connection, T6230 t6230, T6251 t6251, final Date interestDate,
        final Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====固定日每月付息到期还本====开始");
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        Calendar calendar = Calendar.getInstance();
        final int inserestStartDay;// 起息日
        {
            calendar.setTime(interestDate);
            inserestStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (t6230.F18 > 28)
        {
            throw new LogicalException("固定付息日不能大于28");
        }
        if (inserestStartDay == t6230.F18)
        {
            // 自然月还款
            return calZRY_MYFX(connection, t6230, t6251, interestDate, endDate);
        }
        final int term = t6230.F09 + 1;// 总期数
        BigDecimal yearDays =
            new BigDecimal(IntegerParser.parse(configureProvider.getProperty(SystemVariable.REPAY_DAYS_OF_YEAR), 360));
        Date prePayDate = interestDate;// 上次付息日
        Date payDate;
        if (inserestStartDay < t6230.F18)
        {
            calendar.setTime(interestDate);
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
            payDate = new Date(calendar.getTimeInMillis());
        }
        else
        {
            calendar.setTime(interestDate);
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
            payDate = new Date(DateHelper.rollMonth(calendar.getTimeInMillis(), 1));
        }
        // 更新扩展信息
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6231 SET F02 = ?, F03 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setInt(1, term);
            pstmt.setInt(2, term);
            pstmt.setDate(3, payDate);
            pstmt.setInt(4, t6230.F01);
            pstmt.execute();
        }
        // 生成还款记录
        ArrayList<T6252> t6252s = new ArrayList<>();
        for (int i = 1; i <= term; i++)
        {
            if (i == term)
            {
                payDate = endDate;
            }
            int days = (int)Math.ceil((payDate.getTime() - prePayDate.getTime()) / DateHelper.DAY_IN_MILLISECONDS);
            if (1 == term)
            {
                updateT6231(connection, payDate, t6230.F01);
            }
            {
                // 利息
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_LX;
                t6252.F06 = i;
                t6252.F07 = t6251.F07.setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
                t6252.F07 =
                    t6252.F07.multiply(t6230.F06)
                        .multiply(new BigDecimal(days))
                        .divide(yearDays, DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
                t6252.F08 = payDate;
                t6252.F09 = T6252_F09.WH;
                t6252.F10 = null;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            if (i == term)
            {
                // 本金
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_BJ;
                t6252.F06 = i;
                t6252.F07 = t6251.F07;
                t6252.F08 = payDate;
                t6252.F09 = T6252_F09.WH;
                t6252.F10 = null;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            prePayDate = payDate;
            payDate = new Date(DateHelper.rollMonth(payDate.getTime(), 1));
        }
        updateT6231(connection, term, t6230.F01);
        logger.info("====生成还款计划=====固定日每月付息到期还本====结束");
        return t6252s;
    }
    
    private ArrayList<T6252> calGDR_DEBJ(Connection connection, T6230 t6230, T6251 t6251, Date interestDate,
        Date endDate)
        throws Throwable
    {
        logger.info("====生成还款计划=====固定日等额本金====开始");
        Calendar calendar = Calendar.getInstance();
        final int inserestStartDay;// 起息日
        {
            calendar.setTime(interestDate);
            inserestStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        {
            Calendar c = Calendar.getInstance();
            c.setTime(interestDate);
            c.add(Calendar.MONTH, t6230.F09);
            endDate = new Date(c.getTimeInMillis());
        }
        if (inserestStartDay == t6230.F18)
        {
            // 自然月还款
            return calZRY_DEBJ(connection, t6230, t6251, interestDate, endDate);
        }
        if (t6230.F18 > 28)
        {
            throw new LogicalException("固定付息日不能大于28");
        }
        // 借款金额
        BigDecimal x = t6251.F07;
        // 月利率
        BigDecimal y = t6230.F06.divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
        // 借款期限
        int terms = t6230.F09 + 1;
        // 还款期限
        int n = t6230.F09;
        // 首期天数
        int f = 0;
        // 首期付款日
        Calendar ca = Calendar.getInstance();
        if (inserestStartDay > t6230.F18)
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
            ca.setTimeInMillis(calendar.getTimeInMillis());
        }
        else
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
            ca.setTimeInMillis(calendar.getTimeInMillis());
        }
        f = (int)(calendar.getTimeInMillis() - interestDate.getTime()) / (24 * 3600 * 1000);
        // 尾期天数
        calendar.setTime(endDate);
        if (calendar.get(Calendar.DAY_OF_MONTH) > t6230.F18)
        {
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
        }
        else
        {
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, t6230.F18);
        }
        int l = (int)((endDate.getTime() - calendar.getTimeInMillis()) / (24 * 3600 * 1000));
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal monthAmount = x.divide(new BigDecimal(n), 2, BigDecimal.ROUND_HALF_UP);
        ArrayList<T6252> t6252s = new ArrayList<>();
        for (int i = 1; i <= terms; i++)
        {
            BigDecimal interest = BigDecimal.ZERO;
            BigDecimal bj = BigDecimal.ZERO;
            if (1 == i)
            {
                interest =
                    x.multiply(y)
                        .multiply(new BigDecimal(f))
                        .divide(new BigDecimal(f).add(new BigDecimal(l)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
                bj =
                    monthAmount.multiply(new BigDecimal(f)).divide(new BigDecimal(f).add(new BigDecimal(l)),
                        2,
                        BigDecimal.ROUND_HALF_UP);
                total = total.add(bj);
                { // 本金
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_BJ;
                    t6252.F06 = i;
                    t6252.F07 = bj;
                    t6252.F08 = new Date(ca.getTimeInMillis());
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                { // 利息
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_LX;
                    t6252.F06 = i;
                    t6252.F07 = interest;
                    t6252.F08 = new Date(ca.getTimeInMillis());
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                updateT6231(connection, new Date(ca.getTimeInMillis()), t6230.F01);
                continue;
            }
            if (i == terms)
            {
                // total = total.subtract(monthAmount);
                bj = x.subtract(total).setScale(2, BigDecimal.ROUND_HALF_UP);
                interest =
                    bj.multiply(y)
                        .multiply(new BigDecimal(l))
                        .divide(new BigDecimal(f).add(new BigDecimal(l)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
                { // 本金
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_BJ;
                    t6252.F06 = i;
                    t6252.F07 = bj;
                    t6252.F08 = endDate;
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                { // 利息
                    T6252 t6252 = new T6252();
                    t6252.F02 = t6230.F01;
                    t6252.F03 = t6230.F02;
                    t6252.F04 = t6251.F04;
                    t6252.F05 = FeeCode.TZ_LX;
                    t6252.F06 = i;
                    t6252.F07 = interest;
                    t6252.F08 = endDate;
                    t6252.F09 = T6252_F09.WH;
                    t6252.F11 = t6251.F01;
                    t6252s.add(t6252);
                }
                continue;
            }
            interest = x.subtract(total).multiply(y).setScale(2, BigDecimal.ROUND_HALF_UP);
            bj = monthAmount;
            total = total.add(bj);
            ca.add(Calendar.MONTH, 1);
            { // 本金
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_BJ;
                t6252.F06 = i;
                t6252.F07 = bj;
                t6252.F08 = new Date(ca.getTimeInMillis());
                t6252.F09 = T6252_F09.WH;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
            { // 利息
                T6252 t6252 = new T6252();
                t6252.F02 = t6230.F01;
                t6252.F03 = t6230.F02;
                t6252.F04 = t6251.F04;
                t6252.F05 = FeeCode.TZ_LX;
                t6252.F06 = i;
                t6252.F07 = interest;
                t6252.F08 = new Date(ca.getTimeInMillis());
                t6252.F09 = T6252_F09.WH;
                t6252.F11 = t6251.F01;
                t6252s.add(t6252);
            }
        }
        updateT6231(connection, terms, t6230.F01);
        logger.info("====生成还款计划=====固定日等额本金====结束");
        return t6252s;
    }
    
    protected void updateT6230(Connection connection, int F02)
        throws SQLException
    {
        logger.info("===========修改标状态为还款中,标id：" + F02);
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S62.T6230 SET F20 = 'HKZ' WHERE F01 = ?"))
        {
            pstmt.setInt(1, F02);
            pstmt.execute();
        }
    }
    
    protected void updateT6231(Connection connection, int F02)
        throws Throwable
    {
        logger.info("==========更新放款时间，标id：" + F02);
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S62.T6231 SET F12 = ? WHERE F01 = ?"))
        {
            pstmt.setTimestamp(1, getCurrentTimestamp(connection));
            pstmt.setInt(2, F02);
            pstmt.execute();
        }
    }
    
    protected T6238 selectT6238(Connection connection, int F01)
        throws SQLException
    {
        T6238 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04 FROM S62.T6238 WHERE T6238.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6238();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getBigDecimal(2);
                    record.F03 = resultSet.getBigDecimal(3);
                    record.F04 = resultSet.getBigDecimal(4);
                }
            }
        }
        return record;
    }
    
    @Override
    protected int getPTID(Connection connection)
        throws Throwable
    {
        try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S71.T7101 LIMIT 1"))
        {
            try (ResultSet resultSet = ps.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1);
                }
                else
                {
                    throw new LogicalException("平台账号不存在");
                }
            }
        }
    }
    
    protected T6240 selectT6240(Connection connection, int F01)
        throws SQLException
    {
        T6240 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S62.T6240 WHERE T6240.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6240();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getBigDecimal(7);
                }
            }
        }
        return record;
    }
    
    @Override
    public void updateOrderStatus(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6501 SET F03 = ? WHERE F01 = ?"))
            {
                ps.setString(1, T6501_F03.SB.name());
                ps.setInt(2, ordId);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public String addBidInfoUrI(AddBidInfoCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "AddBidInfo";
        String MerCustId = merCustId;
        String ProId = cond.proId();
        String BorrCustId = cond.borrCustId();
        String BorrTotAmt = cond.borrTotAmt();
        String YearRate = cond.yearRate();
        String RetType = cond.retType();
        String BidStartDate = cond.bidStartDate();
        String BidEndDate = cond.bidEndDate();
        String RetAmt = cond.retAmt();
        String RetDate = cond.retDate();
        String GuarCompId = cond.guarCompId();
        String GuarAmt = cond.guarAmt();
        String ProArea = cond.proArea();
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_ADD_BID);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(ProId);
        params.add(BorrCustId);
        params.add(BorrTotAmt);
        params.add(YearRate);
        params.add(RetType);
        params.add(BidStartDate);
        params.add(BidEndDate);
        params.add(RetAmt);
        params.add(RetDate);
        params.add(GuarCompId);
        params.add(GuarAmt);
        params.add(ProArea);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("ProId", ProId);
        map.put("BorrCustId", BorrCustId);
        map.put("BorrTotAmt", BorrTotAmt);
        map.put("YearRate", YearRate);
        map.put("RetType", RetType);
        map.put("BidStartDate", BidStartDate);
        map.put("BidEndDate", BidEndDate);
        map.put("RetAmt", RetAmt);
        map.put("RetDate", RetDate);
        map.put("GuarCompId", GuarCompId);
        map.put("ProArea", ProArea);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public AddBidInfoEntity addBidInfoDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        AddBidInfoEntity entity = new AddBidInfoEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.respCode = request.getParameter("RespCode");
        
        entity.proId = request.getParameter("ProId");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.proId);
        params.add(entity.bgRetUrl);
        
        String str = forEncryptionStr(params);
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public void checkBidInfo(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            // 订单详情
            T6504 t6504 = selectT6504(connection, orderId);
            if (t6504 == null)
            {
                throw new LogicalException("订单明细记录不存在");
            }
            // 标的详情,锁定标
            T6230 t6230 = selectT6230(connection, t6504.F03);
            T6231 t6231 = selectT6231(t6504.F03);
            if (t6230 == null)
            {
                throw new LogicalException("标记录不存在");
            }
            if (T6230_F28.S.equals(t6230.xsb) && (getXsbCount(t6504.F02) > 0 || getZqzrCount(t6504.F02) > 0))
            {
                throw new LogicalException("感谢您的支持！<br/>此标为新手标，只有未成功投资过并且没有购买过债权的新用户才可以投资。");
            }
            boolean ajkt = BooleanParser.parse(configureProvider.getProperty(SystemVariable.BID_SFZJKT));
            if (!ajkt && t6504.F02 == t6230.F02)
            {
                throw new LogicalException("不可投本账号发的标");
            }
            //            if (t6230.F20 != T6230_F20.TBZ)
            //            {
            //                throw new LogicalException("不是投资中状态,不能投资");
            //            }
            if (t6504.F04.compareTo(t6230.F07) > 0)
            {
                throw new LogicalException("投资金额大于可投金额");
            }
            // 校验最低起投金额
            //BigDecimal min = BigDecimalParser.parse(configureProvider.getProperty(SystemVariable.MIN_BIDING_AMOUNT));
            BigDecimal min = t6231.F25;
            if (t6504.F04.compareTo(min) < 0)
            {
                throw new LogicalException("投资金额不能低于最低起投金额");
            }
            BigDecimal max = t6231.F26;
            if (t6504.F04.compareTo(max) > 0)
            {
                throw new LogicalException("投资金额不能大于最大投资金额");
            }
            t6230.F07 = t6230.F07.subtract(t6504.F04);
            if (t6230.F07.compareTo(BigDecimal.ZERO) > 0 && t6230.F07.compareTo(min) < 0)
            {
                throw new LogicalException("剩余可投金额不能低于最低起投金额");
            }
            int pid = getPTID(connection); // 平台用户id
            if (pid <= 0)
            {
                throw new LogicalException("平台账号不存在");
            }
        }
    }
    
    private T6504 selectT6504(Connection connection, int orderId)
        throws Throwable
    {
        T6504 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S65.T6504 WHERE T6504.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, orderId);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6504();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = T6504_F06.parse(resultSet.getString(6));
                    record.F07 = T6504_F07.parse(resultSet.getString(7));
                }
            }
        }
        return record;
    }
    
    /**
     * 根据F01查询标的扩展信息
     * @param f01
     * @return
     */
    private T6231 selectT6231(int F01)
        throws SQLException
    {
        T6231 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F25, F26 FROM S62.T6231 WHERE T6231.F01 = ? "))
            {
                pstmt.setInt(1, F01);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6231();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F05 = resultSet.getBigDecimal(5);
                        record.F06 = resultSet.getDate(6);
                        record.F07 = resultSet.getInt(7);
                        record.F08 = resultSet.getString(8);
                        record.F09 = resultSet.getString(9);
                        record.F10 = resultSet.getTimestamp(10);
                        record.F11 = resultSet.getTimestamp(11);
                        record.F12 = resultSet.getTimestamp(12);
                        record.F13 = resultSet.getTimestamp(13);
                        record.F14 = resultSet.getTimestamp(14);
                        record.F15 = resultSet.getTimestamp(15);
                        record.F16 = resultSet.getString(16);
                        record.F17 = resultSet.getDate(17);
                        record.F18 = resultSet.getDate(18);
                        record.F19 = T6231_F19.parse(resultSet.getString(19));
                        record.F20 = resultSet.getInt(20);
                        record.F21 = T6231_F21.parse(resultSet.getString(21));
                        record.F22 = resultSet.getInt(22);
                        record.F25 = resultSet.getBigDecimal(23);
                        record.F26 = resultSet.getBigDecimal(24);
                    }
                }
            }
            return record;
        }
    }
    
    @Override
    public void updateT6501(int ordId, T6501_F03 f03)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?  WHERE F01 = ?"))
            {
                ps.setString(1, f03.name());
                ps.setInt(2, ordId);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void updateOrderDZStatus(int ordId, T6501_F03 F03)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            if (F03 != null)
            {
                try (PreparedStatement ps =
                    connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F11 = ? WHERE F01 = ?"))
                {
                    ps.setString(1, F03.name());
                    ps.setString(2, T6501_F11.S.name());
                    ps.setInt(3, ordId);
                    ps.executeUpdate();
                }
            }
            else
            {
                T6501_F11 F11 = null;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F11 FROM S65.T6501  WHERE F01 = ?"))
                {
                    ps.setInt(1, ordId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            F11 = T6501_F11.parse(resultSet.getString(1));
                        }
                    }
                }
                //已对账
                if (T6501_F11.S == F11)
                {
                    return;
                }
                try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6501 SET F11 = ? WHERE F01 = ?"))
                {
                    ps.setString(1, T6501_F11.S.name());
                    ps.setInt(2, ordId);
                    ps.executeUpdate();
                }
            }
            
        }
    }
    
    @Override
    public void updatePreFailT6252(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6521 t6521 = selectT6521(connection, orderId);
            //更新还款记录状态
            //            updateT6252(connection, T6252_F09.WH, t6521.F07, t6521.F05, t6521.F04);
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S62.T6252 SET F09 = ? WHERE F05 = ? AND F06 = ? AND F11 = ? AND F09 = ?"))
            {
                pstmt.setString(1, T6252_F09.WH.name());
                pstmt.setInt(2, t6521.F07);
                pstmt.setInt(3, t6521.F05);
                pstmt.setInt(4, t6521.F04);
                pstmt.setString(5, T6252_F09.HKZ.name());
                pstmt.execute();
            }
        }
    }
    
    private T6521 selectT6521(Connection connection, int orderId)
        throws Throwable
    {
        T6521 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07,F08,F09 FROM S65.T6521 WHERE T6521.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, orderId);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6521();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getInt(7);
                    record.F08 = resultSet.getInt(8);
                    record.F09 = resultSet.getInt(9);
                }
            }
        }
        return record;
    }
    
    @Override
    public BigDecimal selectT6527(int F01, int F02)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT F04 FROM S65.T6527 WHERE T6527.F01 = ? AND T6527.F02 = ?  LIMIT 1"))
            {
                ps.setInt(1, F01);
                ps.setInt(2, F02);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getBigDecimal(1);
                    }
                }
            }
        }
        return null;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public String bidRewardHB(String orderId, BigDecimal amount)
        throws Throwable
    {
        
        BigDecimal hbAmt = BigDecimal.ZERO;
        // 判断是否有红包-并作查询
        if (!StringHelper.isEmpty(orderId))
        {
            hbAmt = selectT6527(IntegerParser.parse(orderId), serviceResource.getSession().getAccountId());
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new LogicalException("投标总金额低于低于该红包金额");
        }
        logger.info("订单：" + orderId + "投资金额=" + amount + " 红包=" + hbAmt);
        
        String vocher = null;
        if (hbAmt.compareTo(BigDecimal.ZERO) > 0)
        {
            vocher =
                "{\"Vocher\":{\"AcctId\":\"" + HuiFuConstants.AcctId.ACCT_ID + "\",\"VocherAmt\":\""
                    + hbAmt.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "\"}}";
        }
        
        return vocher;
    }
    
    @Override
    public List<T6501> queryUnFreezeOrder(int orderId)
        throws Throwable
    {
        List<T6501> list = null;
        try (Connection connection = getConnection())
        {
            int bidId = 0;
            int bidRecordId = 0;
            //查询放款订单的 标ID 与 投资记录ID
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F03, F04 FROM S65.T6505 WHERE T6505.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        bidId = resultSet.getInt(1);
                        bidRecordId = resultSet.getInt(2);
                    }
                }
            }
            //通过投标订单号查找解冻订单号
            T6501 record = null;
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT T6501.F01, T6501.F04 FROM S65.T6516 INNER JOIN S65.T6501 ON T6516.F01 = T6501.F01 WHERE T6516.F02 = ( SELECT T6504.F01 FROM S65.T6504 WHERE T6504.F03 = ? AND T6504.F05 = ? )"))
            {
                pstmt.setInt(1, bidId);
                pstmt.setInt(2, bidRecordId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    while (resultSet.next())
                    {
                        record = new T6501();
                        record.F01 = resultSet.getInt(1);
                        record.F04 = resultSet.getTimestamp(2);
                        if (list == null)
                        {
                            list = new ArrayList<T6501>();
                        }
                        list.add(record);
                    }
                }
            }
        }
        return list;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public void updateBadFailT6252(int orderId, int badclaimNum)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6529 t6529 = selectT6529(connection, orderId);
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S62.T6252 SET F09 = ? WHERE F05 = ? AND F06 = ? AND F11 = ?"))
            {
                pstmt.setString(1, T6252_F09.WH.name());
                pstmt.setInt(2, t6529.F07);
                pstmt.setInt(3, badclaimNum);
                pstmt.setInt(4, t6529.F05);
                pstmt.execute();
            }
        }
    }
    
    /** 
     * 查询不良债权转让订单
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    @Override
    public T6529 selectT6529(Connection con, int F01)
        throws SQLException
    {
        T6529 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S65.T6529 WHERE T6529.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, F01);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6529();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getInt(4);
                        record.F05 = resultSet.getInt(5);
                        record.F06 = resultSet.getBigDecimal(6);
                        record.F07 = resultSet.getInt(7);
                        record.F08 = T6529_F08.parse(resultSet.getString(8));
                    }
                }
            }
        }
        
        return record;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public void updateAdvanceFailT6252(int orderId, int advanceNum)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6514 t6514 = selectT6514(connection, orderId);
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S62.T6252 SET F09 = ? WHERE F05 = ? AND F06 = ? AND F11 = ?"))
            {
                pstmt.setString(1, T6252_F09.WH.name());
                pstmt.setInt(2, t6514.F06);
                pstmt.setInt(3, advanceNum);
                pstmt.setInt(4, t6514.F03);
                pstmt.execute();
            }
        }
    }
    
    /**
     * 查询垫付订单
     * <功能详细描述>
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    @Override
    public T6514 selectT6514(Connection con, int F01)
        throws SQLException
    {
        T6514 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S65.T6514 WHERE T6514.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, F01);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6514();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getInt(4);
                        record.F05 = resultSet.getBigDecimal(5);
                        record.F06 = resultSet.getInt(6);
                        record.F07 = T6514_F07.parse(resultSet.getString(7));
                    }
                }
            }
        }
        return record;
    }
    
    /**
     * 已投新手标数量 <功能详细描述>
     *
     * @param userId
     * @return
     * @throws Throwable
     */
    protected int getXsbCount(int userId)
        throws SQLException
    {
        try (Connection connection = getConnection())
        {
            int count = 0;
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT count(*) FROM S62.T6250  WHERE T6250.F03 = ? "))
            {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        count = rs.getInt(1);
                    }
                }
            }
            return count;
        }
    }
    
    /**
     * 获取某用户的债权转让数量
     *
     * @param userId
     * @return
     * @throws Throwable
     */
    protected int getZqzrCount(int userId)
        throws SQLException
    {
        try (Connection connection = getConnection())
        {
            int count = 0;
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT count(*) FROM S62.T6262  WHERE T6262.F03 = ? "))
            {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        count = rs.getInt(1);
                    }
                }
            }
            return count;
        }
    }
    
    @Override
    public Map<String, String> queryBidStatus(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            Map<String, String> params = null;
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT F01, F20 FROM S62.T6230 WHERE T6230.F01 = (SELECT T6505.F03 FROM S65.T6505 WHERE T6505.F01 = ?)"))
            {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        params = new HashMap<String, String>();
                        params.put("bidId", rs.getString(1));
                        params.put("status", rs.getString(2));
                        return params;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public BidCancleReturn cancel(int orderId)
        throws Throwable
    {
        if (orderId <= 0)
        {
            throw new LogicalException("投资订单不存在");
        }
        BidCancleReturn bidReturn = new BidCancleReturn();
        int consoleAccountId = serviceResource.getSession().getAccountId();
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                T6504 t6504 = selectT6504(orderId);
                T6250 t6250 = selectT6250(connection, t6504.F05);
                
                int cancleId = addOrder(connection, t6250, consoleAccountId);
                bidReturn.bidOrderIds = cancleId;
                
                T6286 t6286 = selectT6286(connection, t6504.F05);
                if (t6286 != null)
                {
                    int experOrderIds = addExperienceOrder(connection, t6286, consoleAccountId);
                    bidReturn.experOrderIds = String.valueOf(experOrderIds);
                }
                
                //sendLetter(serviceResource, t6230, connection, des);
                serviceResource.commit(connection);
                // sendMsg(connection, wlzh.F04, content);
                return bidReturn;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    private int addOrder(Connection connection, T6250 t6250, int consoleAccountId)
        throws Throwable
    {
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.BID_CANCEL.orderType();
        t6501.F03 = T6501_F03.DTJ;
        t6501.F04 = getCurrentTimestamp(connection);
        t6501.F07 = T6501_F07.HT;
        t6501.F08 = t6250.F03;
        t6501.F09 = consoleAccountId;
        t6501.F13 = t6250.F04;
        int orderId = insertT6501(connection, t6501);
        insertT6508(connection, orderId, t6250.F03, t6250.F01);
        
        //加息券投资取消
        cancelCoupon(connection, t6250, consoleAccountId, orderId);
        return orderId;
    }
    
    private void insertT6508(Connection connection, int F01, int F02, int F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6508 SET F01 = ?, F02 = ?, F03 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F02);
            pstmt.setInt(3, F03);
            pstmt.execute();
        }
    }
    
    /**
     * 加息券投资取消
     * @param connection
     * @param t6250
     * @param consoleAccountId
     * @param cancelOrderId
     * @throws Throwable
     */
    private void cancelCoupon(Connection connection, T6250 t6250, int consoleAccountId, int cancelOrderId)
        throws Throwable
    {
        //根据T6250投资记录ID查询 T6288加息券投资记录
        T6288 t6288 = getT6288(connection, t6250);
        if (t6288 == null)
        {
            return;
        }
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.BID_COUPON_CANCEL.orderType();
        t6501.F03 = T6501_F03.DTJ;
        t6501.F04 = getCurrentTimestamp(connection);
        t6501.F07 = T6501_F07.HT;
        t6501.F08 = t6288.F03;
        t6501.F09 = consoleAccountId;
        t6501.F13 = t6250.F04;
        int orderId = insertT6501(connection, t6501);
        //插入加息券投资取消订单
        insertT6526(connection, orderId, t6288.F03, t6288.F01, cancelOrderId);
    }
    
    /**
     * 根据T6250投资记录ID查询 T6288加息券投资记录
     * @param connection
     * @return
     * @throws SQLException
     */
    private T6288 getT6288(Connection connection, T6250 t6250)
        throws SQLException
    {
        T6288 t6288 = null;
        try (PreparedStatement pstm =
            connection.prepareStatement("SELECT T6288.F01,T6288.F02,T6288.F03,T6288.F04,T6288.F05,T6288.F06,T6288.F07,T6288.F08,T6288.F09,T6288.F10,T6288.F11 FROM S62.T6288 WHERE T6288.F01 = (SELECT T.F01 FROM S62.T6288 T WHERE T.F09 = ? ) FOR UPDATE "))
        {
            pstm.setInt(1, t6250.F01);
            try (ResultSet resultSet = pstm.executeQuery())
            {
                if (resultSet.next())
                {
                    t6288 = new T6288();
                    t6288.F01 = resultSet.getInt(1);
                    t6288.F02 = resultSet.getInt(2);
                    t6288.F03 = resultSet.getInt(3);
                    t6288.F04 = resultSet.getBigDecimal(4);
                    t6288.F05 = resultSet.getTimestamp(5);
                    t6288.F06 = T6288_F06.parse(resultSet.getString(6));
                    t6288.F07 = T6288_F07.parse(resultSet.getString(7));
                    t6288.F08 = T6288_F08.parse(resultSet.getString(8));
                    t6288.F09 = resultSet.getInt(9);
                    t6288.F10 = resultSet.getInt(10);
                    t6288.F11 = resultSet.getBigDecimal(11);
                    return t6288;
                }
            }
        }
        return t6288;
    }
    
    /**
     * 插入加息券投资取消订单
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    private void insertT6526(Connection connection, int F01, int F02, int F03, int F04)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6526 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F02);
            pstmt.setInt(3, F03);
            pstmt.setInt(4, F04);
            pstmt.execute();
        }
    }
    
    private T6250 selectT6250(Connection connection, int F02)
        throws SQLException
    {
        T6250 record = new T6250();
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S62.T6250 WHERE F01= ? AND F07 = ? AND F08 = ? FOR UPDATE"))
        {
            pstmt.setInt(1, F02);
            pstmt.setString(2, T6250_F07.F.name());
            pstmt.setString(3, T6250_F08.F.name());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getTimestamp(6);
                    record.F07 = T6250_F07.parse(resultSet.getString(7));
                    record.F08 = T6250_F08.parse(resultSet.getString(8));
                }
            }
        }
        return record;
    }
    
    /**
     * 查询体验金投资记录
     * <功能详细描述>
     * @param connection
     * @param F02
     * @return
     * @throws SQLException
     */
    private T6286 selectT6286(Connection connection, int F09)
        throws SQLException
    {
        T6286 record = new T6286();
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S62.T6286 WHERE F09 = ? AND F06 = ? AND F07 = ? FOR UPDATE"))
        {
            pstmt.setInt(1, F09);
            pstmt.setString(2, T6250_F07.F.name());
            pstmt.setString(3, T6250_F08.F.name());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                while (resultSet.next())
                {
                    
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getTimestamp(5);
                    record.F06 = T6286_F06.parse(resultSet.getString(6));
                    record.F07 = T6286_F07.parse(resultSet.getString(7));
                }
            }
        }
        return record;
    }
    
    /**
     * 添加体验金投资取消订单
     * <功能详细描述>
     * @param connection
     * @param t6286
     * @param consoleAccountId
     * @return
     * @throws Throwable
     */
    private int addExperienceOrder(Connection connection, T6286 t6286, int consoleAccountId)
        throws Throwable
    {
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.BID_EXPERIENCE_CANCEL.orderType();
        t6501.F03 = T6501_F03.DTJ;
        t6501.F04 = getCurrentTimestamp(connection);
        t6501.F07 = T6501_F07.HT;
        t6501.F08 = t6286.F03;
        t6501.F09 = consoleAccountId;
        t6501.F13 = t6286.F04;
        int orderId = insertT6501(connection, t6501);
        insertT6522(connection, orderId, t6286.F03, t6286.F01);
        return orderId;
    }
    
    private void insertT6522(Connection connection, int F01, int F02, int F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6522 SET F01 = ?, F02 = ?, F03 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F02);
            pstmt.setInt(3, F03);
            pstmt.execute();
        }
    }
    
}