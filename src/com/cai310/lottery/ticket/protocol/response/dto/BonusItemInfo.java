package com.cai310.lottery.ticket.protocol.response.dto;

import java.math.BigDecimal;

public class BonusItemInfo {
	private String bonusLevel;
	private Integer bonusCount;
	private BigDecimal levelMoney;
	/**
	 * @return the bonusLevel
	 */
	public String getBonusLevel() {
		return bonusLevel;
	}
	/**
	 * @param bonusLevel the bonusLevel to set
	 */
	public void setBonusLevel(String bonusLevel) {
		this.bonusLevel = bonusLevel;
	}
	/**
	 * @return the bonusCount
	 */
	public Integer getBonusCount() {
		return bonusCount;
	}
	/**
	 * @param bonusCount the bonusCount to set
	 */
	public void setBonusCount(Integer bonusCount) {
		this.bonusCount = bonusCount;
	}
	/**
	 * @return the levelMoney
	 */
	public BigDecimal getLevelMoney() {
		return levelMoney;
	}
	/**
	 * @param levelMoney the levelMoney to set
	 */
	public void setLevelMoney(BigDecimal levelMoney) {
		this.levelMoney = levelMoney;
	}
	
	
}
