<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<head>
<link href="./style/style.css" type="text/css" rel="stylesheet">
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
<table width="100%"  class="table_view">
	<tr id="cat" class="tbhead">
		<td align="center" colspan="2" background="./img/band_di.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<FONT color=#ff6600>客户端APK明细</FONT>
		<a href="javascript:history.go(-1);" style="float: left;">返回列表</a>		</td>
	</tr>
   <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">编号：</font></th>
    <td><s:property value="apkManage.id" /></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">客户端显示：</font></th>
    <td><input type="checkbox" <s:if test="apkManage.isValid == 1">checked</s:if>/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">强制更新：</font></th>
    <td><input type="checkbox" <s:if test="apkManage.isForce == 1">checked</s:if>/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">适用平台：</font></th>
    <td><s:property value="platform.name"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">适用渠道：</font></th>
    <td><s:property value="channel.realName"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">文件名：</font></th>
    <td><s:property value="apkManage.apkName"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">路径：</font></th>
  	<td>
  	<s:if test="apkManageSupplement.url!=null">
  	<a href="<s:property value="apkManageSupplement.url"/><s:property value="apkManage.apkPath"/>"><s:property value="apkManageSupplement.url"/><s:property value="apkManage.apkPath"/></a>
  	</s:if>
  	<s:else><a href="cpapk.wayin.cn:82<s:property value="apkManage.apkPath"/>">cpapk.wayin.cn:82<s:property value="apkManage.apkPath"/></a></s:else>
  	</td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">测试环境路径：</font></th>
  	<td>
  	<a href="http://wayin.5166.info:82<s:property value="apkManage.apkPath"/>">http://wayin.5166.info:82<s:property value="apkManage.apkPath"/></a>
  	</td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">二维码生成：</font></th>
    <!-- 
    <td></td>
     -->
  	 <td> <s:if test="apkManageSupplement.qrCode!=null">
  	 <a href="<s:property value="apkManageSupplement.qrCode"/><s:property value="apkManage.packageId"/>"><s:property value="apkManageSupplement.qrCode"/><s:property value="apkManage.packageId"/></a>
  	 </s:if>
  	 <s:else><a href="http://download.wayin.cn:86/getupdate.aspx?fr=<s:property value="apkManage.packageId"/>">http://download.wayin.cn:86/getupdate.aspx?fr=<s:property value="apkManage.packageId"/></a></s:else>
  	 </td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">测试环境二维码生成：</font></th>
     <td>
     <s:if test="apkManageSupplement.testQrCode!=null">
     	<a href="<s:property value="apkManageSupplement.testQrCode"/><s:property value="apkManage.packageId"/>"><s:property value="apkManageSupplement.testQrCode"/><s:property value="apkManage.packageId"/></a>
     </s:if>
     <s:else><a href="http://wayin.5166.info:88/getupdate.aspx?fr=<s:property value="apkManage.packageId"/>">http://wayin.5166.info:88/getupdate.aspx?fr=<s:property value="apkManage.packageId"/></a></s:else>
    </td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">包名：</font></th>
    <td><s:property value="apkManage.packageName"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">大小：</font></th>
    <td><s:property value="apkManage.apkSize"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">版本名称：</font></th>
    <td><s:property value="apkManage.versionName"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">当前版本号：</font></th>
    <td><s:property value="apkManage.curVersion"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">更新版本号：</font></th>
    <td><s:property value="apkManage.updVersion"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">内部版本号：</font></th>
    <td><s:property value="apkManage.versionCode"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">内部子版本号：</font></th>
    <td><s:property value="apkManage.versionNameCode"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">外网地址：</font></th>
    <td><s:property value="apkManage.netPath"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">下载方式：</font></th>
    <td>
    	<s:if test="apkManage.downType == 1">外网</s:if>
    	<s:else>内网</s:else>
    </td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">说明：</font></th>
    <td><s:property value="apkManage.info"/></td>
  </tr>
</table>  	
</body>
