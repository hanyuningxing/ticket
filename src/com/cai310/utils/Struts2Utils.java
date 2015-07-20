package com.cai310.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.utils.StringUtil;

/**
 * Struts2 Utils类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 */
public class Struts2Utils {

	// -- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	private static final String REMOTEADDR_HEADERNAME = "X-Real-IP";

	private static Logger logger = LoggerFactory.getLogger(Struts2Utils.class);

	// -- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		if (null != ServletActionContext.getRequest()) {
			return ServletActionContext.getRequest().getSession();
		}
		return null;
	}

	public static String getDeCodeName(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			try {
				userName = new String(userName.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return userName;
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 取得Request Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	public static void setAttribute(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	public static Object getAttribute(String name) {
		return getRequest().getAttribute(name);
	}

//	/**
//	 * 获取访问者IP
//	 * 
//	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
//	 * 
//	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
//	 * 如果还不存在则调用Request .getRemoteAddr()。
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public static String getRemoteAddr() {
//		try {
//			String ip = getRequest().getHeader("X-Real-IP");
//			if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//				return ip;
//			}
//			ip = getRequest().getHeader("X-Forwarded-For");
//			if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//				// 多次反向代理后会有多个IP值，第一个为真实IP。
//				int index = ip.indexOf(',');
//				if (index != -1) {
//					return ip.substring(0, index);
//				} else {
//					return ip;
//				}
//			} else {
//				return getRequest().getRemoteAddr();
//			}
//		} catch (Exception e) {
//		}
//		return "";
//	}

	/**
	 * 获取客户Ip，优先拿cdn的值
	 * 
	 * @param req
	 */
	public static String getRemoteAddr() {
		String remoteIp = "0.0.0.0";
		try {
			remoteIp = getRequest().getHeader("Cdn-Src-Ip");
			if (remoteIp == null || remoteIp.trim().equals("")) {
				remoteIp = getRequest().getHeader("x-forwarded-for");
				if (remoteIp == null || remoteIp.trim().equals("")) {
					remoteIp = getRequest().getRemoteAddr();
					if (remoteIp == null || remoteIp.trim().equals("")) {
						remoteIp = "0.0.0.0";
					}
				}
			}

			remoteIp = StringUtil.split(remoteIp, ",")[0].trim();
			return remoteIp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	
	/**
	 * 判断是否AJAX请求
	 * 
	 * @return 是否AJAX请求
	 */
	public static boolean isAjaxRequest() {
		String x_requested_with = getRequest().getHeader("x-requested-with");
		return x_requested_with != null && x_requested_with.equalsIgnoreCase("XMLHttpRequest");
	}

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, final String... headers) {
		String contentType;
		String reqContentType = getRequest().getContentType();
		if (reqContentType != null && reqContentType.indexOf("multipart/form-data") >= 0)
			contentType = TEXT_TYPE;
		else
			contentType = JSON_TYPE;

		render(contentType, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		renderJson(jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, final String... headers) {
		String jsonString = JSONObject.fromObject(object).toString();
		renderJson(jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param collction
	 *            Java对象集合, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Collection<?> collction, final String... headers) {
		String jsonString = JSONArray.fromObject(collction).toString();
		renderJson(jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param array
	 *            Java对象数组, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object[] array, final String... headers) {
		String jsonString = JSONArray.fromObject(array).toString();
		renderJson(jsonString, headers);
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param contentMap
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJsonp(final String callbackName, final Map contentMap, final String... headers) {
		String jsonParam = JSONObject.fromObject(contentMap).toString();

		StringBuilder result = new StringBuilder().append(callbackName).append("(").append(jsonParam).append(");");

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{content:'Hello World!!!'}");
		render(JS_TYPE, result.toString(), headers);
	}
}
