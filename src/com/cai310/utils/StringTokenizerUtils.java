package com.cai310.utils;

import java.util.List;
import java.util.StringTokenizer;

import com.google.common.collect.Lists;

public class StringTokenizerUtils {

	/**
	 * 将str将多个分隔符进行切分，
	 * 
	 * 示例：StringTokenizerUtils.split("1,2;3 4"," ,;"); 返回: ["1","2","3","4"]
	 */
	public static String[] split(String str, String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List<String> result = Lists.newArrayList();

		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s.toString());
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

}
