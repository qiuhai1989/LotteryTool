package com.yuncai.modules.lottery.software.lottery;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.AccountRelation;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.AccountRelationService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.CommonConfigFactory;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class LoginNotify extends BaseAction implements SoftwareProcess {

	private SoftwareService softwareService;
	private MemberService memberService;
	private MemberWalletService memberWalletService;
	private MemberScoreService memberScoreService;
	private UsersService sqlUsersService;
	private AccountRelationService accountRelationService;

	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();

		// ----------------新建xml包体--------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element status = new Element("status");
		Element info = new Element("memberDetail");
		Element ableBalance = new Element("ableBalance");
		Element freezeBalance = new Element("freezeBalance");
		Element takeCashQuota = new Element("takeCashQuota");
		Element ableScore = new Element("ableScore");
		Element email = new Element("email");
		Element mobile = new Element("mobile");

		Element vip = new Element("vipInfo");// vip信息
		Element level = new Element("level");// 是否顶层
		Element bonus = new Element("bonus");// 返点比例
		// -------------------------------------------------
		// 获取请过的body
		Element user = bodyEle.getChild("user");
		String channel = null;// 渠道ID
		String userStatus = "";
		String version=null;
		boolean isVip = false;//是否是vip
		try {
			String username = user.getChild("userName").getText().trim();
			String userPwd = user.getChild("userPwd").getText().trim();
			String phoneNo = bodyEle.getChildText("phoneNo");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ? null : bodyEle.getChildText("channel");
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			// 如果合作网站是联合登录又是另一种情况

			Member m =this.memberService.findByAccount(username);
			//普通会员默认是null
			if(m!=null){
			Integer userGradeType = m.getUserGradeType()==null?-2:m.getUserGradeType();
			if((userGradeType==1&& !"admin".equals(username)))
				return PackageXml("012", "对不起！您的账户存在异常情况，请联系云彩客服：400-850-6636", "9001", agentId);
			}
			// LogUtil.out("userPwd:"+userPwd);
			boolean ret = this.memberService.loginEMd5(username, userPwd);
			if (!ret) {
				// LogUtil.out("帐号或密码错误");
				responseCodeEle.setText("2001"); // 帐号或密码错误
				responseMessage.setText("账号或密码错误");				
			} else {
				Member member = this.memberService.findByAccount(username);

				Long ct = 0L;
				try {
					String _s = super.getSession().get("s").toString();
					ct = Long.parseLong(_s);
				} catch (Exception e) {
				}
				// 判断资料是否填充
				if (member.getName() == null || "".equals(member.getName()) || member.getCertNo() == null || "".equals(member.getCertNo()) || member.getMobile() == null
						|| "".equals(member.getMobile())) {
					userStatus = "1";
				} else {
					userStatus = "0";
				}

				member.setWallet(memberWalletService.findByAccount(member.getAccount()));
				MemberScore memberScore = memberScoreService.findByAccount(member.getAccount());
				member.setScore(memberScore);
				// sql同步更新
				Users users = sqlUsersService.findByProperty("name", member.getAccount()).get(0);

				if (member.getIsPhoneBinding() == null || users.getIsPhoneBinding() == null) {
					member.setIsPhoneBinding(0);
					users.setIsPhoneBinding(false);
				}

				// 等会加上判断如果未绑定的才进去
				if (member.getPhoneNo() == null && phoneNo != null && member.getIsPhoneBinding() == 0) {
					member.setPhoneNo(phoneNo);

				}

				if (users.getPhoneNo() == null && phoneNo != null && users.getIsPhoneBinding() == false) {

					users.setPhoneNo(phoneNo);
				}

				this.memberService.updateLastLogin(member, ct, super.remoteIp, super.channelID,version,phoneNo);

				sqlUsersService.saveOrUpdate(users);

				request.getSession().setAttribute(Constant.MEMBER_LOGIN_SESSION, member);

				responseCodeEle.setText("0"); // 处理成功
				responseMessage.setText("处理成功");
				status.setText(userStatus);
				ableBalance.setText(member.getWallet().getAbleBalance().toString());
				freezeBalance.setText(member.getWallet().getFreezeBalance().toString());

				double cashQuota = member.getWallet().getTakeCashQuota();
				int IntCashQuota = (int) cashQuota;
				takeCashQuota.setText(IntCashQuota + "");
				// takeCashQuota.setText(member.getWallet().getTakeCashQuota().toString());
				ableScore.setText(member.getScore().getAbleScore().toString());

				email.setText(member.getEmail());
				mobile.setText(member.getIsPhoneBinding()==1?member.getMobile():null);
				
				info.addContent(ableBalance);
				info.addContent(freezeBalance);
				info.addContent(takeCashQuota);
				info.addContent(ableScore);

				info.addContent(email);
				info.addContent(mobile);
				
				// 获取VIP等级关系
				AccountRelation accountRelation = accountRelationService.findObjectByProperty("account", username);
				if (accountRelation != null) {
					isVip = true;
					if (accountRelation.getParentAccount().equals("0")) {
						level.setText("0");
					} else {
						level.setText("1");
					}
					bonus.setText(member.getBonus() == null ? "0" : member.getBonus().toString());
					vip.addContent(level);
					vip.addContent(bonus);
				}
			}

			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(status);
			myBody.addContent(info);
			if (isVip) {
				myBody.addContent(vip);
			}

			return softwareService.toPackage(myBody, "9001", agentId);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return PackageXml("3002", "组装节点不存在", "9001", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}

	public void setAccountRelationService(AccountRelationService accountRelationService) {
		this.accountRelationService = accountRelationService;
	}

}
