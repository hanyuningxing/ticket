package com.cai310.lottery.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 调用人人API工具类
 * 
 * @author Administrator
 * 
 */
public class RenrenAPIUtil {

	// 通过code换取access token URL
	private static final String ACCESSTOKENURL = "https://graph.renren.com/oauth/token";

	// 通过access token 换取session key URL
	private static final String SESSION_KEY = "http://graph.renren.com/renren_api/session_key";

	private static Map<String, String> appInfo = AppBasicInfoUtil.getInstance().getAppBasicInfo();

	/**
	 * 根据code获取access token
	 * 
	 * @param code
	 * @return
	 */
	public static String getAccessToken(String code) {
		String queryString = "grant_type=" + appInfo.get(AppBasicInfoUtil.GRANT_TYPE) + "&code=" + code + "&client_id="
				+ appInfo.get(AppBasicInfoUtil.CLIENT_ID) + "&client_secret="
				+ appInfo.get(AppBasicInfoUtil.CLIENT_SECRET) + "&redirect_uri="
				+ appInfo.get(AppBasicInfoUtil.REDIRECT_URI);
		System.out.println("queryString ==== " + queryString);
		return getOAuthInfo(ACCESSTOKENURL, queryString);
	}

	/**
	 * 根据accesstoken获取session key
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getSessionKey(String accessToken) {
		String queryString = "oauth_token=" + accessToken;
		return getOAuthInfo(SESSION_KEY, queryString);
	}

	public static String getOAuthInfo(String url, String queryString) {
		try {
			URL token = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) token.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.addRequestProperty("Accept-Encoding", "gzip");
			conn.setDefaultUseCaches(false);
			conn.connect();
			OutputStream os = conn.getOutputStream();
			os.write(queryString.getBytes());

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode======" + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				int contentLength = conn.getContentLength();
				String contentCode = conn.getContentEncoding();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				StringBuilder ret = new StringBuilder(4096);
				char[] buffer = new char[1024];
				int charsRead = reader.read(buffer);
				while (charsRead != -1) {
					ret.append(buffer, 0, charsRead);
					charsRead = reader.read(buffer);
				}
				System.out.println("ret=====" + ret);
				return ret.toString();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
