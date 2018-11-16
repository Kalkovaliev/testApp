package com.pelagusit.store.domain;

public class UploadError {
	
	String Attribute;
	String errorName;
	int lineNumber;
	
	
	public String getAttribute() {
		return Attribute;
	}
	public void setAttribute(String attribute) {
		Attribute = attribute;
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	

}
