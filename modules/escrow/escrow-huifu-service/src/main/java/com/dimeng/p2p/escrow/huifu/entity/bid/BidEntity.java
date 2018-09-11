package com.dimeng.p2p.escrow.huifu.entity.bid;

import java.io.Serializable;

/**
 * 
 * 投标实体类
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月28日]
 */
public class BidEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 消息类型
     */
    public String cmdId;
    
    /**
     * 应答返回码
     */
    public String respCode;
    
    /**
     * 应答描述
     */
    public String respDesc;
    
    /**
     * 商户客户号
     */
    public String merCustId;
    
    /**
     * 订单id
     */
    public String ordId;
    
    /**
     * 订单时间
     */
    public String ordDate;
    
    /**
     * 交易金额
     */
    public String transAmt;
    
    /**
     * 用户客户号
     */
    public String usrCustId;
    
    /**
     * 交易唯一标识
     */
    public String trxId;
    
    /**
     * 是否冻结
     */
    public String isFreeze;
    
    /**
     * 冻结订单id
     */
    public String freezeOrdId;
    
    /**
     * 冻结标识
     */
    public String freezeTrxId;
    
    /**
     * 前台返回地址
     */
    public String retUrl;
    
    /**
     * 商户后台应答地址
     */
    public String bgRetUrl;
    
    /**
     * 校验值
     */
    public String chkValue;
    
    /**
    * 返回扩展域
    */
    public String respExt;
    
    /**
     * 私有域
     */
    public String merPriv;
}
