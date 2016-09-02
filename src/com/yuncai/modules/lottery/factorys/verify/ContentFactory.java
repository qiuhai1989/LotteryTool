package com.yuncai.modules.lottery.factorys.verify;
import java.util.HashMap;
public class ContentFactory {
	private HashMap<Integer, ParserBuilder> parserFactoryMap;

	/**
	 * 功能：解析Factory类型
	 * 
	 * @throws Exception
	 */
	public ContentCheck initFactory(int lotteryType, double amount, String content) throws Exception {
		return parserFactoryMap.get(lotteryType).checkPlan(content, amount);
	}

	public HashMap<Integer, ParserBuilder> getParserFactoryMap() {
		return parserFactoryMap;
	}

	public void setParserFactoryMap(HashMap<Integer, ParserBuilder> parserFactoryMap) {
		this.parserFactoryMap = parserFactoryMap;
	}

}
