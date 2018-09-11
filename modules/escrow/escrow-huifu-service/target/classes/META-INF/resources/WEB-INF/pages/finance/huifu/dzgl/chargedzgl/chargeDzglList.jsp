<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.chargedzgl.ChargeDzglQuery"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.AbstractFinanceServlet"%>
<%@page import="com.dimeng.p2p.escrow.huifu.entity.dzgl.CzdzEntity"%>
<%@page import="com.dimeng.p2p.S65.enums.T6501_F03"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.chargedzgl.ChargeDzglList"%>
<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
    <%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>
<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "CZDZ";
	PagingResult<CzdzEntity> result = (PagingResult<CzdzEntity>) request.getAttribute("result");
%>
<div class="right-container">
    <div class="viewFramework-body">
      <div class="viewFramework-content">
                <form action="<%=controller.getURI(request, ChargeDzglList.class)%>" method="post" name="form1" id="form1">
                    <div class="p20">
                        <div class="border">
                            <div class="title-container">
                                <i class="icon-i w30 h30 va-middle title-left-icon"></i>充值对账
                            </div>
                            <div class="content-container pl40 pt30 pr40">
                                <ul class="gray6 input-list-container clearfix">
                                    <li>
                                        <span class="display-ib mr5">用户名：</span>
                                        <input type="text" name="userName" id="userName" value="${userName}"  class="text border pl5 mr20"/>
                                    </li>
                                    <li>
                                        <span class="display-ib mr5">流水号：</span>
                                        <input type="text" name="TrxId" id="LoanNo" value="${TrxId}" class="text border pl5 mr20"/>
                                    </li>
                                    <li>
                                        <span class="display-ib mr5">订单号：</span>
                                        <input type="text" name="OrdId" id="OrderNo" value="${OrdId}" class="text border pl5 mr20"/>
                                    </li>
                                    <li>
										<span class="display-ib mr5">状态：</span>
										<select name="status" class="border mr20 h32 mw100">
											<option value="0">全部</option>
											<option value="<%=T6501_F03.CG.name()%>" <%if(T6501_F03.CG.name().equals(request.getParameter("status"))) {%>selected="selected" <%}%>><%=T6501_F03.CG.getChineseName()%></option>
											<option value="<%=T6501_F03.DTJ.name()%>" <%if(T6501_F03.DTJ.name().equals(request.getParameter("status"))) {%>selected="selected" <%}%>><%=T6501_F03.DTJ.getChineseName()%></option>
											<option value="<%=T6501_F03.DQR.name()%>" <%if(T6501_F03.DQR.name().equals(request.getParameter("status"))) {%>selected="selected" <%}%>><%=T6501_F03.DQR.getChineseName()%></option>
											<option value="<%=T6501_F03.SB.name()%>" <%if(T6501_F03.SB.name().equals(request.getParameter("status"))) {%>selected="selected" <%}%>><%=T6501_F03.SB.getChineseName()%></option>
										</select>
									</li>
                                    <li>
                                        <span class="display-ib mr5">充值时间：</span>
                                        <input type="text" name="startDatetime"
                                               value="<%StringHelper.filterHTML(out, request.getParameter("startDatetime")); %>"
                                               id="datepicker1" readonly="readonly" class="text border pl5 w120 mr20 date"/>至
                                        <input type="text" name="endDatetime"
                                               value="<%StringHelper.filterHTML(out, request.getParameter("endDatetime")); %>"
                                               id="datepicker2" readonly="readonly" class="text border pl5 w120 mr20 date"/>
                                    </li>
                                    <li><a href="javascript:$('#form1').submit();"
                                           class="btn btn-blue radius-6 mr5 pl1 pr15"><i
                                            class="icon-i w30 h30 va-middle search-icon "></i>搜索</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="mt20 table-container">
                            <table class="table table-style gray6 tl">
                                <thead>
                                <tr class="title tc">
                                	<th>序号</th>
                                	<th>订单号</th>
									<th>用户名</th>
									<th>充值金额(元)</th>
									<th>充值时间</th>
									<th>完成时间</th>
									<th>流水号</th>
									<th>状态</th>
									<th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="f12">
                                <%
                                CzdzEntity[] txdzs = result.getItems();
                                if (txdzs.length > 0) {
									int i = 0;
									for (CzdzEntity txdz : txdzs) {
										if (txdz == null) {
											continue;
										}
                                %>
                                <tr class="dhsbg tc">
                                	<td><%=++i%></td>
									<td><%=txdz.F01 %></td>
									<td><%=txdz.UserName %></td>
									<td><%=Formater.formatAmount(txdz.Amount) %></td>
									<td><%=DateTimeParser.format(txdz.F04,"yyyy-MM-dd HH:mm")%></td>
									<%if(txdz.F06 != null){ %>
										<td><%=DateTimeParser.format(txdz.F06,"yyyy-MM-dd HH:mm")%></td>
									<%}else { %>
										<td>&nbsp;</td>
									<%} %>
									<td><%=txdz.F10%></td>
									<td><%=txdz.F03.getChineseName()%></td>
									<td>
										<%if (dimengSession.isAccessableResource(ChargeDzglQuery.class)){ %>
											<a target="_blank" onclick="zdOrderNo(<%=txdz.F01%>)" class="blue ml5">去对账</a>
										<%}else{ %>
	                                     	<a class="disabled mr20">去对账</a>
	                                     <%} %>
	                                     <%-- <a target="_blank" href="/console/finance/huifu/dzgl/biddzgl/bidCancle.htm?ordId=<%=order.F01 %>&freezeOrdId=<%=order.freezeOrdId %>" class="blue ml5">撤销</a> --%>
									</td>
								</tr>
                                <% }
                                } else {
                                %>
                                <tr>
                                    <td colspan="9" class="tc">暂无数据</td>
                                </tr>
                                <%} %>
                                </tbody>
                            </table>
                        </div>
                           <%
                                AbstractFinanceServlet.rendPagingResult(out, result);
                            %>
                        <div class="clear"></div>
                        <div class="box2 clearfix"></div>
                    </div>
                </form>
            </div>
        </div>
