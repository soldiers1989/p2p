package com.dimeng.p2p.escrow.huifu.service;

import java.util.Set;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeAmend;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeItem;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzQueryEntity;

public abstract interface ChargeQueryManage extends Service
{
    /**
     * 充值对账查询
     * 
     * @param cond
     * @throws Throwable
     */
    public abstract PagingResult<ChargeItem> query(QueryCond cond)
        throws Throwable;
    
    /**
     * 设置页号
     * 
     * @param pageNum
     */
    public abstract void setPageNum(int pageNum);
    
    /**
     * 查询平台订单状态
     * 
     * @param orderId
     * @return
     * @throws Throwable
     */
    public abstract T6501_F03 getOrderStat(int orderId)
        throws Throwable;
    
    /**
     * 更新订单状态
     * 
     * @param orderId
     * @throws Throwable
     */
    public abstract void updateOrderStat(int orderId, T6501_F03 f03)
        throws Throwable;
    
    /**
     * 获取出现异常的订单信息
     * 
     * @param ids
     * @return
     * @throws Throwable
     */
    public abstract ChargeAmend[] chargeInfo(Set<Integer> ids)
        throws Throwable;
    
    /**
     * 充值对账查询
     * 
     * @param cond
     * @throws Throwable
     */
    public abstract CzdzQueryEntity query(ChargeCond cond)
        throws Throwable;
}
