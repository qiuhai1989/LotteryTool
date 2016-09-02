<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
	<link rel="stylesheet" type="text/css" href="../css/zlstyle.css">
	 <script type="text/javascript" src="/swfupload/swfupload.js"></script>
  <script type="text/javascript" src="/swfupload/handlers.js"></script>
  <script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
</head>
<body leftmargin="10" topmargin="10" marginwidth="10" marginheight="10">
		<table width="100%" border="1" bordercolor="#464646" align="center"
			cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td width="20%" class="backTh"><a href='/apk/apkManage.php'>返回</a></td>
					<td align="center">批量添加apk包</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="right">
						客户端显示:
					</td>
					<td>
						<input type="checkbox" id="isValid" value="0" onclick="check1();"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						是否强制更新:
					</td>
					<td>
						<input type="checkbox" id="isForce" value="0" onclick="check2();"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						是否外网:
					</td>
					<td>
						<input type="checkbox" id="downType" value="0" onclick="check3();"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						更新版本:
					</td>
					<td>
    					<input type="text" name="updVersion" id="updVersion"><font color="red">格式v1.0.0 ，v1.2.1(必须以v开头，三个数字中间两个小数点分割)</font>
					</td>
				</tr>
				<tr>
					<td align="right">
						说明:
					</td>
					<td>
						<textarea rows="" cols="" id="info"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<input type="button" onclick="swfupload();" value="添加控件"/>
		<span id="spanButtonPlaceholder"></span>
		<div id="divFileProgressContainer" style="width:200;display:none;"></div>
		<div id="thumbnails">
			<table id="infoTable" border="0" style="background-color: #C5D9FF;"></table>
		</div>
<script type="text/javascript">
var swfu;
			function swfupload() {
				swfu = new SWFUpload({
					upload_url: "/servlet/FileUploadServlet",
				// File Upload Settings
					file_size_limit : "50 MB",	// 1000MB
					file_types : "*.apk",//设置可上传的类型
					file_types_description : "所有文件",
					file_upload_limit : "100",
				// 向服务端传递的参数
	            	use_query_string : true,
					post_params: {
	                 "isValid" : document.getElementById("isValid").value,
	                 "isForce" : document.getElementById("isForce").value,
	                 "downType" : document.getElementById("downType").value,
	                 "updVersion" : document.getElementById("updVersion").value,
	                 "info" : document.getElementById("info").value
	            	},	
	            			
					file_queue_error_handler : fileQueueError,//选择文件后出错
					file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
					file_queued_handler : fileQueued,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					upload_complete_handler : uploadComplete,
	
					// Button Settings
					button_image_url : "images/SmallSpyGlassWithTransperancy_17x18.png",
					button_placeholder_id : "spanButtonPlaceholder",
					button_width: 100,
					button_height: 18,
					button_text : '<span class="button">选择APK</span>',
					button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
					button_text_top_padding: 0,
					button_text_left_padding: 18,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					
					// Flash Settings
					flash_url : "swfupload/swfupload.swf",
	
					custom_settings : {
						upload_target : "divFileProgressContainer"
					},
					// Debug Settings
					debug: false  //是否显示调试窗口
				});
			};
			function startUploadFile(){
				swfu.startUpload();
			}
			
function check1(){
 var isValid = document.getElementById("isValid").value;
 if(isValid==0)
 	document.getElementById("isValid").value="1";
 else
 	document.getElementById("isValid").value="0";

}
function check2(){
 var isForce = document.getElementById("isForce").value;
 if(isForce==0)
 	document.getElementById("isForce").value="1";
 else
 	document.getElementById("isForce").value="0";
}
function check3(){
var downType = document.getElementById("downType").value;
 if(downType==0)
 	document.getElementById("downType").value="1";
 else
 	document.getElementById("downType").value="0";
}
</script>
</body>
