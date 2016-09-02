package com.yuncai.modules.lottery.sporttery.support.football;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemJQS implements OptionItem{
	/** 0进球 */
	S0("1", "0", "0","总进球0"),

	/** 1进球 */
	S1("2", "1", "1","总进球1"),

	/** 2进球 */
	S2("3", "2", "2","总进球2"),

	/** 3进球 */
	S3("4", "3", "3","总进球3"),

	/** 4进球 */
	S4("5", "4", "4","总进球4"),

	/** 5进球 */
	S5("6", "5", "5","总进球5"),

	/** 6进球 */
	S6("7", "6", "6","总进球6"),

	/** 7个以上进球 */
	S7("8", "7+", "7","总进球7+");

	private final String text;

	private final String value;

	private final String bozhongValue;
	
	private final String commonText;

	private OptionItemJQS(String value, String text, String bozhongValue,String commonText) {
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

	public static OptionItemJQS getItemByValue(String value) {
		OptionItemJQS[] values = OptionItemJQS.values();
		for (OptionItemJQS item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemJQS getItemByBozhongValue(String value) {
		OptionItemJQS[] values = OptionItemJQS.values();
		for (OptionItemJQS item : values) {
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
