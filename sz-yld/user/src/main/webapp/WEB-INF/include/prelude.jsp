<%@page import="java.math.BigDecimal"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.dimeng.framework.http.upload.FileStore"%>
<%@page import="com.dimeng.framework.http.session.authentication.PasswordAuthentication"%>
<%@page import="com.dimeng.p2p.variables.defines.*"%>
<%@page import="com.dimeng.util.StringHelper"%>
<%@page import="com.dimeng.util.Formater"%>
<%@page import="com.dimeng.util.parser.*"%>
<%@page import="com.dimeng.framework.config.*"%>
<%@page import="com.dimeng.framework.resource.*"%>
<%@page import="com.dimeng.framework.http.servlet.*"%>
<%@page import="com.dimeng.framework.http.session.*"%>
<%@page import="com.dimeng.framework.service.*"%>
<%@page import="com.dimeng.framework.service.query.*"%>
<%@page session="false"%>
<%
	boolean JQUERY_NOT_INCLUDED = true;
	boolean JQUERY_UI_NOT_INCLUDED = true;
	boolean DIALOG_NOT_INCLUDED = true;
	String CURRENT_CATEGORY = "";
	String CURRENT_SUB_CATEGORY = "";
	ResourceProvider resourceProvider = ResourceRegister.getResourceProvider(application);
	ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
	Controller controller = resourceProvider.getResource(Controller.class);
	Session dimengSession = resourceProvider.getResource(SessionManager.class).getSession(request, response,true);
	SystemDefine systemDefine = resourceProvider.getSystemDefine();
	try (ServiceSession serviceSession = resourceProvider.getResource(ServiceProvider.class).createServiceSession(dimengSession)) {
		FileStore fileStore = resourceProvider.getResource(FileStore.class);
		Rewriter rewriter = resourceProvider.getSystemDefine().getRewriter();
%>