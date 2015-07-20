package com.cai310.lottery.ticket.protocol.response.dto;

import java.math.BigDecimal;
import java.util.List;

public class BonusDetailInfo {
	private String schemeId;
	private BigDecimal bigPrize;
	private BigDecimal littlePrize;
	private List<BonusItemInfo> bonusItems;
	/**
	 * @return the schemeId
	 */
	public String getSchemeId() {
		return schemeId;
	}
	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	/**
	 * @return the bigPrize
	 */
	public BigDecimal getBigPrize() {
		return bigPrize;
	}
	/**
	 * @param bigPrize the bigPrize to set
	 */
	public void setBigPrize(BigDecimal bigPrize) {
		this.bigPrize = bigPrize;
	}
	/**
	 * @return the littlePrize
	 */
	public BigDecimal getLittlePrize() {
		return littlePrize;
	}
	/**
	 * @param littlePrize the littlePrize to set
	 */
	public void setLittlePrize(BigDecimal littlePrize) {
		this.littlePrize = littlePrize;
	}
	/**
	 * @return the bonusItems
	 */
	public List<BonusItemInfo> getBonusItems() {
		return bonusItems;
	}
	/**
	 * @param bonusItems the bonusItems to set
	 */
	public void setBonusItems(List<BonusItemInfo> bonusItems) {
		this.bonusItems = bonusItems;
	}
	
	
	
}
