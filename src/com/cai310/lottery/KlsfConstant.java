package com.cai310.lottery;

public class KlsfConstant {
	public static final String KEY = "klsf";
	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";
	/** 复式：直选区间之间的分隔符 */
	public static final String SEPARATOR_FOR_ = "-";

	/** 除选二直选，前三直选外复式内容正则表达式 */
	public static final String GENERAL_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,19})\\s*$";
	/** 选二直选复式内容正则表达式 */
	public static final String DIRECT2_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,19}(-\\d{1,2}(,\\d{1,2}){0,19}){1})\\s*$";
	/** 前三直选复式内容正则表达式 */
	public static final String DIRECT3_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,19}(-\\d{1,2}(,\\d{1,2}){0,19}){2})\\s*$";

	/** 选1内容正则表达式 */
	public static final String CHOOSE1_SINGLE_REGEX = "^\\s*(\\d{1,2})\\s*$";
	/** 选2内容正则表达式 */
	public static final String CHOOSE2_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){1})\\s*$";
	/** 选3内容正则表达式 */
	public static final String CHOOSE3_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){2})\\s*$";
	/** 选4内容正则表达式 */
	public static final String CHOOSE4_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){3})\\s*$";
	/** 选5内容正则表达式 */
	public static final String CHOOSE5_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){4})\\s*$";
}
