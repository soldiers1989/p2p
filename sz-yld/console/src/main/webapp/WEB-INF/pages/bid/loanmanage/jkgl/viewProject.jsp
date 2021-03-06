<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.dfgl.YqddfList"%>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewProgresBidInfo"%>
<%@page import="com.dimeng.p2p.common.HighPrecisionFormater" %>
<%@page import="com.dimeng.p2p.modules.bid.console.service.entity.BidCheck" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.DetailAuthentication" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewHkRecord" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewRecord" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.LoanList" %>
<%@page import="com.dimeng.p2p.S61.enums.T6110_F06" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.DetailAnnexWz" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.DetailAnnexMsk" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewGuarantee" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewEnterprise" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewDyw" %>
<%@page import="com.dimeng.p2p.console.servlets.bid.loanmanage.jkgl.ViewUserInfo" %>
<%@page import="com.dimeng.util.ObjectHelper" %>
<%@ page import="com.dimeng.p2p.S62.enums.*" %>
<%@ page import="com.dimeng.p2p.S62.entities.*" %>
<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
    <%@include file="/WEB-INF/include/script.jsp" %>
</head>
<body>
<%
    CURRENT_CATEGORY = "YWGL";
    CURRENT_SUB_CATEGORY = "BDGL";
    String ckUrl = controller.getURI(request, LoanList.class);
    String operationJK = request.getParameter("operationJK");
    if (StringHelper.isEmpty(operationJK)) {
        operationJK = "CK";
    }else{
        CURRENT_CATEGORY = "CWGL";
        CURRENT_SUB_CATEGORY = "BLZQZRGLLIST";
        if("BLZQDZR".equals(operationJK))
        {
            ckUrl = "/console/finance/zjgl/blzq/blzqDzrList.htm";
        }else if("BLZQDSH".equals(operationJK)){
            ckUrl = "/console/finance/zjgl/blzq/blzqDshList.htm";
        }else if("BLZQZRZ".equals(operationJK)){
            ckUrl = "/console/finance/zjgl/blzq/blzqZrzList.htm";
        }else if("BLZQYZR".equals(operationJK)){
            ckUrl = "/console/finance/zjgl/blzq/blzqYzrList.htm";
        }else if("BLZQZRSB".equals(operationJK)){
            ckUrl = "/console/finance/zjgl/blzq/blzqZrsbList.htm";
        }
    }
    
    T6230 loan = ObjectHelper.convert(request.getAttribute("loan"), T6230.class);
    T6231 t6231 = ObjectHelper.convert(request.getAttribute("t6231"), T6231.class);
    T6230 t6230 = ObjectHelper.convert(request.getAttribute("t6230"), T6230.class);
    T6110_F06 userType = EnumParser.parse(T6110_F06.class, request.getAttribute("userType").toString());
    T6211[] t6211s = ObjectHelper.convertArray(request.getAttribute("t6211s"), T6211.class);
    T6238 t6238 = ObjectHelper.convert(request.getAttribute("t6238"), T6238.class);
    BidCheck[] bidChecks = ObjectHelper.convertArray(request.getAttribute("bidChecks"), BidCheck.class);
    String userName = ObjectHelper.convert(request.getAttribute("userName"), String.class);
    if (loan == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    int loanId = IntegerParser.parse(request.getParameter("loanId"));
    int userId = loan.F02;
    T6216 product = ObjectHelper.convert(request.getAttribute("product"), T6216.class);
    if (product == null) product = new T6216();
%>
<div class="right-container">
    <div class="viewFramework-body">
        <div class="viewFramework-content">
            <div class="p20">
                <div class="border">
                    <div class="title-container pr"><i class="icon-i w30 h30 va-middle title-left-icon"></i>借款管理-项目信息
                        <div class="pa right0 top5">
                            <input type="button" class="btn btn-blue2 radius-6 pl20 pr20 fr mr10" onclick="location.href='<%=ckUrl %>'" value="返回">
                        </div>
                    </div>
                </div>
                <div class="border mt20">
                    <div class="tabnav-container">
                        <ul class="clearfix">
                            <li><a href="javascript:void(0)" class="tab-btn select-a">项目信息<i class="icon-i tab-arrowtop-icon"></i></a></li>
                            <%
                                if (userType == T6110_F06.ZRR) {
                            %>
                            <li>
                                <a href="<%=controller.getURI(request, ViewUserInfo.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">个人信息</a></li>
                            <li>
                                <a href="<%=controller.getURI(request, DetailAuthentication.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">个人认证信息</a></li>
                            <%
                                }
                            %>
                            <%
                                if (userType == T6110_F06.FZRR) {
                            %>
                            <li>
                                <a href="<%=controller.getURI(request, ViewEnterprise.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">企业信息</a></li>
                            <%
                                }
                            %>
                            <%
                                if (T6230_F13.S == t6230.F13) {
                            %>
                            <li>
                                <a href="<%=controller.getURI(request, ViewDyw.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">抵押物信息</a></li>
                            <%} %>
                            <%
                                if (T6230_F11.S == t6230.F11) {
                            %>
                            <li>
                                <a href="<%=controller.getURI(request, ViewGuarantee.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">担保信息</a></li>
                            <%} %>
                            <li>
                                <a href="<%=controller.getURI(request, DetailAnnexMsk.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">附件(马赛克)</a></li>
                            <li>
                                <a href="<%=controller.getURI(request, DetailAnnexWz.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">附件(完整版)</a></li>
                            <%
                                if (t6230.F20 != T6230_F20.SQZ && t6230.F20 != T6230_F20.DSH && t6230.F20 != T6230_F20.DFB && t6230.F20 != T6230_F20.YFB && t6230.F20 != T6230_F20.YZF) {
                            %>
                            <li>
                                <a href="<%=controller.getURI(request, ViewRecord.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">投资记录</a></li>
                            <%if(t6230.F20 != T6230_F20.YLB){%>
                            <li>
                                <a href="<%=controller.getURI(request, ViewHkRecord.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                   class="tab-btn ">还款计划</a></li>
                            <li><a href="<%=controller.getURI(request, ViewProgresBidInfo.class)%>?loanId=<%=loanId%>&userId=<%=userId%>&operationJK=<%=operationJK%>"
                                       class="tab-btn ">项目动态</a></li>
                            <%}} %>
                        </ul>
                    </div>
                    <div class="tab-content-container p20">
                        <ul class="gray6">
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>产品名称：</span>
                                <%if (product.F02 != null) {%>
                                <%=product.F02 %>
                                <%}%>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>标的类型：</span>
                                <%
                                    if (t6211s != null) {
                                        for (T6211 t6211 : t6211s) {
                                            if (t6211 == null) {
                                                continue;
                                            }
                                            if (loan.F04 == t6211.F01) {
                                                StringHelper.filterHTML(out, t6211.F02);
                                                break;
                                            }
                                        }
                                    }
                                %>
                            </li>

                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>借款账户：</span>
                                <%StringHelper.filterHTML(out, userName); %>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>借款标题：</span>
                                <%StringHelper.filterHTML(out, loan.F03); %>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>借款金额：</span>
                                <%=Formater.formatAmount(loan.F05) %>元
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>标的属性：</span>
                                <input type="checkbox" disabled="disabled" name="F11"
                                       value="S" <%if (loan.F11 == T6230_F11.S) { %> checked="checked" <%} %>/>担保
                                <input type="checkbox" disabled="disabled" name="F13"
                                       value="S" <%if (loan.F13 == T6230_F13.S) { %> checked="checked" <%} %>/>抵押
                                <input type="checkbox" disabled="disabled" name="F14"
                                       value="S" <%if (loan.F14 == T6230_F14.S) { %> checked="checked" <%} %>/>实地认证
                                <input type="checkbox" disabled="disabled" name="F33"
                                       value="S" <%if (loan.F33 == T6230_F33.S) { %> checked="checked" <%} %>/>信用
                            </li>
                            <%
                                if (loan.F11 == T6230_F11.S) {
                            %>
                            <li id="dbfa" class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>担保方案：</span>
                                <%=loan.F12.getChineseName() %>
                            </li>
                            <%} %>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>新增标识：</span>
                                <%if ("F".equals(loan.xsb.name()) && "F".equals(t6231.F27.name())) { %>未标识<%} %>
                                <%if ("S".equals(loan.xsb.name())) { %>新手标<%} %>
                                <%if ("S".equals(t6231.F27.name())) { %>奖励标<%} %>
                            </li>
                            <%
                                if (T6231_F27.S.name().equals(t6231.F27.name())) {
                            %>
                            <li id="jlll" class="mb10">
                                <span class="display-ib w200 tr mr5"><em class="red pr5">*</em>奖励利率：</span>
                                <%=Formater.formatRate(t6231.F28) %>
                            </li>
                            <%} %>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>还款方式：</span>
                                <%=loan.F10.getChineseName() %>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>借款期限：</span>
                                <%
                                    if (T6231_F21.S == t6231.F21) {
                                        out.print(t6231.F22);
                                %>天
                                <%
                                } else {
                                    out.print(loan.F09);
                                %>个月<%} %>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>付息方式：</span>
                                <%=loan.F17.getChineseName() %>
                            </li>
                            <%
                                if (loan.F17 == T6230_F17.GDR) {
                            %>
                            <li id="fxr" class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>付息日：</span>
                                <%=loan.F18 %>号
                            </li>
                            <%} %>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>起息日：</span>
                                <%
                                    if (loan.F19 == 0) {
                                        StringHelper.filterHTML(out, "T+0");
                                    } else if (loan.F19 == 1) {
                                        StringHelper.filterHTML(out, "T+1");
                                    } else if (loan.F19 == 2) {
                                        StringHelper.filterHTML(out, "T+2");
                                    } else if (loan.F19 == 3) {
                                        StringHelper.filterHTML(out, "T+3");
                                    } else if (loan.F19 == 4) {
                                        StringHelper.filterHTML(out, "T+4");
                                    } else if (loan.F19 == 5) {
                                        StringHelper.filterHTML(out, "T+5");
                                    }
                                %>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>年化利率：</span>
                                <%=Formater.formatRate(loan.F06) %>
                            </li>

                            <li class="mb10"><span class="display-ib w200 tr mr5"><em class="red pr5">*</em>成交服务费率：</span>
                                <%=HighPrecisionFormater.toPlainString(t6238.F02) %>
                            </li>

                            <li class="mb10"><span class="display-ib w200 tr mr5"><em class="red pr5">*</em>投资管理费率：</span>
                                <%=HighPrecisionFormater.toPlainString(t6238.F03)%>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em class="red pr5">*</em>逾期罚息利率：</span>
                                <%=HighPrecisionFormater.toPlainString(t6238.F04) %>
                            </li>

                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>筹款期限：</span>
                                <%=loan.F08 %>天
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>起投金额：</span>
                                <%=Formater.formatAmount(t6231.F25) %>元
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em class="red pr5">*</em>最大投资金额：</span>
                                <%=Formater.formatAmount(t6231.F26) %>元
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em class="red pr5">*</em>项目所在区域：</span>
                                <%StringHelper.filterHTML(out, ObjectHelper.convert(request.getAttribute("region"), String.class)); %>
                            </li>
                            <%-- <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>还款来源：</span>
                                <%StringHelper.filterHTML(out, t6231.F16);%>
                            </li>
                            <li class="mb10" title="<%StringHelper.filterHTML(out, t6231.F08);%>"><span class="display-ib w200 tr mr5 fl"><em
                                    class="red pr5">*</em>借款用途：</span>
                                <div class="pl200 ml5"><%StringHelper.filterHTML(out, t6231.F08); %></div>
                            </li> --%>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>活动使用方式：</span>
                                <%=t6231.F36.getChineseName() %>
                            </li>
                            <li class="mb10"><span class="display-ib w200 tr mr5"><em
                                    class="red pr5">*</em>投资端：</span>
                                <%=t6231.F33.getChineseName() %>
                            </li>
                            <li class="mb10"><span
                                    class="display-ib w200 tr mr5 fl"><em class="red pr5">*</em>借款描述：</span>
                                <div class="pl200 ml5">
                                    <textarea id="textarea_id" cols="100" rows="8" style="width: 700px; height: 500px;"><%StringHelper.format(out, t6231.F09, fileStore);%></textarea>
                                </div>
                            </li>

                        </ul>
                    </div>
                    <div class="tab-item">

                        <table class="table table-style gray6 tl">
                            <thead>
                            <tr class="title tc">
                                <th>序号</th>
                                <th>审核人</th>
                                <th>反馈时间</th>
                                <th>状态</th>
                                <th>审核意见</th>
                            </tr>
                            </thead>
                            <tbody class="f12">
                            <%
                                if (bidChecks != null) {
                                    int i = 1;
                                    for (BidCheck bidCheck : bidChecks) {
                                        if (bidCheck == null) {
                                            continue;
                                        }
                            %>
                            <tr class="tc">
                                <td><%=i++%>
                                </td>
                                <td><%=bidCheck.name%>
                                </td>
                                <td>
                                    <%=DateTimeParser.format(bidCheck.F04)
                                    %>
                                </td>
                                <td><%=bidCheck.F05.getChineseName() %>
                                </td>
                                <td><%StringHelper.filterHTML(out, bidCheck.F06); %>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr class="tc">
                                <td colspan="5">没有记录</td>
                            </tr>
                            <%} %>

                            </tbody>
                        </table>

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/include/kindeditor.jsp" %>
<script type="text/javascript">
    KindEditor.ready(function(K) {
        window.editor = K.create('#textarea_id', {
            readonlyMode : true
        });
    });
</script>
</body>
</html>