<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.console.servlets.base.optsettings.jylx.JylxList"%>
<%@page import="com.dimeng.p2p.console.servlets.base.optsettings.jylx.ViewJylx" %>
<%@page import="com.dimeng.p2p.S51.entities.T5122" %>
<html>
<link href="<%=controller.getStaticPath(request) %>/css/hhmmss.css" rel="stylesheet">
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
    <%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>
<%
    CURRENT_CATEGORY = "JCXXGL";
    CURRENT_SUB_CATEGORY = "JYLX";
    T5122 entity = (T5122) request.getAttribute("entity");
%>
<div class="right-container">
    <div class="viewFramework-body">
      <div class="viewFramework-content">
                <!--加载内容-->
                <div class="p20">
                    <form action="<%=controller.getURI(request, ViewJylx.class)%>" method="post" class="form1">
                        <div class="border">
                            <div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>修改交易类型
                            </div>
                            <div class="content-container pl40 pt30 pr40">
                                <ul class="cell noborder yxjh ">
                                    <li class="mb10">
                                        <span class="display-ib w200 tr mr5"><span class="red">*</span>类型名称：</span>
                                        <input name="F02" type="text" class="text border w300 yw_w5 required"
                                               maxlength="20" value="<%StringHelper.filterHTML(out, entity.F02);%>"/>
                                        <span tip></span>
                                        <span errortip class="fl" style="display: none"></span>
                                    </li>
                                    <li class="mb10">
                                        <div class="pl200 ml5">
                                            <input type="hidden" name="F01" value="<%=entity.F01%>"/>
                                            <input type="submit" class="btn btn-blue2 radius-6 pl20 pr20 sumbitForme"
                                                   fromname="form1" value="保存"/>
                                            <input type="button"
                                                   onclick="window.location.href='<%=controller.getURI(request, JylxList.class)%>'"
                                                   class="btn btn-blue2 radius-6 pl20 pr20 ml40" value="返回"/>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </form>
                </div>
                <!--加载内容 结束-->
            </div>
        </div>
    </div>
    <!--右边内容 结束-->
<!--内容-->

<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/validation.js"></script>
<%
    String message = controller.getPrompt(request, response, PromptLevel.INFO);
    if (!StringHelper.isEmpty(message)) {
%>

<script type="text/javascript">
    alert(message);
</script>
<%
    }
%>

<%
    String errorMessage = controller.getPrompt(request, response, PromptLevel.ERROR);
    if (!StringHelper.isEmpty(errorMessage)) {

%>
<script type="text/javascript">
    alert(errorMessage);
</script>
<%
    }
%>

<%
    String warnMessage = controller.getPrompt(request, response, PromptLevel.WARRING);
    if (!StringHelper.isEmpty(warnMessage)) {
%>
<script type="text/javascript">
    alert(warnMessage);
</script>
<%
    }
%>
</body>
</html>