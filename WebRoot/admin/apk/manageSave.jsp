<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<head>
<link href="./style/style.css" type="text/css" rel="stylesheet">

</head>
<body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
<s:form method="post" action="/apk/apkManage.php?action=save" enctype="multipart/form-data">
<table width="100%"  class="table_view">
	<tr id="cat" class="tbhead">
		<td align="center" colspan="2" background="./img/band_di.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<FONT color=#ff6600>渠道修改</FONT>
	  <a href="/apk/apkManage.php" style="float: left;">返回列表</a>		
	</tr>
  <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">客户端显示：</font></th>
    <td><input type="checkbox" name="apkManage.isValid" id="isValid" value="0" <s:if test="apkManage.isValid == 1">checked</s:if>/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">是否强制更新：</font></th>
    <td><input type="checkbox" name="apkManage.isForce" id="isForce" value="0" <s:if test="apkManage.isForce == 1">checked</s:if>/></td>
  </tr>
   <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">下载方式：</font></th>
    <td>
    	<s:if test="apkManage.downType == 1">
    		<input type="radio" name="apkManage.downType" id="downType" value="0"/>内网
    		<input type="radio" name="apkManage.downType" id="downType" value="1" checked="checked"/>外网
    	</s:if>
    	<s:else>
    		<input type="radio" name="apkManage.downType" id="downType" value="0" checked="checked"/>内网
    		<input type="radio" name="apkManage.downType" id="downType" value="1"/>外网
    	</s:else>
    </td>
  </tr>
  <!-- 
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">包名名称：</font></th>
    <td><input type="text" name="apkManage.apkName" id="apkName" value="<s:property value='apkManage.apkName'/>"/ style="width: 80%;"></td>
  </tr>
   -->
   <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">上传APK包：</font></th>
    <td><input type="file" name="apkFile" id="apkFile"><span><s:property value='apkManage.apkPath'/></span></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">当前版本：</font></th>
    <td><s:property value='curVersion'/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">更新版本：</font></th>
    <td>
    	<s:if test="manageId == 0">
    		<input type="text" name="apkManage.updVersion" id="updVersion" value="<s:property value='apkManage.updVersion'/>">
    	</s:if>
    	<s:else><input type="hidden" name="apkManage.updVersion" id="updVersion" value="<s:property value='apkManage.updVersion'/>"><s:property value='apkManage.updVersion'/></s:else>
    </td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">说明：</font></th>
    <td><input type="text" name="apkManage.info" id="info" value="<s:property value='apkManage.info'/>"/ style="width: 80%;"></td>
  </tr>
    <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">路径：</font></th>
    <td><input type="text" name="apkManageSupplement.url"  value="<s:property value='apkManageSupplement.url'/>"/><s:property value="apkManage.apkPath"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">二维码生成：</font></th>
    <td><input type="text" name="apkManageSupplement.qrCode" id="info" value="<s:property value='apkManageSupplement.qrCode'/>"/><s:property value="apkManage.packageId"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">测试二维码生成：</font></th>
    <td><input type="text" name="apkManageSupplement.testQrCode"  value="<s:property value='apkManageSupplement.testQrCode'/>"/><s:property value="apkManage.packageId"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">&nbsp;</font></th>
    <td>
    	<input type="submit" value=" 提交 " onclick="return doForm();"/>
    	<input type="hidden" name="apkManage.id" id="id" value="${apkManage.id}"/>
    	<input type="hidden" name="apkManage.apkName" value="${apkManage.apkName}"/>
    	<input type="hidden" name="apkManage.apkPath" value="${apkManage.apkPath}"/>
    	<input type="hidden" name="apkManage.apkSize" value="${apkManage.apkSize}"/>
    	<input type="hidden" name="apkManage.curVersion" value="${curVersion}"/>
    	<input type="hidden" name="apkManage.packageId" value="${channelId}"/>
    	<input type="hidden" name="apkManage.packageName" value="${apkManage.packageName}"/>
    	<input type="hidden" name="apkManage.versionCode" value="${apkManage.versionCode}"/>
    	<input type="hidden" name="apkManage.versionName" value="${apkManage.versionName}"/>
    	<input type="hidden" name="apkManage.versionNameCode" value="${apkManage.versionNameCode}"/>
    	<input type="hidden" name="apkManageSupplement.channelid" value="${channelId}"/>
    </td>
  </tr>
</table>  
</s:form>
<script type="text/javascript">

//表单验证
function doForm(){

	if(checkApk()&&checkUpdVersion()){
		var isValid =  document.getElementById("isValid");
		var isForce =  document.getElementById("isForce");
		
		isValid.value="0";
		isForce.value="0";
		
		if(isValid.checked){
			isValid.value="1";
		}
		if(isForce.checked){
			isForce.value="1";
		}
		return true;
	}
	
	return false;
}

function checkUpdVersion(){
	var updVersion = document.getElementById("updVersion").value;
	
	if(updVersion==""){
		alert("请输入更新版本号.");
		return false;
	}
	return true;
}
function checkApk(){
	var apkFile = document.getElementById("apkFile").value;
	var id = document.getElementById("id").value;

	if(id <= 0){
		if(apkFile==""){
			alert("请上传apk包.");
			return false;
		}
		else{
			if(/.*[\u4e00-\u9fa5]+.*$/.test(apkFile)){
				alert("上传的apk包名不能为中文名称.")
				return false;
			}
			return true;
		}
	}
	return true;
}
</script>	
</body>
