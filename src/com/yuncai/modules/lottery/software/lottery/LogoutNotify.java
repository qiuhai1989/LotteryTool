package com.yuncai.modules.lottery.software.lottery;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class LogoutNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private MemberService memberService;

	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element user = bodyEle.getChild("user");
		String node = "组装节点不存在";
		try {
			String username = user.getChild("userName").getText().trim();

			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			
			//如果session不为空则清除session
			if (memberSession != null) {
				if ("".equals(username) || !username.equals(memberSession.getAccount())) {
					responseCodeEle.setText("2001"); // 帐号错误
					responseMessage.setText("账号错误");
				}
				
				request.getSession().removeAttribute(Constant.MEMBER_LOGIN_SESSION);
			}

			responseCodeEle.setText("0"); // 处理成功
			responseMessage.setText("处理成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);

			return softwareService.toPackage(myBody, "9011", agentId);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				return PackageXml("3002", "组装节点不存在", "9011", agentId);
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
}
