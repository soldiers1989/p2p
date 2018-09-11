/*
 * 文 件 名:  HuifuTenderConfirmExecutor.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月19日
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
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.entities.T6238;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6505;
import com.dimeng.p2p.S65.entities.T6508;
import com.dimeng.p2p.S65.entities.T6515;
import com.dimeng.p2p.S65.entities.T6527;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.TenderConfirmExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;

/**
 * 
 * 放款执行器
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月19日]
 */
@AchieveVersion(version = 20140715)
@ResourceAnnotation
public class HFBidConfirmExecutor extends TenderConfirmExecutor
{
    
    public HFBidConfirmExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
        throws Throwable
    {
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        
        T6501 t6501 = selectT6501(connection, orderId);
        if (null == t6501)
        {
            throw new LogicalException("订单不存在");
        }
        
        T6505 t6505 = selectT6505(connection, orderId);
        if (null == t6505)
        {
            throw new LogicalException("放款订单不存在");
        }
        
        T6230 t6230 = selectT6230(connection, t6505.F03);
        if (null == t6230)
        {
            throw new LogicalException("标信息不存在");
        }
        
        T6504 t6504 = selectT6504(connection, t6505.F03, t6505.F04);
        if (null == t6504)
        {
            throw new LogicalException("投资订单不存在");
        }
        T6501 yT6501 = selectT6501(connection, t6504.F01);
        T6515 t6515 = selectT6515(connection, t6504.F01);
        T6527 t6527 = selectT6527(connection, t6504.F01);
        T6501 freezeOrder = selectT6501(connection, t6515.F01);
        int count = 0;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT COUNT(*) FROM S62.T6250 WHERE F02 = ? AND F08 = 'F'"))
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
            return;
        }
        //放款传入红包金额
        String reqExt = null;
        if (t6527 != null && t6527.F04 != null && t6527.F04.compareTo(BigDecimal.ZERO) > 0)
        {
            reqExt = "{\"LoansVocherAmt\":\"" + t6527.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "\"}";
        }
        // 收成交服务费
        BigDecimal fee = new BigDecimal(0);
        {
            T6238 t6238 = selectT6238(connection, t6230.F01);
            if (t6238 != null && t6238.F02.compareTo(new BigDecimal(0)) > 0)
            {
                BigDecimal fwf = t6238.F02.multiply(t6505.F05);
                // 收取成交服务费
                fee = fwf.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        
        Timestamp times = getCurrentTimestamp(connection);
        
        // 插入解冻订单
        T6501 unfreezeOrder = new T6501();
        unfreezeOrder.F02 = OrderType.UNFREEZE.orderType();
        unfreezeOrder.F03 = T6501_F03.DQR;
        unfreezeOrder.F04 = times;
        unfreezeOrder.F05 = times;
        unfreezeOrder.F07 = T6501_F07.YH;
        unfreezeOrder.F08 = t6504.F02;
        unfreezeOrder.F09 = getPTID(connection);
        unfreezeOrder.F13 = t6504.F04;
        unfreezeOrder.F01 = insertT6501(connection, unfreezeOrder);
        
        insertT6516(connection, unfreezeOrder.F01, t6504.F01, t6515.F03);
        
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "Loans";
        String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        String OrdId = String.valueOf(orderId);
        String OrdDate = DateParser.format(t6501.F04, "yyyyMMdd");
        String OutCustId = usrCustId(connection, t6505.F02);
        String TransAmt = t6505.F05.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String Fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
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
        String SubOrdId = String.valueOf(yT6501.F01);
        String SubOrdDate = DateParser.format(yT6501.F04, "yyyyMMdd");
        String InCustId = usrCustId(connection, t6230.F02);
        String FeeObjFlag = "I";
        String IsDefault = "N";
        String IsUnFreeze = "Y";
        String UnFreezeOrdId = String.valueOf(unfreezeOrder.F01);
        String FreezeTrxId = freezeOrder.F10;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_LOANS);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(OutCustId);
        params.add(TransAmt);
        params.add(Fee);
        params.add(SubOrdId);
        params.add(SubOrdDate);
        params.add(InCustId);
        params.add(DivDetails);
        params.add(FeeObjFlag);
        params.add(IsDefault);
        params.add(IsUnFreeze);
        params.add(UnFreezeOrdId);
        params.add(FreezeTrxId);
        params.add(BgRetUrl);
        params.add(reqExt);
        
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
        map.put("OrdDate", OrdDate);
        map.put("OutCustId", OutCustId);
        map.put("TransAmt", TransAmt);
        map.put("Fee", Fee);
        map.put("SubOrdId", SubOrdId);
        map.put("SubOrdDate", SubOrdDate);
        map.put("InCustId", InCustId);
        map.put("DivDetails", DivDetails);
        map.put("IsDefault", IsDefault);
        map.put("BgRetUrl", BgRetUrl);
        map.put("FeeObjFlag", FeeObjFlag);
        map.put("IsUnFreeze", IsUnFreeze);
        map.put("UnFreezeOrdId", UnFreezeOrdId);
        map.put("FreezeTrxId", FreezeTrxId);
        map.put("ReqExt", reqExt);
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
            logger.info(String.format("放款订单%s请求参数：%s", orderId, mapParams.toString()));
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
                    logger.info(String.format("放款订单%s返回参数：%s", orderId, rst));
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
    
    public void unFreeze(Connection connection, int orderId, ConfigureProvider configureProvider)
        throws Throwable
    {
        T6505 t6505 = selectT6505(connection, orderId);
        T6504 t6504 = selectT6504(connection, t6505.F04);
        T6515 t6515 = selectT6515(connection, t6504.F01);
        T6501 djOrder = selectT6501(connection, t6515.F01);
        
        Timestamp times = getCurrentTimestamp(connection);
        
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.UNFREEZE.orderType();
        t6501.F03 = T6501_F03.DQR;
        t6501.F04 = times;
        t6501.F05 = times;
        t6501.F07 = T6501_F07.YH;
        t6501.F08 = djOrder.F08;
        t6501.F08 = djOrder.F08;
        t6501.F09 = getPTID(connection);
        t6501.F13 = t6505.F05;
        t6501.F01 = insertT6501(connection, t6501);
        insertT6516(connection, t6501.F01, t6504.F01, t6515.F03);
        
        String Version = "10";
        String CmdId = "UsrUnFreeze";
        String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        String OrdId = String.valueOf(t6501.F01);
        String OrdDate = DateParser.format(getCurrentDate(connection), "yyyyMMdd");
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
                    logger.info(String.format("订单解冻%s返回参数：%s", orderId, rst));
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
    
    private T6504 selectT6504(Connection connection, int F05)
        throws SQLException
    {
        T6504 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05 FROM S65.T6504 WHERE T6504.F05 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F05);
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
    
    protected T6508 selectT6508(Connection connection, int F01)
        throws SQLException
    {
        T6508 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03 FROM S65.T6508 WHERE T6508.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6508();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
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
    
    private T6504 selectT6504(Connection connection, int F03, int F05)
        throws SQLException
    {
        T6504 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05 FROM S65.T6504 WHERE T6504.F03 = ? AND T6504.F05 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F03);
            pstmt.setInt(2, F05);
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
    
    /**
    * 
    * 红包订单
    */
    private T6527 selectT6527(Connection connection, int F06)
        throws SQLException
    {
        T6527 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06 FROM S65.T6527 WHERE T6527.F06 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F06);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6527();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = resultSet.getInt(6);
                }
            }
        }
        return record;
    }
    
}
