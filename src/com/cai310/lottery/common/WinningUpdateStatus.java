package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案中奖更新状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum WinningUpdateStatus {
	/** 未更新中奖 */
	NONE("未更新中奖"),

	/** 已更新中奖 */
	WINNING_UPDATED("已更新中奖"),

	/** 已更新奖金 */
	PRICE_UPDATED("已更新奖金");

	private final String statusName;

	/**
	 * @param statusName {@link #statusName}
	 */
	private WinningUpdateStatus(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return {@link #statusName}
	 */
	public String getStatusName() {
		return statusName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
