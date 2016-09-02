package com.yuncai.modules.lottery.software.service;
import org.jdom.Element;

public interface SoftwareProcess {
	
	/**
	 * 处理通知
	 * 
	 * @param content
	 *            获取到的内容
	 * @return 回应内容
	 * 
	 */
	public String execute(Element bodyEle,String agentId);

}
