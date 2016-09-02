package com.yuncai.modules.lottery.sporttery.support.basketball;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemDXF implements OptionItem {
	BIG("大", "2", "1","大"), SMALL("小", "1", "2","小");

	private final String text;
	private final String value;
	private final String bozhongValue;
	private final String commonText;

	public String getCommonText() {
		return commonText;
	}

	private OptionItemDXF(String text, String value, String bozhongValue,String commonText) {
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

	public static OptionItemDXF getItemByValue(String value) {
		OptionItemDXF[] values = OptionItemDXF.values();
		for (OptionItemDXF item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemDXF getItemByBozhongValue(String value) {
		OptionItemDXF[] values = OptionItemDXF.values();
		for (OptionItemDXF item : values) {
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
