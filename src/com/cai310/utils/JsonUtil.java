package com.cai310.utils;

//在www.json.org上公布了很多Java下的json解析工具，其中org.json和json-lib比较简单，两者使用上差不多。下面两段源代码是分别使用这两个工具解析和构造//JSON的演示程序。   
//这是使用json-lib的程序：   
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;

public class JsonUtil {

	public static String getStringValueByJsonStr(String json, String name) {
		if (StringUtils.isBlank(json)) {
			return "";
		}
		JSONObject jsonObj = JSONObject.fromObject(json);

		String value = jsonObj.getString(name);
		if (StringUtils.isBlank(value)) {
			return "";
		}
		return value;
	}

	public static String getStringValueByJson(JSONObject jsonObj, String name) {
		if (null == jsonObj) {
			return "";
		}
		String value = jsonObj.getString(name);
		if (StringUtils.isBlank(value)) {
			return "";
		}
		return value;
	}

	public static Object getObjectValueByJson(JSONObject jsonObj, String name) {
		if (null == jsonObj) {
			return null;
		}
		String value = jsonObj.getString(name);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return value;
	}

	public static Object getObjectValueByJson(String json, String name) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		JSONObject jsonObj = JSONObject.fromObject(json);
		Object value = jsonObj.get(name);
		if (null == value) {
			return null;
		}
		return value;
	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static JSONArray getJSONArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {
		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		String josn = json.toString();
		return josn;
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObject4JsonString(String jsonString, Class<T> pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return (T) pojo;
	}

	/**
	 * 从一个JSON对象字符格式中得到一个java对象
	 * 
	 * @param jsonString JSON对象字符格式
	 * @param pojoCalss 要转换成的Bean的类型
	 * @param classMap Bean的成员类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObject4JsonString(String jsonString, Class<T> pojoCalss, Map classMap) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss, classMap);
		return (T) pojo;
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	/**
	 * 从json HASH表达式中获取一个map
	 * 
	 * @param <T> map中值的类型
	 * @param jsonString json字符串
	 * @param valueClass map中值的类型的Class
	 * @return map对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getMap4Json(String jsonString, Class<T> valueClass) {
		Map<String, T> valueMap = new HashMap<String, T>();
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Set entrySet = jsonObject.entrySet();
		for (Object object : entrySet) {
			Map.Entry entry = (Map.Entry) object;
			String key = (String) entry.getKey();
			T value = (T) JSONObject.toBean(JSONObject.fromObject(entry.getValue()), valueClass);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	/**
	 * 从json HASH表达式中获取一个map
	 * 
	 * @param <T> map中值的类型
	 * @param jsonString json字符串
	 * @param valueClass map中值的类型的Class
	 * @return map对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getMap4Json(String jsonString, Class<T> valueClass, Map classMap) {
		Map<String, T> valueMap = new HashMap<String, T>();
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Set entrySet = jsonObject.entrySet();
		for (Object object : entrySet) {
			Map.Entry entry = (Map.Entry) object;
			String key = (String) entry.getKey();
			T value = (T) JSONObject.toBean(JSONObject.fromObject(entry.getValue()), valueClass, classMap);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList4Json(String jsonString, Class<T> pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		T pojoValue;

		List<T> list = new ArrayList<T>();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/**
	 * 从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat) {

		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();

	}

	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

	/**
	 * 
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

}