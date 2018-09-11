package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.register.CorpRegisterEntity;
import com.dimeng.p2p.escrow.huifu.service.UserManage;

/**
 * 
 * 企业注册回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class CorpRegisterRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 企业开户回调开始 ----------");
        UserManage manage = serviceSession.getService(UserManage.class);
        CorpRegisterEntity entity = manage.corpRegisterDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                manage.updateCropInfo(entity);
            }
            catch (Exception e)
            {
                logger.error("企业开户回调异常：" + e);
            }
        }
        //响应第三方
        printMark(response, entity != null ? entity.trxId : request.getParameter("TrxId"));
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        logger.error(throwable, throwable);
    }
}
