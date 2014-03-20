<%@page import="com.modelsystem.po.Users"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Users user = (Users)session.getAttribute("loginUser"); 
	String username = user.getUsername();
%>
<%-- 动态生成div,用来存放要被渲染的自定义组件 --%>
<div id='user_index_topp' style="width: 100%; height: 100%"></div>

<script type="text/javascript">
         //获取ajax请求参数subMainId
         var subMainId ='user_index_top';
		 //实例化用户查看编辑面板
		 var userIndexTop = new UserIndexTop({myid:subMainId, username:'<%=username%>'});
		 //渲染组件
		 userIndexTop.render(subMainId+'p');
</script>