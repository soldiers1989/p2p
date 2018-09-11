package com.dimeng.p2p.escrow.huifu.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * 交易类型查询枚举类
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月5日]
 */
@XmlType
@XmlEnum(value = String.class)
public enum QueryMode
{
    
    @XmlEnumValue(value = "LOANS")
    LOANS("放款交易查询"),
    
    @XmlEnumValue(value = "REPAYMENT")
    REPAYMENT("还款交易查询"),
    
    @XmlEnumValue(value = "TENDER")
    TENDER("投标交易查询"),
    
    @XmlEnumValue(value = "CASH")
    CASH("取现交易查询"),
    
    @XmlEnumValue(value = "FREEZE")
    FREEZE("冻结解交易查询");
    
    private String description;
    
    private QueryMode(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
}
