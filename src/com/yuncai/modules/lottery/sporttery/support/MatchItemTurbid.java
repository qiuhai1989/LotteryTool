package com.yuncai.modules.lottery.sporttery.support;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class MatchItemTurbid extends MatchItem{
	private static final long serialVersionUID = 1L;
	
	public Map<String, List<String>> getFbValueStrMap() {
		Map<String, List<String>> sportteryOptionMap = new HashMap<String, List<String>>();
		if (super.getOptions() != null) {
			
			for (SportteryOption op : super.getOptions()) {
				
				if (op.getType() != null && !"".equals(op.getType())) {
					
					if (sportteryOptionMap.containsKey(getFbTypeStr(op.getType()))) {
						sportteryOptionMap.get(getFbTypeStr(op.getType())).add(
								CommonUtils.getByPlayType(LotteryType.getItem(Integer.valueOf(op.getType())), op).getText());
					} else {
						List<String> list = new ArrayList<String>();
						list.add(CommonUtils.getByPlayType(LotteryType.getItem(Integer.valueOf(op.getType())), op).getText());
						sportteryOptionMap.put(getFbTypeStr(op.getType()), list);
					}
				}

			}
		}
		return sportteryOptionMap;
	}
	
	public String getFbTypeStr(String type) {
		
		String fbStr = "";
		if (type != null) {
			if (type.equals("7201")) {
				fbStr = "让球胜平负";
			} else if (type.equals("7207")) {
				fbStr = "胜平负";
			} else if (type.equals("7203")) {
				fbStr = "进球数";
			} else if (type.equals("7202")) {
				fbStr = "比分";
			} else if (type.equals("7204")) {
				fbStr = "半全场";
			}else if(type.equals("7301")){
				fbStr = "胜负" ;
			}else if(type.equals("7302")){
				fbStr = "让分胜负";
			}else if(type.equals("7303")){
				fbStr = "胜分差";
			}else if(type.equals("7304")){
				fbStr = " 大小分";
			}else if(type.equals("8001")){
				fbStr = "世界杯冠军";
			}
		}
		
		return fbStr;
	}
	
}