package com.cai310.lottery.support;

import java.math.BigDecimal;
import java.util.Date;

public class OneDayCounter {

	/**日期**/
	private Date day;
	
	/**总方案数**/
	private int total;
	
	/**成功方案数**/
	private int totalSuccess;
	
	/**总中奖方案数**/
	private int totalWon;
	
	/**总销量(元)**/
	private BigDecimal totalSales = BigDecimal.ZERO;
	
	/**税前总奖金**/
	private BigDecimal totalPrice = BigDecimal.ZERO;
	
	/** 税后总奖金 */
	private BigDecimal prizeAfterTax = BigDecimal.ZERO;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalSuccess() {
		return totalSuccess;
	}

	public void setTotalSuccess(int totalSuccess) {
		this.totalSuccess = totalSuccess;
	}

	public int getTotalWon() {
		return totalWon;
	}

	public void setTotalWon(int totalWon) {
		this.totalWon = totalWon;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getPrizeAfterTax() {
		return prizeAfterTax;
	}

	public void setPrizeAfterTax(BigDecimal prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}

}
