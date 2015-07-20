package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案出票状态
 * @author jack
 *
 */
public enum SchemePrintState {
	
	PRINT("委托中"),
	
	SUCCESS("出票成功"),
	
	FAILED("出票失败"),
	
	UNPRINT("未出票");
	
	/** 状态名称 */
	private final String stateName;

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	
	private SchemePrintState(String stateName) {
		this.stateName = stateName;
	}
	
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
