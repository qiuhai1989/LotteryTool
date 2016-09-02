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
	<div id="headerWapper">
		<div id="header">
			<div class="headerSub">
				<span class="subMenu"><a href="#">首页</a> | <a href="#">帮助中心</a>
					| <a href="#">关于我们</a> </span>欢迎来到云彩大赢家！<a href="#">请登录</a> | <a href="#">免费注册</a>
			</div>
			<div class="headerTop">
				<div class="logo">
					<img src="images/logo.jpg" alt="云彩大赢家">
				</div>
				<div class="service">
					<img src="images/service.jpg" alt="在线客服">
				</div>
			</div>
			<div class="headerMenu">
				<div class="types">
					<span class="arrow"><img src="images/bottom.gif">
					</span>彩票投注导航
					<div class="typesCont" style="display:none">
						<ul>
							<span class="typesTitle01">竞彩：</span>
							<li><a href="#">竞彩足球</a>
							</li>
							<li>竞彩篮球</li>
						</ul>
						<ul>
							<span class="typesTitle02">福彩：</span>
							<li>双色球</li>
							<li>福彩3D</li>
							<li>七乐彩</li>
						</ul>
						<ul>
							<span class="typesTitle03">高频：</span>
							<li>时时彩</li>
							<li>十一运夺金</li>
							<li>11选5</li>
							<li>时时乐</li>
						</ul>
						<ul>
							<span class="typesTitle04">足彩：</span>
							<li><a href="#">足彩胜负</a>
							</li>
							<li><a href="#">任选9场</a>
							</li>
							<li><a href="#">6场半全</a>
							</li>
							<li><a href="#">4场进球</a>
							</li>
						</ul>
						<ul style="border-bottom:none">
							<span class="typesTitle05">体彩：</span>
							<li><a href="#">超级大乐透</a>
							</li>
							<li><a href="#">七星彩</a>
							</li>
							<li><a href="#">排列三/五</a>
							</li>
							<li><a href="#">2选5</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="menu">
					<ul>
						<li class="menuTip"><a href="index.html">首页</a>
						</li>
						<li class="line"></li>
						<li><a href="#">合买跟单</a>
						</li>
						<li class="line"></li>
						<li><a href="#">全国开奖</a>
						</li>
						<li class="line"></li>
						<li><a href="#">擂台比拼</a>
						</li>
						<li class="line"></li>
						<li><a href="#">过关统计</a>
						</li>
						<li class="line"></li>
						<li><a href="#">图表走势</a>
						</li>
						<li class="line"></li>
						<li><a href="#">指数中心</a>
						</li>
						<li class="line"></li>
						<li><a href="#">即时比分</a>
						</li>
						<li class="line"></li>
						<li><a href="#">赛事资料</a>
						</li>
					</ul>
					<span class="mobileLottery"><a href="#">手机购彩</a> </span>
				</div>
			</div>
		</div>
	</div>

	<div id="arear">
		<div class="current">
			您现在的位置：<a href="#">首页</a> &gt; <span class="red">我的账户</span> &gt;
			账户充值
		</div>
		<div class="memberLeft">
			<div class="memberNavmenu">
				<span class="account">我的账户</span>
				<ul>
					<li class="memberTip"><a href="#">帐户充值</a>
					</li>
					<li><a href="#">我要提款</a>
					</li>
					<li><a href="#">账户全览</a>
					</li>
					<li><a href="#">交易明细</a>
					</li>
				</ul>
			</div>
			<div class="memberNavmenu">
				<span class="record">我的彩票记录</span>
				<ul>
					<li><a href="#">投注查询</a>
					</li>
					<li><a href="#">我的自动跟单</a>
					</li>
					<li><a href="#">我的追号</a>
					</li>
					<li><a href="#">我的站内信</a>
					</li>
				</ul>
			</div>
			<div class="memberNavmenu">
				<span class="data">我的资料</span>
				<ul>
					<li><a href="#">修改基本资料</a>
					</li>
					<li><a href="#">手机验证</a>
					</li>
					<li><a href="#">邮箱激活</a>
					</li>
					<li><a href="#">修改密码</a>
					</li>
					<li><a href="#">设置安全问题</a>
					</li>
					<li><a href="#">绑定银行卡</a>
					</li>
				</ul>
			</div>
			<div class="memberNavmenu">
				<span class="integral">我的积分</span>
				<ul>
					<li><a href="#">我的积分明细</a>
					</li>
					<li><a href="#">积分兑换</a>
					</li>
				</ul>
			</div>
			<div class="memberNavmenu">
				<span class="spread">我的推广</span>
				<ul>
					<li><a href="#">我推广的会员</a>
					</li>
					<li><a href="#">佣金提款</a>
					</li>
				</ul>
			</div>

		</div>
		<div class="memberRight">
			<div class="memberTitle">
				您好，<span class="memberUser"><s:property value="username" />！</span>您当前帐户余额为：<span
					class="memberMoney">￥<s:property value="ableBalance" /> </span> 元
			</div>
			<div class="memberCont">
				<div class="memberContTitle">
					<h2>账户充值</h2>
					<ul>

						<li class="memberCuuer"><a href="member/paypalRecharge.php">网上支付</a>
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
							onsubmit="return checkForm();">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tbody>
									<tr>
										<td width="19%" align="right">充值金额：</td>
										<td width="31%"><s:textfield name="money" label="充值金额"
												cssClass="inputBox" onKeyDown="return onlyNumber(event)" onBlur="checkNumber(this)" onpaste="return false"  />
										</td>
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
																list="#{'ZFB':''}"></s:radio>
														</td>
														<td width="23%" align="left">
															<label for="paypalRecharge_bankZFB"><img
															src="images/zfb.jpg"></label>
														</td>
														<td width="3%" align="center"><s:radio name="bank"
																list="#{'CFT':''}"></s:radio>
														</td>
														<td width="71%" align="left">
														<label for="paypalRecharge_bankCFT">
														<img
															src="images/cft.jpg"></label>
														</td>
													</tr>
													<tr>
														<td width="3%" align="left"><s:radio name="bank"
																list="#{'CMM':''}"></s:radio>
														</td>
														<td width="23%" align="left"><label for="paypalRecharge_bankCMM">
														<img
															src="../images/mobilePayment.jpg" alt="手机支付"></label>
														</td>
														<td width="3%" align="center">
														</td>
														<td width="71%" align="left">	
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td colspan="2" valign="top"><s:submit label="sumbit"
												method="change" value="" cssClass="rechargeBtn"></s:submit><a
											href="#" class="tutorials">网上支付指南_流程 视频</a>
										</td>
									</tr>
								</tbody>
							</table>
						</s:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		<div class="navMenu">
			<a href="#">About dyjw.com</a> | <a href="#">联系我们</a> | <a href="#">诚聘英才</a>
			| <a href="#">版权声明</a> | <a href="#">隐私保护</a> | <a href="#">网站地图</a>
		</div>
		<div class="copyFooter">
			Copyright @ 2004-2013 dyjw.com All Right Reserved. 天夏科技 版权所有<br>
			粤ICP备 10072756号 -1 | 粤网文【2001】0315-043 | 增值业务许可证：B2-20090063<br>
			<img src="images/report.jpg" alt="不良信息举报">&nbsp;&nbsp;<img
				src="images/record.jpg" alt="备案">
		</div>
	</div>
