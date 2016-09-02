package com.yuncai.modules.lottery.action.member;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.ServerException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.apache.struts2.ServletActionContext;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import oracle.net.ano.SupervisorService;

import com.yuncai.core.common.Constant;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.modules.comconfig.ChangeConfig;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberChargeLineDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;

@Controller("PaypalRechargeAction")
@Scope("prototype")
public class PaypalRechargeAction extends BaseAction {
	@Resource
	private MemberService memberService;
	@Resource
	private MemberWalletService memberWalletService;
	@Resource
	private ChangeConfig changeConfig;
	@Resource
	private MemberChargeLineService memberChargeLineService;

	private String money = null;
	private String bank = "CMM";
	private String orderid = null;
	private String cdkey = null;
	private String sid = null;
	private String username = null;
	private String pwd = null;
	private String respCode = null;
	private String payNo = null;
	private String successPath = null;
	private String errorPath = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	public String index() {
		// super.errorMessage=new
		// String(super.errorMessage.getBytes("ISO-8859-1"),"UTF-8");
		// super.forwardUrl = "login.php";
		Member member = new Member();
		// 验证登陆
		// Member memberSession = (Member)
		// request.getSession().getAttribute(Constant.MEMBER_CHARGE_SESSION);
		// if (memberSession == null) {
		// 判断用户是否存在
		if (username == null && "".equals(username)) {
			super.errorMessage = "对不起，用名不匹配！";
			super.forwardUrl = "/jsp/member/error.jsp?errorMessage=" + errorMessage;
			return ERROR;
		}
		List<Member> list = memberService.findByProperty("account", username);
		if (list == null || list.isEmpty()) {
			super.errorMessage = "对不起，用户名不存在！";
			super.forwardUrl = "/jsp/member/error.jsp?errorMessage=" + errorMessage;
			return ERROR;
		}
		member = list.get(0);
		// 判断密码是否正确
		if (!member.getPassword().equals(pwd)) {
			super.errorMessage = "对不起，密码错误！";
			super.forwardUrl = "/jsp/member/error.jsp?errorMessage=" + errorMessage;
			return ERROR;
		}
		super.getSession().put(Constant.MEMBER_CHARGE_SESSION, member);
		// }
		money = changeF2Y(money);
		return INDEX;
	}

	public String change() throws UnsupportedEncodingException {
		// 验证登陆
		Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_CHARGE_SESSION);
		if (memberSession == null) {
			super.errorMessage = "对不起，请先登录！";
			super.forwardUrl = "/jsp/member/error.jsp?errorMessage=" + errorMessage;
			return ERROR;
		}

		// 充值金额小于10
		if (Double.parseDouble(money) < 1) {
			super.errorMessage = "充值金额小于1";
			super.forwardUrl = "/jsp/member/error.jsp?errorMessage=" + errorMessage;
			return ERROR;
		}

		// 获取请求地址
		changeConfig = changeConfig.getChangeMap().get(bank);
		String urlPath = changeConfig.getUrlpth();

		// 创建支付订单
		MemberChargeLine memberChargeLine = new MemberChargeLine();
		memberChargeLine.setAccount(memberSession.getAccount());
		memberChargeLine.setAmount(Double.parseDouble(money));
		memberChargeLine.setBank(bank);
		memberChargeLine.setCreateDateTime(new Date());
		memberChargeLine.setStatus(CommonStatus.NO);

		// 支付宝充值
		if (bank.equals("ZFB")) {
			memberChargeLine.setChargeType(ChargeType.ZFB);
		}
		// 财付通充值
		if (bank.equals("CFT")) {
			memberChargeLine.setChargeType(ChargeType.CFT);
		}
		// 移动充值
		if (bank.equals("CMM")) {
			memberChargeLine.setChargeType(ChargeType.CMM);
		}

		try {
			// 生成充值订单
			memberChargeLineService.saveNumber(memberChargeLine);
		} catch (Exception e) {
			super.errorMessage = "对不起，系统出错，请重试！";
			super.forwardUrl = "/jsp/member/error.jsp?errorMessage=" + errorMessage;
			return ERROR;
		}

		// 元转换为分
		money = changeY2F(money);

		// money = "1";

		// 生成cdkey
		String forMd5 = money + memberSession.getAccount() + memberChargeLine.getChargeNo() + changeConfig.getKey();
		cdkey = MD5.encode(forMd5, "utf-8");

