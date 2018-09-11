<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.fkcjjl.CjRecordList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.ddgl.orderexception.OrderExceptionList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.ddgl.order.OrderList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.xxczgl.XxczglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.ptfxbyjgl.DfsdzqList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.jgbyj.JgbyjList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.ptzjgl.PtzjglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.ptfxbyjgl.PtfxbyjglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.fksh.FkshList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.yhzjgl.YhzjglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.txgl.TxglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.czgl.CzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.ZjList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.ByZjList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjgl.yhxygl.XyList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjmx.grzjmx.GrzjDetail"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjmx.qyzjmx.QyzjDetail"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjmx.jgzjmx.JgzjDetail"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.zjmx.ptzjmx.PtzjDetail"%>
<%@ page import="com.dimeng.p2p.console.servlets.finance.zjgl.dfgl.YqddfList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.zjgl.pttzgl.PttzglList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.zjgl.mall.MallRefundList" %>

<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.chargedzgl.ChargeDzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.withdrawdzgl.WithdrawDzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.biddzgl.BidDzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.zqzrdzgl.ZqzrDzglList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.userbalance.UserList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.meracct.MerAcct"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.userinfo.UserInfoList"%>
<%@page import="com.dimeng.p2p.console.servlets.finance.huifu.tggl.usercard.UserCardList"%>
<%@ page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.badclaimdzgl.BadclaimOrderList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.loandzgl.LoanOrderList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.paymentdzgl.PaymentOrderList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.advancedzgl.AdvanceOrderList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.offlinecharge.OfflineChargeOrderList" %>
<%@ page import="com.dimeng.p2p.console.servlets.finance.huifu.dzgl.mallrefunddzgl.MallRefundOrderList" %>

<%
	String HUIFU_CURRENT_SUB_CATEGORY = request.getParameter("CURRENT_SUB_CATEGORY");
