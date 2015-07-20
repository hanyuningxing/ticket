package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 交易类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum TransactionType {
	/** 彩票方案 */
	SCHEME,

	/** 彩票追号 */
	CHASE;

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
