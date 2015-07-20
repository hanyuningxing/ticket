package com.cai310.lottery.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	/***
	 * 获取网页内容
	 * @param url
	 * @param needBR
	 * @return
	 * @throws IOException
	 */
	public static String getContentByURL(String url, boolean needBR) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		conn.setConnectTimeout(1000*30);
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is =conn.getInputStream();
			isr = new InputStreamReader(is, "GBK");
			br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
				if(needBR)
					sb.append("\n");
			}
			sb.deleteCharAt(sb.lastIndexOf("*"));
			return sb.toString();
		}catch(Exception e){
			return null;
		} finally {
			if(br != null) br.close();
			if(isr != null) isr.close();
			if(is != null) is.close();
			conn.disconnect();
		}
	}
	
	public static String getContentByURL(String url, boolean needBR,String encode) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		conn.setConnectTimeout(1000*30);
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is =conn.getInputStream();
			isr = new InputStreamReader(is, encode);
			br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
				if(needBR)
					sb.append("\n");
			}
			sb.deleteCharAt(sb.lastIndexOf("*"));
			return sb.toString();
		}catch(Exception e){
			return null;
		} finally {
			if(br != null) br.close();
			if(isr != null) isr.close();
			if(is != null) is.close();
			conn.disconnect();
		}
	}
}
