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
import com.dimeng.framework.service.query.ArrayParser;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.S61.entities.T6165;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.S61.enums.T6110_F08;
import com.dimeng.p2p.S61.enums.T6110_F10;
import com.dimeng.p2p.S61.enums.T6110_F12;
import com.dimeng.p2p.S61.enums.T6110_F13;
import com.dimeng.p2p.S61.enums.T6165_F02;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserAcctQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserBankCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserInfoQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.util.StringHelper;

/**
 * 余额管理 <一句话功能简述> <功能详细描述>
 * 
 * @author lingyuanjie
 * @version [版本号, 2016年7月21日]
 */
public class UserAcctQueryManageImpl extends AbstractEscrowService implements UserAcctQueryManage
{
    
    public UserAcctQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class UserAccountMangeFactory implements ServiceFactory<UserAcctQueryManage>
    {
        
        @Override
        public UserAcctQueryManage newInstance(ServiceResource serviceResource)
        {
            return new UserAcctQueryManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public UserAcctQueryEntity accountInfo(int accountId)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryBalanceBg";
        String MerCustId = merCustId;
        String UsrCustId = userCustId(accountId);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);// System.out.println(result);
        final UserAcctQueryEntity entity = jsonReflectParser(result, UserAcctQueryEntity.class);
        
        return entity;
    }
    
