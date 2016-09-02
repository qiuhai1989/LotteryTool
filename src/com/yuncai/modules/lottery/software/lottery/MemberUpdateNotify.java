package com.yuncai.modules.lottery.software.lottery;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.CertNoUtil;
import com.yuncai.core.util.DBHelper;
import com.yuncai.core.util.MobileLocationUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Sql.SensitiveKeyWords;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberUpdateNotify extends BaseAction implements SoftwareProcess {
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
		String realName = null;// 真实姓名
		String certNo = null;// 证件号码
		String mobile = null;// 手机号码
		String email = null;// 邮箱
		String status = null;// 状态
		Integer buySendSms = null;
		String phoneNo = null;
		String nickName=null; //昵称
		String channel = null;//渠道ID
		String version=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element upd = bodyEle.getChild("update");
		String node = "组装节点不存在";
		try {
			status = upd.getChildText("status");
			userName = upd.getChildText("userName");
			realName = upd.getChildText("realName");
			certNo = upd.getChildText("certNo");
			email = upd.getChildText("email");
			mobile = upd.getChildText("mobile");
			phoneNo = bodyEle.getChildText("phoneNo");
			nickName =upd.getChildText("nickName");
			node = null;
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			buySendSms = StringTools.isNullOrBlank(upd.getChildText("buysendsms"))?0:Integer.parseInt(upd.getChildText("buysendsms"));
			Member member = new Member();
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

			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "9005",
							agentId);
				} else {
					member = (Member) list.get(0);
				}
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "9005", agentId);
			}
			
			if(certNo != null && !"".equals(certNo)){
				// 判断身份证号码格式是否正确
				if(!CertNoUtil.vId(certNo)){
					super.errorMessage = "身份证号码格式不正确!";
					return PackageXml("002", super.errorMessage, "8003", agentId);
				}
			}
			
			// 判断身份证是否重复
		if(certNo!=null && !"".equals(certNo)){
				List<Member> list=memberService.findByProperty("certNo", certNo);
				if(!list.isEmpty()){
					for(Member memberTmp : list){
						if(memberTmp.getId() != member.getId()){
							if(!memberTmp.getCertNo().equals(member.getAccount())){
								super.errorMessage = "该身份证号已存在!";
								return PackageXml("002", super.errorMessage, "9005", agentId);
							}
						}
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
			//同步更新到SQL数据库中
			 List<Users> usersList= sqlUsersService.findByProperty("name", userName);
				if(usersList==null||usersList.isEmpty()){
					super.errorMessage = "用户名不存在!";
					return PackageXml("002", super.errorMessage, "8002",
							agentId);
				}
			Users users = usersList.get(0);
			Integer i = (status == null || "".equals(status)) ? 0 : Integer
					.parseInt(status);
			switch (i) {
			case 0:
				if (realName != null && !"".equals(realName) && certNo != null
						&& !"".equals(certNo) && email != null
						&& !"".equals(email) && mobile != null
						&& !"".equals(mobile)) {

					member.setAccount(userName);
					member.setName(realName);
					member.setCertNo(certNo);
					member.setMobile(mobile);
					member.setEmail(email);
					if(member.getMobile()!=null&&!member.getMobile().equals(mobile)){
						member.setIsMobileAuthed(0);
					}
//					member.setIsMobileAuthed(1);
					users.setName(userName);
					users.setRealityName(realName);
					users.setCertNo(certNo);
					users.setMobile(mobile);
					users.setEmail(email);
					if(users.getMobile()!=null&&!users.getMobile().equals(mobile)){
						users.setIsMobileValided(false);
					}
					try {
						if(DBHelper.isNoNull(mobile))
							member.setMobilBelongingTo(MobileLocationUtil.getMobileLocation(mobile));
						if(DBHelper.isNoNull(mobile))
							users.setMobilBelongingTo(MobileLocationUtil.getMobileLocation(mobile));
					} catch (Exception e1) {
					}
				} else {
					super.errorMessage = "节点不齐全或部分节点内容为空!";
					return PackageXml("002", super.errorMessage, "9005",
							agentId);
				}
				break;
			case 1:
				
				//检验用户是否已经绑定过身份证
				if(member!=null&&(member.getCertNo()!=null||member.getName()!=null)){
					super.errorMessage = "该帐号已经绑定过身份证信息!";
					return PackageXml("002", super.errorMessage, "9005",
							agentId);
				}else{
					if (realName != null && !"".equals(realName) && certNo != null
							&& !"".equals(certNo)) {
						member.setAccount(userName);
						member.setName(realName);
						member.setCertNo(certNo);
						
						users.setName(userName);
						users.setRealityName(realName);
						users.setCertNo(certNo);
						
					} else {
						super.errorMessage = "节点不齐全或部分节点内容为空!";
						return PackageXml("002", super.errorMessage, "9005",
								agentId);
					}
				}
				

				break;
			case 2:
				if (mobile != null && !"".equals(mobile)) {
					//如果已验证用户需与已验证号码比较
//					if( member.getIsMobileAuthed()!=null&&member.getIsMobileAuthed()==1&&users.getIsMobileValided()!=null&&users.getIsMobileValided()){
//						if(!member.getMobile().equals(mobile)){
//							
//							super.errorMessage = "已绑定的手机号码输入不正确";
//							return PackageXml("002", super.errorMessage, "9005",
//									agentId);
//						}
//						
//					}
					
					member.setAccount(userName);
					member.setMobile(mobile);
					member.setIsMobileAuthed(1);
					
					users.setName(userName);
					users.setMobile(mobile);
					users.setIsMobileValided(true);
					Integer buy = member.getBuySendSms();
					if(buySendSms.equals(1)){
						if(buy==0){
							member.setBuySendSms(1);
							users.setBuySendSms(1);
						}
					}
					//绑定手机的时候 同时绑定机身码
					if(member.getPhoneNo()!=null){
						if(phoneNo!=null){
							member.setPhoneNo(phoneNo);
							users.setPhoneNo(phoneNo);
						}
						member.setIsPhoneBinding(1);
						users.setIsPhoneBinding(true);
					}
					try {
						if(DBHelper.isNoNull(mobile))
							member.setMobilBelongingTo(MobileLocationUtil.getMobileLocation(mobile));
						if(DBHelper.isNoNull(mobile))
							users.setMobilBelongingTo(MobileLocationUtil.getMobileLocation(mobile));
					} catch (Exception e1) {
					}
				} else {
					super.errorMessage = "缺少mobile节点或mobile节点内容为空!";
					return PackageXml("002", super.errorMessage, "9005",
							agentId);
				}
				break;
			case 3:
				if (email != null && !"".equals(email)) {
					member.setAccount(userName);
					member.setEmail(email);
					
					users.setName(userName);
					users.setEmail(email);
					users.setIsEmailValided(true);
				} else {
					super.errorMessage = "缺少email节点或email节点内容为空!";
					return PackageXml("002", super.errorMessage, "9005",
							agentId);
				}
				break;
			case 4:
				//判断昵称是否重复
				
				if(nickName!=null && !"".equals(nickName)){
					
					if(nickName.length()>15){
						super.errorMessage = "昵称长度过长请使用小于15个字的昵称!";
						return PackageXml("002", super.errorMessage, "9004", agentId);	
					}
					
				}
				
				
				if(nickName!=null && !"".equals(nickName)){
					List list=memberService.findByProperty("nickName", nickName);
					List<SensitiveKeyWords>objs = sqlUsersService.findSensitiveKeyWords();
					for(SensitiveKeyWords obj :objs){
						//习近平,温家宝,江泽民
						String str = obj.getKeywords();
						String []keys = str.split(",");
						for(String key:keys){
							if(nickName.indexOf(key)>=0){
								super.errorMessage = "该昵称存在敏感字眼，请尝试其它昵称!";
								return PackageXml("002", super.errorMessage, "9004", agentId);								
							}
						}
					}
					
					
					if(!list.isEmpty()){
						Member temp=(Member)list.get(0);
						if(!temp.getAccount().equals(member.getAccount())){
							super.errorMessage = "该昵称已存在，请尝试其它昵称!";
							return PackageXml("002", super.errorMessage, "9005", agentId);
						}
						
					}
					
				}
				member.setNickName(nickName);
				users.setNickName(nickName);
				break;
			default:
				super.errorMessage = "无法识别此状态!";
				return PackageXml("002", super.errorMessage, "9005", agentId);
			}

			Long ct = 0L;
			try {
				String _s = super.getSession().get("s").toString();
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}

			// 处理业务
			int operLineNo = 0;
			try {
				operLineNo = memberService.update(member, ct, super.remoteIp,"", "",super.channelID,version);
				
				sqlUsersService.saveOrUpdate(users);
				
				request.getSession().setAttribute(Constant.MEMBER_LOGIN_SESSION,member);
				
				Member updateSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
				
				updateSession.setWallet(memberSession.getWallet());
				updateSession.setScore(memberSession.getScore());
			} catch (Exception e) {
				operLineNo = 0;
			}

			String message = "";

			if (operLineNo == 0) {
				message = "用户资料更新失败";
			} else {
				message = "用户资料更新成功";
			}

			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText(message);
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "9005", agentId);
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
