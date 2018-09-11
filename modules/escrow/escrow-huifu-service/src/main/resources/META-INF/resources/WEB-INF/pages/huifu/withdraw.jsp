<%@page import="com.dimeng.util.parser.BigDecimalParser"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.dimeng.p2p.variables.defines.pays.PayVariavle" %>
<%@page import="com.dimeng.p2p.S61.entities.T6101" %>
<%@page import="com.dimeng.p2p.S61.entities.T6118" %>
<%@page import="com.dimeng.p2p.account.user.service.TxManage" %>
<%@page import="com.dimeng.p2p.account.user.service.entity.BankCard" %>
<%@page import="com.dimeng.p2p.common.FormToken" %>
<%@page import="com.dimeng.p2p.user.servlets.account.Addbankcard" %>
<%@page import="com.dimeng.p2p.user.servlets.capital.Withdraw" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="com.dimeng.p2p.common.RSAUtils" %>
<%@page import="org.bouncycastle.util.encoders.Hex" %>
<%@page import="com.dimeng.p2p.service.PtAccountManage" %>
<%@page import="com.dimeng.p2p.common.DimengRSAPulicKey" %>
<%@page import="com.dimeng.p2p.user.servlets.account.UserBases" %>
<%@page import="com.dimeng.p2p.user.servlets.account.SafetymsgFZRR" %>
<%@page import="com.dimeng.p2p.S50.entities.T5023" %>
<%@page import="com.dimeng.p2p.S50.enums.T5023_F02" %>
<%@page import="com.dimeng.p2p.service.SafetymsgViewManage" %>
<%@page import="com.dimeng.p2p.escrow.huifu.service.WithdrawManage" %>
<%@include file="/WEB-INF/include/authenticatedSession.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>我的<%configureProvider.format(out, SystemVariable.SITE_NAME); %>_提现
    </title>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%
    PtAccountManage ptAccountManage = serviceSession.getService(PtAccountManage.class);
    DimengRSAPulicKey publicKey = (DimengRSAPulicKey) ptAccountManage.getPublicKey();
    String modulus = new String(Hex.encode(publicKey.getModulus().toByteArray()));
    String exponent = new String(Hex.encode(publicKey.getPublicExponent().toByteArray()));
    TxManage manage = serviceSession.getService(TxManage.class);
    SafetymsgViewManage safeManage = serviceSession.getService(SafetymsgViewManage.class);

    T6118 t6118 = manage.getVerifyEntity();    //获取认证情况

    UserInfoManage userInfoManage = serviceSession.getService(UserInfoManage.class);
    T6101 t6101 = userInfoManage.search();
    T6110 t6110_tmp = userInfoManage.getUserInfo(serviceSession.getSession().getAccountId());
    String safetymesgView = controller.getViewURI(request, UserBases.class);
    String baseInfoPageName = "个人基础信息";
    if (t6110_tmp.F06 == T6110_F06.FZRR) {
        safetymesgView = controller.getViewURI(request, SafetymsgFZRR.class);
        baseInfoPageName = "安全信息";
    }
    //查询用户第三方可取现金额  ----- 由于取现未冻结金额，所以到第三方查询到的可取现金额未减去上一次取现金额，所以不能展示
    BigDecimal canCashAmt = BigDecimal.ZERO;
    if (t6110_tmp.F06 == T6110_F06.ZRR) {
        WithdrawManage withdrawManage = serviceSession.getService(WithdrawManage.class);
        canCashAmt = withdrawManage.QueryCashBalanceBg(null);
    } 


    BankCard[] cards = manage.bankCards();
    int cardId = IntegerParser.parse(request.getAttribute("cardId"));
    boolean realName = false;
    if(manage.getVerifyStatus() && manage.getVerifyTradingPsw()){
        realName = true;
    }
    String funds = controller.getPrompt(request, response, PromptLevel.WARRING);
    UserManage userManage = serviceSession.getService(UserManage.class);
    String usrCustId = userManage.getUsrCustId();
    boolean tg = BooleanParser.parse(configureProvider.getProperty(SystemVariable.SFZJTG));
    boolean isOpenPsd = BooleanParser.parse(configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD));
	// 增加对托管的区分
    String escrow = configureProvider.getProperty(SystemVariable.ESCROW_PREFIX);
	String action = null;
	if(tg){
		action = configureProvider.format(URLVariable.ESCROW_URL_WITHDRAW);
	}else{
		action = controller.getURI(request, Withdraw.class);
	}

    CURRENT_CATEGORY = "ZJGL";
    CURRENT_SUB_CATEGORY = "TXGL";

    Map bankMap = new HashMap();
    bankMap.put("中国银行", "BOC");
    bankMap.put("工商银行", "ICBC");
    bankMap.put("农业银行", "ABC");
    bankMap.put("建设银行", "CCB");
    bankMap.put("招商银行", "CMB");

    bankMap.put("交通银行", "BOCOM");
    bankMap.put("中国邮政储蓄银行", "PSBC");
    bankMap.put("中国民生银行", "CMBC");
    bankMap.put("中国光大银行", "CEB");
    bankMap.put("平安银行", "PINGAN");
    bankMap.put("上海浦东银行", "SPDB");
    T5023 t5023 = manage.getT5023(T5023_F02.WITHDRAW);
