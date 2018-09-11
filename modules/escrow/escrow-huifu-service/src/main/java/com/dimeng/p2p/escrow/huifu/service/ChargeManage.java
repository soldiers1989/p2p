package com.dimeng.p2p.escrow.huifu.service;

import java.math.BigDecimal;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.escrow.huifu.entity.Auth;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeOrder;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzCond;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzEntity;

public abstract interface ChargeManage extends Service
{
    
    /**
     * 添加充值订单
     * 
     * @param amount
     *            充值金额
     * @param payCompanyCode
     *            支付公司代号
     * @return
     * 
     * @throws Throwable
     */
    public abstract ChargeOrder addOrder(BigDecimal amount, int payCompanyCode, String userType)
        throws Throwable;
    
    /**
     * 获取认证信息
     * 
     * @return
     * @throws Throwable
     */
    public abstract Auth getAutnInfo()
        throws Throwable;
    
    /**
     * 获取订单
     * 
     * @param orderId
     * @return
     * @throws Throwable
     */
    public abstract ChargeOrder getOrder(int orderId)
        throws Throwable;
    
    /**
     * 插入充值订单
     * 
     * @param amount
     * @return
     * @throws Throwable
     */
    public abstract int addChargeOrder(BigDecimal amount)
        throws Throwable;
    
    /**
     * 获取充值对账信息 <功能详细描述>
     * 
     * @param tbdzCond
     * @param paging
     * @return
     * @throws Throwable
     */
    public abstract PagingResult<CzdzEntity> search(CzdzCond czdzCond, Paging paging)
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
