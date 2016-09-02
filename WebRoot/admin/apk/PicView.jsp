<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8" />
		<title>云彩网-客服端管理-广告图管理</title>
		<link rel="stylesheet" href="/editor/themes/default/default.css" />
		<link rel="stylesheet" href="/editor/plugins/code/prettify.css" />
		<link rel="stylesheet" type="text/css" href="/css/news.css">
		<link rel="stylesheet" type="text/css" href="/css/zlstyle.css">
	</head>
	<body>
		<div class="content">
			<s:form method="post" action="/apk/apkPic.php?action=update" enctype="multipart/form-data" id="form">
				<table width="100%" border="1" bordercolor="#464646" align="center" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th width="10%" class="backTh">
								<a href='/apk/apkPic.php'>返回</a>
							</th>
							<th width="100px"></th>
							<th>
								编辑广告图
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="right" width="20%" colspan="2">
								是否显示:
								<s:hidden name="apkPic.id"></s:hidden>
								<s:hidden name="apkPic.imageAdd"></s:hidden>
								<s:hidden name="apkPic.imageAddS"></s:hidden>
								<s:hidden name="apkPic.imageAddB"></s:hidden>
							</td>
							<td>
								<s:radio name="apkPic.isValid" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" value="apkPic.isValid"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								图片:
							</td>
							<td>
								大图：<s:file label="上传" theme="simple" name="image_big"/>
								<span>
									<img src="<s:property value='apkPic.imageAddB'/>" height="45px" width="120px"/>
								</span><br/>
								中图：<s:file label="上传" theme="simple" name="image"/>
								<span>
									<img src="<s:property value='apkPic.imageAdd'/>" height="45px" width="120px"/>
								</span><br/>
								小图：<s:file label="上传" theme="simple" name="image_small"/>
								<span>
									<img src="<s:property value='apkPic.imageAddS'/>" height="45px" width="120px"/>
								</span><br/>
								<span>${str}</span>
								<span style="color:red">请上传200KB以内的图片(gif|jpeg|jpg|png)</span>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								编号：
							</td>
							<td>
								<s:textfield name="apkPic.code" onkeyup="checkPositiveInteger(this)"></s:textfield>
								<span style="color:red">请输入整数且编号不能重复</span>
							</td>
						</tr>
						<tr>
							<td align="right" width="20%" colspan="2">
								分组描述:
							</td>
							<td>
								<s:textfield name="apkPic.pciGroup"></s:textfield>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								类型：
							</td>
							<td>
								<select name="apkPic.jumpType">
								<s:iterator value="jumpTypeList" id="selectType">
									<s:if test="#selectType.value==apkPic.jumpType">
										<option value='<s:property value="value"/>' selected="selected">
											<s:property value="name" />
										</option>
									</s:if>
									<s:else>
										<option value='<s:property value="value"/>'>
											<s:property value="name" />
										</option>
									</s:else>
								</s:iterator>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								类型内容：
							</td>
							<td>
								<s:textfield name="apkPic.adContext"></s:textfield>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								位置编号：
							</td>
							<td>
								<s:textfield name="apkPic.bandpos" onkeyup="checkPositiveInteger(this)"></s:textfield>
								<span style="color:red">请输入数字</span>
							</td>
						</tr>
					</tbody>
				</table>
				<s:submit label="sumbit" value="提交"></s:submit>
			</s:form>
		</div>
	</body>
<script type="text/javascript">
function checkPositiveInteger(obj){
	obj.value=obj.value.replace(/\D/g,'');
}
</script>
</html>

