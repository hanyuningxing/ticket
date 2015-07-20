package com.cai310.lottery.service;

/**
 * Service层公用的Exception.
 * 
 * 继承自RuntimeException,从被Spring声明式事务管理的函数中抛出时会触发事务回滚.
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
