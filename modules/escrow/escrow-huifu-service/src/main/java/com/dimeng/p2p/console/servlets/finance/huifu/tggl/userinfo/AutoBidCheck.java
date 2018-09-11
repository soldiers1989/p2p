package com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.UserInfoQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 用户自动投标信息查询
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月29日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUAUTOBIDCHECK", isMenu = true, name = "自动投标查询", moduleId = "P2P_C_HUIFU_USERINFO", order = 2)
public class AutoBidCheck extends AbstractDzglServlet
{
    
    private static final long serialVersionUID = -8763942853365053754L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        this.processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        int id = IntegerParser.parse(request.getParameter("id"));
        UserAcctQueryManage manage = serviceSession.getService(UserAcctQueryManage.class);
        UserInfoQueryEntity entity = manage.userAutoBidInfo(id);
        if (null != entity)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode))
            {
                if (null != entity.TransStat && "N".equals(entity.TransStat))
                {
                    processRequest(request, response, "OK用户已开启自动投标功能！");
                }
                else if ("C".equals(entity.TransStat))
                {
                    processRequest(request, response, "用户已关闭自动投标功能！");
                }
            }
            else
            {
                processRequest(request, response, "用户尚未开启自动投标功能！");
            }
        }
        else
        {
            processRequest(request, response, "用户尚未注册第三方账号！");
        }
    }
}
