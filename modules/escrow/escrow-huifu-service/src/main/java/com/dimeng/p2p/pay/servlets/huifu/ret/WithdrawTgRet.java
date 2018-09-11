package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawEntity;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.p2p.variables.defines.URLVariable;

/**
 * 
 * 提现申请第三方返回页面地址
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class WithdrawTgRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 提现申请前台回调开始 ----------");
        WithdrawManage manage = serviceSession.getService(WithdrawManage.class);
        WithdrawEntity entity = manage.withdrawReturnDecoder(request);
        String location = getResourceProvider().getResource(ConfigureProvider.class).format(URLVariable.USER_WITHDRAW);
        if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            entity.respDesc = "恭喜您，提现申请成功，请等待运营人员审核";
        }
        getController().prompt(request, response, PromptLevel.INFO, entity.respDesc);
        sendRedirect(request, response, location);
    }
}
