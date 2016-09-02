package com.yuncai.modules.lottery.sporttery.support.football;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemBF  implements OptionItem{
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

	public static final OptionItemBF[] WINS = { WIN10, WIN20, WIN21, WIN30, WIN31, WIN32, WIN40, WIN41, WIN42, WIN50, WIN51, WIN52, WIN_OTHER };
	public static final OptionItemBF[] DRAWS = { DRAW00, DRAW11, DRAW22, DRAW33, DRAW_OTHER };
	public static final OptionItemBF[] LOSES = { LOSE01, LOSE02, LOSE12, LOSE03, LOSE13, LOSE23, LOSE04, LOSE14, LOSE24, LOSE05, LOSE15, LOSE25,
			LOSE_OTHER };

	private OptionItemBF(String value, String text, String bozhongValue,String commonText) {
		this.value = value;
		this.text = text;
		this.bozhongValue = bozhongValue;
		this.commonText=commonText;
	}

	public String getText() {
		return text;
	}

	public String getValue() {
		return value;
	}
	public String getCommonText() {
		return commonText;
	}

	public static OptionItemBF getItemByValue(String value) {
		OptionItemBF[] values = OptionItemBF.values();
		for (OptionItemBF item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		value = value.trim();
		Integer f = Integer.parseInt(value.substring(0, 1));
		Integer l = Integer.parseInt(value.substring(1));
		if (f > l) {
			return OptionItemBF.WIN_OTHER;
		} else if (f == l) {
			return OptionItemBF.DRAW_OTHER;
		} else {
			return OptionItemBF.LOSE_OTHER;
		}
	}
	
	public static OptionItemBF getItemBybozhongValue(String value) {
		OptionItemBF[] values = OptionItemBF.values();
		for (OptionItemBF item : values) {
			if (item.bozhongValue.equals(value)) {
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
