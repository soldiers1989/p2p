package com.dimeng.p2p.escrow.huifu.service;

import java.util.Map;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.escrow.huifu.entity.query.BidExchangeQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.ExtOrderQuery;
import com.dimeng.p2p.escrow.huifu.entity.query.TransQueryEntity;
import com.dimeng.p2p.modules.account.console.service.entity.Order;

public abstract interface QueryManage extends Service
{
    
    /**
     * 交易记录列表查询
     * 
     * @param query
     * @param paging
     * @return
     * @throws Throwable
     */
    public PagingResult<Order> search(ExtOrderQuery query, Paging paging)
        throws Throwable;
    
    /**
     * 获取汇付交易状态结果
     * 
     * @param orderId
     * @param orderDate
     * @param queryTran
     * @return
     * @throws Throwable
     */
    public TransQueryEntity queryTranStatus(String orderId, String orderDate, String queryTran)
        throws Throwable;
    
    /**
     * 获取汇付债权转让记录交易状态
     * 
     * @param map
     * @return
     * @throws Throwable
     */
    public BidExchangeQueryEntity queryZqzrStatus(Map<String, String> map)
        throws Throwable;
    
}