package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TrfItem;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TrfQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.TrfQueryManage;

/**
 * 
 * 转账查询管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class TrfQueryManageImpl extends AbstractEscrowService implements TrfQueryManage
{
    
    public TrfQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public PagingResult<TrfItem> queryTrfReconcilia(QueryCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "TrfReconciliation";
        String MerCustId = merCustId;
        String BeginDate = cond.beginDate();
        String EndDate = cond.endDate();
        String PageNum = cond.pageNum();
        String PageSize = cond.pageSize();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(BeginDate);
        params.add(EndDate);
        params.add(PageNum);
        params.add(PageSize);
        
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
        
        logger.info("商户扣款对账请求参数：" + map.toString());
        
        String result = doPost(map);
        
        logger.info("商户扣款对账返回参数：" + map.toString());
        
        final TrfQueryEntity entity = jsonReflectParser(result, TrfQueryEntity.class);
        PagingResult<TrfItem> pagingResult = new PagingResult<TrfItem>()
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
                return (entity.TrfReconciliationDtoList != null) ? entity.TrfReconciliationDtoList.size() : 0;
            }
            
            @Override
            public TrfItem[] getItems()
            {
                return entity.TrfReconciliationDtoList == null ? null
                    : entity.TrfReconciliationDtoList.toArray(new TrfItem[getItemCount()]);
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
    public String queryUsrName(String usrId)
        throws Throwable
    {
        if (usrId == null || "".equals(usrId))
        {
            return "";
        }
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT a.F02 from S61.T6110 AS a JOIN S61.T6119 b on a.F01=b.F01  WHERE b.F03=? LIMIT 1"))
            {
                pstmt.setString(1, usrId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getString(1);
                    }
                }
            }
        }
        return "";
    }
    
}
