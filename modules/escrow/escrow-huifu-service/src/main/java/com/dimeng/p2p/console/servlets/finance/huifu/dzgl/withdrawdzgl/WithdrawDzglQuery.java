package com.dimeng.p2p.console.servlets.finance.huifu.dzgl.withdrawdzgl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F11;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.cond.QueryCond;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawItem;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.WithdrawQueryManage;
import com.dimeng.p2p.order.WithdrawExecutor;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提现对账处理类
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月20日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUWITHDRAWQUERY", isMenu = true, name = "去对账", moduleId = "P2P_C_HUIFU_TXDZGL", order = 1)
public class WithdrawDzglQuery extends AbstractDzglServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 838756247755348531L;
    
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
        BidManage bidManage = serviceSession.getService(BidManage.class);
        WithdrawQueryManage manage = serviceSession.getService(WithdrawQueryManage.class);
        final String ordId = request.getParameter("ordId");
        
        final T6501 t6501 = bidManage.selectT6501(Integer.parseInt(ordId));
        //订单已经是成功状态，无需对账
        if (T6501_F03.CG == t6501.F03)
        {
            processRequest(request, response, "OK订单已经是成功状态，无需对账！");
            return;
        }
        
        final int pageNum = IntegerParser.parse(request.getParameter(PAGING_CURRENT));
        PagingResult<WithdrawItem> pagingResult = manage.query(new QueryCond()
        {
            
            @Override
            public String pageSize()
            {
                return String.valueOf(1000);
            }
            
            @Override
            public String pageNum()
            {
                return pageNum > 0 ? String.valueOf(pageNum) : "1";
            }
            
            @Override
            public String beginDate()
            {
                return DateParser.format(t6501.F04, "yyyyMMdd");
            }
            
            //结束时间 - 创建时间 加一天
            @Override
            public String endDate()
            {
                return DateParser.format(DateUtils.addDays(t6501.F04, 1), "yyyyMMdd");
            }
        });
        
        WithdrawItem[] wdItems = pagingResult.getItems();
        //校验回调回来的第三方数据中是否包含了此订单
        if (wdItems != null)
        {
            for (WithdrawItem wdItem : wdItems)
            {
                if (ordId.equals(wdItem.OrdId))
                {
                    //交易状态 S-- 成功， F-- 失败， I-- 初始， H-- 经办， R--拒绝
                    if (("S".equals(wdItem.TransStat) || "P".equals(wdItem.TransStat)) && T6501_F11.F == t6501.F11)
                    {
                        bidManage.updateT6501(IntegerParser.parse(ordId), T6501_F03.DQR);
                        WithdrawExecutor executor = getResourceProvider().getResource(WithdrawExecutor.class);
                        executor.confirm(IntegerParser.parse(ordId), null);
                        processRequest(request, response, "OK对账成功！");
                        return;
                    }
                    // 经办
                    else if ("H".equals(wdItem.TransStat))
                    {
                        processRequest(request, response, "该订单平台尚未审核放款！");
                        return;
                    }// 拒绝
                    else if ("R".equals(wdItem.TransStat))
                    {
                        processRequest(request, response, "该订单平台已经拒绝放款！");
                        return;
                    }
                    else
                    {
                        processRequest(request, response, "交易失败！不能对账！");
                        return;
                    }
                }
            }
        }
        
        bidManage.updateOrderDZStatus(IntegerParser.parse(ordId), T6501_F03.SB);
        processRequest(request, response, "OK对账成功，第三方无此订单数据！");
        logger.info("--------- 取现对账结束 -----------");
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        logger.error(throwable);
        if (throwable instanceof LogicalException)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("<html>");
            builder.append("<head>");
            builder.append("<script type=\"text/javascript\">");
            builder.append("    alert(\"" + throwable.getMessage() + "\");");
            builder.append("    window.close();");
            builder.append("</script>");
            builder.append("</head>");
            builder.append("<body>");
            builder.append("</body>");
            builder.append("</html>");
            PrintWriter writer = response.getWriter();
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            writer.print(builder.toString());
            writer.flush();
            writer.close();
        }
        if (throwable instanceof SQLException)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
