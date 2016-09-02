package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import org.jdom.Element;

import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ModifyPasswordNotify extends BaseAction implements SoftwareProcess{
	private MemberService memberService;
	private SoftwareService softwareService;
	private UsersService sqlUsersService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		String account = null;// 用户名
		String pwd = null;// 当前密码
		String channel = null;//渠道ID
		String userName = null;// 用户名
		String email = null;// 邮箱
		String phoneNo = null;// 机身码
		String sim = null;// SIM卡
		String model = null;// 手机机型
		String systemVersion = null;// 系统版本

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element req = bodyEle.getChild("req");
		String node = "组装节点不存在";
		
		try {
			account = req.getChildText("account");
			pwd = req.getChildText("pwd");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ?null:bodyEle.getChildText("channel") ;
			phoneNo = bodyEle.getChildText("phoneNo");
			sim = bodyEle.getChildText("sim");
			model = bodyEle.getChildText("model");
			systemVersion = bodyEle.getChildText("systemVersion");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ? null : bodyEle.getChildText("channel");
			String version = null;
			version = StringTools.isNullOrBlank(bodyEle.getChildText("version")) ? null : bodyEle.getChildText("version");
			Member member = new Member();
			
			List<Member> list = null;
			// 判断用户是否存在
			if (account != null && !"".equals(account)) {
				list = memberService.findByProperty("account", account);
				
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "9008", agentId);
			}
			if(list!=null&&list.size()>0){
				member = list.get(0);
			}else {
				super.errorMessage = "该用户不存在!";
				return PackageXml("3001", super.errorMessage, "9008", agentId);
			}
			
//			传过来时需加密
			
			member.setPassword(pwd);
			
			Users users = new Users();
			try {
				users = sqlUsersService.findObjectByProperty("name", account);
			} catch (Exception e) {
				// TODO: handle exception
				users=null;
			}
			if(users==null){
				super.errorMessage = "此用户不存在!";
				return PackageXml("002", super.errorMessage, "9008",
						agentId);
			}
			users.setPassword(pwd);
			
			

			String ip = super.remoteIp;
			

			Long ct = 0L;

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
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
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
}
