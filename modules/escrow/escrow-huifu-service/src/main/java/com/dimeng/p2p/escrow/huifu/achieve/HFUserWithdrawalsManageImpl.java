/*
 * 文 件 名:  HFUserWithdrawalsManageImpl.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月22日
 */
package com.dimeng.p2p.escrow.huifu.achieve;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.config.Envionment;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.S61.enums.T6130_F09;
import com.dimeng.p2p.S65.entities.T6503;
import com.dimeng.p2p.common.SMSUtils;
import com.dimeng.p2p.escrow.huifu.service.HFUserWithdrawalsManage;
import com.dimeng.p2p.modules.account.console.service.achieve.UserWithdrawalsManageImpl;
import com.dimeng.p2p.modules.account.console.service.entity.UserWithdrawals;
import com.dimeng.p2p.variables.defines.LetterVariable;
import com.dimeng.p2p.variables.defines.MsgVariavle;
import com.dimeng.p2p.variables.defines.smses.SmsVaribles;
import com.dimeng.util.parser.DateTimeParser;

/**
 * 用户提现管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月22日]
 */
public class HFUserWithdrawalsManageImpl extends UserWithdrawalsManageImpl implements HFUserWithdrawalsManage
{
    
    /** <默认构造函数>
     */
    public HFUserWithdrawalsManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public void check(boolean passed, String check_reason, int... ids)
        throws Throwable
    {
        if (ids == null || ids.length == 0)
        {
            return;
        }
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                
                int accountId = serviceResource.getSession().getAccountId();
                T6130_F09 t6130_F09 = passed ? T6130_F09.DFK : T6130_F09.TXSB;
                Timestamp currentTimestamp = getCurrentTimestamp(connection);
                for (int id : ids)
                {
                    T6110 t6110 = getUserInfo(id, connection);
                    if (T6110_F07.HMD == t6110.F07)
                    {
                        throw new LogicalException("用户:" + t6110.F02 + "已经被拉黑，不能进行提现！");
                    }
                    UserWithdrawals userWithdrawals = get(id);
                    if (userWithdrawals == null)
                    {
                        throw new LogicalException("提现申请记录不存在");
                    }
                    if (userWithdrawals.F09 != T6130_F09.DSH)
                    {
                        throw new LogicalException("提现申请不是待审核状态,不能进行审核操作");
                    }
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S61.T6130 SET F09 = ?, F10 = ?, F11 = ?, F12 = ? WHERE F01 = ?"))
                    {
                        ps.setString(1, t6130_F09.name());
                        ps.setInt(2, accountId);
                        ps.setTimestamp(3, currentTimestamp);
                        ps.setString(4, check_reason);
                        ps.setInt(5, id);
                        ps.execute();
                    }
                    if (!passed)
                    {
                        rollback(connection, userWithdrawals);
                    }
                }
                if (passed)
                {
                    writeLog(connection, "操作日志", "提现审核通过");
                }
                else
                {
                    writeLog(connection, "操作日志", String.format("提现审核不通过,原因:%s", check_reason));
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
    
    /**
     * 提现失败
     * 
     * @param check_reason
     * @param txglRecord
     * @throws Throwable
     */
    private void rollback(Connection connection, UserWithdrawals userWithdrawals)
        throws Throwable
    {
        if (userWithdrawals == null)
        {
            throw new ParameterException("提现记录不存在");
        }
        T6101 sdzh = selectT6101(connection, userWithdrawals.F02, T6101_F03.SDZH, true);
        if (sdzh == null)
        {
            throw new LogicalException("用户锁定资金账户不存在");
        }
        T6101 wlzh = selectT6101(connection, userWithdrawals.F02, T6101_F03.WLZH, true);
        if (wlzh == null)
        {
            throw new LogicalException("用户往来资金账户不存在");
        }
        T6503 t6503 = selectT6503(connection, userWithdrawals.F01);
        if (t6503 == null)
        {
            throw new LogicalException("提现订单不存在");
        }
        
        {
            if (sdzh.F06.compareTo(t6503.F11.add(t6503.F05)) < 0)
            {
                throw new LogicalException("用户锁定资金账户余额不足");
            }
            {
                T6102 t6102 = new T6102();
                t6102.F02 = sdzh.F01;
                t6102.F03 = FeeCode.TXSB;
                t6102.F04 = wlzh.F01;
                t6102.F07 = t6503.F11;
                sdzh.F06 = sdzh.F06.subtract(t6503.F11);
                t6102.F08 = sdzh.F06;
                t6102.F09 = "提现撤销,本金返还";
                insertT6102(connection, t6102);
            }
            {
                T6102 t6102 = new T6102();
                t6102.F02 = sdzh.F01;
                t6102.F03 = FeeCode.TXSB_SXF;
                t6102.F04 = wlzh.F01;
                t6102.F07 = t6503.F05;
                sdzh.F06 = sdzh.F06.subtract(t6503.F05);
                t6102.F08 = sdzh.F06;
                t6102.F09 = "提现撤销,手续费返还";
                insertT6102(connection, t6102);
            }
            // 扣减锁定账户资金
            updateT6101(connection, sdzh.F01, sdzh.F06);
        }
        {
            {
                T6102 t6102 = new T6102();
                t6102.F02 = wlzh.F01;
                t6102.F03 = FeeCode.TXSB;
                t6102.F04 = sdzh.F01;
                t6102.F06 = t6503.F11;
                wlzh.F06 = wlzh.F06.add(t6503.F11);
                t6102.F08 = wlzh.F06;
                t6102.F09 = "提现撤销,本金返还";
                insertT6102(connection, t6102);
            }
            {
                T6102 t6102 = new T6102();
                t6102.F02 = wlzh.F01;
                t6102.F03 = FeeCode.TXSB_SXF;
                t6102.F04 = sdzh.F01;
                t6102.F06 = t6503.F05;
                wlzh.F06 = wlzh.F06.add(t6503.F05);
                t6102.F08 = wlzh.F06;
                t6102.F09 = "提现撤销,手续费返还";
                insertT6102(connection, t6102);
            }
            // 增加往来账户资金
            updateT6101(connection, wlzh.F01, wlzh.F06);
        }
        ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
        Envionment envionment = configureProvider.createEnvionment();
        envionment.set("name", wlzh.F05);
        envionment.set("datetime", DateTimeParser.format(userWithdrawals.F08));
        // boolean flag =
        // Boolean.parseBoolean(configureProvider.getProperty(SystemVariable.TXSXF_KCFS));
        BigDecimal je = t6503.F11.add(t6503.F05);
        /*
         * if(flag){ je = je.add(userWithdrawals.F07); }
         */
        envionment.set("amount", je.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        String content = configureProvider.format(LetterVariable.TX_SB, envionment);
        sendLetter(connection, userWithdrawals.F02, "提现失败", content);
        // sendMsg(connection, wlzh.F04, content);
        T6110 t6110 = selectT6110(connection, userWithdrawals.F02);
        String isUseYtx = configureProvider.getProperty(SmsVaribles.SMS_IS_USE_YTX);
        if ("1".equals(isUseYtx))
        {
            SMSUtils smsUtils = new SMSUtils(configureProvider);
            int type = smsUtils.getTempleId(MsgVariavle.TX_SB.getDescription());
            sendMsg(connection,
                t6110.F04,
                smsUtils.getSendContent(envionment.get("name"), envionment.get("datetime"), envionment.get("amount")),
                type);
            
        }
        else
        {
            String msgContent = configureProvider.format(MsgVariavle.TX_SB, envionment);
            sendMsg(connection, t6110.F04, msgContent);
        }
    }
    
    private void updateT6101(Connection connection, int F01, BigDecimal F06)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6101 SET F06 = ?, F07 = ?  WHERE F01 = ? "))
        {
            pstmt.setBigDecimal(1, F06);
            pstmt.setTimestamp(2, getCurrentTimestamp(connection));
            pstmt.setInt(3, F01);
            pstmt.execute();
        }
    }
    
    private T6503 selectT6503(Connection connection, int F09)
        throws SQLException
    {
        T6503 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S65.T6503 WHERE T6503.F09 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F09);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6503();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getBigDecimal(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getInt(7);
                    record.F08 = resultSet.getString(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = resultSet.getBigDecimal(10);
                    record.F11 = resultSet.getBigDecimal(11);
                }
            }
        }
        return record;
    }
    
}
