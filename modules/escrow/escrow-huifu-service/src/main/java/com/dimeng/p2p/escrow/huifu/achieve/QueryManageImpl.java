package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.query.ArrayParser;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.BidExchangeQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.ExtOrderQuery;
import com.dimeng.p2p.escrow.huifu.entity.query.TransQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.QueryManage;
import com.dimeng.p2p.modules.account.console.service.entity.Order;
import com.dimeng.util.StringHelper;

/**
 * 交易业务对账查询业务方法类
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月5日]
 */
public class QueryManageImpl extends AbstractEscrowService implements QueryManage
{
    
    public QueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class QueryManageFactory implements ServiceFactory<QueryManage>
    {
        @Override
        public QueryManage newInstance(ServiceResource serviceResource)
        {
            return new QueryManageImpl(serviceResource);
        }
    }
    
    @Override
    public PagingResult<Order> search(ExtOrderQuery query, Paging paging)
        throws Throwable
    {
        StringBuilder sb =
            new StringBuilder(
                "SELECT T6501.F01 AS F01, T6501.F02 AS F02, T6501.F03 AS F03, T6501.F04 AS F04, T6501.F05 AS F05, T6501.F06 AS F06, T6501.F07 AS F07, T6501.F08 AS F08, T6501.F09 AS F09, T6110.F02 AS F10 FROM S65.T6501 INNER JOIN S61.T6110 ON T6501.F08 = T6110.F01 WHERE T6110.F02 != '平台账号'");
        List<Object> parameters = new ArrayList<>();
        if (query != null)
        {
            String userName = query.getUserName();
            if (!StringHelper.isEmpty(userName))
            {
                sb.append(" AND T6110.F02 LIKE ?");
                parameters.add(getSQLConnectionProvider().allMatch(userName));
            }
            
            sb.append(" AND T6501.F02=?");
            parameters.add(query.getType());
            
            Timestamp timestamp = query.getCreateStart();
            if (timestamp != null)
            {
                sb.append(" AND DATE(T6501.F04)>=?");
                parameters.add(timestamp);
            }
            timestamp = query.getEndStart();
            if (timestamp != null)
            {
                sb.append(" AND DATE(T6501.F04)<=?");
                parameters.add(timestamp);
            }
            String orderId = query.getOrderId();
            if (!StringHelper.isEmpty(orderId))
            {
                sb.append(" AND T6501.F01 LIKE ?");
                parameters.add(getSQLConnectionProvider().allMatch(orderId));
            }
            T6501_F03 F03 = query.getOrderStatus();
            if (F03 != null)
            {
                sb.append(" AND T6501.F03 = ?");
                parameters.add(F03.name());
            }
            else
            {
                // if (query.getType() == OrderType.WITHDRAW.orderType()) {
                // sb.append(" AND T6501.F03 IN(?,?,?)");
                // parameters.add(T6501_F03.DQR.name());
                // parameters.add(T6501_F03.SB.name());
                // parameters.add(T6501_F03.CG.name());
                // } else {
                // sb.append(" AND T6501.F03 IN(?,?)");
                // parameters.add(T6501_F03.DQR.name());
                // parameters.add(T6501_F03.SB.name());
                // }
            }
        }
        sb.append(" ORDER BY T6501.F04 DESC");
        try (Connection connection = this.getConnection())
        {
            PagingResult<Order> result = selectPaging(connection, new ArrayParser<Order>()
            {
                
                @Override
                public Order[] parse(ResultSet resultSet)
                    throws SQLException
                {
                    List<Order> lists = new ArrayList<>();
                    while (resultSet.next())
                    {
                        Order record = new Order();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = T6501_F03.parse(resultSet.getString(3));
                        record.F04 = resultSet.getTimestamp(4);
                        record.F05 = resultSet.getTimestamp(5);
                        record.F06 = resultSet.getTimestamp(6);
                        record.F07 = T6501_F07.parse(resultSet.getString(7));
                        record.F08 = resultSet.getInt(8);
                        record.F09 = resultSet.getInt(9);
                        record.userName = resultSet.getString(10);
                        lists.add(record);
                    }
                    return lists.toArray(new Order[lists.size()]);
                }
            }, paging, sb.toString(), parameters);
            
            if (result != null)
            {
                Order[] orders = result.getItems();
                for (Order order : orders)
                {
                    if (order.F02 == OrderType.CHARGE.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F03 FROM S65.T6502 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.WITHDRAW.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F03 FROM S65.T6503 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.BONDIN.orderType() || order.F02 == OrderType.BONDOUT.orderType()
                        || order.F02 == OrderType.BOND.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F03 FROM S65.T6513 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.BID.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F04 FROM S65.T6504 WHERE F01=?", order.F01);
                        order.freezeOrdId = this.selectT6515(connection, order.F01);
                    }
                    else if (order.F02 == OrderType.BID_CANCEL.orderType())
                    {
                        order.amount =
                            selectBigDecimal(connection,
                                "SELECT T6250.F04 FROM S65.T6508 INNER JOIN S62.T6250 ON T6508.F03=T6250.F01 WHERE T6508.F01=?",
                                order.F01);
                    }
                    else if (order.F02 == OrderType.BID_CONFIRM.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F05 FROM S65.T6505 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.BID_REPAYMENT.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F06 FROM S65.T6506 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.BID_EXCHANGE.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F05 FROM S65.T6507 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.FINANCIAL_PURCHASE.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F04 FROM S65.T6510 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.FINANCIAL_REPAYMENT.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F05 FROM S65.T6512 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.FREEZE.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F03 FROM S65.T6515 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.UNFREEZE.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F03 FROM S65.T6516 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.TRANSFER.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F02 FROM S65.T6517 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.ADVANCE.orderType())
                    {
                        order.amount = selectBigDecimal(connection, "SELECT F05 FROM S65.T6514 WHERE F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.GYBTRANSFER.orderType())
                    {
                        order.amount =
                            selectBigDecimal(connection, "SELECT F04 FROM S65.T6554 WHERE T6554.F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.MALLTRANSFER.orderType())
                    {
                        order.amount =
                            selectBigDecimal(connection, "SELECT F04 FROM S65.T6555 WHERE T6555.F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.MALLREFUND.orderType())
                    {
                        order.amount =
                            selectBigDecimal(connection, "SELECT F03 FROM S65.T6528 WHERE T6528.F01=?", order.F01);
                    }
                    else if (order.F02 == OrderType.OFFLINECHARGE.orderType())
                    {
                        order.amount =
                            selectBigDecimal(connection, "SELECT F03 FROM S65.T6509 WHERE T6509.F01 = ?", order.F01);
                    }
                    else if (order.F02 == OrderType.BUY_BAD_CLAIM.orderType())
                    {
                        order.amount =
                            selectBigDecimal(connection, "SELECT F06 FROM S65.T6529 WHERE T6529.F01 = ?", order.F01);
                    }
                }
            }
            return result;
        }
    }
    
    @Override
    public TransQueryEntity queryTranStatus(String orderId, String orderDate, String queryTran)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryTransStat";
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(merCustId);
        params.add(orderId);
        params.add(orderDate);
        params.add(queryTran);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", merCustId);
        map.put("OrdId", orderId);
        map.put("OrdDate", orderDate);
        map.put("QueryTransType", queryTran);
        map.put("ChkValue", ChkValue);
        
        logger.info("交易状态查询请求参数：" + map.toString());
        
        String result = doPost(map);
        
        logger.info("交易状态查询返回参数：" + result);
        
        TransQueryEntity entity = jsonReflectParser(result, TransQueryEntity.class);
        
        return entity;
    }
    
    private String selectT6515(Connection connection, int F01)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT F01 FROM S65.T6515 WHERE T6515.F02 = ?"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return String.valueOf(resultSet.getInt(1));
                }
            }
        }
        return "";
    }
    
    @Override
    public BidExchangeQueryEntity queryZqzrStatus(Map<String, String> map)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "CreditAssignReconciliation";
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(merCustId);
        params.add(map.get("OrdId"));
        params.add(map.get("BeginDate"));
        params.add(map.get("EndDate"));
        params.add(map.get("PageNum"));
        params.add(map.get("PageSize"));
        
        String ChkValue = chkValue(params);
        
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", merCustId);
        // map.put("SellCustId", SellCustId); // 转让人客户号
        // map.put("BuyCustId", BuyCustId); // 承接人客户号
        map.put("PageNum", "1");
        map.put("PageSize", "1");
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        
        BidExchangeQueryEntity entity = jsonReflectParser(result, BidExchangeQueryEntity.class);
        
        return entity;
    }
}
