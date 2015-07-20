package com.cai310.lottery.utils;

import java.util.HashMap;
import java.util.Map;

import com.cai310.utils.LoginByNetworkConfig;


/**
 * 缓存应用的基本信息 
 * @author Administrator
 *
 */
public class AppBasicInfoUtil {
	
	private static Map<String, String> appBasicInfo = new HashMap<String, String>();
	
	public static final String GRANT_TYPE = "grant_type";//获取token方式
	
	public static final String CLIENT_ID = "client_id";//应用API key详见http://app.renren.com/developers
	
	public static final String CLIENT_SECRET = "client_secret";//应用密钥 详见http://app.renren.com/developers
	
	public static final String REDIRECT_URI = "redirect_uri";//获取code的redirect_uri
	
	public static AppBasicInfoUtil instance = new AppBasicInfoUtil();
	
	
	/**
	 * 读取配置文件，将应用基本信息载入内存
	 */
	private AppBasicInfoUtil() {
			appBasicInfo.put(GRANT_TYPE, "authorization_code");
			appBasicInfo.put(CLIENT_ID, LoginByNetworkConfig.renren_api_key);
			appBasicInfo.put(CLIENT_SECRET,LoginByNetworkConfig.renren_api_secret);
			appBasicInfo.put(REDIRECT_URI, LoginByNetworkConfig.renren_callbackUrl);
		 
	}
	
	public synchronized static AppBasicInfoUtil getInstance() {
		if(instance == null) {
			return instance = new AppBasicInfoUtil();
		}
		return instance;
	}
	
	public Map getAppBasicInfo() {
		return appBasicInfo;
	}

}
