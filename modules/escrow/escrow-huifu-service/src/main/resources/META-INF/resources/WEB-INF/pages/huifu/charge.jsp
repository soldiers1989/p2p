<%@page import="com.dimeng.p2p.user.servlets.capital.Charge" %>
<%@page import="com.dimeng.p2p.user.servlets.Index" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F03" %>
<%@page import="com.dimeng.p2p.common.FormToken" %>
<%@page import="com.dimeng.p2p.variables.defines.pays.PayVariavle" %>
<%@page import="com.dimeng.p2p.escrow.huifu.variables.HuifuVariable" %>
<%@page import="com.dimeng.p2p.S61.entities.T6110" %>
<%@page import="com.dimeng.p2p.S61.entities.T6101" %>
<%@page import="com.dimeng.p2p.account.user.service.UserInfoManage" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.dimeng.p2p.user.servlets.capital.Unpay" %>
<%@page import="com.dimeng.p2p.user.servlets.capital.TradingRecord" %>
<%@page import="com.dimeng.util.ObjectHelper" %>
<%@page import="java.math.BigInteger" %>
<%@page import="com.dimeng.p2p.user.servlets.account.UserBases" %>
<%@page import="com.dimeng.p2p.user.servlets.account.SafetymsgFZRR" %>
<%@page import="com.dimeng.p2p.S61.entities.T6118" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F02" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F03" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F04" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F05" %>
<%@page import="com.dimeng.p2p.account.user.service.TxManage" %>
<%@page import="com.dimeng.p2p.S50.entities.T5023" %>
<%@page import="com.dimeng.p2p.S50.enums.T5023_F02" %>
<%@include file="/WEB-INF/include/authenticatedSession.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>我的<%configureProvider.format(out, SystemVariable.SITE_NAME);%>_充值管理
    </title>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%

    UserInfoManage manage = serviceSession.getService(UserInfoManage.class);
    T6101 balance = manage.search();
    String type = ObjectHelper.convert(request.getAttribute("type"), String.class);
    String amount = ObjectHelper.convert(request.getAttribute("amount"), String.class);
    boolean smrz = manage.isSmrz();
    boolean txmm = manage.isTxmm();
    int _min = IntegerParser.parse(configureProvider.getProperty(PayVariavle.CHARGE_MIN_AMOUNT));
    BigInteger _max = new BigInteger(configureProvider.getProperty(PayVariavle.CHARGE_MAX_AMOUNT));
    //用户充值手续费率
    double _p = DoubleParser.parse(configureProvider.getProperty(PayVariavle.CHARGE_RATE));
    int _pMax = IntegerParser.parse(configureProvider.getProperty(PayVariavle.CHARGE_MAX_POUNDAGE));
    T6110 userInfo = manage.getUserInfo(serviceSession.getSession().getAccountId());
    String safetymesgView = controller.getViewURI(request, UserBases.class);
    if (userInfo.F06 == T6110_F06.FZRR) {
        safetymesgView = controller.getViewURI(request, SafetymsgFZRR.class);
    }
    UserManage userManage = serviceSession.getService(UserManage.class);
    String usrCustId = userManage.getUsrCustId();
    boolean tg = BooleanParser.parse(configureProvider.getProperty(SystemVariable.SFZJTG));
    String escrow = configureProvider.getProperty(SystemVariable.ESCROW_PREFIX);

    TxManage txManage = serviceSession.getService(TxManage.class);
    int count = txManage.getCount();
    int countXx = txManage.getXxczCount();

    T6118 t6118 = txManage.getVerifyEntity();    //获取认证情况
    T5023 t5023 = txManage.getT5023(T5023_F02.CHARGE);
    T5023 t5023Line = txManage.getT5023(T5023_F02.CHARGELINE);
    CURRENT_CATEGORY = "ZJGL";
    CURRENT_SUB_CATEGORY = "CZ";
