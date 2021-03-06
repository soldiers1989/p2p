package com.dimeng.p2p.S61.enums;


import com.dimeng.util.StringHelper;

/**
 * 是否回复
 */
public enum T6195_F06{

    yes("已回复"),
    no("未回复");

    protected final String chineseName;

    private T6195_F06(String chineseName){
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
     * @return {@link T6195_F06}
     */
    public static final T6195_F06 parse(String value) {
        if(StringHelper.isEmpty(value)){
            return null;
        }
        try{
            return T6195_F06.valueOf(value);
        }catch(Throwable t){
            return null;
        }
    }
}
