
package com.cai310.utils;

import weibo4j.Weibo;


public class LoginByNetworkConfig  {
	public static String alipay_partnerID; 
	public static String alipay_key;
	public static String alipay_callbackUrl;
	
	public static String returnAuthUrl;//快捷登陆返回的URL
	public static String returnQueryUrl;
	
	public static String renren_api_key;
	public static String renren_api_secret;
	public static String renren_callbackUrl;
	
	public static String sina_consumerKey;
	public static String sina_consumerSecret;
	public static String sina_callbackUrl;

	public  void setAlipay_partnerID(String alipay_partnerID) {
		LoginByNetworkConfig.alipay_partnerID = alipay_partnerID;
	}
	public  void setAlipay_key(String alipay_key) {
		LoginByNetworkConfig.alipay_key = alipay_key;
	}
	public  void setAlipay_callbackUrl(String alipay_callbackUrl) {
		LoginByNetworkConfig.alipay_callbackUrl = alipay_callbackUrl;
	}
	public  void setRenren_api_key(String renren_api_key) {
		LoginByNetworkConfig.renren_api_key = renren_api_key;
	}
	public  void setRenren_api_secret(String renren_api_secret) {
		LoginByNetworkConfig.renren_api_secret = renren_api_secret;
	}
	public  void setRenren_callbackUrl(String renren_callbackUrl) {
		LoginByNetworkConfig.renren_callbackUrl = renren_callbackUrl;
	}
	public  void setSina_consumerKey(String sina_consumerKey) {
		LoginByNetworkConfig.sina_consumerKey = sina_consumerKey;
		Weibo.CONSUMER_KEY = sina_consumerKey;	
	}
	public  void setSina_consumerSecret(String sina_consumerSecret) {
		LoginByNetworkConfig.sina_consumerSecret = sina_consumerSecret;
		Weibo.CONSUMER_SECRET = sina_consumerSecret;
	}
	public  void setSina_callbackUrl(String sina_callbackUrl) {
		LoginByNetworkConfig.sina_callbackUrl = sina_callbackUrl;
	}
	public void setReturnAuthUrl(String returnAuthUrl) {
		LoginByNetworkConfig.returnAuthUrl = returnAuthUrl;
	}
	public void setReturnQueryUrl(String returnQueryUrl) {
		LoginByNetworkConfig.returnQueryUrl = returnQueryUrl;
	}
	
}
