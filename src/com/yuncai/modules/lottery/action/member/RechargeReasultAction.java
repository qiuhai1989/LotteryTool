package com.yuncai.modules.lottery.action.member;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.ano.SupervisorService;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.modules.comconfig.ChangeConfig;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;

@Controller("RechargeReasultAction")
@Scope("prototype")
public class RechargeReasultAction extends BaseAction {
	@Resource
	private ChangeConfig changeConfig;
	@Resource
	private MemberChargeLineService memberChargeLineService;
	@Resource
	private MemberWalletService memberWalletService;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	private String money = null;
	private String orderid = null;
	private String cdkey = null;
	private String sid = null;
	private String respCode = null;
	private String payNo = null;
	private String responseString = "-11";// 充值失败

	public String index() throws UnsupportedEncodingException {
		// 处理返回结果
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

								// 返回充值成功结果
								responseString = "00";
								super.renderTextHtml(responseString);

								// post到前端通知更新用户余额缓存
								HttpClient httpclient = new HttpClient();
								PostMethod post = new PostMethod(changeConfig.getChangeMap().get(memberChargeLine.getBank()).getReturnPath());
								post.setRequestHeader("Connection", "close");

								NameValuePair param[] = new NameValuePair[2];
								param[0] = new NameValuePair("type", "1");
								param[1] = new NameValuePair("name", memberChargeLine.getAccount());

								post.getParams().setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
								post.setRequestBody(param);
								httpclient.executeMethod(post);
								
//								if ("00".equals(responseString)) {
//									// super.forwardUrl=changeConfig.getReturnPath()+"?sid="+sid+"&money="+money+"&orderid"+orderid+"&cdkey"+cdkey+"&respCode"+respCode+"&payNo"+payNo;
//									super.forwardUrl = changeConfig.getChangeMap().get(memberChargeLine.getBank()).getReturnPath() + "?type=1&name="
//											+ memberChargeLine.getAccount();
//									return "net6636";
//								}

								try {
									Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
									memberSession.setWallet(memberWalletService.findByAccount(memberSession.getAccount()));
								} catch (Exception e) {
									// TODO: handle exception
								}
								// super.forwardUrl="http://192.168.0.157/Home/Room/OnlinePay/OK6636.aspx?username="+memberChargeLine.getAccount()+"&money="+moneyY.toString();
							} catch (Exception e) {
								// TODO Auto-generated catch block

								// 返回充值失败结果
								super.renderTextHtml(responseString);

								e.printStackTrace();
								super.errorMessage = "充值失败" + e.getMessage();
								LogUtil.out("===================ERROR：" + super.errorMessage);

							}
						} else {

							// 返回充值成功结果
							responseString = "00";
							super.renderTextHtml(responseString);

							super.errorMessage = "充值失败,此订单状态为:已成功充值";
							LogUtil.out("=========================ERROR:" + super.errorMessage);
						}
					} else {
						// 返回充值失败结果
						super.renderTextHtml(responseString);

						super.errorMessage = "金额不匹配";
						LogUtil.out("=========================ERROR:" + super.errorMessage);
					}
				} else {
					// 返回充值失败结果
					super.renderTextHtml(responseString);

					super.errorMessage = "CDKey不匹配";
					LogUtil.out("=========================ERROR:" + super.errorMessage);
				}
			} else {
				// 返回充值失败结果
				super.renderTextHtml(responseString);

				super.errorMessage = "没有此订单";
				LogUtil.out("=========================ERROR:" + super.errorMessage);
			}

		} else {
			// 返回充值失败结果
			super.renderTextHtml(responseString);

			super.errorMessage = "状态为交易失败";
			LogUtil.out("=========================ERROR:" + super.errorMessage);
		}

		super.forwardUrl = "/member/paypalRecharge.php?errorMessage=" + URLEncoder.encode(super.errorMessage, "UTF-8");
		return INDEX;
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
}
