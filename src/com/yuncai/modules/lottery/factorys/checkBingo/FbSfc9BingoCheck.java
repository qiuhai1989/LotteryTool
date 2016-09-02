package com.yuncai.modules.lottery.factorys.checkBingo;
import java.util.HashMap;


import com.yuncai.core.tools.LogUtil;

import com.yuncai.core.tools.MathUtils;

import com.yuncai.modules.lottery.factorys.checkBingo.handle.TraditionsFootballResult;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class FbSfc9BingoCheck implements BingoCheck {

	public StringBuffer bingoContent = new StringBuffer();

	private HashMap<String, Long> bingoHashMap;

	// 税后
	private double bingoPosttaxTotal;
	// 税前
	private double bingoPretaxTotal;
	
	private String ticketCountPrize=null;

	private boolean isBingo;

	private double prize1 = 0;
	private double prize2 = 0;
	// 命中场次数
	private Long binggoCount = 0l;

	public void addCheck(BingoCheck otherCheck) {
		long tempBinggoCount = (Long) otherCheck.getBingoHashMap().get("binggoCount");
		LogUtil.out("bingoCount="+tempBinggoCount);
		this.binggoCount = this.binggoCount > tempBinggoCount ? this.binggoCount : tempBinggoCount;
		this.getBingoHashMap().put("binggoCount", this.binggoCount);
		if (!otherCheck.isBingo()) {
			return;
		}
		HashMap<String, Long> otherBingoHashMap = otherCheck.getBingoHashMap();
		this.dealBingoCount(1, otherBingoHashMap.get("prize1").intValue());
		this.dealBingoCount(2, otherBingoHashMap.get("prize2").intValue());
		this.buildBingoContent();
		this.bingoPosttaxTotal += otherCheck.getBingoPosttaxTotal();
		this.bingoPretaxTotal += otherCheck.getBingoPretaxTotal();
	}

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String resultContent = openResultMap.get("result");
		bingoContent = new StringBuffer();
		bingoHashMap = new HashMap<String, Long>();
		bingoHashMap.put("prize1", new Long(0));
		//bingoHashMap.put("prize2", new Long(0));
		prize1 = Double.valueOf(openResultMap.get("prize1"));
		//prize2 = Double.valueOf(openResultMap.get("prize4"));
		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;

		String[] results = resultContent.split("\\,");

		String[] contentLine = contentIn.split("\\~");

		for (int i = 0; i < contentLine.length; i++) {
			String content = contentLine[i].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[i].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + resultContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);

			if (playType.getValue() == PlayType.DS.getValue()) {
				String[] lines = content.split("\\^");
				for (int j = 0; j < lines.length; j++) {
					String line = lines[j];
					TraditionsFootballResult result = BingoUtil.getBingoResultForTraditionsFootball(line, ",", "", resultContent, "\\,", 1, 14,9);
					binggoCount = binggoCount < result.getBingoMatchCount() ? result.getBingoMatchCount() : binggoCount;
					int bingoCount = result.getTotalBingoCount();
					if (bingoCount > 0) {
						this.dealBingoCount(1, bingoCount * multiple);
					}
					/*
					TraditionsFootballResult r8Result = BingoUtil.getBingoResultForTraditionsFootball(line, ",", "", resultContent, "\\,", 1, 14,8);
					int bingoCount2 = r8Result.getTotalBingoCount();// 二等奖中奖数
					if (bingoCount2 > 0) {
						this.dealBingoCount(2, bingoCount2 * multiple);
					}
					*/
				}
			} else if (playType.getValue() == PlayType.FS.getValue()) {
				TraditionsFootballResult result = BingoUtil.getBingoResultForTraditionsFootball(content, ",", "", resultContent, "\\,", 1, 14,9);
				binggoCount = binggoCount < result.getBingoMatchCount() ? result.getBingoMatchCount() : binggoCount;
				int bingoCount = result.getTotalBingoCount();
				if (bingoCount > 0) {
					this.dealBingoCount(1, bingoCount * multiple);
				}
				/*
				TraditionsFootballResult r8Result = BingoUtil.getBingoResultForTraditionsFootball(content, ",", "", resultContent, "\\,", 1, 14,8);
				int bingoCount2 = r8Result.getTotalBingoCount();// 二等奖中奖数
				if (bingoCount2 > 0) {
					this.dealBingoCount(2, bingoCount2 * multiple);
				}*/
			}
		}

		// 一等奖税后奖金
		double prize1_ = prize1 > 10000 ? (prize1 * 0.8) : prize1;
		//double prize2_ = prize2 > 10000 ? (prize2 * 0.8) : prize2;

		// 税前总奖金计算
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * prize1;
		//this.bingoPretaxTotal += bingoHashMap.get("prize2") * prize2;

		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * prize1_;
		//this.bingoPosttaxTotal += bingoHashMap.get("prize2") * prize2_;
		// 正确场次
		bingoHashMap.put("binggoCount", binggoCount);
		this.buildBingoContent();
	}

	public void buildBingoContent() {
		bingoContent = new StringBuffer();
		bingoContent.append("prize1^").append(bingoHashMap.get("prize1")).append("^").append(bingoHashMap.get("prize1") * prize1).append("#");
		//bingoContent.append("prize2^").append(bingoHashMap.get("prize2")).append("^").append(bingoHashMap.get("prize2") * prize2);
	}

	public void dealBingoCount(int bingoLevel, int value) {
		long count = 0;
		this.isBingo = true;
		if (bingoLevel == 1) {
			count = bingoHashMap.get("prize1");
			count += value;
			bingoHashMap.put("prize1", count);
			return;
		}
		if (bingoLevel == 2) {
			count = bingoHashMap.get("prize2");
			count += value;
			bingoHashMap.put("prize2", count);
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

	public static void main(String[] ar) {
		FbSfc9BingoCheck check = new FbSfc9BingoCheck();
		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("prize1", "1");
		openResultMap.put("prize2", "2");
		openResultMap.put("prize3", "3");
		openResultMap.put("prize4", "4");
		openResultMap.put("result", "*,1,0,3,3,1,0,3,3,1,0,3,3,0");

		check.execute("1:31,1,-,-,-,-,-,1,3,1,0,3,3,0~1:3,1,0,-,-,-,-,-,1,3,0,3,3,0", PlayType.HT, 1, openResultMap);

		LogUtil.out(check.getBingoContent());
		LogUtil.out(check.getBingoHashMap());
		System.out.println(MathUtils.comp(7, 12));
	}

}