%>
<div class="main_bg clearfix">
    <div class="user_wrap w1002 clearfix">
        <%@include file="/WEB-INF/include/menu.jsp" %>
        <div class="r_main">
            <div class="user_mod">
                <div class="user_tab clearfix">
                    <ul>
                        <li id="one1" onclick="setTab('one',1,2)">在线充值<i></i></li>
                        <li id="one2" onclick="setTab('one',2,2)">线下充值<i></i></li>
                    </ul>
                </div>
                <div id="con_one_1" class="p25">
                    <form action="<%=configureProvider.format(URLVariable.PAY_CHARGE)%>" target="_blank" method="post"
                          onsubmit="return onSubmit();">
                        <%=FormToken.hidden(serviceSession.getSession())%>
                        <input type="hidden" name="accountName"
                               value="<%StringHelper.filterHTML(out, serviceSession.getService(UserManage.class).getAccountName());%>"/>

                        <%if (tg && StringHelper.isEmpty(usrCustId)) { %>
                        <div class="f16 mt30 pt50 pb50 tc ml30 mr30" style="background:#f3f3f3;">
                          		  您需要在第三方托管平台上进行注册，才可申请充值提现！请<a href="<%=configureProvider.format(URLVariable.OPEN_ESCROW_GUIDE) %>"
                                                          class="red">立即注册</a>！
                        </div>
                        <%} else {%>
                        <div class="lh30 clearfix">
                            <a class="fr f14 red" href="<%=controller.getViewURI(request, Unpay.class) %>">
                                <%if (count != 0) { %>
                                <i class="topup_news_ico"></i>
                                <%}%>
                             		查询未完成充值记录&gt;&gt;
                            </a>
                        </div>
                        <div class="gray3 f16">填写充值金额</div>
                        <div class="user_mod_gray mt10 pt30 pb30">
                            <div class="form_info topup_form">
                                <ul class="cell">
                                    <li>
                                        <div class="til">可用金额：</div>
                                        <div class="info">
                                            <span class="gray3 f18"><%=Formater.formatAmount(balance.F06) %></span>元
                                        </div>
                                        <div class="clear"></div>
                                    </li>
                                    <li>
                                        <div class="til">
                                            <span class="red">*</span>充值金额：
                                        </div>
                                        <div class="info">
                                            <input id="amount" name="amount" type="text"
                                                   onKeyUp="value=value.replace(/\D/g,'');setDMKeyup(this);"
                                                   class="text yhgl_ser fl mr5 required min-size-<%=_min %> max-size-<%=_max %> isNaN"
                                                   value="<%StringHelper.filterQuoter(out, amount); %>" autocomplete="off"/>
                                            <span class="fl">元</span>
											<span class="hover_tips" style="margin-top: 6px;margin-left: 5px;">
	                                            <div class="hover_tips_con">
                                                    <div class="arrow"></div>
                                                    <div class="border">
									                                                        充值金额不能低于<%=_min %>
									                                                        元，不能高于<%=_max.divide(new BigInteger("10000")) %>万元
                                                    </div>
                                                </div>
	                                        </span>
                                            <div class="clear"></div>
                                            <p id="chargeTipInfo" tip></p>
                                            <p id="chargeErrorInfo" errortip class="red"></p>
                                            <!-- <p class="red" id="chargeTips"></p> -->
                                        </div>
                                        <div class="clear"></div>
                                    </li>
                                    <li>
                                        <div class="til">充值手续费：</div>
                                        <div class="info">
                                            <span id="poundage" class="fl">0.00</span>
                                            <span class="fl">元</span>
											 <span class="hover_tips" style="margin-top: 6px;margin-left: 5px;">
	                                            <div class="hover_tips_con">
                                                    <div class="arrow"></div>
	                                                    <div class="border">
										                                                        充值费用按充值金额的<%=_p * 100 %>%由第三方平台收取，上限<%=_pMax %>
										                                                        元，超出部分由<%=configureProvider.getProperty(SystemVariable.SITE_NAME) %>支付
                                                    	</div>
                                                </div>
	                                        </span>

                                            <div class="clear"></div>
                                        </div>
                                        <div class="clear"></div>
                                    </li>
                                    <li>
                                        <div class="til">实际到账金额：</div>
                                        <div class="info">
                                            <span class="f24 red" id="paySum">0.00</span>元
                                        </div>
                                        <div class="clear"></div>
                                    </li>
                                </ul>
                                <div class="tc"><input type="submit" style="cursor: pointer;" value="充值"
                                                       class="btn01 mb15 mt15"/></div>
                            </div>
                        </div>
                        <%} %>
                        <%if (t5023 != null && !StringHelper.isEmpty(t5023.F03)) {%>
                        <div class="mt20 lh24 htsc_pic">
                            <a name="001"></a>
                            <p class="highlight">温馨提示：</p>
                            <%StringHelper.format(out, t5023.F03, fileStore);%>
                        </div>
                        <%}%>
                        <%--<div class="topup_problem mt50"><i class="problem_icon"></i>付款遇到问题了？先看看是不是由于下面的原因。</div>
                        <div class="topup_problem_con mt20">
                            <ul>
                                <li>
                                    <p class="gray3 mb5">要求开通网上银行？</p>

                                    <p>— 建议选择银联在线支付付款，如果是信用卡还可选择快捷支付，再选择对应银行支付。</p>
                                </li>
                                <li>
                                    <p class="gray3 mb5">所需支付金额超过了银行支付限额？</p>

                                    <p>— 建议先分若干次充值到本平台余额，或登录网上银行提高上限额度，即可轻松支付。</p>
                                </li>
                                <li>
                                    <p class="gray3 mb5">收不到银行的短信验证码？</p>

                                    <p>— 建议重新获取短信验证码，如果还是收不到短信，直接打各银行的客服电话获取短信验证码。</p>
                                </li>
                                <li>
                                    <p class="gray3 mb5">网银页面显示错误或者空白？</p>

                                    <p>— 建议更换到IE浏览器进行支付操作，或使用银联在线支付或支付宝付款。</p>
                                </li>
                                <li>
                                    <p class="gray3 mb5">网上银行扣款后，值单仍显示"未付款"怎么办？</p>

                                    <p>—
                                        可能是由于银行的数据没有即时传输，请您不要担心，稍后刷新页面查看。如较长时间仍显示未付款，可先向银行或支付平台获取支付凭证（扣款订单号/第三方交易号），请联系客服(<%configureProvider.format(out, SystemVariable.SITE_CUSTOMERSERVICE_TEL);%>)为您解决。</p>
                                </li>
                            </ul>
                        </div>--%>
                    </form>
                </div>

                <div id="con_one_2" class="p25" style="display:none;">
                    <%-- <form action="<%=configureProvider.format(URLVariable.PAY_CHARGE4Line)%>" target="_blank" method="post" onsubmit="return onSubmitLine();" class="form1"> --%>
                    <%=FormToken.hidden(serviceSession.getSession())%>
                    <input type="hidden" name="accountName" value="<%StringHelper.filterHTML(out, serviceSession.getService(UserManage.class).getAccountName());%>"/>
                    <div class="lh30 clearfix">
                        <h2 class="fl gray3 f16">选择充值方式</h2>
                        <a class="fr f14 red" href="<%=controller.getViewURI(request, Unpay.class) %>?type=unpayXxcz">
                            <%if (countXx != 0) { %>
                            <i class="topup_news_ico"></i>
                            <%}%>
                           	查询未完成充值记录&gt;&gt;
                        </a>
                    </div>
                    <div class="topup_offline_way mt20"><input name="payline" type="radio" value="" checked="checked"
                                                               class="mr10"/><img
                            src="<%=controller.getStaticPath(request) %>/images/offline.png"></div>
                    <div class="gray3 f16 mt30">填写充值金额</div>
                    <div class="user_mod_gray mt10 pt30 pb30">
                        <div class="form_info">
                            <ul class="cell">
                                <li>
                                    <div class="til"><span class="red">*</span> 充值金额(元)：</div>
                                    <div class="info">
                                        <input id="amountLine" name="amount" type="text"
                                               onKeyUp="value=value.replace(/\D/g,'');"
                                               class="text yhgl_ser fl mr5 required min-size-1 isNaN" value=""/>
                                        <p tip></p>
                                        <p errortip></p>
                                    </div>
                                </li>
                                <li>
                                    <div class="til"><span class="red">*</span> 备注：</div>
                                    <div class="info">
                                        <textarea id="remarks" name="remarks" cols="70" rows="4"
                                                  class="textarea_style required max-length-50"></textarea>

                                        <p tip>最大可输入50个字</p>

                                        <p errortip></p>
                                    </div>
                                </li>
                            </ul>
                            <div class="tc"><input type="button" style="cursor: pointer;" fromname="form1"
                                                   onclick="onSubmitLine();" value="申请线下充值"
                                                   class="btn01 mb15 mt15 sumbitForme"/></div>
                        </div>
                    </div>
                    <%if (t5023Line != null && !StringHelper.isEmpty(t5023Line.F03)) {%>
                    <div class="mt20 lh24 htsc_pic">
                        <span class="highlight">温馨提示：</span><br/>
                        <%StringHelper.format(out, t5023Line.F03, fileStore);%>
                    </div>
                    <%}%>
                    <!--  </form> -->
                </div>
            </div>
        </div>

        <div class="clear"></div>
    </div>
