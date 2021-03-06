package com.dimeng.p2p.console.servlets.statistics.ywtj.ywytj;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S71.entities.T7190;
import com.dimeng.p2p.console.servlets.AbstractBuisnessServlet;
import com.dimeng.p2p.repeater.business.SysBusinessManage;
import com.dimeng.p2p.repeater.business.query.PerformanceQuery;
import com.dimeng.util.io.CVSWriter;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

/**
 * 
 * 导出业务员业绩统计
 * <功能详细描述>
 * 
 * @author  heluzhu
 * @version  [版本号, 2015年12月2日]
 */
@Right(id = "P2P_C_STATISTICS_EXPORTBUSINESS", name = "导出业务员业绩统计", moduleId = "P2P_C_STATISTICS_YWTJ_YWYTJ")
public class ExportBusinessStatistics extends AbstractBuisnessServlet
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
        
        SysBusinessManage sysBusinessManage = serviceSession.getService(SysBusinessManage.class);
        
        response.setHeader("Content-disposition",
            "attachment;filename=" + new Timestamp(System.currentTimeMillis()).getTime() + ".csv");
        response.setContentType("application/csv");
        PerformanceQuery query = new PerformanceQuery()
        {
            
            @Override
            public String getEmployNum()
            {
                return request.getParameter("employNum");
            }
            
            @Override
            public Timestamp getCreateTimeStart()
            {
                return TimestampParser.parse(request.getParameter("createTimeStart"));
            }
            
            @Override
            public Timestamp getCreateTimeEnd()
            {
                return TimestampParser.parse(request.getParameter("createTimeEnd"));
            }
            
            @Override
            public String getName()
            {
                return request.getParameter("name");
            }
            
        };
        Paging paging = new Paging()
        {
            
            @Override
            public int getSize()
            {
                return Integer.MAX_VALUE;
            }
            
            @Override
            public int getCurrentPage()
            {
                return IntegerParser.parse(request.getParameter(PAGING_CURRENT));
            }
        };
        
        PagingResult<T7190> result = sysBusinessManage.serarchTjgl(query, paging);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "GBK")))
        {
            CVSWriter writer = new CVSWriter(out);
            writer.write("序号");
            writer.write("成交时间");
            writer.write("业务员工号");
            writer.write("业务员姓名");
            writer.write("一级用户投资金额(元)");
            writer.write("一级用户借款金额(元)");
            writer.write("一级用户充值金额(元)");
            writer.write("一级用户提现金额(元)");
            writer.write("二级用户投资金额(元)");
            writer.write("二级用户借款金额(元)");
            writer.write("二级用户充值金额(元)");
            writer.write("二级用户提现金额(元)");
            writer.newLine();
            int i = 0;
            if (result != null)
            {
                for (T7190 t7190 : result.getItems())
                {
                    i++;
                    if (t7190.F02 != 0 || t7190.F03 != 0 || t7190.F04 != 0 || t7190.F05 != 0)
                    {
                        writer.write(i);
                        writer.write(t7190.F01);
                        writer.write(t7190.employNum);
                        writer.write(t7190.name);
                        writer.write(t7190.F02);
                        writer.write(t7190.F03);
                        writer.write(t7190.F04);
                        writer.write(t7190.F05);
                        writer.write(t7190.F06);
                        writer.write(t7190.F07);
                        writer.write(t7190.F08);
                        writer.write(t7190.F09);
                        writer.newLine();
                    }
                }
            }
        }
        
    }
}
