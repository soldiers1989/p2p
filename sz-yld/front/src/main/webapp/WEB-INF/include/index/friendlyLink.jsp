<%@page import="com.dimeng.p2p.S50.entities.T5014"%>
<%@page import="com.dimeng.p2p.modules.base.front.service.FriendlyLinkManage"%>
<%{
	T5014[] friendlyLinks=serviceSession.getService(FriendlyLinkManage.class).getAll();
	if(friendlyLinks!=null&&friendlyLinks.length>0){
%>
<!--友情链接-->
<div class="home_link clearfix">
  <div class="til">友情链接：</div>
  <ul class="link">
  <%for(T5014 friendlyLink:friendlyLinks){if(friendlyLink==null){continue;}%>
    <li><a href="http://<%StringHelper.filterHTML(out, friendlyLink.F05.replaceAll("http://", "").replaceAll("https://",""));%>" target="_blank" title="<%StringHelper.filterHTML(out, friendlyLink.F04);%>"><%StringHelper.filterHTML(out, friendlyLink.F04);%></a></li>
    <%}%>
  </ul>
</div>
<!--友情链接-->
<%}}%>
