package com.cai310.lottery;

public class SdEl11to5Constant {
	public static final String KEY = "sdel11to5";
	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";
	/** 胆拖码之间的分隔符 */
	public static final String SEPARATOR_DAN_FOR_NUMBER = ";";
	/** 复式：直选区间之间的分隔符 */
	public static final String SEPARATOR_FOR_ = "-";

	/** 除选二直选，前三直选外复式内容正则表达式 */
	public static final String GENERAL_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,10})\\s*$";
	/**含有胆码的格式**/
	public static final String GENERAL_COMPOUND_DAN_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,10}"+SEPARATOR_DAN_FOR_NUMBER+"\\d{1,2}(,\\d{1,2}){0,10})\\s*$";
	/** 前二直选复式内容正则表达式 */
	public static final String DIRECT2_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,10}(-\\d{1,2}(,\\d{1,2}){0,10}){1})\\s*$";
	/** 前三直选复式内容正则表达式 */
	public static final String DIRECT3_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,10}(-\\d{1,2}(,\\d{1,2}){0,10}){2})\\s*$";

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
	/** 选6内容正则表达式 */
	public static final String CHOOSE6_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){5})\\s*$";
	/** 选7内容正则表达式 */
	public static final String CHOOSE7_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){6})\\s*$";
	/** 选8内容正则表达式 */
	public static final String CHOOSE8_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){7})\\s*$";
}
