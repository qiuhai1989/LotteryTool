package com.yuncai.modules.lottery.factorys.checkBingo;

import java.util.HashMap;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MathUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class DltBingoCheck implements BingoCheck {

	public StringBuffer bingoContent = new StringBuffer(); // 定单出错，无票时 null 错误

	private HashMap<String, Long> bingoHashMap = new HashMap<String, Long>();

	// 税后
	private double bingoPosttaxTotal = 0;
	// 税前
	private double bingoPretaxTotal = 0;
	// 是否中奖
	private boolean isBingo = false;
	
	private String ticketCountPrize=null;



	private double prize1 = 0;
	private double prize2 = 0;
	private double prize3 = 0;
	private double prize4 = 0;
	private double prize5 = 0;
	private double prize6 = 0;
	private double prize7 = 0;
	private double prize8 = 0;
	private double prize9 = 0;
	private double prize10 = 0;
	private double prize11 = 0;
	private double prize12 = 0;
	// 追加 4 5 6 7等奖
	private double prize13 = 0;
	private double prize14 = 0;
	private double prize15 = 0;
	private double prize16 = 0;

	public void addCheck(BingoCheck otherCheck) {
		if (!otherCheck.isBingo()) {
			return;
		}
		HashMap<String, Long> otherBingoHashMap = otherCheck.getBingoHashMap();
		this.dealBingoCount(1, otherBingoHashMap.get("prize1").intValue());
		this.dealBingoCount(2, otherBingoHashMap.get("prize2").intValue());
		this.dealBingoCount(3, otherBingoHashMap.get("prize3").intValue());
		this.dealBingoCount(4, otherBingoHashMap.get("prize4").intValue());
		this.dealBingoCount(5, otherBingoHashMap.get("prize5").intValue());
		this.dealBingoCount(6, otherBingoHashMap.get("prize6").intValue());
		this.dealBingoCount(7, otherBingoHashMap.get("prize7").intValue());
		this.dealBingoCount(8, otherBingoHashMap.get("prize8").intValue());
		this.dealBingoCount(9, otherBingoHashMap.get("prize9").intValue());
		this.dealBingoCount(10, otherBingoHashMap.get("prize10").intValue());
		this.dealBingoCount(11, otherBingoHashMap.get("prize11").intValue());
		this.dealBingoCount(12, otherBingoHashMap.get("prize12").intValue());
		// 追加 4 5 6 7等奖
		this.dealBingoCount(13, otherBingoHashMap.get("prize13").intValue());
		this.dealBingoCount(14, otherBingoHashMap.get("prize14").intValue());
		this.dealBingoCount(15, otherBingoHashMap.get("prize15").intValue());
		this.dealBingoCount(16, otherBingoHashMap.get("prize16").intValue());

		this.buildBingoContent();
		this.bingoPosttaxTotal += otherCheck.getBingoPosttaxTotal();
		this.bingoPretaxTotal += otherCheck.getBingoPretaxTotal();
	}

	public void execute(String contentIn, PlayType playTypeIn, int multiple, HashMap<String, String> openResultMap) {
		String resultContent = openResultMap.get("result");

		String redAreaResult = resultContent.substring(0, 17);
		String blueAreaResult = resultContent.substring(18, 20);

		LogUtil.out("开奖号码:" + resultContent);
		LogUtil.out("内容:" + contentIn);
		String add = "";
		String contentReal = "";
		boolean isAdd = false;
		if (contentIn.split("\\=").length > 2) {
			add = contentIn.split("\\=")[2];
			if (add.equals("0")) {
				isAdd = true;
			}
			contentReal = contentIn.split("\\=")[0] + "-" + contentIn.split("\\=")[1];
			LogUtil.out("contentReal:" + contentReal);
		} else {
			add = contentIn.split("\\=")[1];
			if (add.equals("0")) {
				isAdd = true;
			}
			contentReal = contentIn.split("\\=")[0];
			LogUtil.out("contentReal:" + contentReal);
		}

		contentIn = contentReal;
		bingoHashMap.put("prize1", new Long(0));
		bingoHashMap.put("prize2", new Long(0));
		bingoHashMap.put("prize3", new Long(0));
		bingoHashMap.put("prize4", new Long(0));
		bingoHashMap.put("prize5", new Long(0));
		bingoHashMap.put("prize6", new Long(0));
		bingoHashMap.put("prize7", new Long(0));
		bingoHashMap.put("prize8", new Long(0));
		bingoHashMap.put("prize9", new Long(0));
		bingoHashMap.put("prize10", new Long(0));
		bingoHashMap.put("prize11", new Long(0));
		bingoHashMap.put("prize12", new Long(0));
		// /追加 4 5 6 7等奖
		bingoHashMap.put("prize13", new Long(0));
		bingoHashMap.put("prize14", new Long(0));
		bingoHashMap.put("prize15", new Long(0));
		bingoHashMap.put("prize16", new Long(0));

		prize1 = Double.valueOf(openResultMap.get("prize1"));
		prize2 = Double.valueOf(openResultMap.get("prize2"));
		prize3 = Double.valueOf(openResultMap.get("prize3"));
		prize4 = Double.valueOf(openResultMap.get("prize4"));
		prize5 = Double.valueOf(openResultMap.get("prize5"));
		prize6 = Double.valueOf(openResultMap.get("prize6"));
		prize7 = Double.valueOf(openResultMap.get("prize7"));
		prize8 = Double.valueOf(openResultMap.get("prize8"));
		prize9 = Double.valueOf(openResultMap.get("prize9"));
		prize10 = Double.valueOf(openResultMap.get("prize10"));
		prize11 = Double.valueOf(openResultMap.get("prize11"));
		prize12 = Double.valueOf(openResultMap.get("prize12"));
		// /追加 4 5 6 7等奖
		prize13 = Double.valueOf(openResultMap.get("prize13"));
		prize14 = Double.valueOf(openResultMap.get("prize14"));
		prize15 = Double.valueOf(openResultMap.get("prize15"));
		prize16 = Double.valueOf(openResultMap.get("prize16"));

		bingoPosttaxTotal = 0;
		bingoPretaxTotal = 0;
		isBingo = false;

		String[] contentLine = contentIn.split("\\~");

		for (int k = 0; k < contentLine.length; k++) {
			String content = contentLine[k].split("\\:")[1];
			PlayType playType = PlayType.getItem(Integer.valueOf(contentLine[k].split("\\:")[0]));
			LogUtil.out("单独处理开奖结果:" + resultContent + " 票内容:" + content + " 玩法:" + playType.getName() + " 倍数:" + multiple);

			for (String ticketContent : content.split("\\^")) {

				if (playType.getValue() == PlayType.DS.getValue()) {
					String[] contents = ticketContent.split("\\-"); // 将每注的投注号码分解(０:前区号码
					// １:后区号码)
					String preNumber = contents[0]; // 前区投注内容
					String posNumber = contents[1];// 后区投注内容

					String[] preNums = preNumber.split("\\ ");
					String[] posNums = posNumber.split("\\ ");

					int preBingo = this.checkPre(preNums, resultContent);// 前区命中号码个数

					int posBingo = this.checkPos(posNums, resultContent);// 后区命中号码个数

					check1(preBingo, posBingo, multiple, isAdd);
				} else if (playType.getValue() == PlayType.FS.getValue() || playType.getValue() == PlayType.DT.getValue()) {
					String[] contents = ticketContent.split("\\-"); // 将每注的投注号码分解(０:前区号码
					// １:后区号码)
					String preNumber = contents[0]; // 前区投注内容
					String posNumber = contents[1];// 后区投注内容
					if (playType.getValue() == PlayType.FS.getValue()) {
						preNumber = "$" + preNumber;
						posNumber = "$" + posNumber;
					}
					check2(preNumber, posNumber, resultContent, multiple, isAdd);
				} else if (playType.getValue() == PlayType.SXL_DS.getValue()) {
					String[] luckNums = ticketContent.split("\\ ");
					check3(luckNums, resultContent, multiple);
				} else if (playType.getValue() == PlayType.SXL_FS.getValue()) {
					check4("$" + ticketContent, resultContent, multiple);
				}
			}
		}

		// 开始计算奖金
		double firstPrize = Double.valueOf(openResultMap.get("prize1"));
		double secPrize = Double.valueOf(openResultMap.get("prize3"));
		double thirdPrize = Double.valueOf(openResultMap.get("prize5"));

		double addfirstPrize = Double.valueOf(openResultMap.get("prize2"));
		double addsecPrize = Double.valueOf(openResultMap.get("prize4"));
		double addthirdPrize = Double.valueOf(openResultMap.get("prize6"));

		// 一等奖税后奖金
		double firstPrize_ = firstPrize > 10000 ? (firstPrize * 0.8) : firstPrize;
		// 二等奖税后奖金
		double secPrize_ = secPrize > 10000 ? (secPrize * 0.8) : secPrize;
		// 三等奖税后奖金
		double thirdPrize_ = thirdPrize > 10000 ? (thirdPrize * 0.8) : thirdPrize;
		// 追加一等奖税后奖金
		double addfirstPrize_ = addfirstPrize > 10000 ? (addfirstPrize * 0.8) : addfirstPrize;
		// 追加二等奖税后奖金
		double addsecPrize_ = addsecPrize > 10000 ? (addsecPrize * 0.8) : addsecPrize;
		// 追加三等奖税后奖金
		double addthirdPrize_ = addthirdPrize > 10000 ? (addthirdPrize * 0.8) : addthirdPrize;

		double fourthPrize = Double.valueOf(openResultMap.get("prize7"));
		double fifthPrize = Double.valueOf(openResultMap.get("prize8"));
		double sixthPrize = Double.valueOf(openResultMap.get("prize9"));
		double seventhPrize = Double.valueOf(openResultMap.get("prize10"));
		double eigthPrize = Double.valueOf(openResultMap.get("prize11"));
		double luckyPrize = Double.valueOf(openResultMap.get("prize12"));

		// 追加 4 5 6 7等奖
		double addfourthPrize = Double.valueOf(openResultMap.get("prize13"));
		double addfifthPrize = Double.valueOf(openResultMap.get("prize14"));
		double addsixthPrize = Double.valueOf(openResultMap.get("prize15"));
		double addseventhPrize = Double.valueOf(openResultMap.get("prize16"));

		// 税前总奖金计算
		this.bingoPretaxTotal += bingoHashMap.get("prize1") * firstPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize3") * secPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize5") * thirdPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize7") * fourthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize8") * fifthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize9") * sixthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize10") * seventhPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize11") * eigthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize2") * addfirstPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize4") * addsecPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize6") * addthirdPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize12") * luckyPrize;
		// 追加 4 5 6 7等奖
		this.bingoPretaxTotal += bingoHashMap.get("prize13") * addfourthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize14") * addfifthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize15") * addsixthPrize;
		this.bingoPretaxTotal += bingoHashMap.get("prize16") * addseventhPrize;

		// 税后总奖金计算
		this.bingoPosttaxTotal += bingoHashMap.get("prize1") * firstPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize3") * secPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize5") * thirdPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize7") * fourthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize8") * fifthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize9") * sixthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize10") * seventhPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize11") * eigthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize2") * addfirstPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize4") * addsecPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize6") * addthirdPrize_;
		this.bingoPosttaxTotal += bingoHashMap.get("prize12") * luckyPrize;
		// 追加 4 5 6 7等奖
		this.bingoPosttaxTotal += bingoHashMap.get("prize13") * addfourthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize14") * addfifthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize15") * addsixthPrize;
		this.bingoPosttaxTotal += bingoHashMap.get("prize16") * addseventhPrize;

		this.buildBingoContent();
	}

	public void buildBingoContent() {
		bingoContent = new StringBuffer();
		bingoContent.append("prize1^").append(bingoHashMap.get("prize1")).append("^").append(bingoHashMap.get("prize1") * prize1).append("#");
		bingoContent.append("prize2^").append(bingoHashMap.get("prize2")).append("^").append(bingoHashMap.get("prize2") * prize2).append("#");
		bingoContent.append("prize3^").append(bingoHashMap.get("prize3")).append("^").append(bingoHashMap.get("prize3") * prize3).append("#");
		bingoContent.append("prize4^").append(bingoHashMap.get("prize4")).append("^").append(bingoHashMap.get("prize4") * prize4).append("#");
		bingoContent.append("prize5^").append(bingoHashMap.get("prize5")).append("^").append(bingoHashMap.get("prize5") * prize5).append("#");
		bingoContent.append("prize6^").append(bingoHashMap.get("prize6")).append("^").append(bingoHashMap.get("prize6") * prize6).append("#");
		bingoContent.append("prize7^").append(bingoHashMap.get("prize7")).append("^").append(bingoHashMap.get("prize7") * prize7).append("#");
		bingoContent.append("prize8^").append(bingoHashMap.get("prize8")).append("^").append(bingoHashMap.get("prize8") * prize8).append("#");
		bingoContent.append("prize9^").append(bingoHashMap.get("prize9")).append("^").append(bingoHashMap.get("prize9") * prize9).append("#");
		bingoContent.append("prize10^").append(bingoHashMap.get("prize10")).append("^").append(bingoHashMap.get("prize10") * prize10).append("#");
		bingoContent.append("prize11^").append(bingoHashMap.get("prize11")).append("^").append(bingoHashMap.get("prize11") * prize11).append("#");
		bingoContent.append("prize12^").append(bingoHashMap.get("prize12")).append("^").append(bingoHashMap.get("prize12") * prize12).append("#");
		// /追加 4 5 6 7等奖
		bingoContent.append("prize13^").append(bingoHashMap.get("prize13")).append("^").append(bingoHashMap.get("prize13") * prize13).append("#");
		bingoContent.append("prize14^").append(bingoHashMap.get("prize14")).append("^").append(bingoHashMap.get("prize14") * prize14).append("#");
		bingoContent.append("prize15^").append(bingoHashMap.get("prize15")).append("^").append(bingoHashMap.get("prize15") * prize15).append("#");
		bingoContent.append("prize16^").append(bingoHashMap.get("prize16")).append("^").append(bingoHashMap.get("prize16") * prize16);
	}

	public void dealBingoCount(int prizeType, int value) {
		long count = 0;
		this.isBingo = true;
		if (prizeType == 1) {// 一等奖
			count = bingoHashMap.get("prize1");
			count += value;
			bingoHashMap.put("prize1", count);
			return;
		}
		if (prizeType == 3) {// 二等奖
			count = bingoHashMap.get("prize3");
			count += value;
			bingoHashMap.put("prize3", count);
			return;
		}
		if (prizeType == 5) {// 三等奖
			count = bingoHashMap.get("prize5");
			count += value;
			bingoHashMap.put("prize5", count);
			return;
		}
		if (prizeType == 7) {// 四等奖
			count = bingoHashMap.get("prize7");
			count += value;
			bingoHashMap.put("prize7", count);
			return;
		}
		if (prizeType == 8) {// 五等奖
			count = bingoHashMap.get("prize8");
			count += value;
			bingoHashMap.put("prize8", count);
			return;
		}
		if (prizeType == 9) {// 六等奖
			count = bingoHashMap.get("prize9");
			count += value;
			bingoHashMap.put("prize9", count);
			return;
		}
		if (prizeType == 10) {// 七等奖
			count = bingoHashMap.get("prize10");
			count += value;
			bingoHashMap.put("prize10", count);
			return;
		}
		if (prizeType == 11) {// 八等奖
			count = bingoHashMap.get("prize11");
			count += value;
			bingoHashMap.put("prize11", count);
			return;
		}
		if (prizeType == 2) {// 追加一等奖
			count = bingoHashMap.get("prize2");
			count += value;
			bingoHashMap.put("prize2", count);
			return;
		}
		if (prizeType == 4) {// 追加二等奖
			count = bingoHashMap.get("prize4");
			count += value;
			bingoHashMap.put("prize4", count);
			return;
		}
		if (prizeType == 6) {// 追加三等奖
			count = bingoHashMap.get("prize6");
			count += value;
			bingoHashMap.put("prize6", count);
			return;
		}
		if (prizeType == 12) {// 幸运彩
			count = bingoHashMap.get("prize12");
			count += value;
			bingoHashMap.put("prize12", count);
			return;
		}

		if (prizeType == 13) {// 追加4等奖
			count = bingoHashMap.get("prize13");
			count += value;
			bingoHashMap.put("prize13", count);
			return;
		}
		if (prizeType == 14) {// 追加5等奖
			count = bingoHashMap.get("prize14");
			count += value;
			bingoHashMap.put("prize14", count);
			return;
		}
		if (prizeType == 15) {// 追加6等奖
			count = bingoHashMap.get("prize15");
			count += value;
			bingoHashMap.put("prize15", count);
			return;
		}
		if (prizeType == 16) {// 追加7等奖
			count = bingoHashMap.get("prize16");
			count += value;
			bingoHashMap.put("prize16", count);
			return;
		}

	}

	private void check1(int preBingo, int posBingo, int mutiple, boolean isAdd) {
		LogUtil.out("preBingo=" + preBingo);
		LogUtil.out("posBingo=" + posBingo);
		long count = 0;
		if (preBingo == 5 && posBingo == 2) {// 一等奖:选中5个前区号码及2个后区号码
			count = this.bingoHashMap.get("prize1");
			count += mutiple;
			bingoHashMap.put("prize1", count);
			if (isAdd) {
				this.bingoHashMap.put("prize2", count);
			}
		}

		else if (preBingo == 5 && posBingo == 1) {// 二等奖:选中5个前区号码及2个后区号码中的任意1个
			count = this.bingoHashMap.get("prize3");
			count += mutiple;
			bingoHashMap.put("prize3", count);
			if (isAdd) {
				this.bingoHashMap.put("prize4", count);
			}
		}

		else if (preBingo == 5) {// 三等奖:选中5个前区号码
			count = this.bingoHashMap.get("prize5");
			count += mutiple;
			bingoHashMap.put("prize5", count);
			if (isAdd) {
				this.bingoHashMap.put("prize6", count);
			}
		}

		else if (preBingo == 4 && posBingo == 2) {// 四等奖:选中4个前区号码及2个后区号码
			count = this.bingoHashMap.get("prize7");
			count += mutiple;
			bingoHashMap.put("prize7", count);
			if (isAdd) {
				this.bingoHashMap.put("prize13", count);
			}
		}

		else if (preBingo == 4 && posBingo == 1) {// 五等奖:选中4个前区号码及2个后区号码中的任意1个
			count = this.bingoHashMap.get("prize8");
			count += mutiple;
			bingoHashMap.put("prize8", count);
			if (isAdd) {
				this.bingoHashMap.put("prize14", count);
			}
		}

		else if ((preBingo == 3 && posBingo == 2) || preBingo == 4) {// 六等奖:选中3个前区号码及2个后区号码或选中4个前区号码
			count = this.bingoHashMap.get("prize9");
			count += mutiple;
			bingoHashMap.put("prize9", count);
			if (isAdd) {
				this.bingoHashMap.put("prize15", count);
			}
		}

		else if ((preBingo == 3 && posBingo == 1) || (preBingo == 2 && posBingo == 2)) {// 七等奖:选中3个前区号码及2个后区号码中的任意1个或选中2个前区号码及2个后区号码
			count = this.bingoHashMap.get("prize10");
			count += mutiple;
			bingoHashMap.put("prize10", count);
			if (isAdd) {
				this.bingoHashMap.put("prize16", count);
			}
		}

		else if (preBingo == 3 || (preBingo == 1 && posBingo == 2) || (preBingo == 2 && posBingo == 1) || posBingo == 2) {// 八等奖:选中3个前区号码或选中1个前区号码及2个后区号码或2个前区号码及2个后区号码中的任意1个或只选中2个后区号码
			count = this.bingoHashMap.get("prize11");
			count += mutiple;
			bingoHashMap.put("prize11", count);
		}
		if (count != 0) {
			this.isBingo = true;
		}

	}

	private int check2(String preNumber, String posNumber, String resultContent, int mutiple, boolean isAdd) {
		// 前区所需参数
		int k1;// 胆区选中k1个球
		int m1;// 拖区选中m1个球
		int y1;// 开奖中总球数y1为5个
		int j1;// k1个球中有j1个为开出号码
		int n1;// m1个球中有n1个为开出号码

		String[] dantuo = preNumber.split("\\$");
		if (dantuo[0].equals("") || dantuo[0].equals(null)) {
			k1 = 0;
		} else {
			k1 = dantuo[0].split("\\ ").length;// 胆码
		}
		m1 = dantuo[1].split("\\ ").length;// 拖码
		y1 = 5;
		j1 = checkPre(dantuo[0].split("\\ "), resultContent);
		n1 = checkPre(dantuo[1].split("\\ "), resultContent);

		// 后区所需参数
		int k2;// 胆区选中k2个球
		int m2;// 拖区选中m2个球
		int y2;// 开奖中总球数y2为5个
		int j2;// k2个球中有j2个为开出号码
		int n2;// m2个球中有n2个为开出号码

		dantuo = posNumber.split("\\$");
		if (dantuo[0].equals("") || dantuo[0].equals(null)) {
			k2 = 0;
		} else {
			k2 = dantuo[0].split("\\ ").length;// 胆码
		}
		m2 = dantuo[1].split("\\ ").length;// 拖码
		y2 = 2;
		j2 = checkPos(dantuo[0].split("\\ "), resultContent);
		n2 = checkPos(dantuo[1].split("\\ "), resultContent);

		// 求注数公式:C(x-j) n * C(y-k)-(x-j) m-n
		// 求组合公式:MathUtil.getCombinationCount(n,r);n选r的组合

		// ---------------一等奖-------------------------
		int x1 = 5;
		int x2 = 2;
		// 前区中奖注数
		int preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		int posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));
		LogUtil.out("前区中奖注数:" + preWinCount);
		LogUtil.out("后区中奖注数:" + posWinCount);
		// 中奖注数
		LogUtil.out("一等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		if (preWinCount * posWinCount * mutiple != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize1", new Long(preWinCount * posWinCount * mutiple));

		if (isAdd) {
			this.bingoHashMap.put("prize2", new Long(preWinCount * posWinCount * mutiple));
		}
		// ----------------------------------------------

		// ---------------二等奖-------------------------
		x1 = 5;
		x2 = 1;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));
		// 中奖注数
		LogUtil.out("二等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		if (preWinCount * posWinCount * mutiple != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize3", new Long(preWinCount * posWinCount * mutiple));
		if (isAdd) {
			this.bingoHashMap.put("prize4", new Long(preWinCount * posWinCount * mutiple));
		}
		// ----------------------------------------------

		// ---------------三等奖-------------------------
		x1 = 5;
		x2 = 0;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));
		// 中奖注数
		LogUtil.out("三等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		if (preWinCount * posWinCount * mutiple != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize5", new Long(preWinCount * posWinCount * mutiple));
		if (isAdd) {
			this.bingoHashMap.put("prize6", new Long(preWinCount * posWinCount * mutiple));
		}
		// ----------------------------------------------

		// ---------------四等奖-------------------------
		x1 = 4;
		x2 = 2;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));
		// 中奖注数
		LogUtil.out("四等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		if (preWinCount * posWinCount * mutiple != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize7", new Long(preWinCount * posWinCount * mutiple));

		if (isAdd) {
			this.bingoHashMap.put("prize13", new Long(preWinCount * posWinCount * mutiple));
		}
		// ----------------------------------------------

		// ---------------五等奖-------------------------
		x1 = 4;
		x2 = 1;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));
		// 中奖注数
		LogUtil.out("五等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		if (preWinCount * posWinCount * mutiple != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize8", new Long(preWinCount * posWinCount * mutiple));

		if (isAdd) {
			this.bingoHashMap.put("prize14", new Long(preWinCount * posWinCount * mutiple));
		}
		// ----------------------------------------------

		// ---------------六等奖-------------------------
		x1 = 3;
		x2 = 2;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 或

		x1 = 4;
		x2 = 0;
		// 前区中奖注数
		int preWinCount1 = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		int posWinCount1 = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 中奖注数
		LogUtil.out("六等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		LogUtil.out("preWinCount1=" + preWinCount1);
		LogUtil.out("posWinCount1=" + posWinCount1);
		long count6 = 0;
		count6 = (preWinCount * posWinCount) + (preWinCount1 * posWinCount1);
		count6 *= mutiple;
		if (count6 != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize9", count6);

		if (isAdd) {
			this.bingoHashMap.put("prize15", count6);
		}
		// ----------------------------------------------

		// ---------------七等奖-------------------------
		x1 = 3;
		x2 = 1;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 或

		x1 = 2;
		x2 = 2;
		// 前区中奖注数
		preWinCount1 = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount1 = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 中奖注数
		LogUtil.out("七等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		LogUtil.out("preWinCount1=" + preWinCount1);
		LogUtil.out("posWinCount1=" + posWinCount1);
		long count7 = 0;
		count7 = (preWinCount * posWinCount) + (preWinCount1 * posWinCount1);
		count7 *= mutiple;
		if (count7 != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize10", count7);

		if (isAdd) {
			this.bingoHashMap.put("prize16", count7);
		}
		// ----------------------------------------------

		// ---------------八等奖-------------------------
		x1 = 3;
		x2 = 0;
		// 前区中奖注数
		preWinCount = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 或

		x1 = 1;
		x2 = 2;
		// 前区中奖注数
		preWinCount1 = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		posWinCount1 = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 或

		x1 = 2;
		x2 = 1;
		// 前区中奖注数
		int preWinCount2 = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		int posWinCount2 = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 或

		x1 = 0;
		x2 = 2;
		// 前区中奖注数
		int preWinCount3 = MathUtil.getCombinationCount(n1, (x1 - j1)) * MathUtil.getCombinationCount(m1 - n1, (y1 - k1) - (x1 - j1));
		// 后区中奖注数
		int posWinCount3 = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));

		// 中奖注数
		LogUtil.out("八等奖");
		LogUtil.out("preWinCount=" + preWinCount);
		LogUtil.out("posWinCount=" + posWinCount);
		LogUtil.out("preWinCount1=" + preWinCount1);
		LogUtil.out("posWinCount1=" + posWinCount1);
		LogUtil.out("preWinCount2=" + preWinCount2);
		LogUtil.out("posWinCount2=" + posWinCount2);
		LogUtil.out("preWinCount3=" + preWinCount3);
		LogUtil.out("posWinCount3=" + posWinCount3);
		long count8 = 0;
		count8 = (preWinCount * posWinCount) + (preWinCount1 * posWinCount1) + (preWinCount2 * posWinCount2) + (preWinCount3 * posWinCount3);
		count8 *= mutiple;
		if (count8 != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize11", count8);
		// ----------------------------------------------

		return j1 + n1;
	}

	/**
	 * 
	 * @日 期:2007-05-25
	 * @描 述:幸运奖单式中奖判断
	 * @param posNumber
	 *            String
	 * @return void
	 */
	private int check3(String[] luckNums, String resultContent, int mutiple) {
		int posBingo = checkPos(luckNums, resultContent);// 后区命中号码个数
		long count = 0;

		if (posBingo == 2) {// 幸运奖:选中2个后区号码
			count++;
			// count += ticket.getAddAttribute().getValue();
		}
		long luckyCount = 0;
		luckyCount = count * mutiple + bingoHashMap.get("prize12");

		this.bingoHashMap.put("prize12", luckyCount);

		if (luckyCount != 0) {
			this.isBingo = true;
		}
		return posBingo;
	}

	/**
	 * 
	 * @日 期:2007-05-25
	 * @描 述:幸运奖复式中奖判断
	 * @param posNumber
	 *            String
	 * @return void
	 */
	private int check4(String posNumber, String resultContent, int mutiple) {
		// 后区所需参数
		int k2;// 胆区选中k2个球
		int m2;// 拖区选中m2个球
		int y2;// 开奖中总球数y2为5个
		int j2;// k2个球中有j2个为开出号码
		int n2;// m2个球中有n2个为开出号码

		String[] dantuo = posNumber.split("\\$");
		if (dantuo[0].equals("") || dantuo[0].equals(null)) {
			k2 = 0;
		} else {
			k2 = dantuo[0].split("\\ ").length;// 胆码
		}
		m2 = dantuo[1].split("\\ ").length;// 拖码
		y2 = 2;

		j2 = checkPos(dantuo[0].split("\\ "), resultContent);
		n2 = checkPos(dantuo[1].split("\\ "), resultContent);

		LogUtil.out("k2=" + k2);
		LogUtil.out("m2=" + m2);
		LogUtil.out("y2=" + y2);
		LogUtil.out("j2=" + j2);
		LogUtil.out("n2=" + n2);

		int x2 = 2;
		// 后区中奖注数
		int posWinCount = MathUtil.getCombinationCount(n2, (x2 - j2)) * MathUtil.getCombinationCount(m2 - n2, (y2 - k2) - (x2 - j2));
		LogUtil.out(">>>>>>>>>>幸运彩：" + posWinCount);
		// 中奖注数
		long luckyCount = 0;
		luckyCount = posWinCount * mutiple;
		if (luckyCount != 0) {
			this.isBingo = true;
		}
		this.bingoHashMap.put("prize12", luckyCount);

		return j2 + n2;
	}

	/**
	 * 
	 * @日 期:2007-05-26
	 * @描 述:判断前区的中奖个数
	 * @param nums
	 *            String
	 * @return int
	 */
	private int checkPre(String[] preNums, String resultContent) {
		String[] strAwardCode = resultContent.split("\\,");// 开奖号码
		int preBingo = 0;// 前区命中号码个数

		for (int i = 0; i < strAwardCode.length - 2; i++) {
			for (int j = 0; j < preNums.length; j++) {

				if (preNums[j].indexOf(strAwardCode[i]) >= 0) {
					preBingo++;
				}
			}
		}
		return preBingo;
	}

	/**
	 * 
	 * @日 期:2007-05-26
	 * @描 述:判断后区的中奖个数
	 * @param posNums
	 *            String
	 * @return int
	 */
	private int checkPos(String[] posNums, String resultContent) {
		String[] strAwardCode = resultContent.split("\\,");// 开奖号码
		int posBingo = 0;// 后区命中号码个数

		for (int i = 5; i < strAwardCode.length; i++) {
			for (int j = 0; j < posNums.length; j++) {
				if (posNums[j].indexOf(strAwardCode[i]) >= 0) {
					posBingo++;
				}
			}
		}
		return posBingo;
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


	public String getTicketCountPrize() {
		return this.ticketCountPrize;
	}
	
	public BingoCheck clone() throws CloneNotSupportedException {
		return (BingoCheck) super.clone();
	}
}
