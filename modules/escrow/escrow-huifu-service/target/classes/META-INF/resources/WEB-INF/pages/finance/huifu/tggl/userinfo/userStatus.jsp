<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.dimeng.p2p.escrow.huifu.entity.query.UserInfoQueryEntity"%>
<%@page import="com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo.UserInfoList"%>
<%@page import="java.util.List"%>

<html>
<head>
<%@include file="/WEB-INF/include/meta.jsp"%>
<%@include file="/WEB-INF/include/style.jsp"%>
</head>
<body>
<%
	CURRENT_CATEGORY = "CWGL";
	CURRENT_SUB_CATEGORY = "KHXXTB";
	
	UserInfoQueryEntity userEntity = (UserInfoQueryEntity)request.getAttribute("userEntity");
	CorpQueryEntity corpEntity = (CorpQueryEntity)request.getAttribute("corpEntity");
%>
<div class="right-container">
	<div class="viewFramework-body">
      	<div class="viewFramework-content">
      		<div class="p20">
          		<div class="border">
            		<div class="title-container"><i class="icon-i w30 h30 va-middle title-left-icon"></i>用户开户信息
            			<div class="fr mt5 mr10">
							  <input type="button" class="btn btn-blue2 radius-6 pl20 pr20"
									 onclick="location.href='<%=controller.getURI(request, UserInfoList.class) %>'" value="返回">
						</div>
            		</div>
					<div class="content-container pl40 pt30 pr40">
						<%if(null != userEntity) { %>
							<ul class="gray6">
	              				<li class="mb10">
	              					<span class="display-ib w200 tr mr5">用户客户号：</span>
	                                <em class="red pr5"><%=StringHelper.isEmpty(userEntity.UsrCustId) ? "" : userEntity.UsrCustId %></em>
	              				</li>              				
              				</ul>
							<ul class="gray6">
								<li class="mb10">
	              					<span class="display-ib w200 tr mr5">第三方账号：</span>
	                                <em class="red pr5"><%=StringHelper.isEmpty(userEntity.UsrId) ? "" : userEntity.UsrId %></em>
              					</li>
              				</ul>
              				<ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">用户身份证号：</span>
	                                 	<em style="font-weight: bold;"><%=userEntity.CertId %></em>
	                             </li>
                             </ul>
                             <ul class="gray6">
	                             <li class="mb10">
	                           		<span class="display-ib w200 tr mr5">用户状态：</span>
	                                 	<em class="red pr5"><%
													if("N".equals(userEntity.UsrStat)){
														out.print("正常");
													}else if("A".equals(userEntity.UsrStat)){
														out.print("待激活 ，登录后需要用户激活");
													}else if("C".equals(userEntity.UsrStat)){
														out.print("被关闭，临时关闭不能登陆");
													}else{
														out.print("已销户");
													}
												%></em>
	                             </li>
                              </ul>
						<%} else if (null != corpEntity) { %>
							<ul class="gray6">
	              				<li class="mb10">
	              					<span class="display-ib w200 tr mr5">用户客户号：</span>
	                                <em class="red pr5"><%=StringHelper.isEmpty(corpEntity.UsrCustId) ? "" : corpEntity.UsrCustId %></em>
	              				</li>              				
              				</ul>
							<ul class="gray6">
								<li class="mb10">
	              					<span class="display-ib w200 tr mr5">第三方账号：</span>
	                                <em class="red pr5"><%=StringHelper.isEmpty(corpEntity.UsrId) ? "" : corpEntity.UsrId %></em>
              					</li>
              				</ul>
              				<ul class="gray6">
	             				<li class="mb10">
	                           		<span class="display-ib w200 tr mr5" style="font-weight: bold;">企业营业执照编号：</span>
	                                 	<em style="font-weight: bold;"><%=corpEntity.BusiCode %></em>
	                             </li>
                             </ul>
                             <ul class="gray6">
	                             <li class="mb10">
	                           		<span class="display-ib w200 tr mr5">企业审核状态：</span>
	                                 	<em class="red pr5"><%
													if("I".equals(corpEntity.AuditStat)){
														out.print("初始化");
													}else if("T".equals(corpEntity.AuditStat)){
														out.print("已提交");
													}else if("P".equals(corpEntity.AuditStat)){
														out.print("审核中");
													}else if("R".equals(corpEntity.AuditStat)){
														out.print("审核拒绝");
													}else if("F".equals(corpEntity.AuditStat)){
														out.print("开户失败");
													}else if("K".equals(corpEntity.AuditStat)){
														out.print("开户中");
													}else if("Y".equals(corpEntity.AuditStat)){
														out.print("开户成功");
													}else{
														out.print("尚未提交注册申请");
													}
												%></em>
	                             </li>
                              </ul>
						<%} else { %>
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