package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.AccountRelation;
import com.yuncai.modules.lottery.model.Oracle.Channel;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.Platform;
import com.yuncai.modules.lottery.model.Oracle.VipGiftDetail;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.service.oracleService.member.AccountRelationService;
import com.yuncai.modules.lottery.service.oracleService.member.ChannelService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.oracleService.member.PlatformService;
import com.yuncai.modules.lottery.service.oracleService.member.VipGiftDetailService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class VipGiftNotify extends BaseAction implements SoftwareProcess {
	private MemberService memberService;
	private MemberWalletService memberWalletService;
	private SoftwareService softwareService;
	private IpSeeker ipSeeker;
	private MemberOperLineService memberOperLineService;
	private VipGiftDetailService vipGiftDetailService;
	private AccountRelationService accountRelationService;
	private ChannelService channelService;
	private PlatformService platformService;
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		String account = null;
		String giveTo = null;
		String giftAmount = null;
		
		String phoneNo;// 机身码
		String sim;// SIM卡
		String model;// 手机机型
		String systemVersion;// 系统版本
		String version=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------

		// 获取请求的body
		Element vipGift = bodyEle.getChild("vipGift");
		String message = "组装节点错误!";
		try {
			account = vipGift.getChildText("account");
			giveTo = vipGift.getChildText("giveTo");
			giftAmount = vipGift.getChildText("amount");
			
			phoneNo = StringTools.isNullOrBlank(bodyEle.getChildText("phoneNo")) ? null : bodyEle.getChildText("phoneNo");
			sim = StringTools.isNullOrBlank(bodyEle.getChildText("sim")) ? null : bodyEle.getChildText("sim");
			model = StringTools.isNullOrBlank(bodyEle.getChildText("model")) ? null : bodyEle.getChildText("model");
			systemVersion = StringTools.isNullOrBlank(bodyEle.getChildText("systemVersion")) ? null : bodyEle.getChildText("systemVersion");
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请重新登录!", "9300", agentId);
			}
			if (!account.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			Member member = new Member();
			MemberWallet memberWallet = new MemberWallet();
			// 判断用户是否存在
			if (account != null && !"".equals(account)) {
				List<Member> list = memberService.findByProperty("account", account);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}

				member = list.get(0);
				memberWallet = memberWalletService.findByAccount(member.getAccount());
				
				if (memberWallet.getAbleBalance() < 1) {
					return PackageXml("01", "对不起，您的余额不足！", "9059", agentId);
				}
				
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}
						
			// 检查是否是VIP
			if (member.getUserGradeType() != 2) {
				return PackageXml("02", "对不起，非VIP用户无法赠送红包。", "9059", agentId);
			}

			// 检查是否存在该用户
			Member memberGiveTo = memberService.findByAccount(giveTo);
			if (memberGiveTo == null || memberGiveTo.getAccount() == null) {
				return PackageXml("03", "用户名不存在", "9059", agentId);
			} else {
				// 被赠已经是VIP
				if (memberGiveTo.getUserGradeType()!=null&& memberGiveTo.getUserGradeType() == 2) {
					return PackageXml("04", "对不起，不能赠送给VIP用户", "9059", agentId);
				}
				// 计算注册相差时间（毫秒）
				long nowTime = (new Date()).getTime();
				long registerTime = memberGiveTo.getRegisterDateTime().getTime();
				long timecomp = nowTime - registerTime;

				// 如果注册时间大于24小时
				if (timecomp > 24 * 60 * 60 * 1000) {
					return PackageXml("04", "对不起，只能赠送给新注册的用户", "9059", agentId);
				}
			}
			
			try{
				Long ct = 0L;
				try {
					String _s = super.getSession().get("s").toString();
					ct = Long.parseLong(_s);
				} catch (Exception e) {
				}
				
				// 获取操作记录编号
				Integer	operLineNO = addOperLine(member.getAccount(), ct, member.getSourceId(), "VIP赠送红包，赠送对象：" + giveTo, "", "", super.remoteIp, MemberOperType.VIP_GIFT,
						phoneNo, sim, model, systemVersion, super.channelID,version);
				Double amount = null;
				
				try{
				//红包金额
				amount = Double.parseDouble(giftAmount);
				}catch (Exception e) {
					return PackageXml("05", "金额格式错误！code:"+e.getMessage(), "9059", agentId);
				}
				
				// 消费
				memberWalletService.consume(member, amount, LotteryType.NENO.getValue(), null, null, operLineNO, 1,
						member.getSourceId(), "VIP赠送红包，赠送对象：" + giveTo,null,new Integer(0),null);
				
				// 增加受赠着可用余额
				memberWalletService.increaseAble(memberGiveTo, amount, LotteryType.NENO.getValue(), null, null, operLineNO, 1, WalletTransType.VIP_PRESENT.getValue(), null, "VIP红包赠送，赠送账号："+member.getAccount());	
								
				// 增加红包记录
				Channel channel = null;
				Platform platform = null;
				// 获取平台渠道
				try{
				channel = channelService.findByProperty("name", super.channelID).get(0);
				}catch (Exception e) {
				}
				try {
					platform = platformService.find(channel.getPlatformId());
				} catch (Exception e) {
				}
				
				VipGiftDetail giftDetail = new VipGiftDetail();
				giftDetail.setAccount(memberGiveTo.getAccount());
				giftDetail.setAmount(amount);
				giftDetail.setChannelId(channel==null?null:channel.getId());
				giftDetail.setChannelName(channel==null?null:channel.getName());
				giftDetail.setPlatFormeId(platform==null?null:platform.getId());
				giftDetail.setPlatformeName(platform==null?null:platform.getName());
				giftDetail.setCreateTimeDate(new Date());
				giftDetail.setGiftName(member.getAccount()+" 好友赠送");
				giftDetail.setMemberId(memberGiveTo.getId());
				giftDetail.setVipAccount(member.getAccount());
				giftDetail.setVipId(member.getId());
				vipGiftDetailService.save(giftDetail);
				
				// 处理VIP关系
				
				// 创建根节点
				AccountRelation root = accountRelationService.findObjectByProperty("account",member.getAccount());
				if(root==null){
					root = new AccountRelation();
					root.setAccount(member.getAccount());
					root.setCreTime(new Date());
					root.setParentAccount("0");	
					accountRelationService.save(root);
				}
								
				AccountRelation accountRelation = new AccountRelation();
				accountRelation.setCreTime(new Date());
				accountRelation.setAccount(memberGiveTo.getAccount());
				accountRelation.setParentAccount(member.getAccount());			
				accountRelationService.save(accountRelation);
				
				memberGiveTo.setUserGradeType(2);//升级为VIP用户
				memberService.update(memberGiveTo);
				
				responseCodeEle.setText("0"); //处理成功
				responseMessage.setText("处理成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
		    	return softwareService.toPackage(myBody, "9059",agentId);
				
			}catch (Exception e) {
				return PackageXml("05", "对不起，赠送失败！code:"+e.getMessage(), "9059", agentId);
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
	
	// 用户操作记录
	public Integer addOperLine(String account, Long ct, Integer sourceId, String extendInfo, String referer, String url, String ip,
			MemberOperType operType, String phoneNo, String sim, String model, String systemVersion, String channel,String version) {
		try {
			MemberOperLine operLine = new MemberOperLine();
			operLine.setAccount(account);
			LogUtil.out("account: " + account);
			operLine.setTerminalId(ct);
			operLine.setSourceId(sourceId);
			operLine.setExtendInfo(extendInfo + "");
			operLine.setCreateDateTime(new java.util.Date());
			operLine.setReferer("");
			operLine.setUrl("");
			operLine.setIp(ip);
			String country = ipSeeker.getCountry(ip);
			String area = ipSeeker.getArea(ip);
			String fromPlace = country + area;
			operLine.setFromPlace(fromPlace);
			operLine.setOperType(operType);
			operLine.setStatus(OperLineStatus.SUCCESS);
			operLine.setPhoneNo(phoneNo);
			operLine.setSim(sim);
			operLine.setModel(model);
			operLine.setSystemVersion(systemVersion);
			operLine.setChannel(channel);
			operLine.setVersion(version);
			operLine.setExtendInfo(extendInfo);
			
			// memberOperLineDao.saveOrUpdate(operLine);
			memberOperLineService.save(operLine);
			return operLine.getOperLineNo();
		} catch (Exception e) {
			return null;
		}
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public MemberWalletService getMemberWalletService() {
		return memberWalletService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public IpSeeker getIpSeeker() {
		return ipSeeker;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public MemberOperLineService getMemberOperLineService() {
		return memberOperLineService;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}

	public VipGiftDetailService getVipGiftDetailService() {
		return vipGiftDetailService;
	}

	public void setVipGiftDetailService(VipGiftDetailService vipGiftDetailService) {
		this.vipGiftDetailService = vipGiftDetailService;
	}

	public AccountRelationService getAccountRelationService() {
		return accountRelationService;
	}

	public void setAccountRelationService(AccountRelationService accountRelationService) {
		this.accountRelationService = accountRelationService;
	}

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public PlatformService getPlatformService() {
		return platformService;
	}

	public void setPlatformService(PlatformService platformService) {
		this.platformService = platformService;
	}

}
