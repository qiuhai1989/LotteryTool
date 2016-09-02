package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.service.oracleService.card.CardService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class CardListNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private CardService cardService;

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
		Element cardList = new Element("cardList");
		// -------------------------------------------------
		// 获取请过的body
		Element cardType = bodyEle.getChild("cardType");
		String message = "组装节点错误!";
		try {
			userName = cardType.getChildText("account");// 用户名
			isValid = cardType.getChildText("isValid");// 是否自用，0:是，1：否
			amount = cardType.getChildText("amount");// 金额

			List<Object[]> validCardList = new ArrayList<Object[]>();// 渠道卡
			List<Object[]> personalCardList = new ArrayList<Object[]>();// 自用卡

			if (isValid.equals("1")) {
				validCardList = cardService.validCardList(userName, Double.parseDouble(amount));
			} else if (isValid.equals("0")) {
				personalCardList = cardService.personalCardList(userName, Double.parseDouble(amount));
			}

			if (!validCardList.isEmpty() || !personalCardList.isEmpty()) {
				responseCodeEle.setText("0");
				responseMessage.setText("处理成功！");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				if (!validCardList.isEmpty()) {
					for (Object[] menuItem : validCardList) {

						String[] menuItemArray = menuItem.toString().split(",");
						Element card = new Element("card");
						Element cardId = new Element("cardId");// 彩金卡ID
						Element createDateTime = new Element("createDateTime");// 生成时间
						Element expireDateTime = new Element("expireDateTime");// 过期时间
						Element type = new Element("type");// 0：可自用可赠送，1：仅可自用，2：仅可赠送
						Element status = new Element("status");// 0：未使用，1已使用，2已过期

						cardId.setText(menuItem[0].toString());
						createDateTime.setText(menuItem[1].toString().split("\\.")[0]);
						expireDateTime.setText(menuItem[2].toString().split("\\.")[0]);
						type.setText(menuItem[3].toString());

						Date edate = (Date) menuItem[2];
						if (menuItem[4].toString().equals("0") && edate.getTime() < (new Date()).getTime()) {
							status.setText("2");// 已过期
						} else {
							status.setText(menuItem[4].toString());
						}

						card.addContent(cardId);
						card.addContent(createDateTime);
						card.addContent(expireDateTime);
						card.addContent(type);
						card.addContent(status);

						cardList.addContent(card);
					}
				}

				if (!personalCardList.isEmpty()) {
					for (Object[] menuItem : personalCardList) {

						String[] menuItemArray = menuItem.toString().split(",");
						Element card = new Element("card");
						Element cardId = new Element("cardId");
						Element createDateTime = new Element("createDateTime");
						Element expireDateTime = new Element("expireDateTime");
						Element type = new Element("type");
						Element status = new Element("status");

						cardId.setText(menuItem[0].toString());
						createDateTime.setText(menuItem[1].toString().split("\\.")[0]);
						expireDateTime.setText(menuItem[2].toString().split("\\.")[0]);
						type.setText(menuItem[3].toString());

						Date edate = (Date) menuItem[2];
						if (menuItem[4].toString().equals("0") && edate.getTime() < (new Date()).getTime()) {
							status.setText("2");// 已过期
						} else {
							status.setText(menuItem[4].toString());
						}

						card.addContent(cardId);
						card.addContent(createDateTime);
						card.addContent(expireDateTime);
						card.addContent(type);
						card.addContent(status);

						cardList.addContent(card);
					}
				}

				if (validCardList.size() + personalCardList.size() == 1) {
					Element card = new Element("card");
					Element cardId = new Element("cardId");
					Element createDateTime = new Element("createDateTime");
					Element expireDateTime = new Element("expireDateTime");
					Element type = new Element("type");
					Element status = new Element("status");

					cardId.setText("");
					createDateTime.setText("");
					expireDateTime.setText("");
					type.setText("");
					status.setText("");

					card.addContent(cardId);
					card.addContent(createDateTime);
					card.addContent(expireDateTime);
					card.addContent(type);
					card.addContent(status);

					cardList.addContent(card);
				}
				if (validCardList.size() + personalCardList.size() > 0) {
					myBody.addContent(cardList);
				}

				return softwareService.toPackage(myBody, "9053", agentId);
			} else {
				return PackageXml("3001", "暂无彩金卡！", "9053", agentId);
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
				return PackageXml("3002", code, "9053", agentId);
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

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

}
