package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.BindCardEntity;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;

/**
 * 
 * 绑卡回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class BindCardRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 绑定银行卡回调开始 ----------");
        BankCardManage manage = serviceSession.getService(BankCardManage.class);
        BindCardEntity entity = manage.bindCardReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                manage.insertT6114(entity);
            }
            catch (Exception e)
            {
                logger.error("绑定银行卡回调失败！" + e);
            }
        }
        
        printMark(response, entity != null ? entity.trxId : request.getParameter("trxId"));
    }
    
}
