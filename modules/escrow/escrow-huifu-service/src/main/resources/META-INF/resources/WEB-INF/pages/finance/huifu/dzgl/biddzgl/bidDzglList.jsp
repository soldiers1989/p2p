<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.S65.enums.T6501_F03"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.biddzgl.BidDzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.biddzgl.BidDzglQuery"%>
<%@page import="com.dimeng.p2p.OrderType"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.AbstractFinanceServlet"%>
<%@page	import="com.dimeng.p2p.modules.account.console.service.entity.Order"%>
<html>
<head>
	<%@include file="/WEB-INF/include/meta.jsp"%>
	<%@include file="/WEB-INF/include/style.jsp"%>
	<%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>
	<%
		CURRENT_CATEGORY = "CWGL";
		CURRENT_SUB_CATEGORY = "TBDZ";		
		PagingResult<Order> pagingResult = (PagingResult<Order>)request.getAttribute("pagingResult");		
	%>
<div class="right-container">
    <div class="viewFramework-body">
      	<div class="viewFramework-content">
      		<form action="<%controller.getURI(request, BidDzglList.class);%>" method="post" name="form1" id="searchForm">
				<div class="box box1 mb15">
					<div class="p20">
          				<div class="border">
            				<div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>投标对账</div>
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
											<option value="<%=T6501_F03.DQR.name()%>" <%if(T6501_F03.DQR.name().equals(request.getParameter("txStatus"))) {%>selected="selected" <%}%>><%=T6501_F03.DQR.getChineseName()%></option>
											<option value="<%=T6501_F03.SB.name()%>" <%if(T6501_F03.SB.name().equals(request.getParameter("txStatus"))) {%>selected="selected" <%}%>><%=T6501_F03.SB.getChineseName()%></option>
										</select>
									</li>
									<li>
										<span class="display-ib mr5">投标时间：</span>
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
								<th>冻结订单号</th>
								<th>用户名</th>
								<th>类型</th>
								<th>投标金额(元)</th>
								<th>状态</th>
								<th>投标时间</th>
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
								<td class="tc"><%=++i%></td>
								<td class="tc"><%=order.F01%></td>
								<td class="tc"><%=order.freezeOrdId %></td>
								<td class="tc"><%StringHelper.filterHTML(out, order.userName);%></td>
								<td class="tc"><%=OrderType.getTypeName(order.F02)==null?"":OrderType.getTypeName(order.F02)%></td>
								<td class="tc"><%=Formater.formatAmount(order.amount)%></td>
								<td class="tc"><%=order.F03.getChineseName()%></td>
								<td class="tc"><%=DateTimeParser.format(order.F04)%></td>
								<td class="tc"><%=DateTimeParser.format(order.F05)%></td>
								<td class="tc"><%=DateTimeParser.format(order.F06)%></td>
								<td class="tc">
                                     <%if (dimengSession.isAccessableResource(BidDzglQuery.class)){ %>
										<a target="_blank" onclick="goDZ('/console/finance/huifu/dzgl/biddzgl/bidDzglQuery.htm?ordId=<%=order.F01 %>&freezeOrdId=<%=order.freezeOrdId %>')" class="blue ml5">去对账</a>
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
<div id="info"></div>
<script type="text/javascript"  src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>
	<%@include file="/WEB-INF/include/datepicker.jsp"%>
	<script type="text/javascript">
		$(function() {
			$("#datepicker1").datepicker({
				inline: true,
				onSelect : function(selectedDate) {
	                $("#datepicker2").datepicker("option", "minDate", selectedDate);  }
			});
		    $('#datepicker1').datepicker('option', {dateFormat:'yy-mm-dd'});
		    $("#datepicker2").datepicker({inline: true});
		    $('#datepicker2').datepicker('option', {dateFormat:'yy-mm-dd'});
		    $("#datepicker1").datepicker("setDate", "<%StringHelper.filterHTML(out, request.getParameter("startDate"));%>");
		    $("#datepicker2").datepicker("setDate", "<%StringHelper.filterHTML(out, request.getParameter("endDate"));%>");
		    $("#datepicker2").datepicker('option', 'minDate',$("#datepicker1").datepicker().val());
		});
		function search(){
			document.getElementsByName("paging.current")[0].value=1;
			$("#searchForm").submit();
		}
		
		function goDZ(url){
			$.ajax({
				type:"post",
				url: url,
				//data:{"requestNo" : requestNo,"transType": transType},
				dataType: 'text',
			    success: succFunction,
				error: erryFunction
			})
			function erryFunction() {  
				$(".popup_bg").show();
			    $("#info").html(showDialogInfo("系统异常！", "wrong"));
		    }  
			function succFunction(msg) {
				var message = msg.substring(0,2);
				if (message == "OK"){
					$(".c").show();
				    $("#info").html(showDialogInfo(msg.substring(2), "yes"));
				} else {
					$(".popup_bg").show();
				    $("#info").html(showDialogInfo(msg, "wrong"));
				    //window.location.reload();
				}
		    }  
		}
	</script>
</body>
</html>