/*
 * 文 件 名:  HFOfflineChargeManageImpl.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6509;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.S71.entities.T7150;
import com.dimeng.p2p.S71.enums.T7150_F03;
import com.dimeng.p2p.S71.enums.T7150_F05;
import com.dimeng.p2p.escrow.huifu.service.HFOfflineChargeManage;

/**
 * 线下充值审核管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public class HFOfflineChargeManageImpl extends AbstractEscrowService implements HFOfflineChargeManage
{
    
    public HFOfflineChargeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    /** {@inheritDoc} */
    
    @Override
    public int checkCharge(int id, boolean passed)
        throws Throwable
    {
        if (id <= 0)
        {
            throw new LogicalException("充值记录不存在！");
        }
        int orderId = 0;
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                
                T7150 t7150 = selectT7150(id);
                if (t7150 == null)
                {
                    throw new LogicalException("充值记录不存在！");
                }
                if (t7150.F05 != T7150_F05.DSH)
                {
                    throw new LogicalException("不是待审核状态,不能审核通过！");
                }
                if (passed)
                {
                    orderId = addOfflineChargeOrder(connection, t7150);
                }
                else
                {
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S71.T7150 SET F05 = ?, F09 = ?, F10 = ? WHERE F01 = ?"))
                    {
                        ps.setString(1, T7150_F05.YQX.name());
                        ps.setInt(2, serviceResource.getSession().getAccountId());
                        ps.setTimestamp(3, getCurrentTimestamp(connection));
                        ps.setInt(4, id);
                        ps.executeUpdate();
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
        return orderId;
    }
    
    /**
     * 添加线下充值订单
     * 
     * @param connection
     * @param t7150
     * @return
     * @throws Throwable
     */
    private int addOfflineChargeOrder(Connection connection, T7150 t7150)
        throws Throwable
    {
        //int orderId = 0;
        T6501 t6501 = new T6501();
        t6501.F02 = OrderType.OFFLINECHARGE.orderType();
        t6501.F03 = T6501_F03.DTJ;
        t6501.F04 = getCurrentTimestamp(connection);
        t6501.F07 = T6501_F07.HT;
        t6501.F08 = t7150.F02;
        t6501.F09 = serviceResource.getSession().getAccountId();
        t6501.F13 = t7150.F04;
        int orderId = insertT6501(connection, t6501);
        /*try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6501 SET F02 = ?,F03 = ?, F04 = ?, F07 = ?, F09 = ?, F08 = ? ",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, OrderType.OFFLINECHARGE.orderType());
            pstmt.setString(2, T6501_F03.DTJ.name());
            pstmt.setTimestamp(3, getCurrentTimestamp(connection));
            pstmt.setString(4, T6501_F07.HT.name());
            pstmt.setInt(5, serviceResource.getSession().getAccountId());
            pstmt.setInt(6, t7150.F02);
            pstmt.execute();
            try (ResultSet resultSet = pstmt.getGeneratedKeys();)
            {
                if (resultSet.next())
                {
                    orderId = resultSet.getInt(1);
                }
            }
        }*/
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6509 SET F01 = ?, F02 = ?, F03 = ?, F06 = ?"))
        {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, t7150.F02);
            pstmt.setBigDecimal(3, t7150.F04);
            pstmt.setInt(4, t7150.F01);
            pstmt.execute();
        }
        
        // 更新线下充值申请的审核人、审核时间，在订单提交成功后才更新申请的状态为“已充值”
        try (PreparedStatement ps =
            connection.prepareStatement("UPDATE S71.T7150 SET F05 = ?, F09 = ?, F10 = ? WHERE F01 = ?"))
        {
            ps.setString(1, T7150_F05.SHZ.name());
            ps.setInt(2, serviceResource.getSession().getAccountId());
            ps.setTimestamp(3, getCurrentTimestamp(connection));
            ps.setInt(4, t7150.F01);
            ps.executeUpdate();
        }
        return orderId;
        
    }
    
    @Override
    public void updatePtRecord(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            
            T6509 t6509 = selectT6509(connection, orderId);
            if (null == t6509)
            {
                throw new LogicalException("线下充值订单不存在!");
            }
            
            T7150 t7150 = selectT7150(t6509.F06);
            if (null == t7150)
            {
                throw new LogicalException("线下充值申请不存在!");
            }
            //平台ID
            int ptId = getPTID(connection);
            T6101 ptWLZH = selectT6101(connection, ptId, T6101_F03.WLZH, true);
            if (ptWLZH == null)
            {
                throw new LogicalException("平台往来账户不存在!");
            }
            if (ptWLZH.F06.compareTo(t6509.F03) < 0)
            {
                throw new LogicalException("平台往来账户余额不足!");
            }
            
            try
            {
                //开启事物
                serviceResource.openTransactions(connection);
                
                // 更新用户账号信息
                try (PreparedStatement pstmt =
                    connection.prepareStatement("UPDATE S61.T6101 SET F06 = F06 - ?, F07 = CURRENT_TIMESTAMP() WHERE F01 = ?"))
                {
                    pstmt.setBigDecimal(1, t6509.F03);
                    pstmt.setInt(2, ptWLZH.F01);
                    pstmt.executeUpdate();
                }
                
                // 插入资金交易记录，充值
                try (PreparedStatement pstmt =
                    connection.prepareStatement("INSERT INTO S61.T6102 SET F02 = ?, F03 = ?, F04 = ?, F05 = CURRENT_TIMESTAMP(), F07 = ?, F08 = ?, F09 = ?"))
                {
                    pstmt.setInt(1, ptWLZH.F01);
                    pstmt.setInt(2, FeeCode.CZ_XX);
                    pstmt.setInt(3, ptWLZH.F01);
                    pstmt.setBigDecimal(4, t6509.F03);
                    pstmt.setBigDecimal(5, ptWLZH.F06.subtract(t6509.F03));
                    pstmt.setString(6, String.format("线下充值:%s", t7150.F08));
                    pstmt.execute();
                }
                
                //提交事物
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                //异常回滚
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    /**
     * 查询线下充值订单记录
     * <功能详细描述>
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    protected T6509 selectT6509(Connection connection, int F01)
        throws SQLException
    {
        T6509 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05,F06 FROM S65.T6509 WHERE T6509.F01 = ? FOR UPDATE"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6509();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getBigDecimal(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getInt(6);
                }
            }
        }
        return record;
    }
    
    /**
     * 查询线下充值申请记录
     * <功能详细描述>
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    @Override
    public T7150 selectT7150(int F01)
        throws Throwable
    {
        T7150 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S71.T7150 WHERE T7150.F01 = ? FOR UPDATE"))
            {
                pstmt.setInt(1, F01);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T7150();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = T7150_F03.parse(resultSet.getString(3));
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F05 = T7150_F05.parse(resultSet.getString(5));
                        record.F06 = resultSet.getInt(6);
                        record.F07 = resultSet.getTimestamp(7);
                        record.F08 = resultSet.getString(8);
                        record.F09 = resultSet.getInt(9);
                        record.F10 = resultSet.getTimestamp(10);
                        record.F11 = resultSet.getString(11);
                    }
                }
            }
        }
        
        return record;
    }
    
    @Override
    public void updateT7150(int orderId, T7150_F05 status)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            int sqId = 0;
            //根据订单号查找线下申请ID
            try (PreparedStatement pstmt = connection.prepareStatement("SELECT F06 FROM S65.T6509 WHERE T6509.F01 = ?"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        sqId = resultSet.getInt(1);
                    }
                }
            }
            
            try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S71.T7150 SET F05 = ? WHERE F01 = ?"))
            {
                pstmt.setString(1, status.name());
                pstmt.setInt(2, sqId);
                pstmt.executeUpdate();
            }
        }
        
    }
    
}
