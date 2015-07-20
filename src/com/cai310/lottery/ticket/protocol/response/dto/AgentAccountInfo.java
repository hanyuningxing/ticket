package com.cai310.lottery.ticket.protocol.response.dto;

import java.math.BigDecimal;

public class AgentAccountInfo {
private BigDecimal totalPay;
private BigDecimal totalSales;
private BigDecimal totalBonus;
private BigDecimal totalCommission;
private BigDecimal totalDraw;
private BigDecimal balance;
private String responseCode;
/**
 * @return the totalPay
 */
public BigDecimal getTotalPay() {
	return totalPay;
}
/**
 * @param totalPay the totalPay to set
 */
public void setTotalPay(BigDecimal totalPay) {
	this.totalPay = totalPay;
}
/**
 * @return the totalSales
 */
public BigDecimal getTotalSales() {
	return totalSales;
}
/**
 * @param totalSales the totalSales to set
 */
public void setTotalSales(BigDecimal totalSales) {
	this.totalSales = totalSales;
}
/**
 * @return the totalBonus
 */
public BigDecimal getTotalBonus() {
	return totalBonus;
}
/**
 * @param totalBonus the totalBonus to set
 */
public void setTotalBonus(BigDecimal totalBonus) {
	this.totalBonus = totalBonus;
}
/**
 * @return the totalCommission
 */
public BigDecimal getTotalCommission() {
	return totalCommission;
}
/**
 * @param totalCommission the totalCommission to set
 */
public void setTotalCommission(BigDecimal totalCommission) {
	this.totalCommission = totalCommission;
}
/**
 * @return the totalDraw
 */
public BigDecimal getTotalDraw() {
	return totalDraw;
}
/**
 * @param totalDraw the totalDraw to set
 */
public void setTotalDraw(BigDecimal totalDraw) {
	this.totalDraw = totalDraw;
}
/**
 * @return the balance
 */
public BigDecimal getBalance() {
	return balance;
}
/**
 * @param balance the balance to set
 */
public void setBalance(BigDecimal balance) {
	this.balance = balance;
}
/**
 * @return the responseCode
 */
public String getResponseCode() {
	return responseCode;
}
/**
 * @param responseCode the responseCode to set
 */
public void setResponseCode(String responseCode) {
	this.responseCode = responseCode;
}


}
