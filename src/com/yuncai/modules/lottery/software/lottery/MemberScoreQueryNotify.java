package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberScoreQueryNotify extends BaseAction implements SoftwareProcess{
	private MemberService memberService;
	private SoftwareService softwareService;
	private MemberScoreService memberScoreService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String account = null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element ableScore = new Element("ableScore");
		// -------------------------------------------------
		account = bodyEle.getChildText("account");
		String node = "组装节点不存在";
		try {		
		Member member=new Member();
		MemberScore memberScore = new MemberScore();
		
		Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
//		memberSession = null;
		if (memberSession == null) {
			return PackageXml("2000", "请登录!", "9300", agentId);
		}

			
			
		if (!account.equals(memberSession.getAccount())) {
			return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
		}
		
		
		// 判断用户是否存在
		if (account != null && !"".equals(account)) {
			List<MemberScore> list = memberScoreService.findByProperty(
					"account", account);
			List<Member> listm=memberService.findByProperty("account", account);
			if (list == null || list.isEmpty()) {
				super.errorMessage = "此用户不存在!";
				return PackageXml("002", super.errorMessage, "8007",
						agentId);
			}
			memberScore = list.get(0);
			member=listm.get(0);
			ableScore.setText(Integer.toString(memberScore.getAbleScore()));
			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("查询成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(ableScore);
				return softwareService.toPackage(myBody, "9008", agentId);
			}
		} else {
			super.errorMessage = "用户名不能为空!";
			return PackageXml("002", super.errorMessage, "8007", agentId);
		}
		
		
		}catch (Exception e) {
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
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
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
	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}
	
}
