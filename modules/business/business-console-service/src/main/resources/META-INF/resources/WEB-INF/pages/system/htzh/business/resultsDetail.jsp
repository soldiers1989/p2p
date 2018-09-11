<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.framework.http.entity.RoleBean"%>
<%@page import="com.dimeng.p2p.console.servlets.AbstractConsoleServlet"%>
<%@page import="com.dimeng.p2p.repeater.business.entity.Performance"%>
<%@page import="com.dimeng.p2p.repeater.business.entity.Results"%>
<%@page import="com.dimeng.util.ObjectHelper"%>
<%@ page import="com.dimeng.p2p.console.servlets.system.htzh.business.*" %>
<%@ page import="com.dimeng.p2p.modules.business.console.service.achieve.BusTradeType" %>
<html>
<head>
	<%@include file="/WEB-INF/include/meta.jsp"%>
<%@include file="/WEB-INF/include/style.jsp"%>
<%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>
	<%
		PagingResult<Results> result = (PagingResult<Results>) request.getAttribute("result");
	RoleBean[] roleBeans = ObjectHelper.convertArray(
			request.getAttribute("roles"), RoleBean.class);
	Double loanAmount = (Double)request.getAttribute("loanTotalAmount");
	Double investAmount = (Double)request.getAttribute("investTotalAmount");
		Double chargeAmount = (Double)request.getAttribute("chargeTotalAmount");
		Double withdrawAmount = (Double)request.getAttribute("withdrawTotalAmount");
	%>
	<%
		CURRENT_CATEGORY = "XTGL";
		CURRENT_SUB_CATEGORY = "YWYGL";
		Performance performance = ObjectHelper.convert(request.getAttribute("performance"),Performance.class);
		String createTimeStart=request.getParameter("createTimeStart");
		String createTimeEnd=request.getParameter("createTimeEnd");
	%>
