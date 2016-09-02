package com.yuncai.modules.lottery.sporttery.support.basketball;

import java.util.HashMap;
import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemHT implements OptionItem{
	/**胜负*/
	WIN("主胜", "2", "1", "主胜"), 
	
	LOSE("客胜", "1", "2", "主负"),
	/**让分胜负*/
	WIN_RF("主胜", "2", "1", "让主胜"),
	
	LOSE_RF("客胜", "1", "2", "让主负"),
	
	/**胜分差*/
	HW1TO5("胜1-5分", "2", "01","主胜1-5"),
	
	HW6TO10("胜6-10分", "4", "02","主胜6-10"),
	
	HW11TO15("胜11-15分", "6", "03","主胜11-15"),
	
	HW16TO20("胜16-20分", "8", "04","主胜16-20"),
	
	HW21TO25("胜21-25分","10", "05","主胜21-25"),
	
	HW26("胜26分以上", "12", "06","主胜26+"),
	
	GW1TO5("负1-5分", "1", "11","主负1-5"),
	
	GW6TO10("负6-10分", "3", "12","主负6-10"),
	
	GW11TO15("负11-15分", "5", "13","主负11-15"),
	
	GW16TO20("负16-20分", "7", "14","主负16-20"),
	
	GW21TO25("负21-25分", "9", "15","主负21-25"),
	
	GW26("负26分以上", "11", "16","主负26+"),
	
	/**大小分*/
	BIG("大", "2", "1", "大"),
	
	SMALL("小", "1", "2", "小");
	
	private final String text;
	private final String value;
	private final String bozhongValue;
	
	private final String commonText;
	
	

	public static final Map<String, OptionItemHT> SFMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> RFSFMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> SFCMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> DXFMAP = new HashMap<String, OptionItemHT>();
	
	static{
		SFMAP.put("2", WIN);
		SFMAP.put("1", LOSE);
		
		RFSFMAP.put("2", WIN_RF);
		RFSFMAP.put("1", LOSE_RF);
		
		SFCMAP.put("2", HW1TO5);
		SFCMAP.put("4", HW6TO10);
		SFCMAP.put("6", HW11TO15);
		SFCMAP.put("8", HW16TO20);
		SFCMAP.put("10", HW21TO25);
		SFCMAP.put("12", HW26);
		SFCMAP.put("1", GW1TO5);
		SFCMAP.put("3", GW6TO10);
		SFCMAP.put("5", GW11TO15);
		SFCMAP.put("7", GW16TO20);
		SFCMAP.put("9", GW21TO25);
		SFCMAP.put("11", GW26);
		
		DXFMAP.put("2", BIG);
		DXFMAP.put("1", SMALL);
	}
	
	public static OptionItemHT getItemByValue(String lotteryType,String value){
		OptionItemHT[] values=OptionItemHT.values();
		for(OptionItemHT item : values){
			if(lotteryType.equals(LotteryType.JC_LQ_SF.getValue()+"")){
				item=SFMAP.get(value);
			}else if(lotteryType.equals(LotteryType.JC_LQ_RFSF.getValue()+"")){
				item=RFSFMAP.get(value);
			}else if(lotteryType.equals(LotteryType.JC_LQ_SFC.getValue()+"")){
				item=SFCMAP.get(value);
			}else if(lotteryType.equals(LotteryType.JC_LQ_DXF.getValue()+"")){
				item=DXFMAP.get(value);
			}else{
				throw new RuntimeException("彩种类型错误");
			}
			return item;
		}
		throw new RuntimeException("选项获取错误");
	}
	
	
	private OptionItemHT(String text, String value, String bozhongValue,String commonText) {
		this.value = value;
		this.text = text;
		this.bozhongValue = bozhongValue;
		this.commonText = commonText;
	}


	public String getCommonText() {
		return commonText;
	}

	
	public String getText() {
		return text;
	}
	public String getValue() {
		return value;
	}
	public String getBozhongValue() {
		return bozhongValue;
	}


	@Override
	public int getOrdinal() {
		return this.ordinal();
	}
}
