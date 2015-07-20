package com.cai310.lottery.ticket.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.ticket.protocol.local.utils.CPUtil;
import com.cai310.utils.GBKPostMethod;
import com.cai310.utils.UTF8PostMethod;

public class HttpclientUtil {
	protected static Logger logger = LoggerFactory.getLogger(CPUtil.class);
	/**
	 * 
	 * @param reUrl  请求地址
	 * @param ParamMap 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String Utf8HttpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(200000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,200000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 200000);//连接超时 
			httpClient.setParams(p);
			postMethod = new UTF8PostMethod(reUrl);
			// HTTP参数
			if(null!=ParamMap){
				org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[ParamMap.keySet().size()];
				int i =0;
				for (String key : ParamMap.keySet()) {
					String value = ParamMap.get(key);
					postData[i] = new org.apache.commons.httpclient.NameValuePair(key,value);
					i++;
				}
				postMethod.setRequestBody(postData); 
			}
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			logger.debug("回传数据:"+reStr);
			postMethod.releaseConnection();
			return reStr;

		}catch(Exception e){
			e.printStackTrace();
			logger.error("请求出错:"+e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}    
		return null;
    
	}
	
	/**
	 * 
	 * @param reUrl  请求地址
	 * @param ParamMap 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String GBKHttpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(200000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,200000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 200000);//连接超时 
			httpClient.setParams(p);
			postMethod = new GBKPostMethod(reUrl);
			// HTTP参数
			if(null!=ParamMap){
				org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[ParamMap.keySet().size()];
				int i =0;
				for (String key : ParamMap.keySet()) {
					String value = ParamMap.get(key);
					postData[i] = new org.apache.commons.httpclient.NameValuePair(key,value);
					i++;
				}
				postMethod.setRequestBody(postData); 
			}
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			logger.debug("回传数据:"+reStr);
			postMethod.releaseConnection();
			return reStr;

		}catch(Exception e){
			e.printStackTrace();
			logger.error("请求出错:"+e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}    
		return null;
    
	}
	public static void main(String[] args) {
		String transcode="001";
		String partnerid="349022";
		String msg="";
		String key="";
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\""+transcode+"\"  partnerid=\""+partnerid+"\" version=\"1.0\" time=\"20110628175541\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<queryissue  lotteryId=\"SSQ\"  issueNumber=\"\"  />");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
//		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
//		msg = wParamValueSb.toString();
//		ParamMap.put("transcode", transcode);
//		ParamMap.put("msg", msg);
//		ParamMap.put("key", "");
//		ParamMap.put("partnerid", partnerid);
		msg=wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key=123456&partnerid="+partnerid;
		GetResponseDataByID("http://121.12.168.124:661/ticketinterface.aspx", postData);
	}
    public static String GetResponseDataByID(String url, String param){
	     String result=null;
	     try {
	    	    URL httpurl = new URL(url);
				HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				httpConn.setRequestProperty("content-type",  "text/xml");
				// 设置连接超时时间3秒
				httpConn.setConnectTimeout(new Integer(3000));
				// 设置接收超时时间3秒
				httpConn.setReadTimeout(new Integer(3000));

				PrintWriter out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(),"utf-8"));
				out.print(param);
				out.flush();
				out.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				in.close();

		     } catch (Exception ex) {
		      ex.printStackTrace();
		     }
		     System.out.println(result);
		     return result;
	    }

}
