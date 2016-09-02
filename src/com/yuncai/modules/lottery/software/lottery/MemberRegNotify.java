package com.yuncai.modules.lottery.software.lottery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.CertNoUtil;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.comconfig.ChangeConfig;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Sql.SensitiveKeyWords;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.GiftDatailLineService;
import com.yuncai.modules.lottery.service.oracleService.member.GiftManageService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberRegNotify extends BaseAction implements SoftwareProcess {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MemberService memberService;
	private SoftwareService softwareService;
	private UsersService sqlUsersService;
	private GiftManageService giftManageService;
	private GiftDatailLineService giftDatailLineService;
	private MemberWalletService memberWalletService;
	private ChangeConfig postConfig;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		String username = null;
		String realName = null;
		String certNo = null;// 证件号码
		String password = null;
		String mobile = null;
		int amount = 0;
		String provider = null;// 合作供应商(eg:支付宝)
		String outUid = null;// 供应商方的用户id
		String email = null;
		String phoneNo = null;
		String channel = null;//渠道ID
		String version=null;
		// int buySendSms = 0;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element info = new Element("memberDetail");
		Element ableBalance = new Element("ableBalance");
		Element freezeBalance = new Element("freezeBalance");
		Element takeCashQuota = new Element("takeCashQuota");
		Element ableScore = new Element("ableScore");
		// -------------------------------------------------
		// 获取请过的body
		Element reg = bodyEle.getChild("reg");
		String node = "组装节点不存在";
		try {
			username = reg.getChildText("userName");
			realName = reg.getChildText("realName");
			certNo = reg.getChildText("certNo")==null?"":reg.getChildText("certNo");
			password = reg.getChildText("password");
			mobile = reg.getChildText("mobile");
			provider = reg.getChildText("provider");
			outUid = reg.getChildText("outUid");
			email = reg.getChildText("email");
			phoneNo = bodyEle.getChildText("phoneNo");
			// buySendSms=StringTools.isNullOrBlank(
			// reg.getChildText("buySendSms"))?0:1;
			node = null;
			
			//防重注入-----------------------------------
			String resend=super.sessionCode+username;
			if(session.getAttribute(Constant.SOFT_REG)!=null){
				String messageCode=(String) session.getAttribute(Constant.SOFT_REG);
				if(messageCode.equals(resend)){
					LogUtil.out("防重复......"+resend);
					return PackageXml("4009", "用户注册还在处理中,请查看个人中心是否存在账户号!", "4005", agentId); 
				}
			}
			session.setAttribute(Constant.SOFT_REG, resend);
			//------------------------------session结束--------------
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ?null:bodyEle.getChildText("channel") ;
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");			
			//验证是否符合正则要求
			String regx = "^([a-zA-Z0-9]|[_]){6,20}$";
			if(!username.matches(regx)){
				super.errorMessage = "用户名必须长度为6～20个字符的英文字母，数字或下划线!";
				return PackageXml("003", super.errorMessage, "9007", agentId);
			}
			
			List<SensitiveKeyWords>objs = sqlUsersService.findSensitiveKeyWords();
			//敏感词验证
			for(SensitiveKeyWords obj :objs){
				//习近平,温家宝,江泽民
				String str = obj.getKeywords();
				String []keys = str.split(",");
				for(String key:keys){
					if(username.indexOf(key)>=0){
						super.errorMessage = "该用户名存在敏感字眼，请尝试其它昵称!";
						return PackageXml("002", super.errorMessage, "9007", agentId);								
					}
				}
			}
			
			
			// 判断用户是否存在
			if (username != null && !"".equals(username)) {
				List list = memberService.findByProperty("account", username);
				if (list != null && !list.isEmpty()) {
					super.errorMessage = "用户名已存在!";
					return PackageXml("001", "用户名已注册!", "9007", agentId);
				}
			}
			
			if(!"".equals(certNo)){
			// 判断身份证号码格式是否正确
				if(!CertNoUtil.vId(certNo)){
					super.errorMessage = "身份证号码格式不正确!";
					return PackageXml("002", super.errorMessage, "8003", agentId);
				}
						
				// 判断身份证是否重复
				if(certNo!=null && !"".equals(certNo)){
					List<Member> list=memberService.findByProperty("certNo", certNo);
					if(!list.isEmpty()){
						super.errorMessage = "该身份证号已存在!";
						return PackageXml("002", super.errorMessage, "9005", agentId);
					}
				}
			}
			
			String s_id = (String) request.getSession().getAttribute("s_id");
			String s = (String) request.getSession().getAttribute("s");
			if (s_id == null || s_id.length() < 1)
				s_id = "0";
			if (s == null || s.length() < 1)
				s = "0";

			String ip = super.remoteIp;
			Member member = new Member();
			member.setAccount(username);
			member.setCertNo(certNo);
			member.setEmail(email);
			member.setExprerience(new Integer(0));
			member.setLastLoginDateTime(new Date());
			member.setMobile(mobile);
			member.setName(realName);
			member.setPassword(password);
			member.setRank(1);
			member.setRecommender(0);
			member.setRegisterDateTime(new Date());
			member.setSourceId(new Integer(s_id));
			member.setStatus(0);
			member.setSign(s);
			member.setProvider(super.channelID);
			member.setOutUid(outUid);
			member.setPlayStatus(0);
			member.setBuySendSms(0);
			member.setPhoneNo(phoneNo);
			member.setIsPhoneBinding(0);
			
			//昵称默认==账号
			String random=StringTools.randomString(8);
			member.setNickName(username);
			//默认普通用户
			member.setUserGradeType(3);
			Long ct = 0L;
			try {
				String _s = super.getSession().get("s").toString();
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}

			// 处理业务
			int operLineNo = 0;
			try {
				operLineNo = memberService.register(member, ct, super.remoteIp, "", "",super.channelID,version,phoneNo);
				// //临时 手动同步加到SqlServer中
				Users users = new Users();
				users.setCertNo(certNo);
				users.setEmail(email);
				users.setMobile(mobile);
				users.setName(username);
				users.setPassword(password);
				users.setRealityName(realName);
				users.setBuySendSms(0);
				users.setPhoneNo(phoneNo);
				users.setNickName(username); //变更昵称==账号
				// 占时写应代码将来待改
				users.setSiteID(1);
				users.setIsPhoneBinding(false);
				Integer integer = sqlUsersService.findMaxId();
				users.setId(integer + 1);
				
				sqlUsersService.save(users);
//				LogUtil.out("sqlUsersService 保存操作完毕");
			} catch (Exception e) {
				e.printStackTrace();
				operLineNo = 0;
			}

			request.getSession().setAttribute(Constant.MEMBER_LOGIN_SESSION, member);

			String client = "";// cookie
			try {
				javax.servlet.http.Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("client")) {
						client = cookie.getValue();
					}
				}
			} catch (Exception e) {
			}

			HashMap map = new HashMap();
			if (!client.equals("")) {
				client = StringTools.DecodeBase64(client);
				map = StringTools.getParametersByContents(client);
			}
			map.put("reg_date", DateTools.getNowDateYYMMDD());

			Cookie clientCookie = new Cookie("client", StringTools.EncodeBase64(StringTools.getStringByHashMap(map)));
			clientCookie.setMaxAge(60 * 60 * 24 * 365);// cookie时间(保存一年)
			clientCookie.setPath("/");
			response.addCookie(clientCookie); // 添加clientCookie

			//System.out.println("会员注册" + ip + " reg time:" + new Date());

			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("注册成功");
				ableBalance.setText("0");
				freezeBalance.setText("0");
				takeCashQuota.setText("0");
				ableScore.setText("0");

				info.addContent(ableBalance);
				info.addContent(freezeBalance);
				info.addContent(takeCashQuota);
				info.addContent(ableScore);

				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(info);

				//注册赠送红包
				List<GiftManage> listManage = giftManageService.findGift(agentId,0,2);
					for (GiftManage bean: listManage) {
						// 获取这个活动的信息,判断是否使用过一次
						String topNumber = bean.getTopNumber(); // 字头
						String postLimit = bean.getPostLimit(); // 限制
						Double giftMoney = bean.getGiftMoney(); // 红包金额，单位元
								// 判断用户是否使用过一次
								List<GiftDatailLine> listLine = this.giftDatailLineService.findByAccountList(username, topNumber);
								Member mem = this.memberService.findByAccount(username);
								if (!listLine.isEmpty()) {
									// 限制如果小于就进行充值
									if (listLine.size() < Integer.parseInt(postLimit)) {
										{
											String giftId= giftManageLine(mem, bean, giftMoney);
											postGiftInfo(giftId, bean.getTopNumber(),changeY2F(giftMoney.toString()),"");
										}
									}
								} else {
									// 没有进行充值
									String giftId= giftManageLine(mem, bean, giftMoney);
									postGiftInfo(giftId, bean.getTopNumber(),changeY2F(giftMoney.toString()),"");
								}
					}
				
				return softwareService.toPackage(myBody, "9007", agentId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String message = "";
				String status = "200";
				if (node != null)
					message = node;
				else {
					message = e.getMessage();
					status = "1";
				}

				return PackageXml(status, message, "9300", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	// 组装错误信息
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
	
	public String giftManageLine(Member member,GiftManage bean, Double giftMoney) {

		try {
			memberWalletService.increaseAble(member, giftMoney, -1, 0, 0, 0, WalletType.DUOBAO.getValue(), WalletTransType.REG_PRESENT.getValue(),
					member.getSourceId(), bean.getGiftName());
		} catch (Exception e) {
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
		line.setCreateTimeDate(new Date());
		
//		line.setGiftId(bean.getTopNumber());
//		//组装编号字头
		if(DBHelper.isNoNull(member.getMobile())){
			StringBuilder number=new StringBuilder();
			number.append(bean.getTopNumber()).append(member.getMobile().substring(5, 11));
			line.setGiftId(number.toString());
		} else {
			StringBuilder number = new StringBuilder();
			number.append(bean.getTopNumber()).append(member.getAccount());
			line.setGiftId(number.toString());
		}
		this.giftDatailLineService.save(line);

		return line.getGiftId();
	}

	public void postGiftInfo(String giftId, String topNumber,String money,String orderId) throws InterruptedException {
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
						break;//成功之后不再post
					}
				}
			} catch (Exception ex) {
			}
			Thread.sleep(1000);//延迟1秒执行下次
		}
	}
	
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

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}

	public void setGiftManageService(GiftManageService giftManageService) {
		this.giftManageService = giftManageService;
	}

	public void setGiftDatailLineService(GiftDatailLineService giftDatailLineService) {
		this.giftDatailLineService = giftDatailLineService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setPostConfig(ChangeConfig postConfig) {
		this.postConfig = postConfig;
	}

}
