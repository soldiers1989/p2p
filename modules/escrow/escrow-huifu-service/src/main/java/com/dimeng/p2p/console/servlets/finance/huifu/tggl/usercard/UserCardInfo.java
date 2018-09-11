package com.dimeng.p2p.console.servlets.finance.huifu.tggl.usercard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.BindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.CardInfoEntity;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserBankCardEntity;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 用户银行卡信息查询
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月29日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUUSERCARDINFO", isMenu = true, name = "银行卡信息查询", moduleId = "P2P_C_HUIFU_USERCARD", order = 2)
public class UserCardInfo extends AbstractDzglServlet
{
    
    private static final long serialVersionUID = -8763942853365053754L;
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        this.processPost(request, response, serviceSession);
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        int id = IntegerParser.parse(request.getParameter("id"));
        String userTag = request.getParameter("userTag");
        UserAcctQueryManage manage = serviceSession.getService(UserAcctQueryManage.class);
        UserBankCardEntity entity = manage.cardInfo(id);
        
        if (null != entity && null != entity.UsrCardInfolist)
        {
            BankCardManage cardManage = serviceSession.getService(BankCardManage.class);
            BindCardEntity cardEntity = null;
            for (CardInfoEntity cardInfo : entity.UsrCardInfolist)
            {
                if (null != cardInfo.ExpressFlag && cardInfo.ExpressFlag.equals("Y"))
                {
                    ChargeEntity chargeEntity = new ChargeEntity();
                    chargeEntity.usrCustId = cardInfo.UsrCustId;
                    chargeEntity.gateBankId = cardInfo.BankId;
                    chargeEntity.cardId = cardInfo.CardId;
                    cardManage.addQuickPayCardId(chargeEntity);
                }
                else
                {
                    cardEntity = new BindCardEntity();
                    cardEntity.usrCustId = cardInfo.UsrCustId;
                    cardEntity.openBankId = cardInfo.BankId;
                    cardEntity.openAcctId = cardInfo.CardId;
                    cardManage.insertT6114(cardEntity);
                }
            }
        }
        
        request.setAttribute("userTag", userTag);
        request.setAttribute("entity", entity);
        forward(request, response, getController().getViewURI(request, getClass()));
    }
}
