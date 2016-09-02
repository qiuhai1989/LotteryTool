<%@ page language="java" import="java.net.InetAddress,java.net.UnknownHostException" pageEncoding="utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
			String url = "http://download.wayin.cn:86";
	try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();
			if(ip.equals("192.168.0.66")){
				url = "http://wayin.5166.info:88";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
%>
<html>
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tableColor.js"></script>
<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>	
<script type="text/javascript" src="/js/zClip.js"></script>	
</head>
  <body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
  		<form action="${pageContext.request.contextPath}/apk/apkManage.php" method="post" id="form1">
		<table width="100%" align="center" border='0' cellspacing='0' cellpadding='0'>
			<tr>
				<td><font color="blue" size="+1">当前位置：客户端APK管理</font></td>
			</tr>
			<tr align="center">
				<td>
					 <span>适用平台：
						<select name="search.platformId" id="platformId" onchange="javascript:document.getElementById('form1').submit();">
							<struts:iterator value="platformList" id="platform">
								<option value="<struts:property value='#platform.id'/>" <struts:if test="#platform.id==search.platformId">selected</struts:if>><struts:property value="#platform.name"/></option>
							</struts:iterator>
						</select>
					</span>
					<span>适用渠道：
						<select name="search.channelId" id="channelId" onchange="javascript:document.getElementById('form1').submit();">
							<option value="0">全部</option>
							<struts:iterator value="channelList" id="channel">
								<option value="<struts:property value='#channel.id'/>" <struts:if test="#channel.id==search.channelId">selected</struts:if>><struts:property value="#channel.realName"/></option>
							</struts:iterator>
						</select>
					</span>
					<span>更新版本号:
						<select name="search.updVersion" onchange="javascript:document.getElementById('form1').submit();">
							<struts:iterator value="updVersionList" id="updVersionList">
								<struts:if test="#updVersionList.updVersion==search.updVersion">
											<option value="<struts:property value='updVersion'/>" selected="selected">
												<struts:property value="updVersion"/>
											</option>
										</struts:if>
										<struts:else>
											<option value="<struts:property value='updVersion'/>">
												<struts:property value="updVersion" />
											</option>
										</struts:else>
							</struts:iterator>
						</select>
					</span>
					<span><input type="submit" value=" 查询  "/></span>
					<span><input type="button" value="添加" onclick="javascript:add();"/></span>
					<span><input type="button" value="批量添加" onclick="location.href='/manageBatchUpload.jsp'"/></span>
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
										<td width="50px;">客户端显示</td>
										<td width="50px;">强制更新</td>
										<td width="100px;">适用平台</td>
										<td width="100px;">适用渠道</td>
										<td>包名</td>
										<td>二维码地址</td>
										<td width="80px;">包大小</td>
										<td width="80px;">更新版本号</td>
										<td width="50px;">内部版本号</td>
										<td width="50px;">内部子版本号</td>
										<td height="22" width="150px;">
											操作
										</td>
									</tr>
									<struts:iterator value="manageList" id="apkManage" status="li">
									<tr>
										<td  align="center"><input type="checkbox" disabled="disabled" <struts:if test="#apkManage.isValid == 1">checked</struts:if>/></td>
										<td  align="center"><input type="checkbox" disabled="disabled" <struts:if test="#apkManage.isForce == 1">checked</struts:if>/></td>
										<td ><struts:property value="#apkManage.pname" /></td>
										<td ><struts:property value="#apkManage.cname" /></td>
										<td align="center"><struts:property value="#apkManage.packageName" /></td>
										<td style="position: relative;" align="center"><a href="javascript:void(0)" id="copyCode<struts:property value="#li.index+1"/>"/>点击复制</a></td>
										<td ><struts:property value="#apkManage.apkSize" /></td>
										<td ><struts:property value="#apkManage.updVersion" /></td>
										<td ><struts:property value="#apkManage.versionCode" /></td>
										<td ><struts:property value="#apkManage.versionNameCode" /></td>
										<td align="center" height="20" >
											<a href="./apkManage.php?action=view&manageId=<struts:property value='#apkManage.id'/>">查看</a>
											<a href="./apkManage.php?action=edit&manageId=<struts:property value='#apkManage.id'/>&platformId=${platformId}">修改</a>
											<a href="./apkManage.php?action=del&manageId=<struts:property value='#apkManage.id'/>">删除</a>
										</td>
									</tr>
									<script type="text/javascript">
												$("#copyCode<struts:property value="#li.index+1"/>").zclip({
												path:'/js/ZeroClipboard.swf',
												copy:'<%=url%>/getupdate.aspx?fr=<struts:property value="#apkManage.packageId"/>'
												});
									</script>
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
	<script type="text/javascript">
		function add(){
			var channelId =  document.getElementById("channelId").value;
			var platformId =  document.getElementById("platformId").value;
			if(channelId > 0){
				var url = "./apkManage.php?action=edit&channelId="+channelId+"&platformId="+platformId;
				window.location = url;
			}
			else alert("请选择对应渠道.");
		}
	</script>
	</body>
</html>
