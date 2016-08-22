package com.cai310.lottery.ticket.protocol.bd.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class CpResultVisitor extends VisitorSupport{
	private String result;
	private String orderId;
	private String betMoney;
	private Double prize = 0.0;        
	private Double returnMoney= 0.0;
	private String detail;
	private String issueId;
	private Double tax= 0.0;
	private boolean returnAward;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		if("statusCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("000").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
				result = node.getText().trim();
			}
		}
		if("ticketId".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				orderId = node.getText().trim();
			}
		}
		if("betMoney".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				betMoney = node.getText().trim();
			}
		}
	}
	//<ticket ticketId="238" multiple="1" issueNumber="2012057" betType="DS" betUnits="1" 
	//betMoney="2" statusCode="000" message="接收成功" palmid="5966836" />
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBetMoney() {
		return betMoney;
	}
	public void setBetMoney(String betMoney) {
		this.betMoney = betMoney;
	}
	public Double getPrize() {
		return prize;
	}
	public void setPrize(Double prize) {
		this.prize = prize;
	}
	public Double getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public boolean isReturnAward() {
		return returnAward;
	}
	public void setReturnAward(boolean returnAward) {
		this.returnAward = returnAward;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}

}
