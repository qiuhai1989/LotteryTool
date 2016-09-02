package com.yuncai.modules.lottery.sporttery.support.football;

import java.util.HashMap;
import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemHT implements OptionItem{
	/** 让球胜平负 */
	// 顺序对应MatchAwardOfHT 的getAward顺序
	WIN_RQ("1", "胜", "3", "让球胜"),

	LOSE_RQ("3", "负", "1", "让球负"),

	DRAW_RQ("2", "平", "0", "让球平"),
	/** 胜平负 */
	WIN("1", "胜", "3", "胜"),

	LOSE("3", "负", "1", "负"),

	DRAW("2", "平", "0", "平"),
	/** 0进球 */
	S0("1", "0", "0", "总进球0"),

	/** 1进球 */
	S1("2", "1", "1", "总进球1"),

	/** 2进球 */
	S2("3", "2", "2", "总进球2"),

	/** 3进球 */
	S3("4", "3", "3", "总进球3"),

	/** 4进球 */
	S4("5", "4", "4", "总进球4"),

	/** 5进球 */
	S5("6", "5", "5", "总进球5"),

	/** 6进球 */
	S6("7", "6", "6", "总进球6"),

	/** 7个以上进球 */
	S7("8", "7+", "7", "总进球7+"),
	
	//半全场
	/** 胜胜 */
	WIN_WIN("1", "胜胜", "33", "胜胜"),

	/** 胜平 */
	WIN_DRAW("2", "胜平", "31", "胜平"),

	/** 胜负 */
	WIN_LOSE("3", "胜负", "30", "胜负"),

	/** 平胜 */
	DRAW_WIN("4", "平胜", "13", "平胜"),

	/** 平平 */
	DRAW_DRAW("5", "平平", "11", "平平"),

	/** 平负 */
	DRAW_LOSE("6", "平负", "10", "平负"),

	/** 负胜 */
	LOSE_WIN("7", "负胜", "03", "负胜"),

	/** 负平 */
	LOSE_DRAW("8", "负平", "01", "负平"),

	/** 负负 */
	LOSE_LOSE("9", "负负", "00", "负负"),

	//比分
	/** 1:0 */
	WIN10("1", "1:0", "10", "1:0"),

	/** 2:0 */
	WIN20("2", "2:0", "20", "2:0"),

	/** 2:1 */
	WIN21("3", "2:1", "21", "2:1"),

	/** 3:0 */
	WIN30("4", "3:0", "30", "3:0"),

	/** 3:1 */
	WIN31("5", "3:1", "31", "3:1"),

	/** 3:2 */
	WIN32("6", "3:2", "32", "3:2"),

	/** 4:0 */
	WIN40("7", "4:0", "40", "4:0"),

	/** 4:1 */
	WIN41("8", "4:1", "41", "4:1"),

	/** 4:2 */
	WIN42("9", "4:2", "42", "4:2"),

	/** 5:0 */
	WIN50("10", "5:0", "50", "5:0"),

	/** 5:1 */
	WIN51("11", "5:1", "51", "5:1"),

	/** 5:2 */
	WIN52("12", "5:2", "52", "5:2"),

	/** 胜其它 */
	WIN_OTHER("13", "胜其他", "90", "胜其他"),

	/** 0:0 */
	DRAW00("14", "0:0", "00", "0:0"),

	/** 1:1 */
	DRAW11("15", "1:1", "11", "1:1"),

	/** 2:2 */
	DRAW22("16", "2:2", "22", "2:2"),

	/** 3:3 */
	DRAW33("17", "3:3", "33", "3:3"),

	/** 0:0 */
	DRAW_OTHER("18", "平其他", "99", "平其他"),

	/** 0:1 */
	LOSE01("19", "0:1", "01", "0:1"),

	/** 0:2 */
	LOSE02("20", "0:2", "02", "0:2"),

	/** 1:2 */
	LOSE12("21", "1:2", "12", "1:2"),

	/** 0:3 */
	LOSE03("22", "0:3", "03", "0:3"),

	/** 1:3 */
	LOSE13("23", "1:3", "13", "1:3"),

	/** 2:3 */
	LOSE23("24", "2:3", "23", "2:3"),

	/** 0:4 */
	LOSE04("25", "0:4", "04", "0:4"),

	/** 1:4 */
	LOSE14("26", "1:4", "14", "1:4"),

	/** 2:4 */
	LOSE24("27", "2:4", "24", "2:4"),

	/** 0:5 */
	LOSE05("28", "0:5", "05", "0:5"),

	/** 1:5 */
	LOSE15("29", "1:5", "15", "1:5"),

	/** 2:5 */
	LOSE25("30", "2:5", "25", "2:5"),

	/** 负其它 */
	LOSE_OTHER("31", "负其他", "09", "负其他");

	private final String text;

	private final String value;

	private final String bozhongValue;
	
	private final String commonText;
	

	public static final Map<String, OptionItemHT> SPFMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> RQSPFMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> BFMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> JQSMAP = new HashMap<String, OptionItemHT>();
	
	public static final Map<String, OptionItemHT> BQCMAP = new HashMap<String, OptionItemHT>();
	static{
		SPFMAP.put("1", WIN);
		SPFMAP.put("2", DRAW);
		SPFMAP.put("3", LOSE);
		RQSPFMAP.put("1", WIN_RQ);
		RQSPFMAP.put("2", DRAW_RQ);
		RQSPFMAP.put("3", LOSE_RQ);
		JQSMAP.put("1",S0);
		JQSMAP.put("2",S1);
		JQSMAP.put("3",S2);
		JQSMAP.put("4",S3);
		JQSMAP.put("5",S4);
		JQSMAP.put("6",S5);
		JQSMAP.put("7",S6);
		JQSMAP.put("8",S7);
		BQCMAP.put("1", WIN_WIN);
		BQCMAP.put("2", WIN_DRAW);
		BQCMAP.put("3", WIN_LOSE);
		BQCMAP.put("4", DRAW_WIN);
		BQCMAP.put("5", DRAW_DRAW);
		BQCMAP.put("6", DRAW_LOSE);
		BQCMAP.put("7", LOSE_WIN);
		BQCMAP.put("8", LOSE_DRAW);
		BQCMAP.put("9", LOSE_LOSE);
		BFMAP.put("1",WIN10);
		BFMAP.put("2",WIN20);
		BFMAP.put("3",WIN21);
		BFMAP.put("4",WIN30);
		BFMAP.put("5",WIN31);
		BFMAP.put("6",WIN32);
		BFMAP.put("7",WIN40);
		BFMAP.put("8",WIN41);
		BFMAP.put("9",WIN42);
		BFMAP.put("10",WIN50);
		BFMAP.put("11",WIN51);
		BFMAP.put("12",WIN52);
		BFMAP.put("13",WIN_OTHER);
		BFMAP.put("14",DRAW00);
		BFMAP.put("15",DRAW11);
		BFMAP.put("16",DRAW22);
		BFMAP.put("17",DRAW33);
		BFMAP.put("18",DRAW_OTHER);
		BFMAP.put("19",LOSE01);
		BFMAP.put("20",LOSE02);
		BFMAP.put("21",LOSE12);
		BFMAP.put("22",LOSE03);
		BFMAP.put("23",LOSE13);
		BFMAP.put("24",LOSE23);
		BFMAP.put("25",LOSE04);
		BFMAP.put("26",LOSE14);
		BFMAP.put("27",LOSE24);
		BFMAP.put("28",LOSE05);
		BFMAP.put("29",LOSE15);
		BFMAP.put("30",LOSE25);
		BFMAP.put("31" ,LOSE_OTHER);
	}
	private OptionItemHT(String value, String text, String bozhongValue,String commonText) {
		this.value = value;
		this.text = text;
		this.bozhongValue = bozhongValue;
		this.commonText=commonText;
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


	public static OptionItemHT getItemByValue(String lotteryType, String value) {
		OptionItemHT[] values = OptionItemHT.values();
		for (OptionItemHT item : values) {
			if (item.value.equals(value)) {
				// 验证是让球胜平负,还是胜平负
				if (lotteryType.equals(LotteryType.JC_ZQ_SPF.getValue()+"")) {
					item=SPFMAP.get(value);
				} else if (lotteryType.equals(LotteryType.JC_ZQ_RQSPF.getValue()+"")) {
					item=RQSPFMAP.get(value);
				}else if (lotteryType.equals(LotteryType.JC_ZQ_BF.getValue()+"")) {
					item=BFMAP.get(value);
				}else if (lotteryType.equals(LotteryType.JC_ZQ_JQS.getValue()+"")) {
					item=JQSMAP.get(value);
				}else if (lotteryType.equals(LotteryType.JC_ZQ_BQC.getValue()+"")) {
					item=BQCMAP.get(value);
				}else{
					throw new RuntimeException("彩种类型错误");
				}
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public int getOrdinal() {
		return this.ordinal();
	}

	/**
	 * @return the bozhongValue
	 */
	public String getBozhongValue() {
		return bozhongValue;
	}
}
