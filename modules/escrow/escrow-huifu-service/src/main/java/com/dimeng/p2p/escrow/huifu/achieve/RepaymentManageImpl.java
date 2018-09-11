package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S62.entities.T6252;
import com.dimeng.p2p.S62.enums.T6252_F09;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6506;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransStatQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.RepaymentManage;
import com.dimeng.p2p.order.TenderRepaymentExecutor;
import com.dimeng.util.DateHelper;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.DateTimeParser;

public class RepaymentManageImpl extends AbstractEscrowService implements RepaymentManage
{
    
    public RepaymentManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public int[] repayment()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try
            {
                Date currentDate = getCurrentDate(connection);
                // 查询所有用户还款金额
                T6252[] t6252_h = selectT6252User(connection, currentDate);
                // 查询当前时间所有未还的
                T6252[] t6252s = selectAllT6252(connection, currentDate);
                if (t6252s == null || t6252s.length == 0)
                {
                    return new int[0];
                }
                if (DateHelper.beforeDate(currentDate, t6252s[0].F08))
                {
                    throw new LogicalException("还未到还款时间，不能还款。");
                }
                // 还款账户余额
                BigDecimal userBalance = BigDecimal.ZERO;
                T6101 hkrAcount = null;
                int userId = 0;
                Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
                
                for (int i = 0; i < t6252_h.length; i++)
                {
                    String usrCustId = getUsrCustId(connection, t6252_h[i].F03);
                    if (StringHelper.isEmpty(usrCustId))
                    {
                        logger.info(String.format("%s自动还款异常，ID为%d的用户未进行资金托管 ",
                            DateTimeParser.format(new java.util.Date()),
                            t6252_h[i].F03));
                        continue;
                    }
                    if (i == 0)
                    {
                        userId = t6252_h[i].F03;
                        // 拿出借款人余额
                        hkrAcount = selectT6101(connection, userId, T6101_F03.WLZH);
                        userBalance = hkrAcount.F06.subtract(t6252_h[i].F07);
                    }
                    else
                    {
                        if (userId != t6252_h[i].F03)
                        {
                            userId = t6252_h[i].F03;
                            hkrAcount = selectT6101(connection, userId, T6101_F03.WLZH);
                            userBalance = hkrAcount.F06.subtract(t6252_h[i].F07);
                        }
                        else
                        {
                            userBalance = userBalance.subtract(t6252_h[i].F07);
                        }
                    }
                    
                    if (userBalance.compareTo(BigDecimal.ZERO) >= 0)
                    {
                        tempMap.put(t6252_h[i].F01, userId);
                    }
                }
                
                int[] orderIds = new int[t6252s.length];
                int index = 0;
                Timestamp currentTimestamp = getCurrentTimestamp(connection);
                // 遍历能全部还款数据
                for (Integer key : tempMap.keySet())
                {
                    T6252[] t6252_repayment = selectT6252Repayment(connection, currentDate, key, tempMap.get(key));
                    for (T6252 t6252 : t6252_repayment)
                    {
                        // 判断还款计划是否已还
                        boolean flag = false; // 标记还款计划是否已还
                        T6506[] t6506s = selectT6506s(connection, t6252);
                        if (t6506s != null && t6506s.length > 0)
                        {
                            String msg = null;
                            for (T6506 t6506 : t6506s)
                            {
                                if (t6506 == null)
                                {
                                    continue;
                                }
                                T6501 t6501 = selectT6501(connection, t6506.F01);
                                if (t6501 == null)
                                {
                                    msg =
                                        String.format("%s，自动还款订单%d不存在",
                                            DateTimeParser.format(currentTimestamp),
                                            t6506.F01);
                                    continue;
                                }
                                if (t6501 != null && T6501_F03.CG == t6501.F03)
                                {
                                    flag = true;
                                    msg =
                                        String.format("%s，自动还款订单%d已还款成功，无需再次还款",
                                            DateTimeParser.format(currentTimestamp),
                                            t6501.F01);
                                    break;
                                }
                                // 去汇付那边查询订单是否已执行
                                TransStatQueryEntity entity = query(t6501);
                                if (entity != null && "000".equals(entity.RespCode))
                                {
                                    if ("S".equals(entity.TransStat) || "P".equals(entity.TransStat))
                                    {
                                        flag = true;
                                        serviceResource.commit(connection);
                                        serviceResource.getResource(TenderRepaymentExecutor.class).confirm(t6501.F01,
                                            null);
                                        msg =
                                            String.format("%s，自动还款订单%d在汇付已执行成功，但平台执行失败，平台状态更新成功",
                                                DateTimeParser.format(currentTimestamp),
                                                t6501.F01);
                                        break;
                                    }
                                }
                            }
                            if (msg != null)
                            {
                                logger.info(msg);
                            }
                        }
                        if (!flag)
                        {
                            try (PreparedStatement ps =
                                connection.prepareStatement("UPDATE S62.T6252 SET F09 = ? WHERE F01 =?"))
                            {
                                ps.setString(1, T6252_F09.HKZ.name());
                                ps.setInt(2, t6252.F01);
                                ps.executeUpdate();
                            }
                            orderIds[index++] = addOrder(connection, t6252, currentTimestamp);
                        }
                    }
                }
                return orderIds;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    private String getUsrCustId(Connection connection, int accountId)
        throws Throwable
    {
        try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE F01 = ?"))
        {
            ps.setInt(1, accountId);
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
    
    /**
     * 订单交易状态查询 <功能详细描述>
     * 
     * @param t6501
     * @return
     * @throws Throwable
     */
    private TransStatQueryEntity query(T6501 t6501)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryTransStat";
        String MerCustId = merCustId;
        String OrdId = String.valueOf(t6501.F01);
        String OrdDate = DateParser.format(t6501.F04, "yyyyMMdd");
        String QueryTransType = "REPAYMENT";
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(QueryTransType);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("QueryTransType", QueryTransType);
        map.put("ChkValue", ChkValue);
        
        StringBuilder builder = new StringBuilder();
        logger.info(String.format("%s，还款对账请求参数：%s", DateTimeParser.format(new java.util.Date()), builder.toString()));
        String result = doPost(map);
        logger.info(String.format("%s，还款对账返回参数：%s", DateTimeParser.format(new java.util.Date()), result));
        
        Map<String, String> jsonMap = jsonParse(result);
        
        TransStatQueryEntity entity = new TransStatQueryEntity();
        entity.ChkValue = jsonMap.get("ChkValue");
        entity.CmdId = jsonMap.get("CmdId");
        entity.MerCustId = jsonMap.get("MerCustId");
        entity.OrdDate = jsonMap.get("OrdDate");
        entity.OrdId = jsonMap.get("OrdId");
        entity.PlainStr = jsonMap.get("PlainStr");
        entity.RespCode = jsonMap.get("RespCode");
        entity.RespDesc = jsonMap.get("RespDesc");
        entity.TransStat = jsonMap.get("TransStat");
        entity.Version = jsonMap.get("Version");
        entity.QueryTransType = jsonMap.get("QueryTransType");
        
        return entity;
    }
    
    private T6506[] selectT6506s(Connection connection, T6252 t6252)
        throws Throwable
    {
        List<T6506> t6506s = null;
        try (PreparedStatement ps =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S65.T6506 WHERE F04 = ? AND F05 = ? AND F07 = ?"))
        {
            ps.setInt(1, t6252.F11);
            ps.setInt(2, t6252.F06);
            ps.setInt(3, t6252.F05);
            try (ResultSet resultSet = ps.executeQuery())
            {
                while (resultSet.next())
                {
                    if (t6506s == null)
                    {
                        t6506s = new ArrayList<>();
                    }
                    T6506 t6506 = new T6506();
                    t6506.F01 = resultSet.getInt(1);
                    t6506.F02 = resultSet.getInt(2);
                    t6506.F03 = resultSet.getInt(3);
                    t6506.F04 = resultSet.getInt(4);
                    t6506.F05 = resultSet.getInt(5);
                    t6506.F06 = resultSet.getBigDecimal(6);
                    t6506.F07 = resultSet.getInt(7);
                    t6506s.add(t6506);
                }
            }
        }
        return t6506s == null ? null : t6506s.toArray(new T6506[t6506s.size()]);
    }
    
    private int addOrder(Connection connection, T6252 t6252, Timestamp currentTimestamp)
        throws Throwable
    {
        
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.BID_REPAYMENT.orderType();
        t6501.F03 = T6501_F03.DTJ;
        t6501.F04 = currentTimestamp;
        t6501.F05 = currentTimestamp;
        t6501.F07 = T6501_F07.HT;
        t6501.F08 = t6252.F03;
        t6501.F09 = getPTID(connection);
        t6501.F13 = t6252.F07;
        int orderId = insertT6501(connection, t6501);
        
        T6506 t6506 = new T6506();
        t6506.F01 = orderId;
        t6506.F02 = t6252.F04;
        t6506.F03 = t6252.F02;
        t6506.F04 = t6252.F11;
        t6506.F05 = t6252.F06;
        t6506.F06 = t6252.F07;
        t6506.F07 = t6252.F05;
        insertT6506(connection, t6506);
        
        return orderId;
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
    
    private T6252[] selectT6252Repayment(Connection connection, Date F08, int F01, int F03)
        throws SQLException
    {
        ArrayList<T6252> list = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S62.T6252 WHERE T6252.F02 = "
                + "(SELECT F02 FROM S62.T6252 T6252_1 WHERE T6252_1.F01 = ?) AND T6252.F03 = ? AND (T6252.F09 = ? OR T6252.F09 = ?) AND T6252.F08 <= ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F03);
            pstmt.setString(3, T6252_F09.WH.name());
            pstmt.setString(4, T6252_F09.HKZ.name());
            pstmt.setDate(5, F08);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                while (resultSet.next())
                {
                    T6252 record = new T6252();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = resultSet.getInt(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = resultSet.getDate(8);
                    record.F09 = T6252_F09.parse(resultSet.getString(9));
                    record.F10 = resultSet.getTimestamp(10);
                    record.F11 = resultSet.getInt(11);
                    if (list == null)
                    {
                        list = new ArrayList<>();
                    }
                    list.add(record);
                }
            }
        }
        return ((list == null || list.size() == 0) ? null : list.toArray(new T6252[list.size()]));
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
    
    private T6252[] selectT6252User(Connection connection, Date F08)
        throws SQLException
    {
        ArrayList<T6252> list = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT T6252.F01,T6252.F02,T6252.F03,SUM(T6252.F07) AS F07 FROM S62.T6252,S62.T6230 "
                + "WHERE (T6252.F09 = ? OR T6252.F09 = ?) AND T6252.F08 <= ? AND T6252.F02 = T6230.F01 GROUP BY T6252.F02 ORDER BY T6252.F03"))
        {
            pstmt.setString(1, T6252_F09.WH.name());
            pstmt.setString(2, T6252_F09.HKZ.name());
            pstmt.setDate(3, F08);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                while (resultSet.next())
                {
                    T6252 record = new T6252();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F07 = resultSet.getBigDecimal(4);
                    if (list == null)
                    {
                        list = new ArrayList<>();
                    }
                    list.add(record);
                }
            }
        }
        return ((list == null || list.size() == 0) ? null : list.toArray(new T6252[list.size()]));
    }
    
    private T6252[] selectAllT6252(Connection connection, Date F08)
        throws SQLException
    {
        ArrayList<T6252> list = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S62.T6252 WHERE T6252.F08 <= ? AND (T6252.F09 = ? OR T6252.F09 = ?) ORDER BY F02,F06 ASC"))
        {
            pstmt.setDate(1, F08);
            pstmt.setString(2, T6252_F09.WH.name());
            pstmt.setString(3, T6252_F09.HKZ.name());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                while (resultSet.next())
                {
                    T6252 record = new T6252();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getInt(5);
                    record.F06 = resultSet.getInt(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = resultSet.getDate(8);
                    record.F09 = T6252_F09.parse(resultSet.getString(9));
                    record.F10 = resultSet.getTimestamp(10);
                    record.F11 = resultSet.getInt(11);
                    if (list == null)
                    {
                        list = new ArrayList<>();
                    }
                    list.add(record);
                }
            }
        }
        return ((list == null || list.size() == 0) ? null : list.toArray(new T6252[list.size()]));
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
    
    @Override
    protected Timestamp getCurrentTimestamp(Connection connection)
        throws Throwable
    {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT CURRENT_TIMESTAMP()"))
        {
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getTimestamp(1);
                }
            }
        }
        return null;
    }
    
}
