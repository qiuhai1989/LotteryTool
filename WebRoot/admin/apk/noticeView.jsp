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
		<FONT color=#ff6600>公告明细</FONT>
		<a href="javascript:history.go(-1);" style="float: left;">返回列表</a>		</td>
	</tr>
   <tr bgcolor="F2F7FF">
    <th width="15%" class="text_right"><font size="2">公告编号：</font></th>
    <td><s:property value="apkNotice.id" /></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">客户端显示：</font></th>
    <td><input type="checkbox" <s:if test="apkNotice.isValid == 1">checked</s:if>/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">公告名称：</font></th>
    <td><s:property value="apkNotice.noticeName"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">公告内容：</font></th>
    <td><s:property value="apkNotice.noticeContent"/></td>
  </tr>
  <tr bgcolor="F2F7FF">
    <th class="text_right"><font size="2">发布时间：</font></th>
    <td>
    	<fmt:parseDate var="creTime" pattern="yy-MM-dd HH:mm:ss"><s:property value="apkNotice.issueTime"/></fmt:parseDate>
    	<fmt:formatDate value="${creTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
    </td>
  </tr>
</table>  	
</body>
