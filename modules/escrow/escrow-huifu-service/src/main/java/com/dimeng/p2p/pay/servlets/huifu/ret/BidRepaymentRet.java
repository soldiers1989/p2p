package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.payment.PaymentEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.util.parser.IntegerParser;

public class BidRepaymentRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        BidManage bidManage = serviceSession.getService(BidManage.class);
        PaymentEntity entity = bidManage.payment(request);
        if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            BidManage manage = serviceSession.getService(BidManage.class);
            manage.bgRepaymented(IntegerParser.parse(entity.ordId));
        }
        printMark(response, entity.ordId);
    }
    
}