%>
<div class="main_bg clearfix">
    <div class="user_wrap w1002 clearfix">
        <%@include file="/WEB-INF/include/menu.jsp" %>
        <form action="<%=action %>" autocomplete="off" method="post" onsubmit="return onSubmit();">
            <%=FormToken.hidden(serviceSession.getSession()) %>
            <%if (cardId > 0) { %>
            	<input type="hidden" name="cardId" value="<%=cardId %>"/>
            <%} else { %>
            	<input type="hidden" name="cardId" value="<%=(cards!=null&&cards.length>0)?cards[0].id:"" %>"/>
            <%} %>
            
            <div class="r_main">
                <div class="user_mod">
                    <%if (tg && StringHelper.isEmpty(usrCustId)) { %>
                    <div class="f16 mt30 pt50 pb50 tc ml30 mr30" style="background: #f3f3f3;">
                       		 您需要在第三方托管平台上进行注册，才可申请充值提现！请
                       		 <a target="_blank" href="<%=configureProvider.format(URLVariable.OPEN_ESCROW_GUIDE) %>"
                              class="red">立即注册</a>！
                    </div>
                    <%} else { %>
                    <div class="lh30 clearfix">
                        <h2 class="fl gray3 f16">选择提现银行</h2>
                        <a href="<%=configureProvider.format(URLVariable.CARD_MANAGE) %>" class="fr ml20 highlight"><i class="gl_icon">
                        </i><span class="va_m">管理银行卡</span></a>
                        <%if (cards == null || cards.length < IntegerParser.parse(configureProvider.getProperty(SystemVariable.MAX_BANKCARD_COUNT))) { %>
	                        <%if (tg) { %>
	                        <a target="_blank" href="<%=configureProvider.format(URLVariable.ESCROW_URL_BINDCARD) %>"
	                           class="fr ml20 highlight"><i class="gl_icon"></i><span class="va_m">添加银行卡</span></a>
	                        <%} else { %>
	                        	<a href="javascript:void(0);" onclick="addCard();" class="fr highlight"><i class="tj_icon"></i><span
	                                class="va_m">添加银行卡</span></a>
	                        <%} %>
                        <%} %>
                    </div>
                    <ul class="bank_manage_list clearfix">
                        <%
                            if (cards != null && cards.length > 0) {
                                int i = 0;
                                for (BankCard card : cards) {
                                    if (card == null) {
                                        continue;
                                    }
                                    if (bankMap.containsKey(card.Bankname)) {
                        %>
                        <li><a href="javascript:void(0);"
                               onclick="checkCard(this, <%=card.id %>)">
                            <div class="pic">
                                <img
                                        src="<%=controller.getStaticPath(request)%>/images/bank_logo_<%=bankMap.get(card.Bankname) %>.png"
                                        width="148" height="38">
                            </div>
                            <div class="number">
                                <%
                                    if (!StringHelper.isEmpty(card.BankNumber) && card.BankNumber.length() > 4) {
                                        StringHelper.filterHTML(out, card.BankNumber);
                                    }
                                %>
                            </div>
                            <div class="delete clearfix">
                                <%
                                    if (cardId > 0) {
                                        if (cardId == card.id) {
                                %> <i class="ico"></i>
                                <%
                                    }
                                } else {
                                    if (i == 0) {
                                %> <i class="ico"></i>
                                <%
                                        }
                                    }
                                %>
                            </div>
                        </a></li>
                        <%} else { %>
                        <li><a href="javascript:void(0);" onclick="checkCard(this, <%=card.id %>)">
                            <div class="pic f16" style="width:148px;height:38px;">
                                <%StringHelper.filterQuoter(out, card.Bankname); %>
                            </div>
                            <div class="number">
                                <%
                                    if (!StringHelper.isEmpty(card.BankNumber) && card.BankNumber.length() > 4) {
                                        StringHelper.filterHTML(out, card.BankNumber);
                                    }
                                %>
                            </div>
                            <div class="delete clearfix">
                                <%
                                    if (cardId > 0) {
                                        if (cardId == card.id) {
                                %> <i class="ico"></i>
                                <%
                                    }
                                } else {
                                    if (i == 0) {
                                %> <i class="ico"></i>
                                <%
                                        }
                                    }
                                %>
                            </div>
                        </a></li>
                        <%
                                }
                                i++;
                            }
                        } else {
                        %>
                        <li class="add">
                            <%if (tg) { %> 
                            	<a target="_blank" href="<%=configureProvider.format(URLVariable.ESCROW_URL_BINDCARD) %>"><i class="add_ico"></i>
                            	<p>添加银行卡</p></a> 
                            <%} else { %> 
                            	<a href="javascript:void(0);" onclick="addCard();"><i class="add_ico"></i>
                            	<p>添加银行卡</p></a> 
                            <%} %>
                        </li>
                        <%} %>
                        <div class="clear"></div>
                    </ul>
                    <div class="gray3 f16 mt30">填写提现金额</div>
                    <div class="user_mod_gray mt10 pt30 pb30">
                        <div class="form_info topup_form">
                            <ul class="cell">
                                <li>
                                    <div class="til">可用金额(元)：</div>
                                    <div class="info">
                                        <span class="gray3 f18"
                                              id="aFunds"><%=Formater.formatAmount(t6101.F06) %></span>
                                    </div>
                                    <div class="clear"></div>
                                </li>
                                <%if (t6110_tmp.F06 == T6110_F06.ZRR) { %>
                                <li>
                                    <div class="til">可提现金额(元)：</div>
                                    <div class="info">
                                        <span class="gray3 f18" id="aFunds">
                                        	<%=Formater.formatAmount(canCashAmt)%>
                                        </span>
                    						<span class="hover_tips">
												<div class="hover_tips_con" style="margin-left:-118px;">
                                                    <div class="arrow"></div>
                                                    <div class="border" style="width:210px;">可取现金额 = 可用余额 - 当日充值金额</div>
                                                </div>
											</span>
                                    </div>
                                    <div class="clear"></div>
                                </li>
                                <%} %>
                                <li>
                                    <div class="til">
                                        <span class="red">*</span> 提现金额(元)：
                                    </div>
                                    <div class="info">
                                        <input type="text" name="hdnAmount" style="display: none;"/>
                                        <input type="text" name="amount" autocomplete="off" 
                                               onkeyup="return onlyNumber(this);" class="text yhgl_ser"
                                               value="<%=StringHelper.isEmpty(request.getParameter("amount"))?"":request.getParameter("amount")%>"/>
											<span class="hover_tips">
												<div class="hover_tips_con" style="margin-left:-87px;">
                                                    <div class="arrow"></div>
                                                    <div class="border" style="width:150px;">提现金额最多保留两位小数</div>
                                                </div>
											</span>
											<%if(t6110_tmp.F06 == T6110_F06.ZRR){%>
											<span class="f12 gray9"> 
												当日充值金额 T+1日才可提现
											</span>
											<%} %>
                                        <p tip></p>
                                        <p errortip class="red"></p>
                                    </div>
                                </li>
                       			<li>
									<div class="til">提现方式：</div>
									<div class="info">
										<input type="radio" name="withdrawWay" checked="checked" value="GENERAL"/> 普通提现&nbsp;(下一个工作日到账)<br/>
									</div>
								 </li>
<!-- 								 <li>
									<div class="til">&nbsp;</div>
									<div class="info">
										<input type="radio" name="withdrawWay" value="FAST"/> 快速提现&nbsp;(最迟下一个工作日到账)<br/>
									</div>
								 </li>
								 <li> -->
									<div class="til">&nbsp;</div>
									<div class="info">
										<input type="radio" name="withdrawWay" value="IMMEDIATE"/> 即时提现&nbsp;(实时到账)
										<span class="hover_tips">
											<div class="hover_tips_con" style="margin-left:-90px;">
	                                        <div class="arrow"></div>
		                                        <div class="border" style="width:150px;">
			                                       	<%if(t6110_tmp.F06 == T6110_F06.ZRR){ %>
			                                       		 目前仅支持<br/>
		                                       		 	<span style="color: red;">工商银行</span>、
			                                       		<span style="color: red;">农业银行</span><br/>
			                                       		<span style="color: red;">招商银行</span>、
			                                       		<span style="color: red;">北京银行</span><br/>
			                                       		<span style="color: red;">中国银行</span>、
			                                       		<span style="color: red;">民生银行</span><br/>
			                                       		<span style="color: red;">渤海银行</span>、
			                                       		<span style="color: red;">光大银行</span><br/>
			                                       		<span style="color: red;">兴业银行</span>、
			                                       		<span style="color: red;">浙商银行</span><br/>
			                                       		<span style="color: red;">广发银行</span>、
			                                       		<span style="color: red;">华夏银行</span><br/>
			                                       		<span style="color: red;">平安银行</span>、
			                                       		<span style="color: red;">上海农商银行</span>
			                                       		</span>的即时取现。
			                                       	<%} else { %>
			                                       		目前仅支持<br/>
			                                       		<span style="color: red;">工商银行</span>、
			                                       		<span style="color: red;">招商银行</span><br/>
			                                       		的即时取现。
			                                   		<%} %>
		                                        </div>
	                                        </div>
										</span>
									</div>

								 </li>
                                <li>
                                    <div class="til">提现手续费(元)：</div>
                                    <div class="info">
                                        <span class="gray3 f18 va_m" id="poundage">0.00</span> <span
                                            class="hover_tips">
												<div class="hover_tips_con tx_tips">
                                                    <div class="arrow"></div>
                                                    <div class="border">
                                                        <p>收取提现手续费</p>
                                                        <%
                                                            String way = configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_WAY);
                                                            String proportion = configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_PROPORTION);
                                                            if ("BL".equals(way)) {
                                                                if (!StringHelper.isEmpty(proportion)) {
                                                                    float _proportion = Float.parseFloat(proportion);
                                                        %>
                                                        <table width="100%" border="0" cellspacing="0"
                                                               class="mt5 mb5">
                                                            <tr>
                                                                <td>按提现金额的<%=_proportion * 100 %>% + 2 收取
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <%
                                                            }
                                                        } else {
                                                        %>
                                                        <table width="100%" border="0" cellspacing="0"
                                                               class="mt5 mb5">
                                                            <tr>
																<td>5万以下</td>
																<td>5万（含）~20万以内</td>
                                                            </tr>
                                                            <tr>
                                                            	<%
                                                            		//默认第三方收取2元每笔， 取现手续费 = 第三方收取手续费 + 平台收取的服务费
                                                            		BigDecimal fee_1 = BigDecimalParser.parse(configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_1_5)).add(new BigDecimal(2.00));
                                                            		BigDecimal fee_2 = BigDecimalParser.parse(configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_5_20)).add(new BigDecimal(2.00));
                                                            	%>
																<td><%=fee_1 %>
																	元/笔</td>
																<td><%=fee_2 %>
																	元/笔</td>
                                                            </tr>
                                                        </table>
                                                        <%} %>
                                                        <p>
                                                            <span class="highlight">温馨提示：</span>单日提现金额超过500万元请提前三个工作日通知<%=configureProvider.getProperty(SystemVariable.SITE_NAME) %>
                                                      			  ，以便我们尽快处理您的提现
                                                        </p>
                                                    </div>
                                                </div>
											</span> 
											<span class="f12 gray9">实际手续费以第三方为准</span>
										<%-- 	<span
                                            class="f12 gray9"> 提现费用将从您的<%if ("true".equals(configureProvider.getProperty(SystemVariable.TXSXF_KCFS))) { %>
												提现金额<%} else { %><%=configureProvider.getProperty(SystemVariable.SITE_NAME) %>账户余额<%} %>中扣除
											</span> --%>
                                    </div>
                                </li>
                                <li>
                                    <div class="til">实际支付金额：</div>
                                    <div class="info">
                                        <span class="red f18" id="paySum">0.00</span>元
                                    </div>
                                </li>
                                <%-- <li>
                                    <!-- <div class="til">预计到账时间：</div>
                                    <div class="info">
                                        <span class="fl">
                                            <%=DateParser.format(new Date(System.currentTimeMillis()+2*24*60*60*1000)) %>
                                        </span>
                                        <a class="ml20 tx_an1" href="javascript:void(0)"></a>
                                    </div> -->
                                    <div class="pop-con"
                                        style="margin-left: 365px; display: none;">
                                        <div class="fl pop-pic"></div>
                                        <div class="pop-info">1-2个工作日之内到账（遇双休日和法定节假日往后顺延！）</div>
                                    </div>
                                    <div class="clear"></div>
                                </li> --%>
                                <% if (isOpenPsd && !tg) { %>
                                <li>
                                    <div class="til">
                                        <span class="red">*</span> 交易密码：
                                    </div>
                                    <div class="info">
                                        <input
                                            id="passwordId" type="password" class="text"
                                            class="highlight"
                                            style="-moz-user-select: none;"
                                            onselectstart="return false;" ondragenter="return false;"
                                            onpaste="return false;" value="" autocomplete="off" maxlength="30"/>
                                        <input id="hdnPsdId" type="hidden" name="withdrawPsd"/>
                                        <a
                                                href="<%=safetymesgView %>" class="blue blue_line">找回密码？</a>

                                        <p errortip class="red"></p>
                                    </div>
                                </li>
                                <% } %>
                            </ul>
                            <div class="tc">
                                <input type="submit" style="cursor: pointer;" value="提现"
                                       class="btn01"/>
                            </div>
                        </div>
                    </div>
                    <%} %>
                    <%if (t5023 != null && !StringHelper.isEmpty(t5023.F03)) {%>
                    <div class="mt20 lh24 htsc_pic">
                        <span class="highlight">温馨提示：</span><br/>
                        <%StringHelper.format(out, t5023.F03, fileStore);%>
                    </div>
                    <%}%>
                </div>
            </div>
        </form>
    </div>
