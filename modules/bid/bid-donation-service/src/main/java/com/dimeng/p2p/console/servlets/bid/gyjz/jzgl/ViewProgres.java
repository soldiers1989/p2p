/*
 * 文 件 名:  ViewProgres.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  guomianyun
 * 修改时间:  2015年3月10日
 */
package com.dimeng.p2p.console.servlets.bid.gyjz.jzgl;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S62.enums.T6242_F11;
import com.dimeng.p2p.AbstractDonationServlet;
import com.dimeng.p2p.repeater.donation.GyLoanManage;
import com.dimeng.p2p.repeater.donation.GyLoanProgresManage;
import com.dimeng.p2p.repeater.donation.entity.BidProgres;
import com.dimeng.p2p.repeater.donation.entity.GyLoan;
import com.dimeng.p2p.repeater.donation.query.ProgresQuery;
import com.dimeng.util.parser.DateParser;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <公益标进展管理列表>
 * <功能详细描述>
 * 
 * @author  guomianyun
 * @version  [版本号, 2015年3月10日]
 */
@Right(id = "P2P_C_BID_VIEW_PROGRES",  name = "进展管理[按钮]",moduleId="P2P_C_BID_GYJZ_JZGL",order=2)
public class ViewProgres extends AbstractDonationServlet
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processGet(HttpServletRequest request,
            HttpServletResponse response, ServiceSession serviceSession)
            throws Throwable {
        processPost(request, response, serviceSession);
    }

    @Override
    protected void processPost(final HttpServletRequest request,
            final HttpServletResponse response,
            final ServiceSession serviceSession) throws Throwable {
       
        GyLoanProgresManage bidManage = serviceSession.getService(GyLoanProgresManage.class);
       GyLoanManage gyLoanManage = serviceSession.getService(GyLoanManage.class);
       int loanId = IntegerParser.parse(request.getParameter("loanId"));
       //int userId = IntegerParser.parse(request.getParameter("userId"));
       
       try{
       GyLoan gyLoan = gyLoanManage.getInfo(loanId);
       
        PagingResult<BidProgres> loanProgreList = bidManage.search(
                new ProgresQuery() {
                    //创建用户
                    @Override
                    public String getSysName() {
                        return request.getParameter("userName");
                    }
                       //编号
                    @Override
                    public String getBidNo() {
                        return request.getParameter("loanNo");
                    }

                    //状态
                    @Override
                    public T6242_F11 getStatus() {
                        return EnumParser.parse(T6242_F11.class,
                                request.getParameter("loanIntentionState"));
                    }
                    //创建时间
                    @Override
                    public Timestamp getCreateTimeStart() {
                        Date date = DateParser.parse(request
                                .getParameter("createTimeStart"));
                        return date == null ? null : new Timestamp(date
                                .getTime());
                    }

                    @Override
                    public Timestamp getCreateTimeEnd() {
                        Date date = DateParser.parse(request
                                .getParameter("createTimeEnd"));
                        return date == null ? null : new Timestamp(date
                                .getTime());
                    }
                    //标ID
                    @Override
                    public int getBidId()
                    {
                        return IntegerParser.parse(request.getParameter("loanId"));
                    }
                    //完成时间
                    @Override
                    public Timestamp getComTimeStart()
                    {
                        Date date = DateParser.parse(request
                            .getParameter("comTimeStart"));
                        return date == null ? null : new Timestamp(date
                            .getTime());
                    }

                    @Override
                    public Timestamp getComTimeEnd()
                    {
                        Date date = DateParser.parse(request
                            .getParameter("comTimeEnd"));
                        return date == null ? null : new Timestamp(date
                            .getTime());
                    }
                    //公益方
                    @Override
                    public String getGyName()
                    {
                        return request.getParameter("gyName");
                    }
                    //标题
                    @Override
                    public String getLoanTitle()
                    {
                        return request.getParameter("gyTitle");
                    }
                   
                }, new Paging() {

                    @Override
                    public int getSize() {
                        return 10;
                    }

                    @Override
                    public int getCurrentPage() {
                        return IntegerParser.parse(request
                                .getParameter(PAGING_CURRENT));
                    }
                });

        request.setAttribute("loanProgreList", loanProgreList);
        
        request.setAttribute("gyLoan", gyLoan);

        forwardView(request, response, getClass());
       }catch(Throwable throwable)
       {
           logger.error(throwable, throwable);
           if (throwable instanceof ParameterException
                   || throwable instanceof LogicalException) {
               getController().prompt(request, response, PromptLevel.WARRING,
                       throwable.getMessage());
               //processGet(request, response, serviceSession);
           } else {
               super.onThrowable(request, response, throwable);
           }
       }
    }
    
    
}