</div>
<div id="dialogRz" style="display: none;">
    <div class="popup_bg"></div>
    <div class="dialog ">
        <div class="title"><a href="javascript:void(0);" class="out" onclick="closeDivRz()"></a>认证提示</div>
        <div class="content">
            <div class="tip_information">
                <div class="doubt"></div>
                <div class="tips">
                    <span class="f20 gray3" id="rzcon_error"></span>
                </div>
            </div>
            <div class="tc mt20">
                <a class="btn01" href="<%=safetymesgView%>" target="_blank" onclick="closeDivRz()">去认证</a>
            </div>
        </div>
    </div>
</div>
<div id="dialog" style="display: none;">
    <div class="popup_bg"></div>
    <div class="dialog">
        <div class="title"><a href="javascript:void(0);" class="out" onclick="closeDiv()"></a>提示</div>
        <div class="content">
            <div class="tip_information">
                <div id="errorico" class="doubt"></div>
                <div class="tips">
                    <span class="f20 gray3" id="con_error"></span>
                </div>
            </div>
            <div class="tc mt20"><a href="javascript:void(0);" class="btn01" id="doalogClick"
                                    onclick="closeDiv()">确定</a></div>
        </div>
    </div>
</div>
<div id="dialogHmd" style="display: none;">
    <div class="popup_bg"></div>
    <div class="dialog">
        <div class="title"><a href="javascript:void(0);" class="out" onclick="closeDivHmd()"></a>提示</div>
        <div class="content">
            <div class="tip_information">
                <div class="doubt"></div>
                <div class="tips">
                    <span class="f20 gray33">您的账号已被拉入黑名单，不能进行安全认证。如有疑问，请联系客服！</span>
                </div>
            </div>
            <div class="tc mt20"><a href="javascript:void(0);" class="btn01" onclick="closeDivHmd()">确定</a></div>
        </div>
    </div>
