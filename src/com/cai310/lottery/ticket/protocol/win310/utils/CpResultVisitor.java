package com.cai310.lottery.ticket.protocol.win310.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
///<?xml version="1.0" encoding="utf-8"?><msg><head transcode="0201"
		// partnerid="182" version="1.0"
		// time="20131230165322"/><body><ticketorder platformId="30"
		// ticketsnum="1" totalmoney="2.0"><tickets><ticket ticketId="182291"
		// multiple="1" issueNumber="" playType="0" betType="0" schemeNum=""
		// betUnits="1" betTime="1388393598560" comRemark="2x1" betCost="2.0"
		// statusCode="0000" msg="等待交易"
		// palmid="1013123000000002"><betContent>周一001[胜]/周一002[负]</betContent></ticket></tickets></ticketorder></body></msg>
		
		// /<?xml version="1.0" encoding="utf-8"?><msg><head transcode="0201"
		// partnerid="182" version="1.0"
		// time="20131230170313"/><body><ticketorder platformId="30"
		// ticketsnum="1" totalmoney="2.0"><tickets><ticket ticketId="182291"
		// multiple="1" issueNumber="" playType="0" betType="0" schemeNum=""
		// betUnits="1" betTime="1388394190632" comRemark="2x1" betCost="2.0"
		// statusCode="9009" msg="重复订单"
		// palmid="1013123000000002"><betContent>周一001[胜]/周一002[负]</betContent></ticket></tickets></ticketorder></body></msg>
public class CpResultVisitor extends VisitorSupport{
	private String result;
	private String orderId;
	private String betMoney;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		if("statusCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("0000").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
				if(String.valueOf("9009").equals(node.getText().trim())){
					///订单号重复。设为成功
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
		if("betCost".equals(node.getName())){
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
