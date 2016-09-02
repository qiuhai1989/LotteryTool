<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style type="text/css">
#globalNav {
	position:absolute;
	top:40px;
	left:0;
}
#globalNav li a{
	display:block;
	width:180px;
	padding:10px;
	background:#2E75B2;
	border-bottom:2px solid #fff;
	color:#FFF;
	font-weight:bold;
	text-decoration:none;
}
#globalNav li a:hover{
	background:#585858;
	border-right:2px solid #fff;
	color: #f50;
}

#globalNav li ul li a:focus{
	background:#66852B;
	border-right:2px solid #fff;
	color: #f50;
}

#globalNav li ul li a{
	background:#DBEAF9;
}
#globalNav li ul li a:hover{
	background:#666;
}
#frameBord {
	border-left:1px solid #ccc;
}
#globalNav span{
	color: #FF8C00;
}
</style>
</head>

<body id="index">
<div class="kefu">
</div>
<div class="blank1"></div>
<ul id="globalNav">
	<li><a href="#" target="">客户端管理</a>
		<ul>
			<li><a href="/apk/apkNotice.php" target="blank"><span>客户端公告管理</span></a></li>
			<li><a href="/apk/apkPush.php" target="blank"><span>客户端PUSH管理</span></a></li>
			<li><a href="/apk/apkPackage.php" target="blank"><span>客户端渠道管理</span></a></li>
			<li><a href="/apk/apkManage.php" target="blank"><span>客户端APK管理</span></a></li>
		</ul>
	
	</li>
	<!-- 
	<li><a href="login.html">退出</a></li>
	 -->
</ul>
<iframe id="frameBord" name="frameBord" frameborder="0" width="100%" height="100%" src=""></iframe>
<script type="text/javascript">
</script>
</body>
</html>
