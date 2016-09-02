<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userName.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <div style="width:500px;text-align: center;height: 200px;background-color: #145232">
    <table border="1" cellpadding="0" cellspacing="0" width="100%" style="color: red;" height="100%">
    <s:iterator value="listInfo">
    	<tr>
    		<td><s:property value="id"/> </td>
    		<td><s:property value="userName"/> </td>
    		<td><s:property value="userPwd"/> </td>
    		<td><s:property value="demo"/> </td>
    	</tr>
    </s:iterator>
    </table>
    </div>
  </body>
</html>
