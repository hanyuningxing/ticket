package com.cai310.lottery.ticket.protocol.win310.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.utils.DateUtil;

public class Win310QueryPVisitor extends VisitorSupport{
	///<?xml version="1.0" encoding="UTF-8"?>
	//		<msg>
	//			<head transcode="101" partnerid="349022" version="1.0" time="20120517103730" />
	//			<body>
	//				<issueinfo lotteryId="SSQ" issueNumber="2012057" startTime="" stopTime="2012/5/17 19:50:00" closeTime="2012/5/17 20:00:00" status="1" bonusCode="" bonusInfo="" />
	//			</body>
	//	   </msg>
	private String issueNumber;
	private String stopTime;
	private String closeTime;
	private String status;
	
	private String ResultId;
	private String OrderId;
	private String TicketCode;
	private String Awards;
	private String OperateTime;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		if("issueNumber".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				issueNumber=node.getText().trim();
			}
		}
		if("stopTime".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				stopTime=node.getText().trim();
			}
		}
		if("closeTime".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				closeTime=node.getText().trim();
			}
		}
		if("status".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				status=node.getText().trim();
			}
		}
		///查票
		if("ticketId".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				OrderId=node.getText().trim();
			}
		}
		///查票
		if("statusCode".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				ResultId=node.getText().trim();
				if("003".equals(ResultId)){
					isSuccess = true;
					OperateTime=DateUtil.dateToStr(new Date(), "yyyy-MM-dd hh:mm:ss");
				}
			}
		}
		///查票
		if("printodd".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				Awards=node.getText().trim();
			}
		}
	}
	public void visit(Element node){
		
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
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
