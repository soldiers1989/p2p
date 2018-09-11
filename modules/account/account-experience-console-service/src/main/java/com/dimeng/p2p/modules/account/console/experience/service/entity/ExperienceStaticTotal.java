/*
 * 文 件 名:  ExperienceStaticTotal.java
 * 版    权:  © 2014 DM. All rights reserved.
 * 描    述:  体验金统计概要信息
 * 修 改 人:  linxiaolin
 * 修改时间:  2014/9/25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.dimeng.p2p.modules.account.console.experience.service.entity;

import java.math.BigDecimal;

/**
 * 体验金统计概要信息
 * @author linxiaolin
 * @version [版本号, 2014/9/25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ExperienceStaticTotal {

    /**
     * 待还体验金利息
     */
    public BigDecimal totalDh = new BigDecimal(0);
    /**
     * 未还体验金利息
     */
    public BigDecimal totalYh = new BigDecimal(0);


}
