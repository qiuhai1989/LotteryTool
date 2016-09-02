package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;

import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Sql.UserPropose;
import com.yuncai.modules.lottery.service.sqlService.user.UserProposeService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class UserProposeNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService;
	private UserProposeService sqlUserProposeService;
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		
		String account = null;
		String userName = null;
		String mail = null; 
		String propose = null;
		String mobile = null;
		String model = null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		
		// -------------------------------------------------
		Element sug = bodyEle.getChild("sug");
		String node = "组装节点不存在";
		try {
			account = StringTools.isNullOrBlank(sug.getChildText("account"))?null:sug.getChildText("account");
			userName = StringTools.isNullOrBlank(sug.getChildText("userName"))?null:sug.getChildText("userName");
			mail = StringTools.isNullOrBlank(sug.getChildText("mail"))?null:sug.getChildText("mail");
			propose = StringTools.isNullOrBlank(sug.getChildText("propose"))?null:sug.getChildText("propose");
			mobile = StringTools.isNullOrBlank(sug.getChildText("mobile"))?null:sug.getChildText("mobile");
			model = StringTools.isNullOrBlank(bodyEle.getChildText("model"))?null:bodyEle.getChildText("model");
			node = null;
			if(propose==null){
				super.errorMessage = "建议反馈部分不能为空";
				return PackageXml("002", super.errorMessage, "9008",
						agentId);
			}
//			if(propose.length()>200){
//				super.errorMessage = "建议内容长度超出200字符，请调整后提交";
//				return PackageXml("002", super.errorMessage, "9008",
//						agentId);	
//			}
			if(userName!=null&&userName.length()>30){
				super.errorMessage = "用户名长度超出30字符，请调整后提交";
				return PackageXml("002", super.errorMessage, "9008",
						agentId);	
			}
			//验证是否符合正则要求
//			String userNameRegx = "^([a-zA-Z0-9]|[_]){6,20}$";
//			if(!userName.matches(userNameRegx)){
//				super.errorMessage = "用户名必须长度为6～20个字符的英文字母，数字或下划线!";
//				return PackageXml("003", super.errorMessage, "9007", agentId);
//			}
			//验证手机号码格式
			String mobileReg = "^\\d{11}$";
			if(mobile!=null&&!mobile.matches(mobileReg)){
				super.errorMessage = "手机号码格式不正确,请确认后输入";
				return PackageXml("003", super.errorMessage, "9008",
						agentId);
			}
			//验证邮箱号码格式
			String mailReg ="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			if(mail!=null&&!mail.matches(mailReg)&&mail.length()>30){
				super.errorMessage = "邮箱格式不正确，或是否超出30字符,请确认后输入";
				return PackageXml("003", super.errorMessage, "9008",
						agentId);
			}			
			UserPropose userPropose = new UserPropose();
			userPropose.setAccount(account);
			userPropose.setMail(mail);
			userPropose.setMobile(mobile);
			userPropose.setPropose(propose);
			userPropose.setUserName(userName);
			userPropose.setModel(model);
			userPropose.setCreateDate(DateTools.dateToString(new Date()));
			node = "保存过程中出错";
			sqlUserProposeService.save(userPropose);
			node = "保存成功";
			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText(node);
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "9042", agentId);
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

				return PackageXml(errStatus, message, "9042", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return null;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlUserProposeService(UserProposeService sqlUserProposeService) {
		this.sqlUserProposeService = sqlUserProposeService;
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
}
