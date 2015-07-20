package com.cai310.lottery.common;

import java.security.MessageDigest;

public final class MD5 {
	public static String convert(String s) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] bytes = s.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			bytes = md.digest();
			int j = bytes.length;
			char[] chars = new char[j * 2];
			int k = 0;
			for (int i = 0; i < bytes.length; ++i) {
				byte b = bytes[i];
				chars[(k++)] = hexChars[(b >>> 4 & 0xF)];
				chars[(k++)] = hexChars[(b & 0xF)];
			}
			return new String(chars);
		} catch (Exception e) {
		}
		return null;
	}
}