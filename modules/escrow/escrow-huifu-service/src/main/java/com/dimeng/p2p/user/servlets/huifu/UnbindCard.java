/*
 * 文 件 名:  UnbindCard.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  删除银行卡
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月20日
 */
package com.dimeng.p2p.user.servlets.huifu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.AbstractHuifuServlet;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.UnbindCardEntity;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;

/**
 * 删除银行卡servlet
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月20日]
 */
public class UnbindCard extends AbstractHuifuServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        int id = IntegerParser.parse(request.getParameter("id"));
        BankCardManage manage = serviceSession.getService(BankCardManage.class);
        //获取用户商户号
        String userCustId = manage.getUserCustId();
        //获取银行卡号
        String cardId = manage.getBankCard(id);
        
        if (StringHelper.isEmpty(cardId))
        {
            getController().prompt(request, response, PromptLevel.WARRING, "无此银行卡绑定");
            String location = getConfigureProvider().format(URLVariable.CARD_MANAGE);
            sendRedirect(request, response, location);
            return;
        }
        UnbindCardEntity entity = manage.sendUnbindCard(userCustId, cardId);
        
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            String location = getConfigureProvider().format(URLVariable.CARD_MANAGE);
            manage.unbindCard(entity);
            getController().prompt(request, response, PromptLevel.INFO, "银行卡删除成功");
            sendRedirect(request, response, location);
            return;
        }
        else if (entity != null)
        {
            getController().prompt(request, response, PromptLevel.WARRING, entity.respDesc);
            String location = getConfigureProvider().format(URLVariable.CARD_MANAGE);
            sendRedirect(request, response, location);
            return;
        }
    }
    
}
