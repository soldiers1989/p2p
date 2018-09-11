package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.addproject.AddBidInfoEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;

/**
 * 
 * 标信息录入回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class AddBidInfoRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        BidManage manage = serviceSession.getService(BidManage.class);
        AddBidInfoEntity entity = manage.addBidInfoDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            /*try {
            	getResourceProvider().getResource(TenderCancelExecutor.class)
            	.confirm(Integer.parseInt(entity.proId), null);
            } catch (Exception e) {
            	// TODO: handle exception
            }*/
        }
        printMark(response, entity != null ? entity.proId : request.getParameter("ProId"));
    }
    
}