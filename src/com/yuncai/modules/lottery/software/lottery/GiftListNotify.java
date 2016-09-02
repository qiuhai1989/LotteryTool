package com.yuncai.modules.lottery.software.lottery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.service.oracleService.card.CardService;
import com.yuncai.modules.lottery.service.oracleService.member.GiftDatailLineService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class GiftListNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private GiftDatailLineService giftDatailLineService;

	@SuppressWarnings("unchecked")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null;
		String isValid = null;
		String amount = null;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element giftList = new Element("giftList");
		// -------------------------------------------------
		// 获取请过的body
		Element giftType = bodyEle.getChild("giftType");
		String message = "组装节点错误!";
		try {
			userName = giftType.getChildText("account");// 用户名
			amount = giftType.getChildText("amount");// 金额

			// 查找红包列表

			String names[] = new String[2];
			names[0] = "account";
			names[1] = "amount";

			Object values[] = new Object[2];
			values[0] = userName;
			values[1] = Double.parseDouble(amount);

			List<GiftDatailLine> giftDatailLineList = giftDatailLineService.findByPropertys(names, values);// 渠道卡

			if (giftDatailLineList != null && !giftDatailLineList.isEmpty()) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

				// 组装内容
				for (GiftDatailLine menuItem : giftDatailLineList) {
					Element gift = new Element("gift");
					Element giftId = new Element("giftId");// 彩金卡ID
					Element createDateTime = new Element("createDateTime");// 生成时间
					Element status = new Element("status");// 0：未使用，1已使用，2已过期
					Element giftName = new Element("giftName");

					giftId.setText(menuItem.getId().toString());
					createDateTime.setText(sdf.format(menuItem.getCreateTimeDate()));
					status.setText("1");
					giftName.setText(menuItem.getGiftName());

					gift.addContent(giftId);
					gift.addContent(createDateTime);
					gift.addContent(status);
					gift.addContent(giftName);

					giftList.addContent(gift);
				}

				if (giftDatailLineList.size() == 1) {
					Element gift = new Element("gift");
					Element giftId = new Element("giftId");
					Element createDateTime = new Element("createDateTime");
					Element status = new Element("status");
					Element giftName = new Element("giftName");

					giftId.setText("");
					createDateTime.setText("");
					status.setText("");
					giftName.setText("");

					gift.addContent(giftId);
					gift.addContent(createDateTime);
					gift.addContent(status);
					gift.addContent(giftName);

					giftList.addContent(gift);
				}

				responseCodeEle.setText("0");
				responseMessage.setText("处理成功！");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				
				if (giftDatailLineList.size() > 1) {
					myBody.addContent(giftList);
				}

				return softwareService.toPackage(myBody, "9058", agentId);
			} else {
				return PackageXml("3001", "暂无红包！", "9058", agentId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9058", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setGiftDatailLineService(GiftDatailLineService giftDatailLineService) {
		this.giftDatailLineService = giftDatailLineService;
	}

}
