package com.yuncai.modules.lottery.action.system;

import com.yuncai.modules.lottery.model.Oracle.system.ErrorGrade;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorStatus;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import java.util.*;

public class SystemErrorSearch {
	private Integer id;
	private ErrorType type;
	// 彩种
	private LotteryType lotteryType;
	// 错误等级
	private ErrorGrade grade;
	// 错误状态
	private ErrorStatus status;
	private String keyWords;
	private String content;
	private Date createStartDate;
	private Date createEndDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(Date createStartDate) {
		this.createStartDate = createStartDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}
	public void setLotteryTypeByValue(Integer value){
		this.lotteryType=LotteryType.getItem(value);
	}
	public Integer getLotteryTypeByValue(){
		return this.lotteryType.getValue();
	}
	public ErrorGrade getGrade() {
		return grade;
	}

	public void setGrade(ErrorGrade grade) {
		this.grade = grade;
	}

	public ErrorStatus getStatus() {
		return status;
	}

	public void setStatus(ErrorStatus status) {
		this.status = status;
	}
	
	public void setStatusByValue(Integer value){
		this.status=ErrorStatus.getItem(value);
	}
	public Integer getStatusByValue(){
		return this.status.getValue();
	}
	public void setTypeByValue(Integer value){
		this.type=ErrorType.getItem(value);
	}
	public Integer getTypeByValue(){
		return this.type.getValue();
	}
	public void setGradeByValue(Integer value){
		this.grade=ErrorGrade.getItem(value);
	}
	public Integer getGradeByValue(){
		return this.grade.getValue();
	}

}
