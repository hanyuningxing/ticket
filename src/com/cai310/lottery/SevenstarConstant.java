package com.cai310.lottery;

public class SevenstarConstant {
	public static final String KEY = "sevenstar";

	/** 复式内容正则表达式 ((\\d{2},?)?-)?(\\d{2},)? */
	public static final String COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){6})\\s*$";

	/** 单式内容正则表达式 */
	public static final String SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){6})\\s*$";

	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";

	/** 复式：直选区间之间的分隔符 */
	public static final String SEPARATOR_FOR_ = "-";
}
