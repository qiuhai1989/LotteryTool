package com.yuncai.modules.lottery.factorys.checkBingo.handle;

public class SFCBingoResult {
	int number;
	int[] bingoCount = new int[number];
	int[] noBingoCount = new int[number];
	int totalBingoCount;
	public int[] getBingoCount() {
		return bingoCount;
	}

	public void setBingoCount(int[] bingoCount) {
		this.bingoCount = bingoCount;
	}

	public int[] getNoBingoCount() {
		return noBingoCount;
	}

	public void setNoBingoCount(int[] noBingoCount) {
		this.noBingoCount = noBingoCount;
	}

	public int getBingoTotalCount() {
		int ret = 1;
		for (int i = 0; i < bingoCount.length; i++) {
			ret *= bingoCount[i];
		}
		return ret;
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
	public int getBingoNums() {
		int ret = 0;
		for (int i = 0; i < bingoCount.length; i++) {
			ret += bingoCount[i];
		}
		return ret;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTotalBingoCount() {
		return totalBingoCount;
	}

	public void setTotalBingoCount(int totalBingoCount) {
		this.totalBingoCount = totalBingoCount;
	}


}