</div>
<%
    String infoMsg = controller.getPrompt(request, response, PromptLevel.INFO);
%>
<div id="dialog" style="<%=StringHelper.isEmpty(infoMsg)?"display: none;":"" %>">
    <div class="popup_bg"></div>
    <div class="dialog">
        <div class="title"><a href="javascript:void(0);" class="out" onclick="closeDiv()"></a>提示</div>
        <div class="content">
            <div class="tip_information">
                <div class="doubt"></div>
                <div class="tips">
                    <p class="f20 gray33" id="con_error"><%=StringHelper.isEmpty(infoMsg) ? "" : infoMsg %>
                    </p>
                </div>
            </div>
            <div class="tc mt20">
                <a class="btn01"
                   href="javascript:void(0);" onclick="closeDiv()">确定</a>
            </div>
        </div>
    </div>
</div>

<div id="dialogRz" style="display: none;">
    <div class="popup_bg"></div>
    <div class="dialog">
        <div class="title"><a href="javascript:void(0);" class="out" onclick="closeDivRz()"></a>提示</div>
        <div class="content">
            <div class="tip_information">
                <div class="doubt"></div>
                <div class="tips">
                    <span class="f20 gray3" id="rz_con_error"></span>
                </div>
            </div>
            <div class="tc mt20"><a href="<%=safetymesgView%>" target="_blank" class="btn01"
                                    onclick="closeDivRz()">去认证</a></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var p1 = <%=configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_1_5) %>;
    var p2 = <%=configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_5_20) %>;
    var min = <%=configureProvider.getProperty(SystemVariable.WITHDRAW_MIN_FUNDS) %>;
    var max = <%=configureProvider.getProperty(SystemVariable.WITHDRAW_MAX_FUNDS) %>;
    var punWay = '<%=configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_WAY) %>';
    var punProportion = <%=configureProvider.getProperty(SystemVariable.WITHDRAW_POUNDAGE_PROPORTION) %>;
    var txsxfkcfs = '<%=configureProvider.getProperty(SystemVariable.TXSXF_KCFS) %>';
    var ye = '<%=t6101.F06%>';
    var mYxrz = '<%=configureProvider.getProperty(PayVariavle.CHARGE_MUST_EMAIL) %>';
    var mPhone = '<%=configureProvider.getProperty(PayVariavle.CHARGE_MUST_PHONE) %>';
    var mRealName = '<%=configureProvider.getProperty(PayVariavle.CHARGE_MUST_NCIIC) %>';
    var mWithPsd = '<%=configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD) %>';
    var rn = 0;
    var sfFlg = '';
    var sjFlg = '';
    var yxFlg = '';
    var jyFlg = '';
    var _tg = "<%= tg %>";
    <%if(t6118 != null){%>
    sfFlg = '<%=t6118.F02%>';
    sjFlg = '<%=t6118.F03%>';
    yxFlg = '<%=t6118.F04%>';
    jyFlg = '<%=t6118.F05%>';
    <%}%>
    <%if(realName){%>
    rn = 1;
    <%}%>
