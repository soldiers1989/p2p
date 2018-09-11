package com.dimeng.p2p.escrow.huifu.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.ITransferManageService;
import com.dimeng.p2p.S65.entities.T6517;
import com.dimeng.p2p.common.ResourceProviderUtil;
import com.dimeng.p2p.order.TransferExecutor;

/**
 * 
 * 转账处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月27日]
 */
public class TransferTask
{
    private static final Logger LOGGER = Logger.getLogger(TransferTask.class);
    
    public void transfer()
    {
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        
        ServiceProvider serviceProvider = resourceProvider.getResource(ServiceProvider.class);
        try (ServiceSession serviceSession = serviceProvider.createServiceSession())
        {
            List<T6517> orderIds = serviceSession.getService(ITransferManageService.class).findTransferOrderList();
            if (orderIds != null && !orderIds.isEmpty())
            {
                LOGGER.info("开始执行【自动转账】任务，开始时间：" + new Date());
                TransferExecutor executor = resourceProvider.getResource(TransferExecutor.class);
                
                for (T6517 t6517 : orderIds)
                {
                    LOGGER.info("开始执行【自动转账】任务 -- 订单号：" + t6517.F01);
                    
                    executor.submit(t6517.F01, null);
                }
                LOGGER.info("结束执行【自动转账】任务，结束时间：" + new Date());
            }
        }
        catch (Throwable e)
        {
            LOGGER.error(e.toString(), e);
        }
    }
}
