package com.dimeng.p2p.console.servlets.mall.jfgl.gain;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S61.entities.T6106;
import com.dimeng.p2p.console.servlets.AbstractMallServlet;
import com.dimeng.p2p.repeater.score.UserScoreManage;
import com.dimeng.p2p.repeater.score.entity.UserScoreExt;
import com.dimeng.util.parser.IntegerParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 积分获取记录列表
 *
 * @author pengwei
 * @version [版本号, 2015/12/15]
 */
@Right(id = "P2P_C_MALL_JFHQJL", isMenu = true, name = "积分获取记录", moduleId = "P2P_C_MALL_JFGL_JFHQJL", order = 0)
public class ScoreGetList extends AbstractMallServlet
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