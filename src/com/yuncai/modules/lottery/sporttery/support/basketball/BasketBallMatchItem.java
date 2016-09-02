package com.yuncai.modules.lottery.sporttery.support.basketball;

import java.util.List;

import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItemTurbid;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;

public class BasketBallMatchItem extends MatchItemTurbid {

	private static final long serialVersionUID = 1L;
	private Double RF;
	private Double DXF;
	
	public Double getRF() {
		return RF;
	}

	public void setRF(Double rF) {
		RF = rF;
	}

	public Double getDXF() {
		return DXF;
	}

	public void setDXF(Double dXF) {
		DXF = dXF;
	}

	public String show2String(boolean isPass, LotteryType lt) {
		String result = "";
		List<SportteryOption> optionList = this.getOptions();
		String weekStr = DateTools.getWeekStr(DateTools.StringToDate(this.getIntTime() + "", "yyyyMMdd"));
		result += weekStr + this.getLineId() + "(";
		for (int j = 0; j < optionList.size(); j++) {
			SportteryOption option = optionList.get(j);
			OptionItem optionItem = CommonUtils.getByPlayType(lt, option);

			if (!isPass) {// 单关
				result += optionItem.getCommonText() + ",";
			} else {// 过关
				result += optionItem.getCommonText() + option.getAward() + ",";
			}
		}
		result = result.substring(0, result.length() - 1);
		result += ")";
		return result;
	}

	public Double initMaxAward() {
		List<SportteryOption> options = getOptions();
		Double award = 0d;
		for (int i = 0; i < options.size(); i++) {
			Double tempAward = options.get(i).getAward();
			award = award > tempAward ? award : tempAward;
		}
		return award;
	}

	public Double initMinAward() {
		List<SportteryOption> options = getOptions();
		Double award = options.get(0).getAward();
		for (int i = 0; i < options.size(); i++) {
			Double tempAward = options.get(i).getAward();
			award = award < tempAward ? award : tempAward;
		}
		return award;
	}
}
