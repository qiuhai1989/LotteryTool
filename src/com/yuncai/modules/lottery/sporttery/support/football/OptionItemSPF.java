package com.yuncai.modules.lottery.sporttery.support.football;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemSPF implements OptionItem{
	
	WIN("胜", "1", "3","胜"), DRAW("平", "2", "1","平"), LOSE("负", "3", "0","负");

	private final String text;
	private final String value;
	private final String bozhongValue;
	
	private final String commonText;

	public String getCommonText() {
		return commonText;
	}

	private OptionItemSPF(String text, String value, String bozhongValue,String commonText) {
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

	public static String[] getAllValue() {
		OptionItemSPF[] optionItems = OptionItemSPF.values();
		String[] valueArr = new String[optionItems.length];
		for (int i = 0, length = optionItems.length; i < length; i++) {
			valueArr[i] = optionItems[i].getValue();
		}
		return valueArr;
	}

	public static OptionItemSPF getItemByValue(String value) {
		OptionItemSPF[] values = OptionItemSPF.values();
		for (OptionItemSPF item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemSPF getItemByBozhongValue(String value) {
		OptionItemSPF[] values = OptionItemSPF.values();
		for (OptionItemSPF item : values) {
			if (item.bozhongValue.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}
	
	public static OptionItemSPF getItemByText(String value) {
		OptionItemSPF[] values = OptionItemSPF.values();
		for (OptionItemSPF item : values) {
			if (item.text.equals(value)) {
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
