package com.yuncai.modules.lottery.factorys.checkBingo;
import java.util.*;

import com.yuncai.core.tools.CombCallBack;
import com.yuncai.core.tools.MathUtil;
import com.yuncai.core.tools.MathUtils;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.factorys.checkBingo.handle.SFCBingoResult;
import com.yuncai.modules.lottery.factorys.checkBingo.handle.TraditionsFootballResult;

public class BingoUtil {
	/**
	 * 插入排序
	 * 
	 * @param data
	 */
	static public void sort(String[] data) {
		String temp;
		for (int i = 1; i < data.length; i++) {
			for (int j = i; (j > 0) && (Integer.parseInt(data[j]) < Integer.parseInt(data[j - 1])); j--) {
				temp = data[j];
				data[j] = data[j - 1];
				data[j - 1] = temp;
			}
		}
	}

	static public int getTotalByContent(String content, String codeSplit) {
		int ret = 0;
		String[] codes = content.split(codeSplit);
		for (int i = 0; i < codes.length; i++) {
			ret += Integer.parseInt(codes[i]);
		}
		return ret;
	}

	/**
	 * 复式得到中奖注数
	 * 
	 * @param prizeBingoCount
	 *            这一奖级需要中多少个
	 * @param bingoCount
	 *            实际中了多少个
	 * @param buyCount
	 *            买了多少个
	 * @param lotteryNumCount
	 *            彩种号码有多少个
	 * @return
	 */
	static public int getBingoPieceCount(int prizeBingoCount, int bingoCount, int buyCount, int lotteryNumCount) {
		return MathUtil.getCombinationCount(bingoCount, prizeBingoCount)
				* MathUtil.getCombinationCount(buyCount - bingoCount, lotteryNumCount - prizeBingoCount);
	}

	/**
	 * 胆拖得到中奖注数
	 * 
	 * @param lotteryNumCount
	 *            彩种开奖号码有多少个
	 * 
	 * @param prizeBingoCount
	 *            这一奖级需要中多少个
	 * @param dCount
	 *            胆个数
	 * @param dBingoCount
	 *            胆中实际中了多少个
	 * @param tCount
	 *            拖个数
	 * @param tBingoCount
	 *            拖中实际中了多少个
	 * 
	 * @return
	 */
	static public int getDtBingoCount(int lotteryNumCount, int prizeBingoCount, int dCount, int dBingoCount, int tCount, int tBingoCount) {
		return MathUtil.getCombinationCount(tBingoCount, prizeBingoCount - dBingoCount)
				* MathUtil.getCombinationCount(tCount - tBingoCount, (lotteryNumCount - dCount) - (prizeBingoCount - dBingoCount));
	}

	/**
	 * 按区得到中奖号码个数 开奖号码 1+2+3 投注号码 1,2+2+4 得到2个号码中奖号码
	 * 
	 * @param content
	 * @param areaSplitChar
	 * @param numberSplitChar
	 * @param bingoContent
	 * @param bingoNumberSplitChar
	 * @return
	 */
	static public int getBingoNumCountByAreas(String content, String areaSplitChar, String numberSplitChar, String bingoContent,
			String bingoNumberSplitChar, int codeLength) {
		String[] areas = content.split(areaSplitChar);
		int bingoNumCount = 0;
		String[] bingoNumbers = bingoContent.split(bingoNumberSplitChar);
		for (int i = 0; i < areas.length; i++) {
			if (bingoNumbers[i].equals("*")) {
				// TODO:任9开奖bug
				bingoNumCount++;
			} else if (numberSplitChar.equals("")) {
				if (areas[i].equals(bingoNumbers[i])) {
					bingoNumCount++;
				}
			} else if (numberSplitChar.equals("*")) {// 表示号码是紧凑排列，如
				int codeCount = areas[i].length() / codeLength;// 得到号码的数量
				int pos = 0;
				for (int j = 0; j < codeCount; j++) {
					String code = areas[i].substring(pos, pos + codeLength);
					if (code.equals(bingoNumbers[i])) {
						bingoNumCount++;
						break;
					}
					pos += codeLength;
				}
			} else {
				String[] codes = areas[i].split(numberSplitChar);
				for (int j = 0; j < codes.length; j++) {
					if (codes[j].equals(bingoNumbers[i])) {
						bingoNumCount++;
					}
				}
			}

		}
		return bingoNumCount;
	}

