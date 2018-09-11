package com.dimeng.p2p.app.servlets.pay;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.http.session.Session;
import com.dimeng.framework.http.session.authentication.AuthenticationException;
import com.dimeng.framework.http.session.authentication.VerifyCodeAuthentication;
import com.dimeng.framework.resource.ResourceRegister;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.account.user.service.entity.Safety;
import com.dimeng.p2p.app.servlets.AbstractSecureServlet;
import com.dimeng.p2p.app.servlets.pay.service.fuyou.service.UserManage;
import com.dimeng.p2p.app.servlets.platinfo.ExceptionCode;
import com.dimeng.p2p.common.RegexUtils;
import com.dimeng.p2p.common.enums.AttestationState;
import com.dimeng.p2p.escrow.fuyou.service.FYSafetyManage;
import com.dimeng.p2p.variables.defines.MessageVariable;
import com.dimeng.util.StringHelper;

/**
 * 
 * 注册第三方用户
 * 
 * @author  zhoulantao
 * @version  [版本号, 2016年1月11日]
 */
public class PayUserRegister extends AbstractSecureServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(final HttpServletRequest request, HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        // 为对接微信端，需要做一步跳转。
        String location = getSiteDomain("/pay/service/fuyou/fyouUserRegister.htm");
        FYSafetyManage safetyManage = serviceSession.getService(FYSafetyManage.class);
        final String phone = getParameter(request, "phone"); // 手机号
        final String realName = getParameter(request, "realName"); //真实姓名
        
        final String idCard = getParameter(request, "idCard"); //身份证号
        
        String verifyCode = getParameter(request, "verifyCode"); //手机验证码
        String checkMsg = escrowGuideCheck(serviceSession, safetyManage, phone, realName, idCard, verifyCode);
        if (!StringHelper.isEmpty(checkMsg))
        {
            setReturnMsg(request, response, ExceptionCode.COM_REGISTER_ERROR, checkMsg);
            return;
        }
        location = location + "?ts=" + phone;
        //保存用户信息
        safetyManage.updateNameSFZRR(realName, idCard, AttestationState.YYZ.name(), T6110_F06.ZRR);
        // 封装返回信息
        setReturnMsg(request, response, ExceptionCode.SUCCESS, "success!", location);
        return;
    }
    
    @Override
    protected void processGet(final HttpServletRequest request, final HttpServletResponse response,
        final ServiceSession serviceSession)
        throws Throwable
    {
        processPost(request, response, serviceSession);
    }
    
    private String escrowGuideCheck(final ServiceSession serviceSession, FYSafetyManage safetyManage, String phone,
        String name, String idcard, String code)
    {
        try
        {
            ConfigureProvider configureProvider =
                ResourceRegister.getResourceProvider(getServletContext()).getResource(ConfigureProvider.class);
            
            if (StringHelper.isEmpty(name))
            {
                return "姓名不能为空!";
            }
            String mtest = "^[\u4e00-\u9fa5]{2,}$";
            name = name.trim();
            if (!name.matches(mtest))
            {
                return "请输入合法的姓名!";
            }
            if (StringHelper.isEmpty(idcard))
            {
                return "身份证号码不能为空!";
            }
            
            idcard = idcard.trim();
            if (!RegexUtils.isValidId(idcard))
            {
                return "无效的身份证号!";
            }
            
            if (name.length() > 20)
            {
                return "姓名最多为20个字符!";
            }
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int born = Integer.parseInt(idcard.substring(6, 10));
            if ((year - born) < 18)
            {
                return "必须年满18周岁!";
            }
            
            // 判断修改实名认证信息时，该身份证是否正在注册第三方账户
            if (safetyManage.isAuthingUpdate())
            {
                return "正在第三方注册账户，不能修改实名认证信息!";
            }
            
            Safety safety = safetyManage.get();
            UserManage userManage = serviceSession.getService(UserManage.class);
            //是否开通富友，开通富友，一般就有实名认证通过
            String isFuyouUser = userManage.selectT6119();
            //本地实名认证开通和富友认证通过，才算实名认证通过, guomianyun by 20170306
            if (!StringHelper.isEmpty(safety.isIdCard) && !StringHelper.isEmpty(isFuyouUser))
            {
                return "您的账号已通过实名认证，请不要重复认证!";
            }
            if (safetyManage.isIdcard(idcard, T6110_F06.ZRR))
            {
                return "该身份证已认证过!";
            }
            
            if (StringHelper.isEmpty(phone))
            {
                return "手机号码错误";
            }
            else if (StringHelper.isEmpty(code))
            {
                return "手机验证码错误";
            }
            else
            {
                //当日该手机与验证码匹配错误次数
                Integer ecount = safetyManage.phoneMatchVerifyCodeErrorCount(phone, 1);
                if (ecount >= Integer.parseInt(configureProvider.getProperty(MessageVariable.PHONE_VERIFYCODE_MAX_ERROR_COUNT)))
                {
                    return "当前手机号码当天匹配验证码错误次数已达上限！";
                }
                String codeType = "P" + "RZKH|" + phone;
                Session session = serviceSession.getSession();
                VerifyCodeAuthentication verfycode = new VerifyCodeAuthentication();
                verfycode.setVerifyCodeType(codeType);
                verfycode.setVerifyCode(code);
                try
                {
                    session.authenticateVerifyCode(verfycode);
                }
                catch (AuthenticationException e)
                {
                    //绑定手机号码，type为1，见send.java line 110
                    safetyManage.insertPhoneMatchVerifyCodeError(phone, 1, code);
                    return "手机验证码错误";
                }
                
                if (safetyManage.checkPhoneIsExist(phone))
                {
                    return "手机号码已存在";
                }
                session.invalidVerifyCode(codeType);
                return "";
            }
        }
        catch (Throwable e)
        {
            logger.error("", e);
            return "参数错误";
        }
    }
}
