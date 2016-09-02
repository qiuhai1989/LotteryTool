<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>出错啦！</title>
    
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
     <div>
     <p>
     <span>错误信息：<%=request.getParameter("errorMessage") %>，本窗口将在 </span>
     <span id="countTime" style="color:red;"></span>
     <span> 秒后自动关闭</span>
     </p>
     </div>
  </body>
  <script type="text/javascript">	
	function clock(){
		i=i-1;
		document.getElementById("countTime").innerHTML=i;
		//document.title="本窗口将在"+i+"秒后自动关闭！";
		if(i>0){
			setTimeout("clock();",1000);
			}
		else {
			window.opener = null;//为了不出现提示框
			window.close();//关闭窗口
			}
	}

	var i=5;
	clock();
  </script>
</html>
