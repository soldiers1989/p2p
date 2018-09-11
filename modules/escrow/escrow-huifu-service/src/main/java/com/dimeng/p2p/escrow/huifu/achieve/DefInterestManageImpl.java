package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.S62.entities.T6238;
import com.dimeng.p2p.S62.entities.T6252;
import com.dimeng.p2p.S62.enums.T6231_F19;
import com.dimeng.p2p.S62.enums.T6252_F09;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6506;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransStatQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.DefInterestManage;
import com.dimeng.p2p.order.TenderRepaymentExecutor;
import com.dimeng.util.DateHelper;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.DateTimeParser;

/**
 * 
 * 自动还款管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class DefInterestManageImpl extends AbstractEscrowService implements DefInterestManage
{
    
    public DefInterestManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public void calculate()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            serviceResource.openTransactions(connection);
            try
            {
                Date currentDate = getCurrentDate(connection);
                // 查询未还和还款中还款计划，并在汇付那边对账，确认是否已还
                T6252[] checkT6252s = selectCheckT6252s(connection, currentDate);
                if (checkT6252s == null || checkT6252s.length == 0)
                {
                    return;
                }
                for (T6252 t6252 : checkT6252s)
                {
                    // 判断还款计划是否已还
                    T6506[] t6506s = selectT6506s(connection, t6252);
                    if (t6506s != null && t6506s.length > 0)
                    {
                        String msg = null;
                        for (T6506 t6506 : t6506s)
                        {
                            java.util.Date currentDateTime = new java.util.Date();
                            if (t6506 == null)
                            {
                                continue;
                            }
                            T6501 t6501 = selectT6501(connection, t6506.F01);
                            if (t6501 == null)
                            {
                                msg =
                                    String.format("%s生成逾期罚息，自动还款订单%d不存在",
                                        DateTimeParser.format(currentDateTime),
                                        t6506.F01);
                                continue;
                            }
                            if (t6501 != null && T6501_F03.CG == t6501.F03)
                            {
                                msg =
                                    String.format("%s，自动还款订单%d已还款成功，无需再次还款",
                                        DateTimeParser.format(currentDateTime),
                                        t6501.F01);
                                break;
                            }
                            // 去汇付那边查询订单是否已执行
                            TransStatQueryEntity entity = query(t6501);
                            if (entity != null && "000".equals(entity.RespCode))
                            {
                                if ("S".equals(entity.TransStat) || "P".equals(entity.TransStat))
                                {
                                    serviceResource.getResource(TenderRepaymentExecutor.class).confirm(t6501.F01, null);
                                    msg =
                                        String.format("%s，自动还款订单%d在汇付已执行成功，但平台执行失败，平台状态更新成功",
                                            DateTimeParser.format(currentDateTime),
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
                }
                // 计算逾期罚息
                T6252[] t6252s = selectAllT6252(connection, currentDate);
                if (t6252s == null || t6252s.length == 0)
                {
                    serviceResource.commit(connection);
                    return;
                }
                Map<Integer, T6238> cache = new HashMap<>();
                T6252 defaultInterest = new T6252();
                defaultInterest.F05 = FeeCode.TZ_FX;
                BigDecimal zero = new BigDecimal(0);
                Map<String, String> map = new HashMap<String, String>();
                for (T6252 t6252 : t6252s)
                {
                    T6238 t6238 = selectT6238(connection, cache, t6252.F02);
                    if (t6238 == null || t6238.F04.compareTo(zero) <= 0)
                    {
                        continue;
                    }
                    int days =
                        (int)Math.floor((currentDate.getTime() - t6252.F08.getTime()) / DateHelper.DAY_IN_MILLISECONDS);
                    defaultInterest.F02 = t6252.F02;
                    defaultInterest.F03 = t6252.F03;
                    defaultInterest.F04 = t6252.F04;
                    defaultInterest.F06 = t6252.F06;
                    defaultInterest.F07 = t6252.F07.multiply(t6238.F04).multiply(new BigDecimal(days));
                    defaultInterest.F08 = t6252.F08;
                    defaultInterest.F09 = t6252.F09;
                    defaultInterest.F11 = t6252.F11;
                    int id = 0;
                    try (PreparedStatement ps =
                        connection.prepareStatement("SELECT F01 FROM S62.T6252 WHERE F05=? AND F06=? AND F11=?"))
                    {
                        ps.setInt(1, FeeCode.TZ_FX);
                        ps.setInt(2, t6252.F06);
                        ps.setInt(3, t6252.F11);
                        try (ResultSet rs = ps.executeQuery())
                        {
                            if (rs.next())
                            {
                                id = rs.getInt(1);
                            }
                        }
                    }
                    if (id <= 0)
                    {
                        insertT6252(connection, defaultInterest);
                    }
                    else
                    {
                        updateT6252(connection, defaultInterest.F07, id);
                    }
                    if (days > 0)
                    {
                        T6231_F19 t6231_F19 = T6231_F19.F;
                        if (days <= 30)
                        {
                            t6231_F19 = T6231_F19.S;
                        }
                        else if (days > 30)
                        {
                            t6231_F19 = T6231_F19.YZYQ;
                        }
                        try (PreparedStatement pstmt =
                            connection.prepareStatement("UPDATE S62.T6231 SET F19 = ? WHERE F01 = ?"))
                        {
                            pstmt.setString(1, t6231_F19.name());
                            pstmt.setInt(2, t6252.F02);
                            pstmt.execute();
                        }
                    }
                    
                    String keyStr =
                        String.valueOf(t6252.F02) + String.valueOf(t6252.F03) + String.valueOf(t6252.F05)
                            + String.valueOf(t6252.F06);
                    if (map.get(keyStr) == null)
                    {
                        // 更新用户信用档案
                        if (days == 31)
                        {
                            try (PreparedStatement ps =
                                connection.prepareStatement("UPDATE S61.T6144 SET F03 =F03 + ? WHERE F01 = ?"))
                            {
                                ps.setInt(1, 1);
                                ps.setInt(2, t6252.F03);
                                ps.executeUpdate();
                            }
                        }
                        if (days == 1)
                        {
                            try (PreparedStatement ps =
                                connection.prepareStatement("UPDATE S61.T6144 SET F02=F02+? WHERE F01=?"))
                            {
                                ps.setInt(1, 1);
                                ps.setInt(2, t6252.F03);
                                ps.executeUpdate();
                            }
                        }
                        else
                        {
                            try (PreparedStatement ps =
                                connection.prepareStatement("UPDATE S61.T6144 SET F04 = ? WHERE F01 = ?"))
                            {
                                ps.setInt(1, days);
                                ps.setInt(2, t6252.F03);
                                ps.executeUpdate();
                            }
                        }
                        map.put(keyStr, "1");
                    }
                }
                map.clear();
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    /**
     * 订单交易状态查询 <功能详细描述>
     * 
     * @param orderId
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
        for (Entry<String, String> entry : map.entrySet())
        {
            builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
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
    
    private T6252[] selectAllT6252(Connection connection, Date currentDate)
        throws SQLException
    {
        ArrayList<T6252> list = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, SUM(F07), F08, F09, F11 FROM S62.T6252 WHERE (T6252.F05 = ? OR T6252.F05=?) AND T6252.F08 < ?  AND (T6252.F09 = 'WH' OR T6252.F09='HKZ') GROUP BY F02,F04,F06,F11"))
        {
            pstmt.setInt(1, FeeCode.TZ_BJ);
            pstmt.setInt(2, FeeCode.TZ_LX);
            pstmt.setDate(3, currentDate);
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
                    record.F11 = resultSet.getInt(10);
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
    
    private T6252[] selectCheckT6252s(Connection connection, Date currentDate)
        throws SQLException
    {
        ArrayList<T6252> list = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F11 FROM S62.T6252 WHERE T6252.F08 < ?  AND (T6252.F09 = 'WH' OR T6252.F09='HKZ')"))
        {
            pstmt.setDate(1, currentDate);
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
                    record.F11 = resultSet.getInt(10);
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
    
    private T6238 selectT6238(Connection connection, Map<Integer, T6238> cache, int F01)
        throws SQLException
    {
        Integer key = Integer.valueOf(F01);
        T6238 record = cache.get(key);
        if (record != null)
        {
            return record;
        }
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04 FROM S62.T6238 WHERE T6238.F01 = ? "))
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
                    cache.put(key, record);
                }
            }
        }
        return record;
    }
    
    private int insertT6252(Connection connection, T6252 entity)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S62.T6252 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?, F11 = ?",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, entity.F02);
            pstmt.setInt(2, entity.F03);
            pstmt.setInt(3, entity.F04);
            pstmt.setInt(4, entity.F05);
            pstmt.setInt(5, entity.F06);
            pstmt.setBigDecimal(6, entity.F07);
            pstmt.setDate(7, entity.F08);
            pstmt.setString(8, entity.F09.name());
            pstmt.setInt(9, entity.F11);
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
    
    private void updateT6252(Connection connection, BigDecimal amount, int id)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S62.T6252 SET F07 = ? WHERE F01=? AND (F09='WH' OR F09='HKZ')",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setBigDecimal(1, amount);
            pstmt.setInt(2, id);
            pstmt.execute();
        }
    }
    
}
