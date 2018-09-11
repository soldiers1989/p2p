package com.dimeng.p2p.escrow.huifu.service;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6507;
import com.dimeng.p2p.escrow.huifu.cond.BidExchangeCond;
import com.dimeng.p2p.escrow.huifu.entity.bidexchange.BidExchangeEntity;

public abstract interface BidExchangeManage extends Service
{
    /**
     * 创建债权转让访问地址
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public String createBidExchangeUri(BidExchangeCond cond, T6507 t6507)
        throws Throwable;
    
    /**
     * 解析债权转让返回参数
     * @param request
     * @return
     * @throws Throwable
     */
    public BidExchangeEntity bidExchangeReturnDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 获取用户商户号
     * 
     * @throws Throwable
     */
    public abstract String getUserCustId(int accountId)
        throws Throwable;
    
    /**
     * 用户主动购买债权
     * 
     * @param transferId
     *            债权转入申请ID
     * @return 订单ID
     * @throws Throwable
     */
    public abstract int purchase(final int transferId)
        throws Throwable;
    
    /**
     * 查询订单详情
     * @param orderId
     * @return
     * @throws Throwable
     */
    public abstract T6507 selectT6507(int orderId)
        throws Throwable;
    
    /**
     * 查询订单信息
     * @param orderId
     * @return
     * @throws Throwable
     */
    public abstract T6501 selectT6501(int orderId)
        throws Throwable;
    
    /**
     * 获取表详情
     * @return
     * @throws Throwable
     */
    public abstract String bidDetails(int orderId)
        throws Throwable;
    
    /** 操作类别
     *  日志内容
     * @param type
     * @param log
     * @throws Throwable
     */
    public void writeFrontLog(String type, String log)
        throws Throwable;
    
}
