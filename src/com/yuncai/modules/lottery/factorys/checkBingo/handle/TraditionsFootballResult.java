package com.yuncai.modules.lottery.factorys.checkBingo.handle;

public class TraditionsFootballResult {
	private int[] bingoCount;
	private int totalBingoCount;
	public int[] getBingoCount() {
		return bingoCount;
	}
	public void setBingoCount(int[] bingoCount) {
		this.bingoCount = bingoCount;
	}
	public int getTotalBingoCount() {
		return totalBingoCount;
	}
	public void setTotalBingoCount(int totalBingoCount) {
		this.totalBingoCount = totalBingoCount;
	}
	//获取命中场次个数
	public int getBingoMatchCount(){
		int ret = 0;
		for(int i=0;i<bingoCount.length;i++){
			if(bingoCount[i]>0){
				ret ++;
			}
		}
		return ret;
	}

}
