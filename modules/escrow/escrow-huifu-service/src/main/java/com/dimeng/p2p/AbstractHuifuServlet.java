package com.dimeng.p2p;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.http.servlet.AbstractServlet;
import com.dimeng.framework.service.ServiceSession;

public abstract class AbstractHuifuServlet extends AbstractServlet
{
    
    private static final long serialVersionUID = 1L;
    
    protected static final String charSet = "utf-8";
    
    /**
     * 返回第三方响应，告知已收到回调请求
     * <功能详细描述>
     * @param response
     * @param mark
     * @throws Throwable
     */
    protected void printMark(HttpServletResponse response, String mark)
        throws Throwable
    {
        logger.info("-------------------- 响应第三方：  RECV_ORD_ID_".concat(mark));
        
        PrintWriter writer = response.getWriter();
        writer.print("RECV_ORD_ID_");
        writer.print(mark);
        writer.flush();
        writer.close();
    }
    
    /**
     * 提示信息
     * <功能详细描述>
     * @param retUrl 
     * @param noticeMessage
     * @param response
     * @throws Throwable
     */
    protected void createNoticeMessagePage(String retUrl, String noticeMessage, HttpServletResponse response)
        throws Throwable
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<script type=\"text/javascript\">");
        builder.append("    function openURL(){");
        builder.append("    alert(\"" + noticeMessage + "\");");
        builder.append("    location.replace(\"");
        builder.append(retUrl);
        builder.append("\"  );");
        builder.append("    }");
        builder.append("</script>");
        builder.append("</head>");
        builder.append("<body onload=\"openURL();\">");
        builder.append("</body>");
        builder.append("</html>");
        response.setContentType("text/html");
        response.setCharacterEncoding(charSet);
        response.getWriter().write(builder.toString());
        response.getWriter().close();
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        sendRedirect(request, response, getController().getViewURI(request, getClass()));
    }
    
    @Override
    protected boolean mustAuthenticated()
    {
        return true;
    }
    
    protected ConfigureProvider getConfigureProvider()
    {
        return getResourceProvider().getResource(ConfigureProvider.class);
    }
}
