/*
 * 文 件 名:  TransferEntity.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月27日
 */
package com.dimeng.p2p.escrow.huifu.entity.transfer;

import java.io.Serializable;

/**
 * 转账实体
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月27日]
 */
public class TransferEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /*
     * 消息类型
     */
    public String cmdId;
    
    /*
     * 应答返回码
     */
    public String respCode;
    
    /*
     * 应答描述
     */
    public String respDesc;
    
    /*
     * 订单号
     */
    public String ordId;
    
    /*
     * 出账客户号
     */
    public String outCustId;
    
    /*
     * 出账子账户
     */
    public String outAcctId;
    
    /*
     * 交易金额
     */
    public String transAmt;
    
    /*
     * 入账客户号
     */
    public String inCustId;
    
    /*
     * 入账子账户
     */
    public String inAcctId;
    
    /*
     * 页面返回URL
     */
    public String retUrl;
    
    /*
     * 商户后台应答地址
     */
    public String bgRetUrl;
    
    /*
     * 商户私有域
     */
    public String merPriv;
    
    /*
     * 签名
     */
    public String chkValue;
    
    /**
     * @return 返回 cmdId
     */
    public String getCmdId()
    {
        return cmdId;
    }
    
    /**
     * @param 对cmdId进行赋值
     */
    public void setCmdId(String cmdId)
    {
        this.cmdId = cmdId;
    }
    
    /**
     * @return 返回 respCode
     */
    public String getRespCode()
    {
        return respCode;
    }
    
    /**
     * @param 对respCode进行赋值
     */
    public void setRespCode(String respCode)
    {
        this.respCode = respCode;
    }
    
    /**
     * @return 返回 respDesc
     */
    public String getRespDesc()
    {
        return respDesc;
    }
    
    /**
     * @param 对respDesc进行赋值
     */
    public void setRespDesc(String respDesc)
    {
        this.respDesc = respDesc;
    }
    
    /**
     * @return 返回 ordId
     */
    public String getOrdId()
    {
        return ordId;
    }
    
    /**
     * @param 对ordId进行赋值
     */
    public void setOrdId(String ordId)
    {
        this.ordId = ordId;
    }
    
    /**
     * @return 返回 outCustId
     */
    public String getOutCustId()
    {
        return outCustId;
    }
    
    /**
     * @param 对outCustId进行赋值
     */
    public void setOutCustId(String outCustId)
    {
        this.outCustId = outCustId;
    }
    
    /**
     * @return 返回 outAcctId
     */
    public String getOutAcctId()
    {
        return outAcctId;
    }
    
    /**
     * @param 对outAcctId进行赋值
     */
    public void setOutAcctId(String outAcctId)
    {
        this.outAcctId = outAcctId;
    }
    
    /**
     * @return 返回 transAmt
     */
    public String getTransAmt()
    {
        return transAmt;
    }
    
    /**
     * @param 对transAmt进行赋值
     */
    public void setTransAmt(String transAmt)
    {
        this.transAmt = transAmt;
    }
    
    /**
     * @return 返回 inCustId
     */
    public String getInCustId()
    {
        return inCustId;
    }
    
    /**
     * @param 对inCustId进行赋值
     */
    public void setInCustId(String inCustId)
    {
        this.inCustId = inCustId;
    }
    
    /**
     * @return 返回 inAcctId
     */
    public String getInAcctId()
    {
        return inAcctId;
    }
    
    /**
     * @param 对inAcctId进行赋值
     */
    public void setInAcctId(String inAcctId)
    {
        this.inAcctId = inAcctId;
    }
    
    /**
     * @return 返回 retUrl
     */
    public String getRetUrl()
    {
        return retUrl;
    }
    
    /**
     * @param 对retUrl进行赋值
     */
    public void setRetUrl(String retUrl)
    {
        this.retUrl = retUrl;
    }
    
    /**
     * @return 返回 bgRetUrl
     */
    public String getBgRetUrl()
    {
        return bgRetUrl;
    }
    
    /**
     * @param 对bgRetUrl进行赋值
     */
    public void setBgRetUrl(String bgRetUrl)
    {
        this.bgRetUrl = bgRetUrl;
    }
    
    /**
     * @return 返回 merPriv
     */
    public String getMerPriv()
    {
        return merPriv;
    }
    
    /**
     * @param 对merPriv进行赋值
     */
    public void setMerPriv(String merPriv)
    {
        this.merPriv = merPriv;
    }
    
    /**
     * @return 返回 chkValue
     */
    public String getChkValue()
    {
        return chkValue;
    }
    
    /**
     * @param 对chkValue进行赋值
     */
    public void setChkValue(String chkValue)
    {
        this.chkValue = chkValue;
    }
    
}
