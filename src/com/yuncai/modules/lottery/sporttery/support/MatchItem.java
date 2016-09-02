package com.yuncai.modules.lottery.sporttery.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.FtSpfAward;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemSPF;

public class MatchItem implements Serializable, SelectedCount{


	private static final long serialVersionUID = 1L;

	/** 整数日期 */
	private Integer intTime;

	/** 一天赛事里的场次序号 */
	private Integer lineId;

	/** 选择的内容 */
	private List<SportteryOption> options = new ArrayList<SportteryOption>();

	private boolean shedan;
	
	private Integer matchId;

	public MatchItem cloneItem() {
		MatchItem item = new MatchItem();
		item.setIntTime(this.getIntTime());
		item.setLineId(this.getLineId());
		List<SportteryOption> tempOptionList = this.getOptions();
		List<SportteryOption> optionList = new ArrayList<SportteryOption>();
		for (SportteryOption option : tempOptionList) {
			optionList.add(option);
		}
		item.setOptions(optionList);
		item.setShedan(this.isShedan());
		return item;
	}
	public List<MatchItem> toDsItems(){
		List<MatchItem> result=new ArrayList<MatchItem>();
		for(SportteryOption option:options){
			MatchItem tempItem=cloneItem();
			List<SportteryOption> options=new ArrayList<SportteryOption>();
			options.add(option);
			tempItem.setOptions(options);
			result.add(tempItem);
		}
		return result;
	}
	public static List<MatchItem> toItem(FtMatch match,FtSpfAward award,LotteryType lotteryType){
		List<MatchItem> result=new ArrayList<MatchItem>();
		List<SportteryOption> options=new ArrayList<SportteryOption>();
		if(lotteryType.getValue()==LotteryType.JC_ZQ_SPF.getValue()){
			String[] optionsValues=OptionItemSPF.getAllValue();
			for(String optionValue:optionsValues){
				SportteryOption option=new SportteryOption();
				option.setValue(optionValue);
				option.setAward(award.getAward(optionValue));
				options.add(option);
			}
		}else{
			throw new RuntimeException("暂不支持非胜平负玩法");
		}
		for(SportteryOption option:options){
			MatchItem item= new MatchItem();
			item.setIntTime(match.getIntTime());
			item.setLineId(Integer.parseInt(match.getLineId()));
			List<SportteryOption> tempOptions=new ArrayList<SportteryOption>();
			tempOptions.add(option);
			item.setOptions(tempOptions);
			result.add(item);
		}
		return result;
	}
	/**
	 * @return the {@link #intTime}
	 */
	public Integer getIntTime() {
		return intTime;
	}

	/**
	 * @param intTime
	 *            the {@link #intTime} to set
	 */
	public void setIntTime(Integer intTime) {
		this.intTime = intTime;
	}

	/**
	 * @return the {@link #lineId}
	 */
	public Integer getLineId() {
		return lineId;
	}

	/**
	 * @param lineId
	 *            the {@link #lineId} to set
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the {@link #options}
	 */
	public List<SportteryOption> getOptions() {
		return options;
	}

	/**
	 * @param options
	 *            the {@link #options} to set
	 */
	public void setOptions(List<SportteryOption> options) {
		this.options = options;
	}

	/**
	 * @return the {@link #serialVersionUID}
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int selectedCount() {
		if (options != null)
			return options.size();
		else
			return 0;
	}

	public boolean selected(String value) {
		value = value.trim();
		for (SportteryOption op : this.options) {
			if (op.getValue().equals(value))
				return true;
		}
		return false;
	}

	public boolean selected(OptionItem opItem) {
		for (SportteryOption op : this.options) {
			if (op.getValue().equals(opItem.getValue()))
				return true;
		}
		return false;
	}

	public SportteryOption itemToOption(OptionItem rs) {
		for (SportteryOption op : this.options) {
			if (op!=null&&op.getValue().equals(rs.getValue()))
				return op;
		}
		return null;
	}

	public SportteryOption indexToOption(int optionIndex) {
		return this.options.get(optionIndex);
	}

	public SportteryOption won(OptionItem rs) {
		for (SportteryOption op : this.options) {
			if (op.getValue().equals(rs.getValue()))
				return op;
		}
		return null;
	}

	
	/**
	 * 混串
	 * @param hunTou
	 * @param rs
	 * @return
	 */
	public SportteryOption wons(SportteryOption hunTou,OptionItem rs) {
		if (hunTou.getValue().equals(rs.getValue()))
			return hunTou;
		
		return null;
	}

	public String genMatchKey() {
		return CommonUtils.genMatchKey(this.getIntTime(), this.getLineId());
	}
	
	public String genMatchShowKey() {
		return CommonUtils.getShowMatchKey(this.getIntTime(), this.getLineId());
	}
	
	public String genMatchLined(){
		return CommonUtils.getShowMatchLined(this.getLineId());
	}
	
	public boolean isShedan() {
		return shedan;
	}

	public void setShedan(boolean shedan) {
		this.shedan = shedan;
	}
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

}