</div>
<div id="problem" style="display: none;">
    <div class="popup_bg"></div>
    <div class="dialog">
        <div class="content">
            <a href="javascript:void(0);" class="out"></a>

            <div class="tip_information">
                <div class="doubt"></div>
                <div class="tips">
                    <span class="f20 gray33">请您在新打开的页上完成付款。</span>

                    <p>付款完成前请不要关闭此窗口。<br/>完成付款后请根据您的情况点击下面的按钮：</p>
                </div>
            </div>
            <div class="tc mt10">
                <%if (T6110_F07.HMD != userInfo.F07) { %>
                <a href="<%=controller.getViewURI(request, TradingRecord.class) %>" class="btn04">已完成付款</a>
                <%} else { %>
                <a href="<%=controller.getViewURI(request, Index.class) %>" class="btn04">已完成付款</a>
                <%} %>
                <a href="#001" class="btn04" id="reloadChargeUser">付款遇到问题</a>
            </div>
        </div>
    </div>
</div>
<%
    String mPhone = configureProvider.getProperty(PayVariavle.CHARGE_MUST_PHONE);
    String mYxrz = configureProvider.getProperty(PayVariavle.CHARGE_MUST_EMAIL);
    String mRealName = configureProvider.getProperty(PayVariavle.CHARGE_MUST_NCIIC);
    String mWithPsd = configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD);
    //是否托管项目
    String misTg = configureProvider.getProperty(SystemVariable.SFZJTG);
    List<String> list = new ArrayList<>();
    if (BooleanParser.parse(mRealName) && t6118.F02.equals(T6118_F02.BTG)) {
        list.add("实名认证");
    }
    if (BooleanParser.parse(mPhone) && t6118.F03.equals(T6118_F03.BTG)) {
        list.add("手机号认证");
    }
    if (BooleanParser.parse(mYxrz) && t6118.F04.equals(T6118_F04.BTG)) {
        list.add("邮箱认证");
    }
    if (!BooleanParser.parse(misTg) && BooleanParser.parse(mWithPsd) && t6118.F05.equals(T6118_F05.WSZ)) {
        list.add("设置交易密码");
    }
    StringBuilder authStr = new StringBuilder("");
    if (list.size() > 0) {
        authStr.append("充值必须先");
        int i = 0;
        authStr.append("<span class='red'>");
        for (String str : list) {
            i++;
            if (i == 1) {
                authStr.append(str);
                continue;
            }
            authStr.append("、");
            authStr.append(str);
        }
        authStr.append("</span>，请到<a class='blue'");
        String baseInfoPageName = T6110_F06.ZRR == t6110.F06 ?"个人基础信息":"安全信息";
        if (T6110_F07.HMD != t6110.F07) {
            authStr.append("href='");
            authStr.append(safetymesgView);
            authStr.append("'>");
        } else {
            authStr.append("javascript:void(0);'");
            authStr.append(" onclick='showDialogInfoMsg()'>");
        }
        authStr.append(baseInfoPageName).append("</a>设置");

    }
    boolean mAuth = true;
    if (BooleanParser.parse(mPhone) && userInfo.F04 == null) {
        mAuth = false;
    }
    if (BooleanParser.parse(mRealName) && !smrz) {
        mAuth = false;
    }
    if (BooleanParser.parse(mYxrz) && userInfo.F05 == null) {
        mAuth = false;
    }
    if (!BooleanParser.parse(misTg) && BooleanParser.parse(mWithPsd) && !txmm) {
        mAuth = false;
    }
