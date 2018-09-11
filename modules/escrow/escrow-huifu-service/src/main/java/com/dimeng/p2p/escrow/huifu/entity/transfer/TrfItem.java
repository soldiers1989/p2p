package com.dimeng.p2p.escrow.huifu.entity.transfer;

import java.io.Serializable;

/**
 * 
 * 商户扣款对账回调实体类
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月9日]
 */
public class TrfItem implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 订单号
     */
    public String OrdId;
    
    /**
     * 客户商户号
     */
    public String MerCustId;
    
    /**
     * 用户客户号
     */
    public String UsrCustId;
    
    /**
     * 交易金额
     */
    public String TransAmt;
    
    /**
     * 交易状态     S--成功，F--失败，I--初始
     */
    public String TransStat;
    
    /**
     * 交易日期
     */
    public String PnrDate;
    
    /**
     * 交易流水
     */
    public String PnrSeqId;
    
    public String getOrdId()
    {
        return OrdId;
    }
    
    public void setOrdId(String ordId)
    {
        OrdId = ordId;
    }
    
    public String getMerCustId()
    {
        return MerCustId;
    }
    
    public void setMerCustId(String merCustId)
    {
        MerCustId = merCustId;
    }
    
    public String getUsrCustId()
    {
        return UsrCustId;
    }
    
    public void setUsrCustId(String usrCustId)
    {
        UsrCustId = usrCustId;
    }
    
    public String getTransAmt()
    {
        return TransAmt;
    }
    
    public void setTransAmt(String transAmt)
    {
        TransAmt = transAmt;
    }
    
    public String getTransStat()
    {
        return TransStat;
    }
    
    public void setTransStat(String transStat)
    {
        TransStat = transStat;
    }
    
    public String getPnrDate()
    {
        return PnrDate;
    }
    
    public void setPnrDate(String pnrDate)
    {
        PnrDate = pnrDate;
    }
    
    public String getPnrSeqId()
    {
        return PnrSeqId;
    }
    
    public void setPnrSeqId(String pnrSeqId)
    {
        PnrSeqId = pnrSeqId;
    }
}
