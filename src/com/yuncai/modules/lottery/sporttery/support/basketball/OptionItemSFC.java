package com.yuncai.modules.lottery.sporttery.support.basketball;

import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum OptionItemSFC implements OptionItem {

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
	
	GW26("负26分以上", "11", "16","主负26+");

	private final String text;
	private final String value;
	private final String bozhongValue;
	
	private final String commonText;

	public String getCommonText() {
		return commonText;
	}

	private OptionItemSFC(String text, String value, String bozhongValue,String commonText) {
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

	public static OptionItemSFC getItemByValue(String value) {
		OptionItemSFC[] values = OptionItemSFC.values();
		for (OptionItemSFC item : values) {
			if (item.value.equals(value)) {
				return item;
			}
		}
		throw new RuntimeException("选项获取错误");
	}

	public static OptionItemSFC getItemByBozhongValue(String value) {
		OptionItemSFC[] values = OptionItemSFC.values();
		for (OptionItemSFC item : values) {
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
