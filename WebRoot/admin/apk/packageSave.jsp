<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<head>
<link href="./style/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>	
<script language="JavaScript">
<!--
function openwindow(src)
  {
  window.open(src,"","width=840,height=600,scrollbars=1");
  }
//-->
</script>

</head>
<body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
<s:form method="post" action="/apk/apkPackage.php?action=save">
<table width="100%"  class="table_view">
	<tr id="cat" class="tbhead">
		<td align="center" colspan="2" background="./img/band_di.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<FONT color=#ff6600>渠道修改</FONT>
	  <a href="/apk/apkPackage.php" style="float: left;">返回列表</a>		
	</tr>
  <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">客户端显示：</font></th>
    <td><input type="checkbox" name="apkPackage.isValid" id="isValid" value="0" <s:if test="apkPackage.isValid == 1">checked</s:if>/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">渠道名称：</font></th>
    <td><input type="text" name="apkPackage.PackageName" id="packageName" value="<s:property value='apkPackage.packageName'/>"/ style="width: 80%;"></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">渠道编码：</font></th>
    <td><input type="text" name="apkPackage.PackageCode" id="packageCode" value="<s:property value='apkPackage.packageCode'/>"/ style="width: 80%;"></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">备注：</font></th>
    <td><input type="text" name="apkPackage.bak" id="bak" value="<s:property value='apkPackage.bak'/>"/ style="width: 80%;"></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">&nbsp;</font></th>
    <td>
    	<input type="submit" value=" 提交 " onclick="return doForm();"/>
    	<input type="hidden" name="apkPackage.id" value="${apkPackage.id}"/>
    </td>
  </tr>
</table>  
</s:form>
<script type="text/javascript">

//表单验证
function doForm(){

	var isValid =  document.getElementById("isValid");
	isValid.value="0";
	if(isValid.checked){
		isValid.value="1";
	}
	return true;
}
</script>	
</body>