		// 提交支付请求URL
		// super.forwardUrl = "/member/paypalRecharge.php?money=" + money
		// +"&bank="+ bank +"&successPath=" + urlPath + "?money=" + money +
		// "|sid=" + memberSession.getAccount()
		// + "|orderId=" + memberChargeLine.getChargeNo() + "|cdkey=" + cdkey +
		// "|returnPath=" + changeConfig.getReturnPath() + "|returnNotity="
		// + changeConfig.getReturnNotity();
		super.forwardUrl = urlPath + "?money=" + money + "&sid=" + memberSession.getAccount() + "&orderId=" + memberChargeLine.getChargeNo()
				+ "&cdkey=" + cdkey + "&returnPath=" + changeConfig.getReturnPath() + "&returnNotity=" + changeConfig.getReturnNotity();

//		LogUtil.out("==============money=" + money + "&sid=" + memberSession.getAccount() + "&orderId=" + memberChargeLine.getChargeNo() + "&cdkey="
//				+ cdkey);

		return INPUT;
	}

	public String resultPath() throws UnsupportedEncodingException {
		return result();
	}

	// 处理返回结果
	public String result() throws UnsupportedEncodingException {
		LogUtil.out("==================================" + request.getQueryString());
		sid = request.getParameter("sid");
		money = request.getParameter("money");
		orderid = request.getParameter("orderId");
		cdkey = request.getParameter("cdkey");
		respCode = request.getParameter("respCode");
		payNo = request.getParameter("payNo");
		// 交易成功
		if (respCode != null && respCode.equals("11")) {
			// 查找支付订单
			List<MemberChargeLine> list = memberChargeLineService.findByProperty("chargeNo", orderid);
			if (list != null && !list.isEmpty()) {
				MemberChargeLine memberChargeLine = list.get(0);
				String forMd5 = money + sid + orderid + changeConfig.getChangeMap().get(memberChargeLine.getBank()).getKey();
				String myDigest = MD5.encode(forMd5, "utf-8");
				// 验证CDkey
				if (myDigest.equals(cdkey)) {
					// 订单金额单位转换为分
					String myAmount = changeY2F(memberChargeLine.getAmount().toString());
					// 验证金额
					if (money.equals(myAmount)) {
						// 验证订单状态
						if (memberChargeLine.getStatus().getValue() == 0) {
							Long ct = 0L;
							try {
								String _s = super.getSession().get("s").toString();
								ct = Long.parseLong(_s);
							} catch (Exception e) {
							}
							try {
								String ip = super.remoteIp;
								Double moneyY = Double.parseDouble(changeF2Y(money));
								// 进行账户充值
								memberChargeLineService.charger(1, moneyY, "", orderid, request.getRequestURI() + "?" + request.getQueryString(),
										payNo, ip, ct, memberChargeLine.getChargeType().getValue(),0,memberChargeLine.getChannel());
								try {
									Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
									memberSession.setWallet(memberWalletService.findByAccount(memberSession.getAccount()));
								} catch (Exception e) {
									// TODO: handle exception
								}
								// super.forwardUrl="http://192.168.0.157/Home/Room/OnlinePay/OK6636.aspx?username="+memberChargeLine.getAccount()+"&money="+moneyY.toString();
								return SUCCESS;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								super.errorMessage = "充值失败" + e.getMessage();
								LogUtil.out("===================ERROR：" + super.errorMessage);
								return ERROR;
							}
						} else {
							super.errorMessage = "充值失败,此订单状态为:已成功充值";
							LogUtil.out("=========================ERROR:" + super.errorMessage);
						}
					} else {
						super.errorMessage = "金额不匹配";
						LogUtil.out("=========================ERROR:" + super.errorMessage);
					}
				} else {
					super.errorMessage = "CDKey不匹配";
					LogUtil.out("=========================ERROR:" + super.errorMessage);
				}
			} else {
				super.errorMessage = "没有此订单";
				LogUtil.out("=========================ERROR:" + super.errorMessage);
			}

		} else {
			super.errorMessage = "状态为交易失败";
			LogUtil.out("=========================ERROR:" + super.errorMessage);
		}

		super.forwardUrl = "/member/paypalRecharge.php?errorMessage=" + URLEncoder.encode(super.errorMessage, "UTF-8");

		return ERROR;
	}

	/**
	 * 将分为单位的转换为元 （除100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeF2Y(String amount) {
		if (amount != null) {
			Integer moneyF = Integer.valueOf(amount);
			Integer moneyY = moneyF / 100;
			return moneyY.toString();
		}
		return null;
	}

	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
		// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setrespCode(String respCode) {
		this.respCode = respCode;
	}

	public void setpayNo(String payNo) {
		payNo = payNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberChargeLineService(MemberChargeLineService memberChargeLineService) {
		this.memberChargeLineService = memberChargeLineService;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCdkey() {
		return cdkey;
	}

	public void setCdkey(String cdkey) {
		this.cdkey = cdkey;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSuccessPath() {
		return successPath;
	}

	public void setSuccessPath(String successPath) {
		this.successPath = successPath;
	}

	public String getErrorPath() {
		return errorPath;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

}
