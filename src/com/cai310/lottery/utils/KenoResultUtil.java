package com.cai310.lottery.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class KenoResultUtil{
	public static String getKenoResultByXml(String urlString,String issueNum){
		if(StringUtils.isBlank(urlString))return null;
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		} 
		SAXReader saxReader=new SAXReader();
		try {
			Document doc=saxReader.read(url);
			KenoResultVisitor kenoResultVisitor=new KenoResultVisitor();
			doc.accept(kenoResultVisitor);
			if(kenoResultVisitor.getIsSuccess()){
				if(issueNum.equals(kenoResultVisitor.getIssueNum())){
					if(StringUtils.isBlank(kenoResultVisitor.getResult())){
						//读取不到开奖号码
						return null;
					}
					return kenoResultVisitor.getResult().trim();
				}else{
					//期号不一样
					return null;
				}
			}else{
				//读取不到开奖号码
				return null;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
}
