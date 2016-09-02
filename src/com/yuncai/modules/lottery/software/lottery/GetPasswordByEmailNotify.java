package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.core.util.EmailUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class GetPasswordByEmailNotify extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private SoftwareService softwareService;
	private UsersService sqlUsersService;
	private IpSeeker ipSeeker;
	private MemberOperLineService memberOperLineService;

	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;// 用户名
		String email = null;// 邮箱
		String phoneNo = null;// 机身码
		String sim = null;// SIM卡
		String model = null;// 手机机型
		String systemVersion = null;// 系统版本
		String channel = null;// 渠道ID

		// ----------------新建xml包体--------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element se = bodyEle.getChild("req");
		String node = "组装节点不存在";
		try {
			userName = se.getChildText("account");
			// LogUtil.out("获取参数："+userName);
			email = se.getChildText("email");
			node = null;
			Member member = new Member();

			phoneNo = bodyEle.getChildText("phoneNo");
			sim = bodyEle.getChildText("sim");
			model = bodyEle.getChildText("model");
			systemVersion = bodyEle.getChildText("systemVersion");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ? null : bodyEle.getChildText("channel");
			String version = null;
			version = StringTools.isNullOrBlank(bodyEle.getChildText("version")) ? null : bodyEle.getChildText("version");
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "9005", agentId);
				} else {
					member = (Member) list.get(0);
				}
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "9005", agentId);
			}
			// 检查是否已经绑定邮箱
			if (member.getEmail() == null) {
				super.errorMessage = "当前帐号未绑定邮箱，请采用其他方式找回密码";
				return PackageXml("002", super.errorMessage, "9005", agentId);
			}

			// 检查用户邮箱是否一致
			if (!member.getEmail().equals(email)) {
				super.errorMessage = "输入的邮箱号与账户绑定的邮箱不一致，请确认后再次输入";
				return PackageXml("002", super.errorMessage, "9005", agentId);
			}
			String _s = (String) request.getSession().getAttribute("s") == null ? "0" : (String) request.getSession().getAttribute("s");
			Long ct = 0L;
			try {
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}
			// 随机生成一个6位密码
			// Random random = new Random();
			String newPwd = getRandomStr(6);

			// 修改响应用户密码信息
			member.setPassword(MD5.encode(newPwd));
			memberService.saveOrUpdate(member);
			List<Users> usersList = sqlUsersService.findByProperty("name", userName);
			if (usersList == null || usersList.isEmpty()) {
				super.errorMessage = "用户名不存在!";
				return PackageXml("002", super.errorMessage, "8002", agentId);
			}
			Users users = usersList.get(0);
			users.setPassword(MD5.encode(newPwd));
			sqlUsersService.saveOrUpdate(users);
			// 发邮箱绑定的邮件
			boolean isSuccess = sendVidateEmail(member.getEmail(), newPwd);

			if (!isSuccess) {
				super.errorMessage = "邮件发送失败，请检查输入的邮箱是否正确!";
				return PackageXml("888", super.errorMessage, "8808", agentId);
			}
			// 保存会员操作流水
			MemberOperLine operLine = new MemberOperLine();
			operLine.setAccount(member.getAccount());
			operLine.setSourceId(new Integer(0));
			operLine.setCreateDateTime(new Date());
			operLine.setExtendInfo("");
			operLine.setReferer("");
			operLine.setUrl("");
			operLine.setFromPlace(ipSeeker.getCountry(super.remoteIp) + ipSeeker.getArea(super.remoteIp));
			operLine.setIp(super.remoteIp);
			operLine.setTerminalId(ct);
			operLine.setOperType(MemberOperType.GET_PASSWORD);
			operLine.setStatus(OperLineStatus.SUCCESS);
			operLine.setLotteryType(null);
			operLine.setPhoneNo(phoneNo);
			operLine.setSim(sim);
			operLine.setModel(model);
			operLine.setSystemVersion(systemVersion);
			operLine.setChannel(super.channelID);
			operLine.setVersion(version);
			memberOperLineService.save(operLine);

			String message = "新密码已经发送到您在云彩彩票网绑定的邮箱中";

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

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
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

	private boolean sendVidateEmail(String email, String pwd) {
		// String basePath =
		// request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		boolean isSuccess = false;
		String title = "云彩网邮箱绑定验证";
		String content = String.format("<h3>亲爱的云彩网用户：</h3><br/>您好！<br/>您在%s提交了通过邮箱找回密码申请。<br/> "
				+ "<br/><font style='color:#ccc;'>(请在试用新密码登陆后及时修改密码)</font><br/>请保管好新密码:%s", DateTools.dateToString(new Date()), pwd);
		LogUtil.out(content);
		isSuccess = EmailUtil.sendEmail(email, title, content, "text/html;charset=utf-8");

		// 邮件发送日志记录
		return isSuccess;
	}

	private static char[] getChar() {
		char[] passwordLit = new char[62];
		char fword = 'A';
		char mword = 'a';
		char bword = '0';
		for (int i = 0; i < 62; i++) {
			if (i < 26) {
				passwordLit[i] = fword;
				fword++;
			} else if (i < 52) {
				passwordLit[i] = mword;
				mword++;
			} else {
				passwordLit[i] = bword;
				bword++;
			}

		}
		return passwordLit;
	}

	/**
	 * 生成6位随机密码
	 * 
	 * @return
	 */
	private String getRandomNum() {
		StringBuffer buffer = new StringBuffer();
		Random rr = new Random();
		char[] pw = new char[6];
		char[] r = getChar();
		for (int i = 0; i < pw.length; i++) {
			int num = rr.nextInt(62);
			pw[i] = r[num];
			buffer.append(pw[i]);
		}
		return buffer.toString().toLowerCase();
	}

	/**
	 * @param i
	 *            要返回的字符串位数
	 * @return 指定位数的随机字符串
	 */
	public static String getRandomStr(int i) {
		StringBuffer sbf = new StringBuffer();
		Random r;
		r = new Random();
		for (int a = 0; a < i; a++) {
			sbf.append(r.nextInt(10));
		}
		return sbf.toString();

	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}

}
