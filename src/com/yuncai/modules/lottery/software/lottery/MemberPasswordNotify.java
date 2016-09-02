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
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberPasswordNotify extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private SoftwareService softwareService;
	private UsersService sqlUsersService;
	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}
	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;// 用户名
		String oldPwd = null;// 当前密码
		String newPwd = null;// 新密欧
		String channel = null;//渠道ID
		String version=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element upd = bodyEle.getChild("updatePwd");
		String node = "组装节点不存在";
		try {
			userName = upd.getChildText("userName");
			oldPwd = upd.getChildText("oldPwd");
			newPwd = upd.getChildText("newPwd");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ?null:bodyEle.getChildText("channel") ;
			Member member = new Member();
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(
					Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "9008",
							agentId);
				} else {
					member = (Member) list.get(0);
				}
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "9008", agentId);
			}
			//验证旧密码
			if(!member.getPassword().equals(oldPwd)){
				super.errorMessage = "旧密码不正确!";
				return PackageXml("002", super.errorMessage, "9008", agentId);
			}
			member.setPassword(newPwd);
			
			Users users = new Users();
			try {
				users = sqlUsersService.findObjectByProperty("name", userName);
			} catch (Exception e) {
				// TODO: handle exception
				users=null;
			}
			if(users==null){
				super.errorMessage = "此用户不存在!";
				return PackageXml("002", super.errorMessage, "9008",
						agentId);
			}
			users.setPassword(newPwd);
			
			
			String s_id = (String) request.getSession().getAttribute("s_id");
			String s = (String) request.getSession().getAttribute("s");
			if (s_id == null || s_id.length() < 1)
				s_id = "0";
			if (s == null || s.length() < 1)
				s = "0";

			String ip = super.remoteIp;
			

			Long ct = 0L;
			try {
				String _s = super.getSession().get("s").toString();
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}

			// 处理业务
			int operLineNo = 0;
			try {
				operLineNo = memberService.update(member, ct, super.remoteIp,
						"", "",super.channelID,version);
				
				sqlUsersService.saveOrUpdate(users);
			} catch (Exception e) {
				operLineNo = 0;
			}

			String message = "";

			if (operLineNo == 0) {
				message = "密码修改失败";
			} else {
				message = "密码修改成功";
			}

			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText(message);
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "9008", agentId);
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

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	
}