%>
<div class="item-subnav-box" data-title="finance">
    <dl>
      <dt class="f20 fb"><i class="icon-i w30 h30 va-middle nav-cwgl-icon2"></i>财务管理</dt>
       <dd><a href="javascript:void(0);" class="click-link item-a" ><span class="a-text fl">资金管理</span><i class="icon-i w30 h30 arrow-down-icon mt5 fr"></i></a>
        <ul>
        
		  <li>
			<%
				if(dimengSession.isAccessableResource(CzglList.class))
				{
			%>
			<a class="click-link <%if ("CZGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, CzglList.class)%>" target="mainFrame">充值管理</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">充值管理</a>
			<%} %>
		</li>
      	<li>
			<%
			    if(dimengSession.isAccessableResource(XxczglList.class))
				{
			%>
			<a class="click-link <%if ("XXCZGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, XxczglList.class)%>" target="mainFrame">线下充值管理</a>
			<%
			    }else{
			%>
			<a href="javascript:void(0)" class="disabled">线下充值管理</a>
			<%
			    }
			%>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(TxglList.class))
				{
			%>
			<a class="click-link <%if ("TXGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" id="TXGL" href="<%=controller.getURI(request, TxglList.class)%>" target="mainFrame">提现管理</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">提现管理</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(FkshList.class))
				{
			%>
			<a class="click-link <%if ("FKGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" id="FKGL" href="<%=controller.getURI(request, FkshList.class)%>" target="mainFrame">放款管理</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">放款管理</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(CjRecordList.class))
				{
			%>
			<a class="click-link <%if ("FKCJJL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, CjRecordList.class)%>" target="mainFrame">放款成交记录</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">放款成交记录</a>
			<%} %>
		</li>
			<%if(Boolean.parseBoolean(configureProvider.getProperty(BadClaimVariavle.IS_BADCLAIM_TRANSFER))){%>
			<li>
				<%
					if(dimengSession.isAccessableResource("P2P_C_FINANCE_BLZQZRGLLIST"))
					{
				%>
				<a class="click-link <%if ("BLZQZRGL".equals(CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="/console/finance/zjgl/blzq/blzqDzrList.htm" target="mainFrame">不良债权转让管理</a>
				<%}else{ %>
				<a href="javascript:void(0)" class="disabled">不良债权转让管理</a>
				<%} %>
			</li>
			<%}%>
			<% if(Boolean.parseBoolean(configureProvider.getProperty(MallVariavle.IS_MALL))){%>
			<li>
				<%  if(dimengSession.isAccessableResource(MallRefundList.class)) { 	%>
				<a class="click-link <%if ("SCTKGL".equals(CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, MallRefundList.class)%>" target="mainFrame">商城退款管理</a>
				<%}else{ %>
				<a href="javascript:void(0)" class="disabled">商城退款管理</a>
				<%} %>
			</li>
			<%}%>
		<li>
			<%
				if(dimengSession.isAccessableResource(GrzjDetail.class))
				{
			%>
			<a class="click-link <%if ("GRZJMX".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, GrzjDetail.class)%>" target="mainFrame">个人资金明细</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">个人资金明细</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(QyzjDetail.class))
				{
			%>
			<a class="click-link <%if ("QYZJMX".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, QyzjDetail.class)%>" target="mainFrame">企业资金明细</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">企业资金明细</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(JgzjDetail.class))
				{
			%>
			<a class="click-link <%if ("JGZJMX".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, JgzjDetail.class)%>" target="mainFrame">机构资金明细</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">机构资金明细</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(PtzjDetail.class))
				{
			%>
			<a class="click-link <%if ("PTZJMX".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, PtzjDetail.class)%>" target="mainFrame">平台资金明细</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">平台资金明细</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(PttzglList.class))
					{
			%>
			<a class="click-link <%if ("PTTZGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, PttzglList.class)%>" target="mainFrame">平台调账管理</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">平台调账管理</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(XyList.class))
				{
			%>
			<a class="click-link <%if ("XYGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, XyList.class)%>" target="mainFrame">用户信用管理</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">用户信用管理</a>
			<%} %>
		</li>
		<li>
				<%
					if(dimengSession.isAccessableResource("P2P_C_FINANCE_DBLLIST"))
					{
				%>
				<a class="click-link <%if ("XYGL".equals(CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="/console/finance/zjgl/yhdbgl/dbList.htm" target="mainFrame">用户担保管理</a>
				<%}else{ %>
				<a href="javascript:void(0)" class="disabled">用户担保管理</a>
				<%} %>
			</li>
        </ul>
      </dd>

      <dd><a href="javascript:void(0);" class="click-link item-a" ><span class="a-text fl">对账管理</span><i class="icon-i w30 h30 arrow-down-icon mt5 fr"></i></a>
        <ul>
          <li>
			<%
					if(dimengSession.isAccessableResource(ChargeDzglList.class))
					{
			%>
			<a class="click-link <%if ("CZDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, ChargeDzglList.class)%>" target="mainFrame">充值对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">充值对账</a>
			<%} %>
		</li>
		 <li>
			<%
					if(dimengSession.isAccessableResource(WithdrawDzglList.class))
					{
			%>
			<a class="click-link <%if ("TXDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, WithdrawDzglList.class)%>" target="mainFrame">提现对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">提现对账</a>
			<%} %>
		</li>
		<li>
			<%
					if(dimengSession.isAccessableResource(BidDzglList.class))
					{
			%>
			<a class="click-link <%if ("TBDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, BidDzglList.class)%>" target="mainFrame">投标对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">投标对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(ZqzrDzglList.class))
			{
			%>
			<a class="click-link <%if ("ZQZRDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, ZqzrDzglList.class)%>" target="mainFrame">债权转让对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">债权转让对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(BadclaimOrderList.class))
			{
			%>
			<a class="click-link <%if ("BLZQZRDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, BadclaimOrderList.class)%>" target="mainFrame">不良债权转让对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">不良债权转让对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(LoanOrderList.class))
			{
			%>
			<a class="click-link <%if ("FKDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, LoanOrderList.class)%>" target="mainFrame">放款对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">放款对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(PaymentOrderList.class))
			{
			%>
			<a class="click-link <%if ("HKDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, PaymentOrderList.class)%>" target="mainFrame">还款对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">还款对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(AdvanceOrderList.class))
			{
			%>
			<a class="click-link <%if ("DFDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, AdvanceOrderList.class)%>" target="mainFrame">垫付对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">垫付对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(OfflineChargeOrderList.class))
			{
			%>
			<a class="click-link <%if ("XXCZDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, OfflineChargeOrderList.class)%>" target="mainFrame">线下充值对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">线下充值对账</a>
			<%} %>
		</li>
		<li>
			<%
			if(dimengSession.isAccessableResource(MallRefundOrderList.class))
			{
			%>
			<a class="click-link <%if ("SPTKDZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, MallRefundOrderList.class)%>" target="mainFrame">商品退款对账</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">商品退款对账</a>
			<%} %>
		</li>
		
		<li>
			<%
			if(dimengSession.isAccessableResource(UserList.class))
			{
			%>
			<a class="click-link <%if ("YHYECX".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, UserList.class)%>" target="mainFrame">用户余额查询</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">用户余额查询</a>
			<%} %>
		</li>
        </ul>
      </dd>
       
      <dd><a href="javascript:void(0);" class="click-link item-a" ><span class="a-text fl">订单管理</span><i class="icon-i w30 h30 arrow-down-icon mt5 fr"></i></a>
        <ul>
          <li>
			<%
				if(dimengSession.isAccessableResource(OrderList.class))
				{
			%>
			<a class="click-link <%if ("DDGL".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, OrderList.class)%>" target="mainFrame">订单管理</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">订单管理</a>
			<%} %>
		</li>
		<li>
			<%
				if(dimengSession.isAccessableResource(OrderExceptionList.class))
				{
			%>
			<a class="click-link <%if ("DDYCRZ".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, OrderExceptionList.class)%>" target="mainFrame">订单异常日志</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">订单异常日志</a>
			<%} %>
		</li>
        </ul>
      </dd>
            
      <dd><a href="javascript:void(0);" class="click-link item-a" ><span class="a-text fl">托管账户管理</span><i class="icon-i w30 h30 arrow-down-icon mt5 fr"></i></a>
        <ul>
        <li>
			<%
					if(dimengSession.isAccessableResource(UserInfoList.class))
					{
			%>
			<a class="click-link <%if ("KHXXTB".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, UserInfoList.class)%>" target="mainFrame">开户信息同步</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">开户信息同步</a>
			<%} %>
		</li>
		<li>
			<%
					if(dimengSession.isAccessableResource(UserCardList.class))
					{
			%>
			<a class="click-link <%if ("YHKXXTB".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, UserCardList.class)%>" target="mainFrame">银行卡信息同步</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">银行卡信息同步</a>
			<%} %>
		</li>
		<li>
			<%
					if(dimengSession.isAccessableResource(MerAcct.class))
					{
			%>
			<a class="click-link <%if ("SHZHXXCX".equals(HUIFU_CURRENT_SUB_CATEGORY)) {%> select-a <%}%>" href="<%=controller.getURI(request, MerAcct.class)%>" target="mainFrame">商户账户信息</a>
			<%}else{ %>
			<a href="javascript:void(0)" class="disabled">商户账户信息</a>
			<%} %>
		</li>
        </ul>
      </dd>
      
    </dl>
  </div>
