package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;
import com.yuncai.modules.lottery.model.Oracle.toolType.JumpType;
import com.yuncai.modules.lottery.model.Sql.ApkNotice;
import com.yuncai.modules.lottery.service.oracleService.member.PagesInfoService;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkNoticeService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ApkNoticeNotify extends BaseAction implements SoftwareProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ApkNoticeService apkNoticeService;
	private SoftwareService softwareService;
	private PagesInfoService pagesInfoService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element notices = new Element("notices");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element nots = bodyEle.getChild("notices");
		String node = "组装节点不存在";
		ApkNotice apkNotice = new ApkNotice();
		Date date = new Date();
		StringBuffer sbNoticeNo=new StringBuffer();
		int k=0;//表示返回数据条数
		try {
			List<?> lnotice = nots.getChildren();
			int size = lnotice.size();
			for (int i = 0; i < size; i++) {
				Boolean btime = true;
				Boolean etime = true;
				String sisUpdate = "";// 0表示数据未更新
				String snoticeContent = "";
				String sIssueTime = "";
				String sEndTime = "";
				String sSendGroup = "";
				String sReceiveUser = "";
				String sNoticeNo = "";
				String sUpdcode = "";
				String sType = "";
				String sPushContent = "";
				String sAddress = "";
				String sPageAddress = "";
				String sIsNeedLogin = "";
				
				Element notice = new Element("notice");
				Element isUpdate = new Element("isUpdate");
				Element noticeId = new Element("noticeId");
				Element noticeContent = new Element("noticeContent");
				Element issueTime = new Element("issueTime");
				Element endTime = new Element("endTime");
				Element sendGroup = new Element("sendGroup");
				Element receiveUser = new Element("receiveUser");
				Element noticeNo = new Element("noticeNo");
				Element updcode = new Element("updcode");
				Element type = new Element("type");
				Element pushContent = new Element("pushContent");
				Element address = new Element("address");
				Element pageAddress = new Element("pageAddress");
				Element isNeedLogin = new Element("isNeedLogin");
				
				Element not = (Element) lnotice.get(i);
				sNoticeNo = not.getChildText("noticeNo");
				sbNoticeNo.append(","+sNoticeNo);
				sUpdcode = not.getChildText("updcode");
				if (DBHelper.isNoNull(sNoticeNo)) {
					List<ApkNotice> list = apkNoticeService.findByProperty("noticeNo", Integer.valueOf(sNoticeNo));
					if (list.size() > 0) {
						apkNotice = list.get(0);
						if (DBHelper.isNoNull(apkNotice)) {
							if (DBHelper.isNoNull(apkNotice.getEndTime()))
								etime = apkNotice.getEndTime().after(date);
							if (DBHelper.isNoNull(apkNotice.getIssueTime()))
								btime = apkNotice.getIssueTime().before(date);
							if (apkNotice.getIsValid() == 1 && etime && btime) {
								k++;
								if (!apkNotice.getUpdcode().equals(sUpdcode)) {
									sisUpdate = "1";
									snoticeContent = apkNotice.getNoticeContent();
									sIssueTime = DateTools.dateToString(apkNotice.getIssueTime());
									sEndTime = DateTools.dateToString(apkNotice.getEndTime());
									sSendGroup = "" + apkNotice.getSendGroup();
									sReceiveUser = apkNotice.getReceiveUser();
									sNoticeNo = "" + apkNotice.getNoticeNo();
									sUpdcode = apkNotice.getUpdcode();
									sType = "" + apkNotice.getType();
									sPushContent = "" + apkNotice.getPushContent();
									sAddress = apkNotice.getAddress();
									// 自选页面
									if(apkNotice.getPushContent() == JumpType.ZXSS.getValue() || apkNotice.getPushContent() == JumpType.ZXDS.getValue()){
										if(DBHelper.isNoNull(apkNotice.getPageId())){
											List<PagesInfo> tmpList = pagesInfoService.findByProperty("pageId", apkNotice.getPageId());
											if(tmpList != null && tmpList.size() > 0){
												PagesInfo pagesInfo = tmpList.get(0);
												
												sPageAddress = pagesInfo.getAddress();
												sIsNeedLogin = pagesInfo.getIsLogin().toString();
											}
										}
									}
								} else {
									sisUpdate = "0";
								}
								isUpdate.setText(sisUpdate);
								noticeContent.setText(snoticeContent);
								issueTime.setText(sIssueTime);
								endTime.setText(sEndTime);
								sendGroup.setText(sSendGroup);
								receiveUser.setText(sReceiveUser);
								noticeNo.setText(sNoticeNo);
								updcode.setText(sUpdcode);
								type.setText(sType);
								pushContent.setText(sPushContent);
								address.setText(sAddress);
								pageAddress.setText(sPageAddress);
								isNeedLogin.setText(sIsNeedLogin);
								
								notice.addContent(isUpdate);
								notice.addContent(noticeContent);
								notice.addContent(issueTime);
								notice.addContent(endTime);
								notice.addContent(sendGroup);
								notice.addContent(receiveUser);
								notice.addContent(noticeNo);
								notice.addContent(updcode);
								notice.addContent(type);
								notice.addContent(pushContent);
								notice.addContent(address);
								notice.addContent(pageAddress);
								notice.addContent(isNeedLogin);
								
								notices.addContent(notice);
							} 
						}
					}
				}
			}
			if (size == 0) {
				List<ApkNotice> listAll = apkNoticeService.findAll(DBHelper.getNow(),"");
				int sizeAll = listAll.size();
				for(ApkNotice an:listAll){
					k++;
					String sisUpdate = "1";
					String snoticeContent = an.getNoticeContent();
					String sIssueTime = DateTools.dateToString(an.getIssueTime());
					String sEndTime = DateTools.dateToString(an.getEndTime());
					String sSendGroup = "" + an.getSendGroup();
					String sReceiveUser = an.getReceiveUser();
					String sNoticeNo = "" + an.getNoticeNo();
					String sUpdcode = an.getUpdcode();
					String sType = "" + an.getType();
					String sPushContent = "" + an.getPushContent();
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
					
					Element notice = new Element("notice");
					Element isUpdate = new Element("isUpdate");
					Element noticeId = new Element("noticeId");
					Element noticeContent = new Element("noticeContent");
					Element issueTime = new Element("issueTime");
					Element endTime = new Element("endTime");
					Element sendGroup = new Element("sendGroup");
					Element receiveUser = new Element("receiveUser");
					Element noticeNo = new Element("noticeNo");
					Element updcode = new Element("updcode");
					Element type = new Element("type");
					Element pushContent = new Element("pushContent");
					Element address = new Element("address");
					Element pageAddress = new Element("pageAddress");
					Element isNeedLogin = new Element("isNeedLogin");
					
					isUpdate.setText(sisUpdate);
					noticeContent.setText(snoticeContent);
					issueTime.setText(sIssueTime);
					endTime.setText(sEndTime);
					sendGroup.setText(sSendGroup);
					receiveUser.setText(sReceiveUser);
					noticeNo.setText(sNoticeNo);
					updcode.setText(sUpdcode);
					type.setText(sType);
					pushContent.setText(sPushContent);
					address.setText(sAddress);
					pageAddress.setText(sPageAddress);
					isNeedLogin.setText(sIsNeedLogin);
					
					notice.addContent(isUpdate);
					notice.addContent(noticeContent);
					notice.addContent(issueTime);
					notice.addContent(endTime);
					notice.addContent(sendGroup);
					notice.addContent(receiveUser);
					notice.addContent(noticeNo);
					notice.addContent(updcode);
					notice.addContent(type);
					notice.addContent(pushContent);
					notice.addContent(address);
					notice.addContent(pageAddress);
					notice.addContent(isNeedLogin);
					
					notices.addContent(notice);
				}
			}else{
				//查询是否有新数据添加
				List<ApkNotice> listOther = apkNoticeService.findAll(DBHelper.getNow(),"("+sbNoticeNo.substring(1)+")");
				int listOthersize = listOther.size();
				if(listOthersize>0){
					for(ApkNotice an:listOther){
						String sisUpdate = "1";
						String snoticeContent = an.getNoticeContent();
						String sIssueTime = DateTools.dateToString(an.getIssueTime());
						String sEndTime = DateTools.dateToString(an.getEndTime());
						String sSendGroup = "" + an.getSendGroup();
						String sReceiveUser = an.getReceiveUser();
						String sNoticeNo = "" + an.getNoticeNo();
						String sUpdcode = an.getUpdcode();
						String sType = "" + an.getType();
						String sPushContent = "" + an.getPushContent();
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
						
						Element notice = new Element("notice");
						Element isUpdate = new Element("isUpdate");
						Element noticeId = new Element("noticeId");
						Element noticeContent = new Element("noticeContent");
						Element issueTime = new Element("issueTime");
						Element endTime = new Element("endTime");
						Element sendGroup = new Element("sendGroup");
						Element receiveUser = new Element("receiveUser");
						Element noticeNo = new Element("noticeNo");
						Element updcode = new Element("updcode");
						Element type = new Element("type");
						Element pushContent = new Element("pushContent");
						Element address = new Element("address");
						Element pageAddress = new Element("pageAddress");
						Element isNeedLogin = new Element("isNeedLogin");
						
						isUpdate.setText(sisUpdate);
						noticeContent.setText(snoticeContent);
						issueTime.setText(sIssueTime);
						endTime.setText(sEndTime);
						sendGroup.setText(sSendGroup);
						receiveUser.setText(sReceiveUser);
						noticeNo.setText(sNoticeNo);
						updcode.setText(sUpdcode);
						type.setText(sType);
						pushContent.setText(sPushContent);
						address.setText(sAddress);
						pageAddress.setText(sPageAddress);
						isNeedLogin.setText(sIsNeedLogin);
						
						notice.addContent(isUpdate);
						notice.addContent(noticeContent);
						notice.addContent(issueTime);
						notice.addContent(endTime);
						notice.addContent(sendGroup);
						notice.addContent(receiveUser);
						notice.addContent(noticeNo);
						notice.addContent(updcode);
						notice.addContent(type);
						notice.addContent(pushContent);
						notice.addContent(address);
						notice.addContent(pageAddress);
						notice.addContent(isNeedLogin);
						
						notices.addContent(notice);
					}
				}
			}
			if (k == 1) {
				Element notice = new Element("notice");
				Element isUpdate = new Element("isUpdate");
				Element noticeId = new Element("noticeId");
				Element noticeContent = new Element("noticeContent");
				Element issueTime = new Element("issueTime");
				Element endTime = new Element("endTime");
				Element sendGroup = new Element("sendGroup");
				Element receiveUser = new Element("receiveUser");
				Element noticeNo = new Element("noticeNo");
				Element updcode = new Element("updcode");
				Element type = new Element("type");
				Element pushContent = new Element("pushContent");
				Element pageAddress = new Element("pageAddress");
				Element isNeedLogin = new Element("isNeedLogin");
				
				isUpdate.setText("");
				noticeContent.setText("");
				issueTime.setText("");
				endTime.setText("");
				sendGroup.setText("");
				receiveUser.setText("");
				noticeNo.setText("");
				updcode.setText("");
				type.setText("");
				pushContent.setText("");
				pageAddress.setText("");
				isNeedLogin.setText("");
				
				notice.addContent(isUpdate);
				notice.addContent(noticeContent);
				notice.addContent(issueTime);
				notice.addContent(endTime);
				notice.addContent(sendGroup);
				notice.addContent(receiveUser);
				notice.addContent(noticeNo);
				notice.addContent(updcode);
				notice.addContent(type);
				notice.addContent(pushContent);
				notice.addContent(pageAddress);
				notice.addContent(isNeedLogin);
				
				notices.addContent(notice);
			}
			myBody.addContent(notices);

			responseCodeEle.setText("0");// 0 ok 1请填充信息
			responseMessage.setText("返回成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			return softwareService.toPackage(myBody, "9204", agentId);
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

	public ApkNoticeService getApkNoticeService() {
		return apkNoticeService;
	}

	public void setApkNoticeService(ApkNoticeService apkNoticeService) {
		this.apkNoticeService = apkNoticeService;
	}

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setPagesInfoService(PagesInfoService pagesInfoService) {
		this.pagesInfoService = pagesInfoService;
	}

}
