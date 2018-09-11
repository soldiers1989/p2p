package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S61.enums.T6106_F05;
import com.dimeng.p2p.S63.enums.T6340_F03;
import com.dimeng.p2p.S63.enums.T6340_F04;
import com.dimeng.p2p.account.user.service.SafetyManage;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.register.UserRegisterEntity;
import com.dimeng.p2p.escrow.huifu.service.UserManage;
import com.dimeng.p2p.repeater.score.SetScoreManage;
import com.dimeng.p2p.service.ActivityCommon;
import com.dimeng.p2p.variables.defines.MallVariavle;
import com.dimeng.p2p.variables.defines.RealNameAuthVarivale;
import com.dimeng.util.parser.DateTimeParser;

/**
 * 
 * 用户注册回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class UserRegisterRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 个人用户注册回调开始 ----------");
        UserManage manage = serviceSession.getService(UserManage.class);
        UserRegisterEntity entity = manage.registerReturnDecoder(request);
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                //更新用户信息
                manage.updateUserInfo(entity);
                
                ResourceProvider resourceProvider = getResourceProvider();
                ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
                
                int usrId =
                    Integer.parseInt(entity.usrId.substring(entity.usrId.indexOf(HuiFuConstants.UserCode.USER_CODE)
                        + HuiFuConstants.UserCode.USER_CODE.length()));
                //生日登录送红包
                ActivityCommon activityCommon = serviceSession.getService(ActivityCommon.class);
                activityCommon.sendActivity(usrId, T6340_F03.redpacket.name(), T6340_F04.birthday.name());
                SetScoreManage setScoreManage = serviceSession.getService(SetScoreManage.class);
                if (Boolean.parseBoolean(getConfigureProvider().getProperty(MallVariavle.IS_MALL)))
                {
                    // 实名认证赠送积分
                    setScoreManage.giveScore(usrId, T6106_F05.realname, null);
                    //注册第三方账户赠送积分
                    setScoreManage.giveScore(usrId, T6106_F05.trusteeship, null);
                }
                //手机认证赠送积分
                boolean is_mall = Boolean.parseBoolean(configureProvider.getProperty(MallVariavle.IS_MALL));
                if (is_mall)
                {
                    setScoreManage.giveScore(usrId, T6106_F05.cellphone, null);
                }
                //是否有实名认证统计功能
                SafetyManage safetyManage = serviceSession.getService(SafetyManage.class);
                boolean is_realNameAuth =
                    Boolean.parseBoolean(configureProvider.getProperty(RealNameAuthVarivale.IS_REALNAMEAUTH));
                if (is_realNameAuth)
                {
                    //实名认证通过时间
                    safetyManage.updateT6198F06(usrId);
                }
                
            }
            catch (Exception e)
            {
                logger.info(String.format("用户注册异常%s", DateTimeParser.format(new Date())), e);
            }
        }
        printMark(response, entity != null ? entity.trxId : request.getParameter("TrxId"));
    }
    
}
