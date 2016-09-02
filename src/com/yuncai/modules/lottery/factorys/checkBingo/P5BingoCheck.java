package com.yuncai.modules.lottery.factorys.checkBingo;
import java.util.HashMap;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
public class P5BingoCheck implements BingoCheck {

	public StringBuffer bingoContent = new StringBuffer(); // 定单出错，无票时 null 错误

	private HashMap<String, Long> bingoHashMap;

	// 税后
	private double bingoPosttaxTotal;
	// 税前
	private double bingoPretaxTotal;
	
	private String ticketCountPrize=null;

	private boolean isBingo;

	private double prize1 = 0;

	public void addCheck(BingoCheck otherCheck) {
		if (!otherCheck.isBingo()) {
			return;
		}
		HashMap<String, Long> otherBingoHashMap = otherCheck.getBingoHashMap();
		this.dealBingoCount(1, otherBingoHashMap.get("prize1").intValue());
		this.buildBingoContent();
		this.bingoPosttaxTotal += otherCheck.getBingoPosttaxTotal();
		this.bingoPretaxTotal += otherCheck.getBingoPretaxTotal();
	}

	static public void main(String[] args) {
		P5BingoCheck check = new P5BingoCheck();

		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("prize1", "1");

		openResultMap.put("result", "5,1,3,1,2");

		// String content,PlayType playType,int multiple, HashMap<String,
		// String> openResultMap
		check.execute("1:5,1,3,1,21", PlayType.ZX, 1, openResultMap);

		LogUtil.out(check.getBingoContent());
		LogUtil.out(check.getBingoPosttaxTotal());
	}

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String resultContent = openResultMap.get("result");

		LogUtil.out("开奖结果:" + resultContent + " 票内容:" + contentIn + " 玩法:" + playTypeIn.getName() + " 倍数:" + multiple);

		LogUtil.out("开奖号码:" + resultContent);
		prize1 = Double.valueOf(openResultMap.get("prize1"));

		bingoContent = new StringBuffer();
		bingoHashMap = new HashMap<String, Long>();
		bingoHashMap.put("prize1", new Long(0));

		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;
		String[] contentLine = contentIn.split("\\~");

		for (int i = 0; i < contentLine.length; i++) {
			String content = contentLine[i].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[i].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + resultContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);

			for (String ticketContent : content.split("\\^")) {
				if (playType.getValue() == PlayType.DS.getValue() || playType.getValue() == PlayType.FS.getValue()) {
					int bingoCount = BingoUtil.getBingoNumCountByAreas(ticketContent, "\\,", "*", resultContent, "\\,", 1);
					if (bingoCount == 5) {
						// 如果5个都命中，则累加上中奖数据
						this.dealBingoCount(1, 1 * multiple);
					}
				} else {
				}
			}
		}

		// 开始计算奖金
		double firstPrize = Double.valueOf(openResultMap.get("prize1"));

		double firstPrize_ = firstPrize > 10000 ? (firstPrize * 0.8) : firstPrize;

		// 计算税前
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * firstPrize;

		// 计算税后
		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * firstPrize_;

		this.buildBingoContent();
	}

	public void buildBingoContent() {
		bingoContent = new StringBuffer();
		// 组合bingoContent
		// 格式:intFirstCount^0|intSecCount^0|intThirdCount^0|intFourthCount^0|intFifthCount^0|intSixthCount^0|intSevernthCount^0#bingoPretaxTotal^0|bingoPosttaxTotal^0
		bingoContent = new StringBuffer();
		bingoContent.append("prize1^").append(bingoHashMap.get("prize1")).append("^").append(bingoHashMap.get("prize1") * prize1);
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
