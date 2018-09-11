package com.dimeng.p2p.console.servlets.base.bussettings.blx;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.S62.entities.T6211;
import com.dimeng.p2p.console.servlets.base.AbstractBaseServlet;
import com.dimeng.p2p.modules.base.console.service.BlxManage;
import com.dimeng.util.parser.IntegerParser;

@Right(id = "P2P_C_BASE_BLXVIEW", name = "修改标类型", moduleId = "P2P_C_BASE_OPTSETTINGS_BLX")
public class ViewBlx extends AbstractBaseServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        BlxManage manage = serviceSession.getService(BlxManage.class);
        int id = IntegerParser.parse(request.getParameter("id"));
        T6211 entity = manage.get(id);
        request.setAttribute("entity", entity);
        forwardView(request, response, getClass());
    }
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        BlxManage manage = serviceSession.getService(BlxManage.class);
        T6211 t6211 = new T6211();
        t6211.parse(request);
        if (manage.isExist(t6211))
        {
            getController().prompt(request, response, PromptLevel.WARRING, "该类型名称已存在，不能重复新增");
            request.setAttribute("entity", t6211);
            forwardView(request, response, getClass());
        }
        else
        {
            manage.update(t6211);
            sendRedirect(request, response, getController().getURI(request, BlxList.class));
        }
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        getResourceProvider().log(throwable.getMessage());
        if (throwable instanceof SQLException)
        {
            logger.error(throwable, throwable);
            getController().prompt(request, response, PromptLevel.ERROR, "系统繁忙，请您稍后再试");
            sendRedirect(request, response, getController().getURI(request, ViewBlx.class));
            
        }
        else if (throwable instanceof LogicalException || throwable instanceof ParameterException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, getController().getURI(request, ViewBlx.class));
        }
        else
        {
            super.onThrowable(request, response, throwable);
            sendRedirect(request, response, getController().getURI(request, ViewBlx.class));
        }
    }
    
}
