package com.yuncai.modules.lottery.factorys.checkBingo;

import java.util.HashMap;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MathUtil;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class SsqBingoCheck implements BingoCheck {

	public StringBuffer bingoContent = new StringBuffer(); // 定单出错，无票时 null 错误

	private HashMap<String, Long> bingoHashMap = new HashMap<String, Long>();

	// 税后
	private double bingoPosttaxTotal = 0;
	// 税前
	private double bingoPretaxTotal = 0;
	// 是否中奖
	private boolean isBingo = false;
	
	private String ticketCountPrize=null;
	
	// 是否中3+0 既3红0蓝
	private boolean isBingo3RedBall = false;
	
	// 是否8个及以上红球
	private boolean hasMoreThan8RedBall = false;
	
	private double prize1 = 0;
	private double prize2 = 0;
	private double prize3 = 0;
	private double prize4 = 0;
	private double prize5 = 0;
	private double prize6 = 0;

	public void addCheck(BingoCheck otherCheck) {
		if (!otherCheck.isBingo()) {
			SsqBingoCheck ssqBc = (SsqBingoCheck) otherCheck;
			
			if(!ssqBc.isBingo3RedBall() && !ssqBc.hasMoreThan8RedBall()) {
				return;
			} else {
				if(ssqBc.isBingo3RedBall()) {
					this.isBingo3RedBall = true;
				}
				if(ssqBc.hasMoreThan8RedBall()) {
					this.hasMoreThan8RedBall = true;
				}
			}
		} else  {
			HashMap<String, Long> otherBingoHashMap = otherCheck.getBingoHashMap();
			this.dealBingoCount(1, otherBingoHashMap.get("prize1").intValue());
			this.dealBingoCount(2, otherBingoHashMap.get("prize2").intValue());
			this.dealBingoCount(3, otherBingoHashMap.get("prize3").intValue());
			this.dealBingoCount(4, otherBingoHashMap.get("prize4").intValue());
			this.dealBingoCount(5, otherBingoHashMap.get("prize5").intValue());
			this.dealBingoCount(6, otherBingoHashMap.get("prize6").intValue());
			this.buildBingoContent();
			this.bingoPosttaxTotal += otherCheck.getBingoPosttaxTotal();
			this.bingoPretaxTotal += otherCheck.getBingoPretaxTotal();
		}
	}

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String resultContent = openResultMap.get("result");

		String redAreaResult = resultContent.substring(0, 17);
		String blueAreaResult = resultContent.substring(18, 20);

		LogUtil.out("开奖号码:" + resultContent);
		LogUtil.out("内容:" + contentIn);
		bingoHashMap.put("prize1", new Long(0));
		bingoHashMap.put("prize2", new Long(0));
		bingoHashMap.put("prize3", new Long(0));
		bingoHashMap.put("prize4", new Long(0));
		bingoHashMap.put("prize5", new Long(0));
		bingoHashMap.put("prize6", new Long(0));

		prize1 = Double.valueOf(openResultMap.get("prize1"));
		prize2 = Double.valueOf(openResultMap.get("prize2"));
		prize3 = Double.valueOf(openResultMap.get("prize3"));
		prize4 = Double.valueOf(openResultMap.get("prize4"));
		prize5 = Double.valueOf(openResultMap.get("prize5"));
		prize6 = Double.valueOf(openResultMap.get("prize6"));

		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;

		String[] contentLine = contentIn.split("\\~");

		for (int k = 0; k < contentLine.length; k++) {
			String content = contentLine[k].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[k].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + resultContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);
			String[] lines = content.split("\\^");
			for (int i = 0; i < lines.length; i++) {
				if (playType.getValue() == PlayType.DS.getValue()) {

					String line = lines[i];// 得到每一注
					LogUtil.out(line);
					String redArea = line.split("\\#")[0];
					String blueArea = line.split("\\#")[1];

					// 计算总共命中多少个红球
					int redBingoNumber = BingoUtil.getBingoNumCountNotByAreas(redArea, "\\,", redAreaResult, "\\,");
					// 是否命中蓝球
					boolean isBlueBingo = false;
					if (blueAreaResult.equals(blueArea)) {
						isBlueBingo = true;
					}
					LogUtil.out(redBingoNumber);
					LogUtil.out(isBlueBingo);
					if (redBingoNumber == 6 && isBlueBingo) {
						this.dealBingoCount(1, multiple);
					} else if (redBingoNumber == 6 && !isBlueBingo) {
						this.dealBingoCount(2, multiple);
					} else if (redBingoNumber == 5 && isBlueBingo) {
						this.dealBingoCount(3, multiple);
					} else if ((redBingoNumber == 4 && isBlueBingo) || (redBingoNumber == 5 && !isBlueBingo)) {
						this.dealBingoCount(4, multiple);
					} else if ((redBingoNumber == 3 && isBlueBingo) || (redBingoNumber == 4 && !isBlueBingo)) {
						this.dealBingoCount(5, multiple);
					} else if (isBlueBingo) {
						this.dealBingoCount(6, multiple);
					}
					
//					if((redBingoNumber == 3) && (!isBlueBingo)) {//3红0蓝
//						this.isBingo3RedBall = true;
//					}

				} else if (playType.getValue() == PlayType.FS.getValue()) {
					String line = lines[i];// 得到每一注
					String redArea = line.split("\\#")[0];
					String blueArea = line.split("\\#")[1];

					// 计算总共命中多少个红球
					int redBingoCount = BingoUtil.getBingoNumCountNotByAreas(redArea, ",", redAreaResult, ",");
					// 是否命中蓝球
					int blueBingoCount = BingoUtil.getBingoNumCountNotByAreas(blueArea, ",", blueAreaResult, ",");

					LogUtil.out("redBingoCount:" + redBingoCount);
					LogUtil.out("blueBingoCount:" + blueBingoCount);

					int redBuyCount = redArea.split(",").length;
					int blueBuyCount = blueArea.split(",").length;
					// 1到 6等奖的注数
					int p1 = 0, p2 = 0, p3 = 0, p4 = 0, p5 = 0, p6 = 0;

					if (redBingoCount >= 6 && blueBingoCount == 1) {
						p1 = 1;
					}
					if (redBingoCount >= 6 && blueBingoCount >= 0) {
						p2 = MathUtil.getCombinationCount(blueBuyCount - blueBingoCount, 1);
					}
					if (redBingoCount >= 5 && blueBingoCount >= 1) {
						p3 = MathUtil.getCombinationCount(redBingoCount, 5) * MathUtil.getCombinationCount(redBuyCount - redBingoCount, 1);
					}
					if (redBingoCount >= 5 && blueBingoCount >= 0) {
						p4 = MathUtil.getCombinationCount(redBingoCount, 5) * MathUtil.getCombinationCount(blueBuyCount - blueBingoCount, 1)
								* MathUtil.getCombinationCount(redBuyCount - redBingoCount, 1);
					}
					if (redBingoCount >= 4 && blueBingoCount >= 1) {
						p4 = p4 + MathUtil.getCombinationCount(redBingoCount, 4) * MathUtil.getCombinationCount(redBuyCount - redBingoCount, 2);
					}
					if (redBingoCount >= 4 && blueBingoCount >= 0) {
						p5 = MathUtil.getCombinationCount(redBingoCount, 4) * MathUtil.getCombinationCount(blueBuyCount - blueBingoCount, 1)
								* MathUtil.getCombinationCount(redBuyCount - redBingoCount, 2);
					}
					if (redBingoCount >= 3 && blueBingoCount >= 1) {
						p5 = p5 + MathUtil.getCombinationCount(redBingoCount, 3) * MathUtil.getCombinationCount(redBuyCount - redBingoCount, 3);
					}
					if (redBingoCount >= 2 && blueBingoCount >= 1) {
						p6 = p6 + MathUtil.getCombinationCount(redBingoCount, 2) * MathUtil.getCombinationCount(redBuyCount - redBingoCount, 4);
					}
					if (redBingoCount >= 1 && blueBingoCount >= 1) {
						p6 = p6 + MathUtil.getCombinationCount(redBingoCount, 1) * MathUtil.getCombinationCount(redBuyCount - redBingoCount, 5);
					}
					if (redBingoCount >= 0 && blueBingoCount >= 1) {
						p6 = p6 + MathUtil.getCombinationCount(redBuyCount - redBingoCount, 6);
					}

					if (p1 > 0) {
						this.dealBingoCount(1, p1 * multiple);
					}
					if (p2 > 0) {
						this.dealBingoCount(2, p2 * multiple);
					}
					if (p3 > 0) {
						this.dealBingoCount(3, p3 * multiple);
					}
					if (p4 > 0) {
						this.dealBingoCount(4, p4 * multiple);
					}
					if (p5 > 0) {
						this.dealBingoCount(5, p5 * multiple);
					}
					if (p6 > 0) {
						this.dealBingoCount(6, p6 * multiple);
					}

//					if((redBingoCount == 3) && (blueBingoCount == 0)) {//3红0蓝
//						this.isBingo3RedBall = true;
//					}
					
//					if ((redBuyCount >= 8) && (redBingoCount == 3) && (blueBingoCount == 0)) {// 不管定单拆分几张票,只要有一张票所选红球大于或等于8个 且蓝球不中，红球中3,将标识设置为true
//						this.hasMoreThan8RedBall = true;
//					}

				}
			}
		}

		// 计算奖金

		// 税前总奖金计算
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * prize1;
		this.bingoPretaxTotal += bingoHashMap.get("prize2") * prize2;
		this.bingoPretaxTotal += bingoHashMap.get("prize3") * prize3;
		this.bingoPretaxTotal += bingoHashMap.get("prize4") * prize4;
		this.bingoPretaxTotal += bingoHashMap.get("prize5") * prize5;
		this.bingoPretaxTotal += bingoHashMap.get("prize6") * prize6;

		double prize1_ = prize1 > 10000 ? (prize1 * 0.8) : prize1;
		double prize2_ = prize2 > 10000 ? (prize2 * 0.8) : prize2;

		// 税后总奖金计算
		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * prize1_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize2") * prize2_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize3") * prize3;
		this.bingoPosttaxTotal += bingoHashMap.get("prize4") * prize4;
		this.bingoPosttaxTotal += bingoHashMap.get("prize5") * prize5;
		this.bingoPosttaxTotal += bingoHashMap.get("prize6") * prize6;

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

	public void dealBingoCount(int prizeType, int value) {
		long count = 0;
		this.isBingo = true;// 执行该方法，则表示该票已经是中奖的，所以将该开奖器的是否中奖设为是
		if (prizeType == 1) {// 一等奖
			count = bingoHashMap.get("prize1");
			count += value;
			bingoHashMap.put("prize1", count);
			return;
		}

		if (prizeType == 2) {// 二等奖
			count = bingoHashMap.get("prize2");
			count += value;
			bingoHashMap.put("prize2", count);
			return;
		}

		if (prizeType == 3) {// 三等奖
			count = bingoHashMap.get("prize3");
			count += value;
			bingoHashMap.put("prize3", count);
			return;
		}

		if (prizeType == 4) {// 四等奖
			count = bingoHashMap.get("prize4");
			count += value;
			bingoHashMap.put("prize4", count);
			return;
		}

		if (prizeType == 5) {// 五等奖
			count = bingoHashMap.get("prize5");
			count += value;
			bingoHashMap.put("prize5", count);
			return;
		}

		if (prizeType == 6) {// 六等奖
			count = bingoHashMap.get("prize6");
			count += value;
			bingoHashMap.put("prize6", count);
			return;
		}
	}

	public String getBingoContent() {
		return bingoContent.toString();
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

	public boolean isBingo3RedBall() {
		return this.isBingo3RedBall;
	}
	
	public boolean hasMoreThan8RedBall() {
		return this.hasMoreThan8RedBall;
	}

	public BingoCheck clone() throws CloneNotSupportedException {
		return (BingoCheck) super.clone();
	}
	
	public String getTicketCountPrize() {
		return this.ticketCountPrize;
	}

	public static void main(String[] args) {
		SsqBingoCheck check = new SsqBingoCheck();
		Ticket ticket = new Ticket();

		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("prize1", "1");
		openResultMap.put("prize2", "2");
		openResultMap.put("prize3", "3");
		openResultMap.put("prize4", "4");
		openResultMap.put("prize5", "5");
		openResultMap.put("prize6", "6");

		openResultMap.put("result", "05,07,12,15,18,33,13");

		check.execute("1:05,07,12,15,18,33#13~2:05,07,12,15,18,32,33#13", PlayType.DS, 1, openResultMap);

		LogUtil.out(check.getBingoContent());

	}
}
