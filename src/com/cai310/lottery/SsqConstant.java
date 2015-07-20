package com.cai310.lottery;

public final class SsqConstant {
	public static final String KEY = "ssq";
	/** 复式内容正则表达式 */
	public static final String COMPOUND_REGEX = "^\\s*(\\d{1,5}):(?:(\\d{2}(?:,\\d{2}){0,4});)?(\\d{2}(?:,\\d{2}){0,32})\\|(\\d{2}(?:,\\d{2}){0,15})\\s*$";

	/** 单式内容正则表达式 */
	public static final String SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+){6})(\\d{1,2})\\s*$";

	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";
}
