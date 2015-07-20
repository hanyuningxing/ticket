package com.cai310.security;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.mortbay.log.Log;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.cai310.lottery.ticket.common.SecurityUtil;

/**
 * 
 * Description: ES加密的，文件中共有两个方法,加密、解密<br>
 * 
 * Copyright: Copyright (c) 2011 <br>
 * Company: 广东鸿波通信投资控股科技有限公司
 * 
 * @author zhuhui 2011-7-28 编写
 * @version 1.0
 */
public class DES {
	private String algorithm = "DES/ECB/PKCS5Padding";
	private Key deskey;
	private Cipher cipher;
	private String encode = "UTF-8";

	/**
	 * 初始化 DES 实例
	 */
	public DES() {
		try {
			init();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 初始化 DES 实例
	 */
	public DES(String key) {
		this();
		try {
			setKey(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 初始化 DES 加密算法的一些参数
	 */
	private void init() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		cipher = Cipher.getInstance(algorithm);
	}

	/**
	 * 对 byte[] 进行加密
	 * 
	 * @param datasource
	 *            要加密的数据
	 * @return 返回加密后的 byte 数组
	 */
	private byte[] createEncryptor(String datasource) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		return cipher.doFinal(datasource.getBytes(encode));
	}

	/**
	 * 对 datasource 数组进行解密
	 * 
	 * @param datasource
	 *            要解密的数据
	 * @return 返回加密后的 byte[]
	 */
	private byte[] createDecryptor(byte[] datasource) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		return cipher.doFinal(datasource);
	}

	/**
	 * 
	 * 将 DES 加密过的 byte数组转换为字符串
	 * 
	 * @param dataByte
	 * @return
	 */
	private String byteToString(byte[] dataByte) {
		BASE64Encoder be = new BASE64Encoder();
		return be.encode(dataByte);
	}

	/**
	 * 
	 * 将字符串转换为DES算法可以解密的byte数组
	 * 
	 * @param dataByte
	 * @return
	 * @throws Exception
	 */
	private byte[] stringToByte(String datasource) throws Exception {
		BASE64Decoder bd = new BASE64Decoder();
		return bd.decodeBuffer(datasource);
	}

	public String decryptor(String encryptorString){
		try {
			return new String(createDecryptor(stringToByte(encryptorString)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String encryptor(String encryptorString) {
		String encryptorResult = null;
		try {
			encryptorResult = byteToString(createEncryptor(encryptorString));
			encryptorResult = encryptorResult.replace("\r", "");
			encryptorResult = encryptorResult.replace("\n", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptorResult;
	}

	/**
	 * setKey
	 * 
	 * @param key
	 *            String
	 * @throws Exception
	 */

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	public void setKey(String key) throws Exception {/*
													 * MessageDigest mdTemp =
													 * MessageDigest
													 * .getInstance("MD5");
													 * BASE64Encoder be = new
													 * BASE64Encoder(); deskey =
													 * new
													 * SecretKeySpec(be.encode
													 * (mdTemp
													 * .digest(key.getBytes
													 * (encode
													 * ))).getBytes(encode),
													 * algorithm);
													 */

		DESKeySpec desKeySpec;
		desKeySpec = new DESKeySpec(hexStringToByte(key));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		deskey = keyFactory.generateSecret(desKeySpec);
	}

	/**
	 * genKey
	 * 
	 * @return String
	 */
	public String genKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
		SecretKey key = keygen.generateKey();
		return byteToString(key.getEncoded());
	}

	/**
	 * bytes2String
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 */
	private String bytes2String(byte[] data) {

		if (data == null) {
			return null;
		}
		String sbs = new String(data);
		StringBuffer sb = new StringBuffer(sbs + "{");
		for (int i = 0; data != null && i < data.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(data[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		DES des = new DES("BA0D03BC0AEC1208");
		String mingwen = "<records><record>";
		System.out.println("加密前的数据：" + mingwen);
		String miwen = des.encryptor(mingwen);
		System.out.println("des数据：" + miwen);
		System.out.println("ms5数据：" + SecurityUtil.md5(miwen.toString()).toLowerCase());
		System.out.println("des揭密数据：" + des.decryptor("aVqf9NC7bY/jyGIIVgPZUwc4pqwTTIcF11OC8eaKCI+Tdud+SMTgX6xTl2pOoBPYpBb9Lum7jfxU0x9JgT2Q4mCF3H2BLWsbY28y1PP47Bmd9abgumKtUt337tLJ1zmYKprG5Ff7MfndRWJB52aL7JEb/Ndy83v7cnV67oNG1vuRkSpgcRpsoVMyNvE5uv9QYqJzm+PGNoCyi0Z3Mxdv+e6cdlfJuRUSmG3GBZ2bupRa82JjXCkSrcP5s0R9dXZkej5zCP9sft/d3WK9Loqv2ScX8hNe4qtwTGPk+BBz/iDWgZM+r4Q+qFRyYI3B5W/4rlYitAqvEMoUMiV2AKxlIw=="));

		
	}
}
