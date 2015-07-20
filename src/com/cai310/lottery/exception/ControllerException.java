package com.cai310.lottery.exception;

public class ControllerException extends RuntimeException {
	private static final long serialVersionUID = -2894971157798080669L;

	public ControllerException() {
		super();
	}

	public ControllerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ControllerException(String arg0) {
		super(arg0);
	}

	public ControllerException(Throwable arg0) {
		super(arg0);
	}
}
