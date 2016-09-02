<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">

<title>账户充值</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/member.css">
</head>

<body>
	<div id="arear" style="width:auto;padding:0px;margin:0px;">
		<div class="memberRight" style="float:left;padding:0px;">
			<div class="memberCont" style="margin:0px;">
				<div class="memberContTitle">
					<h2>账户充值</h2>
					<ul>

						<li class="memberCuuer"><a>网上支付</a>
						</li>
						<!-- <li><a href="member/phoneRecharge.php">手机充值卡</a>
						</li> -->
					</ul>
				</div>
				<div class="memberArear">
					<div class="warning">温馨提示：
						网上充值免手续费！如果您的银行卡没有开通网上银行，请使用银联手机支付。</div>
					<div class="prompt">
						<span class="bold">网上支付帮助：</span>1）确认你已经开通网上银行的支付功能。如何开通？2）关闭弹出窗口的拦截功能。3）务必使用IE5.0以上浏览器。4）支付出错后请勿按后退键，请刷新或重新下单；多次出错请清除IE缓存。
						<p>
							<span class="bold">异常情况的处理：</span>正常情况下，网银在线充值为实时到账。如果银行扣款成功，但云彩账户缺没有添加余额，这种情况请及时联系在线客服，我们核实后立即为您手动添加余额。
						</p>
					</div>
					<div class="rechargePay">
						<s:form action="paypalRecharge" name="formRecgarge" method="post"
							onsubmit="return checkForm();" target="_blank">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tbody>
									<tr>
										<td width="19%" align="right">充值金额：</td>
										<td width="31%"><s:textfield name="money" label="充值金额"
												cssClass="inputBox" onKeyDown="return onlyNumber(event)"
												onBlur="checkNumber(this)" onpaste="return false" /></td>
										<td width="50%">存入至少10元，<span class="red">充值后必须消费30%才能提款！</span>
										</td>
									</tr>
									<tr>
										<td align="right" valign="middle">选择网上银行：</td>
										<td colspan="2"><table width="100%" border="0"
												align="left" cellpadding="0" cellspacing="0"
												class="smallTable">
												<tbody>
													<tr>
														<td width="3%" align="left"><s:radio name="bank"
																list="#{'ZFB':''}"></s:radio></td>
														<td width="23%" align="left"><label
															for="paypalRecharge_bankZFB"><img
																src="images/zfb.jpg"> </label></td>
														<td width="3%" align="center"><s:radio name="bank"
																list="#{'CFT':''}"></s:radio></td>
														<td width="71%" align="left"><label
															for="paypalRecharge_bankCFT"> <img
																src="images/cft.jpg"> </label></td>
													</tr>
													<tr>
														<td width="3%" align="left"><s:radio name="bank"
																list="#{'CMM':''}"></s:radio></td>
														<td width="23%" align="left"><label
															for="paypalRecharge_bankCMM"> <img
																src="images/mobilePayment.jpg" alt="手机支付"> </label></td>
														<td width="3%" align="center"></td>
														<td width="71%" align="left"></td>
													</tr>
												</tbody>
											</table></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td colspan="2" valign="top"><s:submit label="sumbit"
												method="change" value="立 即 支 付" cssClass="userAlter"></s:submit><a
											href="#" class="tutorials">网上支付指南_流程</a></td>
									</tr>
								</tbody>
							</table>
						</s:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="lockDiv" id="lockDiv"></div>
	<div class="winArear" id="winCont">
	<div class="winCont">
		<div class="winTitle"><span class="winPay">温馨提示</span><a href="javascript:finishPay();">X 关闭</a>&nbsp;</div>
		<div class="winPayment">付款前请不要关闭此窗口。<br />完成付款后请根据您的情况点击下面的按钮：</div>
		<div class="winBtn">
			<a href="javascript:finishPay();" class="payingBtn">已完成付款</a>
			<a href="javascript:showHelp()" class="problemBtn">付款遇到问题</a>
		</div>
		<div class="winReturn">
			<a href="javascript:closeConfirmPanel()">返回选择其他支付方式</a>
		</div>
	</div>
  </div>	
</body>
<script type="text/javascript" src="/js/jquery-1.10.1.js"></script>
<script type="text/javascript">	
	function checkForm() {
		var money = document.getElementById("paypalRecharge_money").value;
		if (money < 1) {
			alert("充值金额不能少于1元");
			return false;
		}
		$("#lockDiv").show();
		$("#winCont").show();
		return true;
	}
	//检查是否是数字
	function checkNumber(obj) {	
		//消除前面的0
		obj.value=parseInt(obj.value);
		obj.value = obj.value.replace(/\D/g, "");
		//不能是0
		if(obj.value==0){
		obj.value="";
		}
	}
	//只能输入数字和返回键
	function onlyNumber(e)
	{
		var keynum;
		
		if(window.event) // IE
 		 {
 		 keynum = e.keyCode;
		  }
		else if(e.which) // Netscape/Firefox/Opera
 		 {
 		 keynum = e.which;
  		}
  		
		if(keynum==8||(keynum>=48&&keynum<=57)||(keynum>=96&&keynum<=105)&&!e.ctrlKey){
			return true;
		}
		
		return false;
}	

	function chkLast(obj) {
		// 如果出现非法字符就截取掉 
		if (obj.value.substr((obj.value.length - 1), 1) == '.')
			obj.value = obj.value.substr(0, (obj.value.length - 1));
	}
	
	//关闭确认信息窗口
	function closeConfirmPanel(){
		$("#lockDiv").hide();
		$("#winCont").hide();
		
	}
	
	var parentHref="http://wayin.5166.info:8081";
	function finishPay(){
		window.parent.location.href=parentHref+"/Home/Room/ViewAccount6636.aspx";
	}
	
	function showHelp(){
		window.parent.location.href=parentHref+"/Help/help_Send.aspx";
	}
</script>
</html>