    @Override
    public PagingResult<T6110> selectUserList(String name, Paging paging, String userTag)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            StringBuilder builder =
                new StringBuilder(
                    "SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13 FROM S61.T6110 WHERE T6110.F13 = ? AND T6110.F01 != (SELECT F01 FROM S71.T7101 LIMIT 1)");
            List<Object> params = new ArrayList<>();
            params.add(T6110_F13.F.name());
            if (!StringHelper.isEmpty(name))
            {
                builder.append(" AND T6110.F02 LIKE ?");
                params.add("%" + name + "%");
            }
            if (!StringHelper.isEmpty(userTag))
            {
                if ("ZRR".equals(userTag))
                {//个人
                    builder.append(" AND T6110.F06 = 'ZRR'");
                }
                else if ("QY".equals(userTag))
                {//企业
                    builder.append(" AND T6110.F06 = 'FZRR' AND T6110.F10 = 'F'");
                }
                else if ("JG".equals(userTag))
                {//机构
                    builder.append(" AND T6110.F06 = 'FZRR' AND T6110.F10 = 'S'");
                }
            }
            builder.append(" ORDER BY T6110.F09 DESC");
            return selectPaging(connection, new ArrayParser<T6110>()
            {
                
                @Override
                public T6110[] parse(ResultSet resultSet)
                    throws SQLException
                {
                    ArrayList<T6110> list = null;
                    while (resultSet.next())
                    {
                        T6110 record = new T6110();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getString(2);
                        record.F03 = resultSet.getString(3);
                        record.F04 = resultSet.getString(4);
                        record.F05 = resultSet.getString(5);
                        record.F06 = T6110_F06.parse(resultSet.getString(6));
                        record.F07 = T6110_F07.parse(resultSet.getString(7));
                        record.F08 = T6110_F08.parse(resultSet.getString(8));
                        record.F09 = resultSet.getTimestamp(9);
                        record.F10 = T6110_F10.parse(resultSet.getString(10));
                        record.F11 = resultSet.getShort(11);
                        record.F12 = T6110_F12.parse(resultSet.getString(12));
                        record.F13 = T6110_F13.parse(resultSet.getString(13));
                        if (list == null)
                        {
                            list = new ArrayList<>();
                        }
                        list.add(record);
                    }
                    return ((list == null || list.size() == 0) ? null : list.toArray(new T6110[list.size()]));
                }
            }, paging, builder.toString(), params);
        }
    }
    
    @Override
    public UserBankCardEntity cardInfo(int accountId)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryCardInfo";
        String MerCustId = merCustId;
        String UsrCustId = userCustId(accountId);
        if (StringHelper.isEmpty(UsrCustId))
        {
            logger.info("------- 查询用户第三方银行卡信息：用户未注册第三方！--------");
            return null;
        }
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        UserBankCardEntity entity = jsonReflectParser(result, UserBankCardEntity.class);
        
        return entity;
    }
    
    @Override
    public T6165[] selectQyT6165s()
        throws Throwable
    {
        List<T6165> records = new ArrayList<T6165>();
        try (Connection connection = this.getConnection())
        {
            try (PreparedStatement pstmt = connection.prepareStatement("SELECT F01,F02 FROM S61.T6165 WHERE F02= ?"))
            {
                pstmt.setString(1, T6165_F02.T.name());
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    while (resultSet.next())
                    {
                        T6165 recorde = new T6165();
                        recorde.F01 = resultSet.getInt(1);
                        recorde.F02 = T6165_F02.valueOf(resultSet.getString(2));
                        records.add(recorde);
                    }
                }
            }
        }
        T6165[] t6165s = new T6165[records.size()];
        records.toArray(t6165s);
        return t6165s;
    }
    
    @Override
    public CorpQueryEntity corpUserInfo(int accountId)
        throws Throwable
    {
        //查询企业信息
        T6161 t6161 = selectT6161(accountId);
        
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "CorpRegisterQuery";
        String MerCustId = merCustId;
        String BusiCode = "N".equals(t6161.F20) ? t6161.F03 : t6161.F19;
        if (StringHelper.isEmpty(BusiCode))
        {
            logger.info("-------------> 企业开户查询： 请求参数 营业执照编号（BusiCode）为空！ ");
            return null;
        }
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(BusiCode);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("BusiCode", BusiCode);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        CorpQueryEntity entity = jsonReflectParser(result, CorpQueryEntity.class);
        
        try (Connection connection = this.getConnection())
        {
            if (null != entity)
            {
                if (null != entity.AuditStat && T6165_F02.Y.name().equals(entity.AuditStat))
                {
                    if (selectT6119(accountId, PaymentInstitution.CHINAPNR.getInstitutionCode(), entity.UsrCustId) <= 0)
                    {
                        insertT6119(connection,
                            accountId,
                            PaymentInstitution.CHINAPNR.getInstitutionCode(),
                            entity.UsrCustId);
                    }
                    updateQyStatus(connection, accountId, T6165_F02.Y);
                }
                else if (T6165_F02.R.name().equals(entity.AuditStat) || T6165_F02.F.name().equals(entity.AuditStat))
                {
                    updateQyStatus(connection, accountId, T6165_F02.F);
                }
            }
            else
            {
                // 已失败
                updateQyStatus(connection, accountId, T6165_F02.T);
            }
        }
        
        return entity;
    }
    
    @Override
    public UserInfoQueryEntity memberUserInfo(int accountId)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryUsrInfo";
        String MerCustId = merCustId;
        String CertId = selectT6141(accountId);
        if (StringHelper.isEmpty(CertId))
        {
            logger.info("--> 开户信息同步，查询个人用户第三方信息失败，原因：平台T6141未保存身份证号（CertId）");
            return null;
        }
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(CertId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("CertId", CertId);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        UserInfoQueryEntity entity = jsonReflectParser(result, UserInfoQueryEntity.class);
        
        return entity;
    }
    
    @Override
    public UserInfoQueryEntity userAutoBidInfo(int accountId)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryTenderPlan";
        String MerCustId = merCustId;
        String UsrCustId = this.selectT6119(accountId);
        if (StringHelper.isEmpty(UsrCustId))
        {
            logger.info("---> 查询用户自动投标计划失败，原因：用户尚未注册第三方账号！");
            return null;
        }
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        UserInfoQueryEntity entity = jsonReflectParser(result, UserInfoQueryEntity.class);
        
        return entity;
    }
    
    /**
     * 修改企业账号审核状态
     * 
     * @param connection
     * @param F01
     * @param t6165_F02
     * @throws Throwable
     */
    public void updateQyStatus(Connection connection, int F01, T6165_F02 t6165_F02)
        throws Throwable
    {
        try (PreparedStatement sts = connection.prepareStatement("UPDATE S61.T6165 SET F02=? WHERE F01=?"))
        {
            sts.setString(1, t6165_F02.name());
            sts.setInt(2, F01);
            sts.execute();
        }
    }
    
    /**
     * 查询企业 营业执照登记注册号或社会信用代码
     * <功能详细描述>
     * @param accountId
     * @return
     * @throws Throwable
     */
    protected T6161 selectT6161(int accountId)
        throws Throwable
    {
        T6161 t6161 = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT F03, F19, F20 FROM S61.T6161 WHERE F01 = ?"))
            {
                ps.setInt(1, accountId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        t6161 = new T6161();
                        t6161.F03 = resultSet.getString(1);
                        t6161.F19 = resultSet.getString(2);
                        t6161.F20 = resultSet.getString(3);
                    }
                }
            }
        }
        
        return t6161;
    }
    
    protected String selectT6141(int accountId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F07 FROM S61.T6141 WHERE F01 = ?"))
            {
                ps.setInt(1, accountId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return StringHelper.decode(resultSet.getString(1));
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public int selectT6119(int F01, int F02, String F03)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01 FROM S61.T6119 WHERE F01 = ? AND F02 = ? AND F03 = ?"))
            {
                pstmt.setInt(1, F01);
                pstmt.setInt(2, F02);
                pstmt.setString(3, F03);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getInt(1);
                    }
                }
            }
        }
        return 0;
    }
    
    protected String selectT6119(int F01)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01,F02,F03 FROM S61.T6119 WHERE F01 = ?"))
            {
                pstmt.setInt(1, F01);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getString(3);
                    }
                }
            }
        }
        return "";
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
    protected void insertT6119(Connection connection, int F01, int F02, String F03)
        throws SQLException
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
