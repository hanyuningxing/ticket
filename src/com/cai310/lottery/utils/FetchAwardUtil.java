package com.cai310.lottery.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.cai310.lottery.common.Lottery;

public class FetchAwardUtil{
	public static List<CpdyjAwardData> getFetchAwardByXml(String urlString,Lottery lottery) throws Exception{
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
			FetchAwardVisitor fetchAwardVisitor=new FetchAwardVisitor();
			doc.accept(fetchAwardVisitor);
			Boolean checkIsSuccess = Boolean.TRUE;
			if(Lottery.DCZC.equals(lottery)){
				//如果是北京单场不检查是否期成功
				checkIsSuccess=Boolean.FALSE;
			}
			if(checkIsSuccess){
				//是够需要检查数据是否成功
				if(!fetchAwardVisitor.getIsSuccess()){
					return null;
				}
			}
			if(fetchAwardVisitor.getIsDataSuccess()){
				//封装数据无出现错误
				return fetchAwardVisitor.getCpdyjFetchDataList();
			}else{
				//报错
				throw new Exception();
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
}
