package com.dimeng.p2p.escrow.huifu.service;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.escrow.huifu.cond.FreezeCond;
import com.dimeng.p2p.escrow.huifu.entity.freeze.FreezeEntity;

public abstract interface FreezeManage extends Service
{
    
    /**
     * 发送冻结订单
     * 
     * @throws Throwable
     */
    public abstract void sendFreeze(FreezeCond cond)
        throws Throwable;
    
    /**
     * 解析返回参数
     * @return
     * @throws Throwable
     */
    public abstract FreezeEntity freezeRetDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 更新订单状态及流水号
     * @param trxId
     * @param orderId
     * @throws Throwable
     */
    public void updateTrxId(String trxId, int orderId)
        throws Throwable;
    
    /**
     * 通过冻结订单号找到提现订单号
     * @param orderId
     * @return  提现订单号
     * @throws Throwable
     */
    public int selectT6515(int orderId)
        throws Throwable;
}
