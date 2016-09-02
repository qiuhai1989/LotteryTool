package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.service.oracleService.card.CardService;
import com.yuncai.modules.lottery.service.oracleService.member.GiftDatailLineService;
import com.yuncai.modules.lottery.service.oracleService.member.GiftManageService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class CardMenuNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private CardService cardService;
	private GiftDatailLineService giftDatailLineService;

	@SuppressWarnings("unchecked")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element cardMenu = new Element("cardMenu");
		Element giftMenu = new Element("giftMenu");
		// -------------------------------------------------
		// 获取请过的body
		Element user = bodyEle.getChild("member");
		String message = "组装节点错误!";
		try {
			userName = user.getChildText("account");

			// // 验证登陆
			// Member memberSession = (Member)
			// request.getSession().getAttribute(
			// Constant.MEMBER_LOGIN_SESSION);
			// if (memberSession == null) {
			// return PackageXml("2000", "请登录!", "9052", agentId);
			// }
			// if (!userName.equals(memberSession.getAccount())) {
			// return PackageXml("9001", "用户名与Cokie用户不匹配", "9052", agentId);
			// }

			List<Object[]> validCardMenu = cardService.validCardMenu(userName);// 渠道卡
			List<Object[]> personalCardMenu = cardService.personalCardMenu(userName);// 自用卡

			List<Object[]> giftMenuList = giftDatailLineService.giftMenu(userName);// 红包菜单

			if (!validCardMenu.isEmpty() || !personalCardMenu.isEmpty() || !giftMenuList.isEmpty()) {
				responseCodeEle.setText("0");
				responseMessage.setText("处理成功！");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);

				if (!validCardMenu.isEmpty()) {
					for (Object[] menuItem : validCardMenu) {

						String[] menuItemArray = menuItem.toString().split(",");
						Element cardType = new Element("cardType");
						Element amount = new Element("amount");
						Element count = new Element("count");
						Element isValid = new Element("isValid");

						amount.setText(menuItem[1].toString());
						isValid.setText("1");
						count.setText(menuItem[0].toString());

						cardType.addContent(amount);
						cardType.addContent(isValid);
						cardType.addContent(count);

						cardMenu.addContent(cardType);
					}

				}
				if (!personalCardMenu.isEmpty()) {
					for (Object[] menuItem : personalCardMenu) {

						String[] menuItemArray = menuItem.toString().split(",");
						Element cardType = new Element("cardType");
						Element amount = new Element("amount");
						Element count = new Element("count");
						Element isValid = new Element("isValid");

						amount.setText(menuItem[1].toString());
						isValid.setText("0");
						count.setText(menuItem[0].toString());

						cardType.addContent(amount);
						cardType.addContent(isValid);
						cardType.addContent(count);

						cardMenu.addContent(cardType);
					}

				}

				if (validCardMenu.size() + personalCardMenu.size() == 1) {
					Element cardType = new Element("cardType");
					Element amount = new Element("amount");
					Element count = new Element("count");
					Element isValid = new Element("isValid");

					amount.setText("");
					isValid.setText("");
					count.setText("");

					cardType.addContent(amount);
					cardType.addContent(isValid);
					cardType.addContent(count);

					cardMenu.addContent(cardType);
				}

				if (!giftMenuList.isEmpty()) {
					for (Object[] menuItem : giftMenuList) {
						Element giftType = new Element("giftType");
						Element amount = new Element("amount");
						Element count = new Element("count");

						count.setText(menuItem[0].toString());
						amount.setText(menuItem[1].toString());

						giftType.addContent(count);
						giftType.addContent(amount);

						giftMenu.addContent(giftType);
					}
				}

				if (giftMenuList.size() == 1) {
					Element giftType = new Element("giftType");
					Element amount = new Element("amount");
					Element count = new Element("count");

					count.setText("");
					amount.setText("");

					giftType.addContent(count);
					giftType.addContent(amount);

					giftMenu.addContent(giftType);
				}

				if (validCardMenu.size() + personalCardMenu.size() > 0) {
					myBody.addContent(cardMenu);
				}
				if (giftMenuList.size() > 0) {
					myBody.addContent(giftMenu);
				}
				return softwareService.toPackage(myBody, "9052", agentId);
			} else {
				return PackageXml("3001", "暂无彩金卡或红包！", "9052", agentId);
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

	public void setGiftDatailLineService(GiftDatailLineService giftDatailLineService) {
		this.giftDatailLineService = giftDatailLineService;
	}

}
