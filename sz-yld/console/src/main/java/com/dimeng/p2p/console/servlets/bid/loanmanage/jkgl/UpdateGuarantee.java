package com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.entities.T6236;
import com.dimeng.p2p.S62.entities.T6237;
import com.dimeng.p2p.S62.enums.T6230_F20;
import com.dimeng.p2p.console.servlets.bid.AbstractBidServlet;
import com.dimeng.p2p.modules.account.console.service.EnterpriseManage;
import com.dimeng.p2p.modules.bid.console.service.BidManage;
import com.dimeng.p2p.modules.bid.console.service.entity.Dbxx;
import com.dimeng.util.parser.IntegerParser;

@Right(id = "P2P_C_LOAN_UPDATEPROJECT", name = "修改", moduleId = "P2P_C_BID_JKGL_LOANMANAGE", order = 3)
public class UpdateGuarantee extends AbstractBidServlet
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -268955794485557959L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        EnterpriseManage enterpriseManage = serviceSession.getService(EnterpriseManage.class);
        int id = IntegerParser.parse(request.getParameter("id"));
        int loanId = IntegerParser.parse(request.getParameter("loanId"));
        BidManage bidManage = serviceSession.getService(BidManage.class);
        Dbxx t6236 = bidManage.getBidDb(id);
        T6237 t6237 = bidManage.getBidFx(loanId);
        String des = enterpriseManage.getJgDes(t6236.F03);
        request.setAttribute("t6236", t6236);
        request.setAttribute("t6237", t6237);
        request.setAttribute("des", des);
        forwardView(request, response, getClass());
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        int id = IntegerParser.parse(request.getParameter("id"));
        int loanId = IntegerParser.parse(request.getParameter("loanId"));
        int userId = IntegerParser.parse(request.getParameter("userId"));
        BidManage bidManage = serviceSession.getService(BidManage.class);
        T6230 t6230 = bidManage.get(loanId);
        if (t6230 != null && t6230.F20 != T6230_F20.SQZ)
        {
            prompt(request, response, PromptLevel.ERROR, "不是申请中状态");
            sendRedirect(request, response, getController().getURI(request, LoanList.class));
            return;
        }
        T6236 t6236 = new T6236();
        t6236.parse(request);
        t6236.F01 = id;
        t6236.F02 = loanId;
        t6236.F05 = request.getParameter("dbzz");
        bidManage.updateGuarantee(t6236);
        T6237 t6237 = new T6237();
        t6237.F01 = loanId;
        t6237.F02 = request.getParameter("dbzz");
        //t6237.F03 = request.getParameter("fdbqk");
        t6237.F03 = "";
        bidManage.addFx(t6237);
        sendRedirect(request, response, getController().getURI(request, AddGuaranteeXq.class) + "?loanId=" + loanId
            + "&userId=" + userId);
    }
}
