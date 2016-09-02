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
					<span class="mobileLottery"><a href="#">手机购彩</a>
					</span>
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
					class="memberMoney">￥<s:property value="ableBalance" />
				</span> 元
			</div>
			<div class="memberCont">
				<div class="memberContTitle">
					<h2>账户充值</h2>
					<ul>
						<li><a href="member/paypalRecharge.php">网上支付</a>
						</li>
						<li class="memberCuuer"><a href="member/phoneRecharge.php">手机充值卡</a>
						</li>
					</ul>
				</div>
				<div class="memberArear">
					<div class="warning">请务必选择与充值卡面值相同的金额，如果选择不一致，将无法给您退还差额或损失部分。</div>
					<div class="recharge">
						<s:form action="phoneRecharge" name="formRecgarge" method="post"
							onsubmit="return checkForm()">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tbody>
									<tr>
										<td width="2%" rowspan="3" align="right">&nbsp;</td>
										<td colspan="2"><span class="blackBold">1.选择充值卡的类型</span>
										</td>
									</tr>
									<tr>
										<td colspan="2"><table width="100%" border="0"
												align="left" cellpadding="0" cellspacing="0">
												<tbody>
													<tr>
														<td width="6%" align="center"><s:radio name="company"
																list="#{0:''}" onclick="changeCompany()"></s:radio>
														</td>
														<td width="20%" align="left"><img
															src="images/mobile.jpg">
														</td>
														<td width="4%" align="right"><s:radio name="company"
																list="#{1:''}" onclick="changeCompany()"></s:radio>
														</td>
														<td width="20%" align="left"><img
															src="images/unicom.jpg">
														</td>
														<td width="4%" align="right"><s:radio name="company"
																list="#{2:''}" onclick="changeCompany()"></s:radio>
														</td>
														<td width="45%" align="left"><img
															src="images/telecom.jpg">
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="2" valign="bottom"><span class="blackBold">2.确认充值卡面值</span><span
											class="red">（运营商将收取<span class="memberMoney">4%</span>的服务费，从充值金额中直接扣除）</span>
										</td>
									</tr>
									<tr>
										<td colspan="2" rowspan="3">&nbsp;</td>
										<td width="98%" valign="bottom"><s:radio name="money"
												list="#{30:''}"></s:radio> <span class="orange">30</span><span>
												元 服务商收取 2.1元手续费用，实际到账 <span class="red">27.9</span> 元</span>
										</td>
									</tr>
									<tr>
										<td valign="bottom"><s:radio name="money" list="#{50:''}"></s:radio>
											<span class="orange">50</span><span> 元 服务商收取
												3.5元手续费用，实际到账 <span class="red">46.5</span> 元</span></td>
									</tr>
									<tr>
										<td valign="bottom"><s:radio name="money"
												list="#{100:''}"></s:radio> <span class="orange">100</span><span>
												元 服务商收取 7元手续费用，实际到账 <span class="red">93</span> 元</span>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td colspan="2" valign="bottom"><span class="blackBold">3.输入充值卡号和密码</span>
										</td>
									</tr>
									<tr>
										<td colspan="2" rowspan="3">&nbsp;</td>
										<td valign="bottom"><span>充值卡号：</span> <s:textfield
												name="card" label="充值卡号" cssClass="inputBox"
												onKeyUp="onlyNumber(this,1)" /> <span class="gray" id="cardNoS"><span
												id="cardNoC">17</span>位数字,已输入<span
												id="cardNo">0</span>位。</span>
										</td>
									</tr>
									<tr>
										<td valign="bottom"><span>充值密码：</span> <s:textfield
												name="password" label="充值密码" cssClass="inputBox"
												onKeyUp="onlyNumber(this,2)" /> <span class="gray" id="cardPswS"><span
												id="cardPswC">18</span>位数字,已输入<span id="cardPsw">0</span>位。</span>
										</td>
									</tr>
									<tr>
										<td valign="bottom">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:submit label="sumbit" value="" cssClass="rechargeBtn"></s:submit>
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
	//  var  username = '<s:property value="username"/>';
	//  var  money = '<s:property value="money"/>';
	//  var  company= '<s:property value="company"/>';
	//  var  password = '<s:property value="password"/>';
	//  var  mobile = '<s:property value="mobile"/>';
	//alert("username:"+username+",money:"+money+",password:"+password+",mobile:"+mobile+"company:"+company);
	var company0;
	var company1;
	var company2;
	var money0;
	var money1;
	var money2;
	var cardNo = document.getElementById("cardNo");
	var cardPsw = document.getElementById("cardPsw");
	var cardNoC = document.getElementById("cardNoC");
	var cardPswC = document.getElementById("cardPswC");
	var cardNoS = document.getElementById("cardNoS");
	var cardPswS = document.getElementById("cardPswS");
	
	//获取用户选取的值
	function getObj() {
		company0 = document.getElementById("phoneRecharge_company0").checked;
		company1 = document.getElementById("phoneRecharge_company1").checked;
		company2 = document.getElementById("phoneRecharge_company2").checked;
		money0 = document.getElementById("phoneRecharge_money30").checked;
		money1 = document.getElementById("phoneRecharge_money50").checked;
		money2 = document.getElementById("phoneRecharge_money100").checked;
	}

	//改变充值卡类型
	function changeCompany() {
		getObj(0);
		if (company0 == true) {
			cardNoC.innerHTML = "17";
			cardPswC.innerHTML = "18";
		}
		if (company1 == true) {
			cardNoC.innerHTML = "15";
			cardPswC.innerHTML = "15";
		}
		if (company2 == true) {
			cardNoC.innerHTML = "19";
			cardPswC.innerHTML = "18";
		}
		checkNo(1);
		checkNo(2);
	}
	
	//提交表单时检查
	function checkForm() {
		//获取用户选择的值
		getObj();
		//检查是否选择了运营商
		if (company0 == false && company1 == false && company2 == false) {
			alert("请选择运营商");
			return false;
		}
		//检查是否选择了充值金额
		if (money0 == false && money1 == false && money2 == false) {
			alert("请选择充值金额");
			return false;
		}
		//检查充值卡号格式是否正确
		if (parseInt(cardNo.innerHTML) != parseInt(cardNoC.innerHTML)) {
			alert("请输入正确的充值卡号码");
			return false;
		}
		//检查充值卡密码格式是否正确
		if (parseInt(cardPsw.innerHTML) != parseInt(cardPswC.innerHTML)) {
			alert("请输入正确的充值卡密码");
			return false;
		}
		return true;
	}

	function onlyNumber(obj, i) {
		//只能输入数字
		obj.value = obj.value.replace(/\D/g, "");
		if(i==1){cardNo.innerHTML = obj.value.length;}
		if(i==2){cardPsw.innerHTML = obj.value.length;}
		checkNo(i);
	}
	
	//检查格式
	function checkNo(i){
	if (i == 1) {
			
			//如果充值卡号位数不等于规定数值，则标红色提示
			if (parseInt(cardNo.innerHTML) != parseInt(cardNoC.innerHTML)) {
				cardNoS.style.color = "red";
			} else {
				cardNoS.style.color = "";
			}
		}
		//如果充值卡密码位数不等于规定数值，则标红色提示
		if (i == 2) {
			
			if (parseInt(cardPsw.innerHTML)!= parseInt(cardPswC.innerHTML)) {
				cardPswS.style.color = "red";
			} else {
				cardPswS.style.color = "";
			}
		}
	}
</script>
</html>
