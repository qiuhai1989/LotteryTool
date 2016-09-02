package com.yuncai.modules.lottery.sporttery.model;

import com.yuncai.core.tools.FileTools;
import com.yuncai.modules.lottery.WebConstants;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchItem;

public class SportteryFileUploadContentModel extends SportteryBetFilterContentModel<FootBallMatchItem>{
	private String filePath;
	private Boolean isWithBoutIndex;
	private String optionMapText;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getIsWithBoutIndex() {
		return isWithBoutIndex;
	}

	public void setIsWithBoutIndex(Boolean isWithBoutIndex) {
		this.isWithBoutIndex = isWithBoutIndex;
	}

	public String getOptionMapText() {
		return optionMapText;
	}

	public void setOptionMapText(String optionMapText) {
		this.optionMapText = optionMapText;
	}
	public String takeFormatBoutIndexs(){
		String formatBoutIndexs="";
		for(MatchItem item:this.getMatchItems()){
			
			formatBoutIndexs+=","+item.genMatchShowKey();
		}
		formatBoutIndexs=formatBoutIndexs.substring(1);
		return formatBoutIndexs;
	}
	public String toFormatContent(){
		String fileContent="";
		try {
			fileContent=FileTools.getFileContent(WebConstants.WEB_PATH+this.getFilePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String formatContent=fileContent+"~"+this.optionMapText;
		if(isWithBoutIndex!=null&&!isWithBoutIndex){
			formatContent+="~"+takeFormatBoutIndexs();	
		}
		return formatContent;
	}

}
