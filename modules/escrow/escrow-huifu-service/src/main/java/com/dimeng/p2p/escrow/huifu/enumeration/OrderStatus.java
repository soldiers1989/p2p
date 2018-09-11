package com.dimeng.p2p.escrow.huifu.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * 汇付交易状态枚举类
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月5日]
 */
@XmlType
@XmlEnum(value = String.class)
public enum OrderStatus
{
    // 投标
    /**
     * 初始
     */
    I("初始"),
    /**
     * 成功或是无需解冻
     */
    N("成功或是无需解冻"),
    /**
     * 失败
     */
    C("失败"),
    // 放款、还款
    /**
     * 部分成功
     */
    P("部分成功"),
    
    // 取现
    /**
     * 成功
     */
    S("成功"),
    /**
     * 失败或是冻结
     */
    F("失败或是冻结"),
    /**
     * 经办
     */
    H("经办"),
    /**
     * 拒绝
     */
    R("拒绝"),
    // 冻结解冻
    /**
     * 已解冻
     */
    U("已解冻");
    
    private String description;
    
    private OrderStatus(String description)
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
