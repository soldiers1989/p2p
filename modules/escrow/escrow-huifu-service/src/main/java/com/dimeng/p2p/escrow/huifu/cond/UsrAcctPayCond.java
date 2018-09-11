/*
 * 文 件 名:  UsrAcctPayCond.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月5日
 */
package com.dimeng.p2p.escrow.huifu.cond;

/**
 * 用户账户支付
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月5日]
 */
public abstract interface UsrAcctPayCond
{
    /**
     * （必填），订单id
     */
    public String ordId();
    
    /**
     * （必填），用户客户号
     */
    public String usrCustId();
    
    /**
     * （必填），商户客户号
     */
    public String merCustId();
    
    /**
     * （必填），交易金额
     */
    public String transAmt();
    
    /**
     * （选填），入账子账户
     */
    public String inAcctId();
    
    /**
     * （必填），入账账户类型
     */
    public String inAcctType();
    
    /**
     * 可选，前台页面回调URL
     */
    public String retUrl();
    
    /**
     * （必填），后台回调URL
     */
    public String bgRetUrl();
    
}