</body>
<script type="text/javascript">
	//var  username = '<s:property value="username"/>';
	//alert(username);
	var errorMessage=decodeURIComponent('<s:property value="errorMessage"/>');
	var errorMessage=unescape('<s:property value="errorMessage"/>');
	if(errorMessage!=""){
	alert(errorMessage);
	}
	
	function checkForm() {
		var money = document.getElementById("paypalRecharge_money").value;
		if (money < 10) {
			alert("充值金额不能少于10元");
			return false;
		}
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
		//var keychar;
		//var numcheck;

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
		//keychar = String.fromCharCode(keynum);
		//numcheck = /[^\d]/g;
		//var temp =numcheck.test(keychar);
		//return !numcheck.test(keychar);
		return false;
}	
	// function onlyNumber(obj){
	//obj.value = obj.value.replace(/[^\d.]/g,""); 
	////必须保证第一位为数字而不是. 
	//obj.value = obj.value.replace(/^\./g,""); 
	////保证只有出现一个.而没有多个. 
	//obj.value = obj.value.replace(/\.{2,}/g,"."); 
	////保证.只出现一次，而不能出现两次以上 
	//obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
	//} 

	function chkLast(obj) {
		// 如果出现非法字符就截取掉 
		if (obj.value.substr((obj.value.length - 1), 1) == '.')
			obj.value = obj.value.substr(0, (obj.value.length - 1));
	}
</script>
</html>
