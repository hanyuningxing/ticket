package com.cai310.lottery.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GrabberUtils {
	protected static final Logger logger = LoggerFactory.getLogger(GrabberUtils.class);

	/**
	 * 读取远程页面的数据
	 * 
	 * @param url 远程页面URL
	 * @param encode 编码
	 * @return 远程页面的数据
	 */
	public static String getRemoteStr(String url, String encode) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpUriRequest httpReq = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpReq, responseHandler);
		} catch (Exception e) {
			logger.error("读取远程页面数据失败.URL="+url, e);
		}
		return null;
	}
}
