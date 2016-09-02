package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.bean.search.VipBonusLineSearch;
import com.yuncai.modules.lottery.model.Oracle.AccountRelation;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.VipBonusLine;
import com.yuncai.modules.lottery.service.oracleService.member.AccountRelationService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.VipBonusLineService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class VipBonusLineNotify extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private SoftwareService softwareService;
	private AccountRelationService accountRelationService;
	private VipBonusLineService vipBonusLineService;

	@SuppressWarnings("unused")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		String account = null;
		String vipAccount = null;
		String startTime = null;
		String endTime = null;

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element bonusLineList = new Element("bonusLineList");
		// -------------------------------------------------

		// 获取请求的body
		Element bonusInfo = bodyEle.getChild("bonusInfo");
		String message = "组装节点错误!";
		try {
			account = bonusInfo.getChildText("account");
			vipAccount = bonusInfo.getChildText("vipAccount");
			startTime = bonusInfo.getChildText("startTime");
			endTime = bonusInfo.getChildText("endTime");


			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请重新登录!", "9061", agentId);
			}
			if (!account.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9061", agentId);
			}

			// 判断查询用户是否是VIP
			if (account != null && !"".equals(account)) {				
				String[] names = new String[2];
				names[0] = "account";
				names[1] = "userGradeType";

				Object[] values = new Object[2];
				values[0] = account;
				values[1] = 2;
				
				List<Member> list = memberService.findByPropertys(names, values);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不是VIP!";
					return PackageXml("002", super.errorMessage, "9061", agentId);
				} else {
					// 如果账号不同，检查否是上下级关系
					if (!account.equals(vipAccount)) {
						String[] names2 = new String[2];
						names2[0] = "account";
						names2[1] = "parentAccount";

						Object[] values2 = new Object[2];
						values2[0] = vipAccount;
						values2[1] = account;

						List<AccountRelation> list2 = accountRelationService.findByPropertys(names2, values2);

						if (list2 == null || list2.isEmpty()) {
							super.errorMessage = "无权查看此用户的信息！";
							return PackageXml("002", super.errorMessage, "9061", agentId);
						}
					}
				}
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "9061", agentId);
			}
			
			VipBonusLineSearch search = new VipBonusLineSearch();			
			search.setAccount(vipAccount);
			search.setStartDate(startTime);
			search.setEndDate(endTime);
			
			List<VipBonusLine> vipBonusList =  vipBonusLineService.findAllBySearch(search);
			for(VipBonusLine line : vipBonusList){
				Element bonusLine = new Element("bonusLine");
				Element bonusId = new Element("bonusId");// 明细ID
				Element createDateTime = new Element("createDateTime");// 生成时间
				Element status = new Element("status");// 0：未结算，1已结算
				Element vipNum = new Element("vipNum");// 用户数
				Element ticketNum = new Element("ticketNum");// 投注笔数
				Element ticketAmount = new Element("ticketAmount");// 出票总额
				
				
				bonusId.setText(line.getId().toString());
				createDateTime.setText(line.getTicketDate());
				status.setText(((Integer)line.getStatus().getValue()).toString());
				
				// 发生消费的下线数
				Integer childNum = line.getChildNum()==null?0:line.getChildNum();
				// 本人购票数
				Integer selfTicketNum = line.getTicketNum()==null?0:line.getTicketNum();
				if(selfTicketNum>0){
				vipNum.setText(((Integer)(childNum+1)).toString());
				}else{
					vipNum.setText(childNum.toString());
				}
				
				// 总购票数
				Integer totalTicketNum = selfTicketNum +(line.getChildTicketNum()==null?0:line.getChildTicketNum());//总购票数
				ticketNum.setText(totalTicketNum.toString());
				
				// 总消费额
				Double totalAmount = (line.getTicketAmount()==null?0.0:line.getTicketAmount()) + (line.getChildTicketAmount()==null?0.0:line.getChildTicketAmount());
				ticketAmount.setText(totalAmount.toString());
				
				bonusLine.addContent(bonusId);
				bonusLine.addContent(createDateTime);
				bonusLine.addContent(status);
				bonusLine.addContent(vipNum);
				bonusLine.addContent(ticketNum);
				bonusLine.addContent(ticketAmount);	
				
				bonusLineList.addContent(bonusLine);
			}
			
			if(vipBonusList.size()==1){
				Element bonusLine = new Element("bonusLine");
				Element bonusId = new Element("bonusId");// 明细ID
				Element createDateTime = new Element("createDateTime");// 生成时间
				Element status = new Element("status");// 0：未结算，1已结算
				Element vipNum = new Element("vipNum");// 用户数
				Element ticketNum = new Element("ticketNum");// 投注笔数
				Element ticketAmount = new Element("ticketAmount");// 出票总额
				
				bonusId.setText("");
				createDateTime.setText("");
				status.setText("");
				vipNum.setText("");
				ticketNum.setText("");
				ticketAmount.setText("");
				
				bonusLine.addContent(bonusId);
				bonusLine.addContent(createDateTime);
				bonusLine.addContent(status);
				bonusLine.addContent(vipNum);
				bonusLine.addContent(ticketNum);
				bonusLine.addContent(ticketAmount);	
				
				bonusLineList.addContent(bonusLine);
			}
			
			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(bonusLineList);
			
			return softwareService.toPackage(myBody, "9061", agentId);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9061", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;

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

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setAccountRelationService(AccountRelationService accountRelationService) {
		this.accountRelationService = accountRelationService;
	}

	public void setVipBonusLineService(VipBonusLineService vipBonusLineService) {
		this.vipBonusLineService = vipBonusLineService;
	}

}
