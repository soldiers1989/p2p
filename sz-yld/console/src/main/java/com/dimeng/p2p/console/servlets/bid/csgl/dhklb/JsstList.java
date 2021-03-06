package com.dimeng.p2p.console.servlets.bid.csgl.dhklb;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.console.servlets.bid.AbstractBidServlet;
import com.dimeng.p2p.modules.bid.console.service.CollectionManage;
import com.dimeng.p2p.modules.bid.console.service.entity.Near30;
import com.dimeng.p2p.modules.bid.console.service.entity.StayRefundGather;
import com.dimeng.p2p.modules.bid.console.service.query.Near30Query;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.SQLDateParser;

@Right(id = "P2P_C_BUSI_CS_DHKLIST", isMenu = true, name = "待还款列表", moduleId = "P2P_C_BID_CSGL_DHKLB", order = 0)
public class JsstList extends AbstractBidServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        CollectionManage collectionManage = serviceSession.getService(CollectionManage.class);
        StayRefundGather stayRefundGather = collectionManage.findStayRefundGather();
        Near30Query near30Query = new Near30Query()
        {
            
            @Override
            public String getUserName()
            {
                return request.getParameter("userName");
            }
            
            @Override
            public String getLoanRecordTitle()
            {
                return request.getParameter("loanRecordTitle");
            }
            
            @Override
            public Date getCreateTimeStart()
            {
                return SQLDateParser.parse(request.getParameter("createTimeStart"));
            }
            
            @Override
            public Date getCreateTimeEnd()
            {
                return SQLDateParser.parse(request.getParameter("createTimeEnd"));
            }
        };
        PagingResult<Near30> near30s = collectionManage.near30Search(near30Query, new Paging()
        {
            
            @Override
            public int getSize()
            {
                return 10;
            }
            
            @Override
            public int getCurrentPage()
            {
                return IntegerParser.parse(request.getParameter(PAGING_CURRENT));
            }
        });
        Near30 near30SearchAmount = collectionManage.near30SearchAmount(near30Query);
        request.setAttribute("stayRefundGather", stayRefundGather);
        request.setAttribute("near30s", near30s);
        request.setAttribute("near30SearchAmount", near30SearchAmount);
        
        forwardView(request, response, getClass());
    }
    
}
