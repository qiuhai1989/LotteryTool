package com.yuncai.modules.lottery.bean.vo;

import java.util.ArrayList;
import java.util.List;

public class EasyLottery {
	private String term;//彩期ID
	private List<EasyEbData>   ebData = new ArrayList<EasyEbData>();
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public List<EasyEbData> getEbData() {
		return ebData;
	}
	public void setEbData(List<EasyEbData> ebData) {
		this.ebData = ebData;
	}

	
	
}
