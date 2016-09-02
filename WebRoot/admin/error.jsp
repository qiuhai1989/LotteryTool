<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
  	function back(){
  		//var backurl=document.referrer;
  		//window.location.href=backurl;
  		//history.back(-2);
  		history.go(-1);
  		
  	};	
</script>
  </head>
  
<body onLoad="setInterval('countDown()',3000);">
  	<div style="width: 400px;margin: auto;">
  		<p>
  			>_< 操作失败！！！
  		</p>
  		<p>正在返回，如果不跳转请点击<a href="javascript:back();" style="color: #0000FF;">这里</a></p>
  	</div>
</html>  