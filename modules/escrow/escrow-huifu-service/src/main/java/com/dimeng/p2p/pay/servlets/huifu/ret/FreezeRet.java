package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.freeze.FreezeEntity;
import com.dimeng.p2p.escrow.huifu.service.FreezeManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 冻结回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class FreezeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("------- 冻结回调开始 ---------");
        FreezeManage manage = serviceSession.getService(FreezeManage.class);
        FreezeEntity entity = manage.freezeRetDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                manage.updateTrxId(entity.trxId, IntegerParser.parse(entity.ordId));
            }
            catch (Exception e)
            {
                logger.error("冻结回调异常：" + e);
            }
        }
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
    
}
