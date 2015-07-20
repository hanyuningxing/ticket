package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class QueryPVisitor{
	private List<QueryTicket> Data;
	private String Code;
	public List<QueryTicket> getData() {
		return Data;
	}
	public void setData(List<QueryTicket> data) {
		Data = data;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	
}
