package com.yuncai.modules.lottery.software.lottery;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.AccountRelation;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.service.oracleService.member.AccountRelationService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

public class BalanceSearchNotify extends BaseAction implements SoftwareProcess{
	
	private SoftwareService softwareService; 
	private MemberService memberService;
	private MemberScoreService memberScoreService;
	private MemberWalletService memberWalletService;
	private AccountRelationService accountRelationService;
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request=ServletActionContext.getRequest();
		String userName=null;
		String userpwd=null; //增加密码（前提是不一定是登录状态）

		Element vip = new Element("vipInfo");// vip信息
		Element level = new Element("level");// 是否顶层
		Element bonus = new Element("bonus");// 返点比例
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		//-------------------------------------------------
		Element issue=bodyEle.getChild("user");
		String message="组装节点不存在";
		boolean isVip = false;//是否是vip
		try {
			userName=issue.getChildText("userName").trim();
			
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
				try {
					userpwd = issue.getChildText("userpwd").trim();
					//LogUtil.out("userPwd:"+userpwd);
					boolean ret = this.memberService.loginEMd5(userName, userpwd);
					if (!ret) {
						LogUtil.out("帐号或密码错误");
						return PackageXml("2001", "账号或密码错误", "9002", agentId);
					
					}
					memberSession=new Member();
					memberSession.setAccount(userName);
				} catch (Exception e) {
					return PackageXml("2000", "用户名密码为空或没有登录，请登录!", "9005", agentId);
				}
				
			}
			message=null;
			if(userName!=null && !"".equals(userName)){
				if(userName.equals(memberSession.getAccount())){
					Member member = this.memberService.findByAccount(userName);
					MemberScore memberScore = memberScoreService.findByAccount(userName);
					MemberWallet wallet=this.memberWalletService.findByAccount(userName);
					memberSession.setWallet(wallet);
					if(member !=null || memberScore !=null || wallet !=null ){
						
						//封装xml
						Element memberDetail=new Element("memberDetail");
						Element ableBalance=new Element("ableBalance");  //可用余额
						ableBalance.setText(wallet.getAbleBalance().toString());
						Element freezeBalance=new Element("freezeBalance"); //冻结金额
						freezeBalance.setText(wallet.getFreezeBalance().toString()); 
						Element ableScore=new Element("ableScore");//可用积分
						ableScore.setText(memberScore.getAbleScore().toString());
						Element heapScore = new Element("heapScore"); //累计积分
						heapScore.setText(memberScore.getHeapScore().toString());
						
						Element takeCashQuota=new Element("takeCashQuota");
						double cashQuota=wallet.getTakeCashQuota();
						int IntCashQuota=(int)cashQuota;
						takeCashQuota.setText(IntCashQuota+"");
						//takeCashQuota.setText(wallet.getTakeCashQuota().toString());
						
						memberDetail.addContent(ableBalance);
						memberDetail.addContent(freezeBalance);
						memberDetail.addContent(ableScore);
						memberDetail.addContent(heapScore);
						memberDetail.addContent(takeCashQuota);
						
						// 获取VIP等级关系
						AccountRelation accountRelation = accountRelationService.findObjectByProperty("account", userName);
						if (accountRelation != null) {
							isVip = true;
							if (accountRelation.getParentAccount().equals("0")) {
								level.setText("0");
							} else {
								level.setText("1");
							}
							bonus.setText(member.getBonus() == null ? "0" : member.getBonus().toString());
							vip.addContent(level);
							vip.addContent(bonus);
						}
						
						
						//处理成功
						responseCodeEle.setText("0");
						responseMessage.setText("处理成功");
						myBody.addContent(responseCodeEle);
						myBody.addContent(responseMessage);
						myBody.addContent(memberDetail);
						
						if (isVip) {
							myBody.addContent(vip);
						}

						return this.softwareService.toPackage(myBody, "9005", agentId);
					}else{
						return PackageXml("3001", "查询为空", "9005", agentId);
					}
					
				}else{
					return PackageXml("9001", "用户名与Cokie用户不匹配", "9005", agentId);
				}
			}else{
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9005", agentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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
	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}
	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}
	public void setAccountRelationService(AccountRelationService accountRelationService) {
		this.accountRelationService = accountRelationService;
	}
	

}
