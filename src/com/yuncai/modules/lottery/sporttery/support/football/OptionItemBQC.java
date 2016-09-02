package com.yuncai.modules.lottery.sporttery.support.football;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemBQC implements OptionItem{
	/** 胜胜 */
	WIN_WIN("1", "胜胜", "33","胜胜"),

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
	LOSE_LOSE("9", "负负", "00", "负负");

	private final String text;

	private final String value;

	private final String bozhongValue;
	
	private final String commonText;


	private OptionItemBQC(String value, String text, String bozhongValue,String commonText) {
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

	public static OptionItemBQC getItemByValue(String value) {
		OptionItemBQC[] values = OptionItemBQC.values();
		for (OptionItemBQC item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemBQC getItemByBozhongValue(String value) {
		OptionItemBQC[] values = OptionItemBQC.values();
		for (OptionItemBQC item : values) {
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
