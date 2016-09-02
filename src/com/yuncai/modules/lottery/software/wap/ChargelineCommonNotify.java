package com.yuncai.modules.lottery.software.wap;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.AmountUtils;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import com.yuncai.modules.lottery.software.service.XFBConfig;

public class ChargelineCommonNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private MemberChargeLineService memberChargeLineService;
	private XFBConfig webConfig;
	
	private IpSeeker ipSeeker;
	
	private MemberOperLineService memberOperLineService ;
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null; // 用户名
	    double  amount =0; // 金额
		String monery=null;
		String changeNo=""; //订单ＩＤ
		String channelId="";
		String returnAddress="";
		String version=null;
		String phoneNo = bodyEle.getChildText("phoneNo");
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		Element change =bodyEle.getChild("change");
		String message = "组装节点不存在";
		try {
			userName = change.getChildText("userName");
			monery=change.getChildText("amount");
			message = null;
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}

			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			if(monery==null && "".equals(monery)){
				return PackageXml("003", "金额不能为空!", "9300", agentId);
			}

			//amount=Double.parseDouble(AmountUtils.changeF2Y(monery.toString()));
			amount=Double.parseDouble(monery);
//			if (amount < 1) {
//				return PackageXml("02", "最低充值金额不应少于10元!", "9300", agentId);
//			}
			MemberChargeLine chargeLine = new MemberChargeLine();
			chargeLine.setAccount(userName);

			chargeLine.setAmount(BigDecimal.valueOf(amount).setScale(2).doubleValue());
			chargeLine.setCreateDateTime(new Date());
			chargeLine.setStatus(CommonStatus.NO);
			chargeLine.setBank("");
			chargeLine.setChargeType(ChargeType.XFB);
			chargeLine.setChannel(super.channelID);
			this.memberChargeLineService.saveNumber(chargeLine);
			
			
			// 创建用户行为记录
			MemberOperLine memberOperLine=new MemberOperLine();
			memberOperLine.setAccount(memberSession.getAccount());
			memberOperLine.setOperType(MemberOperType.ADD_MONEY);
			memberOperLine.setIp(super.remoteIp);
			memberOperLine.setCreateDateTime(new Date());
			memberOperLine.setChannel(channelID);
			memberOperLine.setTerminalId((long)0);
			memberOperLine.setStatus(OperLineStatus.SUCCESS);
			memberOperLine.setVersion(version);
			memberOperLine.setPhoneNo(phoneNo);
			String country = ipSeeker.getCountry(super.remoteIp);
			String area = ipSeeker.getArea(super.remoteIp);
			String fromPlace = country + area;
			memberOperLine.setFromPlace(fromPlace);
			memberOperLineService.save(memberOperLine);
			
			//---------------------------------------------------
			changeNo = chargeLine.getChargeNo();
			channelId = webConfig.getChannelId();
			returnAddress = webConfig.getReturnAddress();
			
			{
				responseCodeEle.setText("0");  //上传成功
				responseMessage.setText("处理成功");
				Element chargeDetail=new Element("chargeDetail");
				Element orderId = new Element("OrderId");
				Element money = new Element("money");
				Element reChannelId = new Element("Sid");
				Element address=new Element("CallBack_Url");
				Element cdkey=new Element("cdkey");
				
				String sid=memberSession.getId().toString();
				String strMonery=AmountUtils.changeY2F(amount+"");
				orderId.setText(changeNo);
				money.setText(strMonery);
				reChannelId.setText(sid);
				address.setText(returnAddress);
				
				String md5key=strMonery+sid+changeNo+channelId;
				//LogUtil.out("monery:"+strMonery+"sid:"+sid+"orderid:"+changeNo+"key:"+channelId);
				md5key=MD5.encode(md5key, "UTF-8");
				
				//LogUtil.out("md5key"+md5key);
				cdkey.setText(md5key);
				
				chargeDetail.addContent(orderId);
				chargeDetail.addContent(money);
				chargeDetail.addContent(reChannelId);
				chargeDetail.addContent(address);
				chargeDetail.addContent(cdkey);
				
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(chargeDetail);
				
				return this.softwareService.toPackage(myBody, "9300", agentId);
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
				return PackageXml("3002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
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

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	


	public void setWebConfig(XFBConfig webConfig) {
		this.webConfig = webConfig;
	}

	public void setMemberChargeLineService(MemberChargeLineService memberChargeLineService) {
		this.memberChargeLineService = memberChargeLineService;
	}

	public IpSeeker getIpSeeker() {
		return ipSeeker;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}

}