	/**
	 * 按区得到持续中奖号码个数 开奖号码 1+2+3 投注号码 1,2+2+4 得到2个号码中奖号码
	 * 
	 * @param content
	 * @param areaSplitChar
	 * @param numberSplitChar
	 * @param bingoContent
	 * @param bingoNumberSplitChar
	 * @return
	 */
	static public int getBingoNumCountByQxc(String content, String areaSplitChar, String bingoContent, String bingoNumberSplitChar) {
		List<Integer> countList = new ArrayList<Integer>();
		String[] areas = content.split(areaSplitChar);
		int bingoNumCount = 0;
		String[] bingoNumbers = bingoContent.split(bingoNumberSplitChar);
		for (int i = 0; i < areas.length; i++) {
			// String[] codes = areas[i].split(numberSplitChar);
			for (int j = 0; j < areas[i].length(); j++) {
				String code = areas[i].substring(j, j + 1);
				if (code.equals(bingoNumbers[i])) {
					bingoNumCount++;
				} else {
					if (bingoNumCount != 0) {
						countList.add(bingoNumCount);
						bingoNumCount = 0;
					}
				}
			}
		}
		if (bingoNumCount != 0) {
			countList.add(bingoNumCount);
		}

		if (countList.size() == 0) {
			return 0;
		}
		int ret = 0;
		for (int i = 0; i < countList.size(); i++) {
			if (i == 0) {
				ret = countList.get(i);
			} else {
				if (countList.get(i).intValue() > ret) {
					ret = countList.get(i);
				}
			}
		}
		return ret;
	}

	/*
	 * public boolean isBingoByCompose(String content,String
	 * numberSplitChar,String resultContent,String resultNumberSplitChar){
	 * 
	 * String [] contentCodes = content.split(numberSplitChar); String []
	 * resultCodes = resultContent.split(resultNumberSplitChar);
	 * 
	 * for(int i=0;i<contentCodes.length;i++){ } }
	 */

	/**
	 * 不按区得到中奖号码个数 开奖号码 1+2+3 投注号码 1,2,3,6 得到3个号码中奖号码
	 * 
	 * @param content
	 * @param areaSplitChar
	 * @param numberSplitChar
	 * @param bingoContent
	 * @param bingoNumberSplitChar
	 * @return
	 */
	static public int getBingoNumCountNotByAreas(String content, String numberSplitChar, String resultContent, String resultNumberSplitChar) {

		int bingoNumCount = 0;

		String[] bingoNumbers = resultContent.split(resultNumberSplitChar);

		HashMap bingoMap = new HashMap();
		for (int i = 0; i < bingoNumbers.length; i++) {
			bingoMap.put(bingoNumbers[i], bingoNumbers[i]);
		}
		String[] codes = content.split(numberSplitChar);
		for (int j = 0; j < codes.length; j++) {
			if (bingoMap.containsKey(codes[j])) {
				bingoNumCount++;
			}
		}

		return bingoNumCount;
	}

	static public int getContinuousCounts(String content, String codeSplitChar) {
		String[] codes = content.split(codeSplitChar);

		int tempCodes = 0;
		int lastCount = 0;
		int countTemp = 0;
		for (int i = 0; i < codes.length; i++) {
			if (i == 0) {
				tempCodes = Integer.valueOf(codes[i]);
				countTemp++;
			} else {
				if (tempCodes + 1 == Integer.valueOf(codes[i])) {
					countTemp++;
				} else {
					if (countTemp > lastCount) {
						lastCount = countTemp;
					}
					countTemp = 1;
				}
				tempCodes = Integer.valueOf(codes[i]);
			}

			if (i + 1 == codes.length) {
				if (countTemp > lastCount) {
					lastCount = countTemp;
				}
			}
		}
		return lastCount;
	}

