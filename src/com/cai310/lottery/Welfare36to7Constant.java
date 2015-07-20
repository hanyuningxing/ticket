package com.cai310.lottery;

public class Welfare36to7Constant {
	public static final String KEY = "welfare36to7";

	/** 36选7复式内容正则表达式 */
	public static final String GENERAL_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,35})\\s*$";

	/** 好彩1季节复式内容正则表达式 */
	public static final String HAOCAI1_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,35})\\s*$";

	/** 好彩1季节复式内容正则表达式 */
	public static final String HAOCAI2_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,35})\\s*$";

	/** 好彩1季节复式内容正则表达式 */
	public static final String HAOCAI3_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,35})\\s*$";

	/** 好彩1生肖复式内容正则表达式 */
	public static final String ZODIAC_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,11})\\s*$";

	/** 好彩1季节复式内容正则表达式 */
	public static final String SEASON_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,3})\\s*$";

	/** 好彩1方位复式内容正则表达式 */
	public static final String AZIMUTH_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{2}(,\\d{2}){0,3})\\s*$";

	/** 36选7单式内容正则表达式 */
	public static final String GENERAL_SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+){6}\\d{1,2})\\s*$";

	/** 好彩1单式内容正则表达式 */
	public static final String HAOCAI1_SINGLE_REGEX = "^\\s*(\\d{1,2})\\s*$";

	/** 好彩2单式内容正则表达式 */
	public static final String HAOCAI2_SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+){1}\\d{1,2})\\s*$";

	/** 好彩3单式内容正则表达式 */
	public static final String HAOCAI3_SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+){2}\\d{1,2})\\s*$";

	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";
}
