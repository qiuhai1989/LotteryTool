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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tableColor.js"></script>
</head>
  <body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
  		<form action="/apk/apkPush.php" method="post" id="form1">
		<table width="100%" align="center" border='0'
			bordercolorlight='#DDDDDD' bordercolordark='#EEEEEE' cellspacing='0'
			cellpadding='0'>
			<tr>
				<td><font color="blue" size="+1">当前位置：PUSH管理</font></td>
			</tr>
			<tr align="center">
				<td>
					<span>编号(整数)：<input type="text" id="PushNo" name="search.noticeNo" value="${search.noticeNo}" onkeyup="checkPositiveInteger(this)"/></span>
					<span>PUSH名称：<input type="text" id="PushName" name="search.name" value="${search.name}"/></span>
					<span>PUSH广告语：<input type="text" id="PushAd" name="search.pushAd" value="${search.pushAd}"/></span>
				</td>
			</tr>
			<tr align="center">
				<td>
					<span>
						<select name="search.type" onchange="javascript:document.getElementById('form1').submit();">
									<option value='-1'>连接类型</option>
									<struts:iterator value="jumpTypeList1" id="selectType">
										<struts:if test="#selectType.value==search.type">
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
						</select>
					</span>
					<span>
						<select name="search.sendGroup" onchange="javascript:document.getElementById('form1').submit();">
									<option value='-1'>接收对象</option>
									<struts:iterator value="sendGroupList" id="selectType">
										<struts:if test="#selectType.value==search.sendGroup">
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
						</select>
					</span>
					<span>PUSH时间：<input type="text" id="startDate" name="search.startDate" value="${search.startDate}" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
					~
					<input type="text" id="endDate" name="search.endDate" value="${search.endDate}" onFocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/></span>
					<span><input type="submit" value=" 查询 "/></span>
					<span><input type="button" value="添加PUSH" onclick="location.href='./apkPush.php?action=edit&commandType=add'"/></span>
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
										<td width="40px;">显示</td>
										<td width="40px;">编号</td>
										<td width="80px;">push名称</td>
										<td>push广告语</td>	
										<td>接受对象</td>
										<td>连接类型</td>
										<td width="">地址</td>
										<td>页面编号</td>
										<td width="50px;">间隔(小时)</td>
										<td width="40px;">次数</td>
										<td width="120px;">首次push时间</td>
										<td width="90px;">
											操作
										</td>
									</tr>
									<struts:iterator value="pushList" id="push">
									<tr>
										<td  align="center"><input type="checkbox" disabled="disabled" <struts:if test="#push.isValid == 1">checked</struts:if>/></td>
										<td  align="center"><struts:property value="pushNo" /></td>
										<td  align="center"><struts:property value="name" /></td>
    									<td>
    									<struts:if test="null!=pushAd&&pushAd.length()>14">
										          <struts:property value="pushAd.substring(0, 14)"/>...
										  </struts:if>
										  <struts:else>
										          <struts:property value="pushAd" />
										   </struts:else>
    									</td>
										<td >
											<struts:iterator value="sendGroupList" id="sendGroupList">
    												<struts:if test="sendGroup==#sendGroupList.value">
    													<struts:property value="#sendGroupList.name" />
    													<struts:if test="#sendGroupList.name!='全部'">
    														：<struts:property value="receiveUser" />
    													</struts:if>
    												</struts:if>
    										</struts:iterator>
    									</td>
										<td >
											<struts:iterator value="jumpTypeList1" id="jumpType1">
    												<struts:if test="type==#jumpType1.value"><struts:property value="#jumpType1.name" /></struts:if>
    										</struts:iterator>
    										:
											<struts:iterator value="jumpTypeList" id="jumpType">
    												<struts:if test="pushContent==#jumpType.value"><struts:property value="#jumpType.name" /></struts:if>
    										</struts:iterator>
										</td>
										<td align="center">
										<struts:if test="null!=address&&address.length()>14">
										          <struts:property value="address.substring(0, 14)"/>...
										  </struts:if>
										  <struts:else>
										          <struts:property value="address"/>
										   </struts:else>
										</td>
										<td align="center"><struts:property value="pageId" /></td>
										<td align="center"><struts:property value="pushSpace" /></td>
    									<td align="center"><struts:property value="pushNum" /></td>
										<td align="center">
											<fmt:parseDate var="creTime" pattern="yy-MM-dd HH:mm:ss"><struts:property value="pushTime" /></fmt:parseDate>
											<fmt:formatDate value="${creTime}" pattern="yy-MM-dd HH:mm" type="both"/>
										</td>
										<td align="center">
										<!-- 
											<a href="./apkPush.php?action=view&pushId=<struts:property value='id'/>">查看</a>
										 -->
											<a href="./apkPush.php?action=edit&commandType=edit&pushId=<struts:property value='id'/>">编辑</a>
											<a href="./apkPush.php?action=del&pushId=<struts:property value='id'/>">删除</a>
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
		<p>
			&nbsp;
		</p>
	</body>
<script type="text/javascript">
function checkPositiveInteger(obj){
	obj.value=obj.value.replace(/\D/g,'');
}
</script>
</html>
