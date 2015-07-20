package com.cai310.lottery.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 字符串工具
 * 
 * @author Peter
 * 
 */
public class StringUtil {

	/**
	 * 填充数字0
	 * 
	 * @param src
	 * @param targetLength
	 * @return
	 */
	public static String fill(long src, int targetLength) {
		return fill(String.valueOf(src), "0", targetLength, true);
	}

	/**
	 * 填充字符串。如果原字符串比目标长度长，则截去多出的部分。如果onTheLeft等于true 截去左边部分，否则截去右边部分。
	 * 注意填充物一般为单个字符，如果是多个字符，可能导致最后的结果不可用。
	 * 
	 * @param src
	 *            原字符串
	 * @param padding
	 *            填充物，一般是0、空格等
	 * @param targetLength
	 *            目标长度
	 * @param onTheLeft
	 *            是否左填充。
	 * @return
	 */
	public static String fill(String src, String padding, int targetLength,
			boolean onTheLeft) {

		if (src == null) {
			src = "";
		}

		while (src.length() < targetLength) {
			if (onTheLeft) {
				src = padding + src;
			} else {
				src = src + padding;
			}
		}

		if (src.length() > targetLength) {
			if (onTheLeft) {
				src = src.substring(src.length() - targetLength);
			} else {
				src = src.substring(0, targetLength);
			}
		}

		return src;
	}

	/**
	 * 若原字符串为Null则返回空字符串。
	 * 
	 * @param src
	 * @return
	 */
	public static String null2Empty(String src) {
		if (src == null) {
			return "";
		}
		return src;
	}

	/**
	 * 创建*Info类型的字符串，一般用于信息查询类接口的返回。
	 * 
	 * 例如，调用buildInfoString("#",aaa,bbb,null,ddd); 得到字符串：aaa#bbb##ddd
	 * 
	 * @param delimiter
	 * @param items
	 * @return
	 */
	public static String buildInfoString(String delimiter, Object... items) {
		StringBuffer buff = new StringBuffer();
		boolean firstItem = true;
		for (Object item : items) {
			if (firstItem) {
				firstItem = false;
			} else {
				buff.append(delimiter);
			}

			if (item == null) {
				buff.append("");
			} else {
				buff.append(item.toString());
			}

		}

		return buff.toString();
	}

	/**
	 * 创建*Info类型的字符串，一般用于信息查询类接口的返回。 常用于分页。
	 * 
	 * @param delimiter
	 * @param items
	 * @param startIndex
	 *            从items的哪一个索引项开始
	 * @param size
	 *            取出多少个。
	 * @return
	 * 
	 */
	public static String buildInfoString(String delimiter, List<String> items,
			int startIndex, int size) {
		if (items == null || items.size() == 0) {
			return "";
		}

		String result = "";
		boolean firstRecord = true;
		for (int i = startIndex; i < startIndex + size && i < items.size(); i++) {
			if (firstRecord) {
				firstRecord = false;
			} else {
				result += delimiter;
			}

			result += items.get(i);
		}

		return result;
	}

	/**
	 * 
	 * 将一个字符串按分隔符分成多个子串。 此方法用于代替Jdk的String.split()方法，不同的地方：
	 * (1)在此方法中，空字符串不管在哪个位置都视为一个有效字串。 (2)在此方法中，分隔符参数不是一个正则表达式。
	 * 
	 * @param src
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String src, String delimiter) {
		if (src == null || delimiter == null || src.trim().equals("")
				|| delimiter.trim().equals("")) {
			return new String[] { src };
		}

		ArrayList<String> list = new ArrayList<String>();

		int lengthOfDelimiter = delimiter.length();
		int pos = 0;
		while (true) {
			if (pos < src.length()) {

				int indexOfDelimiter = src.indexOf(delimiter, pos);
				if (indexOfDelimiter < 0) {
					list.add(src.substring(pos));
					break;
				} else {
					list.add(src.substring(pos, indexOfDelimiter));
					pos = indexOfDelimiter + lengthOfDelimiter;
				}
			} else if (pos == src.length()) {
				list.add("");
				break;
			} else {
				break;
			}
		}

		String[] result = new String[list.size()];
		list.toArray(result);
		return result;

	}

	/**
	 * 删除字符串中非数字部分。
	 * 
	 * @param src
	 * @param removeDot
	 *            是否删除小数点
	 * @return
	 * 
	 */
	public static String removeNotDigit(String src, boolean removeDot) {

		if (removeDot) {
			try {
				Integer.parseInt(src);
				return src;
			} catch (Exception e) {
			}
		} else {
			try {
				Double.parseDouble(src);
				return src;
			} catch (Exception e) {
			}
		}

		char[] chars = src.toCharArray();
		String result = "";
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= 48 && chars[i] <= 57 || !removeDot
					&& chars[i] == '.') {
				result += chars[i];
			}
		}
		return result;

	}

	/**
	 * 删除空白字符，包括空格、制表符、回车、换行、Ascci码0
	 * 
	 * @param src
	 * @return
	 * 
	 */
	public static String removeWhiteSpace(String src) {
		if (src == null) {
			return src;
		}

		char[] chars = src.toCharArray();
		String result = "";
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != 32 && chars[i] != 9 && chars[i] != 10
					&& chars[i] != 13 && chars[i] != 0) {
				result += chars[i];
			}
		}
		return result;
	}

	/**
	 * 字符串转换成Long型数字
	 * 
	 * @param src
	 * @return
	 * 
	 */
	public static long toLong(String src) {
		long dest = 0;
		if (src == null || src.trim().equals("")) {
			return 0;
		}

		try {
			dest = Long.parseLong(src.trim());
		} catch (Exception e) {
		}
		return dest;
	}

	/**
	 * 字符串转换成Int型数字
	 * 
	 * @param src
	 * @return
	 * 
	 */
	public static int toInt(String src) {
		int dest = 0;
		if (src == null || src.trim().equals("")) {
			return 0;
		}

		try {
			dest = Integer.parseInt(src.trim());
		} catch (Exception e) {
		}
		return dest;
	}

	/**
	 * 
	 * @param delimiter
	 * @param padding
	 * @param onTheLeft
	 * @param items
	 * @return
	 * @deprecated
	 */
	public static String fill(String delimiter, String padding,
			boolean onTheLeft, Object... items) {
		StringBuffer buff = new StringBuffer();
		boolean firstItem = true;
		for (Object item : items) {
			if (firstItem) {
				firstItem = false;
			} else {
				buff.append(delimiter);
			}

			if (onTheLeft) {
				buff.append(padding);
			}

			if (item == null) {
				buff.append("");
			} else {
				buff.append(item.toString());
			}

			if (!onTheLeft) {
				buff.append(padding);
			}
		}
		return buff.toString();
	}

	public static boolean isEmpty(String src) {
		String value = null2Empty(src);
		if ("".equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否全部非空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isAllNotEmpty(String... src) {
		for (int i = 0; i < src.length; i++) {
			String value = src[i];
			if (value == null || value.equals("")) {
				return false;
			}
		}

		return true;
	}

}
