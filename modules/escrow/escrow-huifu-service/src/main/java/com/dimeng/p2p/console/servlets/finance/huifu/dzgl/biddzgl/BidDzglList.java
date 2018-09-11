/*
 * 文 件 名:  ChargedzglList.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月19日
 */
package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.biddzgl;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.OrderType;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.entity.query.ExtOrderQuery;
import com.dimeng.p2p.escrow.huifu.service.QueryManage;
import com.dimeng.p2p.modules.account.console.service.entity.Order;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

/**
 * 投标对账-投标记录列表
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUBIDLIST", isMenu = true, name = "投标记录列表", moduleId = "P2P_C_HUIFU_TBDZGL", order = 0)
public class BidDzglList extends AbstractDzglServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 8959552578445058112L;
    
    @Override
    protected void processGet(final HttpServletRequest request, HttpServletResponse response,
        ServiceSession serviceSession)
        throws Throwable
    {
        QueryManage orderManager = serviceSession.getService(QueryManage.class);
        PagingResult<Order> result = orderManager.search(new ExtOrderQuery()
        {
            @Override
            public Timestamp getEndStart()
            {
                return TimestampParser.parse(request.getParameter("endDate"));
            }
            
            @Override
            public Timestamp getCreateStart()
            {
                return TimestampParser.parse(request.getParameter("startDate"));
            }
            
            @Override
            public int getType()
            {
                return OrderType.BID.orderType();
            }
            
            @Override
            public String getUserName()
            {
                return request.getParameter("txuserName");
            }
            
            @Override
            public String getOrderId()
            {
                return request.getParameter("orderId");
            }
            
            @Override
            public T6501_F03 getOrderStatus()
            {
                return T6501_F03.parse(request.getParameter("txStatus"));
            }
        }, new Paging()
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
        request.setAttribute("pagingResult", result);
        forward(request, response, getController().getViewURI(request, getClass()));
        
    }
    
    @Override
    protected void processPost(final HttpServletRequest request, HttpServletResponse response,
        ServiceSession serviceSession)
        throws Throwable
    {
        processGet(request, response, serviceSession);
    }
    
}
