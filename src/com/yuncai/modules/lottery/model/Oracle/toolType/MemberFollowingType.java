package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class MemberFollowingType extends IntegerBeanLabelItem{
	private static final long serialVersionUID = -1410849485815957495L;

	protected MemberFollowingType(String name, int value) {
		super(MemberFollowingType.class.getName(), name, value);
	}

	public static MemberFollowingType getItem(int value) {
		return (MemberFollowingType) MemberFollowingType.getResult(MemberFollowingType.class.getName(), value);
	}
	public static final MemberFollowingType ALL =new MemberFollowingType("全部",-1);
	
	public static final MemberFollowingType NONE = new MemberFollowingType("非自动跟单", 0);
	
	public static final MemberFollowingType TAOCAN = new MemberFollowingType("网页", 1);

	public static final MemberFollowingType HEMAI = new MemberFollowingType("合买", 2);
	
	public static final MemberFollowingType PHONE = new MemberFollowingType("Android手机购彩",3);
	
	public static final MemberFollowingType IPHONE_PHONE= new MemberFollowingType("iphone手机购彩",4);

}
