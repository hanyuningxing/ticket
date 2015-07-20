package com.cai310.lottery.ticket.protocol.rlyg;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class CpResultVisitor extends VisitorSupport{
	////<?xml version="1.0" encoding="UTF-8"?><response errorcode="0"/>
	private String result;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		if("errorcode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("0").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
				result = node.getText().trim();
			}
		}
	}
	public void visit(Element node){
		
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
