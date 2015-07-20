package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 线程状态
 */
public enum TaskState {
	RUN("正常"),
	STOP("停止"),
	RESET("重置"),
	EXCEPTION("报错");
	

	/**
	 * 状态描述
	 */
	private String stateName;

	private TaskState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * 获取状态值
	 */
	public byte getState() {
		return (byte) this.ordinal();
	}

	/**
	 * 获取状态描述
	 */
	public String getStateName() {
		return stateName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