<div class="right-container">
    <div class="viewFramework-body">
      <div class="viewFramework-content">
					<form action="<%=controller.getURI(request,ResultsDetail.class)%>" method="post" name="form1" id="form1">
					<input type="hidden" name="employNum" value="<%=performance.employNum%>"/>
					<div class="p20">
						<div class="border">
							<div class="title-container">
								<i class="icon-i w30 h30 va-middle title-left-icon"></i>业绩明细
								<div class="fr mt5 mr10">
									<input type="button" onclick="location.href='<%=controller.getURI(request,BusinessUserList.class)%>'" class="btn btn-blue2 radius-6 pl20 pr20 ml40" value="返回" />
								</div>
							</div>
							<div class="content-container pl40 pt30 pr40">
              					<ul class="gray6 input-list-container clearfix">
									<li>
										<span class="display-ib mr5">业务员：</span>
										<span class="link-blue"><%StringHelper.filterHTML(out, performance.name);%></span></li>
									<li class="ml50">
										<span class="display-ib mr5">名下客户数 ：</span>
										<span class="link-blue"><%=performance.levelCustomerNumber+performance.secondaryCustomerNumber%></span></li>
									<li class="ml50">
										<span class="display-ib mr5">用户累计投资金额 ：</span>
										<span class="link-blue"><%=Formater.formatAmount(performance.levelInvestmentAmount+performance.secondaryInvestmentAmount)%></span>元
									</li>
									<li class="ml50">
										<span class="display-ib mr5">用户累计借款金额 ：</span>
										<span class="link-blue"><%=Formater.formatAmount(performance.levelLoanAmount+performance.secondaryLoanAmount)%></span>元
									</li>
									<li class="ml50">
										<span class="display-ib mr5">用户累计充值金额 ：</span>
										<span class="link-blue"><%=Formater.formatAmount(performance.levelChargeAmount+performance.secondChargeAmount)%></span>元
									</li>
									<li class="ml50">
										<span class="display-ib mr5">用户累计提现金额 ：</span>
										<span class="link-blue"><%=Formater.formatAmount(performance.levelWithDrawAmount+performance.secondWithDrawAmount)%></span>元
									</li>
								</ul>
							</div>
						</div>
						
						<div class="border mt20">
				          	<div class="content-container pl40 pt30 pr40">
				              <ul class="gray6 input-list-container clearfix">
								  <li><span class="display-ib mr5">客户用户名： </span> <input
										  type="text" name="customName"
										  value="<%StringHelper.filterQuoter(out,request.getParameter("customName"));%>"
										  class="text border pl5 mr20" /></li>
								  <li><span class="display-ib mr5">客户姓名： </span> <input
										  type="text" name="customRealName"
										  value="<%StringHelper.filterQuoter(out,request.getParameter("customRealName"));%>"
										  class="text border pl5 mr20" /></li>
								  <%--<li>
                                    <span class="display-ib mr5">项目ID：</span>
                                    <input type="text" name="project" value="<%StringHelper.filterHTML(out, request.getParameter("project"));%>" class="text border pl5 mr20" />
                                </li>--%>
								  <li>
									  <span class="display-ib mr5">客户级别：</span>
									  <select name="nameLevel" class="border mr10 h32 mw100">
										  <option value="">--全部--</option>
										  <option value="一级用户" <%if("一级用户".equals(request.getParameter("nameLevel"))){ %>selected="selected"<%} %>>一级用户</option>
										  <option value="二级用户" <%if("二级用户".equals(request.getParameter("nameLevel"))){ %>selected="selected"<%} %>>二级用户</option>
									  </select>
								  </li>
								  <li>
									  <span class="display-ib mr5">交易类型：</span>
									  <select name="tradeType" class="border mr10 h32 mw100">
										  <option value="">--全部--</option>
										  <%
										  for(BusTradeType tradeType : BusTradeType.values()){
										  %>
										  <option value="<%=tradeType.name()%>" <%if(tradeType.name().equals(request.getParameter("tradeType"))){ %>selected="selected"<%} %>><%=tradeType.getChineseName()%></option>
										  <%}%>
									  </select>
								  </li>
								  <li>
									  <span class="display-ib mr5">成交时间：</span>
									  <input type="text" name="createTimeStart" id="datepicker1" readonly="readonly" class="text border pl5 w120 date" />
									  <span class="pl5 pr5">至</span>
									  <input type="text" name="createTimeEnd" id="datepicker2" readonly="readonly" class="text border pl5 w120 date mr20" />
								  </li>
								  <li><span class="display-ib mr5">所属一级用户： </span> <input
										  type="text" name="belongLevel"
										  value="<%StringHelper.filterQuoter(out,request.getParameter("belongLevel"));%>"
										  class="text border pl5 mr20" /></li>
							
								<li class="ml10"><a href="javascript:$('#form1').submit();" class="btn btn-blue radius-6 mr5 pl1 pr15"><i class="icon-i w30 h30 va-middle search-icon "></i>搜索</a></li>
								<li>
									<%if (dimengSession.isAccessableResource(ExportResultsDetail.class)) {%>
										<a href="javascript:toOption('<%=controller.getURI(request,ExportResultsDetail.class)%>','<%=performance.employNum%>')" class="btn btn-blue radius-6 mr5  pl1 pr15"><i class="icon-i w30 h30 va-middle export-icon "></i>导出</a>
									<%}else{ %>
										<span class="btn btn-gray radius-6 mr5 pl1 pr15"><i class="icon-i w30 h30 va-middle export-icon "></i>导出</span>
									<%} %> 
								</li>
							</ul>
						</div>
					</div>
					<div class="border mt20">
				    <div class="tabnav-container">
					  <ul class="clearfix">
					    <li><a href="<%=controller.getURI(request,PerformanceStatistics.class)%>?employNum=<%=performance.employNum%>" class="tab-btn-click">业绩统计<i class="icon-i tab-arrowtop-icon"></i></a></li>
					    <li><a href="javascript:void(0);"  class="tab-btn-click select-a"><i class="icon-i tab-arrowtop-icon"></i>业绩明细</a></li>
				      </ul>
					</div>	
					<div class=" table-container p20">
			            <table class="table table-style gray6 tl">
			              <thead>
			                <tr class="title tc">
								<th>序号</th>
								<th>用户名</th>
								<th>姓名</th>
								<th>客户级别</th>
								<th>所属一级用户</th>
								<th>成交金额(元)</th>
								<th>交易类型</th>
								<%--<th>订单ID</th>--%>
								<th>成交时间</th>
								<th>备注</th>
							</tr>
							</thead>
								<tbody class="f12">
								<%
									  Results[] results = result.getItems();
											if (results != null) {
												int i = 1;
												for (Results r : results) {
													if (r == null) {
														continue;
													}
									%>
									<tr class="tc">
										<td><%=i++%></td>
										<td><%StringHelper.filterHTML(out, r.customName);%>
										</td>

										<td>
											<%StringHelper.filterHTML(out, r.customRealName);%>
										</td>
										
										
										<td><%=r.namelevel%></td>
										<td><%StringHelper.filterHTML(out, r.firstCustomName);%></td>
										<td><%=Formater.formatAmount(r.amount)%></td>
										<td><%=BusTradeType.valueOf(r.tradeType).getChineseName()%></td>
										<%--<td><%=r.orderId > 0 ? r.orderId : ""%></td>--%>
										<td><%=DateTimeParser.format(r.showTime,"yyyy-MM-dd HH:mm:ss") %></td>
										<td><%StringHelper.filterHTML(out, (r.tradeType.equals("invest") || r.tradeType.equals("loan") ? "标的ID："+r.projectID : ""));%></td>
										<%
											}
												}else{%>
								<tr><td colspan="9"class="tc">暂无数据</td></tr>
								<%} %>
								</tbody>
							</table>
						</div>
						</div>
						<div class="clear"></div>
							<div class="mb10">
								<span class="mr30">投资金额共计：<em class="red"><%=Formater.formatAmount(investAmount)%></em> 元</span>
								<span class="mr30">借款金额共计：<em class="red"><%=Formater.formatAmount(loanAmount)%></em> 元</span>
								<span class="mr30">充值金额共计：<em class="red"><%=Formater.formatAmount(chargeAmount)%></em> 元</span>
								<span class="mr30">提现金额共计：<em class="red"><%=Formater.formatAmount(withdrawAmount)%></em> 元</span>
							</div>
							<%
								AbstractConsoleServlet.rendPagingResult(out, result);
							%>
							<div class="box2 clearfix"></div>
						</div>
					</form>
				</div>
			</div>
		</div>

	<div id="info"></div>
	<div class="popup_bg"  style="display: none;"></div>
	<%@include file="/WEB-INF/include/datepicker.jsp"%>
	<script type="text/javascript" src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>
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
		    <%if(!StringHelper.isEmpty(createTimeStart)){%>
		   		$("#datepicker1").val("<%StringHelper.filterHTML(out, request.getParameter("createTimeStart"));%>");
		   	<%}%>
		   	<%if(!StringHelper.isEmpty(createTimeEnd)){%>
		    	$("#datepicker2").val("<%StringHelper.filterHTML(out, request.getParameter("createTimeEnd"));%>");
		    <%}%>
		    $("#datepicker2").datepicker('option', 'minDate',$("#datepicker1").datepicker().val());
		});
	 
	 function toOption(url,empNum){
			$("#form1").attr("action",url);
			$("input[name='employNum']").val(empNum);
			$("#form1").submit();
			$("#form1").attr("action",'<%=controller.getURI(request,ResultsDetail.class)%>');
		}
</script> 
</body>
</html>