package com.cai310.lottery.ticket.protocol.tianjin.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class QueryPVisitor extends VisitorSupport{
	private String orderId;
	private String betCost;
	private String winCost;
	private String remoteCode;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		
	}
	public void visit(Element node){
		if("xCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("1").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
		}
		if("xValue".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				String[] arr = node.getText().split("_");
				this.orderId = arr[0].trim();
				this.betCost = arr[1].trim();
				this.winCost = arr[2].trim();
				if(arr.length==4){
					if(arr[3]==null){
						this.remoteCode =  "";
					}else{
						if("null".equals(arr[3])){
							this.remoteCode =  "";
						}else{
							this.remoteCode =  arr[3].trim();
						}
					}
				}
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBetCost() {
		return betCost;
	}
	public void setBetCost(String betCost) {
		this.betCost = betCost;
	}
	public String getWinCost() {
		return winCost;
	}
	public void setWinCost(String winCost) {
		this.winCost = winCost;
	}
	public String getRemoteCode() {
		return remoteCode;
	}
	public void setRemoteCode(String remoteCode) {
		this.remoteCode = remoteCode;
	}
	

}
