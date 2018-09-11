package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.config.Envionment;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.framework.service.query.ArrayParser;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6118_F02;
import com.dimeng.p2p.S61.enums.T6118_F03;
import com.dimeng.p2p.S61.enums.T6118_F05;
import com.dimeng.p2p.S61.enums.T6123_F05;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6502;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.entity.Auth;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeOrder;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzCond;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzEntity;
import com.dimeng.p2p.escrow.huifu.service.ChargeManage;
import com.dimeng.p2p.variables.defines.LetterVariable;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.p2p.variables.defines.pays.PayVariavle;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 充值管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class ChargeManageImpl extends AbstractEscrowService implements ChargeManage
{
    
    public ChargeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    /**
     * 插入站内信内容
     * 
     * @param connection
     * @param F01
     * @param F02
     * @throws SQLException
     */
    @Override
    protected void insertT6124(Connection connection, int F01, String F02)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO S61.T6124 SET F01 = ?, F02 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setString(2, F02);
            pstmt.execute();
        }
    }
    
    /**
     * 插入站内信
     * 
     * @param connection
     * @param F02
     * @param F03
     * @param F04
     * @param F05
     * @return
     * @throws SQLException
     */
    protected int insertT6123(Connection connection, int F02, String F03, Timestamp F04, T6123_F05 F05)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6123 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, F02);
            pstmt.setString(2, F03);
            pstmt.setTimestamp(3, F04);
            pstmt.setString(4, F05.name());
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
    
    /**
     * 更新充值订单流水单号
     * 
     * @param connection
     * @param F01
     * @param F02
     * @throws SQLException
     */
    protected void updateT6502(Connection connection, String F01, int F02)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S65.T6502 SET F08 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, F01);
            pstmt.setInt(2, F02);
            pstmt.execute();
        }
    }
    
    /**
     * 更新订单表
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    protected void updateT6501(Connection connection, T6501_F03 F01, Timestamp F02, int F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, F01.name());
            pstmt.setTimestamp(2, F02);
            pstmt.setInt(3, F03);
            pstmt.execute();
        }
    }
    
    /**
     * 查询资金账户信息
     * 
     * @param connection
     * @param F02
     * @param F03
     * @return
     * @throws SQLException
     */
    protected T6101 selectT6101(Connection connection, int F02, T6101_F03 F03)
        throws SQLException
    {
        T6101 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S61.T6101 WHERE T6101.F02 = ? AND T6101.F03 = ? LIMIT 1"))
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
    
    /**
     * 更新用户往来账户
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    protected void updateT6101(Connection connection, BigDecimal F01, int F02, T6101_F03 F03)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6101 SET F06 = F06 + ?, F07 = 2 WHERE F02 = ? AND F03 = ?"))
        {
            pstmt.setBigDecimal(1, F01);
            pstmt.setTimestamp(2, getCurrentTimestamp(connection));
            pstmt.setInt(3, F02);
            pstmt.setString(4, F03.name());
            pstmt.execute();
        }
    }
    
    /**
     * 查询充值次数
     * 
     * @param connection
     * @param F03
     * @param F02
     * @return
     * @throws SQLException
     */
    protected int selectChargeCount(Connection connection, T6501_F03 F03, int F02)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT COUNT(T6501.F01) FROM S65.T6501 INNER JOIN S65.T6502 ON T6501.F01 = T6502.F01 WHERE T6501.F03 = ? AND T6502.F02 = ?"))
        {
            pstmt.setString(1, F03.name());
            pstmt.setInt(2, F02);
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
    
    /**
     * 推广处理
     * 
     * @param amount
     *            充值金额
     * @param id
     *            充值订单id
     * @throws Throwable
     */
    protected void expand(T6502 t6502, T6101 uT6101, T6101 cT6101)
        throws Throwable
    {
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        if (t6502.F03.compareTo(new BigDecimal(configureProvider.getProperty(SystemVariable.TG_YXCZJS))) < 0)
        {
            return;
        }
        int accountId = serviceResource.getSession().getAccountId();
        Timestamp t = new Timestamp(System.currentTimeMillis());
        BigDecimal exjl = null;
        int exid = 0;// 推广人id
        try (Connection connection = getConnection())
        {
            // 充值次数
            int chargeCount = selectChargeCount(connection, T6501_F03.CG, accountId);
            if (chargeCount >= 2)
            {
                return;
            }
            try
            {
                serviceResource.openTransactions(connection);
                
                try (PreparedStatement ps =
                    connection.prepareStatement("UPDATE S63.T6311 SET F04 = ?, F06 = ? WHERE F03 = ?"))
                {
                    ps.setBigDecimal(1, t6502.F03);
                    ps.setTimestamp(2, t);
                    ps.setInt(3, accountId);
                    ps.execute();
                }
                // 充值推广奖励
                String code = null;// 邀请码
                try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6111 WHERE F01=?"))
                {
                    ps.setInt(1, accountId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            code = resultSet.getString(1);
                        }
                    }
                }
                if (StringHelper.isEmpty(code))
                {
                    return;
                }
                try (PreparedStatement ps =
                    connection.prepareStatement("SELECT F01 FROM S61.T6111 WHERE F02=? LIMIT 1"))
                {
                    ps.setString(1, code);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            exid = resultSet.getInt(1);
                        }
                    }
                }
                if (exid <= 0)
                {
                    return;
                }
                // 判断获取奖励次数是否超过上限
                int excount = 0;
                try (PreparedStatement ps =
                    connection.prepareStatement("SELECT COUNT(F02) FROM S63.T6311 WHERE F02=? AND F05>0"))
                {
                    ps.setInt(1, exid);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            excount = resultSet.getInt(1);
                        }
                    }
                }
                if (excount > IntegerParser.parse(configureProvider.getProperty(SystemVariable.TG_YXTGSX)))
                {
                    return;
                }
                // 计算奖励金额
                exjl = new BigDecimal(configureProvider.getProperty(SystemVariable.TG_YXTGJL));
                try (PreparedStatement ps = connection.prepareStatement("UPDATE S63.T6311 SET F05=? WHERE F03=?"))
                {
                    ps.setBigDecimal(1, exjl);
                    ps.setInt(2, accountId);
                    ps.execute();
                }
                try (PreparedStatement ps = connection.prepareStatement("UPDATE S63.T6310 SET F05=F05+? WHERE F01=?"))
                {
                    ps.setBigDecimal(1, exjl);
                    ps.setInt(2, exid);
                    ps.executeUpdate();
                }
                updateT6101(connection, exjl, exid, T6101_F03.WLZH);
                T6101 eT6101 = selectT6101(connection, exid, T6101_F03.WLZH);
                T6102 eT6102 = new T6102();
                eT6102.F02 = eT6101.F01;
                eT6102.F03 = FeeCode.TG_YX;
                eT6102.F04 = cT6101.F01;
                eT6102.F05 = t;
                eT6102.F06 = exjl;
                eT6102.F08 = eT6101.F06;
                eT6102.F09 = "有效推广奖励";
                insertT6102(connection, eT6102);
                // 站内信
                int letterId = insertT6123(connection, exid, "有效推广奖励", t, T6123_F05.WD);
                String tem = serviceResource.getResource(ConfigureProvider.class).getProperty(LetterVariable.TG_YXJL);
                Envionment envionment = configureProvider.createEnvionment();
                envionment.set("cz", t6502.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                envionment.set("jl", exjl.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                insertT6124(connection, letterId, StringHelper.format(tem, envionment));
                // 平台账户扣除奖励金额
                updateT6101(connection, exjl.multiply(new BigDecimal(-1)), cT6101.F02, T6101_F03.WLZH);
                cT6101 = selectT6101(connection, cT6101.F02, T6101_F03.WLZH);
                T6102 ecT6102 = new T6102();
                ecT6102.F02 = cT6101.F01;
                ecT6102.F03 = FeeCode.TG_YX;
                ecT6102.F04 = eT6101.F01;
                ecT6102.F05 = t;
                ecT6102.F07 = exjl;
                ecT6102.F08 = cT6101.F06;
                ecT6102.F09 = "有效推广奖励";
                insertT6102(connection, ecT6102);
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    @Override
    public ChargeOrder addOrder(BigDecimal amount, int payCompanyCode, String userType)
        throws Throwable
    {
        if (serviceResource.getSession() == null || !serviceResource.getSession().isAuthenticated())
        {
            throw new ParameterException("鉴权失败");
        }
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        String mPhone = configureProvider.getProperty(PayVariavle.CHARGE_MUST_PHONE);
        String mRealName = configureProvider.getProperty(PayVariavle.CHARGE_MUST_NCIIC);
        String mWithPsd = configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD);
        Auth auth = getAutnInfo();
        if ("S".equals(mPhone) && !auth.phone)
        {
            throw new LogicalException("手机号未认证");
        }
        if ("S".equals(mRealName) && !auth.realName)
        {
            throw new LogicalException("未实名认证");
        }
        if ("S".equals(mWithPsd) && !auth.withdrawPsw)
        {
            throw new LogicalException("交易密码未设置");
        }
        if (amount.compareTo(new BigDecimal(0)) <= 0 || payCompanyCode <= 0)
        {
            throw new ParameterException("金额或支付类型错误");
        }
        
        String min = configureProvider.getProperty(PayVariavle.CHARGE_MIN_AMOUNT);
        String max = configureProvider.getProperty(PayVariavle.CHARGE_MAX_AMOUNT);
        if (amount.compareTo(new BigDecimal(min)) < 0 || amount.compareTo(new BigDecimal(max)) > 0)
        {
            StringBuilder builder = new StringBuilder("充值金额必须大于");
            builder.append(min);
            builder.append("元小于");
            builder.append(max);
            builder.append("元");
            throw new LogicalException(builder.toString());
        }
        BigDecimal poundage = new BigDecimal(0);
        ChargeOrder order = null;
        
        try (Connection connection = getConnection())
        {
            try
            {
                //开启事物
                serviceResource.openTransactions(connection);
                // 平台信息
                int pid = getPTID(connection);
                Timestamp times = getCurrentTimestamp(connection);
                
                int accountId;
                if (!StringHelper.isEmpty(userType) && "PT".equals(userType))
                {
                    accountId = pid;
                }
                else
                {
                    accountId = serviceResource.getSession().getAccountId();
                }
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.CHARGE.orderType();
                t6501.F03 = T6501_F03.DTJ;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.YH;
                t6501.F08 = accountId;
                // 平台ID
                t6501.F09 = pid;
                // 流水号
                t6501.F13 = amount;
                int oId = 0;
                
                T6101 t6101 = selectT6101(connection, accountId, T6101_F03.WLZH);
                if (t6101 == null)
                {
                    throw new LogicalException("用户往来账户不存在");
                }
                oId = insertT6501(connection, t6501);
                if (oId <= 0)
                {
                    throw new LogicalException("数据库异常");
                }
                T6502 t6502 = new T6502();
                t6502.F01 = oId;
                t6502.F02 = accountId;
                t6502.F03 = amount;
                t6502.F04 = poundage;
                t6502.F05 = poundage;
                t6502.F07 = payCompanyCode;
                
                insertT6502(connection, t6502);
                order = new ChargeOrder();
                order.id = oId;
                order.amount = amount;
                order.orderTime = t6501.F04;
                order.payCompanyCode = payCompanyCode;
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
        return order;
    }
    
    protected void updateT6501(Connection connection, T6501_F03 F01, int F02)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S65.T6501 SET F03 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, F01.name());
            pstmt.setInt(2, F02);
            pstmt.execute();
        }
    }
    
    protected void insertT6502(Connection connection, T6502 entity)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6502 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F09 = ?, F10 = ? "))
        {
            pstmt.setInt(1, entity.F01);
            pstmt.setInt(2, entity.F02);
            pstmt.setBigDecimal(3, entity.F03);
            pstmt.setBigDecimal(4, entity.F04);
            pstmt.setBigDecimal(5, entity.F05);
            pstmt.setString(6, entity.F06);
            pstmt.setInt(7, entity.F07);
            Map<String, Object> result = getEmpInfo(entity.F02, connection);
            pstmt.setString(8, (String)result.get("empNum"));
            pstmt.setString(9, (String)result.get("empStatus"));
            pstmt.execute();
        }
    }
    
    @Override
    public Auth getAutnInfo()
        throws Throwable
    {
        try (Connection connection = getConnection("S61"))
        {
            Auth auth = null;
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F02, F03, F05 FROM S61.T6118 WHERE T6118.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, serviceResource.getSession().getAccountId());
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        auth = new Auth();
                        auth.realName = T6118_F02.TG.name().equals(resultSet.getString(1));
                        auth.phone = T6118_F03.TG.name().equals(resultSet.getString(2));
                        auth.withdrawPsw = T6118_F05.YSZ.name().equals(resultSet.getString(3));
                    }
                }
            }
            return auth;
        }
    }
    
    @Override
    public ChargeOrder getOrder(int orderId)
        throws Throwable
    {
        if (orderId <= 0)
        {
            return null;
        }
        ChargeOrder order = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT T6501.F01 AS F01, T6501.F04 AS F02, T6502.F03 AS F03, T6502.F07 AS F04, T6501.F03 AS F05 FROM S65.T6501 INNER JOIN S65.T6502 ON T6501.F01 = T6502.F01 WHERE T6501.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        order = new ChargeOrder();
                        order.id = resultSet.getInt(1);
                        order.orderTime = resultSet.getTimestamp(2);
                        order.amount = resultSet.getBigDecimal(3);
                        order.payCompanyCode = resultSet.getInt(4);
                        order.status = EnumParser.parse(T6501_F03.class, resultSet.getString(5));
                    }
                }
            }
        }
        return order;
    }
    
    @Override
    public int addChargeOrder(BigDecimal amount)
        throws Throwable
    {
        int accountId = 0;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S71.T7101 LIMIT 1"))
            {
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        accountId = resultSet.getInt(1);
                    }
                }
            }
        }
        if (accountId <= 0)
        {
            throw new LogicalException("平台用户不存在");
        }
        
        int oId = 0;
        try (Connection connection = getConnection("S65"))
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                
                Timestamp times = getCurrentTimestamp(connection);
                
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.CHARGE.orderType();
                t6501.F03 = T6501_F03.DTJ;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.YH;
                t6501.F08 = accountId;
                t6501.F09 = getPTID(connection);
                t6501.F13 = amount;
                
                oId = insertT6501(connection, t6501);
                if (oId <= 0)
                {
                    throw new LogicalException("数据库异常");
                }
                T6502 t6502 = new T6502();
                t6502.F01 = oId;
                t6502.F02 = accountId;
                t6502.F03 = amount;
                t6502.F04 = new BigDecimal(0);
                t6502.F05 = new BigDecimal(0);
                t6502.F07 = PaymentInstitution.CHINAPNR.getInstitutionCode();
                insertT6502(connection, t6502);
                serviceResource.commit(connection);
                return oId;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    @Override
    public PagingResult<CzdzEntity> search(CzdzCond query, Paging paging)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            StringBuilder sql = new StringBuilder("");
            ArrayList<Object> parameters = new ArrayList<>();
            if (query != null)
            {
                if (query.tradingType().equals(OrderType.CHARGE.toString()))// 查询充值记录
                {
                    sql.append("SELECT T6501.F01 AS F01,T6501.F03 AS F02,T6501.F04 F03,T6501.F05 AS F04,T6501.F06 AS F05,T6502.F08 AS F06,"
                        + "T6502.F03 AS F07,T6110.F02 AS F08 FROM S65.T6501 "
                        + "INNER JOIN S65.T6502 ON T6501.F01=T6502.F01 INNER JOIN S61.T6110 ON T6502.F02=T6110.F01 "
                        + "WHERE 1=1 ");
                    sql.append("AND T6501.F02='" + OrderType.CHARGE.orderType() + "'");
                }
                
                String account = query.userName();
                if (!StringHelper.isEmpty(account))
                {
                    sql.append(" AND T6110.F02 LIKE ?");
                    parameters.add(getSQLConnectionProvider().allMatch(account));
                }
                String orderId = query.f01();
                if (!StringHelper.isEmpty(orderId))
                {
                    sql.append(" AND T6501.F01 LIKE ?");
                    parameters.add(getSQLConnectionProvider().allMatch(orderId));
                }
                String loanNo = query.f10();
                if (!StringHelper.isEmpty(loanNo))
                {
                    sql.append(" AND T6502.F08 LIKE ?");
                    parameters.add(getSQLConnectionProvider().allMatch(loanNo));
                }
                T6501_F03 F03 = query.getOrderStatus();
                if (F03 != null)
                {
                    sql.append(" AND T6501.F03 = ?");
                    parameters.add(F03.name());
                }
                Timestamp datetime = query.getStartExpireDatetime();
                if (datetime != null)
                {
                    sql.append(" AND DATE(T6501.F04) >=?");
                    parameters.add(datetime);
                }
                datetime = query.getEndExpireDatetime();
                if (datetime != null)
                {
                    sql.append(" AND DATE(T6501.F04) <= ?");
                    parameters.add(datetime);
                }
            }
            sql.append(" ORDER BY T6501.F01 DESC");
            
            return selectPaging(connection, new ArrayParser<CzdzEntity>()
            {
                @Override
                public CzdzEntity[] parse(ResultSet resultSet)
                    throws SQLException
                {
                    List<CzdzEntity> lists = new ArrayList<>();
                    while (resultSet.next())
                    {
                        CzdzEntity record = new CzdzEntity();
                        record.F01 = resultSet.getInt(1);
                        record.F03 = T6501_F03.parse(resultSet.getString(2));
                        record.F04 = resultSet.getTimestamp(3);
                        record.F05 = resultSet.getTimestamp(4);
                        record.F06 = resultSet.getTimestamp(5);
                        record.F10 = resultSet.getString(6) != null ? resultSet.getString(6) : "";
                        record.Amount = resultSet.getBigDecimal(7);
                        record.UserName = resultSet.getString(8);
                        lists.add(record);
                    }
                    return lists.toArray(new CzdzEntity[lists.size()]);
                }
            }, paging, sql.toString(), parameters);
        }
    }
    
}
