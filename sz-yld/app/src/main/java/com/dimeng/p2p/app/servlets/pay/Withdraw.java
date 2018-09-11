package com.dimeng.p2p.app.servlets.pay;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.account.user.service.IndexManage;
import com.dimeng.p2p.account.user.service.TxManage;
import com.dimeng.p2p.account.user.service.UserInfoManage;
import com.dimeng.p2p.account.user.service.entity.UserBaseInfo;
import com.dimeng.p2p.app.servlets.AbstractSecureServlet;
import com.dimeng.p2p.app.servlets.platinfo.ExceptionCode;
import com.dimeng.p2p.escrow.fuyou.service.BankManage;
import com.dimeng.p2p.escrow.fuyou.service.WithDrawManage;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 提现到银行卡 
 * @author  zhongsai
 * @version  [V7.0, 2018年4月18日]
 */
public class Withdraw extends AbstractSecureServlet
{
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void processPost(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        // 判断用户是否被拉黑或者锁定
        UserInfoManage userInfoMananage = serviceSession.getService(UserInfoManage.class);
        T6110 t6110 = userInfoMananage.getUserInfo(serviceSession.getSession().getAccountId());
        
        // 用户状态非法
        if (t6110.F07 == T6110_F07.HMD || t6110.F07 == T6110_F07.SD)
        {
            setReturnMsg(request, response, ExceptionCode.ACCOUNT_IS_BLACK, "用户已被拉黑或锁定，不能进行提现操作!");
            return;
        }
        
        int cardId = IntegerParser.parse(getParameter(request, "cardId"));
        BigDecimal amount = BigDecimalParser.parse(getParameter(request, "amount"));
        //提现方式（T+0; T+1）
        final BigDecimal txfs = BigDecimalParser.parse(getParameter(request, "txfs"));
        
        BankManage bankManage = serviceSession.getService(BankManage.class);
        boolean isResult = bankManage.selectT6114Ext();
        if (isResult)
        {
            // 封装返回信息
            setReturnMsg(request, response, ExceptionCode.ERROR, "请先查询银行卡更换进度！");
            return;
        }
        
        // 内扣时需要判断手续费
        String kcfs = getConfigureProvider().getProperty(SystemVariable.TXSXF_KCFS);
        String withdrawPsd = getParameter(request, "withdrawPsd");
        BigDecimal poundage = null;
        
        if (cardId == 0)
        {
            // 封装返回信息
            setReturnMsg(request, response, ExceptionCode.BANK_CARD_NO_ERROR, "银行卡错误！");
            return;
        }
        if (amount.doubleValue() <= 0)
        {
            // 封装返回信息
            setReturnMsg(request, response, ExceptionCode.AMOUNT_ERROR, "金额输入错误！");
            return;
        }
        
        IndexManage indexManage = serviceSession.getService(IndexManage.class);
        UserBaseInfo userBaseInfo = indexManage.getUserBaseInfo();
        if (userBaseInfo != null && userBaseInfo.balance.compareTo(amount) == -1)
        {
            // 账户金额不足
            setReturnMsg(request, response, ExceptionCode.AMOUNT_ERROR_NO_EMPTY, "账户金额不足！");
            return;
        }
        
        UserInfoManage userInfoManage = serviceSession.getService(UserInfoManage.class);
        String isYuqi = userInfoManage.isYuqi();
        if (isYuqi.equals("Y"))
        {
            // 逾期未还
            setReturnMsg(request, response, ExceptionCode.YU_QI_EXCEPTION, "您有逾期未还的贷款，请先还借款再操作！");
            return;
        }
        
        boolean txkcfs = Boolean.parseBoolean(kcfs);
        String pundageWay = getConfigureProvider().getProperty(SystemVariable.WITHDRAW_POUNDAGE_WAY);
        if ("BL".equals(pundageWay))
        {
            // 按比例计算
            String _proportion = getConfigureProvider().getProperty(SystemVariable.WITHDRAW_POUNDAGE_PROPORTION);
            if (StringHelper.isEmpty(_proportion) || _proportion.contains("-"))
            {
                throw new LogicalException("系统繁忙，请稍后再试！");
            }
            BigDecimal proportion = new BigDecimal(_proportion);
            poundage = amount.multiply(proportion).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        else
        {
            // 按额度计算[1-50000), [50000, ]
            if (amount.compareTo(new BigDecimal(50000)) < 0)
            {
                poundage = new BigDecimal(getConfigureProvider().getProperty(SystemVariable.WITHDRAW_POUNDAGE_1_5));
            }
            else
            {
                poundage = new BigDecimal(getConfigureProvider().getProperty(SystemVariable.WITHDRAW_POUNDAGE_5_20));
            }
        }
        
        if (txkcfs && amount.compareTo(poundage) <= 0)
        {
            // 封装返回信息
            setReturnMsg(request, response, ExceptionCode.AMOUNT_ERROR, "提现金额不能少于手续费");
            return;
        }
        
        // 确认是否托管
        boolean tg = true;
        
        if (tg)
        {
            // 判断是否注册第三方
            WithDrawManage withdrawService = serviceSession.getService(WithDrawManage.class);
            // 查询用户的第三方托管账号
            final String login_id = withdrawService.selectT6119();
            if (login_id == null || StringHelper.isEmpty(login_id))
            {
                // 账户金额不足
                setReturnMsg(request, response, ExceptionCode.NO_REGISTER_OTHER_PAY, "用户未注册第三方支付！");
                return;
            }
            
            // 跳转到第三方提现接口
            String location =
                getSiteDomain("/pay/service/fuyou/fyouWithdraw.htm?amount=" + amount + "&cardId=" + cardId + "&txfs="
                    + txfs);
            
            // 跳转到提现接口
            setReturnMsg(request, response, ExceptionCode.SUCCESS, "success!", location);
            return;
        }
        else
        {
            TxManage manage = serviceSession.getService(TxManage.class);
            manage.withdraw(amount, withdrawPsd, cardId, T6101_F03.WLZH, poundage, txkcfs);
            
            //提现金额小于提现审核所限制金额或所限金额为0就自动提现，不需人工审核
            String desc = "提现成功！  ";
            BigDecimal money = new BigDecimal(getConfigureProvider().format(SystemVariable.WITHDRAW_LIMIT_FUNDS));
            if (amount.doubleValue() > money.doubleValue() || money.doubleValue() <= 0)
            {
                desc = "提现申请成功，请等待工作人员审核！";
            }

            // 封装返回信息
            setReturnMsg(request, response, ExceptionCode.SUCCESS, desc);
            return;
        }
    }
}
