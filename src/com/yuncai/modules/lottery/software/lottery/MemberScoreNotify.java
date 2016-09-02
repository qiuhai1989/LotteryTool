package com.yuncai.modules.lottery.software.lottery;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.MemberScoreLine;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberScoreNotify extends BaseAction implements SoftwareProcess {

	private MemberScoreService memberScoreService;
	private SoftwareService softwareService;
	private MemberService memberService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;// 用户名
		Integer score = null;// 发生的积分
		Integer type = null;// 操作类型
		Integer planNO=null;//方案号
		Integer relatedUserID=null;//推荐人ID
		String remark="无";//备注
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element scoreElement= bodyEle.getChild("MemberScore");
		String node = "组装节点不存在";
		try {			
			userName = scoreElement.getChildText("account");// 用户名
			score = Integer.parseInt(scoreElement.getChildText("score"));
			type =Integer.parseInt(scoreElement.getChildText("type"));
			try{
				planNO=scoreElement.getChildText("planNO")==null?null:Integer.parseInt(scoreElement.getChildText("planNO"));
				relatedUserID=scoreElement.getChildText("relatedUserID")==null?null:Integer.parseInt(scoreElement.getChildText("relatedUserID"));
			}catch (Exception e) {
				// TODO: handle exception
			}
			remark = scoreElement.getChildText("remark")==null?"无":scoreElement.getChildText("remark");
			node = null;
			
			//验证登陆
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
			  return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if(!userName.equals(memberSession.getAccount())){
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			Member member=new Member();
			MemberScore memberScore = new MemberScore();
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<MemberScore> list = memberScoreService.findByProperty(
						"account", userName);
				List<Member> listm=memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8007",
							agentId);
				}
				memberScore = list.get(0);
				member=listm.get(0);
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8007", agentId);
			}
			
			if(type==2||type==3|type==4){
				if(score>memberScore.getAbleScore())
				{
					super.errorMessage = "对不起，操作积分不可超过可用积分!";
					return PackageXml("002", super.errorMessage, "8007", agentId);
				}
			}
			
			memberScoreService.increaseScore(member, score, type, planNO, relatedUserID, remark);
			memberSession.setScore(memberScoreService.findByAccount(memberSession.getAccount()));
			
			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "8007", agentId);
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

	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	
}
