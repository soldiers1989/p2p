<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo.UserStatus"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo.AutoBidCheck"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo.UserInfoList" %>
<%@page import="com.dimeng.p2p.S61.enums.T6110_F10"%>
<%@page import="com.dimeng.p2p.S61.enums.T6110_F06"%>
<%@page import="com.dimeng.p2p.S61.entities.T6110"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.AbstractFinanceServlet"%>

<html>
<head>
    <%@include file="/WEB-INF/include/meta.jsp" %>
    <%@include file="/WEB-INF/include/style.jsp" %>
    <%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>

<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "YHYECX";
	
	PagingResult<T6110> pagingResult = (PagingResult<T6110>)request.getAttribute("pagingResult");
%>
<div class="right-container">
    <div class="viewFramework-body">
      <div class="viewFramework-content">
                <form action="<%=controller.getURI(request, UserInfoList.class)%>" method="post" name="form1" id="form1">
                    <div class="p20">
                        <div class="border">
                            <div class="title-container">
                                <i class="icon-i w30 h30 va-middle title-left-icon"></i>开户信息同步
                            </div>
                            <div class="content-container pl40 pt30 pr40">
                                <ul class="gray6 input-list-container clearfix">
                                    <li>
                                        <span class="display-ib mr5">用户名：</span>
                                        <input type="text" name="userName"
                                               value="<%StringHelper.filterHTML(out, request.getParameter("userName")); %>"
                                               class="text border pl5 mr20"/>
                                    </li>
                                    <li>
                                        <span class="display-ib mr5">用户类型：</span>
                                        <select name="userTag" class="border mr20 h32 mw100">
                                            <option value="" <% if ("".equals(request.getAttribute("userTag"))) {%>
                                                    selected="selected"<%} %>>全部
                                            </option>
                                            <option value="ZRR" <%if ("ZRR".equals(request.getAttribute("userTag"))) {%>
                                                    selected="selected"<%} %>>个人
                                            </option>
                                            <option value="QY" <%if ("QY".equals(request.getAttribute("userTag"))) {%>
                                                    selected="selected"<%} %>>企业
                                            </option>
                                            <option value="JG" <%if ("JG".equals(request.getAttribute("userTag"))) {%>
                                                    selected="selected"<%} %>>机构
                                            </option>
                                        </select>
                                    </li>
									<li><a href="javascript:search();"
										class="btn btn-blue radius-6 mr5 pl1 pr15"><i
											class="icon-i w30 h30 va-middle search-icon "></i>搜索</a></li>
								</ul>
                            </div>
                        </div>
                        <div class="border mt20 table-container">
                            <table class="table table-style gray6 tl">
                                <thead>
                                <tr class="title">
                                	<th class="tc">用户ID</th>
									<th class="tc">用户名</th>
									<th class="tc">用户类型</th>
									<th class="tc">是否为担保方</th>
									<th class="tc">操作</th>
                                </tr>
                                </thead>
                                <tbody class="f12">
                                <%
	                                T6110[] items = pagingResult.getItems();
									if(items != null){
										for(T6110 item : items){
                                %>
                                <tr class="dhsbg">
									<td class="tc"><%=item.F01 %></td>
									<td class="tc"><%StringHelper.filterHTML(out, item.F02); %></td>
									<td class="tc">
										<%
                                        if (item.F06 == T6110_F06.ZRR) {
                                            out.print("个人");
                                        } else {
                                            if(T6110_F10.S == item.F10){
                                            	out.print("机构");
                                            }else{
                                                out.print("企业");
                                            }
                                        }
										%>
									</td>
									<td class="tc">
										<%
											if(item.F10 == T6110_F10.S){
												out.print("是");
											}else{
												out.print("否");
											}
										%>
									</td>
									<td class="tc">
									<%if (dimengSession.isAccessableResource(UserStatus.class)){ %>
										<a target="_self" class="blue ml5" href="<%=controller.getURI(request, UserStatus.class) %>?id=<%=item.F01 %>&type=<%=item.F06 %>">开户查询</a>
									<%}else{ %>
                                     	<a class="disabled mr20">开户查询</a>
                                     <%} %>
                                     <%if(item.F10 != T6110_F10.S){
                                         if (dimengSession.isAccessableResource(AutoBidCheck.class)){ 
                                      %>
										<a target="_blank" class="blue ml5" onclick="goDZ('/console/finance/huifu/tggl/userinfo/autoBidCheck.htm?id=<%=item.F01 %>&type=<%=item.F06 %>')">自动投标查询</a>
									<%}else{ %>
                                     	<a class="disabled mr20">自动投标查询</a>
                                     <%} } %>
									 </td>
								</tr>
                                <%
                                    }
                                } else {
                                %>
	                                <tr>
	                                    <td colspan="13" class="tc">暂无数据</td>
	                                </tr>
                                <%} %>
                                </tbody>
                            </table>
                            <%
                                AbstractFinanceServlet.rendPagingResult(out, pagingResult);
                            %>
                        </div>
                        <div class="clear"></div>
                        <div class="box2 clearfix"></div>
                    </div>
                </form>
            </div>
        </div>
</div>
<div id="info"></div>
<script type="text/javascript"  src="<%=controller.getStaticPath(request)%>/js/dialog.js"></script>
<script type="text/javascript">

function search()
{
	$("#form1").submit();
};

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
        alert("error");  
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