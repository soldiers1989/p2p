/*
 * 文 件 名:  HFOfflineChargeManage.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S71.entities.T7150;
import com.dimeng.p2p.S71.enums.T7150_F05;

/**
 * 线下充值审核通过管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public interface HFOfflineChargeManage extends Service
{
    
    /**
    * @param id
    *            线下充值记录ID
    * @param passed
    *            是否审核通过
    * @throws Throwable
    */
    public int checkCharge(int id, boolean passed)
        throws Throwable;
    
    /**
     * 处理平台资金流水记录
     * <功能详细描述>
     * @param orderId
     */
    void updatePtRecord(int orderId)
        throws Throwable;
    
    /**
     * 查询线下充值申请
     * <功能详细描述>
     * @param F01
     * @return
     * @throws Throwable
     */
    T7150 selectT7150(int F01)
        throws Throwable;
    
    /**
     * 修改线下充值申请的状态
     * <功能详细描述>
     * @param orderId
     * @throws Throwable
     */
    void updateT7150(int orderId, T7150_F05 status)
        throws Throwable;
}
