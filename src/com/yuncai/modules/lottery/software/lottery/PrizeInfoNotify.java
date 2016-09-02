package com.yuncai.modules.lottery.software.lottery;

import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.factorys.chupiao.TicketFormater;
import com.yuncai.modules.lottery.factorys.chupiao.testBuy1;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class PrizeInfoNotify extends BaseAction implements SoftwareProcess {

	private SoftwareService softwareService;

	@Override
	public String execute(Element bodyEle, String agentId) {
		Integer lotteryId = null;
		Integer term = null;// 按彩期查询
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请求信息
		Element prizeInfoEle = bodyEle.getChild("prizeInfo");
		String message = "组装节点不存在";
		try {

			lotteryId = Integer.parseInt(prizeInfoEle.getChildText("lotteryType"));
			term = prizeInfoEle.getChildText("term") == null && " ".equals(prizeInfoEle.getChildText("term")) ? 0 : Integer.parseInt(prizeInfoEle
					.getChildText("term"));

			// 票口玩法代号
			String gameId = TicketFormater.playTypeFormater(LotteryType.getItem(lotteryId));

			if ("".equals(gameId)) {
				return PackageXml("002", "无法识别此彩种", "9047", agentId);
			}

			// 查询开奖信息
			if (testBuy1.getInf112DataStr(gameId, term == 0 ? "" : term.toString()).equals("-1")) {
				return PackageXml("002", "未查询到数据", "9047", agentId);
			} else {
				responseCodeEle.setText("0");
				responseMessage.setText("获取成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "9047", agentId);
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
				return PackageXml("002", code, "9047", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return null;
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

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

}
