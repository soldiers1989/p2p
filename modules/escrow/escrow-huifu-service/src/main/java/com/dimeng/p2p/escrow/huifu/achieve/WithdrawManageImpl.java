package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.config.Envionment;
import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.framework.service.query.ItemParser;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6114;
import com.dimeng.p2p.S61.entities.T6130;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.S61.enums.T6114_F08;
import com.dimeng.p2p.S61.enums.T6114_F10;
import com.dimeng.p2p.S61.enums.T6118_F02;
import com.dimeng.p2p.S61.enums.T6118_F03;
import com.dimeng.p2p.S61.enums.T6130_F09;
import com.dimeng.p2p.S62.enums.T6252_F09;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6503;
import com.dimeng.p2p.S65.entities.T6515;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.common.SMSUtils;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.WithdrawCond;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.CashAuditEntity;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawEntity;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.modules.account.console.service.entity.UserWithdrawals;
import com.dimeng.p2p.variables.defines.LetterVariable;
import com.dimeng.p2p.variables.defines.MsgVariavle;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.p2p.variables.defines.smses.SmsVaribles;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.DateTimeParser;

public class WithdrawManageImpl extends AbstractEscrowService implements WithdrawManage
{
    
    public WithdrawManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class WithdrawManageFaxtory implements ServiceFactory<WithdrawManage>
    {
        
        @Override
        public WithdrawManage newInstance(ServiceResource serviceResource)
        {
            return new WithdrawManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public String createWithdrawUri(WithdrawCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "Cash";
        String MerCustId = merCustId;
        String OrdId = cond.ordId();
        String UsrCustId = cond.usrCustId();
        String TransAmt = cond.transAmt();
        String ServFee = cond.servFee();
        String ServFeeAcctId = cond.servFeeAccId();
        String OpenAcctId = cond.openAcctId();
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_WITHDRAW);
        String MerPriv = base64(cond.merPriv());
        String ReqExt =
            "[{\"FeeObjFlag\":\"" + configureProvider.format(HuifuVariable.HF_FEE_OBJ_FLAG) + "\",\"FeeAcctId\":\""
                + HuiFuConstants.AcctId.ACCT_ID + "\",\"CashChl\":\"" + cond.merPriv() + "\"}]";
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(UsrCustId);
        params.add(TransAmt);
        params.add(ServFee);
        params.add(ServFeeAcctId);
        params.add(OpenAcctId);
        params.add(RetUrl);
        params.add(BgRetUrl);
        params.add(MerPriv);
        params.add(ReqExt);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("UsrCustId", UsrCustId);
        map.put("TransAmt", TransAmt);
        map.put("ServFee", ServFee);
        map.put("ServFeeAcctId", ServFeeAcctId);
        map.put("OpenAcctId", OpenAcctId);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        map.put("MerPriv", MerPriv);
        map.put("ReqExt", ReqExt);
        
        return concatUrl(map);
    }
    
    @Override
    public WithdrawEntity withdrawReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        WithdrawEntity entity = new WithdrawEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.feeAcctId = request.getParameter("FeeAcctId");
        entity.feeAmt = request.getParameter("FeeAmt");
        entity.feeCustId = request.getParameter("FeeCustId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.openAcctId = request.getParameter("OpenAcctId");
        entity.openBankId = request.getParameter("OpenBankId");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.servFee = request.getParameter("ServFee");
        entity.servFeeAcctId = request.getParameter("ServFeeAcctId");
        entity.transAmt = request.getParameter("TransAmt");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.realTransAmt = request.getParameter("RealTransAmt");
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        entity.respExt = urlDecoder(request.getParameter("RespExt"));
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.usrCustId);
        params.add(entity.transAmt);
        params.add(entity.openAcctId);
        params.add(entity.openBankId);
        params.add(entity.feeAmt);
        params.add(entity.feeCustId);
        params.add(entity.feeAcctId);
        params.add(entity.servFee);
        params.add(entity.servFeeAcctId);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        params.add(entity.respExt);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public String getBankCard(int id)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F07 FROM S61.T6114 WHERE T6114.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, id);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getString(1);
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public String getUserCustId()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?"))
            {
                ps.setInt(1, serviceResource.getSession().getAccountId());
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getString(1);
                    }
                }
            }
        }
        return null;
    }
    
    // 创建订单
    public int addOrder(Connection connection, UserWithdrawals userWithdrawals)
        throws Throwable
    {
        int orderId = 0;
        String status = "";
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT T.F01 ,T.F03 FROM  S65.T6501 T left join  S65.T6503 T1 on T.F01=T1.F01  WHERE  T1.F09=? order by T.F01 desc limit 1"))
        {
            pstmt.setInt(1, userWithdrawals.F01);
            
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    orderId = rs.getInt(1);
                    status = rs.getString(2);
                }
            }
            if (orderId > 0)
            {
                writeLog(connection, "操作日志放款审核订单已存在.......", "orderId: " + orderId + "  status:  " + status);
                return orderId;
            }
        }
        
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6501 SET F02 = ?,F03 = ?, F04 = ?, F07 = ?, F09 = ?, F08 = ? ",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, OrderType.WITHDRAW.orderType());
            pstmt.setString(2, T6501_F03.DTJ.name());
            pstmt.setTimestamp(3, getCurrentTimestamp(connection));
            pstmt.setString(4, T6501_F07.HT.name());
            pstmt.setInt(5, serviceResource.getSession().getAccountId());
            pstmt.setInt(6, userWithdrawals.F02);
            pstmt.execute();
            try (ResultSet resultSet = pstmt.getGeneratedKeys();)
            {
                if (resultSet.next())
                {
                    orderId = resultSet.getInt(1);
                }
            }
        }
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6503 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?,F09 = ?"))
        {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, userWithdrawals.F02);
            pstmt.setBigDecimal(3, userWithdrawals.F04);
            pstmt.setBigDecimal(4, userWithdrawals.F06);
            pstmt.setBigDecimal(5, userWithdrawals.F07);
            pstmt.setString(6, StringHelper.decode(userWithdrawals.bankId));
            pstmt.setInt(7, userWithdrawals.F01);
            pstmt.execute();
        }
        return orderId;
    }
    
    private T6101 selectT6101(Connection connection, int F02, T6101_F03 F03)
        throws SQLException
    {
        T6101 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S61.T6101 WHERE T6101.F02 = ? AND T6101.F03 = ? FOR UPDATE"))
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
    
    protected void insertT6503(Connection connection, T6503 entity)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6503 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?"))
        {
            pstmt.setInt(1, entity.F01);
            pstmt.setInt(2, entity.F02);
            pstmt.setBigDecimal(3, entity.F03);
            pstmt.setBigDecimal(4, entity.F04);
            pstmt.setBigDecimal(5, entity.F05);
            pstmt.setString(6, entity.F06);
            pstmt.setInt(7, entity.F07);
            pstmt.execute();
        }
    }
    
    @Override
    public void apply(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT F01 FROM S65.T6501 WHERE F03 = ? AND F01 = ? FOR UPDATE"))
            {
                ps.setString(1, T6501_F03.DTJ.name());
                ps.setInt(2, orderId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (!resultSet.next())
                    {
                        return;
                    }
                }
            }
            
            T6503 t6503 = selectT6503(connection, orderId);
            int pid = getPTID(connection); // 平台用户id
            
            //            //查询银行卡
            //            T6114 t6114 = selectT6114(connection, t6503.F02, StringHelper.encode(t6503.F06));
            //            //新增提现申请记录
            //            T6130 t6130 = new T6130();
            //            t6130.F02 = t6503.F02;
            //            t6130.F03 = t6114.F01;
            //            t6130.F04 = t6503.F03;
            //            t6130.F06 = t6503.F05;
            //            t6130.F07 = t6503.F05;
            //            t6130.F09 = T6130_F09.DSH;
            //            int aId = insertT6130(connection, t6130);
            
            //            //更新提现订单关联
            //            try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S65.T6503 SET F09 = ? WHERE F01 = ?"))
            //            {
            //                pstmt.setInt(1, aId);
            //                pstmt.setInt(2, orderId);
            //                pstmt.execute();
            //            }
            
            try
            {
                serviceResource.openTransactions(connection);
                //需冻结的金额
                BigDecimal amt = t6503.F11.add(t6503.F05);
                
                //用户往来账户
                T6101 yhwlT6101 = selectT6101(connection, t6503.F02, T6101_F03.WLZH);
                if (yhwlT6101 == null)
                {
                    throw new LogicalException("用户往来账户不存在");
                }
                //用户锁定账户
                T6101 sT6101 = selectT6101(connection, t6503.F02, T6101_F03.SDZH);
                if (sT6101 == null)
                {
                    throw new LogicalException("用户锁定账户不存在");
                }
                
                //平台往来账户
                T6101 ptwT6101 = selectT6101(connection, pid, T6101_F03.WLZH);
                if (ptwT6101 == null)
                {
                    throw new LogicalException("平台往来账户不存在");
                }
                //平台锁定账户
                T6101 ptsT6101 = selectT6101(connection, pid, T6101_F03.SDZH);
                if (ptsT6101 == null)
                {
                    throw new LogicalException("平台锁定账户不存在");
                }
                
                if (yhwlT6101.F06.compareTo(amt) < 0)
                {
                    throw new LogicalException("用户账户可用余额不足！");
                }
                {
                    {
                        // 往来账户流水
                        T6102 t6102 = new T6102();
                        t6102.F02 = yhwlT6101.F01;
                        t6102.F03 = FeeCode.TX;
                        t6102.F04 = sT6101.F01;
                        t6102.F07 = t6503.F11;
                        yhwlT6101.F06 = yhwlT6101.F06.subtract(t6503.F11);
                        t6102.F08 = yhwlT6101.F06;
                        t6102.F09 = "提现金额";
                        insertT6102(connection, t6102);
                    }
                    if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
                    {
                        // 往来账户流水
                        T6102 t6102 = new T6102();
                        t6102.F02 = yhwlT6101.F01;
                        t6102.F03 = FeeCode.TX_SXF;
                        t6102.F04 = sT6101.F01;
                        t6102.F07 = t6503.F05;
                        yhwlT6101.F06 = yhwlT6101.F06.subtract(t6503.F05);
                        t6102.F08 = yhwlT6101.F06;
                        t6102.F09 = "提现手续费";
                        insertT6102(connection, t6102);
                        
                    }
                    updateT6101(connection, yhwlT6101.F06, yhwlT6101.F01);
                }
                
                {
                    {
                        // 锁定账户流水
                        T6102 t6102 = new T6102();
                        t6102.F02 = sT6101.F01;
                        t6102.F03 = FeeCode.TX;
                        t6102.F04 = yhwlT6101.F01;
                        t6102.F06 = t6503.F11;
                        sT6101.F06 = sT6101.F06.add(t6503.F11);
                        t6102.F08 = sT6101.F06;
                        t6102.F09 = "提现金额";
                        insertT6102(connection, t6102);
                    }
                    if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
                    {
                        // 锁定账户流水
                        T6102 t6102 = new T6102();
                        t6102.F02 = sT6101.F01;
                        t6102.F03 = FeeCode.TX_SXF;
                        t6102.F04 = yhwlT6101.F01;
                        t6102.F06 = t6503.F05;
                        sT6101.F06 = sT6101.F06.add(t6503.F05);
                        t6102.F08 = sT6101.F06;
                        t6102.F09 = "提现手续费";
                        insertT6102(connection, t6102);
                        
                    }
                    updateT6101(connection, sT6101.F06, sT6101.F01);
                }
                
                Timestamp times = getCurrentTimestamp(connection);
                
                // 生成冻结订单
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.FREEZE.orderType();
                t6501.F03 = T6501_F03.DQR;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.YH;
                t6501.F09 = getPTID(connection);
                t6501.F13 = amt;
                t6501.F01 = insertT6501(connection, t6501);
                insertT6515(connection, t6501.F01, t6503.F01, amt);
                
                //提交事物
                serviceResource.commit(connection);
                
                //调用冻结接口
                String Version = HuiFuConstants.Version.VERSION_10;
                String CmdId = "UsrFreezeBg";
                String MerCustId = merCustId;
                String UsrCustId = userCustId(t6503.F02);
                String SubAcctType = "MERDT";
                String SubAcctId = HuiFuConstants.AcctId.ACCT_ID;
                String OrdId = String.valueOf(t6501.F01);
                String OrdDate = DateParser.format(new Date(), "yyyyMMdd");
                String TransAmt = amt.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                String BgRetUrl = configureProvider.format(HuifuVariable.HF_WITHWRAW_FREEZE);
                
                List<String> params = new ArrayList<>();
                params.add(Version);
                params.add(CmdId);
                params.add(MerCustId);
                params.add(UsrCustId);
                params.add(SubAcctType);
                params.add(SubAcctId);
                params.add(OrdId);
                params.add(OrdDate);
                params.add(TransAmt);
                params.add(BgRetUrl);
                
                String ChkValue = chkValue(params);
                
                Map<String, String> map = new HashMap<>();
                map.put("Version", Version);
                map.put("CmdId", CmdId);
                map.put("MerCustId", MerCustId);
                map.put("UsrCustId", UsrCustId);
                map.put("SubAcctType", SubAcctType);
                map.put("SubAcctId", SubAcctId);
                map.put("OrdId", OrdId);
                map.put("OrdDate", OrdDate);
                map.put("TransAmt", TransAmt);
                map.put("BgRetUrl", BgRetUrl);
                map.put("ChkValue", ChkValue);
                
                logger.info(String.format("取现冻结订单号：%s 请求参数：%s", t6501.F01, map.toString()));
                
                doPost(map);
                
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
    
    private T6114 selectT6114(Connection connection, int F02, String F07)
        throws SQLException
    {
        T6114 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S61.T6114 WHERE T6114.F02 = ? AND T6114.F07 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F02);
            pstmt.setString(2, F07);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6114();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getString(7);
                    record.F08 = T6114_F08.parse(resultSet.getString(8));
                    record.F09 = resultSet.getTimestamp(9);
                    record.F10 = T6114_F10.parse(resultSet.getString(10));
                }
            }
        }
        return record;
    }
    
    private T6503 selectT6503(Connection connection, int F01)
        throws SQLException
    {
        T6503 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S65.T6503 WHERE T6503.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6503();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getBigDecimal(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getInt(7);
                    record.F08 = resultSet.getString(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = resultSet.getBigDecimal(10);
                    record.F11 = resultSet.getBigDecimal(11);
                }
            }
        }
        return record;
    }
    
    private int insertT6130(Connection connection, T6130 entity, int userId)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6130 SET F02 = ?, F03 = ?, F04 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?, F17 = ?, F18 = ? ",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, entity.F02);
            pstmt.setInt(2, entity.F03);
            pstmt.setBigDecimal(3, entity.F04);
            pstmt.setBigDecimal(4, entity.F06);
            pstmt.setBigDecimal(5, entity.F07);
            pstmt.setTimestamp(6, getCurrentTimestamp(connection));
            pstmt.setString(7, entity.F09.name());
            Map<String, Object> result = getEmpInfo(userId, connection);
            pstmt.setString(8, (String)result.get("empNum"));
            pstmt.setString(9, (String)result.get("empStatus"));
            pstmt.execute();
            try (ResultSet resultSet = pstmt.getGeneratedKeys();)
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        }
    }
    
    @Override
    public void cancle(int id)
        throws Throwable
    {
        
        try (Connection connection = getConnection())
        {
            T6503 t6503 = selectOrder(connection, id);
            // 取消提现
            String Version = HuiFuConstants.Version.VERSION_10;
            String CmdId = "CashAudit";
            String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
            String OrdId = String.valueOf(t6503.F01);
            String UsrCustId = usrCustId(connection, t6503.F02);
            String TransAmt = t6503.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String AuditFlag = "R";
            String BgRetUrl = configureProvider.format(HuifuVariable.HF_CASHAUDIT);
            
            List<String> params = new ArrayList<>();
            params.add(Version);
            params.add(CmdId);
            params.add(MerCustId);
            params.add(OrdId);
            params.add(UsrCustId);
            params.add(TransAmt);
            params.add(AuditFlag);
            params.add(BgRetUrl);
            String ChkValue = chkValue(params);
            
            Map<String, String> map = new HashMap<>();
            map.put("Version", Version);
            map.put("CmdId", CmdId);
            map.put("MerCustId", MerCustId);
            map.put("OrdId", OrdId);
            map.put("UsrCustId", UsrCustId);
            map.put("TransAmt", TransAmt);
            map.put("AuditFlag", AuditFlag);
            map.put("BgRetUrl", BgRetUrl);
            map.put("ChkValue", ChkValue);
            
            logger.info(String.format("汇付 - 取现审核不通过订单号%s,请求参数%s", OrdId, map));
            
            String result = doPost(map);
            
            logger.info(String.format("汇付 - 取现审核不通过订单号%s,回调参数%s", OrdId, result));
            
            Map<String, String> json = jsonParse(result);
            
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(json.get("RespCode")))
            {
                try (PreparedStatement pstmt =
                    connection.prepareStatement("SELECT F01 FROM S61.T6130 WHERE F09 = ? AND F01 = ?"))
                {
                    pstmt.setString(1, T6130_F09.TXSB.name());
                    pstmt.setInt(2, t6503.F09);
                    try (ResultSet resultSet = pstmt.executeQuery())
                    {
                        if (!resultSet.next())
                        {
                            return;
                        }
                    }
                }
                try (PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE S61.T6130 SET F09 = ? WHERE F01 = ?"))
                {
                    pstmt.setString(1, T6130_F09.TXSB.name());
                    pstmt.setInt(2, t6503.F09);
                    pstmt.execute();
                }
            }
        }
    }
    
    /**
     * 根据取现申请ID查找提现订单
     * <功能详细描述>
     * @param connection
     * @param F09
     * @return
     * @throws SQLException
     */
    private T6503 selectOrder(Connection connection, int F09)
        throws SQLException
    {
        T6503 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S65.T6503 WHERE T6503.F09 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F09);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6503();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getBigDecimal(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getInt(7);
                    record.F08 = resultSet.getString(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = resultSet.getBigDecimal(10);
                    record.F11 = resultSet.getBigDecimal(11);
                }
            }
        }
        return record;
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
    public CashAuditEntity cashAuditRetDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        CashAuditEntity entity = new CashAuditEntity();
        entity.auditFlag = request.getParameter("AuditFlag");
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        if (StringHelper.isEmpty(entity.cmdId))
        {
            return null;
        }
        entity.feeAcctId = request.getParameter("FeeAcctId");
        entity.feeAmt = request.getParameter("FeeAmt");
        entity.feeCustId = request.getParameter("FeeCustId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.openAcctId = request.getParameter("OpenAcctId");
        entity.openBankId = request.getParameter("OpenBankId");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.transAmt = request.getParameter("TransAmt");
        entity.usrCustId = request.getParameter("UsrCustId");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.usrCustId);
        params.add(entity.transAmt);
        params.add(entity.openAcctId);
        params.add(entity.openBankId);
        params.add(entity.auditFlag);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public CashAuditEntity cashAuditRetAsynDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        CashAuditEntity entity = new CashAuditEntity();
        entity.respType = request.getParameter("RespType");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordId = request.getParameter("OrdId");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.transAmt = request.getParameter("TransAmt");
        entity.realTransAmt = request.getParameter("RealTransAmt");
        entity.openAcctId = request.getParameter("OpenAcctId");
        entity.openBankId = request.getParameter("OpenBankId");
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> params = new ArrayList<>();
        params.add(entity.respType);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.usrCustId);
        params.add(entity.transAmt);
        params.add(entity.openAcctId);
        params.add(entity.openBankId);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public String refuse(int id)
        throws Throwable
    {
        String resultCode = "";
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                
                T6503 t6503 = selectOrder(connection, id);
                
                Timestamp times = getCurrentTimestamp(connection);
                
                //查询冻结订单
                T6515 t6515 = selectT6515(connection, t6503.F01);
                if (null == t6515)
                {
                    throw new LogicalException("冻结订单不存在");
                }
                T6501 djOrder = selectT6501(connection, t6515.F01);
                if (null == djOrder)
                {
                    throw new LogicalException("订单不存在");
                }
                
                // 增加解冻订单
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.UNFREEZE.orderType();
                t6501.F03 = T6501_F03.DQR;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.YH;
                t6501.F08 = djOrder.F08;
                t6501.F09 = getPTID(connection);
                t6501.F13 = t6503.F11.add(t6503.F05);
                t6501.F01 = insertT6501(connection, t6501);
                
                insertT6516(connection, t6501.F01, t6503.F01, t6515.F03);
                //提交事务
                serviceResource.commit(connection);
                
                //调用解冻接口
                String Version = HuiFuConstants.Version.VERSION_10;
                String CmdId = "UsrUnFreeze";
                String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
                String OrdId = String.valueOf(t6501.F01);
                String OrdDate = DateParser.format(new Date(), "yyyyMMdd");
                String TrxId = djOrder.F10;
                String BgRetUrl = configureProvider.format(HuifuVariable.HF_UNFREEZE);
                
                List<String> pps = new ArrayList<>();
                pps.add(Version);
                pps.add(CmdId);
                pps.add(MerCustId);
                pps.add(OrdId);
                pps.add(OrdDate);
                pps.add(TrxId);
                pps.add(BgRetUrl);
                
                String ChkValue = chkValue(pps);
                
                Map<String, String> map = new HashMap<>();
                map.put("Version", Version);
                map.put("CmdId", CmdId);
                map.put("MerCustId", MerCustId);
                map.put("OrdId", OrdId);
                map.put("OrdDate", OrdDate);
                map.put("TrxId", TrxId);
                map.put("BgRetUrl", BgRetUrl);
                map.put("ChkValue", ChkValue);
                
                logger.info(String.format("取现审核不通过解冻订单号：%s 请求参数：%s", t6501.F01, map.toString()));
                
                String result = doPost(map);
                
                logger.info(String.format("取现审核不通过解冻订单号：%s 返回参数：%s", t6501.F01, result));
                
                Map<String, String> paramsRet = jsonParse(result);
                
                resultCode = paramsRet.get("RespCode");
                
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
        return resultCode;
    }
    
    private void insertT6516(Connection connection, int F01, int F02, BigDecimal F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6516 SET F01 = ?, F02 = ?, F03 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F02);
            pstmt.setBigDecimal(3, F03);
            pstmt.execute();
        }
    }
    
    private T6515 selectT6515(Connection connection, int F02)
        throws SQLException
    {
        T6515 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03 FROM S65.T6515 WHERE T6515.F02 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F02);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6515();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getBigDecimal(3);
                }
            }
        }
        return record;
    }
    
    @Override
    public int[] orderIds(T6130_F09 status, String checkReason, int... ids)
        throws Throwable
    {
        if (ids == null || ids.length == 0)
        {
            return new int[0];
        }
        int[] idArr = new int[ids.length];
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                
                for (int i = 0; i < ids.length; i++)
                {
                    T6110 t6110 = getUserInfo(ids[i], connection);
                    
                    if (T6110_F07.HMD == t6110.F07)
                    {
                        throw new LogicalException("用户:" + t6110.F02 + "已经被拉黑，不能进行提现！");
                    }
                    UserWithdrawals userWithdrawals = get(ids[i]);
                    if (userWithdrawals == null)
                    {
                        throw new LogicalException("提现申请记录不存在");
                    }
                    if (userWithdrawals.F09 != T6130_F09.DFK)
                    {
                        throw new LogicalException("提现申请不是待放款状态,不能进行放款操作");
                    }
                    int F09 = 0;
                    try (PreparedStatement pstmt =
                        connection.prepareStatement("SELECT F01, F09 FROM S65.T6503 WHERE T6503.F09 = ? LIMIT 1"))
                    {
                        pstmt.setInt(1, ids[i]);
                        try (ResultSet resultSet = pstmt.executeQuery())
                        {
                            if (resultSet.next())
                            {
                                idArr[i] = resultSet.getInt(1);
                                F09 = resultSet.getInt(2);
                            }
                        }
                    }
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S65.T6501 SET F03 = ? WHERE F01 = ?"))
                    {
                        ps.setString(1, T6501_F03.DTJ.name());
                        ps.setInt(2, idArr[i]);
                        ps.executeUpdate();
                    }
                    try (PreparedStatement pstmt =
                        connection.prepareStatement("UPDATE S61.T6130 SET F09 = ?, F13 = ?, F14 = ?, F15 = ? WHERE F01 = ?"))
                    {
                        pstmt.setString(1, status.name());
                        pstmt.setInt(2, serviceResource.getSession().getAccountId());
                        pstmt.setTimestamp(3, getCurrentTimestamp(connection));
                        pstmt.setString(4, checkReason);
                        pstmt.setInt(5, F09);
                        pstmt.execute();
                    }
                }
                if (status == T6130_F09.YFK)
                {
                    writeLog(connection, "操作日志", "提现放款通过");
                }
                else
                {
                    writeLog(connection, "操作日志", String.format("提现放款不通过,原因:%s", checkReason));
                }
                
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
            
            return idArr;
        }
    }
    
    protected T6110 getUserInfo(int id, Connection conn)
        throws Throwable
    {
        
        //锁定提现表(如果不锁定在几个页面同时点放款的时候就会出现问题)
        T6130 record = null;
        try (PreparedStatement pstmt =
            conn.prepareStatement("SELECT  F01, F02  FROM S61.T6130 WHERE  F01 = ?  FOR UPDATE "))
        {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6130();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                }
            }
        }
        
        T6110 t6110 = new T6110();
        try (PreparedStatement ps =
            conn.prepareStatement("SELECT T6110.F02,T6110.F07 FROM S61.T6110 WHERE T6110.F01= ?  "))
        {
            ps.setInt(1, record.F02);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    t6110.F02 = rs.getString(1);
                    t6110.F07 = T6110_F07.parse(rs.getString(2));
                }
            }
        }
        return t6110;
    }
    
    @Override
    public UserWithdrawals get(int id)
        throws Throwable
    {
        StringBuilder sql =
            new StringBuilder(
                "SELECT T6130.F01 AS F01, T6130.F02 AS F02, T6130.F03 AS F03, T6130.F04 AS F04, T6130.F06 AS F05, T6130.F07 AS F06, T6130.F08 AS F07, T6130.F09 AS F08, T6130.F10 AS F09, T6130.F11 AS F10, T6130.F12 AS F11, T6130.F13 AS F12, T6130.F14 AS F13, T6130.F15 AS F14, T6110.F02 AS F15, T6110.F06 AS F16,T6114.F04 AS F17,T5020.F02 AS F18,T6114.F07 AS F19,T6114.F05 AS F20,T6130.F16 AS F21, T6114.F11 AS F22, T6114.F12 AS F23, T5020.F04 AS F24 FROM S61.T6130 INNER JOIN S61.T6110 ON T6130.F02 = T6110.F01 INNER JOIN S61.T6114 ON T6130.F03=T6114.F01 INNER JOIN S50.T5020 ON T6114.F03=T5020.F01 WHERE T6130.F01=?");
        try (Connection connection = getConnection())
        {
            UserWithdrawals userWithdrawals = select(connection, new ItemParser<UserWithdrawals>()
            {
                @Override
                public UserWithdrawals parse(ResultSet resultSet)
                    throws SQLException
                {
                    UserWithdrawals record = new UserWithdrawals();
                    if (resultSet.next())
                    {
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F06 = resultSet.getBigDecimal(5);
                        record.F07 = resultSet.getBigDecimal(6);
                        record.F08 = resultSet.getTimestamp(7);
                        record.F09 = T6130_F09.parse(resultSet.getString(8));
                        record.F10 = resultSet.getInt(9);
                        record.F11 = resultSet.getTimestamp(10);
                        record.F12 = resultSet.getString(11);
                        record.F13 = resultSet.getInt(12);
                        record.F14 = resultSet.getTimestamp(13);
                        record.F15 = resultSet.getString(14);
                        record.userName = resultSet.getString(15);
                        record.userType = T6110_F06.parse(resultSet.getString(16));
                        record.location = getRegion(resultSet.getInt(17), connection);
                        record.extractionBank = resultSet.getString(18);
                        record.bankId = resultSet.getString(19);
                        record.subbranch = resultSet.getString(20);
                        record.shName = getName(connection, record.F10);
                        record.txName = getName(connection, record.F13);
                        record.realName =
                            StringHelper.isEmpty(resultSet.getString(22)) ? getUserName(record.F02,
                                record.userType.name(),
                                connection) : resultSet.getString(22); // 开户名
                        // (兴业银行)银行卡类型0-储蓄卡;1-信用卡;2-企业账户 & 人行体系支付号
                        record.bankCardType = resultSet.getInt(23);
                        record.bankSystemCode = resultSet.getString(24);
                    }
                    return record;
                }
            },
                sql.toString(),
                id);
            // 注释 by chenbin@taojinde.com 2015-7-8 产品邮件定的统一显示方式
            /*
             * if (userWithdrawals != null) { if (userWithdrawals.userType ==
             * T6110_F06.ZRR) { try (Connection connection = getConnection()) {
             * try (PreparedStatement ps = connection
             * .prepareStatement("SELECT F02 FROM S61.T6141 WHERE F01=?")) {
             * ps.setInt(1, userWithdrawals.F02); try (ResultSet rs =
             * ps.executeQuery()) { if (rs.next()) { userWithdrawals.realName =
             * rs.getString(1); } } } } } else if (userWithdrawals.userType ==
             * T6110_F06.FZRR) { try (Connection connection = getConnection()) {
             * try (PreparedStatement ps = connection
             * .prepareStatement("SELECT F04 FROM S61.T6161 WHERE F01=?")) {
             * ps.setInt(1, userWithdrawals.F02); try (ResultSet rs =
             * ps.executeQuery()) { if (rs.next()) { userWithdrawals.realName =
             * rs.getString(1); } } } } } }
             */
            return userWithdrawals;
        }
    }
    
    /**
     * 查询区域名称
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    protected String getRegion(int id, Connection connection)
        throws SQLException
    {
        if (id <= 0)
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F06,F07,F08 FROM S50.T5019 WHERE F01=?"))
            {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        sb.append(rs.getString(1));
                        sb.append(",");
                        sb.append(rs.getString(2));
                        sb.append(",");
                        sb.append(rs.getString(3));
                    }
                }
            }
        }
        return sb.toString();
    }
    
    protected String getName(Connection connection, int id)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F02 FROM S71.T7110 WHERE T7110.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getString(1);
                }
            }
        }
        return "";
    }
    
    protected String getUserName(int id, String userType, Connection connection)
        throws SQLException
    {
        
        try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S61.T6141 WHERE F01=?"))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getString(1);
                }
            }
        }
        return "";
    }
    
    @SuppressWarnings("unused")
    @Override
    public void trade(int... ids)
        throws Throwable
    {
        if (ids == null || ids.length <= 0)
        {
            return;
        }
        for (int id : ids)
        {
            if (id <= 0)
            {
                continue;
            }
            try (Connection connection = getConnection())
            {
                T6503 t6503 = selectOrder(connection, id);
                
                T6501 t6501 = selectT6501(connection, t6503.F01);
                
                if (null == t6503)
                {
                    throw new LogicalException("提现订单不存在");
                }
                
                T6101 wlzh = selectT6101(connection, t6503.F02, T6101_F03.WLZH);
                if (wlzh == null)
                {
                    throw new LogicalException("用户往来账户不存在");
                }
                
                T6101 sdzh = selectT6101(connection, t6503.F02, T6101_F03.SDZH);
                if (sdzh == null)
                {
                    throw new LogicalException("用户锁定账户不存在");
                }
                
                ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
                
                // 平台用户id
                int pid = getPTID(connection);
                T6101 ptwT6101 = selectT6101(connection, pid, T6101_F03.WLZH);
                if (ptwT6101 == null)
                {
                    throw new LogicalException("平台往来账户不存在");
                }
                
                {
                    if (sdzh.F06.compareTo(t6503.F11.add(t6503.F05)) < 0)
                    {
                        throw new LogicalException("用户锁定资金账户余额不足");
                    }
                    {
                        T6102 t6102 = new T6102();
                        t6102.F02 = sdzh.F01;
                        t6102.F03 = FeeCode.TXSB;
                        t6102.F04 = wlzh.F01;
                        t6102.F07 = t6503.F11;
                        sdzh.F06 = sdzh.F06.subtract(t6503.F11);
                        t6102.F08 = sdzh.F06;
                        t6102.F09 = "提现撤销,本金返还";
                        insertT6102(connection, t6102);
                    }
                    {
                        T6102 t6102 = new T6102();
                        t6102.F02 = sdzh.F01;
                        t6102.F03 = FeeCode.TXSB_SXF;
                        t6102.F04 = wlzh.F01;
                        t6102.F07 = t6503.F05;
                        sdzh.F06 = sdzh.F06.subtract(t6503.F05);
                        t6102.F08 = sdzh.F06;
                        t6102.F09 = "提现撤销,手续费返还";
                        insertT6102(connection, t6102);
                    }
                    // 扣减锁定账户资金
                    updateT6101(connection, sdzh.F06, sdzh.F01);
                }
                {
                    {
                        T6102 t6102 = new T6102();
                        t6102.F02 = wlzh.F01;
                        t6102.F03 = FeeCode.TXSB;
                        t6102.F04 = sdzh.F01;
                        t6102.F06 = t6503.F11;
                        wlzh.F06 = wlzh.F06.add(t6503.F11);
                        t6102.F08 = wlzh.F06;
                        t6102.F09 = "提现撤销,本金返还";
                        insertT6102(connection, t6102);
                    }
                    {
                        T6102 t6102 = new T6102();
                        t6102.F02 = wlzh.F01;
                        t6102.F03 = FeeCode.TXSB_SXF;
                        t6102.F04 = sdzh.F01;
                        t6102.F06 = t6503.F05;
                        wlzh.F06 = wlzh.F06.add(t6503.F05);
                        t6102.F08 = wlzh.F06;
                        t6102.F09 = "提现撤销,手续费返还";
                        insertT6102(connection, t6102);
                    }
                    // 增加往来账户资金
                    updateT6101(connection, wlzh.F06, wlzh.F01);
                }
                
                Envionment envionment = configureProvider.createEnvionment();
                envionment.set("name", wlzh.F05);
                envionment.set("datetime", DateTimeParser.format(t6501.F04));
                BigDecimal je = t6503.F11.add(t6503.F05);
                envionment.set("amount", je.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                String content = configureProvider.format(LetterVariable.TX_SB, envionment);
                sendLetter(connection, t6501.F08, "提现失败", content);
                T6110 t6110 = selectT6110(connection, t6503.F02);
                String isUseYtx = configureProvider.getProperty(SmsVaribles.SMS_IS_USE_YTX);
                if ("1".equals(isUseYtx))
                {
                    SMSUtils smsUtils = new SMSUtils(configureProvider);
                    int type = smsUtils.getTempleId(MsgVariavle.TX_SB.getDescription());
                    sendMsg(connection,
                        t6110.F04,
                        smsUtils.getSendContent(envionment.get("name"),
                            envionment.get("datetime"),
                            envionment.get("amount")),
                        type);
                    
                }
                else
                {
                    String msgContent = configureProvider.format(MsgVariavle.TX_SB, envionment);
                    sendMsg(connection, t6110.F04, msgContent);
                }
            }
        }
    }
    
    @Override
    public T6503 ptWithdraw(String bankCard, BigDecimal amount)
        throws Throwable
    {
        if (amount.compareTo(new BigDecimal(0)) <= 0)
        {
            throw new ParameterException("提现金额小于等于0");
        }
        if (StringHelper.isEmpty(bankCard))
        {
            throw new ParameterException("未选择银行卡");
        }
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                int accountId = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S71.T7101 LIMIT 1"))
                {
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            accountId = resultSet.getInt(1);
                        }
                    }
                }
                if (accountId <= 0)
                {
                    throw new LogicalException("平台账户不存在");
                }
                T6101 wlzh = selectT6101(connection, accountId, T6101_F03.WLZH);
                if (wlzh == null)
                {
                    throw new LogicalException("平台往来账户不存在");
                }
                try (PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO S61.T6119 SET F01 = ?, F02 = ?, F03 = ? ON DUPLICATE KEY UPDATE F01 = VALUES(F01), F02 = VALUES(F02), F03 = VALUES(F03)"))
                {
                    pstmt.setInt(1, accountId);
                    pstmt.setInt(2, PaymentInstitution.CHINAPNR.getInstitutionCode());
                    pstmt.setString(3, configureProvider.getProperty(HuifuVariable.HF_CUST_ID));
                    pstmt.execute();
                }
                
                Timestamp times = getCurrentTimestamp(connection);
                
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.WITHDRAW.orderType();
                t6501.F03 = T6501_F03.DTJ;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.YH;
                t6501.F08 = accountId;
                t6501.F09 = getPTID(connection);
                t6501.F13 = amount;
                int orderId = insertT6501(connection, t6501);
                
                T6503 t6503 = new T6503();
                t6503.F01 = orderId;
                t6503.F02 = accountId;
                t6503.F03 = amount;
                t6503.F04 = new BigDecimal(0);
                t6503.F05 = t6503.F04;
                //                    t6503.F04.add(BigDecimalParser.parse(configureProvider.getProperty(HuifuVariable.HF_WITHDRAW_AMOUNT)));
                t6503.F06 = bankCard;
                t6503.F07 = PaymentInstitution.ALLINPAY.getInstitutionCode();
                insertT6503(connection, t6503);
                
                if (wlzh.F06.compareTo(t6503.F05.add(amount)) < 0)
                {
                    throw new LogicalException("账户余额不足");
                }
                serviceResource.commit(connection);
                return t6503;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    @Override
    public void doConfim(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                
                T6503 t6503 = selectT6503(connection, ordId);
                
                //查询银行卡
                T6114 t6114 = selectT6114(connection, t6503.F02, StringHelper.encode(t6503.F06));
                
                //新增提现申请记录
                T6130 t6130 = new T6130();
                t6130.F02 = t6503.F02;
                t6130.F03 = t6114.F01;
                t6130.F04 = t6503.F11;
                t6130.F06 = t6503.F10;//平台手续用户的手续费，放到T6130的F06字段
                t6130.F07 = t6503.F05;
                t6130.F09 = T6130_F09.DSH;
                int applyId = insertT6130(connection, t6130, t6503.F02);
                
                //更新提现订单关联
                try (PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE S65.T6503 SET F09 = ? WHERE F01 = ?"))
                {
                    pstmt.setInt(1, applyId);
                    pstmt.setInt(2, ordId);
                    pstmt.execute();
                }
                
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                logger.error("冻结回调处理失败,原因：" + e.getMessage());
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    @Override
    public void updateT6503(int F01, BigDecimal F04, BigDecimal F10, BigDecimal F11)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S65.T6503 SET F04 = ?, F05 = ?, F10 = ? ,F11 = ?  WHERE F01 = ?"))
            {
                pstmt.setBigDecimal(1, F04);
                pstmt.setBigDecimal(2, F04.add(F10));//实收手续费 = 第三方收取的手续费 + 平台收取的手续费
                pstmt.setBigDecimal(3, F10);
                pstmt.setBigDecimal(4, F11);
                pstmt.setInt(5, F01);
                pstmt.execute();
            }
        }
    }
    
    @Override
    public void withdrawFailUnFreze(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6503 t6503 = selectT6503(connection, ordId);
            if (t6503 == null)
            {
                throw new LogicalException("订单记录不存在");
            }
            // 用户锁定账户信息
            T6101 sT6101 = selectT6101(connection, t6503.F02, T6101_F03.SDZH);
            // 用户往来账户信息
            T6101 wT6101 = selectT6101(connection, t6503.F02, T6101_F03.WLZH);
            if (sT6101 == null)
            {
                throw new LogicalException("用户锁定账户不存在");
            }
            if (wT6101 == null)
            {
                throw new LogicalException("用户往来账户不存在");
            }
            
            try
            {
                serviceResource.openTransactions(connection);
                // 插资金流水
                {
                    sT6101.F06 = sT6101.F06.subtract(t6503.F03);
                    T6102 t6102 = new T6102();
                    t6102.F02 = sT6101.F01;
                    t6102.F03 = FeeCode.TX;
                    t6102.F04 = sT6101.F01;
                    t6102.F07 = t6503.F03;
                    t6102.F08 = sT6101.F06;
                    t6102.F09 = "用户提现冻结失败返还金额";
                    insertT6102(connection, t6102);
                }
                if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
                {
                    sT6101.F06 = sT6101.F06.subtract(t6503.F05);
                    T6102 t6102 = new T6102();
                    t6102.F02 = sT6101.F01;
                    t6102.F03 = FeeCode.TX_SXF;
                    t6102.F04 = sT6101.F01;
                    t6102.F07 = t6503.F05;
                    t6102.F08 = sT6101.F06;
                    t6102.F09 = "用户提现冻结失败手续费";
                    insertT6102(connection, t6102);
                }
                updateT6101(connection, sT6101.F06, sT6101.F01);
                //往来用户
                {
                    wT6101.F06 = wT6101.F06.add(t6503.F03);
                    T6102 t6102 = new T6102();
                    t6102.F02 = wT6101.F01;
                    t6102.F03 = FeeCode.TX;
                    t6102.F04 = wT6101.F01;
                    t6102.F06 = t6503.F03;
                    t6102.F08 = wT6101.F06;
                    t6102.F09 = "用户提现冻结失败返还金额";
                    insertT6102(connection, t6102);
                }
                if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
                {
                    wT6101.F06 = wT6101.F06.add(t6503.F05);
                    T6102 t6102 = new T6102();
                    t6102.F02 = wT6101.F01;
                    t6102.F03 = FeeCode.TX_SXF;
                    t6102.F04 = wT6101.F01;
                    t6102.F06 = t6503.F05;
                    t6102.F08 = wT6101.F06;
                    t6102.F09 = "用户提现冻结失败手续费";
                    insertT6102(connection, t6102);
                }
                updateT6101(connection, wT6101.F06, wT6101.F01);
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    public boolean getVerifyStatus(Connection connection)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F02, F03, F04, F05 FROM S61.T6118 WHERE T6118.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, serviceResource.getSession().getAccountId());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    if (resultSet.getString(1).equals(T6118_F02.TG.name())
                        && resultSet.getString(2).equals(T6118_F03.TG.name()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public int updateTxStatus(int orderId)
        throws Throwable
    {
        int txReqId = 0;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT T6503.F09 FROM S65.T6503 inner join S65.T6501 on T6503.F01=T6501.F01 WHERE T6503.F01 = ? AND T6501.F03='DQR' LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        txReqId = resultSet.getInt(1);
                    }
                }
            }
            //防止多次回调
            if (0 == txReqId)
            {
                throw new LogicalException("该提现审核订单已经处理过了，不再重复处理！");
            }
            
            try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6501 SET F03 = ? WHERE F01 = ?"))
            {
                ps.setString(1, T6501_F03.SB.name());
                ps.setInt(2, orderId);
                ps.execute();
            }
            
            try (PreparedStatement ps = connection.prepareStatement("UPDATE S61.T6130 SET F09 = ? WHERE F01 = ?"))
            {
                ps.setString(1, T6130_F09.TXSB.name());
                ps.setInt(2, txReqId);
                ps.execute();
            }
            
        }
        return txReqId;
    }
    
    /**
     * 增加冻结订单
     * <功能详细描述>
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
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
    
    /** {@inheritDoc} */
    
    @Override
    public BigDecimal QueryCashBalanceBg(String userAccount)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryCashBalanceBg";
        String MerCustId = merCustId;
        String UsrCustId = "";
        if (StringHelper.isEmpty(userAccount))
        {
            UsrCustId = getUserCustId();
        }
        else
        {
            UsrCustId = userAccount;
        }
        
        List<String> params = new ArrayList<>();
        
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("ChkValue", ChkValue);
        
        logger.info("用户第三方可取现金额查询 --> 请求参数：" + map.toString());
        
        String result = doPost(map);
        
        logger.info("用户第三方可取现金额查询 --> 返回参数：" + result);
        
        Map<String, String> jsonMap = jsonParse(result);
        
        List<String> paramsRet = new ArrayList<>();
        paramsRet.add(jsonMap.get("CmdId"));
        paramsRet.add(jsonMap.get("RespCode"));
        paramsRet.add(jsonMap.get("MerCustId"));
        paramsRet.add(jsonMap.get("UsrCustId"));
        paramsRet.add(jsonMap.get("CashBal"));
        
        String str = forEncryptionStr(paramsRet);
        
        BigDecimal amt = BigDecimalParser.parse(jsonMap.get("CashBal"));
        
        return verifyByRSA(str, jsonMap.get("ChkValue")) ? amt : BigDecimal.ZERO;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public void unFreze(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6503 t6503 = selectT6503(connection, ordId);
            if (t6503 == null)
            {
                throw new LogicalException("提现订单记录不存在");
            }
            // 用户锁定账户信息
            T6101 sT6101 = selectT6101(connection, t6503.F02, T6101_F03.SDZH);
            // 用户往来账户信息
            T6101 wT6101 = selectT6101(connection, t6503.F02, T6101_F03.WLZH);
            if (sT6101 == null)
            {
                throw new LogicalException("用户锁定账户不存在");
            }
            if (wT6101 == null)
            {
                throw new LogicalException("用户往来账户不存在");
            }
            // 插资金流水
            {
                sT6101.F06 = sT6101.F06.subtract(t6503.F11);
                T6102 t6102 = new T6102();
                t6102.F02 = sT6101.F01;
                t6102.F03 = FeeCode.TX;
                t6102.F04 = sT6101.F01;
                t6102.F07 = t6503.F11;
                t6102.F08 = sT6101.F06;
                t6102.F09 = "用户提现冻结失败,返还金额";
                insertT6102(connection, t6102);
            }
            if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
            {
                sT6101.F06 = sT6101.F06.subtract(t6503.F05);
                T6102 t6102 = new T6102();
                t6102.F02 = sT6101.F01;
                t6102.F03 = FeeCode.TX_SXF;
                t6102.F04 = sT6101.F01;
                t6102.F07 = t6503.F05;
                t6102.F08 = sT6101.F06;
                t6102.F09 = "用户提现冻结失败,返还手续费";
                insertT6102(connection, t6102);
            }
            updateT6101(connection, sT6101.F06, sT6101.F01);
            //往来用户
            {
                wT6101.F06 = wT6101.F06.add(t6503.F11);
                T6102 t6102 = new T6102();
                t6102.F02 = wT6101.F01;
                t6102.F03 = FeeCode.TX;
                t6102.F04 = wT6101.F01;
                t6102.F06 = t6503.F11;
                t6102.F08 = wT6101.F06;
                t6102.F09 = "用户提现冻结失败,返还金额";
                insertT6102(connection, t6102);
            }
            if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
            {
                wT6101.F06 = wT6101.F06.add(t6503.F05);
                T6102 t6102 = new T6102();
                t6102.F02 = wT6101.F01;
                t6102.F03 = FeeCode.TX_SXF;
                t6102.F04 = wT6101.F01;
                t6102.F06 = t6503.F05;
                t6102.F08 = wT6101.F06;
                t6102.F09 = "用户提现冻结失败,返还手续费";
                insertT6102(connection, t6102);
            }
            updateT6101(connection, wT6101.F06, wT6101.F01);
        }
    }
    
    @Override
    public T6503 addOrder(String bankCard, BigDecimal amount)
        throws Throwable
    {
        if (amount.compareTo(new BigDecimal(0)) <= 0)
        {
            throw new ParameterException("提现金额小于等于0");
        }
        if (StringHelper.isEmpty(bankCard))
        {
            throw new ParameterException("未选择银行卡");
        }
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        
        BigDecimal min = new BigDecimal(configureProvider.getProperty(SystemVariable.WITHDRAW_MIN_FUNDS));
        BigDecimal max = new BigDecimal(configureProvider.getProperty(SystemVariable.WITHDRAW_MAX_FUNDS));
        if (amount.compareTo(min) < 0 || amount.compareTo(max) > 0 || amount.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new LogicalException("提现金额不能低于" + min + "元，不能高于" + max + "元!");
        }
        
        int accountId = serviceResource.getSession().getAccountId();
        try (Connection connection = getConnection())
        {
            if (!getVerifyStatus(connection))
            {
                throw new LogicalException("手机未认证、实名未认证或交易密码未设置!");
            }
            
            if (isYuqi(accountId))
            {
                throw new LogicalException("您尚有逾期借款未还，不能进行提现操作!");
            }
            
            T6110 t6110 = selectT6110(connection, accountId);
            if (T6110_F07.HMD == t6110.F07)
            {
                throw new LogicalException("用户:" + t6110.F02 + "已经被拉黑，不能进行提现!");
            }
            
            T6101 wlzh = selectT6101(connection, accountId, T6101_F03.WLZH);
            if (wlzh == null)
            {
                throw new LogicalException("用户往来账户不存在");
            }
            
            //取现手续费收取规则：取现金额低于200时 ，平台收取手续费按照 （取现金额*0.8%） 计算  --第三方限制商户收取用户取现服务费不能大于1%
            BigDecimal poundage = null;
            String pundageWay = configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_WAY);
            if ("BL".equals(pundageWay))
            {
                // 按比例计算
                String _proportion = configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_PROPORTION);
                if (amount.compareTo(new BigDecimal(201)) < 0)
                {
                    if (new BigDecimal(_proportion).compareTo(BigDecimal.ZERO) == 0)
                    {
                        poundage = BigDecimal.ZERO;
                    }
                    else
                    {
                        //取现金额低于200时 ，平台收取手续费按照 （取现金额*0.8%） 计算  --第三方限制商户收取用户取现服务费不能大于1%
                        poundage = amount.multiply(new BigDecimal(0.008)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }
                else
                {
                    if (StringHelper.isEmpty(_proportion) || _proportion.contains("-"))
                    {
                        throw new LogicalException("系统繁忙，请稍后再试！");
                    }
                    BigDecimal proportion = new BigDecimal(_proportion);
                    poundage = amount.multiply(proportion).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }
            else
            {
                // 按额度计算[1-200],[201-50000], [50000, ]
                if (amount.compareTo(new BigDecimal(201)) < 0)
                {
                    if (new BigDecimal(configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_1_5)).compareTo(BigDecimal.ZERO) == 0)
                    {
                        poundage = BigDecimal.ZERO;
                    }
                    else
                    {
                        poundage = amount.multiply(new BigDecimal(0.008)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }
                else if (amount.compareTo(new BigDecimal(50000)) < 0)
                {
                    poundage = new BigDecimal(configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_1_5));
                }
                else
                {
                    poundage = new BigDecimal(configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_5_20));
                }
            }
            
            if (amount.doubleValue() < poundage.doubleValue())
            {
                throw new LogicalException("提现金额不能小于提现手续费！");
            }
            
            Timestamp times = getCurrentTimestamp(connection);
            
            T6501 t6501 = new T6501();
            t6501.F02 = OrderType.WITHDRAW.orderType();
            t6501.F03 = T6501_F03.DTJ;
            t6501.F04 = times;
            t6501.F05 = times;
            t6501.F07 = T6501_F07.YH;
            t6501.F08 = accountId;
            t6501.F09 = getPTID(connection);
            t6501.F13 = amount;
            
            try
            {
                //开启事物
                serviceResource.openTransactions(connection);
                //新增订单
                int orderId = insertT6501(connection, t6501);
                //生成提现订单
                T6503 t6503 = new T6503();
                t6503.F01 = orderId;
                t6503.F02 = accountId;
                t6503.F03 = amount;
                t6503.F04 = BigDecimal.ZERO;
                t6503.F05 = t6503.F04;
                t6503.F06 = bankCard;
                t6503.F07 = PaymentInstitution.CHINAPNR.getInstitutionCode();
                t6503.F10 = poundage;
                insertT6503(connection, t6503);
                
                if (wlzh.F06.compareTo(amount) < 0)
                {
                    throw new LogicalException("账户余额不足");
                }
                
                serviceResource.commit(connection);
                
                return t6503;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    private boolean isYuqi(int accountId)
        throws Throwable
    {
        String sql = "SELECT DATEDIFF(?,F08) FROM S62.T6252 WHERE F09=? AND F03=? AND F08 < SYSDATE()";
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setTimestamp(1, getCurrentTimestamp(connection));
                ps.setString(2, T6252_F09.WH.name());
                ps.setInt(3, accountId);
                try (ResultSet rs = ps.executeQuery())
                {
                    while (rs.next())
                    {
                        int days = rs.getInt(1);
                        if (days > 0)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
}
