package com.cai310.lottery;

public class SevenConstant {
	public static final String KEY = "seven";

	/** 复式内容正则表达式 1_2,3*/
	public static final String COMPOUND_REGEX = "^\\s*(\\d{1,5}):(?:(\\d{2}(?:,\\d{2}){0,5});)?(\\d{2}(?:,\\d{2}){0,29})\\s*$";

	/** 单式内容正则表达式 */
	public static final String SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){6})\\s*$";

	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";

	/** 复式：直选区间之间的分隔符 */
	public static final String SEPARATOR_FOR_ = "-";
	
	/**
	 * 最大命中数
	 */
	public final static int MAX_HITS = 7;
}
