<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.S61.entities.T6122"%>
<%@page import="com.dimeng.util.ObjectHelper" %>
<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
</head>
<body>
<%
    T6122 t6122 = ObjectHelper.convert(request.getAttribute("t6122"), T6122.class);
%>
<img src="<%=fileStore.getURL(t6122.F02)%>" />
</body>
</html>