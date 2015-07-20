package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 线程名字
 * 
 */
public enum TaskType {
	/** 自动跟单 */
	AutoFollow("自动跟单 "),

	/** 北京单场自动保底 */
	AutoHandleTransaction("北京单场自动保底"),

	/** 高频彩 */
	Keno("高频彩"),

	/** 单场SP */
	FETCH_SP("抓取单场SP"),

	/** 同步出票 */
	Ticket("同步出票"),

	/** 持久化单场SP */
	SAVE_SP("持久化单场SP"),
	
	EL11to5("11选5"),
	
	SDEL11to5("山东11选5"),
	
	QYH("群英会"),
	
	KLSF("快乐十分"),
	
	SSC("时时彩"),
	
	SSL("时时乐"),
	
	DISASSEMBLE("拆票"),
	
	/** 竞彩足球自动保底 */
	JczqAutoHandleTransaction("竞彩足球自动保底"),
	
	/** 竞彩篮球球自动保底 */
	JclqAutoHandleTransaction("竞彩篮球自动保底"),
	
	GDEL11to5("广东11选5");

	/** 类型名称 */
	private final String typeName;

	/**
	 * @param shareName {@link #typeName}
	 */
	private TaskType(String typeName) {
		this.typeName = typeName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
}
