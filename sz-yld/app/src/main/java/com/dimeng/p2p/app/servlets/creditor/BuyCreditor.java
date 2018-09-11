package com.dimeng.p2p.app.servlets.creditor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.account.user.service.SafetyManage;
import com.dimeng.p2p.account.user.service.UserInfoManage;
import com.dimeng.p2p.account.user.service.entity.Safety;
import com.dimeng.p2p.app.servlets.AbstractSecureServlet;
import com.dimeng.p2p.app.servlets.platinfo.ExceptionCode;
import com.dimeng.p2p.variables.defines.pays.PayVariavle;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 购买债券
 * 
 * @author  zhoulantao
 * @version  [版本号, 2015年12月10日]
 */
public class BuyCreditor extends AbstractSecureServlet
{
    
    private static final long serialVersionUID = 904758214711922809L;
    
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
            setReturnMsg(request, response, ExceptionCode.ACCOUNT_IS_BLACK, "用户已被拉黑或锁定，不能进行购买债权操作!");
            return;
        }
        
        // 债权ID
        int zcbId = IntegerParser.parse(getParameter(request, "creditorId"));
        
        UserInfoManage userInfoManage = serviceSession.getService(UserInfoManage.class);
        String isYuqi = userInfoManage.isYuqi();
        if (isYuqi.equals("Y"))
        {
            setReturnMsg(request, response, ExceptionCode.YU_QI_EXCEPTION, "您有逾期未还的贷款，请先还借款再操作！");
            return;
        }
        
        final ConfigureProvider configureProvider = getResourceProvider().getResource(ConfigureProvider.class);
        boolean isOpenWithPsd = BooleanParser.parse(configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD));
        String tranPwd = getParameter(request, "tranPwd");
        if (isOpenWithPsd)
        {
            tranPwd = UnixCrypt.crypt(tranPwd, DigestUtils.sha256Hex(tranPwd));
            if (StringHelper.isEmpty(tranPwd))
            {
                setReturnMsg(request, response, ExceptionCode.TRANPASSWORD_ERROR, "输入正确的交易密码！");
                return;
            }
            SafetyManage safetyManage = serviceSession.getService(SafetyManage.class);
            Safety sa = safetyManage.get();
            if (!tranPwd.equals(sa.txpassword))
            {
                setReturnMsg(request, response, ExceptionCode.TRANPASSWORD_ERROR, "输入正确的交易密码！");
                return;
            }
        }
        String location = getSiteDomain("/pay/service/fuyou/zqzr.htm?zqzrId=" + zcbId);
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", location);
        
        setReturnMsg(request, response, ExceptionCode.SUCCESS, "success!", map);
        return;
    }
}
