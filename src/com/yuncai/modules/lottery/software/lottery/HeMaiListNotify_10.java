package com.yuncai.modules.lottery.software.lottery;

import java.util.Calendar;
import java.util.List;

import org.jdom.Element;

import com.mysql.jdbc.log.Log;
import com.yuncai.core.tools.ArithUtil;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class HeMaiListNotify_10 extends HeMaiListNotify {

	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		Element hmFound = bodyEle.getChild("hmFound");
		LotteryType lotteryType = null;
		String account = null;
		int offSet = 1;
		int pageSize = 10;
		int order = 0;
		int key = 0;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		String message = "组装节点不存在";
		try {

			if (hmFound.getChildText("lotteryType") != null && !"".equals(hmFound.getChildText("lotteryType"))
					&& Integer.parseInt(hmFound.getChild("lotteryType").getText().trim()) > 0) {

				lotteryType = LotteryType.getItem(Integer.parseInt(hmFound.getChild("lotteryType").getText().trim()));
			} else {
				lotteryType = LotteryType.getItem(-1);
			}
			if (hmFound.getChild("readName") != null && !"0".equals(hmFound.getChild("readName").getText().trim())) {
				account = hmFound.getChild("readName").getText().trim();
			} else {
				account = null;
			}
			if (hmFound.getChildText("offSet") != null && !"".equals(hmFound.getChildText("offSet"))) {
				try {
					offSet = Integer.parseInt(hmFound.getChildText("offSet").trim());
				} catch (Exception e) {
					offSet = 1;
				}
			}
			if (hmFound.getChildText("pageSize") != null && !"".equals("pageSize") && !"0".equals(hmFound.getChildText("pageSize").trim())) {
				try {
					pageSize = Integer.parseInt(hmFound.getChildText("pageSize").trim());
				} catch (Exception e) {
					pageSize = 10;
				}
			}
			order = StringTools.isNullOrBlank(hmFound.getChildText("order")) ? 0 : Integer.parseInt(hmFound.getChildText("order"));
			key = StringTools.isNullOrBlank(hmFound.getChildText("key")) ? 0 : Integer.parseInt(hmFound.getChildText("key"));
			message = null;
			List<LotteryPlan> plans = lotteryPlanService.findLotteryPlans(lotteryType, account, offSet, pageSize, PlanStatus.getItem(2), PlanType
					.getItem(2), order, key);
			if (plans.size() != 0) {
				// 新建xml包装体
				Element recordDetails = new Element("recordDetails");
				responseCodeEle.setText("0");
				responseMessage.setText("查询成功");

				for (LotteryPlan lotteryPlan : plans) {
					assembleCoreContent(lotteryPlan, recordDetails);

				}
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(recordDetails);
				return softwareService.toPackage(myBody, "9030", agentId);

			} else {
				return PackageXml("002", "没有找到相应的记录", "9116", agentId);
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
				return PackageXml("002", code, "9116", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 组装核心内容
	 * 
	 * @param lotteryPlan
	 *            提取对象
	 * @param recordDetails
	 *            组装后添加到的对象
	 */
	void assembleCoreContent(LotteryPlan lotteryPlan, Element recordDetails) {
		Element recordDetail = new Element("recordDetail");
		Element planNo = new Element("planNo");
		Element belongAccount = new Element("belongAccount");
		Element game = new Element("game");
		Element dealDateTime = new Element("dealDateTime");
		Element totalAmount = new Element("totalAmount");
		Element schedule = new Element("schedule");
		Element otherAmount = new Element("otherAmount");
		Element status = new Element("status");
		Element nickName = new Element("nickName");
		Element card = new Element("card");
		planNo.setText(Integer.toString(lotteryPlan.getPlanNo()));
		belongAccount.setText(lotteryPlan.getAccount());
		game.setText(lotteryPlan.getLotteryType().getName());
		// LogUtil.out(lotteryPlan.getLotteryType().getName()+"---"+lotteryPlan.getTerm());

		// 排除掉竞彩的情况
		if (!LotteryType.JCZQList.contains(lotteryPlan.getLotteryType()) && !LotteryType.JCLQList.contains(lotteryPlan.getLotteryType())) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lotteryPlan.getDealDateTime());
			calendar.add(Calendar.MINUTE, -20);

			dealDateTime.setText(DateTools.DateToString_ddMMHHmm(calendar.getTime()));
		} else {

			dealDateTime.setText(DateTools.DateToString_ddMMHHmm(lotteryPlan.getDealDateTime()));
		}

		totalAmount.setText(Integer.toString(lotteryPlan.getAmount()));
		schedule.setText(NumberTools.doubleToPercent(ArithUtil.div(lotteryPlan.getSoldPart(), lotteryPlan.getPart(), 2), "0%") + "+"
				+ NumberTools.doubleToPercent(ArithUtil.div(lotteryPlan.getReservePart(), lotteryPlan.getPart(), 2), "0%"));
		otherAmount.setText(Integer.toString((lotteryPlan.getPart() - lotteryPlan.getSoldPart()) * lotteryPlan.getPerAmount()));
		status.setText(lotteryPlan.getPlanStatus().getName());

		if (lotteryPlan.getNickName() == null) {
			nickName.setText(lotteryPlan.getAccount());
		} else {
			
			
			nickName.setText(lotteryPlan.getNickName());
		}
		card.setText(lotteryPlan.getCard() + "");

		recordDetail.addContent(planNo);
		recordDetail.addContent(belongAccount);
		recordDetail.addContent(game);
		recordDetail.addContent(dealDateTime);
		recordDetail.addContent(totalAmount);
		recordDetail.addContent(otherAmount);
		recordDetail.addContent(schedule);
		recordDetail.addContent(status);
		recordDetail.addContent(nickName);
		recordDetail.addContent(card);
		recordDetails.addContent(recordDetail);
	}

}
