package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.google.common.collect.Maps;

public class CpResultVisitor extends VisitorSupport{
	private String result;
	private String orderId;
	private Map<String,Boolean> resultMap = Maps.newHashMap();
	public void visit(Attribute node){
		
	}
	public static void main(String[] args) {
		String arr = "134^1234".replaceAll("\\^", ",");
		String[] resultArr = arr.split(",");
		int i = 0 ;
	}
	public void visit(Element node){
		if("xCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				result = node.getText().trim();
				String[] resultArr = result.replaceAll("\\^", ",").split(",");
				String[] orderIdArr = orderId.replaceAll("\\^", ",").split(",");
				if(resultArr.length==orderIdArr.length){
					for (int i = 0; i < orderIdArr.length; i++) {
						String orderId = orderIdArr[i];
						String result = resultArr[i];
						if(String.valueOf("0").equals(result.trim())||String.valueOf("1008").equals(result.trim())){
							///成功
							resultMap.put(orderId, true);
						}else{
							resultMap.put(orderId, false);
						}
						
					}
				}
			}
		}
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Map<String, Boolean> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Boolean> resultMap) {
		this.resultMap = resultMap;
	}
	

}
