package com.dimeng.p2p.pay.servlets.huifu.ret;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransferEntity;
import com.dimeng.p2p.escrow.huifu.service.DonationManage;
import com.dimeng.p2p.escrow.huifu.service.TransferManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 公益标捐款失败,资金返回捐款用户回调地址
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月22日]
 */
public class DonationFailRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 公益标捐款失败,资金返回捐款用户回调开始 ----------");
        TransferManage transferManage = serviceSession.getService(TransferManage.class);
        TransferEntity entity = transferManage.transferReturnDecoder(request);
        if (entity != null)
        {
            if (HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
            {
                try
                {
                    DonationManage manage = serviceSession.getService(DonationManage.class);
                    manage.donationFundDeal(IntegerParser.parse(entity.ordId));
                }
                catch (Exception e)
                {
                    logger.error(String.format("公益标捐款失败,资金返回捐款用户，订单%s异常", entity.ordId), e);
                }
            }
            else
            {
                logger.info("公益标捐款失败，返回码：" + entity.respCode + " 返回描述：" + entity.respDesc);
            }
        }
        else
        {
            logger.info("公益标捐款失败，验签失败！");
        }
        //响应第三方
        printMark(response, entity != null ? entity.ordId : request.getParameter("OrdId"));
    }
}
