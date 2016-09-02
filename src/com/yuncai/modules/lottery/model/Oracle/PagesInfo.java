package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PageType;

public class PagesInfo extends AbstractPagesInfo implements java.io.Serializable {

	public PagesInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagesInfo(Integer id, String pageName, String pageId, LotteryType lotteryType, Integer isRoot, PageType pageType, Date createDateTime,
			String platform, String address) {
		super(id, pageName, pageId, lotteryType, isRoot, pageType, createDateTime, platform, address);
		// TODO Auto-generated constructor stub
	}

}
