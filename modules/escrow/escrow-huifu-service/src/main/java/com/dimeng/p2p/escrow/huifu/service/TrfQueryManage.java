package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TrfItem;

public abstract interface TrfQueryManage extends Service
{
    
    /**
     * 商户扣款对账
     * 
     * @param cond
     * @throws Throwable
     */
    public abstract PagingResult<TrfItem> queryTrfReconcilia(QueryCond cond)
        throws Throwable;
    
    /**
     * 通过汇付客户号查询用户名
     * 
     * @param usrId
     * @return
     * @throws Throwable
     */
    public String queryUsrName(String usrId)
        throws Throwable;
}
