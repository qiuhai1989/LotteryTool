package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;

import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.UserShareRecord;
import com.yuncai.modules.lottery.service.oracleService.member.UserShareRecordService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class UserShareRecordNotify extends BaseAction implements SoftwareProcess{
	private UserShareRecordService userShareRecordService;
	private SoftwareService softwareService; 
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		String account = null;
		String shareNo = null;
		String shareName = null;
		String message="组装节点错误!";
		//获取请求的body
		Element req=bodyEle.getChild("req");
		try{
			account = req.getChildText("account");
			shareNo = req.getChildText("shareNo");
			shareName = req.getChildText("shareName");
			if(account==null){
				message="account不能为空!";
				return PackageXml("4022", message, "4022", agentId);
			}
			if(shareNo==null){
				message="shareNo不能为空!";
				return PackageXml("4022", message, "4022", agentId);
			}
			if(shareName==null){
				message="shareName不能为空!";
				return PackageXml("4022", message, "4022", agentId);
			}
			UserShareRecord record = new UserShareRecord();
			record.setAccount(account);
			record.setShareName(shareName);
			record.setShareNo(shareNo);
			record.setSaveTime(new Date());
			
			 message="保存出现错误!";
			userShareRecordService.saveOrUpdate(record);
			message="保存成功";
		}catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="保存出现异常";
				}
				return PackageXml("4022", code, "4022", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return null;
	}

	public UserShareRecordService getUserShareRecordService() {
		return userShareRecordService;
	}

	public void setUserShareRecordService(UserShareRecordService userShareRecordService) {
		this.userShareRecordService = userShareRecordService;
	}
	
	
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
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
}
