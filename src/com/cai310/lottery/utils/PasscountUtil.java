package com.cai310.lottery.utils;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.Lottery;

public class PasscountUtil {
	public static String getPeriodDir(Lottery lottery){
		return "/passcount/"+lottery.getKey()+"/";
	}
	public static String trimToEmpty(Object o){
		 if(null==o)return "";
		 return StringUtils.trimToEmpty(String.valueOf(o));
	}
	public static String trimNumToEmpty(Object o){
		 if(null==o)return "0";
		 return StringUtils.trimToEmpty(String.valueOf(o));
	}
}
