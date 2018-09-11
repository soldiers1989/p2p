package com.dimeng.p2p.pay.servlets.fuyou.ret;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.session.authentication.AuthenticationException;
import com.dimeng.framework.resource.PromptLevel;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.AbstractFuyouServlet;
import com.dimeng.p2p.S63.enums.T6340_F03;
import com.dimeng.p2p.S63.enums.T6340_F04;
import com.dimeng.p2p.escrow.fuyou.entity.UserQueryResponseEntity;
import com.dimeng.p2p.escrow.fuyou.entity.enums.FuyouRespCode;
import com.dimeng.p2p.escrow.fuyou.face.UserinfoQuery;
import com.dimeng.p2p.escrow.fuyou.service.ComManage;
import com.dimeng.p2p.escrow.fuyou.util.BackCodeInfo;
import com.dimeng.p2p.escrow.fuyou.variables.FuyouVariable;
import com.dimeng.p2p.service.ActivityCommon;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.parser.IntegerParser;
import com.google.gson.Gson;

/**
 * 
 * 富友，企业注册商户返回地址
 * 
 * @author  heshiping
 * @version  [版本号, 2015年11月20日]
 */
public class ComRegisterRet extends AbstractFuyouServlet
{
    
    private static final long serialVersionUID = 2805987722692987500L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("COM-富友-注册时返页面返回——IP:" + request.getRemoteAddr());
        response.setContentType("text/html");
        response.setCharacterEncoding(charSet);
        ComManage manage = serviceSession.getService(ComManage.class);
        Gson gs = new Gson();
        String jsonData = gs.toJson(reversRequest(request));
        logger.info("处理注册时返回数据：" + jsonData);
        String noticeMessage = null;
        if (jsonData.length() < 5)
        {
            noticeMessage = "欢迎光临！";
            createNoticeMessagePage(getConfigureProvider().format(URLVariable.INDEX), noticeMessage, response);
            return;
        }
        //分析从对方传回来的数据
        @SuppressWarnings("unchecked")
        Map<String, String> retMap = gs.fromJson(jsonData, Map.class);
        // 验签
        boolean flag = manage.registerReturnDecoder(retMap);
        if (!flag)
        {
            logger.info("注册验签失败-返回码：" + retMap.get("resp_code"));
            throw new LogicalException("注册验签失败-返回码：" + retMap.get("resp_code") + "。");
        }
        if (FuyouRespCode.JYCG.getRespCode().equals(retMap.get("resp_code")))
        {
            try
            {
                // 对获取的数据进行集中处理
                manage.updateLegealInfo(retMap);
                int usrId = IntegerParser.parse(retMap.get("user_id_from"));
                //生日登录送红包
                ActivityCommon activityCommon = serviceSession.getService(ActivityCommon.class);
                activityCommon.sendActivity(usrId, T6340_F03.redpacket.name(), T6340_F04.birthday.name());
//                if (Boolean.parseBoolean(getConfigureProvider().getProperty(MallVariavle.IS_MALL)))
//                {
//                    // 实名认证赠送积分
//                    SetScoreManage setScoreManage = serviceSession.getService(SetScoreManage.class);
//                    setScoreManage.giveScore(usrId, T6106_F05.realname, null);
//                    //注册第三方账户赠送积分
//                    setScoreManage.giveScore(usrId, T6106_F05.trusteeship, null);
//                }
                //  getController().prompt(request, response, PromptLevel.INFO, "恭喜您注册成功！");
                noticeMessage = "恭喜您注册成功！";
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
        else if (FuyouRespCode.YHYKH.getRespCode().equals(retMap.get("resp_code")))
        {
            String mobile_no = retMap.get("mobile_no");
            // 5343用户已开户——针对注册异常处理（富友成功，平台第一次失败时，第二次 返回 5343时更新同步用户信息）
            logger.info("5343用户已开户-处理企业用户-注册异常处理-手机号：" + mobile_no);
            try
            {
                if (manage.selectT6119(mobile_no))
                {
                    logger.info("5343用户已开户-注册异常处理-手机号：" + mobile_no + "备注:手机号未被占用-查询用户信息");
                    // 至 富友查询用户信息
                    UserinfoQuery userinfoQuery = new UserinfoQuery();
                    final UserQueryResponseEntity userQuery =
                        userinfoQuery.userinfoQuery(manage.userChargeQuery(getConfigureProvider().format(FuyouVariable.FUYOU_ACCOUNT_ID),
                            mobile_no),
                            serviceSession,
                            getConfigureProvider().format(FuyouVariable.FUYOU_QUERYUSERINFS_URL));
                    if (!FuyouRespCode.JYCG.getRespCode().equals(userQuery.getResp_code()))
                    {
                        logger.info("5343用户已开户-注册异常处理-手机号：" + mobile_no + "备注：查询信息失败");
                        throw new LogicalException("5343用户已开户-注册异常处理，查询信息失败！");
                    }
                    // 身份证
                    retMap.put("certif_id", userQuery.getCertif_id());
                    // 邮箱
                    retMap.put("email", userQuery.getEmail());
                    // 开户行地区代码
                    retMap.put("city_id", userQuery.getCity_id());
                    // 开户行行别
                    retMap.put("parent_bank_id", userQuery.getParent_bank_id());
                    // 开户支行名称
                    retMap.put("bank_nm", userQuery.getBank_nm());
                    // 银行卡账号
                    retMap.put("capAcntNo", userQuery.getCapAcntNo());
                    
                    retMap.put("cust_nm", userQuery.getCust_nm());
                    
                    //对获取的数据进行集中处理
                    manage.updateLegealInfo(retMap);
                    int usrId = serviceSession.getSession().getAccountId();
                    //生日登录送红包
                    ActivityCommon activityCommon = serviceSession.getService(ActivityCommon.class);
                    activityCommon.sendActivity(usrId, T6340_F03.redpacket.name(), T6340_F04.birthday.name());
//                    if (Boolean.parseBoolean(getConfigureProvider().getProperty(MallVariavle.IS_MALL)))
//                    {
//                        // 实名认证赠送积分
//                        SetScoreManage setScoreManage = serviceSession.getService(SetScoreManage.class);
//                        setScoreManage.giveScore(usrId, T6106_F05.realname, null);
//                        //注册第三方账户赠送积分
//                        setScoreManage.giveScore(usrId, T6106_F05.trusteeship, null);
//                    }
                    logger.info("5343用户已开户-注册异常处理-手机号：" + mobile_no + "备注：处理成功");
                    //  getController().prompt(request, response, PromptLevel.INFO, "恭喜您注册成功！");
                    noticeMessage = "恭喜您注册成功！";
                }
                else
                {
                    logger.info("5343用户已开户-注册异常处理-手机号：" + mobile_no + "备注：处理失败，手机号已被占用");
                    // getController().prompt(request, response, PromptLevel.INFO, "您使用的手机号已被占用，请更换手机号！");
                    noticeMessage = "您使用的手机号已被占用，请更换手机号！";
                }
            }
            catch (Exception e)
            {
                logger.info("5343用户已开户-注册异常处理-手机号：" + mobile_no + "备注：处理失败");
                logger.error(e, e);
            }
        }
        else
        {
            logger.info("返回码为：" + retMap.get("resp_code"));
            //  getController().prompt(request, response, PromptLevel.INFO, BackCodeInfo.info(retMap.get("resp_code")));
            noticeMessage = BackCodeInfo.info(retMap.get("resp_code"));
        }
        logger.info("处理企业用户注册时返回数据结束...");
        //        sendRedirect(request, response, getConfigureProvider().format(URLVariable.USER_CHARGE));
        createNoticeMessagePage(getConfigureProvider().format(URLVariable.USER_CHARGE), noticeMessage, response);
    }
    
    @Override
    protected boolean mustAuthenticated()
    {
        return false;
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        getResourceProvider().log(throwable);
        String retUrl = request.getHeader("Referer");
        if (throwable instanceof ParameterException || throwable instanceof SQLException)
        {
            getController().prompt(request, response, PromptLevel.ERROR, "系统繁忙，请您稍后再试!");
            sendRedirect(request, response, retUrl);
        }
        else if (throwable instanceof LogicalException)
        {
            getController().prompt(request, response, PromptLevel.WARRING, throwable.getMessage());
            sendRedirect(request, response, retUrl);
        }
        else if (throwable instanceof AuthenticationException)
        {
            sendRedirect(request, response, retUrl);
        }
        else
        {
            super.onThrowable(request, response, throwable);
        }
    }
    
}
