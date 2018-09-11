<%@page import="com.dimeng.p2p.account.user.service.BankCardManage" %>
<%@page import="com.dimeng.p2p.account.user.service.TxManage" %>
<%@page import="com.dimeng.p2p.account.user.service.entity.BankCard" %>
<%@page import="com.dimeng.p2p.common.enums.BankCardStatus" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@ page import="com.dimeng.p2p.user.servlets.account.Deletebankcard" %>
<%@ page import="com.dimeng.p2p.user.servlets.account.Bankcardlist" %>
<%@ page import="com.dimeng.p2p.user.servlets.account.Addbankcard" %>
<%@ page import="com.dimeng.p2p.user.servlets.account.Editbankcard" %>
<%@ page import="com.dimeng.p2p.user.servlets.account.SafetymsgFZRR" %>
<%@include file="/WEB-INF/include/authenticatedSession.jsp" %>
<%@page import="com.dimeng.p2p.S61.entities.T6118" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F02" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F03" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F04" %>
<%@page import="com.dimeng.p2p.S61.enums.T6118_F05" %>
<%@page import="com.dimeng.p2p.variables.defines.pays.PayVariavle" %>
<%@page import="com.dimeng.p2p.service.SafetymsgViewManage" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title><%=configureProvider.getProperty(SystemVariable.SITE_NAME)%>
    </title>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%
    BankCardManage bankCardManage = serviceSession.getService(BankCardManage.class);
    BankCard[] card = bankCardManage.getBankCars(BankCardStatus.QY.name());
    String iserror = "";
    TxManage manage = serviceSession.getService(TxManage.class);
    SafetymsgViewManage safeManage = serviceSession.getService(SafetymsgViewManage.class);
    if (!manage.getVerifyStatus() || !manage.getVerifyTradingPsw()) {
        iserror = "1";
    }
    T6118 t6118 = manage.getVerifyEntity();    //获取认证情况

    UserManage usrManage = serviceSession.getService(UserManage.class);
    String usrCustId = usrManage.getUsrCustId();

    UserInfoManage userInfoManage = serviceSession.getService(UserInfoManage.class);
    T6110 t6110_tmp = userInfoManage.getUserInfo(serviceSession.getSession().getAccountId());
    String safetymesgView = configureProvider.format(URLVariable.USER_ZRR_NCIIC);
    if (t6110_tmp.F06 == T6110_F06.FZRR) {
        safetymesgView = controller.getViewURI(request, SafetymsgFZRR.class);
    }

    boolean tg = BooleanParser.parse(configureProvider.getProperty(SystemVariable.SFZJTG));

    CURRENT_CATEGORY = "ZHGL";
    CURRENT_SUB_CATEGORY = "YHKXX";

    Map<String, String> bankMap = new HashMap<String, String>();
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
 	// 增加对托管的区分
    String escrow = configureProvider.getProperty(SystemVariable.ESCROW_PREFIX);
%>

