/*
 * 文 件 名:  DonationService.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.escrow.huifu.service;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S65.entities.T6554;
import com.dimeng.p2p.S65.entities.T6555;
import com.dimeng.p2p.escrow.huifu.cond.UsrAcctPayCond;
import com.dimeng.p2p.escrow.huifu.entity.usracctpay.UsrAcctPayEntity;

/**
 * 用户账户支付管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public abstract interface HFUsrAcctPayManage extends Service
{
    /**
     * 查询捐款订单信息
     * <功能详细描述>
     * @param orderId
     * @throws Throwable
     */
    T6554 selectT6554(int orderId)
        throws Throwable;
    
    /**
     * 查询商品购买订单
     * <功能详细描述>
     * @param orderId
     * @return
     * @throws Throwable
     */
    T6555 selectT6555(int orderId)
        throws Throwable;
    
    /**
     * 用户支付回调参数解析
     * <功能详细描述>
     * @param request
     * @throws Throwable
     */
    public abstract UsrAcctPayEntity usrAcctPayDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 创建用户账户支付接口参数
     * <功能详细描述>
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract String createUsrAcctPayUrI(UsrAcctPayCond cond)
        throws Throwable;
    
    /** 
     * 查询当前用户第三方账户
     * <功能详细描述>
     * @return
     */
    public abstract String getUserCustId(int userId)
        throws Throwable;
    
}
