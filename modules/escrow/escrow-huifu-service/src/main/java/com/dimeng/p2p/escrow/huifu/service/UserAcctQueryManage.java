package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6165;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserAcctQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserBankCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserInfoQueryEntity;

public abstract interface UserAcctQueryManage extends Service
{
    
    /**
     * 获取用户列表
     * 
     * @return
     * @throws Throwable
     */
    public PagingResult<T6110> selectUserList(String name, Paging paging, String userTag)
        throws Throwable;
    
    /**
     * 获取到审核中的企业账号集合
     * 
     * @return
     * @throws Throwable
     */
    public T6165[] selectQyT6165s()
        throws Throwable;
    
    /**
     * 获取用户第三方平台记录信息
     * 
     * @param F01
     * @param F02
     * @param F03
     * @return
     * @throws Throwable
     */
    public int selectT6119(int F01, int F02, String F03)
        throws Throwable;
    
    /**
     * 获取用户账户信息
     * 
     * @param usrCustId
     * @throws Throwable
     */
    public UserAcctQueryEntity accountInfo(int accountId)
        throws Throwable;
    
    /**
     * 获取用户银行卡信息
     * 
     * @param accountId
     * @return
     * @throws Throwable
     */
    public UserBankCardEntity cardInfo(int accountId)
        throws Throwable;
    
    /**
     * 获取企业账号审核信息
     * 
     * @param accountId
     * @return
     * @throws Throwable
     */
    public CorpQueryEntity corpUserInfo(int accountId)
        throws Throwable;
    
    /**
     * 获取普通用户账号信息
     * 
     * @param accountId
     * @return
     * @throws Throwable
     */
    public UserInfoQueryEntity memberUserInfo(int accountId)
        throws Throwable;
    
    /**
     * 获取用户自动投标信息
     * 
     * @param accountId
     * @return
     * @throws Throwable
     */
    public UserInfoQueryEntity userAutoBidInfo(int accountId)
        throws Throwable;
}
