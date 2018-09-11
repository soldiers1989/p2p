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

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6110_F17;
import com.dimeng.p2p.S61.enums.T6147_F04;
import com.dimeng.p2p.S61.enums.T6148_F02;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.entities.T6250;
import com.dimeng.p2p.S62.entities.T6251;
import com.dimeng.p2p.S62.entities.T6260;
import com.dimeng.p2p.S62.enums.T6230_F10;
import com.dimeng.p2p.S62.enums.T6230_F11;
import com.dimeng.p2p.S62.enums.T6230_F12;
import com.dimeng.p2p.S62.enums.T6230_F13;
import com.dimeng.p2p.S62.enums.T6230_F14;
import com.dimeng.p2p.S62.enums.T6230_F15;
import com.dimeng.p2p.S62.enums.T6230_F16;
import com.dimeng.p2p.S62.enums.T6230_F17;
import com.dimeng.p2p.S62.enums.T6230_F20;
import com.dimeng.p2p.S62.enums.T6230_F27;
import com.dimeng.p2p.S62.enums.T6250_F07;
import com.dimeng.p2p.S62.enums.T6250_F08;
import com.dimeng.p2p.S62.enums.T6251_F08;
import com.dimeng.p2p.S62.enums.T6252_F09;
import com.dimeng.p2p.S62.enums.T6260_F07;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6507;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.common.RiskLevelCompareUtil;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.BidExchangeCond;
import com.dimeng.p2p.escrow.huifu.entity.bidexchange.BidExchangeEntity;
import com.dimeng.p2p.escrow.huifu.service.BidExchangeManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.variables.defines.RegulatoryPolicyVariavle;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.util.DateHelper;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.DateParser;

