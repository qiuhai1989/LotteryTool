package com.yuncai.modules.lottery.factorys.verify;

public interface ParserBuilder {

	/**
	 * 功能：判断方案内容是否存在问题
	 * 
	 * @return
	 */
	public abstract ContentCheck checkPlan(String content, double amount) throws Exception;

}
