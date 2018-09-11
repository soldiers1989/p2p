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
import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6508;
import com.dimeng.p2p.S65.entities.T6515;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.S65.enums.T6501_F11;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.TenderCancelExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;
import com.google.gson.Gson;

@AchieveVersion(version = 20140806)
@ResourceAnnotation
public class HFBidCancleExecutor extends TenderCancelExecutor
{
    
    public HFBidCancleExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> params)
        throws Throwable
    {
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        T6508 t6508 = selectT6508(connection, orderId);
        if (null == t6508)
        {
            throw new LogicalException("投资取消订单不存在");
        }
        T6504 t6504 = selectT6504(connection, t6508.F03);
        if (null == t6504)
        {
            throw new LogicalException("投资订单不存在");
        }
        T6515 t6515 = selectT6515(connection, t6504.F01);
        if (null == t6515)
        {
            throw new LogicalException("冻结订单不存在");
        }
        T6501 djOrder = selectT6501(connection, t6515.F01);
        
        Timestamp times = getCurrentTimestamp(connection);
        //新增解冻订单
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.UNFREEZE.orderType();
        t6501.F03 = T6501_F03.DQR;
        t6501.F04 = times;
        t6501.F05 = times;
        t6501.F07 = T6501_F07.YH;
        t6501.F08 = t6504.F02;
        t6501.F09 = getPTID(connection);
        t6501.F13 = t6504.F04;
        t6501.F01 = insertT6501(connection, t6501);
        insertT6516(connection, t6501.F01, t6504.F01, t6515.F03);
        
        Gson gson = new Gson();
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UsrUnFreeze";
        String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        String OrdId = String.valueOf(t6501.F01);
        String OrdDate = DateParser.format(new Date(), "yyyyMMdd");
        String TrxId = djOrder.F10;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_BID_UNFREEZE);
        
        //由于传的是解冻订单号到第三方，异步回调没有T6501主订单号，所以在这里将主订单号放入扩展字段带回
        Map<String, String> ps = new HashMap<String, String>();
        ps.put("orderId", String.valueOf(orderId));
        ps.put("des", HttpClientHandler.getBase64Encode(params.get("des")));
        if ("".equals(params.get("experOrderId")) || params.get("experOrderId") != null)
        {
            ps.put("experOrderId", params.get("experOrderId"));
        }
        String MerPriv = gson.toJson(ps);
        
        List<String> pps = new ArrayList<>();
        pps.add(Version);
        pps.add(CmdId);
        pps.add(MerCustId);
        pps.add(OrdId);
        pps.add(OrdDate);
        pps.add(TrxId);
        pps.add(BgRetUrl);
        pps.add(MerPriv);
        
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
        map.put("MerPriv", MerPriv);
        map.put("ChkValue", ChkValue);
        
        logger.info(String.format("流标解冻资金%s请求参数：%s", orderId, map.toString()));
        
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
                    logger.info(String.format("流标解冻资金%s返回参数：%s", orderId, rst));
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
        int orderId = 0;
        StringBuilder sql =
            new StringBuilder(
                "INSERT INTO S65.T6501 SET F02 = ?,F03 = ?,F04 = ?,F05 = ?,F06 = ?,F07 = ?,F08 = ?,F10 = ?,F11 = ?,F12 = ?,F13 = ?");
        if (entity.F09 != null)
        {
            sql.append(",F09 = ?");
        }
        try (PreparedStatement pstmt =
            connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, entity.F02);
            pstmt.setString(2, entity.F03.name());
            pstmt.setTimestamp(3, entity.F04);
            pstmt.setTimestamp(4, entity.F05);
            pstmt.setTimestamp(5, entity.F06);
            pstmt.setString(6, entity.F07.name());
            pstmt.setInt(7, entity.F08);
            pstmt.setString(8, entity.F10);
            pstmt.setString(9, entity.F11 == null ? T6501_F11.F.name() : entity.F11.name());
            pstmt.setString(10, entity.F12);
            pstmt.setBigDecimal(11, entity.F13);
            if (entity.F09 != null)
            {
                pstmt.setInt(12, entity.F09);
            }
            pstmt.execute();
            try (ResultSet resultSet = pstmt.getGeneratedKeys();)
            {
                if (resultSet.next())
                {
                    orderId = resultSet.getInt(1);
                }
            }
        }
        if (orderId == 0)
        {
            logger.error("HFBidCancleExecutor.insertT6501():数据库异常");
            throw new SQLException("数据库异常");
        }
        return orderId;
    }
    
}
