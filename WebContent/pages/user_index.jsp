<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>欢迎使用本系统</title>

<%-- Ext必须的css文件 --%>
<link href="extjs/resources/css/ext-all.css" rel="stylesheet" type="text/css" />


<%--Ext必须的js文件 --%>
<script language="javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script language="javascript" src="extjs/ext-all.js"></script>
<script language="javascript" src="extjs/ext-lang-zh_CN.js"></script>

<%--Ext工具类 --%> 
<script type="text/javascript" src="js/utils/TreeGridSorter.js"></script>
<script type="text/javascript" src="js/utils/TreeGridColumnResizer.js"></script>
<script type="text/javascript" src="js/utils/TreeGridNodeUI.js"></script>
<script type="text/javascript" src="js/utils/TreeGridLoader.js"></script>
<script type="text/javascript" src="js/utils/TreeGridColumns.js"></script>
<script type="text/javascript" src="js/utils/TreeGrid.js"></script>

<!--修改ext样式-->
<link href="css/treegrid.css" rel="stylesheet" type="text/css" />
<!--logo css-->
<!-- <link href="css/user_index.css" rel="stylesheet" type="text/css" /> -->

<!-- <link href="css/spinner.css" rel="stylesheet" type="text/css" /> -->


<%--引入与资源resource有关的css文件和js文件 --%>
<%@include file="resource_include.jsp"%>

<%--引入与角色role有关的 css文件和js文件 --%>
<%@include file="role_include.jsp"%>

<%--引入与用户user有关的css文件和js文件 --%>
<%@include file="user_include.jsp"%>

<%--引入与日志记录login_record有关的css和js文件 --%>
<%@include file="authority_include.jsp"%>

<%--引入与日志记录login_record有关的css和js文件 --%>
<%@include file="login_record_include.jsp"%>

<%--引入与部门department相关的css和js文件 --%>
<%@include file="department_include.jsp" %>

</head>

<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			new UserIndex();
		});
	</script>
</body>
</html>
