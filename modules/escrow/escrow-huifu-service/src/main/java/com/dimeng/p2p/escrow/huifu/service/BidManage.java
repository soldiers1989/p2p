package com.dimeng.p2p.escrow.huifu.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S62.enums.T6280_F10;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6514;
import com.dimeng.p2p.S65.entities.T6529;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.cond.AddBidInfoCond;
import com.dimeng.p2p.escrow.huifu.cond.AutoBidCond;
import com.dimeng.p2p.escrow.huifu.cond.BidCancleCond;
import com.dimeng.p2p.escrow.huifu.cond.BidCond;
import com.dimeng.p2p.escrow.huifu.cond.CancleAutoBidCond;
import com.dimeng.p2p.escrow.huifu.entity.addproject.AddBidInfoEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.AutoBidEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidCancleEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidCancleReturn;
import com.dimeng.p2p.escrow.huifu.entity.bid.BidEntity;
import com.dimeng.p2p.escrow.huifu.entity.bid.CancleAutoBidEntity;
import com.dimeng.p2p.escrow.huifu.entity.loans.LoansEntity;
import com.dimeng.p2p.escrow.huifu.entity.payment.PaymentEntity;

public interface BidManage extends Service
{
    
    /**
     * 投资
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract String createBidUrI(BidCond cond)
        throws Throwable;
    
    /**
     * 解析投资返回参数（后台)
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public abstract BidEntity bidReturnDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 解析投资返回参数(前台)
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public abstract BidEntity bidTgReturnDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 投资取消
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract String CreateBidCancleUri(BidCancleCond cond)
        throws Throwable;
    
    /**
     * 解析取消投资返回参数
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public abstract BidCancleEntity bidCancleReturnDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 还款
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract PaymentEntity payment(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 放款
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract LoansEntity loans(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 获取用户商户号
     * 
     * @throws Throwable
     */
    public abstract String getUserCustId()
        throws Throwable;
    
    /**
     * 
     * @param orderId
     * @return
     * @throws Throwable
     */
    public T6501 selectT6501(int orderId)
        throws Throwable;
    
    /**
     * 
     * @param orderId
     * @return
     * @throws Throwable
     */
    public T6504 selectT6504(int orderId)
        throws Throwable;
    
    /**
     * 获取借款人信息
     * 
     * @param loanId
     * @return
     * @throws Throwable
     */
    public String borrowerDetails(int loanId, BigDecimal amount)
        throws Throwable;
    
    /**
     * 还款失败更新还款计划为未还
     * 
     * @param orderId
     * @throws Throwable
     */
    public void updatePayFailT6252(int orderId)
        throws Throwable;
    
    /**
     * 归还已放款投资
     * 
     * @param ordId
     *            投资订单id
     * @throws Throwable
     */
    public PaymentEntity repayment(int ordId)
        throws Throwable;
    
    /**
     * 后台还款处理
     * 
     * @param ordId
     *            还款订单id
     * @throws Throwable
     */
    public void bgRepaymented(int ordId)
        throws Throwable;
    
    /**
     * 创建自动投资访问地址
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public String createAutoBidUri(AutoBidCond cond)
        throws Throwable;
    
    /**
     * 解析自动投资参数
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public AutoBidEntity autoBidRetDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 更新投资状态
     * 
     * @param accountId
     * @throws Throwable
     */
    public void updateAutoBidStatus(int accountId, T6280_F10 status)
        throws Throwable;
    
    /**
     * 创建投资撤销访问地址
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public String createCancleAutoBidUri(CancleAutoBidCond cond)
        throws Throwable;
    
    /**
     * 取消自动投资返回参数解析
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public CancleAutoBidEntity cancleAutoBidRetDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 修复标未生产还款计划、未更新标状态为“还款中”，未收取成交服务费
     * 
     * @param loanId
     * @throws Throwable
     */
    public void confirmRepair(int loanId)
        throws Throwable;
    
    /**
     * 更新订单状态
     * 
     * @param ordId
     * @throws Throwable
     */
    public void updateOrderStatus(int ordId)
        throws Throwable;
    
    /**
     * 标的信息录入
     * 
     * @param cond
     * @return
     * @throws Throwable
     */
    public abstract String addBidInfoUrI(AddBidInfoCond cond)
        throws Throwable;
    
    /**
     * 标的信息录入参数解析
     * 
     * @param request
     * @return
     * @throws Throwable
     */
    public AddBidInfoEntity addBidInfoDecoder(HttpServletRequest request)
        throws Throwable;
    
    /**
     * 进行标验证
     * 
     * @param orderId
     * @throws Throwable
     */
    public void checkBidInfo(int orderId)
        throws Throwable;
    
    /**
     * 更新订单状态
     * 
     * @param ordId
     * @param f03
     * @throws Throwable
     */
    public void updateT6501(int ordId, T6501_F03 F03)
        throws Throwable;
    
    /**
     * 更新对账状态
     * 
     * @param ordId
     * @param isDz
     *            是否已经对账
     * @param f03
     *            订单状态
     * @throws Throwable
     */
    public void updateOrderDZStatus(int ordId, T6501_F03 F03)
        throws Throwable;
    
    /**
    * 提前还款失败更新还款计划为未还
    * 
    * @param orderId
    * @throws Throwable
    */
    public void updatePreFailT6252(int orderId)
        throws Throwable;
    
    /**
     * 查询红包投资订单
     * @param connection
     * @param F01 红包订单
     * @param F02 投资人ID
     * @return
     * @throws SQLException
     */
    public BigDecimal selectT6527(int F01, int F02)
        throws Throwable;
    
    /** 红包参数组装
     * <功能详细描述>
     * @param string
     * @return
     */
    public String bidRewardHB(String orderId, BigDecimal amount)
        throws Throwable;
    
    /**
     * 通过放款订单号查找冻结订单号
     * @param orderId
     * @return
     * @throws Throwable
     */
    public List<T6501> queryUnFreezeOrder(int orderId)
        throws Throwable;
    
    /**
    * 不良债权购买对账失败，更新还款计划为未还
    * 
    * @param orderId
    * @throws Throwable
    */
    public void updateBadFailT6252(int orderId, int badclaimNum)
        throws Throwable;
    
    /**
    * 垫付对账失败，更新还款计划为未还
    * 
    * @param orderId
    * @throws Throwable
    */
    public void updateAdvanceFailT6252(int orderId, int advanceNum)
        throws Throwable;
    
    /** 
     * 查询不良债权转让订单
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    public T6529 selectT6529(Connection connection, int F01)
        throws Throwable;
    
    /** 
     * 查询垫付订单
     * @param connection
     * @param F01
     * @return
     * @throws SQLException
     */
    public T6514 selectT6514(Connection connection, int F01)
        throws Throwable;
    
    /**
     * 查询标的状态
     * <功能详细描述>
     * @param bidId
     * @return
     * @throws Throwable
     */
    public Map<String, String> queryBidStatus(int bidId)
        throws Throwable;
    
    /**
     * 投标撤销
     * <功能详细描述>
     * @param ordId
     * @return
     * @throws Throwable
     */
    public BidCancleReturn cancel(int ordId)
        throws Throwable;
    
}