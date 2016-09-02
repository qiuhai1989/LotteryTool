package com.yuncai.modules.lottery.factorys.ticketBingo;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.modules.lottery.factorys.checkBingo.BingoCheck;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItemTurbid;
import com.yuncai.modules.lottery.sporttery.support.PassMode;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.SportteryTicketModelTurbid;

public abstract class SportteryBingoCheckTurbid<T extends SportteryTicketModelTurbid<X>,X extends MatchItemTurbid> implements BingoCheck {

	
	protected Map<String, Double> prizeHashMap = new HashMap<String, Double>();
	protected HashMap<String, Long> bingoHashMap = new HashMap<String, Long>();

	public StringBuffer bingoContent = new StringBuffer();// 中奖后的内容
	protected double bingoPretaxTotal;// 税前奖金
	protected double bingoPosttaxTotal;// 税后奖金
	
	protected StringBuffer ticketCountPrize=new StringBuffer();
	protected static DecimalFormat DF = new DecimalFormat("#.00");
	protected boolean isBingo = false;
	protected boolean isOpenAble = true;
	@SuppressWarnings("unchecked")
	public void addCheck(BingoCheck otherCheck) {
		SportteryBingoCheckTurbid<T, X> check = (SportteryBingoCheckTurbid<T, X>) otherCheck;
		if (!check.isOpenAble) {
			this.isOpenAble = false;
			return;
		}
		if (!check.isBingo)
			return;
		isBingo = true;
		// 中奖情况加入
		for (String key : check.bingoHashMap.keySet()) {
			if (this.bingoHashMap.containsKey(key))
				this.bingoHashMap.put(key, this.bingoHashMap.get(key) + check.bingoHashMap.get(key));
			else
				this.bingoHashMap.put(key, check.bingoHashMap.get(key));
		}
		for (String key : check.prizeHashMap.keySet()) {
			if (this.prizeHashMap.containsKey(key))
				this.prizeHashMap.put(key, this.prizeHashMap.get(key) + check.prizeHashMap.get(key));
			else
				this.prizeHashMap.put(key, check.prizeHashMap.get(key));
		}
		this.bingoPosttaxTotal += Double.valueOf(DF.format(check.getBingoPosttaxTotal()));
		this.bingoPretaxTotal += Double.valueOf(DF.format(check.getBingoPretaxTotal()));
	}
	/**
	 * 竟彩篮球开奖算法
	 */
	public void execute(String content, PlayType playType, int multiple, HashMap<String, String> openResultMap) {
		T ticketModel = CommonUtils.Object4TikectJsonTurbid(content, getTClass(), getXClass());
		List<X> matchItems = ticketModel.getMatchItems();
		String type = openResultMap.get("lotteryType");
		if (ticketModel.getPassMode().equals(PassMode.SINGLE.ordinal())) {// 处理单关
			// 根据玩法取得比赛的选项
			Double resultAward = 0d;
			
			//先判断完整是否有赛果
			for(X f : matchItems){
				try {
					String tempKey = f.getIntTime() + "_" + f.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					if (!tempHomeScore.equals("0")) {// 赛事取消
						
					}
				}catch (NullPointerException e) {
					isOpenAble = false;
					break;// 空指针异常，说明选择的赛事中有赛事还未结果
				}
			}
			int index=0;
			for (X x : matchItems) {
				index++;
				Double tempAward = 0d;
				try {// TODO:赛事取消的情况
					Map<String, String> matchResultMap = new HashMap<String, String>();
					String tempKey = x.getIntTime() + "_" + x.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					String tempGuestScore = openResultMap.get(CommonUtils.guestScoreKey + tempKey);
					String temphandicap = openResultMap.get(CommonUtils.handicapKey + tempKey);
					String tempbigOrSmallbase = openResultMap.get(CommonUtils.bigSmallKey + tempKey);
					matchResultMap.put(CommonUtils.homeScoreKey, tempHomeScore);
					matchResultMap.put(CommonUtils.guestScoreKey, tempGuestScore);
					matchResultMap.put(CommonUtils.handicapKey, temphandicap);
					matchResultMap.put(CommonUtils.bigSmallKey, tempbigOrSmallbase);

					String tempSFAward = openResultMap.get(CommonUtils.resultSFKey + tempKey);
					String tempSFCAward = openResultMap.get(CommonUtils.resultSFCKey + tempKey);
					String tempRFSFAward = openResultMap.get(CommonUtils.resultRFSFKey + tempKey);
					String tempDXFward = openResultMap.get(CommonUtils.resultDXFKey + tempKey);

					Double SFAward = Double.valueOf(tempSFAward);// 胜负玩法
					//Double SFCAward = Double.valueOf(tempSFCAward);// 胜分差玩法
					Double RFSFAward = Double.valueOf(tempRFSFAward);// 让分胜负
					Double DXFward = Double.valueOf(tempDXFward);// 大小分
					if (tempHomeScore.equals("0")) {// 赛事取消
						tempAward = 2d * x.getOptions().size();
						if(index==1){
							ticketCountPrize.append("2×"+x.getOptions().size()+"×");
						}else{
							ticketCountPrize.append("2×"+x.getOptions().size()+"×");
						}
						
					} else {
						OptionItem option =null; 
						SportteryOption winOption=x.won(option);
						boolean isWon =  winOption== null ? false : true;// 是否中奖
						if (!isWon)
							continue;
						// 单关直接就是每注的奖金值
						if (type.equals("7301")) {
							tempAward = SFAward;
							ticketCountPrize.append(SFAward + " × ");
						} else if (type.equals("7302")) {
							tempAward = RFSFAward;
							ticketCountPrize.append(RFSFAward + "×");
						} else if (type.equals("7303")) {
							//胜分差从浮动奖改为固定奖
							tempAward = winOption.getAward()*2;
							ticketCountPrize.append(winOption.getAward()+"×2" + "×");
						} else if (type.equals("7304")) {
							tempAward = DXFward;
							ticketCountPrize.append(DXFward + "×");
						}
						
						if (tempAward.intValue() == 0&&!type.equals("7303")) {
							// 结果出来但是sp值未出来
							// 浮动奖与胜分差无关
							isOpenAble = false;
							break;
						}
					}
					if (tempAward < 2)
						tempAward = 2d;// 不足两元补足两元
					resultAward += Double.valueOf(DF.format(tempAward));
					isBingo = true;
					if (bingoHashMap.get("单关") != null)
						bingoHashMap.put("单关:", bingoHashMap.get("单关") + 1);
					else
						bingoHashMap.put("单关:", 1l);
				} catch (NullPointerException e) {
					isOpenAble = false;
					break;// 空指针异常，说明选择的赛事中有赛事还未结果
				}
			}
			// 计算奖金
			bingoPretaxTotal += Double.valueOf(DF.format(resultAward * ticketModel.getMultiple()));
			if (resultAward > 10000) {// 税后
				resultAward *= 0.8d;
			}
			bingoPosttaxTotal += Double.valueOf(DF.format(resultAward * ticketModel.getMultiple()));
			prizeHashMap.put("单关:", bingoPosttaxTotal);// 单关的奖金加入
			
			ticketCountPrize.append("×"+ticketModel.getMultiple()+"=" + bingoPosttaxTotal);
//			//加奖操作 单票奖金大于或等于400 
//			if(bingoPretaxTotal>=400){
//				bingoHashMap.put("加奖", 1l);
//				prizeHashMap.put("加奖", 50d);
//				bingoPosttaxTotal+=50d;
//				bingoPretaxTotal+=50d;
//			}
		} else {// 处理过关
			boolean hit = true;// 先定义好，假设全部命中
			double resultAward = 1d;
			int cancelSum = 0;
			//先判断赛事是否有赛果
			for (X x : matchItems) {
				try {
					String tempKey = x.getIntTime() + "_" + x.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					if (!tempHomeScore.equals("0")) { //赛事取消
						
					}
				} catch (NullPointerException e) {
					hit = false;
					isOpenAble = false;
					break;
				}
			}
			int sum=0;
			for (X x : matchItems) {
				sum++;
				try {
					Map<String, String> matchResultMap = new HashMap<String, String>();
					String tempKey = x.getIntTime() + "_" + x.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					String tempGuestScore = openResultMap.get(CommonUtils.guestScoreKey + tempKey);
					// TODO:赛事取消的情况
					if (!tempHomeScore.equals("0")) {
						matchResultMap.put(CommonUtils.homeScoreKey, tempHomeScore);
						matchResultMap.put(CommonUtils.guestScoreKey, tempGuestScore);
						OptionItem item = getPassOptionItem(type, x, matchResultMap);
						SportteryOption wonItem = x.won(item);
						hit = wonItem == null ? false : true;
						if (hit == false)
							break;
						Double tempAward = Double.valueOf(wonItem.getAward());
						resultAward *= tempAward;
						ticketCountPrize.append(tempAward +" × ");
					} else {
						resultAward *= x.getOptions().size();
						cancelSum++;
						if(sum==1){
							ticketCountPrize.append("2×"+x.getOptions().size()+"×");
						}else{
							ticketCountPrize.append("2×"+x.getOptions().size()+"×");
						}
					}
				} catch (NullPointerException e) {
					hit = false;
					isOpenAble = false;
					break;
				}
			}
			if (hit) {
				resultAward *= 2;// 乘以2
				Double tempAward = Double.valueOf(DF.format(resultAward));
				int passCount = matchItems.size() - cancelSum;
				if (tempAward > 200000) {// 如果金额超过20万:一般都很难所以这里以金额做判断
					if (passCount <= 3) {
						tempAward = 200000d;
					} else if (4 <= passCount && passCount <= 5) {
						tempAward = tempAward > 500000 ? 500000d : tempAward;
					} else if (6 <= passCount) {
						tempAward = tempAward > 1000000 ? 1000000d : tempAward;
					}
				}
				// 税前奖金
				bingoPretaxTotal += Double.valueOf(tempAward * ticketModel.getMultiple());
				// 税后
				if (tempAward > 10000 && passCount > 0)// 所有的赛事都取消不算税
					tempAward *= 0.8;
				bingoPosttaxTotal += Double.valueOf(DF.format(tempAward * ticketModel.getMultiple()));
				bingoHashMap.put(matchItems.size() + "串1", 1l);
				isBingo = true;
				prizeHashMap.put(matchItems.size() + "串1", bingoPosttaxTotal);// 过关的奖金加入
				
				ticketCountPrize.append("2×"+ticketModel.getMultiple()+"=" + bingoPosttaxTotal);
//				//加奖操作 单票奖金大于或等于400 
//				if(bingoPretaxTotal>=400){
//					bingoHashMap.put("加奖", 1l);
//					prizeHashMap.put("加奖", 50d);
//					bingoPosttaxTotal+=50d;
//					bingoPretaxTotal+=50d;
//				
//				}
			}
		}
	}