/**
 * 
 * 债权转让管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class BidExchangeManageImpl extends AbstractEscrowService implements BidExchangeManage
{
    
    public BidExchangeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class BidExchangeManageFactory implements ServiceFactory<BidExchangeManage>
    {
        
        @Override
        public BidExchangeManage newInstance(ServiceResource serviceResource)
        {
            return new BidExchangeManageImpl(serviceResource);
        }
    }
    
    @Override
    public String createBidExchangeUri(BidExchangeCond cond, T6507 t6507)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6260 t6260 = selectT6260(connection, t6507.F02);
            T6251 t6251 = selectT6251(connection, t6260.F02);
            
            String Version = HuiFuConstants.Version.VERSION_10;
            String CmdId = "CreditAssign";
            String MerCustId = merCustId;
            String SellCustId = getUserCustId(t6251.F04);
            String CreditAmt = cond.creditAmt();
            String CreditDealAmt = cond.creditDealAmt();
            String BidDetails = cond.bidDetails();
            String Fee = cond.fee();
            String DivDetails = cond.divDetails();
            String BuyCustId = cond.buyCustId();
            String OrdId = cond.ordId();
            String OrdDate = cond.ordDate();
            String RetUrl = cond.retUrl();
            String BgRetUrl = configureProvider.format(HuifuVariable.HF_EXCHANGE);
            String MerPriv = cond.merPriv();
            
            List<String> params = new ArrayList<>();
            params.add(Version);
            params.add(CmdId);
            params.add(MerCustId);
            params.add(SellCustId);
            params.add(CreditAmt);
            params.add(CreditDealAmt);
            params.add(BidDetails);
            params.add(Fee);
            params.add(DivDetails);
            params.add(BuyCustId);
            params.add(OrdId);
            params.add(OrdDate);
            params.add(RetUrl);
            params.add(BgRetUrl);
            params.add(MerPriv);
            
            String ChkValue = chkValue(params);
            
            Map<String, String> map = new HashMap<>();
            map.put("Version", Version);
            map.put("CmdId", CmdId);
            map.put("MerCustId", MerCustId);
            map.put("SellCustId", SellCustId);
            map.put("CreditAmt", CreditAmt);
            map.put("CreditDealAmt", CreditDealAmt);
            map.put("BidDetails", BidDetails);
            map.put("Fee", Fee);
            map.put("DivDetails", DivDetails);
            map.put("BuyCustId", BuyCustId);
            map.put("OrdId", OrdId);
            map.put("OrdDate", OrdDate);
            map.put("RetUrl", RetUrl);
            map.put("BgRetUrl", BgRetUrl);
            map.put("MerPriv", MerPriv);
            map.put("ChkValue", ChkValue);
            
            return concatUrl(map);
        }
    }
    
    @Override
    public BidExchangeEntity bidExchangeReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        BidExchangeEntity entity = new BidExchangeEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.buyCustId = request.getParameter("BuyCustId");
        entity.cmdId = request.getParameter("CmdId");
        entity.creditAmt = request.getParameter("CreditAmt");
        entity.creditDealAmt = request.getParameter("CreditDealAmt");
        entity.fee = request.getParameter("Fee");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.sellCustId = request.getParameter("SellCustId");
        entity.merPriv = request.getParameter("MerPriv");
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.sellCustId);
        params.add(entity.creditAmt);
        params.add(entity.creditDealAmt);
        params.add(entity.fee);
        params.add(entity.buyCustId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public String getUserCustId(int accountId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?"))
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
        }
        return null;
    }
    
    @Override
    public int purchase(int transferId)
        throws Throwable
    {
        if (transferId <= 0)
        {
            throw new LogicalException("线上债权转让申请不存在");
        }
        int accountId = serviceResource.getSession().getAccountId();
        try (Connection connection = getConnection())
        {
            serviceResource.openTransactions(connection);
            try
            {
                // 锁定转让申请
                T6260 t6260 = selectT6260(connection, transferId);
                if (t6260 == null)
                {
                    throw new LogicalException("线上债权转让申请不存在");
                }
                
                T6110 t6110 = selectT6110(connection, accountId);
                if (T6110_F17.F == t6110.F17 && T6110_F06.FZRR == t6110.F06)
                {
                    throw new LogicalException("您的账号不能购买债权");
                }
                // 查询是否有逾期未还
                int count = selectYqInfo(connection, accountId);
                if (count > 0)
                {
                    throw new LogicalException("您有逾期未还的借款，请先还完再操作。");
                }
                if (t6260.F07 != T6260_F07.ZRZ)
                {
                    throw new LogicalException("线上债权转让申请不是转让中状态,不能转让");
                }
                if (!DateHelper.beforeOrMatchDate(getCurrentDate(connection), t6260.F06))
                {
                    throw new LogicalException("线上债权转让申请已到截至日期,不能转让");
                }
                int zqrId = 0;
                int bidId = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F03, F04 FROM S62.T6251 WHERE F01=?"))
                {
                    ps.setInt(1, t6260.F02);
                    try (ResultSet rs = ps.executeQuery())
                    {
                        if (rs.next())
                        {
                            bidId = rs.getInt(1);
                            zqrId = rs.getInt(2);
                        }
                    }
                }
                T6110_F06 F06 = selectT6110F06(connection, accountId);
                /*2016-03-31 增加判断用户等级与债权风险等级是否匹配的判断*/
                //是否开启风险承受能力评估
                boolean isOpenRiskAccess =
                    Boolean.parseBoolean(serviceResource.getResource(ConfigureProvider.class)
                        .getProperty(RegulatoryPolicyVariavle.IS_OPEN_RISK_ASSESS));
                boolean isInvestLimit =
                    Boolean.parseBoolean(serviceResource.getResource(ConfigureProvider.class)
                        .getProperty(RegulatoryPolicyVariavle.IS_INVEST_LIMIT));
                if (isOpenRiskAccess
                    && isInvestLimit
                    && !RiskLevelCompareUtil.compareRiskLevel(getUserRiskLevel(connection, accountId),
                        getProductRiskLevel(connection, bidId).name()) && T6110_F06.ZRR == F06)
                {
                    throw new LogicalException("您的风险承受等级不可购买该债权。");
                }
                ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
                boolean zqzrBol = BooleanParser.parse(configureProvider.getProperty(SystemVariable.ZQZR_SFZJKT));
                if (!zqzrBol && accountId == zqrId)
                {
                    throw new LogicalException("不能购买自己的债权");
                }
                int jkrId = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S62.T6230 WHERE F01 = ?"))
                {
                    ps.setInt(1, bidId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            jkrId = resultSet.getInt(1);
                        }
                    }
                }
                if (jkrId == accountId)
                {
                    throw new LogicalException("不能承接自己发标的债权");
                }
                // 锁定投资人资金账户
                BigDecimal fee = t6260.F03.multiply(t6260.F08);
                BigDecimal total = t6260.F03;
                T6101 investor = selectT6101(connection, accountId, T6101_F03.WLZH);
                if (investor == null)
                {
                    throw new LogicalException("用户往来账户不存在");
                }
                if (investor.F06.compareTo(total) < 0)
                {
                    throw new LogicalException("可用余额不足");
                }
                
                Timestamp times = getCurrentTimestamp(connection);
                
                T6501 t6501 = new T6501();
                t6501.F02 = OrderType.BID_EXCHANGE.orderType();
                t6501.F03 = T6501_F03.DTJ;
                t6501.F04 = times;
                t6501.F05 = times;
                t6501.F07 = T6501_F07.YH;
                t6501.F08 = accountId;
                t6501.F09 = getPTID(connection);
                t6501.F13 = t6260.F04;
                int orderId = insertT6501(connection, t6501);
                
                T6507 t6507 = new T6507();
                t6507.F01 = orderId;
                t6507.F02 = transferId;
                t6507.F03 = accountId;
                t6507.F04 = t6260.F04;
                t6507.F05 = t6260.F03;
                t6507.F06 = fee;
                insertT6507(connection, t6507);
                
                serviceResource.commit(connection);
                return orderId;
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    /**
     * 查询用户的风险等级
     * <功能详细描述>
     * @param connection
     * @param id
     * @return
     * @throws Throwable
     */
    private T6147_F04 getUserRiskLevel(Connection connection, int id)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F04 FROM S61.T6147 WHERE T6147.F02 = ? ORDER BY F06 DESC LIMIT 1"))
        {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return T6147_F04.parse(rs.getString(1));
                }
            }
        }
        return null;
    }
    
    private T6110_F06 selectT6110F06(Connection connection, int F01)
        throws SQLException
    {
        T6110_F06 F06 = null;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT F06 FROM S61.T6110 WHERE T6110.F01 = ?"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    F06 = T6110_F06.parse(resultSet.getString(1));
                }
            }
        }
        return F06;
    }
    
    /**
     * 查询产品风险等级
     * <功能详细描述>
     * @param connection
     * @param bidId
     * @return
     * @throws Throwable
     */
    private T6148_F02 getProductRiskLevel(Connection connection, int bidId)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT T6216.F18 FROM S62.T6230 INNER JOIN S62.T6216  ON T6230.F32=T6216.F01 WHERE T6230.F01=? LIMIT 1"))
        {
            pstmt.setInt(1, bidId);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return T6148_F02.parse(rs.getString(1));
                }
            }
        }
        return T6148_F02.BSX;
    }
    
    private int selectYqInfo(Connection connection, int F03)
        throws SQLException
    {
        int count = 0;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT COUNT(*) FROM S62.T6252 WHERE F03 = ? AND F08 < CURDATE() AND F09 = ? "))
        {
            pstmt.setInt(1, F03);
            pstmt.setString(2, T6252_F09.WH.name());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    count = resultSet.getInt(1);
                }
            }
        }
        return count;
    }
    
    @Override
    protected int insertT6501(Connection connection, T6501 entity)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6501 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, entity.F02);
            pstmt.setString(2, entity.F03.name());
            pstmt.setTimestamp(3, entity.F04);
            pstmt.setTimestamp(4, entity.F05);
            pstmt.setTimestamp(5, entity.F06);
            pstmt.setString(6, entity.F07.name());
            pstmt.setInt(7, entity.F08);
            pstmt.setInt(8, entity.F09);
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
    
    private T6260 selectT6260(Connection connection, int F01)
        throws SQLException
    {
        T6260 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S62.T6260 WHERE F01 = ? FOR UPDATE"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6260();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getBigDecimal(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getTimestamp(5);
                    record.F06 = resultSet.getDate(6);
                    record.F07 = T6260_F07.parse(resultSet.getString(7));
                    record.F08 = resultSet.getBigDecimal(8);
                }
            }
        }
        return record;
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
    
    protected void insertT6507(Connection connection, T6507 entity)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S65.T6507 SET F01 = ?, F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?"))
        {
            pstmt.setInt(1, entity.F01);
            pstmt.setInt(2, entity.F02);
            pstmt.setInt(3, entity.F03);
            pstmt.setBigDecimal(4, entity.F04);
            pstmt.setBigDecimal(5, entity.F05);
            pstmt.setBigDecimal(6, entity.F06);
            pstmt.execute();
        }
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
    
    @Override
    public T6507 selectT6507(int orderId)
        throws Throwable
    {
        T6507 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06 FROM S65.T6507 WHERE T6507.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6507();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F05 = resultSet.getBigDecimal(5);
                        record.F06 = resultSet.getBigDecimal(6);
                    }
                }
            }
        }
        return record;
    }
    
    @Override
    public T6501 selectT6501(int orderId)
        throws Throwable
    {
        T6501 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09 FROM S65.T6501 WHERE T6501.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6501();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = T6501_F03.parse(resultSet.getString(3));
                        record.F04 = resultSet.getTimestamp(4);
                        record.F05 = resultSet.getTimestamp(5);
                        record.F06 = resultSet.getTimestamp(6);
                        record.F07 = T6501_F07.parse(resultSet.getString(7));
                        record.F08 = resultSet.getInt(8);
                        record.F09 = resultSet.getInt(9);
                    }
                }
            }
        }
        return record;
    }
    
    protected T6251 selectT6251(Connection connection, int F01)
        throws SQLException
    {
        T6251 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12 FROM S62.T6251 WHERE T6251.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6251();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getString(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = T6251_F08.parse(resultSet.getString(8));
                    record.F09 = resultSet.getDate(9);
                    record.F10 = resultSet.getDate(10);
                    record.F11 = resultSet.getInt(11);
                    record.F12 = resultSet.getInt(12);
                }
            }
        }
        return record;
    }
    
    protected T6250 selectT6250(Connection connection, int F01)
        throws SQLException
    {
        T6250 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S62.T6250 WHERE T6250.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6250();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getTimestamp(6);
                    record.F07 = T6250_F07.parse(resultSet.getString(7));
                    record.F08 = T6250_F08.parse(resultSet.getString(8));
                }
            }
        }
        return record;
    }
    
    protected T6504 selectT6504(Connection connection, int F05)
        throws SQLException
    {
        T6504 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05 FROM S65.T6504 WHERE T6504.F05 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F05);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6504();
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
    
    protected T6230 selectT6230(Connection connection, int F01)
        throws SQLException
    {
        T6230 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25, F26, F27 FROM S62.T6230 WHERE T6230.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6230();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getString(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getBigDecimal(6);
                    record.F07 = resultSet.getBigDecimal(7);
                    record.F08 = resultSet.getInt(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = T6230_F10.parse(resultSet.getString(10));
                    record.F11 = T6230_F11.parse(resultSet.getString(11));
                    record.F12 = T6230_F12.parse(resultSet.getString(12));
                    record.F13 = T6230_F13.parse(resultSet.getString(13));
                    record.F14 = T6230_F14.parse(resultSet.getString(14));
                    record.F15 = T6230_F15.parse(resultSet.getString(15));
                    record.F16 = T6230_F16.parse(resultSet.getString(16));
                    record.F17 = T6230_F17.parse(resultSet.getString(17));
                    record.F18 = resultSet.getInt(18);
                    record.F19 = resultSet.getInt(19);
                    record.F20 = T6230_F20.parse(resultSet.getString(20));
                    record.F21 = resultSet.getString(21);
                    record.F22 = resultSet.getTimestamp(22);
                    record.F23 = resultSet.getInt(23);
                    record.F24 = resultSet.getTimestamp(24);
                    record.F25 = resultSet.getString(25);
                    record.F26 = resultSet.getBigDecimal(26);
                    record.F27 = T6230_F27.parse(resultSet.getString(27));
                }
            }
        }
        return record;
    }
    
    protected BigDecimal selectBigDecimal(Connection connection, int F05, T6252_F09 F09, int F11)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE T6252.F05 = ? AND T6252.F09 = ? AND T6252.F11 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F05);
            pstmt.setString(2, F09.name());
            pstmt.setInt(3, F11);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getBigDecimal(1);
                }
            }
        }
        return null;
    }
    
    protected T6507 selectT6507(Connection connection, int F01)
        throws SQLException
    {
        T6507 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06 FROM S65.T6507 WHERE T6507.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6507();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getBigDecimal(5);
                    record.F06 = resultSet.getBigDecimal(6);
                }
            }
        }
        return record;
    }
    
    @Override
    public String bidDetails(int orderId)
        throws Throwable
    {
        T6507 t6507 = selectT6507(orderId);
        try (Connection connection = getConnection())
        {
            
            T6260 t6260 = selectT6260(connection, t6507.F02);
            T6251 t6251 = selectT6251(connection, t6260.F02);
            T6230 t6230 = selectT6230(connection, t6251.F03);
            String bidOrdId = null;
            String bidOrdDate = null;
            String borrowerCreditAmt = null;
            String borrowerCustId = null;
            String bidCreditAmt = null;
            String prinAmt = null;
            if (t6251.F12 > 0)
            {
                T6507 oldT6507 = selectT6507(t6251.F12);
                T6501 t6501 = selectT6501(oldT6507.F01);
                bidOrdId = String.valueOf(t6501.F01); // 标订单id
                bidOrdDate = DateParser.format(t6501.F04, "yyyyMMdd"); // 标订单时间
                borrowerCreditAmt = t6507.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 转出原投资金额
                borrowerCustId = getUserCustId(t6230.F02); // 借款人账户号
                bidCreditAmt = t6507.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 转出原标借款金额
                prinAmt =
                    selectBigDecimal(connection, FeeCode.TZ_BJ, T6252_F09.YH, t6251.F01).setScale(2,
                        BigDecimal.ROUND_HALF_UP).toString(); // 已还本金
            }
            else
            {
                T6504 t6504 = selectT6504(connection, t6251.F11);
                T6501 t6501 = selectT6501(t6504.F01);
                bidOrdId = String.valueOf(t6504.F01); // 标订单id
                bidOrdDate = DateParser.format(t6501.F04, "yyyyMMdd"); // 标订单时间
                borrowerCreditAmt = t6507.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 转出原投资金额
                borrowerCustId = getUserCustId(t6230.F02); // 借款人账户号
                bidCreditAmt = t6507.F04.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 转出原标借款金额
                prinAmt =
                    selectBigDecimal(connection, FeeCode.TZ_BJ, T6252_F09.YH, t6251.F01).setScale(2,
                        BigDecimal.ROUND_HALF_UP).toString(); // 已还本金
            }
            
            StringBuilder builder = new StringBuilder();
            builder.append("{\"BidDetails\":[{\"BidOrdId\":\"");
            builder.append(bidOrdId);
            builder.append("\",\"BidOrdDate\":\"");
            builder.append(bidOrdDate);
            builder.append("\",\"BidCreditAmt\":\"");
            builder.append(bidCreditAmt);
            builder.append("\",\"BorrowerDetails\":[{\"BorrowerCustId\":\"");
            builder.append(borrowerCustId);
            builder.append("\",\"BorrowerCreditAmt\":\"");
            builder.append(borrowerCreditAmt);
            builder.append("\",\"PrinAmt\":\"");
            builder.append(prinAmt);
            builder.append("\"}]}]}");
            return builder.toString();
        }
    }
    
}