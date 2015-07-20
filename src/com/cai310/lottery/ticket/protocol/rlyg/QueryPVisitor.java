package com.cai310.lottery.ticket.protocol.rlyg;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.utils.DateUtil;

public class QueryPVisitor extends VisitorSupport{
	///<ticket seq="3" status="Y" ticketid="J0007201206130000003224" groundid="20063851690912933897" errmsg="3.250-3.550">201206144051:[1]/201206144052:[1]|06150714$1$2</ticket>
	private String issueNumber;
	private String stopTime;
	
	private String ResultId;
	private String OrderId;
	private String TicketCode;
	private String Awards;
	private String betValue;
	private String OperateTime;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		
		if("issue".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				issueNumber=node.getText().trim();
			}
		}
		if("endtime".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				stopTime=node.getText().trim();
			}
		}
		if("status".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				ResultId=node.getText().trim();
				if("Y".equals(ResultId)){
					isSuccess=Boolean.TRUE;
				}
			}
			
		}
		if("groundid".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				TicketCode=node.getText().trim();
			}
		}
		if("errmsg".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				Awards=node.getText().trim();
			}
		}
		///查票
		if("seq".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				OrderId=node.getText().trim();
			}
		}
	}
	public void visit(Element node){
		///查票
		if("ticket".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				betValue=node.getText().trim();
			}
		}
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
	public String getBetValue() {
		return betValue;
	}
	public void setBetValue(String betValue) {
		this.betValue = betValue;
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

}
