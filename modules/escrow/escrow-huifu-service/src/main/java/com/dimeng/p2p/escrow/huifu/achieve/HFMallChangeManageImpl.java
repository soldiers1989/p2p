package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S63.entities.T6350;
import com.dimeng.p2p.S63.entities.T6351;
import com.dimeng.p2p.S63.enums.T6350_F07;
import com.dimeng.p2p.S63.enums.T6351_F11;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6517;
import com.dimeng.p2p.S65.entities.T6555;
import com.dimeng.p2p.S65.entities.T6556;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.service.HFMallChangeManage;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.repeater.score.entity.AddressResult;
import com.dimeng.util.StringHelper;

public class HFMallChangeManageImpl extends AbstractEscrowService implements HFMallChangeManage
{
    
    public HFMallChangeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public String mallChangeTgReturn(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6555 t6555 = selectT6555(connection, orderId);
            if (t6555 == null)
            {
                return "订单详细不存在！";
            }
            List<T6556> t6556s = selectT6556s(connection, orderId);
            
            for (T6556 t6556 : t6556s)
            {
                T6351 t6351 = queryGoodsByIdForUpdate(connection, t6556.F02);
                if (t6351 == null)
                {
                    return "所购买的商品不存在或已下架!";
                }
                
                if (t6556.F04 > t6351.F06)
                {
                    return "商品的库存不足";
                }
                
                T6101 wlzh = selectT6101(connection, t6555.F02, T6101_F03.WLZH, true);
                if (wlzh == null)
                {
                    return "您购买账号不存在";
                }
                if (wlzh.F06.compareTo(t6351.F15.multiply(new BigDecimal(t6556.F04))) < 0)
                {
                    return "您的账户余额不足";
                }
                int pid = getPTID(connection);
                if (pid <= 0)
                {
                    return "平台账号不存在";
                }
                // 平台往来账户信息
                T6101 ptwlzh = selectT6101(connection, pid, T6101_F03.WLZH, true);
                if (ptwlzh == null)
                {
                    return "平台往来账户不存在";
                }
/*                int buyCount = queryBuyCount(connection, t6556.F02, t6555.F02);
                if (t6351.F18 > 0 && (t6556.F04 + buyCount) > t6351.F18)
                {
                    //return "购买的数量已超过限购数：" + t6351.F18;
                    return "不能超过限购数：" + t6351.F18 + "<br/>已购买数量：" + buyCount+t6556.F04;
                }*/
                
            }
        }
        return null;
    }
    
    @Override
    public void mallChangeFailBack(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            
            //查询购买订单
            T6555 t6555 = selectT6555(connection, orderId);
            if (null == t6555)
            {
                throw new LogicalException("购买订单不存在!");
            }
            
            Timestamp times = getCurrentTimestamp(connection);
            
            T6501 t6501 = new T6501();
            t6501.F02 = OrderType.TRANSFER.orderType();
            t6501.F03 = T6501_F03.DQR;
            t6501.F04 = times;
            t6501.F05 = times;
            t6501.F07 = T6501_F07.YH;
            t6501.F08 = t6555.F02;
            t6501.F09 = getPTID(connection);
            t6501.F12 = "商品购买失败，资金退回给购买人";
            t6501.F13 = t6555.F04;
            t6501.F01 = insertT6501(connection, t6501);
            
            insertT6517(connection, t6501.F01, t6555.F02, t6555.F04);
            
            ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
            String Version = HuiFuConstants.Version.VERSION_10;
            String CmdId = "Transfer";
            String OrdId = String.valueOf(t6501.F01);
            String OutCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
            if (StringHelper.isEmpty(OutCustId))
            {
                throw new LogicalException("平台第三方账号不存在!");
            }
            String OutAcctId = HuiFuConstants.AcctId.ACCT_ID;
            String TransAmt = t6555.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String InCustId = userCustId(t6555.F02);
            if (StringHelper.isEmpty(InCustId))
            {
                throw new LogicalException("购买用户第三方账号不存在!");
            }
            String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
            String BgRetUrl = configureProvider.format(HuifuVariable.HF_DONATION_FAIL_RET);
            
            List<String> params = new ArrayList<>();
            params.add(Version);
            params.add(CmdId);
            params.add(OrdId);
            params.add(OutCustId);
            params.add(OutAcctId);
            params.add(TransAmt);
            params.add(InCustId);
            params.add(InAcctId);
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
            map.put("OrdId", OrdId);
            map.put("OutCustId", OutCustId);
            map.put("OutAcctId", OutAcctId);
            map.put("TransAmt", TransAmt);
            map.put("InCustId", InCustId);
            map.put("InAcctId", InAcctId);
            map.put("TransAmt", TransAmt);
            map.put("BgRetUrl", BgRetUrl);
            map.put("ChkValue", ChkValue);
            
            logger.info(String.format("商城商品购买失败，资金退回给购买用户，订单：%s 请求参数：%s", t6555.F01, map.toString()));
            
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
                        
                        logger.info(String.format("商城商品购买失败，资金退回给购买用户，订单：%s 返回参数：%s", t6555.F01, rst));
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
    }
    
    protected int queryBuyCount(Connection connection, int goodId, int userId)
        throws Throwable
    {
        try (PreparedStatement statement =
            connection.prepareStatement("SELECT SUM(T6359.F06) FROM S63.T6359 LEFT JOIN S63.T6352 ON T6359.F02 = T6352.F01 WHERE T6352.F02 = ? AND T6359.F03 = ? "))
        {
            statement.setInt(1, userId);
            statement.setInt(2, goodId);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }
    
    protected T6555 selectT6555(Connection connection, int orderId)
        throws Throwable
    {
        T6555 record = null;
        try (PreparedStatement statement =
            connection.prepareStatement("SELECT F01,F02,F03,F04,F05 FROM S65.T6555 WHERE F01=?"))
        {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6555();
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
    
    protected List<T6556> selectT6556s(Connection connection, int orderId)
        throws Throwable
    {
        List<T6556> list = null;
        try (PreparedStatement statement =
            connection.prepareStatement("SELECT F01,F02,F03,F04,F05,F06,F07 FROM S65.T6556 WHERE F03=?"))
        {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    T6556 record = new T6556();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    if (list == null)
                    {
                        list = new ArrayList<T6556>();
                    }
                    list.add(record);
                }
            }
        }
        return list;
    }
    
    protected T6351 queryGoodsByIdForUpdate(Connection connection, int id)
        throws Throwable
    {
        T6351 t6351 = null;
        try (PreparedStatement statement =
            connection.prepareStatement("SELECT T6351.F01 AS F01, T6351.F06 AS F02, T6351.F18 AS F03,T6351.F11 AS F04,T6351.F07 AS F05,T6350.F07 AS F06,T6351.F05 AS F07,T6351.F15 AS F08,T6351.F03 AS F09 FROM S63.T6351,S63.T6350 WHERE T6351.F04 = T6350.F01 AND T6351.F11 = 'sold' AND T6351.F01 = ? FOR UPDATE "))
        {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    t6351 = new T6351();
                    t6351.F01 = rs.getInt(1);
                    t6351.F06 = rs.getInt(2);
                    t6351.F18 = rs.getInt(3);
                    t6351.F11 = T6351_F11.parse(rs.getString(4));
                    t6351.F07 = rs.getInt(5);
                    if (t6351.t6350 == null)
                    {
                        t6351.t6350 = new T6350();
                    }
                    t6351.t6350.F07 = T6350_F07.valueOf(rs.getString(6));
                    t6351.F05 = rs.getInt(7);
                    t6351.F15 = rs.getBigDecimal(8);
                    t6351.F03 = rs.getString(9);
                }
            }
        }
        return t6351;
    }
    
    protected AddressResult queryById(Connection connection, int id, int userId)
        throws Throwable
    {
        AddressResult result = null;
        try (PreparedStatement statement =
            connection.prepareStatement("SELECT F01,F02,F03,F04,F05,F06,F07,F08 FROM S63.T6355 WHERE F01 = ? AND F02 = ? "))
        {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    result = new AddressResult();
                    result.id = rs.getInt(1);
                    result.region = rs.getInt(4);
                    result.receiverName = rs.getString(3);
                    result.address = rs.getString(5);
                    result.telephone = rs.getString(6);
                    result.code = rs.getString(7);
                    result.isDefault = rs.getString(8);
                }
            }
        }
        return result;
    }
    
    private void insertT6517(Connection connection, int F01, int F02, BigDecimal F03)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6517 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setBigDecimal(2, F03);
            pstmt.setInt(3, getPTID(connection));
            pstmt.setInt(4, F02);
            pstmt.setString(5, "商城商品购买失败，资金退回给购买用户");
            pstmt.setInt(6, FeeCode.MALL_NOPASS);
            pstmt.execute();
        }
    }
    
    @Override
    public void mallChangeFailRet(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                
                T6501 t6501 = selectT6501(connection, orderId);
                if (null == t6501)
                {
                    throw new LogicalException("订单不存在");
                }
                
                T6517 t6517 = selectT6517(connection, orderId);
                if (null == t6517)
                {
                    throw new LogicalException("转账订单不存在");
                }
                // 入账资金账户
                T6101 rzT6101 = selectT6101(connection, t6501.F08, T6101_F03.WLZH, true);
                if (null == rzT6101)
                {
                    throw new LogicalException("入账资金账户不存在");
                }
                
                // 出账资金账户
                T6101 cz6101 = selectT6101(connection, t6517.F03, T6101_F03.WLZH, true);
                if (null == cz6101)
                {
                    throw new LogicalException("出账资金账户不存在");
                }
                
                //                Timestamp times = getCurrentTimestamp(connection);
                
                //                T6102 eT6102 = new T6102();
                //                // 增加捐款用户资金返还流水
                //                updateT6101(connection, t6517.F02, t6517.F04, T6101_F03.WLZH);
                //                eT6102.F02 = rzT6101.F01;
                //                eT6102.F03 = FeeCode.PUBLIC_GOOD_ID;
                //                eT6102.F04 = cz6101.F01;
                //                eT6102.F05 = times;
                //                eT6102.F06 = t6517.F02;
                //                eT6102.F08 = rzT6101.F06.add(t6517.F02);
                //                eT6102.F09 = "商品购买失败，资金退回";
                //                insertT6102(connection, eT6102);
                //                
                //                // 平台账户扣除奖励金额
                //                updateT6101(connection, t6517.F02.multiply(new BigDecimal(-1)), t6517.F03, T6101_F03.WLZH);
                //                T6102 ecT6102 = new T6102();
                //                ecT6102.F02 = cz6101.F01;
                //                ecT6102.F03 = t6517.F06;
                //                ecT6102.F04 = rzT6101.F01;
                //                ecT6102.F05 = times;
                //                ecT6102.F07 = t6517.F02;
                //                ecT6102.F08 = cz6101.F06.subtract(t6517.F02);
                //                ecT6102.F09 = t6517.F05;
                //                insertT6102(connection, ecT6102);
                
                //更新订单成功
                try (PreparedStatement ps =
                    connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F06 = ? WHERE F01 = ?"))
                {
                    ps.setString(1, T6501_F03.CG.name());
                    ps.setTimestamp(2, getCurrentTimestamp(connection));
                    ps.setInt(3, orderId);
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
    
}
