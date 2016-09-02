package com.yuncai.modules.lottery.software.lottery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.IsusesMange;
import com.yuncai.modules.lottery.model.Oracle.Platform;
import com.yuncai.modules.lottery.service.oracleService.member.ChannelService;
import com.yuncai.modules.lottery.service.oracleService.member.IsusesMangeService;
import com.yuncai.modules.lottery.service.oracleService.member.PlatformService;
import com.yuncai.modules.lottery.service.commonService.CommonService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class IsusesMangeNotify extends BaseAction implements SoftwareProcess {
	/**
	 * 彩种管理 客服端接口
	 */
	private static final long serialVersionUID = 1L;
	private IsusesMangeService isusesMangeService;
	private SoftwareService softwareService;
	private PlatformService platformService;
	private CommonService commonService;
	private ChannelService channelService;
	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element isusesManges = new Element("isusesManges");

		// -------------------------------------------------
		String node = "组装节点不存在";
		int sisBetting = 0;// 0不投注
		int ssorder = 0;// 顺序
		String sname = "";// 彩种名称
		String smsg = "";// 彩种信息
		String saddress = "";// 彩种入口地址
		String scontent = "";// 广告语
		String salertMsg = "";//关闭投注时的弹窗信息
		int platformID = 0;// 平台
		String stime = "";// 修改时间
		String slogo = "";
		int sisusesID = -1;// 彩种id
		String channel="";
		int channelID=-1;
		Platform pp = new Platform();
		List<IsusesMange> list = new ArrayList<IsusesMange>();
		if(agentId.equals("offlineAndroid")){
			try {
				channel = bodyEle.getChildText("channel");
				channelID = channelService.findByProperty("name", channel).get(0).getId();
			} catch (Exception e) {
				logger.info("在线数据平台未传送具体渠道");
			}
		}
		if(!agentId.equals("offlineAndroid"))
			pp = platformService.findObjectByProperty("agentId", agentId);
//		if(agentId.equals("offlineAndroid"))
//			pp = platformService.findObjectByProperty("agentId", "ycAndroid");
//		else
		try {
			if(!DBHelper.isNoNull("channel")||channel.equals(""))
				list = isusesMangeService.findByPlatformID_isShow(1, pp.getId());
			else list = isusesMangeService.findByChannelID_isShow(1, channelID);
			for (IsusesMange ism : list) {
				sisusesID = ism.getIsusesID();
				if(sisusesID!=-1){
					Element isusesMange = new Element("isusesMange");
					Element isBetting = new Element("isBetting");
					Element sorder = new Element("sorder");
					Element name = new Element("name");
					Element address = new Element("address");
					Element msg = new Element("msg");
					Element content = new Element("content");
					Element alertMsg = new Element("alertMsg");
					Element updateTime = new Element("updateTime");
					Element logo = new Element("logo");
					Element isusesID = new Element("isusesID");
					sisBetting = ism.getIsBetting();
					ssorder = ism.getSorder();
					sname = ism.getName();
					saddress = ism.getAddress();
					smsg = ism.getMsg();
					//msg不是纯文本的 需要调用接口 返回数据
					if(DBHelper.isNoNull(smsg)){
						if(smsg.substring(0,1).equals("@")){
							smsg = commonService.getInformation(smsg.substring(1));
						}
					}
					scontent = ism.getContent();
					salertMsg = ism.getAlertMsg();
					stime = ism.getUpdateTime();
					slogo = ism.getLogo();
					//应客服端需求，图片传送绝对地址。
					if(DBHelper.isNoNull(slogo))
						slogo = IsusesMangeNotify.getUrl()+slogo;
//						slogo = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/software"))+slogo;
					isBetting.setText("" + sisBetting);
					sorder.setText("" + ssorder);
					name.setText(sname);
					address.setText(saddress);
					msg.setText(smsg);
					content.setText(scontent);
					alertMsg.setText(salertMsg);
					updateTime.setText(stime);
					logo.setText(slogo);
					isusesID.setText(""+sisusesID);
					
					isusesMange.addContent(isBetting);
					isusesMange.addContent(sorder);
					isusesMange.addContent(name);
					isusesMange.addContent(address);
					isusesMange.addContent(msg);
					isusesMange.addContent(content);
					isusesMange.addContent(alertMsg);
					isusesMange.addContent(updateTime);
					isusesMange.addContent(logo);
					isusesMange.addContent(isusesID);
					
					isusesManges.addContent(isusesMange);
				}
			}if(list.size()==1){
				Element isusesMange = new Element("isusesMange");
				Element isBetting = new Element("isBetting");
				Element sorder = new Element("sorder");
				Element name = new Element("name");
				Element address = new Element("address");
				Element msg = new Element("msg");
				Element content = new Element("content");
				Element alertMsg = new Element("alertMsg");
				Element updateTime = new Element("updateTime");
				Element logo = new Element("logo");
				Element isusesID = new Element("isusesID");
				isBetting.setText("");
				sorder.setText("");
				name.setText("");
				address.setText("");
				msg.setText("");
				content.setText("");
				alertMsg.setText("");
				updateTime.setText("");
				logo.setText("");
				isusesID.setText("");
				
				isusesMange.addContent(isBetting);
				isusesMange.addContent(sorder);
				isusesMange.addContent(name);
				isusesMange.addContent(address);
				isusesMange.addContent(msg);
				isusesMange.addContent(content);
				isusesMange.addContent(alertMsg);
				isusesMange.addContent(updateTime);
				isusesMange.addContent(logo);
				isusesMange.addContent(isusesID);

				isusesManges.addContent(isusesMange);
			}
			myBody.addContent(isusesManges);

			responseCodeEle.setText("0");// 0 ok 1请填充信息
			responseMessage.setText("返回成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			return softwareService.toPackage(myBody, "9203", agentId);
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
	
	public static String getUrl(){
		String url = "http://www.dyjw.com";
		try {
				InetAddress addr = InetAddress.getLocalHost();
				String ip=addr.getHostAddress().toString();
				if(ip.equals("192.168.0.66")){
					url = "http://wayin.5166.info:81";
				}else if(ip.equals("192.168.0.59")){
					url = "http://192.168.0.59";
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		return url;
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

	public IsusesMangeService getIsusesMangeService() {
		return isusesMangeService;
	}

	public void setIsusesMangeService(IsusesMangeService isusesMangeService) {
		this.isusesMangeService = isusesMangeService;
	}

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public PlatformService getPlatformService() {
		return platformService;
	}

	public void setPlatformService(PlatformService platformService) {
		this.platformService = platformService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

}
