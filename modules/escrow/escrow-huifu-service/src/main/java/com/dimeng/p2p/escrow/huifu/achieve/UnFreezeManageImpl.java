package com.dimeng.p2p.escrow.huifu.achieve;

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

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6515;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.UnFreezeCond;
import com.dimeng.p2p.escrow.huifu.entity.freeze.UnFreezeEntity;
import com.dimeng.p2p.escrow.huifu.service.UnFreezeManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;

public class UnFreezeManageImpl extends AbstractEscrowService implements UnFreezeManage
{
    
    public UnFreezeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class UnFreezeManageFactory implements ServiceFactory<UnFreezeManage>
    {
        
        @Override
        public UnFreezeManage newInstance(ServiceResource serviceResource)
        {
            return new UnFreezeManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public UnFreezeEntity unFreezeReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        UnFreezeEntity entity = new UnFreezeEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.trxId = request.getParameter("TrxId");
        entity.merPriv = request.getParameter("MerPriv");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.trxId);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public UnFreezeEntity sendUnFreeze(UnFreezeCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UsrUnFreeze";
        String MerCustId = merCustId;
        String OrdId = cond.ordId();
        String OrdDate = cond.ordDate();
        String TrxId = cond.trxId();
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_UNFREEZE);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(TrxId);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("TrxId", TrxId);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        logger.info(String.format("汇付 - 投资失败，解冻资金订单号%s，请求参数%s：", OrdId, map));
        
        String result = doPost(map);
        
        logger.info(String.format("汇付 - 投资失败，解冻资金订单号%s，回调结果%s：", OrdId, result));
        
        Map<String, String> jsonMap = jsonParse(result);
        
        UnFreezeEntity entity = new UnFreezeEntity();
        entity.bgRetUrl = jsonMap.get("BgRetUrl");
        entity.chkValue = jsonMap.get("ChkValue");
        entity.cmdId = jsonMap.get("CmdId");
        entity.merCustId = jsonMap.get("MerCustId");
        entity.ordDate = jsonMap.get("OrdDate");
        entity.ordId = jsonMap.get("OrdId");
        entity.respCode = jsonMap.get("RespCode");
        entity.respDesc = jsonMap.get("RespDesc");
        entity.trxId = jsonMap.get("TrxId");
        
        return entity;
    }
    
    @Override
    public String getTrxId(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT F10 FROM S65.T6501 WHERE F01 = ? AND F02 = ?"))
            {
                ps.setInt(1, ordId);
                ps.setInt(2, OrderType.FREEZE.orderType());
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getString(1);
                    }
                }
            }
        }
        throw new LogicalException("冻结订单不存在");
    }
    
    @Override
    public T6501 addUnfreezeOrder(int ordId)
        throws Throwable
    {
        T6501 t6501 = null;
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                
                T6515 t6515 = selectT6515(connection, ordId);
                int accountId = selectUserId(connection, t6515.F02);
                
                Timestamp times = getCurrentTimestamp(connection);
                
                t6501 = new T6501();
                t6501.F02 = OrderType.UNFREEZE.orderType();
                t6501.F03 = T6501_F03.DQR;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.HT;
                t6501.F08 = accountId;
                t6501.F09 = serviceResource.getSession().getAccountId();
                t6501.F01 = insertT6501(connection, t6501);
                t6501.F13 = t6515.F03;
                insertT6516(connection, t6501.F01, t6515.F02, t6515.F03);
                
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
        return t6501;
    }
    
    private int selectUserId(Connection connection, int F01)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F08 FROM S65.T6501 WHERE T6501.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private T6515 selectT6515(Connection connection, int F01)
        throws SQLException
    {
        T6515 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03 FROM S65.T6515 WHERE T6515.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
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
    
    @Override
    public void updateTrxId(String trxId, int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F06 = ?, F10 = ? WHERE F01 = ?"))
            {
                ps.setString(1, T6501_F03.CG.name());
                ps.setTimestamp(2, getCurrentTimestamp(connection));
                ps.setString(3, trxId);
                ps.setInt(4, orderId);
                ps.executeUpdate();
            }
        }
    }
    
    /**
     * 生成解冻订单
     */
    @Override
    public T6501 addUnfreezeOrder(int ordId, BigDecimal amount)
        throws Throwable
    {
        T6501 t6501 = null;
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                
                T6515 t6515 = selectT6515(connection, ordId);
                int accountId = selectUserId(connection, t6515.F02);
                
                Timestamp times = getCurrentTimestamp(connection);
                
                t6501 = new T6501();
                t6501.F02 = OrderType.UNFREEZE.orderType();
                t6501.F03 = T6501_F03.DQR;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.HT;
                t6501.F08 = accountId;
                t6501.F09 = 0;
                t6501.F13 = amount;
                t6501.F01 = insertT6501(connection, t6501);
                
                insertT6516(connection, t6501.F01, t6515.F02, amount);
                
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
            
        }
        return t6501;
    }
    
    @Override
    public void updateOrderFailure(int ordId, String trxId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F10 = ? WHERE F01 = ?"))
            {
                ps.setString(1, T6501_F03.SB.name());
                ps.setString(2, trxId);
                ps.setInt(3, ordId);
                ps.executeUpdate();
            }
        }
    }
    
}
