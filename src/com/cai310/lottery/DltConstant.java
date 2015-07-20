package com.cai310.lottery;

public final class DltConstant {
	public static final String KEY = "dlt";

	public static final String COMPOUND_REGEX = "^\\s*(\\d{1,5}):(?:(\\d{2}(?:,\\d{2}){0,3});)?(\\d{2}(?:,\\d{2}){0,34})\\|(?:(\\d{2});)?(\\d{2}(?:,\\d{2}){0,11})\\s*$";

	/**
	 * 常规复式内容正则表达式 1,2,3;4,5|1;2,3
	 * 
	 */
	public static final String GENERAL_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(?:(\\d{2}(?:,\\d{2}){0,3});)?(\\d{2}(?:,\\d{2}){0,34})\\|(?:(\\d{2});)?(\\d{2}(?:,\\d{2}){0,11})\\s*$";

	/**
	 * 12选2复式内容正则表达式 1_2,3
	 */
	public static final String SELECT12TO2_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(?:(\\d{2});)?(\\d{2}(?:,\\d{2}){0,11})\\s*$";

	/** 常规单式内容正则表达式 */
	public static final String GENERAL_SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+){5})((?:\\d{1,2}\\D+){1}\\d{1,2})\\s*$";

	/** 12选2单式内容正则表达式 */
	public static final String SELECT12TO2_SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+){1}\\d{1,2})\\s*$";

	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";

	/** 红球篮球之间的分隔符 */
	public static final String SEPARATOR_FOR_RED_BLUE = "-";

	/**
	 * 前区最大命中数
	 */
	public final static int RED_MAX_HITS = 5;

	/**
	 * 后区最大命中数
	 */
	public final static int BLUE_MAX_HITS = 2;
}
