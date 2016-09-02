package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.AccountRelation;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.service.oracleService.member.AccountRelationService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ApplyForVipNotify extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private SoftwareService softwareService;
	private AccountRelationService accountRelationService;
	private MemberOperLineService memberOperLineService;
	private IpSeeker ipSeeker;

	@SuppressWarnings("unused")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		String account = null;
		String recommender = null;

		String phoneNo;// 机身码
		String sim;// SIM卡
		String model;// 手机机型
		String systemVersion;// 系统版本
		String version=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		Element application = bodyEle.getChild("application");
		String message = "组装节点错误!";
		try {
			account = application.getChildText("account");
			recommender = application.getChildText("recommender");

			phoneNo = StringTools.isNullOrBlank(bodyEle.getChildText("phoneNo")) ? null : bodyEle.getChildText("phoneNo");
			sim = StringTools.isNullOrBlank(bodyEle.getChildText("sim")) ? null : bodyEle.getChildText("sim");
			model = StringTools.isNullOrBlank(bodyEle.getChildText("model")) ? null : bodyEle.getChildText("model");
			systemVersion = StringTools.isNullOrBlank(bodyEle.getChildText("systemVersion")) ? null : bodyEle.getChildText("systemVersion");
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			// // 验证登陆
			// Member memberSession = (Member)
			// request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			// if (memberSession == null) {
			// return PackageXml("2000", "请重新登录!", "9061", agentId);
			// }
			// if (!account.equals(memberSession.getAccount())) {
			// return PackageXml("9001", "用户名与Cokie用户不匹配", "9061", agentId);
			// }

			Member member = new Member();
			// 判断查询用户是否是VIP
			if (account != null && !"".equals(account)) {

				member = memberService.findByAccount(account);

				if (member == null) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("3", super.errorMessage, "9062", agentId);
				}

				if (member.getUserGradeType() != null && member.getUserGradeType() == 2) {
					super.errorMessage = "此用户已经是VIP!";
					return PackageXml("2", super.errorMessage, "9062", agentId);
				}

			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("3", super.errorMessage, "9062", agentId);
			}

			// 判断是否存在此推荐人，且可用
			if (recommender == null || recommender.equals("")) {
				super.errorMessage = "推荐号码错误!";
				return PackageXml("1", super.errorMessage, "9062", agentId);
			} else {
				String[] names = new String[2];
				names[0] = "recommendEnable";
				names[1] = "recommendId";

				Object[] values = new Object[2];
				values[0] = 1;
				values[1] = recommender;

				List<Member> list = memberService.findByPropertys(names, values);

				if (list == null || list.isEmpty()) {
					super.errorMessage = "推荐号码错误!";
					return PackageXml("1", super.errorMessage, "9062", agentId);
				}
				// 限额
				Integer recommandQuota = list.get(0).getRecommendQuota();

				// 获取已使用的额度
				List<AccountRelation> ARlist = accountRelationService.findByProperty("recommender", recommender);
				Integer usedQuota = ARlist.size();

				if (recommandQuota != -1 && recommandQuota <= usedQuota) {
					super.errorMessage = "已达到推荐限额!";
					return PackageXml("1", super.errorMessage, "9062", agentId);
				}
			}

			// 处理业务
			{
				Long ct = 0L;
				try {
					String _s = super.getSession().get("s").toString();
					ct = Long.parseLong(_s);
				} catch (Exception e) {
				}

				// 增加操作记录
				addOperLine(member.getAccount(), ct, member.getSourceId(), "申请vip，账号：" + member.getAccount(), "", "", super.remoteIp, MemberOperType.APPLY_VIP, phoneNo, sim, model, systemVersion,
						super.channelID,version);

				// 设置为VIP
				member.setUserGradeType(2);
				member.setBonus(4.0);
				memberService.update(member);

				// 关系表增加跟节点
				AccountRelation accountRelation = new AccountRelation();
				accountRelation.setAccount(account);
				accountRelation.setCreTime(new Date());
				accountRelation.setParentAccount("0");
				accountRelation.setRecommender(recommender);
				accountRelationService.save(accountRelation);
			}

			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);

			return softwareService.toPackage(myBody, "9062", agentId);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9061", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
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

	// 用户操作记录
	public void addOperLine(String account, Long ct, Integer sourceId, String extendInfo, String referer, String url, String ip, MemberOperType operType, String phoneNo, String sim, String model,
			String systemVersion, String channel,String version) {
		try {
			MemberOperLine operLine = new MemberOperLine();
			operLine.setAccount(account);
			LogUtil.out("account: " + account);
			operLine.setTerminalId(ct);
			operLine.setSourceId(sourceId);
			operLine.setExtendInfo(extendInfo + "");
			operLine.setCreateDateTime(new java.util.Date());
			operLine.setReferer("");
			operLine.setUrl("");
			operLine.setIp(ip);
			String country = ipSeeker.getCountry(ip);
			String area = ipSeeker.getArea(ip);
			String fromPlace = country + area;
			operLine.setFromPlace(fromPlace);
			operLine.setOperType(operType);
			operLine.setStatus(OperLineStatus.SUCCESS);
			operLine.setPhoneNo(phoneNo);
			operLine.setSim(sim);
			operLine.setModel(model);
			operLine.setSystemVersion(systemVersion);
			operLine.setChannel(channel);
			operLine.setExtendInfo(extendInfo);
			operLine.setVersion(version);
			// memberOperLineDao.saveOrUpdate(operLine);
			memberOperLineService.save(operLine);

		} catch (Exception e) {
		}
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setAccountRelationService(AccountRelationService accountRelationService) {
		this.accountRelationService = accountRelationService;
	}

	public IpSeeker getIpSeeker() {
		return ipSeeker;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}
}
