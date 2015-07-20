package com.cai310.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * 读取Properties文件
 * @author TOSHIBA
 *
 */
public class PropertiesUtil {
	
	public static HashMap readProperties(String fileName){
		Properties pro = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName.trim());
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return conver2Map(pro);
	}
	
	public static HashMap conver2Map(Properties pro){
		HashMap map= new HashMap();
		Iterator iter = pro.keySet().iterator();
		for(;iter.hasNext();){
			String key = (String) iter.next();
			String value = pro.getProperty(key);
			map.put(key, value);
		}
		return map;
	}
	
}
