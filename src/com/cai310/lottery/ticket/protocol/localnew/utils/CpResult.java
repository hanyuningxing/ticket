package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class CpResult{
	private Integer Code;
	private List<CpTicket> Data;
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public List<CpTicket> getData() {
		return Data;
	}
	public void setData(List<CpTicket> data) {
		Data = data;
	}
}
