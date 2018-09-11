package com.dimeng.p2p.escrow.huifu.executor;

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

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import chinapnr.SecureLink;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.config.Envionment;
import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6130_F09;
import com.dimeng.p2p.S61.enums.T6130_F16;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6503;
import com.dimeng.p2p.S65.entities.T6515;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.common.SMSUtils;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.WithdrawExecutor;
import com.dimeng.p2p.variables.defines.LetterVariable;
import com.dimeng.p2p.variables.defines.MsgVariavle;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.p2p.variables.defines.smses.SmsVaribles;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.DateTimeParser;

/**
 * 
 * 提现复核
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
@AchieveVersion(version = 20140731)
@ResourceAnnotation
public class HFWithdrawTgExecutor extends WithdrawExecutor
{
    
    public HFWithdrawTgExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
        throws Throwable
    {
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        
        boolean tg = BooleanParser.parseObject(configureProvider.getProperty(SystemVariable.SFZJTG));
        if (!tg)
        {
            return;
        }
        
        //查询提现订单
        T6503 t6503 = selectT6503(connection, orderId);
        
        // 解冻金额
        unFreeze(connection, orderId, configureProvider);
        
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "CashAudit";
        String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        String OrdId = String.valueOf(t6503.F01);
        String UsrCustId = usrCustId(connection, t6503.F02);
        String TransAmt = t6503.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String AuditFlag = "S";
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
        
        StringBuffer buffer = null;
        for (String param : params)
        {
            if (StringHelper.isEmpty(param))
            {
                continue;
            }
            if (buffer == null)
            {
                buffer = new StringBuffer();
            }
            buffer.append(StringUtils.trimToEmpty(param));
        }
        String RECV_MER_ID = configureProvider.getProperty(HuifuVariable.HF_ACCOUNT_ID);
        String MER_PRI_KEY_PATH = configureProvider.getProperty(HuifuVariable.HF_PRI_KEY_PATH);
        SecureLink sl = new SecureLink();
        int result = sl.SignMsg(RECV_MER_ID, MER_PRI_KEY_PATH, buffer.toString());
        if (result < 0)
        {
            // 打印日志
            throw new Exception();
        }
        String ChkValue = sl.getChkValue();
        
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
        
        logger.info(String.format("提现放款订单号：%s 请求参数：%s", orderId, map.toString()));
        
        List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(map);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        EntityBuilder builder = EntityBuilder.create();
        try
        {
            HttpPost httpPost = new HttpPost(configureProvider.getProperty(HuifuVariable.HF_URL));
            builder.setParameters(nvps);
            httpPost.setEntity(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            
            try
            {
                HttpEntity entity = response.getEntity();
                if (response.getStatusLine().getReasonPhrase().equals("OK")
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String rst = EntityUtils.toString(entity, "UTF-8");
                    logger.info(String.format("提现放款订单号：%s 返回参数：%s", orderId, rst));
                }
                EntityUtils.consume(entity);
            }
            finally
            {
                response.close();
            }
        }
        finally
        {
            httpclient.close();
        }
    }
    
    @Override
    protected void doConfirm(SQLConnection connection, int orderId, Map<String, String> params)
        throws Throwable
    {
        try
        {
            // 查询订单
            T6503 t6503 = selectT6503(connection, orderId);
            if (t6503 == null)
            {
                throw new LogicalException("订单记录不存在");
            }
            int pid = getPTID(connection);
            // 用户锁定账户信息
            ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
            T6101 uT6101 = selectT6101(connection, t6503.F02, T6101_F03.SDZH, true);
            if (uT6101 == null)
            {
                throw new LogicalException("用户锁定账户不存在");
            }
            
            T6101 wT6101 = selectT6101(connection, t6503.F02, T6101_F03.WLZH, true);
            if (wT6101 == null)
            {
                throw new LogicalException("用户往来账户不存在");
            }
            // 平台往来账户信息
            T6101 cT6101 = selectT6101(connection, pid, T6101_F03.WLZH, true);
            if (cT6101 == null)
            {
                throw new LogicalException("平台往来账户不存在");
            }
            if (uT6101.F06.compareTo(t6503.F11.add(t6503.F05)) < 0)
            {
                throw new LogicalException("用户账户金额不足");
            }
            // 插资金流水
            {
                uT6101.F06 = uT6101.F06.subtract(t6503.F11);
                T6102 t6102 = new T6102();
                t6102.F02 = uT6101.F01;
                t6102.F03 = FeeCode.TX;
                t6102.F04 = uT6101.F01;
                t6102.F07 = t6503.F11;
                t6102.F08 = uT6101.F06;
                t6102.F09 = "提现成功，扣除提现金额";
                insertT6102(connection, t6102);
            }
            if (t6503.F05.compareTo(BigDecimal.ZERO) > 0)
            {
                uT6101.F06 = uT6101.F06.subtract(t6503.F05);
                T6102 t6102 = new T6102();
                t6102.F02 = uT6101.F01;
                t6102.F03 = FeeCode.TX_SXF;
                t6102.F04 = uT6101.F01;
                t6102.F07 = t6503.F05;
                t6102.F08 = uT6101.F06;
                t6102.F09 = "提现成功，扣除提现手续费";
                insertT6102(connection, t6102);
            }
            updateT6101(connection, uT6101.F06, uT6101.F01);
            if (t6503.F10.compareTo(BigDecimal.ZERO) > 0)
            {
                cT6101.F06 = cT6101.F06.add(t6503.F10);
                updateT6101(connection, cT6101.F06, cT6101.F01);
                {
                    T6102 t6102 = new T6102();
                    t6102.F02 = cT6101.F01;
                    t6102.F03 = FeeCode.TX_SXF;
                    t6102.F04 = uT6101.F01;
                    t6102.F06 = t6503.F10;
                    t6102.F08 = cT6101.F06;
                    t6102.F09 = "用户提现手续费";
                    insertT6102(connection, t6102);
                }
            }
            
            //平台代付取现手续费
            //增加资金流水
            //            BigDecimal feeAmt = BigDecimal.ZERO;
            //            if (params != null)
            //            {
            //                feeAmt = BigDecimalParser.parse(params.get("feeAmt"));
            //            }
            //            //生成平台资金流水
            //            if (feeAmt.compareTo(BigDecimal.ZERO) > 0)
            //            {
            //                cT6101.F06 = cT6101.F06.subtract(feeAmt);
            //                updateT6101(connection, cT6101.F06, cT6101.F01);
            //                {
            //                    T6102 t6102 = new T6102();
            //                    t6102.F02 = cT6101.F01;
            //                    t6102.F03 = FeeCode.TX_CB;
            //                    t6102.F04 = wT6101.F01;
            //                    t6102.F07 = feeAmt;
            //                    t6102.F08 = cT6101.F06;
            //                    t6102.F09 = "用户提现成本";
            //                    insertT6102(connection, t6102);
            //                }
            //            }
            
            if (t6503.F09 > 0)
            {
                // 修改提现申请放款状态
                if (params == null || StringHelper.isEmpty(params.get("check_reason")))
                {
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S61.T6130 SET F09 = ? ,F11 = now(),F14 =now(), F16= ? WHERE F01 = ?"))
                    {
                        ps.setString(1, T6130_F09.YFK.name());
                        ps.setString(2, T6130_F16.S.name());
                        ps.setInt(3, t6503.F09);
                        ps.execute();
                    }
                }
                else
                {
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S61.T6130 SET F09 = ? ,F11 = now(),F14 =now(), F15 =?, F16= ? WHERE F01 = ?"))
                    {
                        ps.setString(1, T6130_F09.YFK.name());
                        ps.setString(2, params.get("check_reason"));
                        ps.setString(3, T6130_F16.S.name());
                        ps.setInt(4, t6503.F09);
                        ps.execute();
                    }
                }
            }
            
            T6110 t6110 = selectT6110(connection, t6503.F02);
            Envionment envionment = configureProvider.createEnvionment();
            envionment.set("name", t6110.F02);
            envionment.set("datetime", DateTimeParser.format(new Date()));
            envionment.set("amount", t6503.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            envionment.set("poundage", t6503.F05.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            String content = configureProvider.format(LetterVariable.TX_CG, envionment);
            sendLetter(connection, t6503.F02, "提现成功", content);
            String isUseYtx = configureProvider.getProperty(SmsVaribles.SMS_IS_USE_YTX);
            if ("1".equals(isUseYtx))
            {
                SMSUtils smsUtil = new SMSUtils(configureProvider);
                int type = smsUtil.getTempleId(MsgVariavle.TX_CG.getDescription());
                sendMsg(connection,
                    t6110.F04,
                    smsUtil.getSendContent(envionment.get("datetime"),
                        envionment.get("amount"),
                        configureProvider.getProperty(SystemVariable.SITE_CUSTOMERSERVICE_TEL)),
                    type);
            }
            else
            {
                String msgContent = configureProvider.format(MsgVariavle.TX_CG, envionment);
                sendMsg(connection, t6110.F04, msgContent);
            }
        }
        catch (Exception e)
        {
            logger.error(e, e);
            throw e;
        }
    }
    
    /**
     * 取现解冻资金
     * <功能详细描述>
     * @param connection
     * @param orderId
     * @param configureProvider
     * @throws Throwable
     */
    public void unFreeze(Connection connection, int orderId, ConfigureProvider configureProvider)
        throws Throwable
    {
        T6515 t6515 = selectT6515(connection, orderId);
        T6501 djOrder = selectT6501(connection, t6515.F01);
        
        Timestamp times = getCurrentTimestamp(connection);
        
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.UNFREEZE.orderType();
        t6501.F03 = T6501_F03.DQR;
        t6501.F04 = times;
        t6501.F05 = times;
        t6501.F07 = T6501_F07.YH;
        t6501.F08 = djOrder.F08;
        t6501.F09 = getPTID(connection);
        t6501.F13 = t6515.F03;
        t6501.F01 = insertT6501(connection, t6501);
        
        insertT6516(connection, t6501.F01, orderId, t6515.F03);
        
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
        
        StringBuffer buffer = null;
        for (String param : pps)
        {
            if (StringHelper.isEmpty(param))
            {
                continue;
            }
            if (buffer == null)
            {
                buffer = new StringBuffer();
            }
            buffer.append(StringUtils.trimToEmpty(param));
        }
        String RECV_MER_ID = configureProvider.getProperty(HuifuVariable.HF_ACCOUNT_ID);
        String MER_PRI_KEY_PATH = configureProvider.getProperty(HuifuVariable.HF_PRI_KEY_PATH);
        SecureLink sl = new SecureLink();
        int result = sl.SignMsg(RECV_MER_ID, MER_PRI_KEY_PATH, buffer.toString());
        if (result < 0)
        {
            // 打印日志
            throw new Exception();
        }
        String ChkValue = sl.getChkValue();
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("TrxId", TrxId);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        logger.info(String.format("提现订单解冻%s 请求参数：%s", orderId, map));
        
        List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(map);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        EntityBuilder builder = EntityBuilder.create();
        try
        {
            HttpPost httpPost = new HttpPost(configureProvider.getProperty(HuifuVariable.HF_URL));
            builder.setParameters(nvps);
            httpPost.setEntity(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            
            try
            {
                HttpEntity entity = response.getEntity();
                if (response.getStatusLine().getReasonPhrase().equals("OK")
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String rst = EntityUtils.toString(entity, "UTF-8");
                    logger.info(String.format("提现订单解冻%s 返回参数：%s", orderId, rst));
                }
                EntityUtils.consume(entity);
            }
            finally
            {
                response.close();
            }
        }
        finally
        {
            httpclient.close();
        }
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
    
    private int insertT6501(Connection connection, T6501 entity)
        throws Throwable
    {
        Timestamp timestamp = getCurrentTimestamp(connection);
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6501 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?, F10 = ?",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, entity.F02);
            pstmt.setString(2, entity.F03.name());
            pstmt.setTimestamp(3, timestamp);
            pstmt.setTimestamp(4, timestamp);
            pstmt.setTimestamp(5, entity.F06);
            pstmt.setString(6, entity.F07.name());
            pstmt.setInt(7, entity.F08);
            pstmt.setInt(8, entity.F09);
            pstmt.setString(9, entity.F10);
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
    protected T6503 selectT6503(Connection connection, int F01)
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
    
}
