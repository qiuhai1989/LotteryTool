<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<head>
<link href="./style/style.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/css/zlstyle.css">
<link rel="stylesheet" href="/css/jquery-ui.css" />
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
<body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10" onload="javascript:addChannel(${apkPush.sendGroup});">
<div class="content">
			<s:form method="post" action="/apk/apkPush.php?action=save">
				<table width="100%" border="1" bordercolor="#464646" align="center" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th width="10%" class="backTh">
								<a href='/apk/apkPush.php'>返回</a>
							</th>
							<th width="100px"></th>
							<th>
								添加PUSH
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="right" width="20%" colspan="2">
								是否显示:
							</td>
							<td>
								<s:if test="apkPush.isValid==1">
									<s:radio name="apkPush.isValid" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" value="apkPush.isValid"/>
								</s:if>
								<s:else>
									<s:radio name="apkPush.isValid" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" value="0"/>
								</s:else>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								PUSH编号:
							</td>
							<td>
								<s:textfield name="apkPush.pushNo" onkeyup="checkPositiveInteger(this)"></s:textfield>
								<span style="color:red">请输入整数</span>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								PUSH名称:
							</td>
							<td>
								<s:textfield name="apkPush.name"></s:textfield>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								PUSH广告语:
							</td>
							<td>
								<s:textfield name="apkPush.pushAd"></s:textfield>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								PUSH类型：
							</td>
							<td>
								<select name="apkPush.pushContent">
									<s:iterator value="jumpTypeList" id="selectType">
										<s:if test="#selectType.value==apkPush.pushContent">
											<option value='<s:property value="value"/>' selected="selected">
												<s:property value="name"/>
											</option>
										</s:if>
										<s:else>
											<option value='<s:property value="value"/>'>
												<s:property value="name" />
											</option>
										</s:else>
									</s:iterator>
								</select>
								<font class="red">设为自选页面时，跳转页面为'页面编号 '相对应的页面</font>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								接受对象：
							</td>
							<td>
								<select name="apkPush.sendGroup" onchange="javascript:addChannel(this.value);">
									<s:iterator value="sendGroupList" id="sendGroupList">
										<s:if test="#sendGroupList.value==apkPush.sendGroup">
											<option value='<s:property value="value"/>' selected="selected">
												<s:property value="name"/>
											</option>
										</s:if>
										<s:else>
											<option value='<s:property value="value"/>'>
												<s:property value="name" />
											</option>
										</s:else>
									</s:iterator>
								</select>
								<div id="channel" style="display: none"><font color="red">请选择具体渠道</font><br/>
								<s:checkboxlist list="#request.channelList" listKey="name" listValue="realName" value="#request.channelListByID.{name}" name="apkPush.receiveUser"/>
								</div>
								<div id="use" style="display: none"><font color="red">请输入用户名，多个用户用英文逗号分开(zs,ll,ww)</font><br/>
									<s:textfield name="use_receiveUser"></s:textfield>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								地址:
							</td>
							<td>
								<s:textfield name="apkPush.address"></s:textfield>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								页面编号:
							</td>
							<td>
								<s:textfield name="apkPush.pageId"></s:textfield>
								<font class="red">对应基本设置->页面信息 中的页面，可以输入相应编号进行模糊搜索。</font>
							</td>							
						</tr>
						<tr>
							<td align="right" colspan="2">
								Push时间：
							</td>
							<td>
								<fmt:parseDate var="pushTime" pattern="yy-MM-dd HH:mm:ss"><s:property value="apkPush.pushTime"/></fmt:parseDate>
    							<fmt:formatDate value="${pushTime}" var="pushTime" pattern="yyyy-MM-dd HH:mm:ss"/>
								<input type="text" name="apkPush.pushTime" id="pushTime" value="${pushTime}" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								PUSH间隔（小时）：
							</td>
							<td>
								<s:textfield name="apkPush.pushSpace" ></s:textfield>
								<span style="color:red">推荐输入整数</span>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								PUSH次数:
							</td>
							<td>
								<s:textfield name="apkPush.pushNum" onkeyup="checkPositiveInteger(this)"></s:textfield>
								<span style="color:red">请输入整数</span>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								备注:
							</td>
							<td>
								<s:textfield name="apkPush.bak"></s:textfield>
							</td>
						</tr>
					</tbody>
				</table>
				<s:hidden name="apkPush.id"></s:hidden>
				<s:submit label="sumbit" value="提交"></s:submit>
			</s:form>
		</div>
<script src="/js/jquery-1.9.1.js"></script>
<script src="/js/jquery-ui.js"></script>
<script type="text/javascript">

$(function() {
		$("#apkPush_apkPush_pageId")
				.autocomplete(
						{
							source : function(request, response) {
								$
										.ajax({
											url : "/apk/pagesInfo.php?action=findAllList",
											dataType : "json",
											data : "search.pageId="
													+ $("#apkPush_apkPush_pageId").val(),
											success : function(data) {
												response($
														.map(
																data,
																function(item) {
																	return {
																		label : "编号："
																				+ item.pageId
																				+ ", 名称："
																				+ item.pageName,
																		value : item.pageId
																	};
																}));
											}
										});
							},
							minLength : 1,
							select : function(event, ui) {
									$("#apkPush_apkPush_pageId").val(ui.item.value);
							},
							open : function() {
								$(this).removeClass("ui-corner-all").addClass(
										"ui-corner-top");
							},
							close : function() {
								$(this).removeClass("ui-corner-top").addClass(
										"ui-corner-all");
							}
						});
				});

//表单验证
function doForm(){

	var isValid =  document.getElementById("isValid");
	isValid.value="0";
	if(isValid.checked){
		isValid.value="1";
	}
	return true;
}
function checkPositiveInteger(obj){
	obj.value=obj.value.replace(/\D/g,'');
}
function addChannel(sendGroup){
	var channel = document.getElementById("channel");
	var use = document.getElementById("use");
	if(sendGroup==1){
		channel.style.display="";
	}else{
		channel.style.display="none";
	}
	if(sendGroup==2)
		use.style.display="";
	else
		use.style.display="none";
}
</script>	
</body>
