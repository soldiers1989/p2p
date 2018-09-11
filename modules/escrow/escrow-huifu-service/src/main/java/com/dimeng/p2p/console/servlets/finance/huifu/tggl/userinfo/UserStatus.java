package com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.http.servlet.annotation.Right;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.console.servlets.finance.huifu.dzgl.AbstractDzglServlet;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.query.UserInfoQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.UserAcctQueryManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 用户银行卡信息查询
 * 
 * @author raoyujun
 * @version [版本号, 2016年7月29日]
 */
@Right(id = "P2P_C_FINANCE_HUIFUUSERSTATUS", isMenu = true, name = "开户查询", moduleId = "P2P_C_HUIFU_USERINFO", order = 1)
public class UserStatus extends AbstractDzglServlet
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
        String type = request.getParameter("type");
        UserAcctQueryManage manage = serviceSession.getService(UserAcctQueryManage.class);
        if (T6110_F06.ZRR.name().equals(type))
        {
            UserInfoQueryEntity entity = manage.memberUserInfo(id);
            if (null != entity && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.RespCode))
            {
                if (null != entity.UsrStat && "N".equals(entity.UsrStat))
                {
                    if (manage.selectT6119(id, PaymentInstitution.CHINAPNR.getInstitutionCode(), entity.UsrCustId) == 0)
                    {
                        // UserManage userManage = serviceSession
                        // .getService(UserManage.class);
                        // UserRegisterEntity userRegisterEntity = new
                        // UserRegisterEntity();
                        // userManage.updateUserInfo(userRegisterEntity);
                        //
                        // ResourceProvider resourceProvider =
                        // getResourceProvider();
                        // ConfigureProvider configureProvider =
                        // resourceProvider
                        // .getResource(ConfigureProvider.class);
                        //
                        // // 生日登录送红包
                        // ActivityCommon activityCommon = serviceSession
                        // .getService(ActivityCommon.class);
                        // activityCommon.sendActivity(id,
                        // T6340_F03.redpacket.name(),
                        // T6340_F04.birthday.name());
                        // if (Boolean.parseBoolean(getConfigureProvider()
                        // .getProperty(MallVariavle.IS_MALL))) {
                        // // 实名认证赠送积分
                        // SetScoreManage setScoreManage = serviceSession
                        // .getService(SetScoreManage.class);
                        // setScoreManage.giveScore(id, T6106_F05.realname,
                        // null);
                        // // 注册第三方账户赠送积分
                        // setScoreManage.giveScore(id, T6106_F05.trusteeship,
                        // null);
                        // }
                        // // 是否有实名认证统计功能
                        // SafetyManage safetyManage = serviceSession
                        // .getService(SafetyManage.class);
                        // boolean is_realNameAuth = Boolean
                        // .parseBoolean(configureProvider
                        // .getProperty(RealNameAuthVarivale.IS_REALNAMEAUTH));
                        // if (is_realNameAuth) {
                        // // 实名认证通过时间
                        // safetyManage.updateT6198F06(id);
                        // }
                    }
                }
                else if ("D".equals(entity.UsrStat))
                {
                    
                }
            }
            else
            {
                
            }
            request.setAttribute("userEntity", entity);
        }
        else
        {
            CorpQueryEntity entity = manage.corpUserInfo(id);
            request.setAttribute("corpEntity", entity);
        }
        forward(request, response, getController().getViewURI(request, getClass()));
    }
}
