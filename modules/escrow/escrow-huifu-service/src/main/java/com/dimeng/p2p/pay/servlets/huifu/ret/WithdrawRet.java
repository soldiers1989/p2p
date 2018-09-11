package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawEntity;
import com.dimeng.p2p.escrow.huifu.service.WithdrawManage;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 提现申请第三方返回后台
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class WithdrawRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 提现申请后台回调开始 ----------");
        WithdrawManage manage = serviceSession.getService(WithdrawManage.class);
        WithdrawEntity entity = manage.withdrawReturnDecoder(request);
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
        
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            int orderId = IntegerParser.parse(entity.ordId);
            try
            {
                //更新手续费
                BigDecimal servFee = BigDecimal.ZERO;
                if (!StringHelper.isEmpty(entity.servFee))
                {
                    //平台收取用户的提现服务费金额
                    servFee = new BigDecimal(entity.servFee);
                }
                //第三方收取的提现手续费金额
                BigDecimal feeAmt = new BigDecimal(entity.feeAmt);
                //实际到账金额
                BigDecimal realTransAmt = new BigDecimal(entity.realTransAmt);
                
                manage.updateT6503(orderId, feeAmt, servFee, realTransAmt);
                //添加提现请求
                manage.apply(orderId);
            }
            catch (Exception e)
            {
                logger.error("提现申请后台回调" + e);
                //manage.withdrawFailUnFreze(orderId);
            }
        }
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        logger.error(throwable, throwable);
    }
    
}
