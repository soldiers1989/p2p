<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.S62.enums.T6230_F20" %>
<%@page import="com.dimeng.p2p.modules.bid.console.service.entity.Hkjllb" %>
<%@page import="com.dimeng.p2p.S62.enums.T6230_F13" %>
<%@page import="com.dimeng.p2p.S62.enums.T6230_F11" %>
<%@page import="com.dimeng.p2p.S62.entities.T6230" %>
<%@page import="com.dimeng.p2p.S61.enums.T6110_F06" %>
<%@page import="com.dimeng.util.ObjectHelper" %>
<%@page import="com.dimeng.p2p.console.servlets.AbstractConsoleServlet" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.zjgl.fksh.*" %>
<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
    <%@include file="/WEB-INF/include/script.jsp" %>
</head>
<body>
<%
    CURRENT_CATEGORY = "CWGL";
    CURRENT_SUB_CATEGORY = "FKGL";
    PagingResult<Hkjllb> result = ObjectHelper.convert(request.getAttribute("hkjllbs"), PagingResult.class);
    Hkjllb[] hkjllbs = result.getItems();
    T6110_F06 userType = EnumParser.parse(T6110_F06.class, request.getAttribute("userType").toString());
    T6230 t6230 = ObjectHelper.convert(request.getAttribute("t6230"), T6230.class);
    int loanId = IntegerParser.parse(request.getParameter("loanId"));
    int userId = IntegerParser.parse(request.getParameter("userId"));
%>
<div class="right-container">
    <div class="viewFramework-body">
      <div class="viewFramework-content">
                <div class="p20">
                    <div class="border">
                        <div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>查看借款信息
                        </div>
                    </div>
                    <!--切换栏目-->
                    <div class="border mt20">
                        <div class="tabnav-container">
                            <ul class="clearfix pr pr60">
                                <li>
                                    <a href="<%=controller.getURI(request, ViewProject.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">项目信息</a></li>
                                <%if (userType == T6110_F06.ZRR) {%>
                                <li>
                                    <a href="<%=controller.getURI(request, ViewUserInfo.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">个人信息</a></li>
                                <li>
                                    <a href="<%=controller.getURI(request, DetailAuthentication.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">个人认证信息</a></li>
                                <%}%>

                                <%if (userType == T6110_F06.FZRR) {%>
                                <li>
                                    <a href="<%=controller.getURI(request, ViewEnterprise.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">企业信息</a></li>
                                <%}%>
                                <%if (T6230_F13.S == t6230.F13) {%>
                                <li>
                                    <a href="<%=controller.getURI(request, ViewDyw.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">抵押物信息</a></li>
                                <%}%>
                                <%if (T6230_F11.S == t6230.F11) {%>
                                <li>
                                    <a href="<%=controller.getURI(request, ViewGuarantee.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">担保信息</a></li>
                                <%}%>
                                <li>
                                    <a href="<%=controller.getURI(request, DetailAnnexMsk.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">附件(马赛克)</a></li>
                                <li>
                                    <a href="<%=controller.getURI(request, DetailAnnexWz.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">附件(完整版)</a></li>

                                <%if (t6230.F20 != T6230_F20.SQZ && t6230.F20 != T6230_F20.DSH && t6230.F20 != T6230_F20.DFB && t6230.F20 != T6230_F20.YFB) {%>
                                <li>
                                    <a href="<%=controller.getURI(request, ViewRecord.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn-click">投资记录<i class="icon-i tab-arrowtop-icon"></i></a></li>
                                <%}%>
                                <li><a href="javascript:void(0);" class="tab-btn-click  select-a">还款计划<i
                                        class="icon-i tab-arrowtop-icon"></i></a></li>
                                <%if (t6230.F20 != T6230_F20.SQZ && t6230.F20 != T6230_F20.DSH && t6230.F20 != T6230_F20.DFB && t6230.F20 != T6230_F20.YFB && t6230.F20 != T6230_F20.YLB) {%>
                                <li><a href="<%=controller.getURI(request, ViewProgresBidInfo.class)%>?loanId=<%=loanId%>&userId=<%=userId%>"
                                       class="tab-btn ">项目动态</a></li>
                                <%}%>
                                <li class="pa right0 top0 mt5"><input type="button" value="返回"
                                                                       class="btn btn-blue radius-6 pl20 pr20 ml40 mr10"
                                                                       onclick="window.location.href='<%=configureProvider.format(URLVariable.FKSHLIST_URL)%>'">
                                </li>
                            </ul>
                        </div>
                        <form id="form_loan"
                              action="<%=controller.getURI(request, ViewHkRecord.class)%>"
                              method="get">
                            <input name="userId" value="<%=request.getParameter("userId") %>" type="hidden"/>
                            <input name="loanId" value="<%=request.getParameter("loanId") %>" type="hidden"/>

                            <div class="tab-content-container p20">
                                <table class="table table-style gray6 tl">
                                    <thead>
                                    <tr class="title">
                                        <th class="tc">合约还款日期</th>
                                        <th class="tc">状态</th>
                                        <th class="tc">科目</th>
                                        <th class="tc">金额</th>
                                        <th class="tc">实际还款日期</th>
                                    </tr>
                                    </thead>
                                    <tbody class="f12">
                                    <%
                                        if (hkjllbs != null && hkjllbs.length > 0) {
                                            int i = 1;
                                            for (Hkjllb hkjllb : hkjllbs) {
                                                if (hkjllb == null) {
                                                    continue;
                                                }
                                    %>
                                    <tr class="tc">
                                        <td><%=DateParser.format(hkjllb.F02) %>
                                        </td>
                                        <td><%=hkjllb.F03.getChineseName()%>
                                        </td>
                                        <td><%StringHelper.filterHTML(out, hkjllb.F05);%></td>
                                        <td><%=Formater.formatAmount(hkjllb.F01)%>
                                        </td>
                                        <td><%=DateParser.format(hkjllb.F04)%>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <tr>
                                        <td colspan="5" class="tc">暂无数据</td>
                                    </tr>
                                    <%} %>
                                    </tbody>
                                </table>
                                <%AbstractConsoleServlet.rendPagingResult(out, result); %>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>