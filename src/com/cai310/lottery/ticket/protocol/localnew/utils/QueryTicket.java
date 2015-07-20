package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class QueryTicket{
	private Integer TicketState;
	private String TicketCode;
	private String Odds;
	private String OperateTime;
	private Integer Code;
	private Long TicketId;
	public Boolean getIsSuccess() {
		return TicketState.equals(1);
	}
	public Integer getTicketState() {
		return TicketState;
	}
	public void setTicketState(Integer ticketState) {
		TicketState = ticketState;
	}
	public String getTicketCode() {
		return TicketCode;
	}
	public void setTicketCode(String ticketCode) {
		TicketCode = ticketCode;
	}
	public String getOperateTime() {
		return OperateTime;
	}
	public void setOperateTime(String operateTime) {
		OperateTime = operateTime;
	}
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public Long getTicketId() {
		return TicketId;
	}
	public void setTicketId(Long ticketId) {
		TicketId = ticketId;
	}
	public String getOdds() {
		return Odds;
	}
	public void setOdds(String odds) {
		Odds = odds;
	}
	
	
}
