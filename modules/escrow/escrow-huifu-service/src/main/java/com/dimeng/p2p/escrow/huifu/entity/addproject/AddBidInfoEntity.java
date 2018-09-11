package com.dimeng.p2p.escrow.huifu.entity.addproject;

import java.io.Serializable;

public class AddBidInfoEntity implements Serializable
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
     * 项目id
     */
    public String proId;
    
    /**
     * 借款人id
     */
    public String borrCustId;
    
    /**
     * 借款人总金融
     */
    public String borrTotAmt;
    
    /**
     * 担保公司id
     */
    public String guarCompId;
    
    /**
     * 担保金额
     */
    public String guarAmt;
    
    /**
     * 担保金额
     */
    public String proArea;
    
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
    
}
