package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S61.entities.T6119;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.S61.entities.T6165;
import com.dimeng.p2p.S61.enums.T6165_F02;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.CorpRegQueryManage;

/**
 * 
 * 企业开户查询管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class CorpRegQueryManageImpl extends AbstractEscrowService implements CorpRegQueryManage
{
    
    public CorpRegQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class CorpRegQueryManageFactory implements ServiceFactory<CorpRegQueryManage>
    {
        
        @Override
        public CorpRegQueryManage newInstance(ServiceResource serviceResource)
        {
            return new CorpRegQueryManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public CorpQueryEntity query(String busiCode)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "CorpRegisterQuery";
        String MerCustId = merCustId;
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(busiCode);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("BusiCode", busiCode);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        System.out.println(result);
        CorpQueryEntity entity = jsonReflectParser(result, CorpQueryEntity.class);
        
        return entity;
    }
    
    @Override
    public void updateCorpInfo(CorpQueryEntity entity, T6161 t6161, T6165 t6165, int id)
    {
        //6165表插入数据，企业状态表
        if (t6165 == null)
        {
            try
            {
                insertZhshzt(id, T6165_F02.parse(entity.AuditStat), T6165_F02.parse(entity.AuditStat).getChineseName());
            }
            catch (Throwable e)
            {
                logger.error(e, e);
            }
        }
        else
        {
            try
            {
                updateZhshzt(id, T6165_F02.parse(entity.AuditStat), T6165_F02.parse(entity.AuditStat).getChineseName());
            }
            catch (Throwable e)
            {
                logger.error(e, e);
            }
        }
        
        if (!"Y".equals(entity.AuditStat))
        {
            return;
        }
        //往6119表插数据
        try
        {
            T6119 t6119 = selectT6119(id);
            if (t6119 == null)
            {
                insertT6119(id, PaymentInstitution.CHINAPNR.getInstitutionCode(), entity.UsrCustId);
            }
        }
        catch (Throwable e)
        {
            logger.error(e, e);
        }
        
    }
    
    private void insertZhshzt(int F01, T6165_F02 F02, String F03)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("INSERT INTO S61.T6165 SET F01 = ?, F02 = ?, F03 = ? ON DUPLICATE KEY UPDATE F01 = VALUES(F01), F02 = VALUES(F02), F03 = VALUES(F03)"))
            {
                pstmt.setInt(1, F01);
                pstmt.setString(2, F02.name());
                pstmt.setString(3, F03);
                pstmt.execute();
            }
        }
    }
    
    private void updateZhshzt(int F01, T6165_F02 F02, String F03)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S61.T6165 SET F02 = ?, F03 = ?, F04 = ? WHERE F01 = ?"))
            {
                pstmt.setString(1, F02.name());
                pstmt.setString(2, F03);
                pstmt.setTimestamp(3, getCurrentTimestamp(connection));
                pstmt.setInt(3, F01);
                pstmt.execute();
            }
        }
    }
    
    /**
     * 查询T6119
     * 
     * @return
     * @throws Throwable
     */
    private T6119 selectT6119(int id)
        throws Throwable
    {
        T6119 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03 FROM S61.T6119 WHERE T6119.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, id);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6119();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getString(3);
                    }
                }
            }
        }
        return record;
    }
    
    /**
     * 插入第三方托管账号
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    private void insertT6119(int F01, int F02, String F03)
        throws SQLException
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("INSERT INTO S61.T6119 SET F01 = ?, F02 = ?, F03 = ?"))
            {
                pstmt.setInt(1, F01);
                pstmt.setInt(2, F02);
                pstmt.setString(3, F03);
                pstmt.execute();
            }
        }
    }
    
    @Override
    public T6165 getZhshzt(int id)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6165 record = null;
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01,F02,F03 FROM S61.T6165 WHERE T6165.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, id);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6165();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = T6165_F02.parse(resultSet.getString(2));
                        record.F03 = resultSet.getString(3);
                    }
                }
            }
            return record;
        }
    }
    
}
