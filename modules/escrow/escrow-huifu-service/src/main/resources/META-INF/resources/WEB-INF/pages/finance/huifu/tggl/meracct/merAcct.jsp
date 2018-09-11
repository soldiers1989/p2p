<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@page import="com.dimeng.p2p.escrow.huifu.entity.meracct.MerAcctItem"%>
<%@page import="com.dimeng.p2p.escrow.huifu.entity.query.MerAcctQueryEntity"%>
<html>
<head>
<%@include file="/WEB-INF/include/meta.jsp"%>
<%@include file="/WEB-INF/include/style.jsp"%>
<%@include file="/WEB-INF/include/script.jsp"%>
</head>
<body>
<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "SHZHXXCX";
	
	MerAcctQueryEntity entity = (MerAcctQueryEntity)request.getAttribute("entity");
	List<MerAcctItem> items = entity.AcctDetails;
%>
<div class="right-container">
	<div class="viewFramework-body">
		<div class="viewFramework-content">
          	<div class="p20">
				<div class="border">
					<div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>商户账户信息查询</div>
                    	<div class="content-container pl40 pt40 pr40 pb20">
                       	<ul class="gray6">
                       		<%if(items != null){
										for(MerAcctItem item : items){
									%>
                              <li class="mb10">
                            		<span class="display-ib w200 tr mr5" style="font-weight: bold;">账户类型：</span>
                                  	<em style="font-weight: bold;"><%
													if("BASEDT".equals(item.AcctType)){
														out.print("基本借记户");
													}else if("MERDT".equals(item.AcctType)){
														out.print("专属借记账户");
													}else if("SPEDT".equals(item.AcctType)){
													    if("SDT000001".equals(item.SubAcctId)){
													        out.print("担保账户");
													    }else if("SDT000002".equals(item.SubAcctId)){
															out.print("风险金账户");
													    }else{
													        out.print("商户自定义账户");
													    }
													}else if("DEP".equals(item.SubAcctId)){
													    out.print("保证金账户");
													}else{
													    out.print("商户自定义账户");
													}
												%></em>
                              </li>
                              <li class="mb10">
                            		<span class="display-ib w200 tr mr5">账户余额：</span>
                                  	<em class="red pr5"><%=item.AcctBal %></em>
                              </li>
                              <li class="mb10">
                            		<span class="display-ib w200 tr mr5">可用余额：</span>
                                  	<em class="red pr5"><%=item.AvlBal %></em>
                              </li>
                              <li class="mb10">
                            		<span class="display-ib w200 tr mr5">冻结余额：</span>
                                  	<em class="red pr5"><%=item.FrzBal %></em>
                              </li>                              
                            <%}} %>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>