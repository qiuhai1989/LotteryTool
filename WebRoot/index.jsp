<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script type="text/javascript">
   
  
   function orcShow(){
   		document.location.href="/test/testMember.php";
   }
   function sqlShow(){
   		document.location.href="/test/sqlTestList.php";
   		
   }
   
   function showMatch(){
    var myDate=new Date();
    var m=(myDate.getMonth()+1);
    var d=myDate.getDate();
    if(m<10){
    	m="0"+m;
    }
    if(d<10){
    	d="0"+d;
    }
    
  	var str=myDate.getFullYear()+"-"+m+"-"+d;
  	document.location.href="/match/match.php?startDate="+str;
   }
  </script>
  <body>
    <input type="button" value="对阵赛事" onclick="showMatch();">
  </body>
</html>
