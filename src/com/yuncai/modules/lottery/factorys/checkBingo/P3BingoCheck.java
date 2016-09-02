package com.yuncai.modules.lottery.factorys.checkBingo;

import java.util.HashMap;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class P3BingoCheck implements BingoCheck {
	public StringBuffer bingoContent = new StringBuffer(); // 定单出错，无票时 null 错误

	private HashMap<String, Long> bingoHashMap;

	// 税后
	private double bingoPosttaxTotal;
	// 税前
	private double bingoPretaxTotal;
	
	private String ticketCountPrize=null;

	private boolean isBingo;

	private double prize1 = 0;
	private double prize2 = 0;
	private double prize3 = 0;

	public void addCheck(BingoCheck otherCheck) {
		if (!otherCheck.isBingo()) {
			return;
		}
		HashMap<String, Long> otherBingoHashMap = otherCheck.getBingoHashMap();
		this.dealBingoCount(1, otherBingoHashMap.get("prize1").intValue());
		this.dealBingoCount(2, otherBingoHashMap.get("prize2").intValue());
		this.dealBingoCount(3, otherBingoHashMap.get("prize3").intValue());
		this.buildBingoContent();
		this.bingoPosttaxTotal += otherCheck.getBingoPosttaxTotal();
		this.bingoPretaxTotal += otherCheck.getBingoPretaxTotal();
	}

	/*
	 * public static final PlayType ZX = new PlayType("直选", 28); public static
	 * final PlayType ZS = new PlayType("组三", 29); public static final PlayType
	 * ZL = new PlayType("组六", 30); public static final PlayType ZXHZ = new
	 * PlayType("直选和值", 31); public static final PlayType ZSHZ = new
	 * PlayType("组三和值", 32); public static final PlayType ZLHZ = new
	 * PlayType("组六和值", 33); public static final PlayType ZLDT = new
	 * PlayType("组六胆拖", 38);
	 */
	static public void main(String[] args) {
		P3BingoCheck check = new P3BingoCheck();

		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("prize1", "1");
		openResultMap.put("prize2", "2");
		openResultMap.put("prize3", "3");

		openResultMap.put("result", "5,1,3");

		// String content,PlayType playType,int multiple, HashMap<String,
		// String> openResultMap
		check.execute("33:9", PlayType.ZLHZ, 1, openResultMap);

		LogUtil.out(check.getBingoContent());
		LogUtil.out(check.getBingoPosttaxTotal());
	}

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String tempContent = openResultMap.get("result");

		LogUtil.out("开奖结果:" + tempContent + " 票内容:" + contentIn + " 玩法:" + playTypeIn.getName() + " 倍数:" + multiple);
		String[] ss = tempContent.split("\\,");

		boolean isBaoZhi = false;
		boolean isZhuSan = false;
		if (ss[0].equals(ss[1]) && ss[0].equals(ss[2])) {
			isBaoZhi = true;
		} else if (ss[0].equals(ss[1]) || ss[0].equals(ss[2]) || ss[1].equals(ss[2])) {
			isZhuSan = true;
		}
		// String resultContent = tempContent.substring(0,
		// tempContent.length()-3);
		String resultContent = tempContent;
		int resultTotal = BingoUtil.getTotalByContent(resultContent, "\\,");

		LogUtil.out("开奖号码:" + tempContent);
		prize1 = Double.valueOf(openResultMap.get("prize1"));
		prize2 = Double.valueOf(openResultMap.get("prize2"));
		prize3 = Double.valueOf(openResultMap.get("prize3"));

		bingoContent = new StringBuffer();
		bingoHashMap = new HashMap<String, Long>();
		bingoHashMap.put("prize1", new Long(0));
		bingoHashMap.put("prize2", new Long(0));
		bingoHashMap.put("prize3", new Long(0));

		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;
		String[] contentLine = contentIn.split("\\~");

		for (int i = 0; i < contentLine.length; i++) {
			String content = contentLine[i].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[i].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + tempContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);

			for (String ticketContent : content.split("\\^")) {
				if (playType.getValue() == PlayType.ZX.getValue() || playType.getValue() == PlayType.ZXFS.getValue()) {
					int bingoCount = BingoUtil.getBingoNumCountByAreas(ticketContent, "\\,", "*", resultContent, "\\,", 1);
					if (bingoCount == 3) {
						// 如果三个都命中，则累加上中奖数据
						this.dealBingoCount(1, 1 * multiple);
					}
				} else if (playType.getValue() == PlayType.ZXHZ.getValue()) {
					String[] ticketLine = ticketContent.split("\\|");
					for (int j = 0; j < ticketLine.length; j++) {
						if (resultTotal == Integer.parseInt(ticketLine[j])) {
							dealBingoCount(1, 1 * multiple);
						}
					}
				} else if (playType.getValue() == PlayType.ZS.getValue() && isZhuSan) {
					if (ticketContent.indexOf(ss[0]) != -1 && ticketContent.indexOf(ss[1]) != -1 && ticketContent.indexOf(ss[2]) != -1) {
						// 如果三个都命中，则累加上中奖数据
						this.dealBingoCount(2, 1 * multiple);
					}
				} else if (playType.getValue() == PlayType.ZSHZ.getValue() && isZhuSan) {
					String[] ticketLine = ticketContent.split("\\|");
					for (int j = 0; j < ticketLine.length; j++) {
						if (resultTotal == Integer.parseInt(ticketLine[j])) {
							dealBingoCount(2, 1 * multiple);
						}
					}
				} else if (playType.getValue() == PlayType.ZHUXUAN.getValue()) {
					int bingoCount = BingoUtil.getBingoNumCountNotByAreas(ticketContent, "\\,", resultContent, "\\,");
					// LogUtil.out(bingoCount);
					if (BingoUtil.getTotalByContent(ticketContent, "\\,") == resultTotal) {
						if (isZhuSan && bingoCount >= 3) {
							this.dealBingoCount(2, 1 * multiple);
						} else if (!isZhuSan && !isBaoZhi && bingoCount >= 3) {
							this.dealBingoCount(3, 1 * multiple);
						}
					}
				} else if (playType.getValue() == PlayType.ZL.getValue() && !isZhuSan && !isBaoZhi) {
					// int bingoCount =
					// BingoUtil.getBingoNumCountNotByAreas(ticketContent,"\\,",
					// resultContent, "\\,");
					if (ticketContent.indexOf(ss[0]) != -1 && ticketContent.indexOf(ss[1]) != -1 && ticketContent.indexOf(ss[2]) != -1) {
						// 如果三个都命中，则累加上中奖数据
						this.dealBingoCount(3, 1 * multiple);
					}
				} else if (playType.getValue() == PlayType.ZLHZ.getValue() && !isZhuSan && !isBaoZhi) {
					String[] ticketLine = ticketContent.split("\\|");
					for (int j = 0; j < ticketLine.length; j++) {
						if (resultTotal == Integer.parseInt(ticketLine[j])) {
							dealBingoCount(3, 1 * multiple);
						}
					}
				} else if (playType.getValue() == PlayType.ZLDT.getValue() && !isZhuSan && !isBaoZhi) {
					String danString = ticketContent.split("\\|")[0];
					String tuoString = ticketContent.split("\\|")[1];
					int bingoCount = BingoUtil.getBingoNumCountNotByAreas(danString, "\\,", resultContent, "\\,");
					int danCount = danString.split("\\,").length;
					if (bingoCount == danCount) {
						int tuoBingoCount = BingoUtil.getBingoNumCountNotByAreas(tuoString, "\\,", resultContent, "\\,");
						if (bingoCount + tuoBingoCount == 3) {
							dealBingoCount(3, 1 * multiple);
						}
					}
				} else {
				}
			}
		}

		// 开始计算奖金
		double firstPrize = Double.valueOf(openResultMap.get("prize1"));
		double secPrize = Double.valueOf(openResultMap.get("prize2"));
		double thirdPrize = Double.valueOf(openResultMap.get("prize3"));

		double firstPrize_ = firstPrize > 10000 ? (firstPrize * 0.8) : firstPrize;
		double secPrize_ = secPrize > 10000 ? (secPrize * 0.8) : secPrize;

		// 计算税前
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * firstPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize2") * secPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize3") * thirdPrize;

		// 计算税后
		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * firstPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize2") * secPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize3") * thirdPrize;

		this.buildBingoContent();
	}

	public void buildBingoContent() {
		bingoContent = new StringBuffer();
		// 组合bingoContent
		// 格式:intFirstCount^0|intSecCount^0|intThirdCount^0|intFourthCount^0|intFifthCount^0|intSixthCount^0|intSevernthCount^0#bingoPretaxTotal^0|bingoPosttaxTotal^0
		bingoContent = new StringBuffer();
		bingoContent.append("prize1^").append(bingoHashMap.get("prize1")).append("^").append(bingoHashMap.get("prize1") * prize1).append("#");
		bingoContent.append("prize2^").append(bingoHashMap.get("prize2")).append("^").append(bingoHashMap.get("prize2") * prize2).append("#");
		bingoContent.append("prize3^").append(bingoHashMap.get("prize3")).append("^").append(bingoHashMap.get("prize3") * prize3);
	}

	public void dealBingoCount(int bingoCount, int value) {
		this.isBingo = true;
		long count = 0;
		LogUtil.out("----------------" + bingoCount);
		if (bingoCount == 1) {// 一等奖
			count = bingoHashMap.get("prize1");
			count += value;
			bingoHashMap.put("prize1", count);
			return;
		}
		if (bingoCount == 2) {// 二等奖
			count = bingoHashMap.get("prize2");
			count += value;
			bingoHashMap.put("prize2", count);
			return;
		}
		if (bingoCount == 3) {// 三等奖
			count = bingoHashMap.get("prize3");
			count += value;
			bingoHashMap.put("prize3", count);
			return;
		}
	}

	public String getBingoContent() {
		return this.bingoContent.toString();
	}

	public HashMap getBingoHashMap() {
		return this.bingoHashMap;
	}

	public double getBingoPosttaxTotal() {
		return this.bingoPosttaxTotal;
	}

	public double getBingoPretaxTotal() {
		return this.bingoPretaxTotal;
	}

	public boolean isBingo() {
		return this.isBingo;
	}
	
	public String getTicketCountPrize() {
		return this.ticketCountPrize;
	}

	// 复制对象
	public BingoCheck clone() throws CloneNotSupportedException {
		return (BingoCheck) super.clone();
	}

}
