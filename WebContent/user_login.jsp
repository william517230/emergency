<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>系统登陆页面</title>
<%-- Ext必须的css文件 --%>
<link href="extjs/resources/css/ext-all.css" rel="stylesheet" type="text/css" />
<%--Ext必须的js文件 --%>
<script language="javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script language="javascript" src="extjs/ext-all.js"></script>
<script language="javascript" src="extjs/ext-lang-zh_CN.js"></script>
<script language="javascript" src="js/user/user_login.js"></script>
<LINK href="css/xtree.css" type=text/css rel=stylesheet>
<LINK href="css/User_Login.css" type=text/css rel=stylesheet>
</head>

<BODY id=userlogin_body>
<DIV id=user_login>
<DL>
  <DD id=user_top>
  <UL>
    <LI class=user_top_l></LI>
    <LI class=user_top_c></LI>
    <LI class=user_top_r></LI>
 </UL>
  <DD id=user_main>
  <UL>
    <LI class=user_main_l></LI>
    <LI class=user_main_c>
	    <DIV class=user_main_box>
		    <UL>
		      <LI class=user_main_text>用户名： </LI>
		      <LI class=user_main_input><input type='text' id='username' value="admin"></LI>
		    </UL>
		    <UL>
		      <LI class=user_main_text>密 码： </LI>
		      <LI class=user_main_input><input type='password' id='password' value="admin"> </LI>
		   </UL>
		    <!-- 
		    <UL>
		      <LI class=user_main_text>
		      	<img src="oa-user/tbUserAction_getImage.action" onclick="changeValidateCode(this)" /> 
		      </LI>
		      <LI class=user_main_input><input type='text' id='verifyCode'/></LI>
		    </UL>
		     -->
	   </DIV>
   </LI>
   <LI class=user_main_r>
	   	<INPUT class=IbtnEnterCssClass  id = "login"
	    style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px" 
	    type="image" src="images/login/user_botton.gif" name=IbtnEnter> 
   </LI>
  </UL>
  <DD id=user_bottom>
	  <UL>
	    <LI class=user_bottom_l></LI><LI class=user_bottom_c><SPAN style="MARGIN-TOP: 40px"></SPAN></LI>
	    <LI class=user_bottom_r></LI>
	  </UL>
  </DD>
</DL>
</DIV>
<!--   <SPAN id=ValrUserName style="DISPLAY: none; COLOR: red"></SPAN>
  <SPAN id=ValrPassword style="DISPLAY: none; COLOR: red"></SPAN>
  <SPAN id=ValrValidateCode style="DISPLAY: none; COLOR: red"></SPAN>
  <DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></DIV> -->
<!--   <DIV></DIV> -->
</BODY>

</html>
