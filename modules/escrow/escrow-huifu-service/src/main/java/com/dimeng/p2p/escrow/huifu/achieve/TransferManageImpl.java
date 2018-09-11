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
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6517;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.TransferCond;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;

public class TransferManageImpl extends AbstractEscrowService implements TransferManage
{
    
    public TransferManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class TransferManageFactory implements ServiceFactory<TransferManage>
    {
        
        @Override
        public TransferManage newInstance(ServiceResource serviceResource)
        {
            return new TransferManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public String sendTansfer(TransferCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "Transfer";
        String OrdId = cond.ordId();
        String OutCustId = cond.outCustId();
        String OutAcctId = cond.outAcctId();
        String TransAmt = cond.transAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String InCustId = cond.inCustId();
        String InAcctId = cond.inAcctId();
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_TRANSFER);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(OrdId);
        params.add(OutCustId);
        params.add(OutAcctId);
        params.add(TransAmt);
        params.add(InCustId);
        params.add(InAcctId);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
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
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return doPost(map);
    }
    
    @Override
    public void decoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        String respCode = request.getParameter("RespCode");
        T6501_F03 f03 = T6501_F03.SB;
        if ("000".equals(respCode))
        {
            f03 = T6501_F03.CG;
        }
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F06 = ? WHERE F01  =?"))
            {
                ps.setString(1, f03.name());
                ps.setTimestamp(2, getCurrentTimestamp(connection));
                ps.setInt(3, IntegerParser.parse(request.getParameter("OrdId")));
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void transfer(String name, BigDecimal amount, String reason)
        throws Throwable
    {
        if (StringHelper.isEmpty(name))
        {
            throw new LogicalException("用户名不能为空");
        }
        if (amount == null || amount.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0)) <= 0)
        {
            throw new LogicalException("转账金额异常");
        }
        amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                int accountId = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S61.T6110 WHERE F02 = ?"))
                {
                    ps.setString(1, name);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            accountId = resultSet.getInt(1);
                        }
                    }
                }
                try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE F01 = ?"))
                {
                    ps.setInt(1, accountId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (!resultSet.next())
                        {
                            throw new LogicalException("用户账户未托管");
                        }
                    }
                }
                if (accountId <= 0)
                {
                    throw new LogicalException("用户不存在");
                }
                int pid = selectPtAccountId(connection);
                if (pid <= 0)
                {
                    throw new LogicalException("平台账户不存在");
                }
                T6101 ptzh = selectT6101(connection, pid, T6101_F03.WLZH);
                if (ptzh == null)
                {
                    throw new LogicalException("平台往来账户不存在");
                }
                T6101 yhzh = selectT6101(connection, accountId, T6101_F03.WLZH);
                if (yhzh == null)
                {
                    throw new LogicalException("用户往来账户不存在");
                }
                
                Timestamp times = getCurrentTimestamp(connection);
                //新增订单
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.TRANSFER.orderType();
                t6501.F03 = T6501_F03.DTJ;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.HT;
                t6501.F08 = accountId;
                t6501.F09 = serviceResource.getSession().getAccountId();
                t6501.F13 = amount;
                int orderId = insertT6501(connection, t6501);
                
                //新增转账订单
                T6517 t6517 = new T6517();
                t6517.F01 = orderId;
                t6517.F02 = amount;
                t6517.F03 = pid;
                t6517.F04 = accountId;
                t6517.F05 = reason;
                insertT6517(connection, t6517);
                
                {
                    // 插入平台流水
                    ptzh.F06 = ptzh.F06.subtract(amount);
                    updateT6101(connection, ptzh.F06, ptzh.F01);
                    // 资金流水
                    T6102 t6102 = new T6102();
                    t6102.F02 = ptzh.F01;
                    t6102.F03 = FeeCode.PTHB;
                    t6102.F04 = yhzh.F01;
                    t6102.F07 = amount;
                    t6102.F08 = ptzh.F06;
                    t6102.F09 = reason;
                    insertT6102(connection, t6102);
                }
                {
                    // 插入用户流水
                    yhzh.F06 = yhzh.F06.add(amount);
                    updateT6101(connection, yhzh.F06, yhzh.F01);
                    // 资金流水
                    T6102 t6102 = new T6102();
                    t6102.F02 = yhzh.F01;
                    t6102.F03 = FeeCode.PTHB;
                    t6102.F04 = ptzh.F01;
                    t6102.F06 = amount;
                    t6102.F08 = yhzh.F06;
                    t6102.F09 = reason;
                    insertT6102(connection, t6102);
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
    
    private void updateT6101(Connection connection, BigDecimal F01, int F02)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6101 SET F06 = ?, F07 = ? WHERE F01 = ?"))
        {
            pstmt.setBigDecimal(1, F01);
            pstmt.setTimestamp(2, getCurrentTimestamp(connection));
            pstmt.setInt(3, F02);
            pstmt.execute();
        }
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
    
    private int selectPtAccountId(Connection connection)
        throws Throwable
    {
        try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S71.T7101 LIMIT 1"))
        {
            try (ResultSet resultSet = ps.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private void insertT6517(Connection connection, T6517 t6517)
        throws Throwable
    {
        try (PreparedStatement ps =
            connection.prepareStatement("INSERT INTO S65.T6517 SET F01 =?, F02 = ?, F03 = ?, F04 = ?, F05 =?"))
        {
            ps.setInt(1, t6517.F01);
            ps.setBigDecimal(2, t6517.F02);
            ps.setInt(3, t6517.F03);
            ps.setInt(4, t6517.F04);
            ps.setString(5, t6517.F05);
            ps.execute();
        }
    }
    
    /** {@inheritDoc} 
     * @return */
    
    @Override
    public TransferEntity transferReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        TransferEntity entity = new TransferEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.ordId = request.getParameter("OrdId");
        entity.outCustId = request.getParameter("OutCustId");
        entity.outAcctId = request.getParameter("OutAcctId");
        entity.transAmt = request.getParameter("TransAmt");
        entity.inCustId = request.getParameter("InCustId");
        entity.inAcctId = request.getParameter("InAcctId");
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.merPriv = request.getParameter("MerPriv");
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> ps = new ArrayList<>();
        ps.add(entity.cmdId);
        ps.add(entity.respCode);
        ps.add(entity.ordId);
        ps.add(entity.outCustId);
        ps.add(entity.outAcctId);
        ps.add(entity.transAmt);
        ps.add(entity.inCustId);
        ps.add(entity.inAcctId);
        if (entity.retUrl != null)
        {
            ps.add(entity.retUrl);
        }
        ps.add(entity.bgRetUrl);
        if (entity.merPriv != null)
        {
            ps.add(entity.merPriv);
        }
        
        String str = forEncryptionStr(ps);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
}
