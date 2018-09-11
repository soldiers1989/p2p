package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S62.enums.T6230_F20;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.loans.LoansEntity;
import com.dimeng.p2p.escrow.huifu.service.BidManage;
import com.dimeng.p2p.escrow.huifu.service.UnFreezeManage;
import com.dimeng.p2p.escrow.huifu.threads.ContractPreservationFormationThread;
import com.dimeng.p2p.order.TenderConfirmExecutor;
import com.dimeng.p2p.repeater.preservation.ContractPreservationManage;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 放款回调处理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class TenderConfirmRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 放款回调开始 -----------");
        BidManage bidManage = serviceSession.getService(BidManage.class);
        LoansEntity entity = bidManage.loans(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                synchronized (TenderConfirmRet.class)
                {
                    TenderConfirmExecutor executor = getResourceProvider().getResource(TenderConfirmExecutor.class);
                    
                    executor.confirm(IntegerParser.parse(entity.ordId), null);
                    
                    UnFreezeManage manage = serviceSession.getService(UnFreezeManage.class);
                    manage.updateTrxId(entity.freezeTxId, IntegerParser.parse(entity.unFreezeOrdId));
                }
                
                ConfigureProvider configureProvider = getResourceProvider().getResource(ConfigureProvider.class);
                //查询标ID以及标状态
                Map<String, String> params = bidManage.queryBidStatus(IntegerParser.parse(entity.ordId));
                
                if (T6230_F20.HKZ.name().equals(params.get("status")))
                {
                    /*放款成功后，开启一个线程去创建合同保全pdf文件*/
                    if (Boolean.parseBoolean(configureProvider.getProperty(SystemVariable.IS_SAVE_BID_CONTRACT)))
                    {
                        ContractPreservationManage contractPreservationManage =
                            serviceSession.getService(ContractPreservationManage.class);
                        contractPreservationManage.insertT6271(IntegerParser.parse(params.get("bidId")));
                        Thread thread =
                            new Thread(
                                new ContractPreservationFormationThread(IntegerParser.parse(params.get("bidId"))));
                        thread.setName("生成借款合同线程类!");
                        thread.start();
                    }
                }
                
            }
            catch (Exception e)
            {
                logger.error(String.format("还款订单%s异常", entity.ordId), e);
            }
        }
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
}
