package com.dimeng.p2p.escrow.huifu.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.escrow.huifu.cond.TransferCond;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;

public abstract interface TransferManage extends Service
{
    
    /**
     * 发送转账信息
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract String sendTansfer(TransferCond cond)
        throws Throwable;
    
    /**
     * 
     * @param request
     * @throws Throwable
     */
    public abstract void decoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 平台转账
     * 
     * @param name
     * @param amount
     * @param reason
     * @throws Throwable
     */
    public abstract void transfer(String name, BigDecimal amount, String reason)
        throws Throwable;
    
    /**
     * 转账回调解析
     * <功能详细描述>
     * @param request
     * @throws Throwable
     */
    public abstract TransferEntity transferReturnDecoder(HttpServletRequest request)
        throws Throwable;
    
}
