package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 交易状态类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum TransactionState {
	/** 进行中 */
	UNDER_WAY("进行中"),

	/** 取消 */
	CANCEL("取消"),

	/** 成功 */
	SUCCESS("成功"),

	/** 退款 */
	REFUNDMENT("退款");

	/** 交易状态名称 */
	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private TransactionState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
