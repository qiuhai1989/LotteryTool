<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		<link rel="stylesheet" href="/css/css.css" type="text/css">
		<script src="./style/common.js"></script>
		<link href="./style/style.css" type="text/css" rel="stylesheet">
		<link type="text/css" href="${pageContext.request.contextPath}/css/lightbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lightbox-2.6.min.js"></script>								
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>		
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tableColor.js"></script>
</head>
  <body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
  		<form action="${pageContext.request.contextPath}/apk/apkPic.php" method="post" id="form1">
		<table width="100%" align="center" border='0' bordercolorlight='#DDDDDD' bordercolordark='#EEEEEE' cellspacing='0' cellpadding='0'>
			<tr>
				<td><font color="blue" size="+1">当前位置：广告图管理</font></td>
			</tr>
			<tr align="center">
				<td><span>类型:
					<select name="search.jumpType" onchange="javascript:document.getElementById('form1').submit();">
									<option value='-1'>全部</option>
									<struts:iterator value="jumpTypeList" id="selectType">
										<struts:if test="#selectType.value==search.jumpType">
											<option value='<struts:property value="value"/>' selected="selected">
												<struts:property value="name"/>
											</option>
										</struts:if>
										<struts:else>
											<option value='<struts:property value="value"/>'>
												<struts:property value="name" />
											</option>
										</struts:else>
									</struts:iterator>
				</select></span>
				<!-- 
				<span>位置编号:<input type="text" name="search.bandpos" onkeyup="checkPositiveInteger(this)" onafterpaste="checkPositiveInteger(this)" onpaste="checkPositiveInteger(this)"/></span>
				<span><input type="submit" value="查询"/></span>
				 -->				
				<span><input type="button" value="添加广告图" onclick="location.href='./apkPic.php?action=edit&commandType=add'"/></span>
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
									cellspacing="1" id="table1">
									<tr bgcolor="5f8ac5" align="center">
										<td align="center" height="22" width="40px;">
											显示
										</td>
										<td align="center" height="22" width="80px;">
											图片
										</td>
										<td align="center" height="22" width="80px;">
											编号
										</td>
										<td align="center" height="22" width="">
											分组描述
										</td>
										<td align="center" height="22" width="">
											类型
										</td>
										<td align="center" height="22" width="">
											类型内容
										</td>
										<td align="center" height="22" width="80px">
											位置编号
										</td>
										<td align="center" height="22" width="150px">
											修改时间
										</td>
										<td align="center" height="22" width="80px">
											操作
										</td>
									</tr>
									<struts:iterator value="picList" id="apkPic">
									<tr>
										<td align="center" height="20" >
										<struts:if test="#apkPic.IsValid==1">
											<struts:checkbox value="true" id="che1" name="che1" fieldValue="1" disabled="true"></struts:checkbox>
										</struts:if>
										<struts:else>
											<struts:checkbox value="false" id="che" name="che" fieldValue="0" disabled="true"></struts:checkbox>
										</struts:else>
										</td>
										<td align="center" >
											<a href="${apkPic.Image_Add}" data-lightbox="adImage"><img src="${apkPic.Image_Add}" height="20px" width="60px"/></a>	
										</td>
										<td align="center" ><struts:property value="#apkPic.Code"/></td>
										<td align="center" ><struts:property value="#apkPic.Pci_Group"/></td>
										<td align="center" >
											<struts:iterator value="jumpTypeList" id="jumpType">
    												<struts:if test="#apkPic.Jump_Type==#jumpType.value"><struts:property value="#jumpType.name" /></struts:if>
    										</struts:iterator>
										</td>
										<td align="center" ><struts:property value="#apkPic.Ad_context" /></td>
										<td align="center" ><struts:property value="#apkPic.Band_pos" /></td>
										<td align="center" >
										<fmt:parseDate var="creTime" pattern="yy-MM-dd HH:mm:ss"><struts:property value="#apkPic.Updtime" /></fmt:parseDate>
												<fmt:formatDate value="${creTime}" pattern="yy-MM-dd HH:mm:ss" type="both"/>
										</td>
										<td align="center" height="20">
											<a href="./apkPic.php?action=edit&commandType=edit&picId=<struts:property value='#apkPic.Id'/>">编辑</a>
											<a href="./apkPic.php?action=del&picId=<struts:property value='#apkPic.Id'/>" onclick="return confirm('是否删除？');">删除</a>
										</td>
									</tr>
									</struts:iterator>
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
	</body>
<script type="text/javascript">
function checkPositiveInteger(obj){
	obj.value=obj.value.replace(/\D/g,'');
}
</script>
</html>
