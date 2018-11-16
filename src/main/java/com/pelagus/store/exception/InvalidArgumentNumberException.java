package com.pelagus.store.exception;


public class InvalidArgumentNumberException extends Exception{

	private int row;
	private int expected;
	private int currentNumberOfArguments;
	private String type;
	private String message;
	
	public InvalidArgumentNumberException(int row, int expected,
			int currentNumberOfArguments, String type, String message) {
		super();
		this.row = row;
		this.expected = expected;
		this.currentNumberOfArguments = currentNumberOfArguments;
		this.type = type;
		this.message = message;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getExpected() {
		return expected;
	}

	public void setExpected(int expected) {
		this.expected = expected;
	}

	public int getCurrentNumberOfArguments() {
		return currentNumberOfArguments;
	}

	public void setCurrentNumberOfArguments(int currentNumberOfArguments) {
		this.currentNumberOfArguments = currentNumberOfArguments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
