<%@page import="com.dimeng.p2p.S61.entities.T6162" %>
<%@page import="com.dimeng.p2p.account.user.service.QyBaseManage" %>
<%@page import="com.dimeng.p2p.user.servlets.account.QyBases" %>
<%@page import="com.dimeng.p2p.user.servlets.account.Qyjszl" %>
<%@page import="com.dimeng.p2p.user.servlets.account.Qycwzk" %>
<%@page import="com.dimeng.p2p.user.servlets.account.Qylxxx" %>
<%@page import="com.dimeng.p2p.user.servlets.account.Qyccxx" %>
<%@page import="com.dimeng.p2p.user.servlets.account.Qyfcxx" %>
<%@include file="/WEB-INF/include/authenticatedSession.jsp" %>
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
    QyBaseManage manage = serviceSession.getService(QyBaseManage.class);
    T6162 qy = manage.getQyjs();
    if (qy == null) {
        qy = new T6162();
    }
    CURRENT_CATEGORY = "ZHGL";
    CURRENT_SUB_CATEGORY = "QYJCXX";
%>
<div class="contain clearfix">
    <div class="user_top"></div>
    <div class="about">
        <%@include file="/WEB-INF/include/menu.jsp" %>
        <div class="w780 container fr">
            <div>
                <div class="newsbox">
                    <div class="til clearfix Men_bt">
                        <div class="Menubox">
                            <ul>
                                <li style="border-right: 1px #d7dfe3 solid;"><a
                                        href="<%=controller.getURI(request, QyBases.class) %>">基础信息</a></li>
                                <li class="hover"><a style="color: #fff;"
                                                     href="<%=controller.getURI(request, Qyjszl.class) %>">介绍资料</a></li>
                                <li style="border-right: 1px #d7dfe3 solid;"><a
                                        href="<%=controller.getURI(request, Qycwzk.class) %>">财务状况</a></li>
                                <li style="border-right: 1px #d7dfe3 solid;"><a
                                        href="<%=controller.getURI(request, Qylxxx.class) %>">联系信息</a></li>
                                <li style="border-right: 1px #d7dfe3 solid;"><a
                                        href="<%=controller.getURI(request, Qyccxx.class) %>">车产信息</a></li>
                                <li style="border-right: 1px #d7dfe3 solid;"><a
                                        href="<%=controller.getURI(request, Qyfcxx.class) %>">房产信息</a></li>
                            </ul>
                        </div>
                    </div>

                    <%--<div class="fl jc_l">&nbsp;</div>--%>
                    <div class="jc_z fl pt20" id="jcxx">
                        <ul class="fl" style="width:400px; padding-left:200px; ">
                            <li>
                                <div class="til"><span class="red">*</span>企业介绍：</div>
                                <div class="info"><%StringHelper.filterHTML(out, qy.F02); %>&nbsp;</div>
                                <div class="clear"></div>
                            </li>
                            <li>
                                <div class="til"><span class="red">*</span>经营情况：</div>
                                <div class="info"><%StringHelper.filterHTML(out, qy.F03); %>&nbsp;</div>
                                <div class="clear"></div>
                            </li>
                            <li>
                                <div class="til"><span class="red">*</span>涉诉情况：</div>
                                <div class="info"><%StringHelper.filterHTML(out, qy.F04); %>&nbsp;</div>
                                <div class="clear"></div>
                            </li>
                            <li>
                                <div class="til"><span class="red">*</span>征信情况：</div>
                                <div class="info"><%StringHelper.filterHTML(out, qy.F05); %>&nbsp;</div>
                                <div class="clear"></div>
                            </li>
                            <li>
                                <div class="til"><span class="red"></span>&nbsp;</div>
                                <div class="info">&nbsp;</div>
                                <div class="clear"></div>
                            </li>
                            <li>
                                <div class="til"><span class="red"></span>&nbsp;</div>
                                <div class="info">&nbsp;</div>
                                <div class="clear"></div>
                            </li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<%@include file="/WEB-INF/include/footer.jsp" %>
<%@include file="/WEB-INF/include/dialog.jsp" %>
</body>
</html>