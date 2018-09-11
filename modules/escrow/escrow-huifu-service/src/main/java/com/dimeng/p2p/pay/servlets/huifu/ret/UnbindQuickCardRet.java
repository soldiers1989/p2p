package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.UnbindCardEntity;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;

/**
 * 
 * 解绑快捷卡通知
 * 
 * 需在商户第三方控台配置地址：http://平台网址/pay/huifu/ret/unbindQuickCardRet.htm
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2015年10月21日]
 */
public class UnbindQuickCardRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 第三方回调解绑快捷卡通知 ----------");
        BankCardManage manage = serviceSession.getService(BankCardManage.class);
        UnbindCardEntity entity = manage.unbindQuickCardRetDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                manage.updateQuickCard(entity);
            }
            catch (Exception e)
            {
                logger.error("解绑快捷卡通知异常！" + e);
            }
        }
        printMark(response, entity != null ? entity.trxId : request.getParameter("TrxId"));
    }
    
}
