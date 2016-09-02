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
<iframe url=""></iframe>
<table width="100%"  class="table_view">
	<tr id="cat" class="tbhead">
		<td align="center" colspan="2" background="./img/band_di.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<FONT color=#ff6600>PUSH明细</FONT>
		<a href="javascript:history.go(-1);" style="float: left;">返回列表</a>		</td>
	</tr>
   <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">PUSH编号：</font></th>
    <td><s:property value="apkPush.id" /></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">客户端显示：</font></th>
    <td><input type="checkbox" <s:if test="apkPush.isValid == 1">checked</s:if>/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">PUSH名称：</font></th>
    <td><s:property value="apkPush.noticeName"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">PUSH类型：</font></th>
    <td>
    	<s:if test="apkPush.type==1">外链</s:if>
    	<s:elseif test="apkPush.type==2">测试</s:elseif>
    	<s:else>内链</s:else>
    </td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">连接内容：</font></th>
    <td><s:property value="apkPush.pushContent"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">PUSH时间：</font></th>
    <td><s:property value="apkPush.pushTime"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">PUSH间隔：</font></th>
    <td><s:property value="apkPush.pushSpace"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">PUSH次数：</font></th>
    <td><s:property value="apkPush.pushContent"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">连接内容：</font></th>
    <td><s:property value="apkPush.pushContent"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">PUSH广告语：</font></th>
    <td><s:property value="apkPush.pushAd"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">备注：</font></th>
    <td><s:property value="apkPush.bak"/></td>
  </tr>
</table>  	
</body>
