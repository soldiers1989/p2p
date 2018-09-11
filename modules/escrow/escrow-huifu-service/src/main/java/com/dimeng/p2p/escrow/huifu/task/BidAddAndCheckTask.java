package com.dimeng.p2p.escrow.huifu.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dimeng.framework.message.email.EmailSender;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.common.ResourceProviderUtil;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.modules.account.console.service.GrManage;
import com.dimeng.p2p.modules.bid.console.service.AddBidManage;
import com.dimeng.p2p.variables.defines.EmailVariavle;

/**
 * 标自动汇付录入及审核定时器
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月6日]
 */
public class BidAddAndCheckTask
{
    
    private static final Logger LOGGER = Logger.getLogger(BidAddAndCheckTask.class);
    
    public void bidStatusQuery()
    {
        ResourceProvider resourceProvider = ResourceProviderUtil.getResourceProvider();
        
        try (ServiceSession serviceSession = resourceProvider.getResource(ServiceProvider.class).createServiceSession())
        {
            AddBidManage addBidManage = serviceSession.getService(AddBidManage.class);
            List<T6230> t6230s = addBidManage.selectT6230s();
            if (t6230s != null && !t6230s.isEmpty())
            {
                LOGGER.info("开始执行【标自动审核】任务，开始时间：" + new Date());
                
                for (T6230 t6230 : t6230s)
                {
                    LOGGER.info("开始执行【标自动审核】任务 -- 标号：" + t6230.F01 + "，标编号：" + t6230.F25);
                    Map<String, String> map = addBidManage.checkBidInfo(t6230.F01);
                    this.sendEmail(map, serviceSession, t6230);
                }
                LOGGER.info("结束执行【标自动审核】任务，结束时间：" + new Date());
            }
        }
        catch (Throwable e)
        {
            LOGGER.error(e.toString(), e);
        }
    }
    
    public void sendEmail(Map<String, String> map, ServiceSession serviceSession, T6230 t6230)
        throws Throwable
    {
        if (null != map)
        {
            if (HuiFuConstants.AuditStatus.REFUSING.equals(map.get("stat"))
                || HuiFuConstants.AuditStatus.ERROR.equals(map.get("stat")))
            {
                // “02”拒绝和“06”异常状态，直接将标状态改为已作废，执行作废流程
                EmailSender emailSender = serviceSession.getService(EmailSender.class);
                emailSender.send(0,
                    "审核不通过",
                    String.format(EmailVariavle.JKSHBTG_MAIL_STR.getDescription(), map.get("desc"), ""),
                    serviceSession.getService(GrManage.class).findBasicInfo(t6230.F02).mailbox);
            }
        }
    }
    
}
