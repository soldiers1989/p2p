<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.dimeng.p2p.variables.defines.URLVariable"%>
<html dir="ltr" xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" lang="zh-CN">
<head>
<%@include file="/WEB-INF/include/meta.jsp"%>
<title>访问受限</title>
</head>
<body style="background:#e7e6eb;">
<div style="width:599px; height:306px; background:url('<%=controller.getStaticPath(request)%>/images/404.jpg') no-repeat; margin:150px auto 0 auto; text-align:center; font-size:14px; font-family:Microsoft yahei; padding-top:180px; line-height:28px; color:#666;">
访问受限！<br />
请联系客服 <%configureProvider.format(out,SystemVariable.SITE_CUSTOMERSERVICE_TEL);%> <br />
管理员为网站给您带来不便致以诚挚的歉意！
</div>
</body>
</html>