	/**
	 * 
	 * @param n
	 *            总共有多少个号码
	 * @param a
	 *            a[0...N] = 0;
	 * @param b
	 *            中奖情况b[0...N] = 0,1,1,1,1,1,0
	 * @param c
	 * @param d
	 * @param map
	 */
	static public void checkQxcBingoMap(int n, int m, int l, int[] a, int[] b, int[] c, int[] d, HashMap<Integer, Integer> map) {
		// int tempN = n;
		if (n == -1)
			return;
		// 判断a数组最大连续数;
		// 中奖注数为d数组所有数相乘;
		if (n == l) {
			int maxCount = 0;
			int currentCount = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] == 1) {
					currentCount++;
				} else {
					if (currentCount != 0 && currentCount > maxCount) {
						maxCount = currentCount;
					}
					currentCount = 0;
				}
			}
			if (currentCount != 0 && currentCount > maxCount) {
				maxCount = currentCount;
			}
			int bingoCount = 1;
			for (int i = 0; i < d.length; i++) {
				bingoCount *= d[i];
			}

			if (bingoCount != 0 && maxCount >= m) {
				Integer count = map.get(maxCount);
				map.put(maxCount, count + bingoCount);
			}

		}
		if (a[n] == 0) {
			a[n] = 1;
			d[n] = b[n];
			n = l;
			checkQxcBingoMap(n, m, l, a, b, c, d, map);
		} else {
			a[n] = 0;
			d[n] = c[n];
			n = n - 1;
			checkQxcBingoMap(n, m, l, a, b, c, d, map);
		}

	}

	/**
	 * 按区得到中奖号码个数 开奖号码 1+2+3 投注号码 1,2+2+4 得到2个号码中奖号码
	 * 
	 * @param content
	 * @param areaSplitChar
	 * @param numberSplitChar
	 * @param bingoContent
	 * @param bingoNumberSplitChar
	 * @return
	 */
	static public SFCBingoResult getBingoResultCountBySFC(String content, String areaSplitChar, String numberSplitChar, String resultContent,
			String bingoNumberSplitChar, int codeLength, int number) {
		SFCBingoResult result = new SFCBingoResult();
		int[] bingoCount = new int[number];
		int[] noBingoCount = new int[number];
		String[] codeContent = content.split(areaSplitChar);
		String[] bingoContent = resultContent.split(bingoNumberSplitChar);

		for (int i = 0; i < codeContent.length; i++) {
			// String [] codes = codeContent[i].split("\\,");
			if (bingoContent[i].equals("*") && !codeContent[i].equals("-")) {
				bingoCount[i] = codeContent[i].length();
				noBingoCount[i] = 0;
			} else if (codeContent[i].equals("-")) {
				bingoCount[i] = 1;
				noBingoCount[i] = 0;
			} else {
				int bingoNumCount = 0;
				int noBingoNumCount = 0;

				String[] codes = StringTools.convertToCharArray(codeContent[i]);
				int codeCount = codes.length;// 得到号码的数量
				int pos = 0;

				for (int j = 0; j < codeCount; j++) {
					String code = codes[j];
					if (code.equals(bingoContent[i])) {
						bingoNumCount++;
					} else {
						noBingoNumCount++;
					}
					pos += codeLength;
				}
				/*
				 * for(int j = 0 ; j < codes.length; j++){
				 * if(codes[j].equals(bingoContent[i])){ bingoNumCount++; }else{
				 * noBingoNumCount++; } }
				 */
				bingoCount[i] = bingoNumCount;
				noBingoCount[i] = noBingoNumCount;
			}
		}

		result.setBingoCount(bingoCount);
		result.setNoBingoCount(noBingoCount);
		return result;
	}
	/**
	 * 传统足彩中奖算法
	 * @param content
	 * @param areaSplitChar
	 * @param numberSplitChar
	 * @param resultContent
	 * @param bingoNumberSplitChar
	 * @param codeLength
	 * @param number
	 * @return
	 */
	public static TraditionsFootballResult getBingoResultForTraditionsFootball(String content, String areaSplitChar, String numberSplitChar, String resultContent,String bingoNumberSplitChar, int codeLength, int number,int needBingoCount)
	{
		TraditionsFootballResult result = new TraditionsFootballResult();
		int[] bingoCount = new int[number];
		String[] codeContent = content.split(areaSplitChar);
		String[] bingoContent = resultContent.split(bingoNumberSplitChar);
		for (int i = 0; i < codeContent.length; i++) {
			if (bingoContent[i].equals("*") && !codeContent[i].equals("-")) {
				bingoCount[i] = codeContent[i].length();
			} else if (codeContent[i].equals("-")) {
				bingoCount[i] = 0;
			} else {
				int bingoNumCount = 0;
				String[] codes = StringTools.convertToCharArray(codeContent[i]);
				int codeCount = codes.length;// 得到号码的数量
				int pos = 0;
				for (int j = 0; j < codeCount; j++) {
					String code = codes[j];
					if (code.equals(bingoContent[i])) {
						bingoNumCount++;
					}
					pos += codeLength;
				}
				bingoCount[i] = bingoNumCount;
			}
		}
		result.setBingoCount(bingoCount);
		System.out.println(BingoUtil.getBingoTotalCount(needBingoCount,bingoCount));
		result.setTotalBingoCount(BingoUtil.getBingoTotalCount(needBingoCount,bingoCount));
		return result;
	}
	public static SFCBingoResult getBingoResultCountBySFR8(String content, String areaSplitChar, String numberSplitChar, String resultContent,
			String bingoNumberSplitChar, int codeLength, int number) {
		SFCBingoResult result = new SFCBingoResult();
		int[] bingoCount = new int[number];
		int[] noBingoCount = new int[number];
		String[] codeContent = content.split(areaSplitChar);
		String[] bingoContent = resultContent.split(bingoNumberSplitChar);
		for (int i = 0; i < codeContent.length; i++) {
			// String [] codes = codeContent[i].split("\\,");
			if (bingoContent[i].equals("*") && !codeContent[i].equals("-")) {
				bingoCount[i] = codeContent[i].length();
				noBingoCount[i] = 0;
			} else if (codeContent[i].equals("-")) {
				bingoCount[i] = -1;
				noBingoCount[i] = -1;
			} else {
				int bingoNumCount = 0;
				int noBingoNumCount = 0;

				String[] codes = StringTools.convertToCharArray(codeContent[i]);
				int codeCount = codes.length;// 得到号码的数量

				for (int j = 0; j < codeCount; j++) {
					String code = codes[j];
					if (code.equals(bingoContent[i])) {
						bingoNumCount++;
					} else {
						noBingoNumCount++;
					}
				}
				bingoCount[i] = bingoNumCount;
				noBingoCount[i] = noBingoNumCount;
			}
		}
		result.setBingoCount(bingoCount);
		result.setNoBingoCount(noBingoCount);
		return result;

	}

	// 解析Llc字符串2(-),2(-),3(-),4(-),5(-)
	public static int[] getLlcBingoNumCount(String ticket, String resultContent) {
		int[] ret = new int[2];
		int resultNumber = 0;
		int bingoCount = 1;
		String[] single = ticket.split(",");
		for (int i = 0; i < single.length; i++) {
			for (String result : resultContent.split(",")) {
				int singleNum = Integer.valueOf(single[i].substring(0, single[i].indexOf("(")));
				int resultNum = Integer.valueOf(result.substring(0, result.indexOf("(")));
				/*
				 * if(single[i].substring(0,single[i].indexOf("(")).equals(result.substring(0,result.indexOf("(")))) {
				 * resultNumber++; }
				 */
				if (singleNum == resultNum) {
					resultNumber++;

					String peace = single[i].substring(single[i].indexOf("(") + 1, single[i].indexOf(")"));
					bingoCount *= peace.split("\\;").length;
				}
			}

		}
		ret[0] = resultNumber;
		ret[1] = bingoCount;
		return ret;
	}

	// 单式解析ttc字符串2(-),2(-),3(-),4(-),5(-)
	public static int getTtcBingoNumCount(String ticket, String resultContent) {
		int ret = 0;
		String[] single = ticket.split(",");
		String resultArray[] = resultContent.split(",");
		String ticketItem, resultItem;
		String[] ticketLine, resultLine;
		for (String singleVo : single) {
			ticketItem = singleVo.trim().substring(0, singleVo.trim().indexOf("(")).trim();
			ticketLine = singleVo.trim().substring(singleVo.trim().indexOf("(") + 1, singleVo.trim().indexOf(")")).split(";");
			for (String resultVo : resultArray) {
				resultItem = resultVo.trim().substring(0, resultVo.trim().indexOf("(")).trim();
				resultLine = resultVo.trim().substring(resultVo.trim().indexOf("(") + 1, resultVo.trim().indexOf(")")).split(";");
				if (ticketItem.equals(resultItem)) {
					if ("*".equals(resultLine[0])) {
						ret++;
					} else {
						for (String vo : resultLine) {
							if (vo.equals(ticketLine[0])) {
								ret++;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public static int getTtcFsBingoNumCount(String ticket, String resultContent) {
		List rstList = new ArrayList();
		String[] single = ticket.split(",");
		String resultArray[] = resultContent.split(",");
		String ticketItem, resultItem;
		String[] ticketLine, resultLine;
		for (String singleVo : single) {
			ticketItem = singleVo.trim().substring(0, singleVo.trim().indexOf("(")).trim();
			ticketLine = singleVo.trim().substring(singleVo.trim().indexOf("(") + 1, singleVo.trim().indexOf(")")).split(";");
			for (String resultVo : resultArray) {
				int ret = 0;
				resultItem = resultVo.trim().substring(0, resultVo.trim().indexOf("(")).trim();
				resultLine = resultVo.trim().substring(resultVo.trim().indexOf("(") + 1, resultVo.trim().indexOf(")")).split(";");
				if (ticketItem.equals(resultItem)) {
					if ("*".equals(resultLine[0])) {
						ret++;
					} else {
						for (String rst : resultLine) {
							for (String vo : ticketLine) {
								if (rst.equals(vo)) {
									ret++;
									break;
								}
							}
						}
					}
					rstList.add(ret);
					break;
				}
			}
		}
		return getNumber(rstList);
	}

	private static int getNumber(List rstList) {
		int retBetNum = 0;
		if (rstList.size() >= 6) {
			int tmpLen = rstList.size();
			for (int ss1 = 0; ss1 < tmpLen - 5; ss1++) {
				for (int ss2 = ss1 + 1; ss2 < tmpLen - 4; ss2++) {
					for (int ss3 = ss2 + 1; ss3 < tmpLen - 3; ss3++) {
						for (int ss4 = ss3 + 1; ss4 < tmpLen - 2; ss4++) {
							for (int ss5 = ss4 + 1; ss5 < tmpLen - 1; ss5++) {
								for (int ss6 = ss5 + 1; ss6 < tmpLen; ss6++) {
									retBetNum = retBetNum + Integer.parseInt(rstList.get(ss1).toString())
											* Integer.parseInt(rstList.get(ss2).toString()) * Integer.parseInt(rstList.get(ss3).toString())
											* Integer.parseInt(rstList.get(ss4).toString()) * Integer.parseInt(rstList.get(ss5).toString())
											* Integer.parseInt(rstList.get(ss6).toString());
								}
							}
						}
					}
				}
			}

		}
		return retBetNum;
	}

	/**
	 * 得到中奖注数 cont1："14,26,37" subchar1:"," * cont2："1,2,3" subchar2:"," return
	 * 3;
	 */
	public static int substring(String cont1, String subchar1, String cont2, String subchar2) {
		int number = 0;
		String[] insertArry = cont1.split(subchar1);
		String[] openrstArray = cont2.split(subchar2);
		int suff = -1;
		for (int i = 0; i < openrstArray.length; i++) {
			suff = insertArry[i].indexOf(openrstArray[i]);
			if (suff > -1) {
				number++;
				suff = -1;
			}
		}
		return number;
	}

	/**
	 * 判断串中某一位是否命中 cont1："14,26,37" subchar1:"," * cont2："1,2,3" subchar2:","
	 * return 3;
	 */
	public static boolean checkPos(String cont1, String subchar1, String cont2, String subchar2, int pos) {
		String[] insertArry = cont1.split(subchar1);
		String[] openrstArray = cont2.split(subchar2);
		boolean result = insertArry[pos - 1].indexOf(openrstArray[pos - 1]) > -1;
		return result;
	}

	/*
	 * 判断快乐8中奖号码个数
	 * 
	 * 
	 */
	public static int getKL8BingoCount(String content, String resultContent) {

		String[] bingoNumbers = resultContent.split("\\#")[0].split("\\,"); // 中奖号码
		String[] Numbers = content.split("\\,");

		HashMap bingoMap = new HashMap();
		for (int i = 0; i < bingoNumbers.length; i++) {
			bingoMap.put(bingoNumbers[i], bingoNumbers[i]); // 把中奖号码放到hashMap里
		}

		int bingoNumCount = 0;
		// 计算中奖数
		for (int j = 0; j < Numbers.length; j++) {
			if (bingoMap.containsKey(Numbers[j]))
				bingoNumCount++;
		}

		return bingoNumCount;
	}

	public static int getKL8FlyMultiple(String resultContent) {
		String flyNumber = resultContent.split("\\#")[1]; // 飞盘开奖号码
		int flyMultiple = 0;

		HashMap<String, String> bingoMap = new HashMap<String, String>();
		bingoMap.put("1", "1");
		bingoMap.put("2", "2");
		bingoMap.put("3", "3");
		bingoMap.put("4", "4");
		bingoMap.put("5", "5");
		bingoMap.put("10", "10");

		if (bingoMap.containsKey(flyNumber)) {
			flyMultiple = Integer.parseInt(flyNumber);
		}
		return flyMultiple;
	}

	/*	*//**
			 * 判断11选5的号码中奖个数
			 * 
			 * @param content
			 *            票的内容
			 * @param numberSplitChar
			 *            票的分隔符
			 * @param resultContent
			 *            开奖的内容
			 * @param resultNumberSplitChar
			 *            开奖分隔符
			 * @param wflx
			 *            笼统玩法类型 0: 任选1 1: 任选2--任选8以及前二前三组选 2：任选5-8胆拖 3：前二直选 4:
			 *            前三直选
			 * @return
			 */
	public static int getSd11x5BingoCount(String content, String resultContent, int wflx) {
		int bingoNumCount = 0;

		String[] bingoNumbers = resultContent.split("\\,"); // 中奖号码

		HashMap bingoMap = new HashMap();
		for (int i = 0; i < bingoNumbers.length; i++) {
			bingoMap.put(bingoNumbers[i], bingoNumbers[i]); // 把中奖号码放到hashMap里
		}

		// content.split("\\*");
		if (wflx == 0) { // 任选1
			String[] codes = StringTools.convertToStringArray(content);
			for (int j = 0; j < codes.length; j++) {
				if (codes[j].equals(bingoNumbers[0].trim())) {
					bingoNumCount = 1;
					break;
				}
			}
		} else if (wflx == 1) { // 任选2--8 和 组选单式
			String[] codes = StringTools.convertToStringArray(content);
			for (int j = 0; j < codes.length; j++) {
				if (bingoMap.containsKey(codes[j]))
					bingoNumCount++;
			}
		} else if (wflx == 2) { // 任选5--8胆拖
			String[] codes = content.split("\\*");
			String[] danCodes = StringTools.convertToStringArray(codes[0]);
			String[] tuoCodes = StringTools.convertToStringArray(codes[1]);

			for (int j = 0; j < danCodes.length; j++) {
				if (bingoMap.containsKey(danCodes[j]))
					bingoNumCount++;
			}
			if (bingoNumCount == danCodes.length) { // 胆码的中奖个数等于胆码的个数
				for (int j = 0; j < tuoCodes.length; j++) {
					if (bingoMap.containsKey(tuoCodes[j]))
						bingoNumCount++;
				}
			}
		} else if (wflx == 3) { // 前二直选复试
			String[] codes = content.split("\\*");
			String[] firstCodes = StringTools.convertToStringArray(codes[0]);
			String[] secondCodes = StringTools.convertToStringArray(codes[1]);
			for (int j = 0; j < firstCodes.length; j++) {
				if (firstCodes[j].trim().equals(bingoNumbers[0].trim()))
					bingoNumCount++;
			}
			if (bingoNumCount == 1) {
				for (int j = 0; j < secondCodes.length; j++) {
					if (secondCodes[j].trim().equals(bingoNumbers[1].trim()))
						bingoNumCount++;
				}
			}
		} else if (wflx == 4) { // 前三直选复试
			String[] codes = content.split("\\*");
			String[] firstCodes = StringTools.convertToStringArray(codes[0]);
			String[] secondCodes = StringTools.convertToStringArray(codes[1]);
			String[] thirdCodes = StringTools.convertToStringArray(codes[2]);
			for (int j = 0; j < firstCodes.length; j++) {
				if (firstCodes[j].trim().equals(bingoNumbers[0].trim()))
					bingoNumCount++;
			}
			if (bingoNumCount == 1) {
				for (int j = 0; j < secondCodes.length; j++) {
					if (secondCodes[j].trim().equals(bingoNumbers[1].trim()))
						bingoNumCount++;
				}
			}
			if (bingoNumCount == 2) {
				for (int j = 0; j < thirdCodes.length; j++) {
					if (thirdCodes[j].trim().equals(bingoNumbers[2].trim())) {
						bingoNumCount++;
					}
				}
			}
		} else if (wflx == 5) { // 前三直(组)选单式
			if (content.trim().length() == 6) {
				String firstCode = content.trim().substring(0, 2);
				String secondCode = content.trim().substring(2, 4);
				String thirdCode = content.trim().substring(4, 6);
				if (firstCode.equals(bingoNumbers[0].trim()) && secondCode.equals(bingoNumbers[1].trim()) && thirdCode.equals(bingoNumbers[2].trim()))
					bingoNumCount = 3;
				else
					bingoNumCount = 0;
			}
		} else if (wflx == 6) { // 前二直（组）选单式
			if (content.trim().length() == 4) {
				String firstCode = content.trim().substring(0, 2);
				String secondCode = content.trim().substring(2, 4);
				if (firstCode.equals(bingoNumbers[0].trim()) && secondCode.equals(bingoNumbers[1].trim()))
					bingoNumCount = 2;
				else
					bingoNumCount = 0;
			}
		} else if (wflx == 7) { // 前三组选复试
			String[] codes = StringTools.convertToStringArray(content);
			for (int j = 0; j < codes.length; j++) {
				for (int i = 0; i < 3; i++) {
					if (codes[j].equals(bingoNumbers[i].trim())) {
						bingoNumCount++;
						break;
					}
				}
			}
			if (bingoNumCount > 3)
				bingoNumCount = 0;
		}// 前二组选复试
		else if (wflx == 8) {
			String[] codes = StringTools.convertToStringArray(content);
			for (int j = 0; j < codes.length; j++) {
				for (int i = 0; i < 2; i++) {
					if (codes[j].equals(bingoNumbers[i].trim())) {
						bingoNumCount++;
						break;
					}
				}
			}
			if (bingoNumCount > 2)
				bingoNumCount = 0;
		}// 选2连组
		else if (wflx == 9) {
			String[] codes = StringTools.convertToStringArray(content);
			for (int i = 0; i < bingoNumbers.length - 1; i++) {
				for (int m = 0; m < codes.length; m++) {
					for (int n = 0; n < codes.length; n++) {
						if (codes[m].equals(bingoNumbers[i].trim()) && codes[n].equals(bingoNumbers[i + 1].trim())) {
							bingoNumCount++;
						}
					}
				}

			}
		}// 选2连直单式
		else if (wflx == 10) {
			if (content.trim().length() == 4) {
				String firstCode = content.trim().substring(0, 2);
				String secondCode = content.trim().substring(2, 4);
				for (int i = 0; i < bingoNumbers.length - 1; i++) {
					if (firstCode.equals(bingoNumbers[i].trim()) && secondCode.equals(bingoNumbers[i + 1].trim())) {
						bingoNumCount = 2;
						break;
					} else
						bingoNumCount = 0;
				}
			}
		}
		return bingoNumCount;
	}

	/*
	 * public static int getSd11x5BingoCount(String content,String
	 * numberSplitChar, String resultContent, String resultNumberSplitChar,int
	 * wflx) { int bingoNumCount = 0;
	 * 
	 * String[] bingoNumbers = resultContent.split(resultNumberSplitChar);
	 * 
	 * HashMap bingoMap = new HashMap(); for (int i = 0; i <
	 * bingoNumbers.length; i++) { bingoMap.put(bingoNumbers[i],
	 * bingoNumbers[i]); } String[] codes = content.split(numberSplitChar);
	 * if(wflx == 0){ //任选1 for (int j = 0; j < codes.length; j++) { if
	 * (codes[j].equals(bingoNumbers[0].trim())) { bingoNumCount=1; break; } } }
	 * else if(wflx == 1){ //任选2--8 和 组选 for (int j = 0; j < codes.length; j++) {
	 * if (bingoMap.containsKey(codes[j])) bingoNumCount++; }
	 * 
	 * }else if(wflx ==2){ //任选5--8胆拖 String[] danCodes = codes[0].split("\\,");
	 * String[] tuoCodes = codes[1].split("\\,");
	 * 
	 * for (int j = 0; j < danCodes.length; j++) { if
	 * (bingoMap.containsKey(danCodes[j])) bingoNumCount++; } if(bingoNumCount ==
	 * danCodes.length){ //胆码的中奖个数等于胆码的个数 for (int j = 0; j < tuoCodes.length;
	 * j++) { if (bingoMap.containsKey(tuoCodes[j])) bingoNumCount++; } } }else
	 * if(wflx ==3){ //前二直选 String[] firstCodes = codes[0].split("\\,");
	 * String[] secondCodes = codes[1].split("\\,"); for (int j = 0; j <
	 * firstCodes.length; j++) {
	 * if(firstCodes[j].trim().equals(bingoNumbers[0].trim()) ) bingoNumCount++; }
	 * if(bingoNumCount == 1){ for (int j = 0; j < secondCodes.length; j++) {
	 * if(secondCodes[j].trim().equals(bingoNumbers[1].trim()) )
	 * bingoNumCount++; } } } else if(wflx ==4){ //前三直选 String[] firstCodes =
	 * codes[0].split("\\,"); String[] secondCodes = codes[1].split("\\,");
	 * String[] thirdCodes = codes[2].split("\\,"); for (int j = 0; j <
	 * firstCodes.length; j++) {
	 * if(firstCodes[j].trim().equals(bingoNumbers[0].trim()) ) bingoNumCount++; }
	 * if(bingoNumCount == 1){ for (int j = 0; j < secondCodes.length; j++) {
	 * if(secondCodes[j].trim().equals(bingoNumbers[1].trim()) )
	 * bingoNumCount++; } } if(bingoNumCount == 2){ for (int j = 0; j <
	 * thirdCodes.length; j++) {
	 * if(thirdCodes[j].trim().equals(bingoNumbers[2].trim()) ){
	 * bingoNumCount++; } } } } return bingoNumCount; }
	 */

	public static int combination(int m, int n)// m为下标，n为上标
	{
		if (m < 0 || n < 0 || m < n)
			return -1;
		// 数据不符合要求，返回错误信息
		n = n < (m - n) ? n : m - n;// C(m,n)=C(m,m-n)
		if (n == 0)
			return 1;
		int result = m;// C(m,1);
		for (int i = 2, j = result - 1; i <= n; i++, j--) {
			result = result * j / i;// 得到C(m,i)
		}
		return result;
	}

	public static void main(String[] args) throws Exception {

		// com.cailele.lottery.tools.LogUtil.out("23".substring(1,2));
		// com.cailele.lottery.tools.LogUtil.out(getBingoNumCountByAreas("0102,03,0304","\\,","*","01,03,04","\\,",2));

		// com.cailele.lottery.tools.LogUtil.out(BingoUtil.getBingoNumCountByQxc("12,2,3,4,5,6",
		// "\\,", "0,2,3,4,7,8", "\\,"));
		// com.cailele.lottery.tools.LogUtil.out(BingoUtil.getContinuousCounts("9,1,2,3,0","\\,"));

		// SFCBingoResult result =
		// BingoUtil.getBingoResultCountBySFC("1,31,1,1,1,1,1,1,1,1,1,1,1,1",
		// "\\,", "\\*", "1,*,1,1,1,1,1,1,1,1,1,1,1,1", "\\,",1);

		// for(int i=0;i<result.getBingoCount().length;i++){
		// System.out.print(result.getBingoCount()[i]);
		// }
		// com.cailele.lottery.tools.LogUtil.out();
		// for(int i=0;i<result.getNoBingoCount().length;i++){
		// System.out.print(result.getNoBingoCount()[i]);
		// }
		// com.cailele.lottery.tools.LogUtil.out();
		// com.cailele.lottery.tools.LogUtil.out(result.getBingoTotalCount());
		// String [] bingoContent = "1,*,1,1,1,1,1,1,1,1,1,1,1,1".split("\\,");
		//		
		// int totalCount2 = 0;//二等奖中奖数
		// for (int k = 0; k < result.getNoBingoCount().length; k++) {
		// int temp = result.getNoBingoCount()[k];
		// for (int h = 0; h < result.getBingoCount().length; h++) {
		// if (k != h ) {
		// temp = temp * result.getBingoCount()[h];
		// }
		// }
		// com.cailele.lottery.tools.LogUtil.out(k+"|"+temp);
		// totalCount2 += temp;
		// }
		//		
		// com.cailele.lottery.tools.LogUtil.out(totalCount2);
		System.out.println(combination(0, 0));
		int[] bingoCount=new int[]{1,1,1,1,1,1,1,1,2,0,0,0,0,0};
		System.out.println(BingoUtil.getBingoTotalCount(9,bingoCount));
	}
	/**
	 * 获取中奖注数
	 * 
	 * @param needBingoCount  需要命中的个数,如:任9，needBingoCount=9 胜负14，needBingoCount=14
	 * @return
	 */
	public static int getBingoTotalCount(int needBingoCount,int[] bingoCount) {
		Integer totalCount = 0;
		final List<Integer> bingoCountList=new ArrayList<Integer>();
		for(Integer count:bingoCount){
			if(count>0){
				bingoCountList.add(count);
			}
		}
		if(bingoCountList.size()<needBingoCount){
			return totalCount;
		}
		final int[] tempTotalCount=new int[]{0};
		
		MathUtils.efficientComb(bingoCountList.size(), needBingoCount, new CombCallBack(){
			public void callback(boolean[] comb, int m) {
				final int[] tempBingoCount=new int[m];
				for(int i=0,pos=0;i<comb.length;i++){
					if(comb[i]){
						tempBingoCount[pos]=bingoCountList.get(i);
						pos++;
					}
				}
				int tempTotallCount=1;
				for(int i=0;i<tempBingoCount.length;i++){
					tempTotallCount*=tempBingoCount[i];
				}
				tempTotalCount[0]+=tempTotallCount;
			}
		});
		totalCount=tempTotalCount[0];
		return totalCount;
	}

}
