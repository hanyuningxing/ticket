package com.cai310.security;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/**
 *
 * Description:MD5不可逆加密算法 <br>

 * Copyright: Copyright (c) 2011 <br>
 * Company: 广东鸿波通信投资控股科技有限公司
 * 
 * @author zhuhui 2011-7-28 编写
 * @version 1.0
 */
public class MD5 {
	  private static String encode = "UTF-8";

	  public final static String md5(String str) {
	    try {
	      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	      BASE64Encoder be = new BASE64Encoder();
	      return be.encode(mdTemp.digest(str.getBytes(encode)));
	    } catch(Exception e) {
	      return null;
	    }
	  }
}
