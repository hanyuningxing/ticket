package com.cai310.lottery;

public class AhKuai3Constant {
	public static final String KEY="ahkuai3";
	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";
	/** 胆拖码之间的分隔符 */
	public static final String SEPARATOR_DAN_FOR_NUMBER = ";";
	
	/**二同号单选分隔符*/
	public static final String SEPARATOR_DISTINT_FOR_NUMBER="#";
	
	/** 复式内容正则表达式 */
	public static final String GENERAL_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,3}(,\\d{1,3}){0,15})\\s*$";
	
	/**含有胆码的格式**/
	public static final String GENERAL_COMPOUND_DAN_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,10}"+SEPARATOR_DAN_FOR_NUMBER+"\\d{1,2}(,\\d{1,2}){0,10})\\s*$";
	
	/**二同号单选内容正则表达式 */
	public static final String GENERAL_TWODX_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,10}"+SEPARATOR_DISTINT_FOR_NUMBER+"\\d{1,2}(,\\d{1,2}){0,10})\\s*$";
	
	/** 二不同号内容正则表达式 */
	public static final String CHOOSE2_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){1})\\s*$";
	
	/** 三不同号内容正则表达式 */
	public static final String CHOOSE3_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){2})\\s*$";
	
	/**任意选号*/
}
