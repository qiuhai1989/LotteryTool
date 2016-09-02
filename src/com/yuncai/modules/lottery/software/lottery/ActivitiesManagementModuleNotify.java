package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.ActivitiesManagementModule;
import com.yuncai.modules.lottery.service.oracleService.member.ActivitiesManagementModuleService;
import com.yuncai.modules.lottery.service.oracleService.member.ChannelService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ActivitiesManagementModuleNotify extends BaseAction implements SoftwareProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChannelService channelService;
	private SoftwareService softwareService;
	private ActivitiesManagementModuleService ammService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {// 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String channelName = "";// 渠道名称
		String channelRealName = "";// 更新表示

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element amms = new Element("amms");

		String node = "组装节点不存在";
		try {
			channelName = bodyEle.getChildText("channel");
			// updcode = bodyEle.getChildText("updcode");
			try {
				channelRealName = channelService.findByProperty("name", channelName).get(0).getRealName();
			} catch (Exception e) {
				logger.info("渠道" + channelName + "不存在");
				return PackageXml("200", "渠道" + channelName + "不存在", "9207", agentId);
			}
			ActivitiesManagementModule acmm = new ActivitiesManagementModule();
			List<ActivitiesManagementModule> listAmm = ammService.findEffective(channelRealName);
			for (ActivitiesManagementModule acmamo : listAmm) {
				Element amm = new Element("amm");

				Element sorde = new Element("sorde");
				sorde.setText(acmamo.getSorde() + "");
				amm.addContent(sorde);
				Element name = new Element("name");
				name.setText(acmamo.getName());
				amm.addContent(name);
				Element module = new Element("module");
				module.setText(acmamo.getModule() + "");
				amm.addContent(module);
				Element logo = new Element("logo");
				logo.setText(IsusesMangeNotify.getUrl()+acmamo.getLogo());
				amm.addContent(logo);
				Element msg = new Element("msg");
				msg.setText(acmamo.getMsg());
				amm.addContent(msg);
				Element ad = new Element("ad");
				ad.setText(acmamo.getAd());
				amm.addContent(ad);
				Element packageName = new Element("packageName");
				packageName.setText(acmamo.getPackageName());
				amm.addContent(packageName);
				Element address = new Element("address");
				address.setText(IsusesMangeNotify.getUrl()+acmamo.getAddress());
				amm.addContent(address);
				Element version = new Element("version");
				version.setText(acmamo.getVersion() + "");
				amm.addContent(version);
				Element psize = new Element("psize");
				psize.setText(acmamo.getPsize());
				amm.addContent(psize);
				Element pno = new Element("pno");
				pno.setText(acmamo.getPno() + "");
				amm.addContent(pno);
				Element type = new Element("type");
				type.setText(acmamo.getType() + "");
				amm.addContent(type);
				Element updcode = new Element("updcode");
				updcode.setText(acmamo.getUpdcode());
				amm.addContent(updcode);
				
				amms.addContent(amm);
			}

			if(listAmm.size()==1){
				Element amm = new Element("amm");

				Element sorde = new Element("sorde");
				sorde.setText("");
				amm.addContent(sorde);
				Element name = new Element("name");
				name.setText("");
				amm.addContent(name);
				Element module = new Element("module");
				module.setText("");
				amm.addContent(module);
				Element logo = new Element("logo");
				logo.setText("");
				amm.addContent(logo);
				Element msg = new Element("msg");
				msg.setText("");
				amm.addContent(msg);
				Element ad = new Element("ad");
				ad.setText("");
				amm.addContent(ad);
				Element packageName = new Element("packageName");
				packageName.setText("");
				amm.addContent(packageName);
				Element address = new Element("address");
				address.setText("");
				amm.addContent(address);
				Element version = new Element("version");
				version.setText("");
				amm.addContent(version);
				Element psize = new Element("psize");
				psize.setText("");
				amm.addContent(psize);
				Element pno = new Element("pno");
				pno.setText("");
				amm.addContent(pno);
				Element type = new Element("type");
				type.setText("");
				amm.addContent(type);
				Element updcode = new Element("updcode");
				updcode.setText("");
				amm.addContent(updcode);
				
				amms.addContent(amm);
			}
				
			myBody.addContent(amms);

			responseCodeEle.setText("0");// 0 ok 1请填充信息
			responseMessage.setText("返回成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			return softwareService.toPackage(myBody, "9207", agentId);
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

				return PackageXml(errStatus, message, "9207", agentId);
			} catch (Exception e1) {
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

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public ActivitiesManagementModuleService getAmmService() {
		return ammService;
	}

	public void setAmmService(ActivitiesManagementModuleService ammService) {
		this.ammService = ammService;
	}

}
