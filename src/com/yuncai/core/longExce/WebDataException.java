package com.yuncai.core.longExce;

public class WebDataException extends Exception {
	private static final long serialVersionUID = 1L;

	public WebDataException(String errorCode, String errorMesg) {
		super(errorCode + ":" + errorMesg);
		this.errorCode = errorCode;
		this.errorMesg = errorMesg;
	}

	public WebDataException(String errorMesg) {
		super(errorMesg);
	}

	private String errorCode;
	private String errorMesg;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMesg() {
		return errorMesg;
	}

	public void setErrorMesg(String errorMesg) {
		this.errorMesg = errorMesg;
	}

}
