package com.cai310.lottery.exception;

public class FetchParseException extends Exception {
	private static final long serialVersionUID = 529286980571990612L;

	public FetchParseException(String msg) {
		super(msg);
	}

	public FetchParseException(String msg, Throwable t) {
		super(msg, t);
	}
}
