package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeAmend;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeItem;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.ChargeQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.ChargeQueryManage;
import com.dimeng.util.parser.EnumParser;

/**
 * 
 * 充值查询管理 <功能详细描述>
 * 
 * @author lingyuanjie
 * @version [版本号, 2016年7月21日]
 */
public class ChargeQueryManageImpl extends AbstractEscrowService implements ChargeQueryManage
{
    
    public ChargeQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class ChargeQueryManageFactory implements ServiceFactory<ChargeQueryManage>
    {
        
        @Override
        public ChargeQueryManage newInstance(ServiceResource serviceResource)
        {
            return new ChargeQueryManageImpl(serviceResource);
        }
        
    }
    
    private int pageNum = 0;
    
    @Override
    public PagingResult<ChargeItem> query(QueryCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "SaveReconciliation";
        String MerCustId = merCustId;
        String BeginDate = cond.beginDate();
        String EndDate = cond.endDate();
        String PageNum = pageNum > 0 ? String.valueOf(pageNum) : cond.pageNum();
        String PageSize = cond.pageSize();
        pageNum = 0;
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(BeginDate);
        params.add(EndDate);
        params.add(PageNum);
        params.add(PageSize);
        
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
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        // System.out.println(result);
        final ChargeQueryEntity entity = jsonReflectParser(result, ChargeQueryEntity.class);
        
        PagingResult<ChargeItem> pagingResult = new PagingResult<ChargeItem>()
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
                return (entity.SaveReconciliationDtoList != null) ? entity.SaveReconciliationDtoList.size() : 0;
            }
            
            @Override
            public ChargeItem[] getItems()
            {
                return entity.SaveReconciliationDtoList == null ? null
                    : entity.SaveReconciliationDtoList.toArray(new ChargeItem[getItemCount()]);
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
    public ChargeAmend[] chargeInfo(Set<Integer> ids)
        throws Throwable
    {
        if (ids == null || ids.size() <= 0)
        {
            return null;
        }
        List<ChargeAmend> amends = null;
        try (Connection connection = getConnection())
        {
            for (int ordId : ids)
            {
                if (amends == null)
                {
                    amends = new ArrayList<>();
                }
                ChargeAmend amend = new ChargeAmend();
                amend.ordId = ordId;
                int accountId = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F04, F08 FROM S65.T6501 WHERE F01 = ?"))
                {
                    ps.setInt(1, ordId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            amend.time = resultSet.getTimestamp(1);
                            accountId = resultSet.getInt(2);
                        }
                    }
                }
                try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S61.T6110 WHERE F01 = ?"))
                {
                    ps.setInt(1, accountId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            amend.userName = resultSet.getString(1);
                        }
                    }
                }
                try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S65.T6502 WHERE F01 = ?"))
                {
                    ps.setInt(1, ordId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            amend.amount = resultSet.getBigDecimal(1);
                        }
                    }
                }
                amends.add(amend);
            }
        }
        return amends == null ? null : amends.toArray(new ChargeAmend[amends.size()]);
    }
    
    @Override
    public CzdzQueryEntity query(ChargeCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryTransDetail";
        String MerCustId = merCustId;
        String OrdId = cond.ordId();
        String QueryTransType = "SAVE";// 充值
        
        List<String> params = new ArrayList<String>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(QueryTransType);
        
        String chkValue = chkValue(params);
        params.add(chkValue);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("QueryTransType", QueryTransType);
        map.put("ChkValue", chkValue);
        String res = concatUrl(map);
        logger.info("充值对账请求信息：" + res);
        String result = doPost(map);
        logger.info("充值对账返回信息：" + urlDecoder(result));
        CzdzQueryEntity entity = jsonReflectParser(urlDecoder(result), CzdzQueryEntity.class);
        
        return entity;
    }
}
