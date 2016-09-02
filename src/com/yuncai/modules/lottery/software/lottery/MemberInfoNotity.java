package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.bean.vo.MemberInfoShowBean;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberInfoNotity extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private SoftwareService softwareService;
	private MemberWalletService memberWalletService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element UserInfo = new Element("UserInfo");
		Element account = new Element("account");
		Element name = new Element("name");
		Element nickName = new Element("nickName");
		Element certNo = new Element("certNo");
		Element email = new Element("email");
		Element phone = new Element("phone");
		Element blankCard = new Element("blankCard");
		Element address = new Element("address");
		Element part = new Element("part");
		Element takeCashQuota  = new Element("takeCashQuota");//可提金额
		Element ableBalance  = new Element("ableBalance");//可用余额
		Element isLimitDraw   = new Element("isLimitDraw");//可用余额
		// -------------------------------------------------
		Element user = bodyEle.getChild("userInfo");
		String node = "组装节点不存在";
		try {
			String userName = user.getChildText("userName").trim();
			
			
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			Member member = null;
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<Member> list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}
				member = list.get(0);

			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}
			MemberWallet wallet = memberWalletService.findByAccount(member.getAccount());
//			LogUtil.out(Double.toString(wallet.getTakeCashQuota()).substring(0,Double.toString(wallet.getTakeCashQuota()).indexOf(".")));
			takeCashQuota.setText(Double.toString(wallet.getTakeCashQuota()).substring(0,Double.toString(wallet.getTakeCashQuota()).indexOf(".")));
			
			ableBalance.setText(NumberTools.doubleToMoneyString(wallet.getAbleBalance()));
			
			
			MemberInfoShowBean bean = memberService.findMemberInfoShowBeanByName(userName);
//			if(bean==null){
//				return PackageXml("3001", "找不到相应的记录", "9115", agentId);
//			}
			
			isLimitDraw.setText(member.getRecommender().toString());
			
			//在没有银行卡的时候也可以显示
			if(member.getName()!=null){
				name.setText(member.getName());
			}
			nickName.setText(StringTools.isNullOrBlank(member.getNickName())?"":member.getNickName());
			if(member.getCertNo()!=null){
				certNo.setText(member.getCertNo());
			}
			//当银行卡绑定好bean才不会为null
			if(bean!=null){
				account.setText(bean.getAccount());
				
				if(bean.getCertNo()!=null){
					certNo.setText(bean.getCertNo());
				}
				if(bean.getEmail()!=null){
					email.setText(bean.getEmail());
				}
				if(bean.getPhone()!=null){
					phone.setText(bean.getPhone());
				}
				if(bean.getBankCard()!=null){
					blankCard.setText(bean.getBankCard());//bean.getBankCard());
				}
				if(bean.getAddress()!=null){
					address.setText(bean.getAddress());
				}
				if(bean.getPart()!=null){
					part.setText(bean.getPart());
				}
			}
			
			

			
			
			UserInfo.addContent(account);
			UserInfo.addContent(name);
			UserInfo.addContent(nickName);
			UserInfo.addContent(certNo);
			UserInfo.addContent(email);
			UserInfo.addContent(phone);
			UserInfo.addContent(blankCard);
			UserInfo.addContent(address);
			UserInfo.addContent(part);
			UserInfo.addContent(takeCashQuota);
			UserInfo.addContent(ableBalance);
			UserInfo.addContent(isLimitDraw);
			responseCodeEle.setText("0");
			responseMessage.setText("查询成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(UserInfo);
	
			
			return softwareService.toPackage(myBody, "8004", agentId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				return PackageXml("3002", "组装节点不存在", "9011", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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
	// 组装错误信息
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


	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}
	
	
}
