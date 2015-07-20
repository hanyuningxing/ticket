package com.cai310.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.mortbay.log.Log;



/**
 * 工具箱
 * 
 * @author Administrator
 * @version cai3101.0 2010-10-15
 */
public class ToolKit {
	private static SimpleDateFormat SDF_yyyyMMdd = new SimpleDateFormat(
			"yyyyMMdd");
	private static SimpleDateFormat SDF_STANDARD = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat SDF_yyyyMMddHHmmss = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static DecimalFormat SDF_YUAN = new DecimalFormat("0.00");
	
	// 十六进制字符
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param origin
	 * @return
	 */
	public static String getMD5String(String origin) {
		String md5String = null;

		if (origin != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] result = md.digest(origin.getBytes());
				char[] target = new char[result.length * 2];
				int i = 0;

				// 把字节类型转换为十六进制
				for (int j = 0; j < result.length; j++) {
					target[i++] = HEX_DIGITS[result[j] >>> 4 & 0xf];
					target[i++] = HEX_DIGITS[result[j] & 0xf];
				}

				md5String = new String(target);
			} catch (NoSuchAlgorithmException e) {
				Log.info(e.toString());
			} catch (Exception e) {
				Log.info(e.toString());
			}
		}

		return md5String;
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
	 * 把分转换为元
	 * 
	 * @param src
	 * @return
	 */
	public static String cent2Yuan(long src) {
		return cent2Yuan(Long.toString(src));
	}

	/**
	 * 把分转换为元
	 * 
	 * @param src
	 * @return
	 */
	public static String cent2Yuan(String src) {
		if (src == null || src.trim().equals("")) {
			return "0.00";
		}

		try {
			long temp = Long.parseLong(src.trim());
			return SDF_YUAN.format(temp / 100.00);
		} catch (Exception e) {
		}

		return "0.00";
	}

	/**
	 * 百分比
	 * 
	 * @param src
	 * @return
	 */
	public static String percentage(String src) {
		if (src == null || src.trim().equals("")) {
			return "0.00%";
		}

		try {
			long temp = Long.parseLong(src.trim());
			return SDF_YUAN.format(temp / 100.00) + "%";
		} catch (Exception e) {
		}

		return "0.00%";
	}

	/**
	 * 把分转换为元
	 * 
	 * @param price
	 * @return
	 */
	public static String getPrice(String price) {
		if (price != null && !price.equals("")) {
			float f = Float.parseFloat(price);
			if (f > 0) {
				price = (f / 100) + "";
			}
		} else {
			price = "0";
		}
		return price;
	}

	/**
	 * 把元转换为分
	 * 
	 * @return
	 */
	public static String yuanToCent(String price) {
		if (price != null && !price.equals("")) {
			float f = Float.parseFloat(price);
			if (f > 0) {
				price = (f * 100) + "";
				price = price.split("\\.")[0];
			}
		}
		return price;
	}

	/**
	 * 把元转换为分
	 * 
	 * @return
	 */
	public static String yuanToCent2(String price) {
		if (price != null && !price.equals("")) {
			float f = Float.parseFloat(price);
			if (f > 0) {
				DecimalFormat df = new DecimalFormat(".00");
				price = df.format(f).replaceAll("\\.", "");
			}
		}
		return price;
	}

	/**
	 * 把日期字符串格式为yyyy-MM-dd或yyyy-MM-dd HH:mm:ss的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String layoutStrToDate(String str) {
		StringBuffer sb = new StringBuffer("");
		if (str != null) {
			sb.append(str);
			if (str.length() == 8) {
				sb.insert(4, "-");
				sb.insert(7, "-");
			} else if (str.length() == 14) {
				sb.insert(4, "-");
				sb.insert(7, "-");
				sb.insert(10, " ");
				sb.insert(13, ":");
				sb.insert(16, ":");
			}
		}
		return sb.toString();
	}

	/**
	 * 当前日期格式化成字符串，例20090812
	 * 
	 * @return
	 */
	public static String getDate() {
		return getDate(new Date());
	}

	/**
	 * 日期格式化成字符串，例20090812
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return SDF_yyyyMMdd.format(date);
	}

	/**
	 * 日期时间格式化成标准字符串，一般用于显示。例2009-08-12 23:59:33
	 * 
	 * @param millisecond
	 *            毫秒
	 * @return
	 */
	public static String getStandard(long millisecond) {
		return getStandard(new Date(millisecond));
	}

	/**
	 * 日期时间格式化成标准字符串，一般用于显示。例2009-08-12 23:59:33
	 * 
	 * @param date
	 * @return
	 */
	public static String getStandard(Date date) {
		return SDF_STANDARD.format(date);
	}

	/**
	 * 当前日期时间格式化成字符串，例20090812235933
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return getDateTime(new Date());
	}

	/**
	 * 日期时间格式化成字符串，例20090812235933
	 * 
	 * 
	 * @param milliseconde
	 *            毫秒
	 * @return
	 */
	public static String getDateTime(long milliseconde) {
		return getDateTime(new Date(milliseconde));
	}

	/**
	 * 日期时间格式化成字符串，例20090812235933
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return SDF_yyyyMMddHHmmss.format(date);
	}

	/**
	 * 解析字符串为日期类型。格式只支持YYYYMMDD或YYYYMMDDhhmmss 例：20101022 例：20101022220033
	 * 
	 * @param dateString
	 *            格式YYYYMMDD或YYYYMMDDhhmmss
	 * @return
	 * 
	 */
	public static Date parseDate(String dateString) {
		if (dateString.length() == 8) {
			try {
				return SDF_yyyyMMdd.parse(dateString);
			} catch (Exception e) {
				return null;
			}
		} else if (dateString.length() == 14) {
			try {
				return SDF_yyyyMMddHHmmss.parse(dateString);
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 得到两个时间的差距。不足一个单位的舍去。 返回大于0表示第二个时间比第一个大。
	 * 
	 * @param first
	 *            第一个时间
	 * @param second
	 *            第二个时间
	 * @param type
	 *            类型：日、时、分、秒。使用Calendar的定义
	 * @return
	 * 
	 */
	public static long getTimeDiff(Date first, Date second, int type) {
		long result = 0;
		long lFirst = first.getTime();
		long lSecond = second.getTime();

		long diffMillisecond = lSecond - lFirst;

		switch (type) {
		// case Calendar.YEAR:
		//
		// break;
		// case Calendar.MONTH:
		//
		// break;
		case Calendar.DATE:
			result = diffMillisecond / (1000 * 3600 * 24);
			break;
		case Calendar.HOUR_OF_DAY:
		case Calendar.HOUR:

			result = diffMillisecond / (1000 * 3600);

			break;
		case Calendar.MINUTE:
			result = diffMillisecond / (1000 * 60);
			break;
		case Calendar.SECOND:
			result = diffMillisecond / 1000;
			break;
		default:
			throw new UnsupportedOperationException("不支持此类型：" + type);

		}

		return result;

	}

	/**
	 * 将一个字符串按分隔符分成多个子串。空字符串不管在哪个位置都视为一个字串。 (与JDK的String.split()不同)
	 * 
	 * @param src
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String src, String delimiter) {
		if (src == null || delimiter == null) {
			return null;
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
	 * Map转换成字符串，主要用于打印调试信息
	 * 
	 * @param map
	 * @param head
	 *            输出的头
	 * @param entryPrefix
	 *            每一项输出的前缀
	 * @param foot
	 *            输出的脚
	 * @param isOneItemPl
	 *            是否每行一项
	 * @param kvSep
	 *            Key和Value的分隔符
	 * @return
	 */
	public static String map2String(Map map, String head, String entryPrefix,
			String foot, boolean isOneItemPl, String kvSep) {

		if (map == null) {
			return null;
		}
		String lineSeparator = (String) System.getProperty("line.separator");
		StringBuffer buff = new StringBuffer();
		if (head != null && !head.equals("")) {
			buff.append(head);

			if (isOneItemPl) {
				buff.append(lineSeparator);
			}
		}

		if (entryPrefix == null) {
			entryPrefix = "";
		}
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			buff.append(entryPrefix).append(entry.getKey()).append(kvSep)
					.append(entry.getValue());

			if (isOneItemPl) {
				buff.append(lineSeparator);
			}
		}

		if (foot != null && !foot.equals("")) {
			buff.append(foot);
			if (isOneItemPl) {
				buff.append(lineSeparator);
			}
		}
		return buff.toString();
	}

	/**
	 * 算出MD5消息摘要
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(byte[] source) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(source);
			byte[] temp = messageDigest.digest();
			String result = bytes2Hex(temp).toUpperCase();
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean verifyMd5(byte[] source, String md5String) {
		boolean verifiedResult = false;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(source);
			byte[] temp = messageDigest.digest();
			String result = bytes2Hex(temp).toUpperCase();
			if (md5String.equalsIgnoreCase(result)) {
				verifiedResult = true;
			}
		} catch (Exception e) {
		}
		return verifiedResult;
	}

	/**
	 * 字节数组转换成16进制的字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex(byte[] bytes) {
		return bytes2Hex(bytes, 0, bytes.length);
	}

	public static String bytes2Hex(byte[] src, int offset, int length) {
		StringBuffer strbuff = new StringBuffer();

		for (int i = offset; i < length; i++) {
			String temp = Integer.toHexString(src[i]).toUpperCase();

			switch (temp.length()) {
			case 0:
			case 1:
				temp = "0" + temp;
				break;
			default:
				temp = temp.substring(temp.length() - 2);
				break;
			}

			strbuff.append(temp);
		}

		return strbuff.toString();
	}

	public static boolean isPhoneNo(String src) {
		Pattern pattern = Pattern.compile("^[0-9]{11}$");
		Matcher matcher = pattern.matcher(src);

		return matcher.matches();
	}

	public static boolean isEmail(String src) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,4}){1,2})$");
		Matcher matcher = pattern.matcher(src);

		return matcher.matches();
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
	 * 合并多个字节数组
	 * 
	 * @param datas
	 * @return
	 * 
	 */
	public static byte[] concat(byte[]... datas) {

		int length = 0;
		for (byte[] bs : datas) {
			length += bs.length;
		}

		byte[] result = new byte[length];

		int offset = 0;
		for (byte[] bs : datas) {
			System.arraycopy(bs, 0, result, offset, bs.length);
			offset += bs.length;
		}

		return result;
	}

	public static String getContentPath() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
				 
		return path;
	}

	public static void main(String[] args) {
		System.out.println(cent2Yuan(2));
	}
}
