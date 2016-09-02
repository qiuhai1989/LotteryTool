package com.yuncai.modules.lottery.software.lottery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.comconfig.ChangeConfig;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.service.oracleService.card.CardService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import com.yuncai.modules.lottery.software.service.XFBConfig;

public class CardRechargeNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private CardService cardService;
	private ChangeConfig changeConfig;
	private XFBConfig xfbConfig;
	private IpSeeker ipSeeker;
	private MemberOperLineService memberOperLineService;
	private MemberChargeLineService memberChargeLineService;
	private MemberService memberService;

	@SuppressWarnings("unchecked")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null;
		String cardId = null;// 彩金卡ID
		String version=null;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element cardCharge = bodyEle.getChild("cardCharge");
		String message = "组装节点错误!";
		try {

			userName = cardCharge.getChildText("account");
			cardId = cardCharge.getChildText("cardId");
			 // 验证登陆
			 Member memberSession = (Member)
			 request.getSession().getAttribute(
			 Constant.MEMBER_LOGIN_SESSION);
			 if (memberSession == null) {
			 return PackageXml("2000", "请登录!", "9054", agentId);
			 }
			 if (!userName.equals(memberSession.getAccount())) {
			 return PackageXml("9001", "用户名与Cokie用户不匹配", "9054", agentId);
			 }
			 version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			//Member memberSession = memberService.findByAccount(userName);//测试

			Card card = cardService.find(Integer.parseInt(cardId));

			if (card == null) {
				super.errorMessage = "对不起,无此彩金卡，或此彩金卡目前无法使用";
				return PackageXml("01", super.errorMessage, "9054", agentId);
			}

			String resultStr= doPost(card, memberSession, agentId,version);
			
			if (!resultStr.split("\\|")[0].equals("00")) {
				return PackageXml(resultStr.split("\\|")[0], resultStr.split("\\|")[1], "9054", agentId);
			}
			
			responseCodeEle.setText("0"); //处理成功
			responseMessage.setText("处理成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
	    	return softwareService.toPackage(myBody, "9001",agentId);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9053", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	// post到林总那进行充值
	private String doPost(Card card, Member member, String agentId,String version) throws Exception {

		String postResult = "04";
		String postMessage = "对不起，无此彩金卡或已使用";
		String money =changeY2F( card.getAmount().toString());
		String OrderId = "";
		String SPID = xfbConfig.getChannelId();
		String PayID = "";

		// 获取请求地址
		ChangeConfig cc=changeConfig.getChangeMap().get("CARD");
		String urlPath = cc.getUrlpth();

		// 创建支付订单
		MemberChargeLine memberChargeLine = new MemberChargeLine();
		memberChargeLine.setAccount(member.getAccount());
		memberChargeLine.setAmount(card.getAmount());
		memberChargeLine.setBank("CARD");
		memberChargeLine.setCreateDateTime(new Date());
		memberChargeLine.setStatus(CommonStatus.NO);
		memberChargeLine.setChargeType(ChargeType.CARD);
		memberChargeLine.setChannel(SPID);
		// 创建用户行为记录
		MemberOperLine memberOperLine = new MemberOperLine();
		memberOperLine.setAccount(member.getAccount());
		memberOperLine.setTerminalId((long) 0);
		memberOperLine.setOperType(MemberOperType.ADD_MONEY);
		memberOperLine.setIp(super.remoteIp);
		memberOperLine.setCreateDateTime(new Date());
		memberOperLine.setChannel(SPID);
		memberOperLine.setStatus(OperLineStatus.SUCCESS);
		memberOperLine.setVersion(version);
		String country = ipSeeker.getCountry(super.remoteIp);
		String area = ipSeeker.getArea(super.remoteIp);
		String fromPlace = country + area;
		memberOperLine.setFromPlace(fromPlace);

		memberOperLineService.save(memberOperLine);

		try {
			// 生成充值订单
			memberChargeLineService.saveNumber(memberChargeLine);
			OrderId = memberChargeLine.getChargeNo();
		} catch (Exception e) {
			postMessage= "对不起，系统出错，请重试！";
			return postResult+"|"+postMessage;
		}

		// 生成cdkey
		String forMd5 = SPID + card.getNo() + OrderId + PayID + money + cc.getKey();
		String Md5key = MD5.encode(forMd5, "utf-8");

		// post到林总那
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(urlPath);
		post.setRequestHeader("Connection", "close");

		NameValuePair param[] = new NameValuePair[6];
		param[0] = new NameValuePair("SPID", SPID);
		param[1] = new NameValuePair("VIPID", card.getNo());
		param[2] = new NameValuePair("OrderId", OrderId);
		param[3] = new NameValuePair("PayID", PayID);
		param[4] = new NameValuePair("Md5key", Md5key);
		param[5] = new NameValuePair("Money", money);

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
				String txt = resBuffer.toString();
				// LogUtil.out("Data:==" + txt);
				String[] paras = txt.split("&");

				// // 生成cdkey
				// String forMd52 = paras[0].split("=")[1] +
				// paras[1].split("=")[1] + paras[2].split("=")[1] +
				// paras[3].split("=")[1]
				// + changeConfig.getKey() + paras[4].split("=")[1];
				// String Md5key2 = MD5.encode(forMd52, "utf-8");
				//
				// if
				// (!(paras[5].split("=")[1].trim().equals(Md5key2)))
				// {
				// super.errorMessage = "对不起，数据不匹配，请重试";
				// super.forwardUrl =
				// "/jsp/member/error2.jsp?errorMessage=" +
				// errorMessage;
				// return ERROR;
				// }

				if (!paras[4].split("=")[1].equals("11")) {
					postMessage = "对不起，支付不成功，请重试";
					postResult=paras[4].split("=")[1];
					return postResult+"|"+postMessage;
				}
				postResult="00";	
				postMessage="处理成功";
			}
		} catch (Exception ex) {
			// LogUtil.out("postErrorData:==" + ex.getMessage());
			postMessage = "对不起，无此彩金卡或已使用或其它错误";
			return postResult+"|"+postMessage;
		} finally {
			post.releaseConnection();
			post = null;
			httpclient = null;
		}
		return postResult+"|"+postMessage;
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
	
	public String PackageXml(String CodeEle, String message, String type, String agentId) throws Exception {
		// ----------------新建包体--------------------
		String returnContent = "";
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		responseCodeEle.setText(CodeEle);
		responseMessage.setText(message);
		myBody.addContent(responseCodeEle);
		myBody.addContent(responseMessage);
		returnContent = this.softwareService.toPackage(myBody, type, agentId);
		return returnContent;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	public void setChangeConfig(ChangeConfig changeConfig) {
		this.changeConfig = changeConfig;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}

	public void setMemberChargeLineService(MemberChargeLineService memberChargeLineService) {
		this.memberChargeLineService = memberChargeLineService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setXfbConfig(XFBConfig xfbConfig) {
		this.xfbConfig = xfbConfig;
	}

}
