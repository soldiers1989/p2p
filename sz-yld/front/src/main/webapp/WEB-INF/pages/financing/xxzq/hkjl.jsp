<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.S62.enums.T6230_F13"%>
<%@page import="com.dimeng.p2p.modules.bid.front.service.entity.Hkjllb" %>
<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <title>线下债权转让详情-<%=configureProvider.getProperty(SystemVariable.SITE_TITLE)%>
    </title>
    <%@include file="/WEB-INF/include/style.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<div class="contain clearfix">
    <%@include file="/WEB-INF/include/xxzq/header.jsp" %>

    <div class="plan_tab clearfix">
        <ul>
            <li>
                <a href="<%=controller.getPagingItemURI(request, com.dimeng.p2p.front.servlets.financing.xxzq.Bdxq.class, id)%>">标的详情</a>
            </li>
            <%if (creditInfo.F11 == T6230_F11.S) { %>
            <li>
                <a href="<%=controller.getPagingItemURI(request, com.dimeng.p2p.front.servlets.financing.xxzq.Dbxx.class, id)%>">风控信息</a>
            </li>
            <%} %>
            <%
                if (creditInfo.F13 == T6230_F13.S) {
            %>
            <li>
                <a href="<%=controller.getPagingItemURI(request, com.dimeng.p2p.front.servlets.financing.xxzq.Dyxx.class, id)%>">抵押物信息</a>
            </li>
            <%
                }
            %>
            <li>
                <a href="<%=controller.getPagingItemURI(request, com.dimeng.p2p.front.servlets.financing.xxzq.Xgwj.class, id)%>">相关文件</a>
            </li>
            <li class="hover">还款计划</li>
            <li>
                <a href="<%=controller.getPagingItemURI(request, com.dimeng.p2p.front.servlets.financing.xxzq.Tbjl.class, id)%>">投资记录</a>
            </li>
            <li style="border-left: 1px solid #d1dfea; padding: 0;"></li>
        </ul>
    </div>

    <%
        Hkjllb[] alsoMoneys = investManage.getHk(id);
    %>
    <div class="contain_main clearfix " id="con_one_2">
        <div class="plan_tab_con01 clearfix">

            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tc">
                <tr class="leve_titbj">
                    <td width="330">合约还款日期</td>
                    <td width="156">状态</td>
                    <td width="362">科目</td>
                    <td width="362">金额</td>
                    <td width="362">实际还款日期</td>
                </tr>
                <%
                    if (alsoMoneys != null && alsoMoneys.length > 0) {
                        for (Hkjllb alsoMoney : alsoMoneys) {
                            if (alsoMoney == null) {
                                continue;
                            }
                %>
                <tr>
                    <td><%=DateParser.format(alsoMoney.F02) %>
                    </td>
                    <td width="330"><%=alsoMoney.F03.getChineseName() %>
                    </td>
                    <td width="156"><%=alsoMoney.F05%>
                    </td>
                    <td width="362"><%=alsoMoney.F01 %>元</td>
                    <td width="362"><%=alsoMoney.F04 == null ? "--" : DateParser.format(alsoMoney.F04) %>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </div>

</div>
<%@include file="/WEB-INF/include/footer.jsp" %>
<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/financing/sbtz.js"></script>
<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>

<%
    String message = controller.getPrompt(request, response, PromptLevel.INFO);
    if (!StringHelper.isEmpty(message)) {
%>
<script type="text/javascript">
    $("#info").html(showSuccInfo("<%=message%>", "succeed", $("#zqSucc").val()));
    $("div.popup_bg").show();
</script>
<%
    }
%>

<%
    String errorMessage = controller.getPrompt(request, response, PromptLevel.ERROR);
    if (!StringHelper.isEmpty(errorMessage)) {

%>
<script type="text/javascript">
    $("#info").html(showDialogInfo("<%=errorMessage%>", "error"));
    $("div.popup_bg").show();
</script>
<%
    }
%>

<%
    String warnMessage = controller.getPrompt(request, response, PromptLevel.WARRING);
    if (!StringHelper.isEmpty(warnMessage)) {
%>
<script type="text/javascript">
    $("#info").html(showDialogInfo("<%=warnMessage%>", "perfect"));
    $("div.popup_bg").show();
</script>
<%
    }
%>

</body>
</html>