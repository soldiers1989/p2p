package com.dimeng.p2p.console.servlets.info.zxdt.mtbd;

import java.sql.Timestamp;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.http.upload.FileStore;
import com.dimeng.framework.http.upload.PartFile;
import com.dimeng.framework.http.upload.UploadFile;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.S50.enums.T5011_F02;
import com.dimeng.p2p.common.FormToken;
import com.dimeng.p2p.common.enums.ArticlePublishStatus;
import com.dimeng.p2p.console.servlets.info.AbstractInformationServlet;
import com.dimeng.p2p.modules.base.console.service.ArticleManage;
import com.dimeng.p2p.modules.base.console.service.entity.Article;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

@MultipartConfig
@Right(id = "P2P_C_INFO_ZXDT_MENU", name = "最新动态",moduleId="P2P_C_INFO_ZXDT",order=0)
public class AddMtbd extends AbstractInformationServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        try
        {
            if (!FormToken.verify(serviceSession.getSession(), request.getParameter(FormToken.parameterName())))
            {
                throw new LogicalException("请不要重复提交请求！");
            }
            ArticleManage articleManage = serviceSession.getService(ArticleManage.class);
            Article article = new Article()
            {
                @Override
                public String getTitle()
                {
                    return request.getParameter("title");
                }
                
                @Override
                public String getSummary()
                {
                    return request.getParameter("summary");
                }
                
                @Override
                public String getSource()
                {
                    return request.getParameter("source");
                }
                
                @Override
                public int getSortIndex()
                {
                    String index = request.getParameter("sortIndex");
                    int sortIndex = 1;
                    if (!StringHelper.isEmpty(index))
                    {
                        sortIndex = IntegerParser.parse(index);
                    }
                    return sortIndex;
                }
                
                @Override
                public ArticlePublishStatus getPublishStatus()
                {
                    return EnumParser.parse(ArticlePublishStatus.class, request.getParameter("status"));
                }
                
                @Override
                public UploadFile getImage()
                    throws Throwable
                {
                    Part part = request.getPart("image");
                    if (part == null || part.getContentType() == null || part.getSize() == 0)
                    {
                        throw new ParameterException("封面图片不能为空.");
                    }
                    return new PartFile(part);
                }
                
                @Override
                public String getContent()
                {
                    return getResourceProvider().getResource(FileStore.class).encode(request.getParameter("content"));
                }
                
                @Override
                public Timestamp publishTime()
                {
                    return TimestampParser.parse(request.getParameter("publishTime"));
                }
            };
            articleManage.add(T5011_F02.MTBD, article);
            sendRedirect(request, response, getController().getURI(request, SearchMtbd.class));
        }
        catch (ParameterException | LogicalException e)
        {
            prompt(request, response, PromptLevel.ERROR, e.getMessage());
            processGet(request, response, serviceSession);
        }
    }
    
}
