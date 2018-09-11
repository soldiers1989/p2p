package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.entities.T6505;
import com.dimeng.p2p.S65.entities.T6506;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidItem;
import com.dimeng.p2p.escrow.huifu.entity.loans.LoansAmend;
import com.dimeng.p2p.escrow.huifu.entity.payment.RepaymentAmend;
import com.dimeng.p2p.escrow.huifu.entity.query.BidQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.BidQueryManage;
import com.dimeng.util.parser.EnumParser;

/**
 * 
 * 投标查询管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class BidQueryManageImpl extends AbstractEscrowService implements BidQueryManage
{
    
    public BidQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class BidQueryManageFactory implements ServiceFactory<BidQueryManage>
    {
        
        @Override
        public BidQueryManage newInstance(ServiceResource serviceResource)
        {
            return new BidQueryManageImpl(serviceResource);
        }
        
    }
    
    private int pageNum = 0;
    
    @Override
    public PagingResult<BidItem> query(QueryCond cond, String queryTransType)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "Reconciliation";
        String MerCustId = merCustId;
        String BeginDate = cond.beginDate();
        String EndDate = cond.endDate();
        String PageNum = pageNum > 0 ? String.valueOf(pageNum) : cond.pageNum();
        String QueryTransType = queryTransType;
        String PageSize = cond.pageSize();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(BeginDate);
        params.add(EndDate);
        params.add(PageNum);
        params.add(PageSize);
        params.add(QueryTransType);
        
        // 生成签名
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("BeginDate", BeginDate);
        map.put("EndDate", EndDate);
        map.put("PageNum", PageNum);
        map.put("PageSize", PageSize);
        map.put("QueryTransType", QueryTransType);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        result = result.replaceAll("\"DivDetails\":\\[\\{[^\\}\\]]*\\}\\]", "\"DivDetails\":");
        System.out.println(result);
        
        final BidQueryEntity entity = jsonReflectParser(result, BidQueryEntity.class);
        
        PagingResult<BidItem> pagingResult = new PagingResult<BidItem>()
        {
            
            @Override
            public int getCurrentPage()
            {
                return entity.PageNum;
            }
            
            @Override
            public int getSize()
            {
                return entity.PageSize;
            }
            
            @Override
            public int getItemCount()
            {
                return (entity.ReconciliationDtoList != null) ? entity.ReconciliationDtoList.size() : 0;
            }
            
            @Override
            public BidItem[] getItems()
            {
                return entity.ReconciliationDtoList == null ? null
                    : entity.ReconciliationDtoList.toArray(new BidItem[getItemCount()]);
            }
            
            @Override
            public int getPageCount()
            {
                return pageCount(entity.TotalItems, entity.PageSize);
            }
        };
        
        return pagingResult;
    }
    
    @Override
    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }
    
    @Override
    public void updateOrderStat(int orderId, T6501_F03 f03)
        throws Throwable
    {
        if (f03 == null)
        {
            return;
        }
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6501 SET F03 = ? WHERE F01 = ?"))
            {
                ps.setString(1, f03.name());
                ps.setInt(2, orderId);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public T6501_F03 getOrderStat(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S65.T6501 WHERE F01 = ?"))
            {
                ps.setInt(1, orderId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return EnumParser.parse(T6501_F03.class, resultSet.getString(1));
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public LoansAmend[] loansAmends(Set<Integer> ids)
        throws Throwable
    {
        if (ids == null || ids.size() <= 0)
        {
            return null;
        }
        List<LoansAmend> loansAmends = new ArrayList<>();
        List<T6505> t6505s = selectT6505s(ids);
        if (t6505s == null || t6505s.size() <= 0)
        {
            return null;
        }
        for (T6505 t6505 : t6505s)
        {
            LoansAmend amend = new LoansAmend();
            amend.ordId = t6505.F01;
            amend.amount = t6505.F05;
            amend.name = selectName(t6505.F02);
            amend.bidName = selectBidName(t6505.F03);
            amend.ordTime = selectOrdTime(t6505.F01);
            loansAmends.add(amend);
        }
        return loansAmends == null ? null : loansAmends.toArray(new LoansAmend[loansAmends.size()]);
    }
    
    @Override
    public RepaymentAmend[] repaymentAmends(Set<Integer> ids)
        throws Throwable
    {
        if (ids == null || ids.size() <= 0)
        {
            return null;
        }
        List<RepaymentAmend> loansAmends = new ArrayList<>();
        List<T6506> t6506s = selectT6506s(ids);
        for (T6506 t6506 : t6506s)
        {
            RepaymentAmend amend = new RepaymentAmend();
            amend.ordId = t6506.F01;
            amend.name = selectName(t6506.F02);
            amend.amount = t6506.F06;
            amend.bidName = selectBidName(t6506.F03);
            amend.issue = t6506.F05;
            amend.subject = selectSubject(t6506.F07);
            amend.ordTime = selectOrdTime(t6506.F01);
            loansAmends.add(amend);
        }
        return loansAmends == null ? null : loansAmends.toArray(new RepaymentAmend[loansAmends.size()]);
    }
    
    private Timestamp selectOrdTime(int ordId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F04 FROM S65.T6501 WHERE F01 = ?"))
            {
                ps.setInt(1, ordId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getTimestamp(1);
                    }
                }
            }
        }
        return null;
    }
    
    private List<T6506> selectT6506s(Set<Integer> ids)
        throws Throwable
    {
        List<T6506> t6506s = new ArrayList<>();
        for (int id : ids)
        {
            try (Connection connection = getConnection())
            {
                try (PreparedStatement ps =
                    connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07 FROM S65.T6506 WHERE F01 = ?"))
                {
                    ps.setInt(1, id);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            T6506 t6506 = new T6506();
                            t6506.F01 = resultSet.getInt(1);
                            t6506.F02 = resultSet.getInt(2);
                            t6506.F03 = resultSet.getInt(3);
                            t6506.F04 = resultSet.getInt(4);
                            t6506.F05 = resultSet.getInt(5);
                            t6506.F06 = resultSet.getBigDecimal(6);
                            t6506.F07 = resultSet.getInt(7);
                            t6506s.add(t6506);
                        }
                    }
                }
            }
        }
        return t6506s;
    }
    
    private List<T6505> selectT6505s(Set<Integer> ids)
        throws Throwable
    {
        List<T6505> t6505s = new ArrayList<>();
        for (int id : ids)
        {
            try (Connection connection = getConnection())
            {
                try (PreparedStatement ps =
                    connection.prepareStatement("SELECT F01, F02, F03, F04, F05 FROM S65.T6505 WHERE F01 = ?"))
                {
                    ps.setInt(1, id);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            T6505 t6505 = new T6505();
                            t6505.F01 = resultSet.getInt(1);
                            t6505.F02 = resultSet.getInt(2);
                            t6505.F03 = resultSet.getInt(3);
                            t6505.F04 = resultSet.getInt(4);
                            t6505.F05 = resultSet.getBigDecimal(5);
                            t6505s.add(t6505);
                        }
                    }
                }
            }
        }
        return t6505s;
    }
    
    private String selectName(int userId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S61.T6110 WHERE F01 = ?"))
            {
                ps.setInt(1, userId);
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
    
    private String selectBidName(int bidId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S62.T6230 WHERE F01 = ?"))
            {
                ps.setInt(1, bidId);
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
    
    private String selectSubject(int subId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S51.T5122 WHERE F01 = ?"))
            {
                ps.setInt(1, subId);
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
}
