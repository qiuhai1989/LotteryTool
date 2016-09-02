package com.yuncai.modules.lottery.software.lottery;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class CheckLoginNotify  extends BaseAction implements SoftwareProcess{
	
	private SoftwareService softwareService; 
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request=ServletActionContext.getRequest();
		//获取请过的body
		Element check=bodyEle.getChild("checkLogin");
		String node="组装节点不存在";
		try {
			String username=check.getChild("userName").getText().trim();
			node=null;
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
				return PackageXml("2000", "请登录!", "9300", agentId);
			}else{
				return PackageXml("0", "已登录", "9300", agentId);
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
	
}
