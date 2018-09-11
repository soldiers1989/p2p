<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.escrow.huifu.entity.query.UserAcctQueryEntity"%>
<%@page import="com.dimeng.p2p.escrow.huifu.service.WithdrawManage" %>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.userbalance.UserList" %>

<%@page import="java.math.BigDecimal"%>

<html>
<head>
<%@include file="/WEB-INF/include/meta.jsp"%>
<%@include file="/WEB-INF/include/style.jsp"%>
</head>
<body>
<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "YHYECX";
	
	UserAcctQueryEntity entity = (UserAcctQueryEntity)request.getAttribute("entity");
	String isZrr = (String)request.getAttribute("isZrr");
	
    //查询用户第三方可取现金额 ----由于取现未冻结金额，所以到第三方查询到的可取现金额未减去上一次取现金额，所以不能展示
    BigDecimal canCashAmt = BigDecimal.ZERO;
    if("ZRR".equals(isZrr))
    {
        WithdrawManage withdrawManage = serviceSession.getService(WithdrawManage.class);
        canCashAmt = withdrawManage.QueryCashBalanceBg(entity.UsrCustId);
    } 

%>
<div class="right-container">
	<div class="viewFramework-body">
      	<div class="viewFramework-content">
      		<div class="p20">
          		<div class="border">
            		<div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>用户余额查询
            			<div class="fr mt5 mr10">
							  <input type="button" class="btn btn-blue2 radius-6 pl20 pr20"
									 onclick="location.href='<%=controller.getURI(request, UserList.class) %>'" value="返回">
						</div>
            		</div>
					<div class="content-container pl40 pt30 pr40">
              			<ul class="gray6 input-list-container clearfix">
			                <li><span class="display-ib mr5">用户账户号：<%=entity.UsrCustId==null ? "" : entity.UsrCustId %></span></li>
						</ul>
						<ul class="gray6 input-list-container clearfix">
							<li><span class="display-ib mr5">账户余额：<%=entity.AcctBal == null ? 0 : entity.AcctBal %></span></li>
						</ul>
						<ul class="gray6 input-list-container clearfix">
							<li><span class="display-ib mr5">可用余额：<%=entity.AvlBal == null ? 0 : entity.AvlBal%></span></li>
						</ul>
						<ul class="gray6 input-list-container clearfix">
							<li><span class="display-ib mr5">冻结余额：<%=entity.FrzBal == null ? 0 : entity.FrzBal %></span></li>
						</ul>	
						<%if("ZRR".equals(isZrr)){ %>
						<ul class="gray6 input-list-container clearfix">
							<li><span class="display-ib mr5">可取现金额：<%=Formater.formatAmount(canCashAmt)%></span></li>
						</ul>
						<%} %>				
					</div>
				</div>
			</div>
      	</div>
	</div>
</div>
</body>
</html>