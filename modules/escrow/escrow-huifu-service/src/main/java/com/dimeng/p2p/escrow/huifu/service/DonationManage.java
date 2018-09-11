package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;

/**
 * 
 * 公益标管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月20日]
 */
public interface DonationManage extends Service
{
    
    /**
     * 捐款失败，资金返还捐款用户
     * <功能详细描述>
     * @param orderId
     * @throws Throwable
     */
    public void failCapitalBack(int orderId)
        throws Throwable;
    
    /**
     * 捐款失败,回调处理
     * <功能详细描述>
     * @param orderId
     * @throws Throwable
     */
    public void donationFundDeal(int orderId)
        throws Throwable;
    
    /**
     * 公益标捐款前台回调校验
     * <功能详细描述>
     * @throws Throwable
     */
    public String donationTgReturn(int orderId)
        throws Throwable;
}
