package com.yuncai.modules.lottery.factorys.checkBingo;
import java.util.HashMap;
import java.util.Iterator;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class QxcBingoCheck implements BingoCheck {

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
	private double prize4 = 0;
	private double prize5 = 0;
	private double prize6 = 0;
	private double prize7 = 0;

	public void addCheck(BingoCheck otherCheck) {
		if (!otherCheck.isBingo()) {
			return;
		}
		HashMap<String, Long> otherBingoHashMap = otherCheck.getBingoHashMap();
		this.dealBingoCount(2, otherBingoHashMap.get("prize6").intValue());
		this.dealBingoCount(3, otherBingoHashMap.get("prize5").intValue());
		this.dealBingoCount(4, otherBingoHashMap.get("prize4").intValue());
		this.dealBingoCount(5, otherBingoHashMap.get("prize3").intValue());
		this.dealBingoCount(6, otherBingoHashMap.get("prize2").intValue());
		this.dealBingoCount(7, otherBingoHashMap.get("prize1").intValue());
		this.buildBingoContent();
		this.bingoPosttaxTotal += otherCheck.getBingoPosttaxTotal();
		this.bingoPretaxTotal += otherCheck.getBingoPretaxTotal();
	}

	static public void main(String[] args) {
		QxcBingoCheck check = new QxcBingoCheck();

		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("prize1", "1");
		openResultMap.put("prize2", "2");
		openResultMap.put("prize3", "3");
		openResultMap.put("prize4", "4");
		openResultMap.put("prize5", "5");
		openResultMap.put("prize6", "6");

		openResultMap.put("result", "5,1,3,1,2,1,2");

		// String content,PlayType playType,int multiple, HashMap<String,
		// String> openResultMap
		check.execute("1:5,1,3,1,2,1,2", PlayType.FS, 1, openResultMap);

		LogUtil.out(check.getBingoContent());
		LogUtil.out(check.getBingoPosttaxTotal());
	}

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String resultContent = openResultMap.get("result");

		LogUtil.out("开奖结果:" + resultContent + " 票内容:" + contentIn + " 玩法:" + playTypeIn.getName() + " 倍数:" + multiple);

		LogUtil.out("开奖号码:" + resultContent);
		prize1 = Double.valueOf(openResultMap.get("prize1"));
		prize2 = Double.valueOf(openResultMap.get("prize2"));
		prize3 = Double.valueOf(openResultMap.get("prize3"));
		prize4 = Double.valueOf(openResultMap.get("prize4"));
		prize5 = Double.valueOf(openResultMap.get("prize5"));
		prize6 = Double.valueOf(openResultMap.get("prize6"));

		bingoContent = new StringBuffer();
		bingoHashMap = new HashMap<String, Long>();
		bingoHashMap.put("prize1", new Long(0));
		bingoHashMap.put("prize2", new Long(0));
		bingoHashMap.put("prize3", new Long(0));
		bingoHashMap.put("prize4", new Long(0));
		bingoHashMap.put("prize5", new Long(0));
		bingoHashMap.put("prize6", new Long(0));
		bingoHashMap.put("prize7", new Long(0));

		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;
		String[] contentLine = contentIn.split("\\~");

		for (int i = 0; i < contentLine.length; i++) {
			String content = contentLine[i].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[i].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + resultContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);

			for (String ticketContent : content.split("\\^")) {
				if (playType.getValue() == PlayType.DS.getValue()) {
					String[] lines = ticketContent.split("\\^");
					for (int j = 0; j < lines.length; j++) {
						String line = lines[j];
						int bingoCount = BingoUtil.getBingoNumCountByQxc(line, "\\,", resultContent, "\\,");
						dealBingoCount(bingoCount, 1 * multiple);
					}
				} else {
					HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
					map.put(2, 0);
					map.put(3, 0);
					map.put(4, 0);
					map.put(5, 0);
					map.put(6, 0);
					map.put(7, 0);

					int[] a = new int[7];
					int[] b = new int[7];
					int[] c = new int[7];
					int[] d = new int[7];

					String[] resultCodes = resultContent.split("\\,");
					String[] ticketCodes = ticketContent.split("\\,");

					for (int j = 0; j < resultCodes.length; j++) {
						String resultCode = resultCodes[j];
						// String [] subCodes = ticketCodes[i].split("\\,");
						a[j] = 0;
						b[j] = 0;
						c[j] = 0;
						d[j] = 0;
						for (int k = 0; k < ticketCodes[j].length(); k++) {
							String code = ticketCodes[j].substring(k, k + 1);
							if (code.equals(resultCode)) {
								b[j] = 1;
							} else {
								c[j]++;
								d[j]++;
							}
						}
					}
					BingoUtil.checkQxcBingoMap(6, 2, 6, a, b, c, d, map);
					Iterator<Integer> it = map.keySet().iterator();
					while (it.hasNext()) {
						int bingoCount = it.next();
						int value = map.get(bingoCount);
						if (value != 0) {
							this.dealBingoCount(bingoCount, value * multiple);
						}
					}
				}
			}
		}

		// 开始计算奖金
		double specialPrize = Double.valueOf(openResultMap.get("prize1"));
		double firstPrize = Double.valueOf(openResultMap.get("prize2"));
		double secPrize = Double.valueOf(openResultMap.get("prize3"));

		// 特定奖税后
		double specialPrize_ = specialPrize > 10000 ? (specialPrize * 0.8) : specialPrize;
		// 一等奖税后
		double firstPrize_ = firstPrize > 10000 ? (firstPrize * 0.8) : firstPrize;
		// 二等奖税后
		double secPrize_ = secPrize > 10000 ? (secPrize * 0.8) : secPrize;

		double thirdPrize = Double.valueOf(openResultMap.get("prize4"));
		double forthPrize = Double.valueOf(openResultMap.get("prize5"));
		double fifthPrize = Double.valueOf(openResultMap.get("prize6"));

		// 税前总奖金计算
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * specialPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize2") * firstPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize3") * secPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize4") * thirdPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize5") * forthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize6") * fifthPrize;

		// 税后总奖金计算
		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * specialPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize2") * firstPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize3") * secPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize4") * thirdPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize5") * forthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize6") * fifthPrize;

		this.buildBingoContent();
	}

	public void buildBingoContent() {
		bingoContent = new StringBuffer();
		bingoContent.append("prize1^").append(bingoHashMap.get("prize1")).append("^").append(bingoHashMap.get("prize1") * prize1).append("#");
		bingoContent.append("prize2^").append(bingoHashMap.get("prize2")).append("^").append(bingoHashMap.get("prize2") * prize2).append("#");
		bingoContent.append("prize3^").append(bingoHashMap.get("prize3")).append("^").append(bingoHashMap.get("prize3") * prize3).append("#");
		bingoContent.append("prize4^").append(bingoHashMap.get("prize4")).append("^").append(bingoHashMap.get("prize4") * prize4).append("#");
		bingoContent.append("prize5^").append(bingoHashMap.get("prize5")).append("^").append(bingoHashMap.get("prize5") * prize5).append("#");
		bingoContent.append("prize6^").append(bingoHashMap.get("prize6")).append("^").append(bingoHashMap.get("prize6") * prize6);
	}

	public void dealBingoCount(int bingoCount, int value) {
		if (bingoCount < 2) {
			return;
		}
		long count = 0;
		this.isBingo = true;
		// 中两个号码
		if (bingoCount == 2) {
			count = bingoHashMap.get("prize6");
			count += value;
			bingoHashMap.put("prize6", count);
			return;
		}

		// 中三个号码
		if (bingoCount == 3) {
			count = bingoHashMap.get("prize5");
			count += value;
			bingoHashMap.put("prize5", count);
			return;
		}

		// 中四个号码
		if (bingoCount == 4) {
			count = bingoHashMap.get("prize4");
			count += value;
			bingoHashMap.put("prize4", count);
			return;
		}

		// 中五个号码
		if (bingoCount == 5) {
			count = bingoHashMap.get("prize3");
			count += value;
			bingoHashMap.put("prize3", count);
			return;
		}

		// 中六个号码
		if (bingoCount == 6) {
			count = bingoHashMap.get("prize2");
			count += value;
			bingoHashMap.put("prize2", count);
			return;
		}

		// 中七个号码
		if (bingoCount == 7) {
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
