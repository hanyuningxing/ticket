package com.cai310.lottery.ticket.common;

import java.util.ArrayList;
import java.util.List;

public class StringOfListUtil {
	
	/**
	 * 将list中的字符小于10的前面补"0"
	 * @param codeList
	 * @return
	 */
	public static List<String> format(List<String> codeList) {
		List<String> resultList = new ArrayList<String>();
		if(codeList==null)return resultList;
		for(String code:codeList){
			if(code.length()==1){
				resultList.add("0"+code);
			}else{
				resultList.add(code);
			}
		}
		return resultList;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
