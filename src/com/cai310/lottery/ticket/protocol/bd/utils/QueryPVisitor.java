package com.cai310.lottery.ticket.protocol.bd.utils;

import org.dom4j.VisitorSupport;

public class QueryPVisitor extends VisitorSupport{
	///{"draws":[{"name":"胜平负","gameId":200,"maxDraws":1,"drawId":21275,"drawNo":"51105","mainLeagueName":"","beginTime":"2015-11-26 09:00:00","endTime":"2015-12-12 18:30:00","drawDate":"2015-12-14 08:30:00","extension":2}],"code":0,"message":null}
	private String issueNumber;
	private String issueId;
	private String stopTime;
	private String closeTime;
	private String status;
	
	private String ResultId;
	private String OrderId;
	private String TicketCode;
	private String Awards;
	private String OperateTime;
	private Boolean isSuccess=Boolean.FALSE;
	
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResultId() {
		return ResultId;
	}
	public void setResultId(String resultId) {
		ResultId = resultId;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getTicketCode() {
		return TicketCode;
	}
	public void setTicketCode(String ticketCode) {
		TicketCode = ticketCode;
	}
	public String getAwards() {
		return Awards;
	}
	public void setAwards(String awards) {
		Awards = awards;
	}
	public String getOperateTime() {
		return OperateTime;
	}
	public void setOperateTime(String operateTime) {
		OperateTime = operateTime;
	}
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	

}
