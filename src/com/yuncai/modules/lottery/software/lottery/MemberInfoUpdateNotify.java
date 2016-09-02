package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.model.Sql.UserBankBindDetails;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.user.UserBankBindDetailsService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberInfoUpdateNotify extends BaseAction implements
		SoftwareProcess {
	private MemberInfoService memberInfoService;
	private SoftwareService softwareService;
	private MemberService memberService;
	private UsersService sqlUsersService;
	private UserBankBindDetailsService userBankBindDetailsService;
	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;// 用户名
		String realName = null;// 真实姓名
		String certNo = null;// 证件号码
		String bank = null;// 银行（银行名或支付宝）
		String openCardAddress = null;// 开卡省市
		String channel = null;//渠道ID
		String bankCard=null;
		String version=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element bindCard = bodyEle.getChild("bindCard");
		String node = "组装节点不存在";

		try {
			userName = bindCard.getChildText("userName");
			openCardAddress = bindCard.getChildText("openCardAddress");
			realName = bindCard.getChildText("realName");
			certNo = bindCard.getChildText("certNo");
			bank = bindCard.getChildText("bank");
			bankCard = bindCard.getChildText("bankCard");
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ?null:bodyEle.getChildText("channel") ;
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(
					Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			MemberInfo memberInfo = new MemberInfo();
			Member member= new Member();
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<MemberInfo> list = memberInfoService.findByProperty("account",
						userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "用户名不存在!";
					return PackageXml("002", super.errorMessage, "8003",
							agentId);
				} else {
					memberInfo = (MemberInfo) list.get(0);
					member=memberService.findByProperty("account", userName).get(0);
				}
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8003", agentId);
			}

			if(!member.getCertNo().equals(certNo)||!member.getName().equals(realName)){
				super.errorMessage = "证件号码或真实姓名不正确!";
				return PackageXml("002", super.errorMessage, "8003", agentId);
			}
			
			
			memberInfo.setBank(bank);
			memberInfo.setBankPart(openCardAddress);
			memberInfo.setBankCard(bankCard);
			
			
			Long ct = 0L;
			try {
				String _s = super.getSession().get("s").toString();
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}
			 List<Users> usersList= sqlUsersService.findByProperty("name", userName);
				if(usersList==null||usersList.isEmpty()){
					super.errorMessage = "用户名不存在!";
					return PackageXml("002", super.errorMessage, "8002",
							agentId);
				}
				
				//同步更新用户信息
				Users users = usersList.get(0);
				users.setCertNo(certNo);
				users.setBankName(bank);
				users.setBandCardNumber(bankCard);
//				if(bank.equals("支付宝")){
//					users.setAlipayName(zfbCard);
//					users.setBandCardNumber(null);
//				}
				String[] strs = openCardAddress.trim().split("\\|");
				//对应的银行详情
				UserBankBindDetails bankBindDetails = new UserBankBindDetails();
				bankBindDetails.setUserID(users.getId());
//				LogUtil.out(bankBindDetails);
				
				
				bankBindDetails.setBankCardNumber(bankCard);
				bankBindDetails.setBankInProvinceName(strs[0]);
				bankBindDetails.setBankInCityName(strs[1]);
				bankBindDetails.setBankName(strs[2]);
				bankBindDetails.setBankUserName(realName);
				bankBindDetails.setBankTypeName(bank);
				
				
				userBankBindDetailsService.saveOrUpdate(bankBindDetails);
			// 处理业务
			int operLineNo = 0;
			try {
				operLineNo = memberInfoService.update(memberInfo, ct,
						super.remoteIp, "", "",super.channelID,version);
				sqlUsersService.saveOrUpdate(users);
			} catch (Exception e) {
				operLineNo = 0;
			}

			String message = "";
			if (operLineNo == 0) {
				message = "修改失败";
			} else {
				message = "修改成功";
			}

			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText(message);
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "8003", agentId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String message = "";
				String errStatus = "200";
				if (node != null)
					message = node;
				else {
					message = e.getMessage();
					errStatus = "1";
				}

				return PackageXml(errStatus, message, "9300", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public String PackageXml(String CodeEle, String message, String type,
			String agentId) throws Exception {
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

	public void setMemberInfoService(MemberInfoService memberInfoService) {
		this.memberInfoService = memberInfoService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}

	public void setUserBankBindDetailsService(UserBankBindDetailsService userBankBindDetailsService) {
		this.userBankBindDetailsService = userBankBindDetailsService;
	}

}