	public String getBingoContent() {
		bingoContent = new StringBuffer();
		for (String key : bingoHashMap.keySet()) {
			bingoContent.append(key).append("^" + bingoHashMap.get(key)).append("^").append(Double.valueOf(DF.format(prizeHashMap.get(key)))).append(
					"元#");
		}
		return bingoContent.toString();
	}
	
	public String getTicketCountPrize() {
		return ticketCountPrize.toString();
	}

	public HashMap<String, Long> getBingoHashMap() {
		return this.bingoHashMap;
	}

	public double getBingoPosttaxTotal() {
		return bingoPosttaxTotal;
	}

	public double getBingoPretaxTotal() {
		return bingoPretaxTotal;
	}

	public boolean isBingo() {
		return isBingo;
	}

	/**
	 * @return the isOpenAble
	 */
	public boolean isOpenAble() {
		return isOpenAble;
	}

	/**
	 * @param isOpenAble
	 *            the isOpenAble to set
	 */
	public void setOpenAble(boolean isOpenAble) {
		this.isOpenAble = isOpenAble;
	}

	// 复制对象
	public BingoCheck clone() throws CloneNotSupportedException {
		return (BingoCheck) super.clone();
	}

	public abstract Class<T> getTClass();

	public abstract Class<X> getXClass();

	public abstract OptionItem getPassOptionItem(String lotteryType, X matchItem, Map<String, String> resultMap);
	
}
