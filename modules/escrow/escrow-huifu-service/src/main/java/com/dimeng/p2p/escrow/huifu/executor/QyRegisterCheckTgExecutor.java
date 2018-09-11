package com.dimeng.p2p.escrow.huifu.executor;

import java.util.Map;

import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S61.entities.T6165;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.p2p.order.QyRegisterCheckExecutor;

/**
 * 企业机构注册审核
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月1日]
 */
@AchieveVersion(version = 20151221)
@ResourceAnnotation
public class QyRegisterCheckTgExecutor extends QyRegisterCheckExecutor
{
    
    public QyRegisterCheckTgExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, Map<String, String> params)
        throws Throwable
    {
        try (ServiceSession serviceSession = resourceProvider.getResource(ServiceProvider.class).createServiceSession())
        {
            UserAcctQueryManage queryService = serviceSession.getService(UserAcctQueryManage.class);
            logger.info("开始执行汇付企业账号自动审核定时器..................");
            T6165[] t6165s = queryService.selectQyT6165s();
            for (T6165 t6165 : t6165s)
            {
                queryService.corpUserInfo(t6165.F01);
            }
            logger.info("结束汇付企业账号自动审核定时器..................");
        }
    }
    
}
