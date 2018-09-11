package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.S61.entities.T6165;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;

/**
 * 
 * 查询企业注册状态
 * 
 * @author  lijianping
 * @version  [版本号, 2014年11月29日]
 */
public abstract interface CorpRegQueryManage extends Service
{
    
    /**
     * 企业第三方托管中注册状态查询
     * 
     * @param 
     * @throws Throwable
     */
    public abstract CorpQueryEntity query(String busiCode)
        throws Throwable;

    /**
     * 更新企业信息
     * 
     * @param entity
     * @param t6165 
     * @param t6161 
     * @param id
     */
    public abstract void updateCorpInfo(CorpQueryEntity entity, T6161 t6161, T6165 t6165, int id);
    
    
    /**
     * 查询T6165表
     * 
     * @param id
     * @return T6165
     */
    public abstract T6165 getZhshzt(int id) throws Throwable;;
  
    
}