%>
<script type="text/javascript">
    var p = <%=_p %>;
    var pMax = <%=_pMax %>;
    var min = <%=_min %>;
    var max =
    <%=_max %>
    var auth = false;
    var isHmd = <%=T6110_F07.HMD == userInfo.F07%>;
    var loginUrl = "<%=controller.getViewURI(request, Login.class) %>";
    var chargeUrl = "<%=controller.getViewURI(request, Charge.class) %>";
    var chargeSubmitUrl = "<%=configureProvider.format(URLVariable.PAY_CHARGE4Line)%>";
    var tabIndex = <%=request.getParameter("i")%>
            <%if(mAuth){%>
            auth = true;
    <%}%>
    var authText = "<%=authStr.toString() %>";

    function setCheckBox(index) {
        $("#radio" + index).click();
    }

    function closeDiv() {
        if (!$("#errorico").hasClass("doubt")) {
            $("#errorico").removeClass();
            $("#errorico").addClass("doubt");
        }
        $("#dialog").hide();
    }
    function closeDivRz() {
        $("#dialogRz").hide();
    }
    function showDialogInfoMsg() {
        $("#dialog").hide();
        $("#dialogHmd").show();
    }

    function closeDivHmd() {
        $("#dialogHmd").hide();
    }
    $(function () {

        setTab('one', tabIndex == null ? 1 : tabIndex, 2);
    });
</script>
<%@include file="/WEB-INF/include/footer.jsp" %>
<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/charge.js"></script>
<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/amountUtil.js"></script>
<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/validation.js"></script>
</body>
</html>