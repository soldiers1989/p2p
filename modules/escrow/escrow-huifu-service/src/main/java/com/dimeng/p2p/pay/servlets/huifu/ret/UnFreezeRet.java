package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.freeze.UnFreezeEntity;
import com.dimeng.p2p.escrow.huifu.service.UnFreezeManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 资金解冻回调开始 
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class UnFreezeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("---------- 资金解冻回调开始  ---------");
        UnFreezeManage manage = serviceSession.getService(UnFreezeManage.class);
        UnFreezeEntity entity = manage.unFreezeReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            //更新解冻订单状态、流水号
            manage.updateTrxId(entity.trxId, IntegerParser.parse(entity.ordId));
        }
        
        printMark(response, entity.ordId);
    }
    
}