</script>
<%@include file="/WEB-INF/include/footer.jsp" %>
<%@include file="/WEB-INF/include/dialog.jsp" %>
<script type="text/javascript"
        src="<%=controller.getStaticPath(request)%>/js/huifu/withdraw.js"></script>
<script type="text/javascript"
        src="<%=controller.getStaticPath(request)%>/js/amountUtil.js"></script>
<script type="text/javascript"
        src="<%=controller.getStaticPath(request)%>/js/security.js"></script>
<script type="text/javascript">
    function addCard() {
        if (rn == 0) {
            var str = "";
            if (mRealName == 'true' && sfFlg == 'BTG') {
                str += "实名认证、";
            }
            if (mPhone == 'true' && sjFlg == 'BTG') {
                str += "手机号认证、";
            }
            if (mYxrz == 'true' && yxFlg == 'BTG') {
                str += "邮箱认证、";
            }
            if (_tg == 'false' && mWithPsd == 'true' && jyFlg == 'WSZ') {
                str += "设置交易密码、";
            }
            str = str.substring(0, str.length - 1)
            var msg = "添加银行卡必须先<span class='red'>" + str + "</span>，请到<a href='<%configureProvider.format(out,safeManage.getSafetymsgView());%>'  class='blue'><%=baseInfoPageName%></a>进行认证";

            $("#rz_con_error").html(msg);
            $("#dialogRz").show();
            return;
        }
        global_art = art.dialog.open(
                "<%=controller.getViewURI(request, Addbankcard.class)%>", {
                    id: 'addCard',
                    title: '添加银行卡',
                    opacity: 0.1,
                    width: 700,
                    height: 520,
                    lock: true,
                    close: function () {
                        window.location.reload();
                    }
                }, false);
    }
    var len = <%=cards==null?0:cards.length %>;

    function closeDiv() {
        $("#dialog").hide();
    }
    function closeDivRz() {
        $("#dialogRz").hide();
    }
    var _isOpenPsd = "<%= isOpenPsd %>";
    function onSubmit() {
        if (_tg == 'false' && _isOpenPsd == 'true') {
            var modulus = "<%=modulus%>", exponent = "<%=exponent%>";
            var key = RSAUtils.getKeyPair(exponent, '', modulus);
            $("#hdnPsdId").val(RSAUtils.encryptedString(key, $("#passwordId").val()));
            $("#passwordId").val(RSAUtils.encryptedString(key, $("#passwordId").val()));
        }
        return onSubmit1();
    }

    //限制只能输入数字
    function onlyNumber(obj) {
        var isNotNumber = isNaN($(obj).val());
        if (isNotNumber) {//如果不是数字
            $(obj).val("");
        }
        $(obj).nextAll("p[errortip]").hide();
        $(obj).nextAll("p[tip]").html(chinaCost($(obj).val())).show();
    }

</script>
</body>
</html>