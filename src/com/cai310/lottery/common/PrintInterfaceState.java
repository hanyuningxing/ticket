package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 接口表出票状态
 * @author 
 *
 */
public enum PrintInterfaceState {
	
	UNDISASSEMBLE("未拆票"),
	
	DISASSEMBLED("已拆票");
	
	/** 状态名称 */
	private final String stateName;

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	
	private PrintInterfaceState(String stateName) {
		this.stateName = stateName;
	}
	
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
