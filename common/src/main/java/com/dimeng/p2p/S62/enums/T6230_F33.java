package com.dimeng.p2p.S62.enums;


import com.dimeng.util.StringHelper;

/** 
 * 是否信用标
 */
public enum T6230_F33 {


    /**
     * 是
     */
    S("是"),

    /**
     * 否
     */
    F("否");

    protected final String chineseName;

    private T6230_F33(String chineseName){
        this.chineseName = chineseName;
    }
    /**
     * 获取中文名称.
     * 
     * @return {@link String}
     */
    public String getChineseName() {
        return chineseName;
    }
    /**
     * 解析字符串.
     * 
     * @return {@link T6230_F33}
     */
    public static final T6230_F33 parse(String value) {
        if(StringHelper.isEmpty(value)){
            return null;
        }
        try{
            return T6230_F33.valueOf(value);
        }catch(Throwable t){
            return null;
        }
    }
}
