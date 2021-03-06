/*
 * 文 件 名:  T6504_F07.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  God
 * 修改时间:  2016年4月22日
 */
package com.dimeng.p2p.S65.enums;

import com.dimeng.util.StringHelper;

/**
 * <来源渠道>
 * <功能详细描述>
 * 
 * @author  zhoucl
 * @version  [版本号, 2016年4月22日]
 */
public enum T6504_F07
{
    /** 
     * PC
     */
    PC("PC"),
    
    /** 
     * APP
     */
    APP("APP"),
    
    /** 
     * WEIXIN
     */
    WEIXIN("微信");
    
    protected final String chineseName;
    
    private T6504_F07(String chineseName)
    {
        this.chineseName = chineseName;
    }
    
    /**
     * 获取中文名称.
     * 
     * @return {@link String}
     */
    public String getChineseName()
    {
        return chineseName;
    }
    
    /**
     * 解析字符串.
     * 
     * @return {@link T6504_F07}
     */
    public static final T6504_F07 parse(String value)
    {
        if (StringHelper.isEmpty(value))
        {
            return null;
        }
        try
        {
            return T6504_F07.valueOf(value);
        }
        catch (Throwable t)
        {
            return null;
        }
    }
}
