package com.yuncai.modules.lottery.sporttery.support;

import java.util.ArrayList;
import java.util.List;

public class SportteryTicketModel<X extends MatchItem> {
	

	private List<X> matchItems;
	private Integer passMode;
	private int multiple;
	private SportteryPassType passType;

	// 计算注数
	public int countUnit() {
		int count = 0;
		final List<Integer> sheDanMatch = new ArrayList<Integer>();
		final List<Integer> unDanMatch = new ArrayList<Integer>();
		for (X x : matchItems) {
			if (x.isShedan())
				sheDanMatch.add(x.selectedCount());
			else
				unDanMatch.add(x.selectedCount());
		}
		final List<Integer> result = new ArrayList<Integer>();
		int passCount = passType.getPassMatchs()[0];
		if (passCount < sheDanMatch.size())
			throw new RuntimeException("设胆错误");
		ExtensionMathUtils.efficientCombExtension(passCount, sheDanMatch.size(), unDanMatch.size(), new ExtensionCombCallBack() {
			public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
				Integer tempCount = 1;
				for (int i = 0; i < comb1.length; i++) {
					if (comb1[i])
						tempCount *= sheDanMatch.get(i);
				}
				for (int i = 0; i < comb2.length; i++) {
					if (comb2[i])
						tempCount *= unDanMatch.get(i);
				}
				result.add(tempCount);
			}
		});
		for (Integer r : result) {
			count += r;
		}
		return count;
	}

	public List<X> getMatchItems() {
		return matchItems;
	}

	public void setMatchItems(List<X> matchItems) {
		this.matchItems = matchItems;
	}

	public Integer getPassMode() {
		return passMode;
	}

	public void setPassMode(Integer passMode) {
		this.passMode = passMode;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public SportteryPassType getPassType() {
		return passType;
	}

	public void setPassType(SportteryPassType passType) {
		this.passType = passType;
	}

}
