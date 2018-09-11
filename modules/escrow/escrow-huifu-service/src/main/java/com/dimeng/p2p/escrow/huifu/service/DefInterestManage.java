package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;

public interface DefInterestManage extends Service
{
    /**
     * 罚息计算.
     * 
     * @throws Throwable
     */
    public abstract void calculate() throws Throwable;
}
