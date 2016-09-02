package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Sql.Sites;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.user.SitesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class HandCancelNotify extends BaseAction implements SoftwareProcess{

	private SoftwareService softwareService; 
	private MemberService memberService;
	private LotteryPlanService lotteryPlanService;
	private MemberWalletService memberWalletService;
	private MemberScoreService memberScoreService;
	private SitesService sqlSitesService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null;// 用户名
		Integer planNO = null;// 方案单据号
		String  scale=null;  //设置限额
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
		Element handCeancel = bodyEle.getChild("handCeancel");
		String node = "组装节点不存在";
		try {
			userName = handCeancel.getChildText("userName");
			planNO=handCeancel.getChildText("planNo")==null?null:Integer.parseInt(handCeancel.getChildText("planNo"));
			scale=handCeancel.getChildText("scale");
			
			//验证登陆 (后台不需要进行验证，前端需要进行验证。1前端 0后端)
			Member memberSession=new Member();
			if(scale.equals("1")){
			    memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
				if(memberSession==null){
				  return PackageXml("2000", "请登录!", "9300", agentId);
				}
				if(!userName.equals(memberSession.getAccount())){
					return PackageXml("9001", "用户名与Cokie用户不匹配", "9036", agentId);
				}
			}else{
				memberSession=memberService.findByAccount(userName);
				if(memberSession==null){
					return PackageXml("9001", "该用户不是管理员的角色!", "9036", agentId);
				}
			}
			node=null;
			//获取方案信息
			LotteryPlan lotteryPlan=lotteryPlanService.findLotteryPlanByPlanNoForUpdate(planNO);
			
			if(lotteryPlan==null){
				super.errorMessage = "对不起，方案不存在!";
				return PackageXml("002", super.errorMessage, "9036", agentId);
			}
			
			
			
			//不是合买方案
			if(lotteryPlan.getPlanType()!=PlanType.getItem(2))
			{
				super.errorMessage = "对不起，此方案不是合买方案!";
				return PackageXml("002", super.errorMessage, "9036", agentId);
			}
			
			//是否撤单 CANCEL
			if(lotteryPlan.getPlanStatus().getValue()==PlanStatus.CANCEL.getValue()){
				super.errorMessage = "对不起，方案已被撤单!";
				return PackageXml("002", super.errorMessage, "9036", agentId);
			}
			
			if (lotteryPlan.getPlanStatus().getValue() != PlanStatus.RECRUITING.getValue()
					&& lotteryPlan.getPlanStatus().getValue() != PlanStatus.PAY_FINISH.getValue()) {// 方案状态不正常
				return PackageXml("9001", "方案状态错误!", "9300", agentId);
				
			}
			
			Date sysDate=new Date();
			if(sysDate.getTime()>lotteryPlan.getDealDateTime().getTime()){
				return PackageXml("9001", "方案已截止!", "9300", agentId);
			}
			
			
			//判断是否符合条件进行撤单（后继处理）1.是否发起人 0.是否管理员
			//进度
			int newPart = lotteryPlan.getSoldPart() + lotteryPlan.getReservePart();
			double c=(double)newPart/lotteryPlan.getPart()*100;
			double d=(double)(Math.round(c*100))/100.0;
			
			Sites sites=this.sqlSitesService.getSitesInfo();
			if(sites==null){
				return PackageXml("002", "对不起，站点信息为空！", "9036", agentId);
			}
			double endScale=0.8*100;
			Double theTop=sites.getTheTopScheduleOfInitiateCanQuashScheme();
			
			
			//先判断他是否是管理员
			if(scale.equals("1")){
				//判断是不是方案发起人
				if(!lotteryPlan.getAccount().equals(memberSession.getAccount()) && d<=theTop){
					super.errorMessage = "对不起，您不可以撤单，不是发起人或都认购+保底超过了"+(theTop*100)+"%";
					return PackageXml("002", super.errorMessage, "9036", agentId);
				}
			}else{
			   if(d>endScale){
				   super.errorMessage = "对不起，您不可以撤单，认购+保底超过了"+(endScale*100)+"%";
					return PackageXml("002", super.errorMessage, "9036", agentId);
			   }
			}
			
			try {
				//处理方案撤单
				lotteryPlanService.dealPlanBdFailure(lotteryPlan,PlanStatus.CANCEL);
			} catch (Exception e) {
				return PackageXml("002", e.getMessage(), "9036", agentId);
			}
			
			memberSession.setWallet(this.memberWalletService.findByAccount(memberSession.getAccount()));
			memberSession.setScore(memberScoreService.findByAccount(memberSession.getAccount()));
			
			{
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");
				
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
				myBody.addContent(info);
				
				return softwareService.toPackage(myBody, "9036", agentId);
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

				return PackageXml(errStatus, message, "9036", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	//组装错误信息
	public String PackageXml(String CodeEle,String message,String type,String agentId) throws Exception{
		//----------------新建包体--------------------
		 String returnContent="";
		 Element myBody=new Element("body");
		 Element responseCodeEle=new Element("responseCode");
		 Element responseMessage=new Element("responseMessage");
		 responseCodeEle.setText(CodeEle); 
		 responseMessage.setText(message);
		 myBody.addContent(responseCodeEle);
		 myBody.addContent(responseMessage);
		 returnContent= this.softwareService.toPackage(myBody, type, agentId);
		 return returnContent;
		 
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
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

	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}

	public void setSqlSitesService(SitesService sqlSitesService) {
		this.sqlSitesService = sqlSitesService;
	}
}
