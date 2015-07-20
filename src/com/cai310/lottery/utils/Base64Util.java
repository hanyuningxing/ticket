package com.cai310.lottery.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Peter
 * 
 */
public class Base64Util {
	private static BASE64Encoder base64Encoder = new BASE64Encoder();
	private static BASE64Decoder base64Decoder = new BASE64Decoder();

	public static String encode(byte[] original) {
		String encoded = base64Encoder.encode(original).trim();
		return encoded;
	}

	public static String encode(String original) {
		return encode(original, Charset.defaultCharset().name());
	}

	public static String encode(String original, String charset) {
		String encoded = null;
		try {
			encoded = encode(original.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
		}
		return encoded;

	}

	public static byte[] decode(String encoded) {
		byte[] original = null;
		try {
			original = base64Decoder.decodeBuffer(encoded);
		} catch (IOException e) {
		}

		return original;
	}

	public static String decodeToString(String encoded) {
		return decodeToString(encoded, Charset.defaultCharset().name());
	}

	public static String decodeToString(String encoded, String charset) {
		String result = null;
		try {
			byte[] original = base64Decoder.decodeBuffer(encoded);
			result = new String(original, charset);
		} catch (IOException e) {
		}

		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(decodeToString("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48cm9vdD48TWVzc2FnZUluZm8gY291bnQ9IjEwIiAgU2l0ZUlkPSJwaG9uZSIgIFNlcnZpY2VJZD0ic2l0ZU5vdGljZSIgIFRyYWNlTm89IjIwMTEwNTI0MTYzOTAwMDQiICBTaWduVHlwZT0iTUQ1IiAgU2lnbj0iMzBjOWE0NWFjZDFhMTZhOTYzYTNhY2E5OGExNGQwZDQiICAvPjwvcm9vdD4="));
	}

}
