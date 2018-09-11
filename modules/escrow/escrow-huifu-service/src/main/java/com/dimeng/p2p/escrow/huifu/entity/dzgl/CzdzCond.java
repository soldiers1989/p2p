package com.dimeng.p2p.escrow.huifu.entity.dzgl;

import java.sql.Timestamp;

import com.dimeng.p2p.S65.enums.T6501_F03;

public interface CzdzCond
{
    /**
     * 订单号
     */
    public String f01();
    
    /**
     * 流水号
     */
    public String f10();
    
    /**
     * 创建时间， 大于等于查询
     * 
     * @return {@link Timestamp}空值无效
     */
    public abstract Timestamp getStartExpireDatetime();
    
    /**
     * 创建时间， 小于等于查询
     * 
     * @return {@link Timestamp}空值无效
     */
    public abstract Timestamp getEndExpireDatetime();
    
    /**
     * 平台用户名
     */
    public String userName();
    
    /**
     * 交易类型 <功能详细描述>
     * 
     * @return
     */
    public String tradingType();
    
    /**
     * 订单状态
     * <功能详细描述>
     * @return
     */
    public T6501_F03 getOrderStatus();
}
