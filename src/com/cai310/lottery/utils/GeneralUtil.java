package com.cai310.lottery.utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具 任何未分类的工具方法都放在这里
 * 
 * @author Peter
 * 
 */
public class GeneralUtil {
	private static Random random = new Random();

	/**
	 * 得到一个长度为length的随机数
	 * 
	 * @param maxLength
	 * @return
	 */
	public static long getRandom(long maxLength) {
		if (maxLength < 1) {
			return 0;
		}

		long temp = 1;
		for (int i = 0; i < maxLength; i++) {
			temp *= 10;
		}
		return getRandom(0, temp - 1);
	}

	/**
	 * 得到一个随机数，介乎beginNumber和endNumber之间
	 * 
	 * @param beginNumber
	 * @param endNumber
	 * @return
	 */
	public static long getRandom(long beginNumber, long endNumber) {

		double randomInDouble = random.nextDouble();
		long result = beginNumber
				+ (long) ((endNumber - beginNumber + 1) * randomInDouble);

		return result;
	}

	/**
	 * 得到长度为length的随机字符串，由数字和大写字母组成
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(long length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			long num = getRandom(0, 35);
			if (num < 10) {
				result += String.valueOf(num);
			} else {
				char ch = (char) (num - 10 + 65);
				result += ch;
			}
		}

		return result;

	}

	/**
	 * 得到长度为length的随机字符串，由数字和A～F大写字母组成.
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomHex(long length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			long num = getRandom(0, 15);
			if (num < 10) {
				result += String.valueOf(num);
			} else {
				char ch = (char) (num - 10 + 65);
				result += ch;
			}
		}

		return result;

	}

	/**
	 * 
	 * @param text
	 * @param endocing
	 * @return
	 * @deprecated
	 */
	public static String encode(String text, String endocing) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes(endocing);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}

	public static boolean equal(Object one, Object another) {
		if (one == null) {
			if (another == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (another == null) {
				return false;
			}
		}

		// if (one instanceof String || another instanceof String) {
		// return one.toString().equals(another.toString());
		// }

		return one.toString().equals(another.toString());
	}

	/**
	 * 
	 * @param codes
	 * @return
	 * @deprecated
	 */
	public static String decode(String codes) {
		ArrayList<String> code_list = new ArrayList<String>();
		StringBuffer period = new StringBuffer();
		codes = codes.substring(1);
		codes += '%';
		for (int i = 0; i < codes.length(); i++) { // 拆分字符
			if (codes.charAt(i) == '%' && period.length() != 0) {
				code_list.add(period.toString());
				period = new StringBuffer();
			} else {
				period.append(codes.charAt(i));
			}
		}
		StringBuffer result = new StringBuffer();
		for (String code : code_list) { // 逐个转换
			result.append((char) Integer.valueOf(code.substring(1), 16)
					.intValue());
		}
		return result.toString();
	}

	/**
	 * 是否为电话号码，包括固话、手机
	 * 
	 * @param src
	 * @return
	 * 
	 *         13912345678 02012345678 075512345678 07561234567
	 */
	public static boolean isPhoneNo(String src) {
		Pattern pattern = Pattern.compile("^[0-9]{11,12}$");
		Matcher matcher = pattern.matcher(src);

		return matcher.matches();
	}

	/**
	 * 是否为手机号码
	 * 
	 * @param src
	 * @return
	 * 
	 */
	public static boolean isMobilePhoneNo(String src) {
		Pattern pattern = Pattern.compile("^[1][3458][0-9]{9}$");
		Matcher matcher = pattern.matcher(src);

		return matcher.matches();
	}

	/**
	 * 是否为邮箱地址
	 * 
	 * @param src
	 * @return
	 * 
	 */
	public static boolean isEmail(String src) {
		Pattern pattern = Pattern
				.compile("^([\\.a-zA-Z0-9_-]){2,}@([a-zA-Z0-9_-]){2,}(\\.([a-zA-Z0-9]){2,}){1,4}$");
		Matcher matcher = pattern.matcher(src);

		return matcher.matches();
	}

	/**
	 * 是否为有效用户名
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isValidUserName(String src) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9\u4E00-\u9FBF_]{2,}$");
		Matcher matcher = pattern.matcher(src);

		return matcher.matches();
	}

	public static void main(String[] args) {
		// System.out.println(isMobilePhoneNo("13900000001"));
		//
		// for (int i = 0; i < 20; i++) {
		// System.out.println(getRandomHex(8));
		// }
//		System.out.println(isEmail("139000000001@189.info"));
System.out.println(isMobilePhoneNo("16909876543"));
		// for (int i = 0; i < 100; i++) {
		// System.out.println(getRandom(6));
		// }
		// System.out.println(equal("5", null));

	}

}
