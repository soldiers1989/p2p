package com.dimeng.p2p.console.servlets.finance.huifu.tggl.usercard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 用户信息列表
 * 
 * @author raoyujun
 * @version [版本号, 2016年8月1日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUUSERCARDLIST", isMenu = true, name = "用户信息列表", moduleId = "P2P_C_HUIFU_USERCARD", order = 0)
public class UserCardList extends AbstractDzglServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        final int pageNum = IntegerParser.parse(request.getParameter(PAGING_CURRENT));
        final String name = request.getParameter("userName");
        String userTag = request.getParameter("userTag");
        Paging paging = new Paging()
        {
            
            @Override
            public int getSize()
            {
                return 10;
            }
            
            @Override
            public int getCurrentPage()
            {
                if (pageNum > 0)
                {
                    return pageNum;
                }
                return 0;
            }
        };
        UserAcctQueryManage manage = serviceSession.getService(UserAcctQueryManage.class);
        PagingResult<T6110> pagingResult = manage.selectUserList(name, paging, userTag);
        request.setAttribute("pagingResult", pagingResult);
        request.setAttribute("userTag", userTag);
        forward(request, response, getController().getViewURI(request, getClass()));
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
}
