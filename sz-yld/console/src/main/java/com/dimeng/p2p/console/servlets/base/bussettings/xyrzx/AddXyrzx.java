package com.dimeng.p2p.console.servlets.base.bussettings.xyrzx;

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
import com.dimeng.p2p.S51.entities.T5123;
import com.dimeng.p2p.console.servlets.base.AbstractBaseServlet;
import com.dimeng.p2p.modules.base.console.service.CreditCertificationManage;

@Right(id = "P2P_C_BASE_XYRZXADD", name = "新增信用认证项", moduleId = "P2P_C_BASE_OPTSETTINGS_XYRZX")
public class AddXyrzx extends AbstractBaseServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        forwardView(request, response, getClass());
    }
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        CreditCertificationManage creditCertificationManage =
            serviceSession.getService(CreditCertificationManage.class);
        T5123 t5123 = new T5123();
        t5123.parse(request);
        if (creditCertificationManage.isExist(t5123))
        {
            getController().prompt(request, response, PromptLevel.WARRING, "该类型名称已存在，不能重复新增");
            request.setAttribute("entity", t5123);
            forwardView(request, response, getClass());
        }
        else
        {
            creditCertificationManage.add(t5123);
            sendRedirect(request, response, getController().getURI(request, XyrzxList.class));
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
            sendRedirect(request, response, getController().getURI(request, AddXyrzx.class));
            
        }
        else if (throwable instanceof LogicalException || throwable instanceof ParameterException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, getController().getURI(request, AddXyrzx.class));
        }
        else
        {
            super.onThrowable(request, response, throwable);
            sendRedirect(request, response, getController().getURI(request, AddXyrzx.class));
        }
    }
    
}
