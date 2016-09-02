package com.yuncai.modules.lottery.software.lottery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Sql.ApkPic;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPicService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ApkPicNotify extends BaseAction implements SoftwareProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApkPicService apkPicService;
	private SoftwareService softwareService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element apkPic = new Element("apkPic");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element updcode = new Element("updcode");
		Element pciGroup = new Element("pciGroup");
		Element bandpos = new Element("bandpos");
		Element jumpType = new Element("jumpType");
		Element adContext = new Element("adContext");
		Element imageAdd = new Element("imageAdd");
		Element imageAddB = new Element("imageAddB");
		Element imageAddS = new Element("imageAddS");
		Element isUpdate = new Element("isUpdate");
		Element picId = new Element("picId");
		// -------------------------------------------------
		// 获取请过的body
		Element ap = bodyEle.getChild("ap");
		String node = "组装节点不存在";
		String supdcode = ap.getChildText("updcode");
		int spicId = Integer.valueOf(ap.getChildText("picId"));
		String spciGroup = "";// 图片分组描述
		String sbandpos = "";// 位置编号，例1001首位为组别 后3图序
		String sjumpType = "";// 站内:A 代表合买方案 B充值 C中奖单 D彩种 E活动界面 站外:F
								// 调用浏览器访问URL 文本:G 显示内容
		String sadContext = "";// 类型内容，受Jump_Type
		String simageAdd = "";
		String simageAddB = "";
		String simageAddS = "";
		int scode = 0;
		String sisUpdate = "0";// 为0表示数据未修改 直接显示
		ApkPic sApkPic = new ApkPic();
		try {
			sApkPic = apkPicService.findObjectByProperty("code",spicId);
			String str = "返回成功";
			if(DBHelper.isNoNull(sApkPic)){
				if (sApkPic.getUpdcode().equals(supdcode)) {
					sisUpdate = "0";
				} else {
					if (sApkPic.getIsValid() == 1) {
						spciGroup = sApkPic.getPciGroup();
						sbandpos = "" + sApkPic.getBandpos();
						sjumpType = "" + sApkPic.getJumpType();
						sadContext = sApkPic.getAdContext();
						simageAdd = sApkPic.getImageAdd();
						simageAddB = sApkPic.getImageAddB();
						simageAddS = sApkPic.getImageAddS();
						String sURL = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/software"));
						if(DBHelper.isNoNull(simageAdd))
							simageAdd = sURL+simageAdd;
						if(DBHelper.isNoNull(simageAddB))
							simageAddB = sURL+simageAddB;
						if(DBHelper.isNoNull(simageAddS))
							simageAddS = sURL+simageAddS;
						supdcode = sApkPic.getUpdcode();
						scode = sApkPic.getCode();
					}
					sisUpdate = "1";
				}
			}else{
				str = "编号不存在";
			}

			isUpdate.setText(sisUpdate);
			updcode.setText(supdcode);
			pciGroup.setText(spciGroup);
			bandpos.setText(sbandpos);
			jumpType.setText(sjumpType);
			adContext.setText(sadContext);
			imageAdd.setText(simageAdd);
			imageAddB.setText(simageAddB);
			imageAddS.setText(simageAddS);
			picId.setText(""+scode);
			
			apkPic.addContent(updcode);
			apkPic.addContent(pciGroup);
			apkPic.addContent(bandpos);
			apkPic.addContent(jumpType);
			apkPic.addContent(adContext);
			apkPic.addContent(imageAdd);
			apkPic.addContent(imageAddB);
			apkPic.addContent(imageAddS);
			apkPic.addContent(picId);
			
			myBody.addContent(isUpdate);
			myBody.addContent(apkPic);

			responseCodeEle.setText("0");// 0 ok 1请填充信息
			responseMessage.setText(str);
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			return softwareService.toPackage(myBody, "9202", agentId);
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

	public ApkPicService getApkPicService() {
		return apkPicService;
	}

	public void setApkPicService(ApkPicService apkPicService) {
		this.apkPicService = apkPicService;
	}

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

}
