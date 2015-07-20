package com.cai310.lottery.ticket.protocol.yuecai;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class CpResultVisitor extends VisitorSupport{
	private String result;
	private String orderId;
	private String betMoney;
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

}
