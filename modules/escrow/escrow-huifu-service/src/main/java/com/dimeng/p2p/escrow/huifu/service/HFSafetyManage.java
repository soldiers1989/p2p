package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.p2p.account.user.service.SafetyManage;

/**
 *  托管引导页实名认证管理 
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月19日]
 */
public interface HFSafetyManage extends SafetyManage
{
    /**
     * 校验手机号是否存在
     * <功能详细描述>
     * @param phone
     * @return 
     */
    public boolean checkPhoneIsExist(String phone)
        throws Throwable;
}
