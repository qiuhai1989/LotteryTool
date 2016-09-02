package com.yuncai.modules.lottery.sporttery.model;

import java.util.List;

import com.yuncai.modules.lottery.betfilter.impl.Conditions;
import com.yuncai.modules.lottery.sporttery.support.MatchItemTurbid;

public class SportteryBetFilterContentModelTurbid <X extends MatchItemTurbid> extends SportteryBetContentModelTurbid<X> {
	private static final long serialVersionUID = 1L;
	private List<String> contentList;
	private Conditions conditions;

	public List<String> getContentList() {
		return contentList;
	}

	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

}
