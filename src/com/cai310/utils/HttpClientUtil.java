package com.cai310.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	private static DefaultHttpClient httpClient = null;
	private static DefaultHttpClient proxyClientHttpClient = null;

	protected  static List<String> useProxy = new ArrayList<String>();

//	static {
//		try {
//
//			String contenStringt = FileUtils.getFileContent(Thread.currentThread().getContextClassLoader()
//					.getResource("useProxy.txt").getPath());
//			String[] contentList = contenStringt.split("(\r\n|\n)+");
//
//			for (String content : contentList) {
//				if (StringUtils.isNotBlank(content)) {
//					useProxy.add(content);
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	public static DefaultHttpClient getHttpProxyClient() {
		if (proxyClientHttpClient == null) {
			HttpParams params = new BasicHttpParams();
			ConnManagerParams.setMaxTotalConnections(params, 50);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			proxyClientHttpClient = new DefaultHttpClient(cm, params);
		}
		return proxyClientHttpClient;
	}

	public static DefaultHttpClient getHttpClient() {
		if (httpClient == null) {
			HttpParams params = new BasicHttpParams();
			ConnManagerParams.setMaxTotalConnections(params, 50);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			httpClient = new DefaultHttpClient(cm, params);
		}
		return httpClient;
	}

	public static String getRemoteSource(String url) {
		return getRemoteSource(url, null);
	}

	private static String getUseProxy() {
		if(null!=useProxy&&useProxy.size()>0) {
			System.out.println("代理剩余："+useProxy.size());
			return  useProxy.get(0); 
		} else{
			logger.error("代理已经用完！！");
		} 
		return null;
	}

	public static String getRemoteSource(String url, String encode) {
		DefaultHttpClient httpclient = getHttpClient();
		HttpUriRequest httpReq = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		try {
			ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
			String resultContent = httpclient.execute(httpReq, responseHandler, context);
			return resultContent;
		} catch (Exception e) {
			logger.error(String.format("请求远程地址失败，URL：[%s]", url), e);
			httpReq.abort();
			return null;
		}
	}

	public static String getRemoteSource(String url, String encode, boolean useProxy) {
		DefaultHttpClient httpclient = getHttpProxyClient();
		HttpUriRequest httpReq = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		String useProxyIPPort = null;
		try {
				if (useProxy) {
					useProxyIPPort = getUseProxy();
					if (StringUtils.isNotBlank(useProxyIPPort)) {
						// 使用代理
						System.out.println("useProxyIPPort:" + useProxyIPPort);
						String proxyIP = useProxyIPPort.split(":")[0];
						int proxyPort = Integer.valueOf(useProxyIPPort.split(":")[1]);
						String proxyUserName = "test";
						String proxyPassWord = "123456";
						String proxyScheme = "http";
						httpclient.getCredentialsProvider().setCredentials(new AuthScope(proxyIP, proxyPort),
								new UsernamePasswordCredentials(proxyUserName, proxyPassWord));
						HttpHost proxy = new HttpHost(proxyIP, proxyPort, proxyScheme);
						httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
					}
				}

				ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
				String resultContent = httpclient.execute(httpReq, responseHandler, context);
				return resultContent;
 
		} catch (Exception e) {
			logger.error(String.format("请求远程地址失败，URL：[%s]", url), e);
			httpReq.abort();
			HttpClientUtil.useProxy.remove(0);
			if (StringUtils.isNotBlank(useProxyIPPort)) {
				HttpClientUtil.useProxy.add(useProxyIPPort);
			}
		}
		return null;
	}
}