<div class="main_bg clearfix">
    <div class="user_wrap w1002 clearfix">
        <%@include file="/WEB-INF/include/menu.jsp" %>
        <div class="r_main">
            <div class="user_mod">
                <div class="user_til">
                    <i class="icon"></i><span class="gray3 f18">银行卡管理</span>
                </div>
                <%if (tg && StringHelper.isEmpty(usrCustId)) { %>
                <div class="f16 mt30 pt50 pb50 tc ml30 mr30" style="background: #f3f3f3;">
                   	您需要在第三方托管平台上进行注册，才可管理银行卡！请
                   	<a href="<%=configureProvider.format(URLVariable.OPEN_ESCROW_GUIDE) %>"
                    class="red">立即注册</a>！
                </div>
                <%} else { %>
                <ul class="bank_manage_list clearfix">
                    <%
                        for (BankCard b : card) {
                            if (bankMap.containsKey(b.Bankname)) {
                    %>
                    <li>
                        <div class="pic">
                            <img src="<%=controller.getStaticPath(request)%>/images/bank_logo_<%=bankMap.get(b.Bankname) %>.png"
                                    width="148" height="38">
                        </div>
                        <div class="number">
                            <%StringHelper.filterHTML(out, b.BankNumber.substring(0, 4) + " *** *** " + b.BankNumber.substring(b.BankNumber.length() - 4, b.BankNumber.length()));%>
                        </div>
                        <div class="delete clearfix">
                           	<%if (tg) { %>
                            	<a href="javascript:void(-1);" onclick="deleteBankcard(<%=b.id %>);" class="fr">删除</a>
                            <%} else { %>
	                            <% if (b.jbRequestNo == null || b.jbRequestNo.isEmpty()) { %>
	                            	<a href="javascript:void(-1);" onclick="deletecard(<%=b.id %>);" class="fr">删除</a>
                               <%} else { %>
	                            	<span class="fc red">解绑中</span>
	                            <%} %>
                            <%} %>
                        </div>
                    </li>
                    <%} else {%>
                    <li>
                        <div class="pic"
                             title="<%StringHelper.filterHTML(out, b.Bankname);%>">
                            <%StringHelper.truncation(out, b.Bankname, 6, "***");%>
                        </div>
                        <div class="number">
                            <%
                                StringHelper.filterHTML(out, b.BankNumber.substring(0, 4) + " *** *** " + b.BankNumber.substring(b.BankNumber.length() - 4, b.BankNumber.length()));%>
                        </div>
                        <div class="delete clearfix">
                            <%if (tg) { %>
                            	<a href="<%=configureProvider.format(URLVariable.ESCROW_URL_UNBINDCARD) %>?id=<%=b.id %>"
                                class="fr">删除</a>
                            <%} else { %>
	                            <% if (b.jbRequestNo == null || b.jbRequestNo.isEmpty()) { %>
	                            <a href="javascript:void(-1);" onclick="deletecard(<%=b.id %>);"
	                               class="fr">删除</a>
	                               <%} else { %>
	                            <span class="fc red">解绑中</span>
	                            <%} %>
                            <%} %>
                        </div>
                    </li>
                    <%
                            }
                        }
                    %>
                    <%if (card.length < IntegerParser.parse(configureProvider.getProperty(SystemVariable.MAX_BANKCARD_COUNT))) {%>
                    <%if (tg) { %>
                    <li class="add"><a target="_blank"
                                       href="<%=configureProvider.format(URLVariable.ESCROW_URL_BINDCARD) %>">
                        <i class="add_ico"></i>

                        <p>添加银行卡</p>
                    </a></li>
                    <%} else { %>
                    <li class="add"><a href="javascript:void(-1);"
                                       onclick="addCard(0);"> <i class="add_ico"></i>

                        <p>添加银行卡</p>
                    </a></li>
                    <%} %>
                    <%}%>
                    <div class="clear"></div>
                </ul>
                <%} %>
            </div>
            <div class="mt20 mb30" style="padding:15px 20px; background:#f8f8f8; line-height:24px;">
            <span class="highlight">温馨提示：</span><br>
            1.如果您填写的开户支行不正确，可能将无法成功提现，由此产生的提现费用将不予返还。<br>
            2.如果您不确定开户行支行名称，可打电话到所在地银行的营业网点询问或上网查询。<br>
            3.不支持提现至信用卡账户。
            </div>
            <%
                String mYxrz = configureProvider.getProperty(PayVariavle.CHARGE_MUST_EMAIL);
                String mPhone = configureProvider.getProperty(PayVariavle.CHARGE_MUST_PHONE);
                String mRealName = configureProvider.getProperty(PayVariavle.CHARGE_MUST_NCIIC);
                String mWithPsd = configureProvider.getProperty(PayVariavle.CHARGE_MUST_WITHDRAWPSD);

                String msg = "";
                if (BooleanParser.parse(mRealName) && t6118.F02.equals(T6118_F02.BTG)) {
                    msg += "实名认证、";
                }
                if (BooleanParser.parse(mPhone) && t6118.F03.equals(T6118_F03.BTG)) {
                    msg += "手机号认证、";
                }
                if (BooleanParser.parse(mYxrz) && t6118.F04.equals(T6118_F04.BTG)) {
                    msg += "邮箱认证、";
                }
                if (!tg && BooleanParser.parse(mWithPsd) && t6118.F05.equals(T6118_F05.WSZ)) {
                    msg += "设置交易密码、";
                }
                if (!StringHelper.isEmpty(msg)) {
                    String baseInfoPageName = T6110_F06.ZRR == t6110.F06 ?"个人基础信息":"安全信息";
                    msg = msg.substring(0, msg.length() - 1);
            %>

            <div id="smrz" style="display: none;">
                <div class="popup_bg"></div>
                <div class="dialog ">
                    <div class="title"><a href="javascript:void(-1);" class="out" onclick="hidebg('smrz');"></a>认证提示
                    </div>
                    <div class="content">
                        <div class="tip_information">
                            <div class="doubt"></div>
                            <div class="tips">
                                <p class="f20 gray33">添加银行卡必须先<span class="red"><%=msg %></span>,请到<a
                                        href="<%=safetymesgView%>" class="blue"><%=baseInfoPageName%></a>进行认证
                            </div>
                        </div>
                        <div class="tc mt20">
                            <a class="btn01"
                               href="<%=safetymesgView%>"
                               target="_blank" onclick="hidebg('smrz')">去认证</a>
                        </div>
                    </div>
                </div>
            </div>
            <%} %>

        </div>
        <div class="clear"></div>

    </div>
