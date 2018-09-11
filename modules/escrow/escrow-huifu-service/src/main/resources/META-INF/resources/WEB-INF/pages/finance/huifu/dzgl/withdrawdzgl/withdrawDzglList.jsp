<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.withdrawdzgl.WithdrawDzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.withdrawdzgl.WithdrawDzglQuery"%>
<%@page import="com.dimeng.p2p.escrow.huifu.service.TrfQueryManage"%>
<%@page import="com.dimeng.p2p.escrow.huifu.entity.withdraw.WithdrawItem"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.AbstractFinanceServlet" %>
<%@page import="com.dimeng.p2p.S65.enums.T6501_F03"%>
<%@page import="com.dimeng.p2p.OrderType"%>
<%@page	import="com.dimeng.p2p.modules.account.console.service.entity.Order"%>
<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
    <%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>

<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "TXDZ";
	PagingResult<Order> pagingResult = (PagingResult<Order>) request.getAttribute("pagingResult");
%>
<div class="right-container">
    <div class="viewFramework-body">
      	<div class="viewFramework-content">
      		<form action="<%controller.getURI(request, WithdrawDzglList.class);%>" method="post" name="form1" id="searchForm">
				<div class="box box1 mb15">
					<div class="p20">
          				<div class="border">
            				<div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>提现对账</div>
            				<div class="content-container pl40 pt30 pr40">
              					<ul class="gray6 input-list-container clearfix">
              						<li>
										<span class="display-ib mr5">用户名：</span> 
										<input type="text" name="txuserName" value="<%StringHelper.filterHTML(out, request.getParameter("txuserName"));%>" class="text border pl5 mr20"  />
									</li>
					                <li>
					                	<span class="display-ib mr5">订单号：</span>
										<input type="text" class="text border pl5 mr20" name="orderId" value="<%StringHelper.filterQuoter(out, request.getParameter("orderId")); %>" />
									</li>
									<li>
										<span class="display-ib mr5">状态：</span>
										<select name="txStatus" class="border mr20 h32 mw100">
											<option value="0">全部</option>
											<option value="<%=T6501_F03.CG.name()%>" <%if(T6501_F03.CG.name().equals(request.getParameter("txStatus"))) {%>selected="selected" <%}%>><%=T6501_F03.CG.getChineseName()%></option>
											<option value="<%=T6501_F03.DTJ.name()%>" <%if(T6501_F03.DTJ.name().equals(request.getParameter("status"))) {%>selected="selected" <%}%>><%=T6501_F03.DTJ.getChineseName()%></option>
											<option value="<%=T6501_F03.DQR.name()%>" <%if(T6501_F03.DQR.name().equals(request.getParameter("txStatus"))) {%>selected="selected" <%}%>><%=T6501_F03.DQR.getChineseName()%></option>
											<option value="<%=T6501_F03.SB.name()%>" <%if(T6501_F03.SB.name().equals(request.getParameter("txStatus"))) {%>selected="selected" <%}%>><%=T6501_F03.SB.getChineseName()%></option>
										</select>
									</li>
									<li>
										<span class="display-ib mr5">提现时间：</span>
										    <input type="text" name="startDate"
                                               value="<%StringHelper.filterHTML(out, request.getParameter("startDate")); %>"
                                               id="datepicker1" readonly="readonly" class="text border pl5 w120 mr20 date"/>至
                                       		<input type="text" name="endDate"
                                               value="<%StringHelper.filterHTML(out, request.getParameter("endDate")); %>"
                                               id="datepicker2" readonly="readonly" class="text border pl5 w120 mr20 date"/>
									</li>
									<li>
										<a href="javascript:search();" class="btn btn-blue radius-6 mr5 pl1 pr15"><i class="icon-i w30 h30 va-middle search-icon "></i>搜索</a> 
									</li>
								</ul>
            				</div>
          				</div>
          				<div class="border mt20 table-container">
          				<table class="table table-style gray6 tl">
			              	<thead>
			                <tr class="title tc">
							<th>序号</th>
							<th>订单号</th>
							<th>用户名</th>
							<th>类型</th>
							<th>提现金额(元)</th>
							<th>状态</th>
							<th>提现时间</th>
							<th>提交时间</th>
							<th>完成时间</th>
							<th>操作</th>
							</tr>
							</thead>
							<tbody class="f12">
							<%
							    Order[] items = pagingResult.getItems();
							    if (items.length > 0) {
								    int i=0;
									for(Order order : items) {
									     if (order == null) {
                                             continue;
                                         }
							%>
							<tr class="dhsbg tc">
								<td><%=++i%></td>
								<td><%=order.F01%></td>
								<td><%StringHelper.filterHTML(out, order.userName);%></td>
								<td><%=OrderType.getTypeName(order.F02)==null?"":OrderType.getTypeName(order.F02)%></td>
								<td><%=Formater.formatAmount(order.amount)%></td>
								<td><%=order.F03.getChineseName()%></td>
								<td><%=DateTimeParser.format(order.F04)%></td>
								<td><%=DateTimeParser.format(order.F05)%></td>
								<td><%=DateTimeParser.format(order.F06)%></td>
								<td>
                                     <%if (dimengSession.isAccessableResource(WithdrawDzglQuery.class)){ %>
										<a target="_blank" onclick="goDZ('<%=order.F01 %>')" class="blue ml5">去对账</a>
									<%}else{ %>
                                     	<a class="disabled mr20">去对账</a>
                                     <%} %>
								</td>
							</tr>
							<%		}
								}else {
                            %>
                            <tr>
                                <td colspan="10" class="tc">暂无数据</td>
                            </tr>
                            <%	} %>
							</tbody>
						</table>
						</div>
						<%AbstractFinanceServlet.rendPagingResult(out, pagingResult);%>
					</div>
				</div>
			</form>
      	</div>
	</div>
</div>

<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>
<div class="popup_bg hide"></div>
<div id="info"></div>

<%
    String warring = controller.getPrompt(request, response, PromptLevel.WARRING);
    if (!StringHelper.isEmpty(warring)) {
%>
	<script type="text/javascript">
	    $("div.popup_bg").show();
	    $("#info").html(showDialogInfoReload(warring, "wrong"));
	</script>
<%
    }
%>	

<%@include file="/WEB-INF/include/datepicker.jsp" %>
<script type="text/javascript">
function search(){
	document.getElementsByName("paging.current")[0].value=1;
	$("#searchForm").submit();
}

function goDZ(orderNo){
	$.ajax({
		type:"post",
		url:"/console/finance/huifu/dzgl/withdrawdzgl/withdrawDzglQuery.htm?ordId=" + escape(orderNo),
		dataType: 'text',
	    success: succFunction,
		error: erryFunction
	})
	function erryFunction() {  
	    $("div.popup_bg").show();
	    $("#info").html(showDialogInfo("系统异常，请稍后重试！", "error"));
    }  
	function succFunction(msg) { 
		var message = msg.substring(0,2);
		if (message == "OK"){
		    $("div.popup_bg").show();
		    $("#info").html(showDialogInfo(msg.substring(2), "yes"));
		} else {
		    $("div.popup_bg").show();
		    $("#info").html(showDialogInfo(msg, "wrong"));
            //$('#form1').submit();
//				$("#dialog").show();
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