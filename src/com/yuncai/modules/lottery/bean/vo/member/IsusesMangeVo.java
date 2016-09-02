package com.yuncai.modules.lottery.bean.vo.member;

import com.yuncai.modules.lottery.model.Oracle.IsusesMange;
import com.yuncai.modules.lottery.model.Oracle.Platform;

public class IsusesMangeVo {
	/**
	 * TODO
	 * @author gx
	 * Dec 13, 2013 4:38:12 PM
	 */
	
	private IsusesMange isusesMange;
	private Platform platform;
	
	public IsusesMangeVo(IsusesMange isusesMange,Platform platform) {
		super();
		this.isusesMange = isusesMange;
		this.platform = platform;
	}

	public IsusesMange getIsusesMange() {
		return isusesMange;
	}

	public void setIsusesMange(IsusesMange isusesMange) {
		this.isusesMange = isusesMange;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	
}
