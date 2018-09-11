<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.escrow.huifu.entity.query.UserBankCardEntity"%>
<%@page import="com.dimeng.p2p.escrow.huifu.entity.bankcard.CardInfoEntity"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.usercard.UserCardList"%>
<%@page import="java.util.List"%>

<html>
<head>
<%@include file="/WEB-INF/include/meta.jsp"%>
<%@include file="/WEB-INF/include/style.jsp"%>
</head>
<body>
<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "YHKXXTB";
	
	UserBankCardEntity entity = (UserBankCardEntity)request.getAttribute("entity");
	String userTag = (String)request.getAttribute("userTag");
%>
<div class="right-container">
	<div class="viewFramework-body">
      	<div class="viewFramework-content">
      		<div class="p20">
          		<div class="border">
            		<div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>用户银行卡信息
	            		<div class="fr mt5 mr10">
							  <input type="button" class="btn btn-blue2 radius-6 pl20 pr20"
									 onclick="location.href='<%=controller.getURI(request, UserCardList.class) %>'" value="返回">
						</div>
					</div>
					<div class="content-container pl40 pt30 pr40">
						<%if (null != entity) { %>
							<ul class="gray6">
	              				<li class="mb10">
	              					<span class="display-ib w200 tr mr5">用户客户号：</span>
	                                <em class="red pr5"><%=entity.UsrCustId == null ? "" : entity.UsrCustId %></em>
	              				</li>              				
              				</ul>
              				<%if(null != entity.CardId){ %>
              				<ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">开户银行账号：</span>
	                                 	<em style="font-weight: bold;"><%=entity.CardId == null ? "" : entity.CardId %></em>
	                             </li>
                             </ul>
                             <%} %>
                            <%if(null == entity.UsrCardInfolist){ %>
              				 <ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">开户银行账号：</span>
	                                 	<em style="font-weight: bold;">尚未绑定银行卡</em>
	                             </li>
                             </ul>
              				<% } else {
              				    for (CardInfoEntity cardinfo : entity.UsrCardInfolist) { 
            				%>
              				<ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">银行编码：</span>
	                                 	<em style="font-weight: bold;"><%=cardinfo.BankId %></em>
	                             </li>
                             </ul>
                             <ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">银行卡号：</span>
	                                 	<em style="font-weight: bold;"><%=cardinfo.CardId %></em>
	                             </li>
                             </ul>
                             <ul class="gray6">
	                             <li class="mb10">
	                           		<span class="display-ib w200 tr mr5">银行卡是否实名：</span>
	                                 	<em class="red pr5"><%
													if("R".equals(cardinfo.RealFlag)){
														out.print("实名");
													}else if("I".equals(cardinfo.RealFlag)){
														out.print("认证中");
													}else if("N".equals(cardinfo.RealFlag)){
														out.print("未实名");
													}else{
														out.print("未知");
													}
												%></em>
	                             </li>
                              </ul>
                              <ul class="gray6">
	                             <li class="mb10">
	                           		<span class="display-ib w200 tr mr5">是否默认：</span>
	                                 	<em class="red pr5"><%
													if("Y".equals(cardinfo.IsDefault)){
														out.print("是");
													}else {
														out.print("否");
													}
												%></em>
	                             </li>
                              </ul>
                              <%if("ZRR".equals(userTag)){ %>
                              <ul class="gray6">
	                             <li class="mb10">
	                           		<span class="display-ib w200 tr mr5">是否快捷充值卡：</span>
	                                 	<em class="red pr5"><%
	                                 	if("Y".equals(cardinfo.ExpressFlag)){
											out.print("是");
										}else {
											out.print("否");
										}
												%></em>
	                             </li>
                              </ul>
                              <%
	                              	} 
	                              } 
	             				} 
                            }else { %>
							<ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">用户信息：</span>
	                                 	<em style="font-weight: bold;">用户尚未注册第三方托管账号！</em>
	                             </li>
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