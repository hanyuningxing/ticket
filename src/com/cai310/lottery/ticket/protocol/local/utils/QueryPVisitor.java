package com.cai310.lottery.ticket.protocol.local.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class QueryPVisitor extends VisitorSupport{
	private String ResultId;
	private String OrderId;
	private String TicketCode;
	private String Awards;
	private String OperateTime;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		
	}
	public void visit(Element node){
		if("ResultId".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("1").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
			ResultId = node.getText().trim();
		}
		if("OrderId".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				OrderId = node.getText().trim();
			}
		}
		if("TicketCode".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				TicketCode = node.getText().trim();
			}
		}
		if("Awards".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				Awards = node.getText().trim();
			}
		}
		if("OperateTime".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				OperateTime = node.getText().trim();
			}
		}
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
	public String getResultId() {
		return ResultId;
	}
	public void setResultId(String resultId) {
		ResultId = resultId;
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
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

}
