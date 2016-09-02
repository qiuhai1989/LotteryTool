package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;

import org.jdom.Element;

import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Sql.PhoneError;
import com.yuncai.modules.lottery.service.sqlService.user.PhoneErrorService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class PhoneErrorNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private PhoneErrorService phoneErrorService;

	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		String phoneNo = null;// 机身码
		// String sim = null;//SIM卡
		String model = null;// 手机机型
		String systemVersion = null;// 手机系统版本
		String channel = null;// 渠道ID
		String version = null;// 客户端版本
		String errorInfo = null;
		// ----------------新建xml包体--------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请求的body
		Element req = bodyEle.getChild("req");
		String node = "组装节点不存在";

		try {
			phoneNo = bodyEle.getChildText("phoneNo");
			model = bodyEle.getChildText("model");
			systemVersion = bodyEle.getChildText("systemVersion");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ? null : bodyEle.getChildText("channel");
			version = StringTools.isNullOrBlank(bodyEle.getChildText("version"))?null:bodyEle.getChildText("version");
			errorInfo = req.getChildText("errorInfo");
			
			PhoneError phoneError = new PhoneError();
			phoneError.setChannelId(channel);
			phoneError.setErrorInfo(errorInfo);
			phoneError.setErrorTime(new Date());
			phoneError.setMachineNo(phoneNo);
			phoneError.setModel(model);
			phoneError.setSystemVersion(systemVersion);
			phoneError.setVersion(version);
			node = "保存失败";
			phoneErrorService.saveOrUpdate(phoneError);
			node = "";
			
			responseCodeEle.setText("0");  //保存成功
			responseMessage.setText("投注成功");
			
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			
			return softwareService.toPackage(myBody, "9419", agentId);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return PackageXml("4019", node, "9001", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return null;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setPhoneErrorService(PhoneErrorService phoneErrorService) {
		this.phoneErrorService = phoneErrorService;
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
}