</div>

<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>
<div class="popup_bg hide"></div>
<div id="info"></div>

<%@include file="/WEB-INF/include/datepicker.jsp" %>
<script type="text/javascript">
    function zdOrderNo(orderNo){
		$.ajax({
			type:"post",
			url:"/console/finance/huifu/dzgl/chargedzgl/chargeDzglQuery.htm?ordId=" + escape(orderNo),
			data:{"orderNo" : orderNo},
			dataType: 'text',
		    success: succFunction,
			error: erryFunction
		})
		function erryFunction() {  
		    $("div.popup_bg").show();
		    $("#info").html(showDialogInfo("系统异常，请稍后重试！", "error"));
	    }  
		function succFunction(msg) { 
			if (msg == "OK"){
			    $("div.popup_bg").show();
			    $("#info").html(showDialogInfo("对账成功！", "yes"));
			} else {
			    $("div.popup_bg").show();
			    $("#info").html(showDialogInfo(msg, "wrong"));
                //$('#form1').submit();
// 				$("#dialog").show();
			}
        }  
	}
</script>   
<script type="text/javascript">
    $(function () {
        $("#datepicker1").datepicker({inline: true});
        $('#datepicker1').datepicker('option', {dateFormat: 'yy-mm-dd'});
        $("#datepicker2").datepicker({inline: true});
        $('#datepicker2').datepicker('option', {dateFormat: 'yy-mm-dd'});
        $("#datepicker1").datepicker("setDate", "<%StringHelper.filterHTML(out, request.getParameter("startExpireDatetime"));%>");
        $("#datepicker2").datepicker("setDate", "<%StringHelper.filterHTML(out, request.getParameter("endExpireDatetime"));%>");
    });
</script>
</body>
</html>