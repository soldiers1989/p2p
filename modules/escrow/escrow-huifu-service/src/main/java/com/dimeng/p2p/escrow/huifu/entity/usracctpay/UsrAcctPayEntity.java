/*
 * 文 件 名:  UsrAcctPayEntity.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月5日
 */
package com.dimeng.p2p.escrow.huifu.entity.usracctpay;

import java.io.Serializable;

/**
 * 用户账户支付实体
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月5日]
 */
public class UsrAcctPayEntity implements Serializable
{
    
    public static final long serialVersionUID = 1L;
    
    public String cmdId;
    
    public String respCode;
    
    public String respDesc;
    
    public String ordId;
    
    public String usrCustId;
    
    public String merCustId;
    
    public String transAmt;
    
    public String inAcctId;
    
    public String inAcctType;
    
    public String retUrl;
    
    public String bgRetUrl;
    
    public String merPriv;
    
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
     * @return 返回 usrCustId
     */
    public String getUsrCustId()
    {
        return usrCustId;
    }
    
    /**
     * @param 对usrCustId进行赋值
     */
    public void setUsrCustId(String usrCustId)
    {
        this.usrCustId = usrCustId;
    }
    
    /**
     * @return 返回 merCustId
     */
    public String getMerCustId()
    {
        return merCustId;
    }
    
    /**
     * @param 对merCustId进行赋值
     */
    public void setMerCustId(String merCustId)
    {
        this.merCustId = merCustId;
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
     * @return 返回 inAcctType
     */
    public String getInAcctType()
    {
        return inAcctType;
    }
    
    /**
     * @param 对inAcctType进行赋值
     */
    public void setInAcctType(String inAcctType)
    {
        this.inAcctType = inAcctType;
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
