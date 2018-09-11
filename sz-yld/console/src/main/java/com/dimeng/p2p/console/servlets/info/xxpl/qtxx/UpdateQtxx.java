package com.dimeng.p2p.console.servlets.info.xxpl.qtxx;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.http.upload.FileStore;
import com.dimeng.framework.http.upload.UploadFile;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.S50.entities.T5010;
import com.dimeng.p2p.S50.enums.T5010_F04;
import com.dimeng.p2p.S50.enums.T5011_F02;
import com.dimeng.p2p.common.enums.ArticlePublishStatus;
import com.dimeng.p2p.console.servlets.info.xxpl.AbstractXxplServlet;
import com.dimeng.p2p.modules.base.console.service.ArticleManage;
import com.dimeng.p2p.modules.base.console.service.entity.Article;
import com.dimeng.p2p.modules.base.console.service.entity.ArticleRecord;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

/**
 * 
 * <其他信息>
 * <修改>
 * 
 * @author  God
 * @version  [版本号, 2018年2月6日]
 */
@Right(id = "P2P_C_INFO_XXPL_MENU", isMenu = true, name = "信息披露", moduleId = "P2P_C_INFO_XXPL", order = 0)
public class UpdateQtxx extends AbstractXxplServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        try
        {
            ArticleManage manage = serviceSession.getService(ArticleManage.class);
            Article article = new Article()
            {
                
                @Override
                public String getTitle()
                {
                    return T5011_F02.QTXX.getChineseName();
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
                    return IntegerParser.parse(request.getParameter("sortIndex"));
                }
                
                @Override
                public ArticlePublishStatus getPublishStatus()
                {
                    return ArticlePublishStatus.YFB;
                }
                
                @Override
                public UploadFile getImage()
                    throws Throwable
                {
                    /*Part part = request.getPart("image");
                    if (part == null || part.getContentType() == null
                    		|| part.getSize() == 0) {
                    	return null;
                    }
                    return new PartFile(part);*/
                    return null;
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
            ArticleRecord exist = manage.get(T5011_F02.QTXX);
            T5010 category = new T5010();
            category.F02 = "QTXX";
            category.F03 = request.getParameter("title");
            category.F04 = T5010_F04.parse(request.getParameter("status"));
            manage.updateT5010(category);
            if (exist == null)
            {
                manage.add(T5011_F02.QTXX, article);
            }
            else
            {
                manage.update(exist.id, article);
            }
            prompt(request, response, PromptLevel.INFO, "保存成功");
            processGet(request, response, serviceSession);
        }
        catch (ParameterException | LogicalException e)
        {
            prompt(request, response, PromptLevel.ERROR, e.getMessage());
            processGet(request, response, serviceSession);
        }
        
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        ArticleManage manage = serviceSession.getService(ArticleManage.class);
        String content = manage.getContent(T5011_F02.QTXX);
        request.setAttribute("content", content);
        T5010 t5010 = manage.getArticleTypeByCode("QTXX");
        request.setAttribute("t5010", t5010);
        forwardView(request, response, getClass());
    }
    
}
