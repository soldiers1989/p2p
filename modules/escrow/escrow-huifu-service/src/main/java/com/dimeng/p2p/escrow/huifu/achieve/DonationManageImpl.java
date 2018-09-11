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
import com.dimeng.p2p.S62.entities.T6242;
import com.dimeng.p2p.S62.enums.T6242_F10;
import com.dimeng.p2p.S62.enums.T6242_F11;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6517;
import com.dimeng.p2p.S65.entities.T6554;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.service.DonationManage;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BooleanParser;

public class DonationManageImpl extends AbstractEscrowService implements DonationManage
{
    
    public DonationManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public void failCapitalBack(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            
            //查询捐款订单
            T6554 t6554 = selectT6554(connection, orderId);
            if (null == t6554)
            {
                throw new LogicalException("捐款订单不存在!");
            }
            
            Timestamp times = getCurrentTimestamp(connection);
            
            T6501 t6501 = new T6501();
            t6501.F02 = OrderType.TRANSFER.orderType();
            t6501.F03 = T6501_F03.DQR;
            t6501.F04 = times;
            t6501.F05 = times;
            t6501.F07 = T6501_F07.YH;
            t6501.F08 = t6554.F02;
            t6501.F09 = getPTID(connection);
            t6501.F12 = "公益标捐款失败，资金退回给投资人";
            t6501.F13 = t6554.F04;
            t6501.F01 = insertT6501(connection, t6501);
            
            insertT6517(connection, t6501.F01, t6554.F02, t6554.F04);
            
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
            String TransAmt = t6554.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String InCustId = userCustId(t6554.F02);
            if (StringHelper.isEmpty(InCustId))
            {
                throw new LogicalException("捐款用户第三方账号不存在!");
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
            
            logger.info(String.format("公益标捐款失败,资金返还捐款用户，订单：%s 请求参数：%s", t6554.F01, map.toString()));
            
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
                        
                        logger.info(String.format("公益标捐款失败，资金返还捐款用户，订单：%s 返回参数：%s", t6554.F01, rst));
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
    
    /**
     * 查询捐款订单
     * <功能详细描述>
     * @param connection
     * @param orderId
     * @return
     * @throws Throwable
     */
    protected T6554 selectT6554(Connection connection, int orderId)
        throws Throwable
    {
        T6554 record = null;
        try (PreparedStatement statement =
            connection.prepareStatement("SELECT F01,F02,F03,F04,F05 FROM S65.T6554 WHERE F01=?"))
        {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6554();
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
            pstmt.setString(5, "公益标捐款失败，资金退回捐款用户");
            pstmt.setInt(6, FeeCode.PUBLIC_GOOD_ID);
            pstmt.execute();
        }
    }
    
    @Override
    public void donationFundDeal(int orderId)
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
                //                eT6102.F09 = "公益捐款资金返还";
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
    
    protected void updateT6101(Connection connection, BigDecimal F01, int F02, T6101_F03 F03)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6101 SET F06 = F06 + ?, F07 = ? WHERE F02 = ? AND F03 = ?"))
        {
            pstmt.setBigDecimal(1, F01);
            pstmt.setTimestamp(2, getCurrentTimestamp(connection));
            pstmt.setInt(3, F02);
            pstmt.setString(4, F03.name());
            pstmt.execute();
        }
    }
    
    @Override
    public String donationTgReturn(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6554 t6554 = selectT6554(connection, orderId);
            if (t6554 == null)
            {
                return "订单详细不存在！";
            }
            //查询标信息
            T6242 t6242 = selectT6242(connection, t6554.F03);
            if (t6242 == null)
            {
                return "标的信息不存在!";
            }
            ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
            boolean ajkt = BooleanParser.parse(configureProvider.getProperty(SystemVariable.BID_SFZJKT));
            if (!ajkt && t6554.F02 == t6242.F23)
            {
                return "不可捐本账号发起的公益标！";
            }
            if (t6242.F11 != T6242_F11.JKZ)
            {
                return "不是捐款中状态,不能捐款！";
            }
            if (t6554.F04.compareTo(t6242.F07) > 0)
            {
                return "捐款金额大于可捐金额！";
            }
            // 校验最低起投金额
            BigDecimal min = t6242.F06;
            if (t6554.F04.compareTo(min) < 0)
            {
                return "捐款金额不能低于最低起投金额！";
            }
            //修改可捐金额
            t6242.F07 = t6242.F07.subtract(t6554.F04);
            
            if (t6242.F07.compareTo(BigDecimal.ZERO) > 0 && t6242.F07.compareTo(min) < 0)
            {
                return "剩余可捐金额不能低于最低起捐金额！";
            }
            // 锁定投资人资金账户
            T6101 czzh = selectT6101(connection, t6554.F02, T6101_F03.WLZH, true);
            if (czzh == null)
            {
                return "投资人往来账户不存在！";
            }
            // 扣减出账账户金额
            czzh.F06 = czzh.F06.subtract(t6554.F04);
            if (czzh.F06.compareTo(BigDecimal.ZERO) < 0)
            {
                return "账户金额不足！";
            }
            T6101 rzzh = selectT6101(connection, t6242.F23, T6101_F03.WLZH, true);
            if (rzzh == null)
            {
                return "借款人往来账号不存在！";
            }
        }
        
        return null;
    }
    
    public T6242 selectT6242(Connection connection, int loadId)
        throws Throwable
    {
        T6242 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT T6242.F01, T6242.F02, T6242.F03, T6242.F04, T6242.F05, T6242.F06, T6242.F07, T6242.F08, T6242.F09, T6242.F10, T6242.F11, T6242.F12, T6242.F13, T6242.F14, T6242.F15, "
                + "T6242.F16, T6242.F17, T6242.F18, T6242.F19, T6242.F20, T6242.F21, T6242.F22, T6242.F23, T6242.F24"
                + "  FROM S62.T6242  WHERE T6242.F01 = ?"))
        {
            pstmt.setInt(1, loadId);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6242();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getString(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = resultSet.getInt(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = T6242_F10.parse(resultSet.getString(10));
                    record.F11 = T6242_F11.parse(resultSet.getString(11));
                    record.F12 = resultSet.getString(12);
                    record.F13 = resultSet.getTimestamp(13);
                    record.F14 = resultSet.getInt(14);
                    record.F15 = resultSet.getTimestamp(15);
                    record.F16 = resultSet.getTimestamp(16);
                    record.F17 = resultSet.getTimestamp(17);
                    record.F18 = resultSet.getTimestamp(18);
                    record.F19 = resultSet.getTimestamp(19);
                    record.F20 = resultSet.getTimestamp(20);
                    record.F21 = resultSet.getString(21);
                    record.F22 = resultSet.getString(22);
                    record.F23 = resultSet.getInt(23);
                    record.F24 = resultSet.getString(24);
                    
                }
            }
        }
        return record;
    }
}
