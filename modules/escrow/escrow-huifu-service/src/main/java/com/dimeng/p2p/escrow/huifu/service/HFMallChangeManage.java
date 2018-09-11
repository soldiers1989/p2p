package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;

/**
 * 
 * 积分商城管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月22日]
 */
public interface HFMallChangeManage extends Service
{
    /**
     * 商品购买前台回调校验
     * <功能详细描述>
     * @throws Throwable
     */
    public String mallChangeTgReturn(int orderId)
        throws Throwable;
    
    /**
     * 商品购买失败，回滚资金
     * <功能详细描述>
     * @throws Throwable
     */
    public void mallChangeFailBack(int orderId)
        throws Throwable;
    
    /**
     * 商品购买失败资金退回，回调处理
     * <功能详细描述>
     * @param orderId
     */
    public void mallChangeFailRet(int orderId)
        throws Throwable;
}
