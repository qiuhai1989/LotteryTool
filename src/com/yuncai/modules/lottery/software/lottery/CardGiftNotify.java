package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.oracleService.card.CardService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class CardGiftNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private CardService cardService;
	private MemberService memberService;

	@SuppressWarnings("unchecked")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null;
		String cardId = null;// 彩金卡ID
		String giveTo = null;// 被赠送者账号
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element cardGift = bodyEle.getChild("cardGift");
		String message = "组装节点错误!";
		try {
			userName = cardGift.getChildText("account");
			cardId = cardGift.getChildText("cardId");
			giveTo = cardGift.getChildText("giveTo");

			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9055", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9055", agentId);
			}

			Member memberGiveTo = memberService.findByAccount(giveTo);
			if(memberGiveTo==null||memberGiveTo.getAccount()==null){
				return PackageXml("03", "赠送用户不存在", "9055", agentId);
			}
			
			Card cardGiftTo=cardService.find(Integer.parseInt(cardId));
			if(cardGiftTo==null||cardGiftTo.getNo()==null){
				return PackageXml("01", "赠送卡号不存在", "9055", agentId);
			}else if(cardGiftTo.getOwner()==null||!cardGiftTo.getOwner().equals(userName)){
				return PackageXml("05", "对不起，您不能赠送该卡", "9055", agentId);	
			}else if(cardGiftTo.getAccount()!=null||cardGiftTo.getUseDateTime()!=null){
				return PackageXml("02", "对不起，该卡已使用", "9055", agentId);	
			}
			else if(cardGiftTo.getExpireDateTime().getTime()<(new Date()).getTime()){
				return PackageXml("06", "对不起，该卡已过期", "9055", agentId);				
			}else{
				try{
					// 处理赠送
					cardGiftTo.setAccount(giveTo);
					cardGiftTo.setMemberId(memberGiveTo.getId());
					cardService.update(cardGiftTo);	
										
					responseCodeEle.setText("0"); //处理成功
					responseMessage.setText("处理成功");
					myBody.addContent(responseCodeEle);
					myBody.addContent(responseMessage);
			    	return softwareService.toPackage(myBody, "9001",agentId);
					
				}catch(Exception e){
					return PackageXml("04", "对不起，系统出错，赠送不成功", "9055", agentId);		
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9053", agentId);
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

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
