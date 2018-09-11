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
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransStatQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.TransStatQueryManage;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;

/**
 * 
 * 交易状态查询管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class TransStatQueryManageImpl extends AbstractEscrowService implements TransStatQueryManage
{
    
    public TransStatQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class TransStatManageFactory implements ServiceFactory<TransStatQueryManage>
    {
        
        @Override
        public TransStatQueryManage newInstance(ServiceResource serviceResource)
        {
            return new TransStatQueryManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public TransStatQueryEntity query(int orderId)
        throws Throwable
    {
        T6501 t6501 = null;
        try (Connection connection = getConnection())
        {
            t6501 = selectT6501(connection, orderId);
        }
        if (t6501 == null)
        {
            return null;
        }
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryTransStat";
        String MerCustId = merCustId;
        String OrdId = String.valueOf(t6501.F01);
        String OrdDate = DateParser.format(t6501.F04, "yyyyMMdd");
        String QueryTransType = null;
        if (t6501.F02 == OrderType.BID_CONFIRM.orderType())
        {
            QueryTransType = "LOANS";
        }
        else if (t6501.F02 == OrderType.BID_REPAYMENT.orderType())
        {
            QueryTransType = "REPAYMENT";
        }
        
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
        
        String result = doPost(map);
        
        //System.out.println(result);
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
    
    protected T6501 selectT6501(Connection connection, int F01)
        throws SQLException
    {
        T6501 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S65.T6501 WHERE T6501.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F01);
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
                    record.F10 = resultSet.getString(10);
                }
            }
        }
        return record;
    }
    
    @Override
    public PagingResult<T6501> orderList(int orderId, String startDate, String endDate, int type, Paging paging)
        throws Throwable
    {
        StringBuilder builder =
            new StringBuilder("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S65.T6501 WHERE F03 = ?");
        List<Object> params = new ArrayList<>();
        params.add(T6501_F03.CG.toString());
        if (orderId > 0)
        {
            builder.append(" AND F01 = ?");
            params.add(orderId);
        }
        if (!StringHelper.isEmpty(startDate))
        {
            builder.append(" AND F04 >= ?");
            params.add(startDate);
        }
        if (!StringHelper.isEmpty(endDate))
        {
            builder.append(" AND F04 <= ?");
            params.add(endDate);
        }
        if (type <= 0)
        {
            builder.append(" AND F02 IN (?,?)");
            params.add(OrderType.BID_CONFIRM.orderType());
            params.add(OrderType.BID_REPAYMENT.orderType());
        }
        else
        {
            builder.append(" AND F02 = ?");
            params.add(type);
        }
        builder.append(" ORDER BY F01 DESC");
        try (Connection connection = getConnection())
        {
            return selectPaging(connection, new ArrayParser<T6501>()
            {
                
                @Override
                public T6501[] parse(ResultSet resultSet)
                    throws SQLException
                {
                    ArrayList<T6501> list = null;
                    while (resultSet.next())
                    {
                        T6501 record = new T6501();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = T6501_F03.parse(resultSet.getString(3));
                        record.F04 = resultSet.getTimestamp(4);
                        record.F05 = resultSet.getTimestamp(5);
                        record.F06 = resultSet.getTimestamp(6);
                        record.F07 = T6501_F07.parse(resultSet.getString(7));
                        record.F08 = resultSet.getInt(8);
                        record.F09 = resultSet.getInt(9);
                        record.F10 = resultSet.getString(10);
                        if (list == null)
                        {
                            list = new ArrayList<>();
                        }
                        list.add(record);
                    }
                    return ((list == null || list.size() == 0) ? null : list.toArray(new T6501[list.size()]));
                }
            }, paging, builder.toString(), params);
        }
    }
    
}
