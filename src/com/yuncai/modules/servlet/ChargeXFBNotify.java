package com.yuncai.modules.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.io.PrintWriter;
import java.net.URLEncoder;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.AmountUtils;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.core.util.DBHelper;

import com.yuncai.modules.comconfig.ChangeConfig;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.oracleService.member.GiftDatailLineService;
import com.yuncai.modules.lottery.service.oracleService.member.GiftManageService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.XFBConfig;

public class ChargeXFBNotify extends HttpServlet {

	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;

	// 获取Spring上下文环境
	public Object getBean(String name) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}
		return ctx.getBean(name);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.addHeader("Expires", "0");
		response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.addHeader("Pragma", "no-cache");

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		try {
			// 获取请求交易数据
			MemberChargeLineService memberChargeLineService = (MemberChargeLineService) this.getBean("memberChargeLineService");

			GiftManageService giftManageService = (GiftManageService) this.getBean("giftManageService");

			GiftDatailLineService giftDatailLineService = (GiftDatailLineService) this.getBean("giftDatailLineService");

			MemberService memberService = (MemberService) this.getBean("memberService");

			MemberWalletService memberWalletService = (MemberWalletService) this.getBean("memberWalletService");

			IpSeeker ipSeeker = (IpSeeker) this.getBean("ipSeeker");

			MemberOperLineService memberOperLineService = (MemberOperLineService) this.getBean("memberOperLineService");

			XFBConfig xfbConfig = (XFBConfig) this.getBean("xfbConfig");

			String money = request.getParameter("money"); // 金额
			String Sid = request.getParameter("Sid"); // 渠道ID
			String OrderId = request.getParameter("OrderId"); // 订单号
			String payType = request.getParameter("payType"); // 支付方式（1银联，2支付宝、3财付通、4移动支付）
			double percent_ = 0; // 手续费比例
			String returnCount = "-11";

			// 获取手续费费率
			{
				// post到林总那
				HttpClient httpclient = new HttpClient();
				PostMethod post = new PostMethod(xfbConfig.getDeductPercentAddress());
				post.setRequestHeader("Connection", "close");

				NameValuePair param[] = new NameValuePair[2];
				param[0] = new NameValuePair("Sid", Sid);
				param[1] = new NameValuePair("payType", payType);

				post.getParams().setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
				post.setRequestBody(param);

				try {
					int result = httpclient.executeMethod(post);
					if (result == 200) {
						InputStream resStream = post.getResponseBodyAsStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
						StringBuffer resBuffer = new StringBuffer();
						String resTemp = "";
						while ((resTemp = br.readLine()) != null) {
							resBuffer.append(resTemp);
						}
						String txt = resBuffer.toString().trim();

						String[] paras = txt.split(";");

						percent_ = Double.parseDouble(paras[2].split("=")[1]);

					} else {
						returnCount = "-44";
						response.getWriter().print(returnCount);
						out.flush();
						out.close();
					}

				} catch (Exception e) {
					returnCount = "-44";
					response.getWriter().print(returnCount);
					out.flush();
					out.close();
				}
			}

			int payType_ = ChargeType.changeMap.get(Integer.parseInt(payType)).getValue();
			String RespCode = request.getParameter("RespCode"); // 支付结果响应码；返回：11
			// 成功；其它数字是代表失败
			String payNo = request.getParameter("payNo"); // 支付码

			double amount_ = Double.parseDouble(AmountUtils.changeF2Y(money.toString()));
			// double amount_ = Double.parseDouble(money);
			MemberChargeLine chargeLine = memberChargeLineService.findByIdForUpdate(OrderId);
			if ("11".equals(RespCode)) {
				Long ct = 0L;
				try {
					String _s = (String) request.getSession().getAttribute("s");
					ct = Long.parseLong(_s);
				} catch (Exception e) {
				}
				String remoteIp = request.getRemoteAddr();// inet.getHostAddress();
				// //客户端IP地址
				if (chargeLine.getStatus().getValue() == CommonStatus.YES.getValue()) {// ！！！如果不是处理成功的才处理充值
					chargeLine.setStatus(CommonStatus.YES);
					chargeLine.setSuccessDateTime(new Date());
					chargeLine.setResponseInfo(request.getRequestURL().toString());
					chargeLine.setPayedNo(payNo);
					chargeLine.setChargeType(ChargeType.getItem(payType_));
					memberChargeLineService.saveOrUpdate(chargeLine);
					// LogUtil.out("充值前1-----------------");
					returnCount = "00";
				} else {
					// 签名成功,执行充值动作
					try {
						Double formalitiesFees = chargeLine.getAmount() * percent_ * 0.01;// 计算手续费金额
						//Float.valueOf(Double.toString(amount_)).intValue()
						memberChargeLineService.charger(WalletType.DUOBAO.getValue(), amount_, "", OrderId, request.getRequestURL().toString(), payNo,
								remoteIp, ct, payType_, formalitiesFees,chargeLine.getChannel());

						// 进行活动赠送处理
						{
							{
								// 处理活动 1.先获取渠道 2.根据渠道查询是否有这个活动 3.检测是否存在
								String channlId = chargeLine.getChannel();
								String account = chargeLine.getAccount();
								List<GiftManage> listManage = giftManageService.findByChannelList(channlId, 1);
								if (!listManage.isEmpty()) {
									for (int i = 0; i < listManage.size(); i++) {
										GiftManage bean = (GiftManage) listManage.get(i);
										// 获取这个活动的信息,判断是否使用过一次
										String topNumber = bean.getTopNumber(); // 字头
										String postLimit = bean.getPostLimit(); // 限制
										Double giftRequest = bean.getGiftRequest(); // 要求
										Integer giftType = bean.getGiftType(); // 类型
										Double giftMoney = bean.getGiftMoney(); // 红包金额，单位元
										// 判断活动是否开启
										Integer is_val = bean.getIsValid() == null ? 0 : bean.getIsValid();
										if (is_val == 1) {
											// 判断要求是否大于金额
											Integer reMonery = Double.valueOf(giftRequest).intValue();
											if (amount_ >= reMonery) {
												// 判断用户是否使用过一次
												List<GiftDatailLine> listLine = giftDatailLineService.findByAccountList(account, topNumber);
												Member member = memberService.findByAccount(account);
												if (!listLine.isEmpty()) {
													// 限制如果小于就进行充值
													if (listLine.size() < Integer.parseInt(postLimit)) {
														{
															String giftId = giftManageLine(member, bean, giftMoney, memberWalletService, giftDatailLineService, ipSeeker, memberOperLineService, ip);
															postGiftInfo(giftId, bean.getTopNumber(), changeY2F(giftMoney.toString()), OrderId);
														}
													}
												} else {
													// 没有进行充值
													String giftId = giftManageLine(member, bean, giftMoney, memberWalletService, giftDatailLineService, ipSeeker, memberOperLineService, ip);
													postGiftInfo(giftId, bean.getTopNumber(), changeY2F(giftMoney.toString()), OrderId);
												}
											}

										}
									}
								}

							}
						}

					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					returnCount = "00";
					// LogUtil.out("充值前2-----------------");
				}
			} else {
				// chargeLine.setStatus(CommonStatus.NO);
				// chargeLine.setSuccessDateTime(new Date());
				// chargeLine.setResponseInfo(request.getRequestURL().toString());
				// chargeLine.setPayedNo(payNo);
				// memberChargeLineService.saveOrUpdate(chargeLine);
			}
			response.getWriter().print(returnCount);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response.getWriter() != null) {
				out.close();
			}
		}
	}

	public String giftManageLine(Member member, GiftManage bean, Double giftMoney, MemberWalletService memberWalletService, GiftDatailLineService giftDatailLineService, IpSeeker ipSeeker,
			MemberOperLineService memberOperLineService, String ip) {
		// 保存会员操作流水
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(new Integer(0));
		operLine.setCreateDateTime(new Date());
		operLine.setExtendInfo("");
		operLine.setReferer("");
		operLine.setUrl("");
		operLine.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
		operLine.setIp(ip);
		operLine.setTerminalId((long) 0);
		operLine.setOperType(MemberOperType.CHARGE_PRESENT);
		operLine.setStatus(OperLineStatus.SUCCESS);

		operLine.setChannel(bean.getChannel().getName());

		memberOperLineService.save(operLine);
		Integer operLineNo = operLine.getOperLineNo();

		try {
			memberWalletService
					.increaseAble(member, giftMoney, -1, 0, 0, operLineNo, WalletType.DUOBAO.getValue(), WalletTransType.CHARGE_PRESENT.getValue(), member.getSourceId(), bean.getGiftName());
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 保存记录
		GiftDatailLine line = new GiftDatailLine();
		line.setGiftName(bean.getGiftName());
		line.setPlatFormeId(bean.getPlatform().getId());
		line.setPlatformeName(bean.getPlatform().getName());
		line.setChannelId(bean.getChannel().getId());
		line.setChannelName(bean.getChannel().getName());
		line.setMemberId(member.getId());
		line.setAccount(member.getAccount());
		line.setAmount(giftMoney);
		// 组装编号字头
		if (DBHelper.isNoNull(member.getMobile())) {
			StringBuilder number = new StringBuilder();
			number.append(bean.getTopNumber()).append(member.getMobile().substring(5, 11));
			line.setGiftId(number.toString());
		} else {
			StringBuilder number = new StringBuilder();
			number.append(bean.getTopNumber()).append(member.getAccount());
			line.setGiftId(number.toString());
		}
		line.setCreateTimeDate(new Date());
		giftDatailLineService.save(line);

		return line.getGiftId();
	}

	public void postGiftInfo(String giftId, String topNumber, String money, String orderId) throws InterruptedException {

		ChangeConfig postConfig = (ChangeConfig) this.getBean("postConfig");

		// 生成cdkey
		String forMd5 = giftId.toString() + topNumber + money + postConfig.getChangeMap().get("GIFT").getKey();
		String Md5key = MD5.encode(forMd5, "utf-8");

		// post到林总那
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(postConfig.getChangeMap().get("GIFT").getUrlpth());
		post.setRequestHeader("Connection", "close");

		NameValuePair param[] = new NameValuePair[5];
		param[0] = new NameValuePair("RedPackageId", giftId.toString());
		param[1] = new NameValuePair("HeaderId", topNumber);
		param[2] = new NameValuePair("Md5key", Md5key);
		param[3] = new NameValuePair("Money", money);
		param[4] = new NameValuePair("OrderId", orderId);

		post.getParams().setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		post.setRequestBody(param);

		// 不成功则继续post，一共三次
		for (int i = 0; i < 3; i++) {
			try {
				int result = httpclient.executeMethod(post);
				if (result == 200) {
					InputStream resStream = post.getResponseBodyAsStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
					StringBuffer resBuffer = new StringBuffer();
					String resTemp = "";
					while ((resTemp = br.readLine()) != null) {
						resBuffer.append(resTemp);
					}
					String txt = resBuffer.toString().trim();
					if (txt.equals("00")) {
						break;// 成功之后不再post
					}
				}
				// LogUtil.out(result);
			} catch (Exception ex) {
				// LogUtil.out(ex);
			} // finally {
				// post.releaseConnection();
				// post = null;
				// httpclient = null;
			// }
			Thread.sleep(1000);// 延迟1秒执行下次
		}
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
