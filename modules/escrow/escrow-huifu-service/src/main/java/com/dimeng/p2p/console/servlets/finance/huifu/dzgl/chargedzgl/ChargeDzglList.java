/*
 * 文 件 名:  ChargedzglList.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年7月19日
 */
package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.chargedzgl;

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
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzCond;
import com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzEntity;
import com.dimeng.p2p.escrow.huifu.service.ChargeManage;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

/**
 * 充值对账-充值记录列表
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUCHARGELIST", isMenu = true, name = "充值记录列表", moduleId = "P2P_C_HUIFU_CZDZGL", order = 0)
public class ChargeDzglList extends AbstractDzglServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(final HttpServletRequest request, HttpServletResponse response,
        ServiceSession serviceSession)
        throws Throwable
    {
        ChargeManage manage = serviceSession.getService(ChargeManage.class);
        PagingResult<CzdzEntity> result = manage.search(new CzdzCond()
        {
            
            @Override
            public String tradingType()
            {
                return OrderType.CHARGE.toString();
            }
            
            @Override
            public String userName()
            {
                return request.getParameter("userName");
            }
            
            @Override
            public Timestamp getStartExpireDatetime()
            {
                return TimestampParser.parse(request.getParameter("startDatetime"));
            }
            
            @Override
            public Timestamp getEndExpireDatetime()
            {
                return TimestampParser.parse(request.getParameter("endDatetime"));
            }
            
            @Override
            public String f01()
            {
                return request.getParameter("OrdId");
            }
            
            @Override
            public String f10()
            {
                return request.getParameter("TrxId");
            }
            
            @Override
            public T6501_F03 getOrderStatus()
            {
                return T6501_F03.parse(request.getParameter("status"));
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
        request.setAttribute("result", result);
        request.setAttribute("userName", request.getParameter("userName"));
        request.setAttribute("TrxId", request.getParameter("TrxId"));
        request.setAttribute("OrdId", request.getParameter("OrdId"));
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
