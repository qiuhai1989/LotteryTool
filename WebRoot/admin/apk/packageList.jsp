<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<html>
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		<link rel="stylesheet" href="/css/css.css" type="text/css">
		<script src="./style/common.js"></script>
		<link href="./style/style.css" type="text/css" rel="stylesheet">
		
<!-- 时间控件js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>		
<script language="JavaScript">
</script>
</head>
  <body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
  		<form action="/apk/apkPackage.php" method="post">
		<table width="100%" align="center" border='0'
			bordercolorlight='#DDDDDD' bordercolordark='#EEEEEE' cellspacing='0'
			cellpadding='0'>
			<tr>
				<td><a href="./apkPackage.php?action=edit">添加</a></td>
			</tr>
			<tr>
				<td>
					<span>渠道名称：<input type="text" id="noticeName" name="search.name" value="${search.name}"/></span>
					<span><input type="submit" value=" 查询  "/></span>
				</td>
			</tr>
			<tr id="cat">
				<td colspan="3" height="35" align="center" class="tbhead"
					background="./img/band_di.gif">
					<b align="center"><FONT color=#ff6600>客户端渠道管理</font><b>
				</td>
			</tr>
		</table>
		</form>
		<table width="100%" align="center" border='0'
			bordercolorlight='#DDDDDD' bordercolordark='#EEEEEE' cellspacing='0'
			cellpadding='0'>
			<tr>
				<td width="80%" valign="top">
					<table width="100%" border='0' bordercolorlight='#DDDDDD'
						bordercolordark='#EEEEEE' cellspacing='0' cellpadding='0'>
						<tr>
							<td colspan="7" align="right"></td>
						</tr>
						<tr>
							<td>
								<table width="101%" border="0" align="center" cellpadding="0"
									cellspacing="1" style="TABLE-LAYOUT: fixed">
									<tr bgcolor="5f8ac5" align="center">
										<td width="80px;">渠道序号</td>
										<td width="80px;">客户端显示</td>
										<td>渠道名称</td>
										<td>渠道编码</td>
										<td>备注</td>
										<td height="22">
											操作
										</td>
									</tr>
									<struts:iterator value="packageList" id="package">
									<tr>
										<td bgcolor="#EFEFEF" align="center"><struts:property value="id" /></td>
										<td bgcolor="#EFEFEF" align="center"><input type="checkbox" disabled="disabled" <struts:if test="#package.isValid == 1">checked</struts:if>/></td>
										<td bgcolor="#EFEFEF"><struts:property value="packageName" />-<struts:property value="id" /></td>
										<td bgcolor="#EFEFEF"><struts:property value="packageCode" /></td>
										<td bgcolor="#EFEFEF"><struts:property value="bak" /></td>
										<td align="center" height="20" bgcolor="#EFEFEF">
											<a href="./apkPackage.php?action=view&packageId=<struts:property value='id'/>">查看</a>
											<a href="./apkPackage.php?action=edit&packageId=<struts:property value='id'/>">修改</a>
											<a href="./apkPackage.php?action=del&packageId=<struts:property value='id'/>">删除</a>
										</td>
									</tr>
									</struts:iterator>
									<tr>
										<td colspan="3" align="center">
											
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p align="center">
			<struts:property value="pagerView" escape="false" />
		</p>
		<p>
			&nbsp;
		</p>
	</body>
</html>
