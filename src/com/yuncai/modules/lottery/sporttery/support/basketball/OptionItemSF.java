package com.yuncai.modules.lottery.sporttery.support.basketball;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemSF implements OptionItem {

	WIN("主胜", "2", "1","主胜"), LOSE("客胜", "1", "2","主负");

	private final String text;
	private final String value;
	private final String bozhongValue;
	private final String commonText;

	
	private OptionItemSF(String text, String value, String bozhongValue,String commonText) {
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

	public static OptionItemSF getItemByValue(String value) {
		OptionItemSF[] values = OptionItemSF.values();
		for (OptionItemSF item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemSF getItemByBozhongValue(String value) {
		OptionItemSF[] values = OptionItemSF.values();
		for (OptionItemSF item : values) {
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
