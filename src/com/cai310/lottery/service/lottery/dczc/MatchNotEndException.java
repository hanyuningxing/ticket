package com.cai310.lottery.service.lottery.dczc;

import com.cai310.lottery.service.ServiceException;

/**
 * 比赛未结束异常
 * 
 */
public class MatchNotEndException extends ServiceException {
	private static final long serialVersionUID = -8409103006157679063L;

	public MatchNotEndException() {
		super();
	}

	public MatchNotEndException(String message) {
		super(message);
	}

	public MatchNotEndException(Throwable cause) {
		super(cause);
	}

	public MatchNotEndException(String message, Throwable cause) {
		super(message, cause);
	}
}
