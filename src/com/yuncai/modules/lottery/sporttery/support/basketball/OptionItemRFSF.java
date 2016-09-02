package com.yuncai.modules.lottery.sporttery.support.basketball;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemRFSF implements OptionItem {

	WIN("主胜", "2", "1","让主胜"), LOSE("客胜", "1", "2","让主负");

	private final String text;
	private final String value;
	private final String bozhongValue;
	
	private final String commonText;

	

	private OptionItemRFSF(String text, String value, String bozhongValue,String commonText) {
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

	public static OptionItemRFSF getItemByValue(String value) {
		OptionItemRFSF[] values = OptionItemRFSF.values();
		for (OptionItemRFSF item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemRFSF getItemByBozhongValue(String value) {
		OptionItemRFSF[] values = OptionItemRFSF.values();
		for (OptionItemRFSF item : values) {
			if (item.bozhongValue.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static String[] getAllValue() {
		OptionItemRFSF[] optionItems = OptionItemRFSF.values();
		String[] valueArr = new String[optionItems.length];
		for (int i = 0, length = optionItems.length; i < length; i++) {
			valueArr[i] = optionItems[i].getValue();
		}
		return valueArr;
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
