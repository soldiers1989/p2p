package com.dimeng.p2p.escrow.huifu.entity.transfer;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * 转账查询
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月27日]
 */
public class TrfQueryEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public String CmdId;
    
    public String RespCode;
    
    public String RespDesc;
    
    public String MerCustId;
    
    public String BeginDate;
    
    public String EndDate;
    
    public String Version;
    
    public int PageNum;
    
    public int PageSize;
    
    public int TotalItems;
    
    public List<TrfItem> TrfReconciliationDtoList;
    
    public String ChkValue;
    
    public String PlainText;
    
    public String getCmdId()
    {
        return CmdId;
    }
    
    public void setCmdId(String cmdId)
    {
        CmdId = cmdId;
    }
    
    public String getRespCode()
    {
        return RespCode;
    }
    
    public void setRespCode(String respCode)
    {
        RespCode = respCode;
    }
    
    public String getRespDesc()
    {
        return RespDesc;
    }
    
    public void setRespDesc(String respDesc)
    {
        RespDesc = respDesc;
    }
    
    public String getMerCustId()
    {
        return MerCustId;
    }
    
    public void setMerCustId(String merCustId)
    {
        MerCustId = merCustId;
    }
    
    public String getBeginDate()
    {
        return BeginDate;
    }
    
    public void setBeginDate(String beginDate)
    {
        BeginDate = beginDate;
    }
    
    public String getEndDate()
    {
        return EndDate;
    }
    
    public void setEndDate(String endDate)
    {
        EndDate = endDate;
    }
    
    public String getVersion()
    {
        return Version;
    }
    
    public void setVersion(String version)
    {
        Version = version;
    }
    
    public int getPageNum()
    {
        return PageNum;
    }
    
    public void setPageNum(int pageNum)
    {
        PageNum = pageNum;
    }
    
    public int getPageSize()
    {
        return PageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        PageSize = pageSize;
    }
    
    public int getTotalItems()
    {
        return TotalItems;
    }
    
    public void setTotalItems(int totalItems)
    {
        TotalItems = totalItems;
    }
    
    public List<TrfItem> getTrfReconciliationDtoList()
    {
        return TrfReconciliationDtoList;
    }
    
    public void setTrfReconciliationDtoList(List<TrfItem> trfReconciliationDtoList)
    {
        TrfReconciliationDtoList = trfReconciliationDtoList;
    }
    
    public String getChkValue()
    {
        return ChkValue;
    }
    
    public void setChkValue(String chkValue)
    {
        ChkValue = chkValue;
    }
    
    public String getPlainText()
    {
        return PlainText;
    }
    
    public void setPlainText(String plainText)
    {
        PlainText = plainText;
    }
    
}
