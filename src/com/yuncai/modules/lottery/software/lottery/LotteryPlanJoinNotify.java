package com.yuncai.modules.lottery.software.lottery;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.CompileUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class LotteryPlanJoinNotify extends BaseAction implements SoftwareProcess {
	private TradeService tradeService;
	private MemberService memberService;
	private LotteryPlanService lotteryPlanService;
	private MemberWalletService memberWalletService;
	private SoftwareService softwareService;
	private MemberScoreService memberScoreService;
	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		String userName = null;// 用户名
		Integer planNO = null;// 方案单据号
		Integer amount = null;// 购买金额
		Integer part = null;// 购买份数
		Integer walletType=1;//钱包类型
		String planOrderNo=null;
		
		 String phoneNo=null;//机身码
		 String sim = null;//SIM卡
		 String model = null;//手机机型
		 String systemVersion = null;//系统版本
		 String channel = null;//渠道ID
		 String version=null;
		 
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		
		Element info=new Element("memberDetail");
	    Element ableBalance=new Element("ableBalance");
	    Element freezeBalance=new Element("freezeBalance");
	    Element takeCashQuota=new Element("takeCashQuota");
	    Element ableScore=new Element("ableScore");
		// -------------------------------------------------
		// 获取请过的body
		Element invest = bodyEle.getChild("invest");
		String node = "组装节点不存在";
		try {
			
			userName = invest.getChildText("userName");
			planNO=invest.getChildText("planNo")==null?null:Integer.parseInt(invest.getChildText("planNo"));
//			amount=invest.getChildText("amount")==null?null:Integer.parseInt(invest.getChildText("amount"));
			part=invest.getChildText("amount")==null?null:Integer.parseInt(invest.getChildText("amount"));
//			walletType=invest.getChildText("walletType")==null?null:Integer.parseInt(invest.getChildText("walletType"));
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			 phoneNo = bodyEle.getChildText("phoneNo");
			 sim = bodyEle.getChildText("sim");
			 model = bodyEle.getChildText("model");
			 systemVersion = bodyEle.getChildText("systemVersion");
			 channel = bodyEle.getChildText("channel") ;
			
			//验证登陆
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
			  return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if(!userName.equals(memberSession.getAccount())){
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			//防重注入-----------------------------------
			String resend=super.sessionCode+userName;
			//LogUtil.out("session之前:"+super.sessionCode+resend);
			if(session.getAttribute(Constant.SOFT_JOIN)!=null){
				String messageCode=(String) session.getAttribute(Constant.SOFT_JOIN);
				//LogUtil.out("session之后:"+messageCode);
				if(messageCode.equals(resend)){
					LogUtil.out("防重复......"+resend);
					return PackageXml("4009", "参与合买还在处理中,请查看投注记录是否投注成功!", "4005", agentId); 
				}
			}
			session.setAttribute(Constant.SOFT_JOIN, resend);
			//------------------------------session结束--------------
			
			node=null;
			//获取方案信息
			LotteryPlan lotteryPlan=lotteryPlanService.findLotteryPlanByPlanNoForUpdate(planNO);
			
			if(lotteryPlan==null){
				super.errorMessage = "对不起，方案不存在!";
				return PackageXml("002", super.errorMessage, "9007", agentId);
			}
			
			{
				/*有效验证*/
				boolean ispart=CompileUtil.isNumber(part+"");
				if(!ispart)
					return PackageXml("002", "对不起!输入金额有误!", "9007", agentId);
			}
			
			//不是合买方案
			if(lotteryPlan.getPlanType()!=PlanType.getItem(2))
			{
				super.errorMessage = "对不起，此方案不是合买方案!";
				return PackageXml("002", super.errorMessage, "9007", agentId);
			}
			
			
			Member member=new Member();
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<Member> list=memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "9007",
							agentId);
				}
				member=list.get(0);
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "9007", agentId);
			}
			
			//获取总金额
			amount=part*lotteryPlan.getPerAmount();
			
			//获取用户钱包信息
			MemberWallet memberWallet=memberWalletService.findByMemberIdAndType(member.getId(),WalletType.getItem(walletType));
			//余额不足	
			if(memberWallet.getAbleBalance()<1||memberWallet.getAbleBalance()<amount){
				super.errorMessage = "对不起，余额不足!";
				return PackageXml("002", super.errorMessage, "9007", agentId);
			}
			
			
			try {
				String _s = (String) request.getSession().getAttribute("s") == null ? "0" :(String)request.getSession().getAttribute("s");
				Long ct = 0L;
				try {
					ct = Long.parseLong(_s);
				} catch (Exception e) {
				}
		
			String ip = super.remoteIp;
			//tradeService.buyLotteryPlanJoin(member, ct, lotteryPlanNo, part, fromPage, ip, walletType, followingType, sourceId, autoHm)
			LogUtil.out("--------------------参与合买接口start!");
			planOrderNo=tradeService.buyLotteryPlanJoin(member, ct, planNO, part, 0, ip, WalletType.DUOBAO.getValue(), MemberFollowingType.HEMAI.getValue(), 0, "",phoneNo,sim,model,systemVersion,super.channelID,version);
			LogUtil.out("--------------------参与合买接口end!");
			} catch (ServiceException en) {
				en.printStackTrace();
				return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9300", agentId);
			} catch (Exception e) {
				 e.printStackTrace();
				    return PackageXml("1", e.getMessage(), "9300", agentId);
			}
			memberSession.setWallet(this.memberWalletService.findByAccount(memberSession.getAccount()));
			memberSession.setScore(memberScoreService.findByAccount(memberSession.getAccount()));
			
			{
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");
//				Element orderNo=new Element("orderNo");
//				orderNo.setText(planOrderNo);
				
				ableBalance.setText(memberSession.getWallet().getAbleBalance().toString());
				freezeBalance.setText(memberSession.getWallet().getFreezeBalance().toString());
				
				double cashQuota=memberSession.getWallet().getTakeCashQuota();
				int IntCashQuota=(int)cashQuota;
				takeCashQuota.setText(IntCashQuota+"");
				//takeCashQuota.setText(memberSession.getWallet().getTakeCashQuota().toString());
				ableScore.setText(memberSession.getScore().getAbleScore().toString());
				info.addContent(ableBalance);
				info.addContent(freezeBalance);
				info.addContent(takeCashQuota);
				info.addContent(ableScore);
				
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				//myBody.addContent(orderNo);
				myBody.addContent(info);
				
				return softwareService.toPackage(myBody, "9007", agentId);
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

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}
	
}
