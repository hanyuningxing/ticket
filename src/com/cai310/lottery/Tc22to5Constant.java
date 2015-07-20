package com.cai310.lottery;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Tc22to5Constant {
	public static final String KEY = "tc22to5";
	/** 复式内容正则表达式 */
	public static final String COMPOUND_REGEX = "^\\s*(\\d{1,5}):(?:(\\d{2}(?:,\\d{2}){0,3});)?(\\d{2}(?:,\\d{2}){0,21})\\s*$";

	/** 单式内容正则表达式 */
	public static final String SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){4})\\s*$";

	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";
	
	public static void  main(String args[]){
		Pattern patt = Pattern.compile(Tc22to5Constant.COMPOUND_REGEX);
		Matcher matcher = patt.matcher("11:03,01,02,03,04,05,19,22");
		if (!matcher.matches())
			System.out.print("方案内容格式不正确.");
		patt = Pattern.compile(Tc22to5Constant.SINGLE_REGEX);
		matcher = patt.matcher("01,02,03,04,05");
		if (!matcher.matches())
			System.out.print("a方案内容格式不正确.");
	}
}
