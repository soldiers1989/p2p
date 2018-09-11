package com.dimeng.p2p.escrow.huifu.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S61.enums.T6130_F09;
import com.dimeng.p2p.S65.entities.T6503;
import com.dimeng.p2p.escrow.huifu.cond.WithdrawCond;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.CashAuditEntity;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawEntity;
import com.dimeng.p2p.modules.account.console.service.entity.UserWithdrawals;

public abstract interface WithdrawManage extends Service
{
    /**
     * 生成提现地址
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract String createWithdrawUri(WithdrawCond cond)
        throws Throwable;
    
    /**
     * 解析返回参数
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public abstract WithdrawEntity withdrawReturnDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 获取银行卡号
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    public abstract String getBankCard(int id)
        throws Throwable;
    
    /**
     * 获取用户商户号
     * 
     * @throws Throwable
     */
    public abstract String getUserCustId()
        throws Throwable;
    
    /**
     * 添加订单
     * 
     * @param bankCard
     * @param amount
     * @throws Throwable
     */
    public abstract T6503 addOrder(String bankCard, BigDecimal amount)
        throws Throwable;
    
    /**
     * 添加提现请求
     * 
     * @param orderId
     * @throws Throwable
     */
    public abstract void apply(int orderId)
        throws Throwable;
    
    /**
     * 取消提现
     * 
     * @param id
     *            提现申请id
     * @throws Throwable
     */
    public abstract void cancle(int id)
        throws Throwable;
    
    /**
     * 提现复核同步返回参数解析
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public abstract CashAuditEntity cashAuditRetDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 提现复核异步返回参数解析
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public abstract CashAuditEntity cashAuditRetAsynDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 更新提现申请为失败
     * 
     * @param id
     * @throws Throwable
     */
    public abstract String refuse(int id)
        throws Throwable;
    
    /**
     * 获取提现申请订单
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    public abstract int[] orderIds(T6130_F09 status, String checkResion, int... ids)
        throws Throwable;
    
    /**
     * 插入资金流水
     * 
     * @param ids
     * @throws Throwable
     */
    public abstract void trade(int... ids)
        throws Throwable;
    
    /**
     * 平台提现
     * 
     * @param amount
     * @throws Throwable
     */
    public abstract T6503 ptWithdraw(String bankCard, BigDecimal amount)
        throws Throwable;
    
    /**
     * 更新提现数据
     * @throws Throwable
     */
    public void doConfim(int ordId)
        throws Throwable;
    
    /**
     * 更新手续费
     * <功能详细描述>
     * @param orderId
     * @param realTransAmt 实际到账金额
     * @param feeAmt    第三方收取手续费
     * @param servFee   平台收取的手续费
     * @throws Throwable
     */
    public void updateT6503(int orderId, BigDecimal feeAmt, BigDecimal servFee, BigDecimal realTransAmt)
        throws Throwable;
    
    /**
     * 提现失败，解冻平台资金
     * @param F01
     * @return
     * @throws SQLException
     */
    public void withdrawFailUnFreze(int ordId)
        throws Throwable;
    
    /** 操作类别
     *  日志内容
     * @param type
     * @param log
     * @throws Throwable
     */
    public void writeFrontLog(String type, String log)
        throws Throwable;
    
    /**
     * 根据6503的F01修改提现状态,返回F09提现申请ID
     * <功能详细描述>
     * @param orderId
     * @throws Throwable
     */
    public int updateTxStatus(int orderId)
        throws Throwable;
    
    /**
     * 查询用户第三方可取现金额（前台传入参数为null,后台传入参数为用户第三方客户号）
     * <功能详细描述>
     * @param userAccount 用户第三方客户号
     * @return
     * @throws Throwable
     */
    public BigDecimal QueryCashBalanceBg(String userAccount)
        throws Throwable;
    
    /**
     * 解冻平台金额
     * @param F01
     * @return
     * @throws SQLException
     */
    public void unFreze(int ordId)
        throws Throwable;
    
    /**
     * 
     * <dl>
     * 描述：查询提现记录详情.
     * </dl>
     * 
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    public abstract UserWithdrawals get(int id)
        throws Throwable;
}
