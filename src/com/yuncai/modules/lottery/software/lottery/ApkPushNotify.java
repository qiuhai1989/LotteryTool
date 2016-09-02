package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;
import com.yuncai.modules.lottery.model.Oracle.toolType.JumpType;
import com.yuncai.modules.lottery.model.Sql.ApkPush;
import com.yuncai.modules.lottery.service.oracleService.member.PagesInfoService;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPushService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ApkPushNotify extends BaseAction implements SoftwareProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ApkPushService apkPushService;
	private SoftwareService softwareService;
	private PagesInfoService pagesInfoService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element pushs = new Element("pushs");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element nots = bodyEle.getChild("pushs");
		String user = bodyEle.getChildText("user");
		String node = "组装节点不存在";
		ApkPush apkPush = new ApkPush();
		Date date = new Date();
		StringBuffer sbPushNo=new StringBuffer();
		int k=0;//表示返回数据条数
		try {
			List<?> lpush = nots.getChildren();
			int size = lpush.size();
			for(int i=0;i<size;i++){
				String sPushAd = "";
				String sisUpdate = "";//0表示数据未更新
				String sPushTime = "";
				String sPushSpace = "";
				String sPushNum = "";
				String sSendGroup = "";
				String sReceiveUser = "";
				String sPushNo="";
				String sUpdcode = "";
				String sType = "";
				String sPushContent = "";
				String sAddress = "";
				String sPageAddress = "";
				String sIsNeedLogin = "";
				
				Element push = new Element("push");
				Element isUpdate = new Element("isUpdate");
				Element pushAd = new Element("pushAd");
				Element type = new Element("type");
				Element pushContent = new Element("pushContent");
				Element pushTime = new Element("pushTime");
				Element pushSpace = new Element("pushSpace");
				Element pushNum = new Element("pushNum");
				Element sendGroup = new Element("sendGroup");
				Element receiveUser = new Element("receiveUser");
				Element pushNo = new Element("pushNo");
				Element updcode = new Element("updcode");
				Element address = new Element("address");
				Element pageAddress = new Element("pageAddress");
				Element isNeedLogin = new Element("isNeedLogin");
				
				Element not = (Element)lpush.get(i);
				sPushNo = not.getChildText("pushNo");
				sbPushNo.append(","+sPushNo);
				sUpdcode = not.getChildText("updcode");
				if(DBHelper.isNoNull(sPushNo)){
					List<ApkPush> list= apkPushService.findByProperty("id",Integer.valueOf(sPushNo));
					if(list.size()>0){
						apkPush = list.get(0);
						if(DBHelper.isNoNull(apkPush)){
							if(apkPush.getIsValid()==1){
								k++;
								if(!apkPush.getUpdcode().equals(sUpdcode)){
									sisUpdate="1";
									sPushAd = apkPush.getPushAd();
									sType = ""+apkPush.getType();
									sPushContent = ""+apkPush.getPushContent();
									sPushTime  = apkPush.getPushTime();
									sPushSpace = apkPush.getPushSpace();
									sPushNum = ""+apkPush.getPushNum();
									sSendGroup = ""+apkPush.getSendGroup();
									sReceiveUser = apkPush.getReceiveUser();
									sPushNo = ""+apkPush.getId();
									sUpdcode = apkPush.getUpdcode();
									sAddress = apkPush.getAddress();
									
									// 自选页面
									if(apkPush.getPushContent() == JumpType.ZXSS.getValue() || apkPush.getPushContent() == JumpType.ZXDS.getValue()){
										if(DBHelper.isNoNull(apkPush.getPageId())){
											List<PagesInfo> tmpList = pagesInfoService.findByProperty("pageId", apkPush.getPageId());
											if(tmpList != null && tmpList.size() > 0){
												PagesInfo pagesInfo = tmpList.get(0);
												
												sPageAddress = pagesInfo.getAddress();
												sIsNeedLogin = pagesInfo.getIsLogin().toString();
											}
										}
									}
									
								}else{
									sisUpdate="0";
								}

								isUpdate.setText(sisUpdate);
								pushAd.setText(sPushAd);
								type.setText(sType);
								pushContent.setText(sPushContent);
								pushTime.setText(sPushTime);
								pushSpace.setText(sPushSpace);
								pushNum.setText(sPushNum);
								sendGroup.setText(sSendGroup);
								receiveUser.setText(sReceiveUser);
								pushNo.setText(sPushNo);
								updcode.setText(sUpdcode);
								address.setText(sAddress);
								pageAddress.setText(sPageAddress);
								isNeedLogin.setText(sIsNeedLogin);
								
								push.addContent(isUpdate);
								push.addContent(pushAd);
								push.addContent(type);
								push.addContent(pushContent);
								push.addContent(pushTime);
								push.addContent(pushSpace);
								push.addContent(pushNum);
								push.addContent(sendGroup);
								push.addContent(receiveUser);
								push.addContent(pushNo);
								push.addContent(updcode);
								push.addContent(address);
								push.addContent(pageAddress);
								push.addContent(isNeedLogin);
								
								pushs.addContent(push);
								
								if(apkPush.getPushContent()==4||apkPush.getPushContent()==8){//如果是中奖或余额变动的push，push一次后，使数据失效
									apkPush.setIsValid(0);
									apkPushService.update(apkPush);
								}
							}
						}
					}
				}
			}
			
			if (size == 0&&!DBHelper.isNoNull(user)) {
				List<ApkPush> listAll = apkPushService.findAll(DBHelper.getNow(),"","",super.channelID);
				int sizeAll = listAll.size();
				for(ApkPush an:listAll){
					k++;
					String sisUpdate="1";
					String sPushAd = an.getPushAd();
					String sType = ""+an.getType();
					String sPushContent = ""+an.getPushContent();
					String sPushTime  = an.getPushTime();
					String sPushSpace = an.getPushSpace();
					String sPushNum = ""+an.getPushNum();
					String sSendGroup = ""+an.getSendGroup();
					String sReceiveUser = an.getReceiveUser();
					String sPushNo = ""+an.getId();
					String sUpdcode = an.getUpdcode();
					String sAddress = an.getAddress();
					String sPageAddress = "";
					String sIsNeedLogin = "";
					
					// 自选页面
					if(an.getPushContent() == JumpType.ZXSS.getValue() || an.getPushContent() == JumpType.ZXDS.getValue()){
						if(DBHelper.isNoNull(an.getPageId())){
							List<PagesInfo> tmpList = pagesInfoService.findByProperty("pageId", an.getPageId());
							if(tmpList != null && tmpList.size() > 0){
								PagesInfo pagesInfo = tmpList.get(0);
								
								sPageAddress = pagesInfo.getAddress();
								sIsNeedLogin = pagesInfo.getIsLogin().toString();
							}
						}
					}

					Element push = new Element("push");
					Element isUpdate = new Element("isUpdate");
					Element pushAd = new Element("pushAd");
					Element type = new Element("type");
					Element pushContent = new Element("pushContent");
					Element pushTime = new Element("pushTime");
					Element pushSpace = new Element("pushSpace");
					Element pushNum = new Element("pushNum");
					Element sendGroup = new Element("sendGroup");
					Element receiveUser = new Element("receiveUser");
					Element pushNo = new Element("pushNo");
					Element updcode = new Element("updcode");
					Element address = new Element("address");
					Element pageAddress = new Element("pageAddress");
					Element isNeedLogin = new Element("isNeedLogin");
					
					isUpdate.setText(sisUpdate);
					pushAd.setText(sPushAd);
					type.setText(sType);
					pushContent.setText(sPushContent);
					pushTime.setText(sPushTime);
					pushSpace.setText(sPushSpace);
					pushNum.setText(sPushNum);
					sendGroup.setText(sSendGroup);
					receiveUser.setText(sReceiveUser);
					pushNo.setText(sPushNo);
					updcode.setText(sUpdcode);
					address.setText(sAddress);
					pageAddress.setText(sPageAddress);
					isNeedLogin.setText(sIsNeedLogin);
						
					push.addContent(isUpdate);
					push.addContent(pushAd);
					push.addContent(type);
					push.addContent(pushContent);
					push.addContent(pushTime);
					push.addContent(pushSpace);
					push.addContent(pushNum);
					push.addContent(sendGroup);
					push.addContent(receiveUser);
					push.addContent(pushNo);
					push.addContent(updcode);
					push.addContent(address);
					push.addContent(pageAddress);
					push.addContent(isNeedLogin);
					
					pushs.addContent(push);
					
					if(sPushContent.equals("4")||sPushContent.equals("8")){//如果是中奖的push，push一次后，使数据失效
						an.setIsValid(0);
						apkPushService.update(an);
					}
				}
			}else{
				//查询是否有新数据添加
				String str = "";
				if(size>0){
					str = "("+sbPushNo.substring(1)+")";
				}
				List<ApkPush> listOther = apkPushService.findAll(DBHelper.getNow(),str,user,super.channelID);
				int listOthersize = listOther.size();
				if(listOthersize>0){
					int userLength=0;
					for(ApkPush an:listOther){
						Boolean b = true;
						String sSendGroup = ""+an.getSendGroup();
						String sReceiveUser = an.getReceiveUser();
						//接受对象包含此用户的才传送
						if(sSendGroup.equals("2")){
							String[] suserLength =sReceiveUser.split(",");
							userLength = suserLength.length;
							if(userLength==1){
								if(!sReceiveUser.equals(user))
									b=false;
							}
							if(userLength>1){
								b=false;
								for(int m=0;m<userLength;m++){
									if(suserLength[m].equals(user))
										b=true;
								}
							}
						}
						if(b){
							k++;
							String sisUpdate="1";
							String sPushAd = an.getPushAd();
							String sType = ""+an.getType();
							String sPushContent = ""+an.getPushContent();
							String sPushTime  = an.getPushTime();
							String sPushSpace = an.getPushSpace();
							String sPushNum = ""+an.getPushNum();
							String sPushNo = ""+an.getId();
							String sUpdcode = an.getUpdcode();
							String sAddress = an.getAddress();
							String sPageAddress = "";
							String sIsNeedLogin = "";
							
							// 自选页面
							if(an.getPushContent() == JumpType.ZXSS.getValue() || an.getPushContent() == JumpType.ZXDS.getValue()){
								if(DBHelper.isNoNull(an.getPageId())){
									List<PagesInfo> tmpList = pagesInfoService.findByProperty("pageId", an.getPageId());
									if(tmpList != null && tmpList.size() > 0){
										PagesInfo pagesInfo = tmpList.get(0);
										
										sPageAddress = pagesInfo.getAddress();
										sIsNeedLogin = pagesInfo.getIsLogin().toString();
									}
								}
							}
							
							Element push = new Element("push");
							Element isUpdate = new Element("isUpdate");
							Element pushAd = new Element("pushAd");
							Element type = new Element("type");
							Element pushContent = new Element("pushContent");
							Element pushTime = new Element("pushTime");
							Element pushSpace = new Element("pushSpace");
							Element pushNum = new Element("pushNum");
							Element sendGroup = new Element("sendGroup");
							Element receiveUser = new Element("receiveUser");
							Element pushNo = new Element("pushNo");
							Element updcode = new Element("updcode");
							Element address = new Element("address");
							Element pageAddress = new Element("pageAddress");
							Element isNeedLogin = new Element("isNeedLogin");
							
							isUpdate.setText(sisUpdate);
							pushAd.setText(sPushAd);
							type.setText(sType);
							pushContent.setText(sPushContent);
							pushTime.setText(sPushTime);
							pushSpace.setText(sPushSpace);
							pushNum.setText(sPushNum);
							sendGroup.setText(sSendGroup);
							receiveUser.setText(sReceiveUser);
							pushNo.setText(sPushNo);
							updcode.setText(sUpdcode);
							address.setText(sAddress);
							pageAddress.setText(sPageAddress);
							isNeedLogin.setText(sIsNeedLogin);
							
							push.addContent(isUpdate);
							push.addContent(pushAd);
							push.addContent(type);
							push.addContent(pushContent);
							push.addContent(pushTime);
							push.addContent(pushSpace);
							push.addContent(pushNum);
							push.addContent(sendGroup);
							push.addContent(receiveUser);
							push.addContent(pushNo);
							push.addContent(updcode);
							push.addContent(address);
							push.addContent(pageAddress);
							push.addContent(isNeedLogin);
							
							pushs.addContent(push);
							if(sPushContent.equals("4")||sPushContent.equals("8")){//如果是中奖的push，push一次后，使数据失效
								an.setIsValid(0);
								apkPushService.update(an);
							}
						}
					}
				}
			}
			if(k==1){
				Element push = new Element("push");
				Element isUpdate = new Element("isUpdate");
				Element pushAd = new Element("pushAd");
				Element type = new Element("type");
				Element pushContent = new Element("pushContent");
				Element pushTime = new Element("pushTime");
				Element pushSpace = new Element("pushSpace");
				Element pushNum = new Element("pushNum");
				Element sendGroup = new Element("sendGroup");
				Element receiveUser = new Element("receiveUser");
				Element pushNo = new Element("pushNo");
				Element updcode = new Element("updcode");
				Element pageAddress = new Element("pageAddress");
				Element isNeedLogin = new Element("isNeedLogin");
				
				isUpdate.setText("");
				pushAd.setText("");
				type.setText("");
				pushContent.setText("");
				pushTime.setText("");
				pushSpace.setText("");
				pushNum.setText("");
				sendGroup.setText("");
				receiveUser.setText("");
				pushNo.setText("");
				updcode.setText("");
				pageAddress.setText("");
				isNeedLogin.setText("");
				
				push.addContent(isUpdate);
				push.addContent(pushAd);
				push.addContent(type);
				push.addContent(pushContent);
				push.addContent(pushTime);
				push.addContent(pushSpace);
				push.addContent(pushNum);
				push.addContent(sendGroup);
				push.addContent(receiveUser);
				push.addContent(pushNo);
				push.addContent(updcode);
				push.addContent(pageAddress);
				push.addContent(isNeedLogin);
				
				pushs.addContent(push);
			}
				myBody.addContent(pushs);
				
				responseCodeEle.setText("0");// 0 ok 1请填充信息
				responseMessage.setText("返回成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "9205", agentId);
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

	public ApkPushService getApkPushService() {
		return apkPushService;
	}

	public void setApkPushService(ApkPushService apkPushService) {
		this.apkPushService = apkPushService;
	}

	public void setPagesInfoService(PagesInfoService pagesInfoService) {
		this.pagesInfoService = pagesInfoService;
	}

}
