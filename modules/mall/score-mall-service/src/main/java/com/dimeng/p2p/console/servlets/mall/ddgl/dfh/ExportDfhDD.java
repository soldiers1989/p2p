package com.dimeng.p2p.console.servlets.mall.ddgl.dfh;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S63.enums.T6359_F08;
import com.dimeng.p2p.console.servlets.AbstractMallServlet;
import com.dimeng.p2p.repeater.score.ScoreOrderManage;
import com.dimeng.p2p.repeater.score.entity.ScoreOrderMainRecord;
import com.dimeng.p2p.repeater.score.query.ScoreOrderQuery;
import com.dimeng.util.parser.IntegerParser;

@Right(id = "P2P_C_MALL_EXPORTDFHDD", name = "导出", moduleId = "P2P_C_MALL_DDGL_DFH", order = 4)
public class ExportDfhDD extends AbstractMallServlet
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
    {}
    
}
