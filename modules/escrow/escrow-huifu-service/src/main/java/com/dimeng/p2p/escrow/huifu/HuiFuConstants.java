/*
 * 文 件 名:  HuiFuConstants.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年07月18日
 */
package com.dimeng.p2p.escrow.huifu;

/**
 * 
 * 汇付常量 <功能详细描述>
 * 
 * @author lingyuanjie
 * @version [版本号, 2016年7月18日]
 */
public interface HuiFuConstants
{
    /*
     * 成功返回码
     */
    interface Common
    {
        // 成功
        String SUCCESS_CODE = "000";
        
        // 请求处理中
        String PROCESSING = "999";
        
        // 投标已确认（表示投标请求已经成功处理返回）
        String BID_SUCCESS = "322";
    }
    
    /*
     * 用户第三方账号填补标识
     */
    interface UserCode
    {
        String USER_CODE = "00000";
    }
    
    /*
     * 用户第三方账号子账号标识
     */
    interface AcctId
    {
        String ACCT_ID = "MDT000001";
    }
    
    /*
     * 第三方接口版本
     */
    interface Version
    {
        String VERSION_10 = "10";
        
        String VERSION_20 = "20";
        
        String VERSION_30 = "30";
    }
    
    /*
     * 手续费收取对象: M 向商户收取, U 向用户收取
     */
    interface FeeObjFalg
    {
        String USER = "U";
        
        String MER = "M";
    }
    
    /*
     * 投资是否冻结：Y--冻结 ，N--不冻结
     */
    interface IsFreeze
    {
        String YES = "Y";
        
        String NO = "N";
    }
    
    /*
     * 标审核状态
     */
    interface AuditStatus
    {
        // 通过
        String SUCCESS = "01";
        
        // 拒绝
        String REFUSING = "02";
        
        // 待上传证照
        String NEED_UPLOAD = "03";
        
        // 待审核
        String NEED_AUDIT = "04";
        
        // 待审核证照
        String NEED_AUDIT_LICENSE = "05";
        
        // 状态异常
        String ERROR = "06";
    }
    
}
