package com.yuncai.modules.lottery.software.lottery;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletLineService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberWalletLineNotify extends BaseAction implements SoftwareProcess {
	protected SoftwareService softwareService;
	protected MemberWalletLineService memberWalletLineService;
	protected MemberService memberService;
	protected MemberScoreService memberScoreService;
	protected Map<String, MemberWalletLineNotify>map;
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// TODO Auto-generated method stub
		String account = null;
		Date time = null;
		Integer transType = null;
		Integer lineNo =null;
		Integer order = null;
		Integer pageSize = null;
		Date date = null;
		Integer pageNo = null;
		Date startDate = null;
		Date endDate = null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		
		
		Element details = new Element("details");
		Element revenue = new Element("revenue");
		Element expend = new Element("expend");
		Element win = new Element("win");
		Element rows = new Element("rows");
		

		
		// -------------------------------------------------
		String node = "组装节点不存在";
		String version = null;
		SoftwareProcess softwareProcess = null;
		try {

			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
//			version = "6";
			if(version==null||Integer.parseInt(version.trim())<=5){
				softwareProcess = map.get("5");
			}else {
				softwareProcess = map.get(version);
			}
			if(softwareProcess==null){
//				message = "对应版本处理不存在";
//				return  PackageXml("004", message, "9116", agentId);
				softwareProcess = map.get("5");
			}
			return  softwareProcess.execute(bodyEle, agentId);
		}catch (Exception e) {
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
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	public void setMemberWalletLineService(MemberWalletLineService memberWalletLineService) {
		this.memberWalletLineService = memberWalletLineService;
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}
	
	public void setMap(Map<String, MemberWalletLineNotify> map) {
		this.map = map;
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
	
	/**
	 * 保留两位小时百分比
	 * @param ratio
	 * @return
	 */
	public static String doRatio(double ratio){
		DecimalFormat df = new DecimalFormat("0.00");
		if(ratio==0.0 || ratio==-1.00) return "0.00";
		return df.format(ratio);
	}
}
