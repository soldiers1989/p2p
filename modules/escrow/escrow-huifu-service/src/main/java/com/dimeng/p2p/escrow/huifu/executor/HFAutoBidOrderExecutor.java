/*
 * 文 件 名:  HFAutoBidOrderExecutor.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月8日
 */
package com.dimeng.p2p.escrow.huifu.executor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.p2p.OrderType;
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
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.AutoTenderOrderExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.DateTimeParser;

/**
 * 自动投标执行器
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月8日]
 */
@AchieveVersion(version = 201401022)
@ResourceAnnotation
public class HFAutoBidOrderExecutor extends AutoTenderOrderExecutor
{
    
    public HFAutoBidOrderExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> params)
        throws Throwable
    {
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        
        T6501 t6501 = selectT6501(connection, orderId);
        if (null == t6501)
        {
            logger.info("订单不存在");
            return;
        }
        T6504 t6504 = selectT6504(connection, orderId);
        if (null == t6504)
        {
            logger.info("自动投标订单不存在");
            return;
        }
        T6501 freezeOrd = new T6501();
        {
            Timestamp times = getCurrentTimestamp(connection);
            
            // 添加冻结订单
            freezeOrd.F02 = OrderType.FREEZE.orderType();
            freezeOrd.F03 = T6501_F03.DQR;
            freezeOrd.F04 = times;
            freezeOrd.F05 = times;
            freezeOrd.F07 = T6501_F07.YH;
            freezeOrd.F08 = t6504.F02;
            freezeOrd.F09 = getPTID(connection);
            freezeOrd.F13 = t6504.F04;
            
            freezeOrd.F01 = insertT6501(connection, freezeOrd);
            
            insertT6515(connection, freezeOrd.F01, t6501.F01, t6504.F04);
        }
        
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "AutoTender";
        String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        String OrdId = String.valueOf(orderId);
        String OrdDate = DateTimeParser.format(t6501.F04, "yyyyMMdd");
        String TransAmt = t6504.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String UsrCustId = usrCustId(connection, t6504.F02);
        String MaxTenderRate = configureProvider.getProperty(HuifuVariable.HF_MAXTENDERRATE);
        String BorrowerDetails = borrowerDetails(connection, t6504.F03, t6504.F04, configureProvider);
        String IsFreeze = "Y";
        String FreezeOrdId = String.valueOf(freezeOrd.F01);
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_BID);
        
        List<String> paramList = new ArrayList<>();
        paramList.add(Version);
        paramList.add(CmdId);
        paramList.add(MerCustId);
        paramList.add(OrdId);
        paramList.add(OrdDate);
        paramList.add(TransAmt);
        paramList.add(UsrCustId);
        paramList.add(MaxTenderRate);
        paramList.add(BorrowerDetails);
        paramList.add(IsFreeze);
        paramList.add(FreezeOrdId);
        paramList.add(BgRetUrl);
        
        StringBuffer buffer = null;
        for (String param : paramList)
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
        map.put("TransAmt", TransAmt);
        map.put("UsrCustId", UsrCustId);
        map.put("MaxTenderRate", MaxTenderRate);
        map.put("BorrowerDetails", BorrowerDetails);
        map.put("IsFreeze", IsFreeze);
        map.put("FreezeOrdId", FreezeOrdId);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        {
            StringBuilder mapParams = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                if (entry == null)
                {
                    continue;
                }
                mapParams.append("&");
                mapParams.append(entry.getKey());
                mapParams.append("=");
                mapParams.append(entry.getValue());
            }
            logger.info(String.format("自动投资订单%s请求参数：%s", orderId, mapParams.toString()));
        }
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
                    logger.info(String.format("自动投资订单%s返回参数：%s", orderId, rst));
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
    
    protected String borrowerDetails(Connection connection, int loanId, BigDecimal amount,
        ConfigureProvider configureProvider)
        throws Throwable
    {
        BigDecimal borrowRate = BigDecimalParser.parse(configureProvider.getProperty(HuifuVariable.HF_BORROWERRATE));
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
    
    @Override
    protected T6230 selectT6230(Connection connection, int F01)
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
    protected T6501 selectT6501(Connection connection, int orderId)
        throws Throwable
    {
        T6501 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09 FROM S65.T6501 WHERE T6501.F01 = ? LIMIT 1"))
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
                }
            }
            return record;
        }
    }
}
