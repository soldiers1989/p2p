/*
 * 文 件 名:  HFZdtbClose.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月29日
 */
package com.dimeng.p2p.user.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.escrow.huifu.cond.CancleAutoBidCond;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;

/**
 * 自动投标计划关闭
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月29日]
 */
public class HFZdtbClose extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 6455695252942810932L;
    
    @Override
    protected void processGet(final HttpServletRequest request, HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        
        /*
         * ZdtbManage
         * autoUtilFinacingManage=serviceSession.getService(ZdtbManage.class);
         * autoUtilFinacingManage.close();
         * 
         * forward(request, response, getController().getURI(request,
         * Index.class));
         */
        BidManage manage = serviceSession.getService(BidManage.class);
        final String usrCustId = manage.getUserCustId();
        String location = manage.createCancleAutoBidUri(new CancleAutoBidCond()
        {
            
            @Override
            public String usrCustId()
            {
                return usrCustId;
            }
            
            @Override
            public String retUrl()
            {
                try
                {
                    return getResourceProvider().getResource(ConfigureProvider.class)
                        .format(HuifuVariable.HF_RETURL_CANCLEAUTOBID);
                }
                catch (Exception e)
                {
                    logger.error(e);
                }
                return null;
            }
            
            @Override
            public String merPriv()
            {
                return String.valueOf(serviceSession.getSession().getAccountId());
            }
        });
        
        logger.info("汇付 - 【自动投标计划关闭】请求参数：" + location);
        
        sendRedirect(request, response, location);
    }
    
}
