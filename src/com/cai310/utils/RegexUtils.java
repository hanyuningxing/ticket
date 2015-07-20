package com.cai310.utils;

public class RegexUtils {
	/** 由汉字组成的字符串 */
	public static final String Chinese = "^[\u4e00-\u9fa5]+$";

	/** 由数字、26个英文字母或中文组成的字符串 */
	public static final String LetterAndNumberAndChinese = "^[A-Za-z0-9\u4e00-\u9fa5]+$";

	/** 由数字、26个英文字母或者下划线组成的字符串 */
	public static final String LetterAndNumberAndUnderline = "^\\w+$";

	/** 由数字和26个英文字母组成的字符串 */
	public static final String LetterAndNumber = "^[A-Za-z0-9]+$";

	/** 由数字和26个小写英文字母组成的字符串 */
	public static final String LowerLetterAndNumber = "^[a-z0-9]+$";

	/** 由数字和26个大写英文字母组成的字符串 */
	public static final String UpperLetterAndNumber = "^[A-Z0-9]+$";

	/** 由26个英文字母组成的字符串 */
	public static final String Letter = "^[A-Za-z]+$";

	/** 由26个英文字母的小写组成的字符串 */
	public static final String LowerLetter = "^[a-z]+$";

	/** 由26个英文字母的大写组成的字符串 */
	public static final String UpperLetter = "^[A-Z]+$";

	/** 由数字的字符串 */
	public static final String Number = "^[0-9]+\\s*$";

	/** email地址 */
	public static final String Email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

	/** url */
	public static final String Url = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";

	/** 正整数 */
	public static final String Gt0Integer = "^[0-9]*[1-9][0-9]*$";

	/** 非负数（正数 + 0） */
	public static final String Ge0Double = "^\\d+(\\.\\d+)?$";

	/** 非数字 */
	public static final String UnNumber = "[^0-9]+";

	/** 身份证 */
	public static final String IdCard = "^(([0-9]{14}[xX0-9]{1})|([0-9]{17}[xX0-9]{1}))$";

}
