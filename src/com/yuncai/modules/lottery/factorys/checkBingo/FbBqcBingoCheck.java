package com.yuncai.modules.lottery.factorys.checkBingo;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.factorys.checkBingo.handle.SFCBingoResult;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class FbBqcBingoCheck implements BingoCheck {

	private static final Log log = LogFactory.getLog(FbBqcBingoCheck.class);

	public StringBuffer bingoContent = new StringBuffer();

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

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String resultContent = openResultMap.get("result");
		bingoContent = new StringBuffer();
		bingoHashMap = new HashMap<String, Long>();
		bingoHashMap.put("prize1", new Long(0));

		prize1 = Double.valueOf(openResultMap.get("prize1"));

		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;

		String[] contentLine = contentIn.split("\\~");

		for (int i = 0; i < contentLine.length; i++) {
			String content = contentLine[i].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[i].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + resultContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);

			if (playType.getValue() == PlayType.DS.getValue()) {
				String[] lines = content.split("\\^");

				for (int j = 0; j < lines.length; j++) {
					SFCBingoResult result = BingoUtil.getBingoResultCountBySFC(lines[j], ",", "", resultContent, "\\,", 1, 12);

					int totalCount1 = result.getBingoTotalCount();

					if (totalCount1 != 0) {
						this.dealBingoCount(1, totalCount1 * multiple);
					}
				}

			} else if (playType.getValue() == PlayType.FS.getValue()) {
				SFCBingoResult result = BingoUtil.getBingoResultCountBySFC(content, ",", "", resultContent, "\\,", 1, 12);

				int totalCount1 = result.getBingoTotalCount();

				if (totalCount1 != 0) {
					this.dealBingoCount(1, totalCount1 * multiple);
				}
			}
		}

		// 一等奖税后奖金
		double prize1_ = prize1 > 10000 ? (prize1 * 0.8) : prize1;

		// 税前总奖金计算
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * prize1;

		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * prize1_;

		this.buildBingoContent();
	}

	public void buildBingoContent() {
		bingoContent = new StringBuffer();
		bingoContent.append("prize1^").append(bingoHashMap.get("prize1")).append("^").append(bingoHashMap.get("prize1") * prize1);
	}

	public void dealBingoCount(int bingoLevel, int value) {
		long count = 0;
		if (bingoLevel > 1) {
			return;
		}
		this.isBingo = true;
		if (bingoLevel == 1) {
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

	public static void main(String[] args) {

		FbBqcBingoCheck check = new FbBqcBingoCheck();
		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("prize1", "1");

		openResultMap.put("result", "3,1,0,3,3,1,0,3,3,1,0,3");

		check.execute("1:3,1,0,3,3,1,0,3,3,1,0,3~2:3,1,0,3,3,1,0,3,3,1,0,310", PlayType.HT, 1, openResultMap);

		LogUtil.out(check.getBingoContent());

	}
}