</div>
<div class="dialog" style="display: none;" id="delc">
    <div class="title"><a href="javascript:void(0);" class="out close"></a>提示</div>
    <div class="content">
        <div class="tip_information">
            <div class="doubt"></div>
            <div class="tips">
                <input type="hidden" name="delId" id="delId">
                <span class="f20 gray3">是否确认删除银行卡?</span>
            </div>
        </div>
        <div class="tc mt20">
        	<%if (tg && escrow.equals("yeepay")) { %>
				<%for (BankCard b : card) {%>
	        	<a href="<%=configureProvider.format(URLVariable.ESCROW_URL_UNBINDCARD) %>?id=<%=b.id %>" class="btn01">是</a>
	        	<%} %>
        	<%} else { %>
        		<a href="javascript:okDelete();" class="btn01">是</a>
        	<%} %>
        	<a href="javascript:void(0);" class="btn01 btn_gray ml20 close">否</a>
        </div>
    </div>
</div>

<div class="popup_bg" style="display: none;"></div>
<div id="info"></div>
<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>
<%
    String message = controller.getPrompt(request, response, PromptLevel.WARRING);
    if (!StringHelper.isEmpty(message)) {
%>
<script type="text/javascript">
    $(".c").show();
    $("#info").html(showDialogInfo('<%=message%>', "doubt"));
</script>
<%} %>
<%
    String messageInfo = controller.getPrompt(request, response, PromptLevel.INFO);
    if (!StringHelper.isEmpty(messageInfo)) {
%>
<script type="text/javascript">
    $(".popup_bg").show();
    $("#info").html(showDialogInfo('<%=messageInfo%>', "yes"));
</script>
<%} %> 

<%@include file="/WEB-INF/include/footer.jsp" %>
<%@include file="/WEB-INF/include/dialog.jsp" %>
<script type="text/javascript"
        src="<%=controller.getStaticPath(request)%>/js/validation.js"></script>
<script type="text/javascript">
    function deletecard(id) {
        $(".popup_bg").show();
        $("#delId").val(id);
        $("#delc").show();
        <%-- artDialog.confirm("是否确认删除银行卡?",function(){
            var data={"value":id};
            $.ajax({
                type:"post",
                dataType:"html",
                url:"<%=controller.getURI(request, Deletebankcard.class)%>",
                data:data,
                success:function(data){
                    location.href="<%=controller.getViewURI(request, Bankcardlist.class)%>";
                }
            });
        },function(){}); --%>
    }

    function deleteBankcard(id)
    {
    	var url='<%=configureProvider.format(URLVariable.ESCROW_URL_UNBINDCARD)%>?id=' + id;
        $(".popup_bg").show();
        $("#info").html(showForwardInfo("确定删除此银行卡？","question",url));
    }
    
    function okDelete() {
        var id = $("#delId").val();
        var data = {"value": id};
        $.ajax({
            type: "post",
            dataType: "html",
            url: "<%=controller.getURI(request, Deletebankcard.class)%>",
            data: data,
            success: function (data) {
                location.href = "<%=controller.getViewURI(request, Bankcardlist.class)%>";
            }
        });
    }

    $(".close").click(function () {
        $("div.dialog").hide();
        $("div.popup_bg").hide();
    });

    function addCard(id) {
        if ("1" == "<%=iserror%>") {
            $("#smrz").show();
            return false;
        }

        global_art = art.dialog.open("<%=controller.getViewURI(request, Addbankcard.class)%>", {
            id: 'addCard',
            title: '添加银行卡',
            opacity: 0.1,
            width: 700,
            height: 520,
            padding: 0,
            lock: true,
            close: function () {
                window.location.reload();
            }

        }, false);
    }

    function updateCard(id) {
        if ("1" == "<%=iserror%>") {
            $("#smrz").show();
            return false;
        }

        global_art = art.dialog.open("<%=controller.getViewURI(request, Editbankcard.class)%>?id=" + id, {
            id: 'addCard',
            title: '修改银行卡',
            opacity: 0.1,
            width: 700,
            height: 520,
            lock: true,
            close: function () {
                window.location.reload();
            }

        }, false);
    }

    function hidebg(id) {
        $("#" + id).hide();
    }

</script>
</body>
</html>
