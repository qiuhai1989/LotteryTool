package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class UserInfoNotify extends BaseAction implements SoftwareProcess{
	private MemberInfoService memberInfoService;
	private SoftwareService softwareService;
	private MemberService memberService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		
		// 获取请求的body
		Element userInfo = bodyEle.getChild("userInfo");
		String node = "组装节点不存在";
		try {
			userName = userInfo.getChildText("userName");
			node = null;
			
			//验证登陆
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
			  return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if(!userName.equals(memberSession.getAccount())){
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}		
			
			MemberInfo memberInfo = new MemberInfo();
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List list = memberInfoService.findByProperty("account",
						userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "用户名不存在!";
					return PackageXml("002", super.errorMessage, "8002",
							agentId);
				}
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8002", agentId);
			}
			//业务部分。。。
			
			
		} catch (Exception e) {
			// TODO: handle exception

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
	public MemberInfoService getMemberInfoService() {
		return memberInfoService;
	}
	public void setMemberInfoService(MemberInfoService memberInfoService) {
		this.memberInfoService = memberInfoService;
	}
	public SoftwareService getSoftwareService() {
		return softwareService;
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
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
}
