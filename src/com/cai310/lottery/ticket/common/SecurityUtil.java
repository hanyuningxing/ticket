package com.cai310.lottery.ticket.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.cai310.utils.MD5;


/**
 * 
 * @author luoweifeng
 *
 */
public class SecurityUtil {
	public static String MD5Old(String str) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			try {
				for (byte b : md.digest(str.getBytes("UTF-8"))) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			} catch (UnsupportedEncodingException e) {
				for (byte b : md.digest(str.getBytes())) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			}
			return result.toString();
		} catch (java.security.NoSuchAlgorithmException ex) {
			System.err.println("MD5 does not appear to be supported" + ex);
			return "";
		}
	}
	
	
	public final static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * add by peter
	 * @param s
	 * @return
	 */
	public final static String md5(byte[] block) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = block;
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	
	
	public static void main(String agrv[]){
		//46277dec51f0151bee39f2aac72f27ef
		String message = "220110224123456<body><bonusQuery><issue gameName=\"SDQYH\" number=\"\"/></bonusQuery></body>";
		try {
			System.out.println(SecurityUtil.md5(new String(message.getBytes(),"GBK")));
			System.out.println(SecurityUtil.md5(message));
			System.out.println(MD5.md5(message));
		} catch (UnsupportedEncodingException e) {
			
		}
	}

}
