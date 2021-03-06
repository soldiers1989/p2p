package com.dimeng.p2p.console.servlets.info.yygl.gggl;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.http.upload.PartFile;
import com.dimeng.framework.http.upload.UploadFile;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.modules.base.console.service.AdvertisementManage;
import com.dimeng.p2p.modules.base.console.service.entity.AdvertisementType;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

@MultipartConfig
@Right(id = "P2P_C_INFO_YYGL_MENU", isMenu = true, name = "运营管理", moduleId = "P2P_C_INFO_YYGL", order = 0)
public class AddGggl extends AbstractAdvertiseServlet
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
        if (!FormToken.verify(serviceSession.getSession(), request.getParameter(FormToken.parameterName())))
        {
            throw new LogicalException("请不要重复提交请求！");
        }
        try
        {
            AdvertisementManage advertisementManage = serviceSession.getService(AdvertisementManage.class);
            advertisementManage.addForType(new AdvertisementType()
            {
                
                @Override
                public Timestamp getUnshowTime()
                {
                    return TimestampParser.parse(request.getParameter("unshowTime"));
                }
                
                @Override
                public String getURL()
                {
                    return request.getParameter("url");
                }
                
                @Override
                public String getTitle()
                {
                    return request.getParameter("title");
                }
                
                @Override
                public int getSortIndex()
                {
                    return IntegerParser.parse(request.getParameter("sortIndex"));
                }
                
                @Override
                public Timestamp getShowTime()
                {
                    return TimestampParser.parse(request.getParameter("showTime"));
                }
                
                @Override
                public UploadFile getImage()
                    throws IOException, ServletException
                {
                    Part part = request.getPart("image");
                    if (part == null || part.getContentType() == null || part.getSize() == 0)
                    {
                        return null;
                    }
                    return new PartFile(part);
                }
                
                @Override
                public String getAdvType()
                {
                    return request.getParameter("advType");
                }
            });
            sendRedirect(request, response, getController().getURI(request, SearchGggl.class));
        }
        catch (ParameterException | LogicalException e)
        {
            prompt(request, response, PromptLevel.ERROR, e.getMessage());
            processGet(request, response, serviceSession);
        }
    }
    
}
