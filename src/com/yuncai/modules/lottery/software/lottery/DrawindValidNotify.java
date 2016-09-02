package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.EmailInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class DrawindValidNotify extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private MemberWalletService memberWalletService;
	private SoftwareService softwareService;
	private LotteryPlanService lotteryPlanService;
	private IpSeeker ipSeeker;
	private MemberOperLineService memberOperLineService;
	private MemberDrawingService memberDrawingService;
	private MemberInfoService memberInfoService;
	private MemberDrawingLineService memberDrawingLineService;
	private MemberOperLineDAO memberOperLineDao;
	private EmailInfoService emailInfoService;
	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;// 用户名
		String amount = null;// 金额
		String type = null;// 操作类型
		Integer lotteryType = -1;// 彩种
		Integer planNO = null;// 方案编号
		Integer orderNO = null;// 方案编号
		Integer operLineNO = null;// 操作流水号
		Integer walletType = null;// 钱包类型
		String tradeNO = null; // 针对：提款、追号执行的单号
		String remark = "无";// 备注
		Integer tradeType = -1; // 交易参数

		String phoneNo;// 机身码
		String sim;// SIM卡
		String model;// 手机机型
		String systemVersion;// 系统版本

		String channel;// 渠道ID
		
		String realName ;//账号注册真实姓名
		String certNo;//账号注册身份证号码
		
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element memberDetail = new Element("memberDetail");
		Element ableBalance = new Element("ableBalance");
		Element freezeBalance = new Element("freezeBalance");
		Element takeCashQuota = new Element("takeCashQuota");
		Element ableScore = new Element("ableScore");
		// -------------------------------------------------
		
		// 获取请求的body
		Element wallet = bodyEle.getChild("MemberWallet");
		String node = "组装节点不存在";
		try {
			userName = wallet.getChildText("account");
			amount = wallet.getChildText("amount");
			realName = wallet.getChildText("realName");
			
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			Member member = new Member();
			MemberWallet memberWallet = new MemberWallet();
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<Member> list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}

				member = list.get(0);
				memberWallet = memberWalletService.findByAccount(member.getAccount());
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}

			
			if(realName.trim()==null&&!member.getName().equalsIgnoreCase(realName.trim())){
				super.errorMessage = "姓名与账户信息中不一致";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}
			
			if(member.getRecommender()!=0){
				super.errorMessage = "限制提款用户，请联系客服";
				System.out.println(super.errorMessage);
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}		
			
			
			if (memberWallet.getTakeCashQuota() < Double.parseDouble(amount)) {
				super.errorMessage = "对不起，提款金额不可超过提款额度！";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}
			
			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("验证成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "9420", agentId);
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
	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}
	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}
	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}
	public void setMemberDrawingService(MemberDrawingService memberDrawingService) {
		this.memberDrawingService = memberDrawingService;
	}
	public void setMemberInfoService(MemberInfoService memberInfoService) {
		this.memberInfoService = memberInfoService;
	}
	public void setMemberDrawingLineService(MemberDrawingLineService memberDrawingLineService) {
		this.memberDrawingLineService = memberDrawingLineService;
	}
	public void setEmailInfoService(EmailInfoService emailInfoService) {
		this.emailInfoService = emailInfoService;
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
}
