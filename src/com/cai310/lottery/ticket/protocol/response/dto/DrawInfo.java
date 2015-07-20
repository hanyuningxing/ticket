package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.List;

public class DrawInfo {
	/**玩法编号*/
	private String game;
	/**奖期号*/
	private String gameIssue;
	/**摇奖号码*/
	private String drawCode;
	
	private List<BonusItemInfo> bonusItems;
	
	/**全国销售总额*/
	private Integer totalSales;
	/**全国中奖总额*/
	private Integer totalBonus;
	/**奖池金额*/
	private Integer poolBonus;
	/**
	 * @return the game
	 */
	public String getGame() {
		return game;
	}
	/**
	 * @param game the game to set
	 */
	public void setGame(String game) {
		this.game = game;
	}
	/**
	 * @return the gameIssue
	 */
	public String getGameIssue() {
		return gameIssue;
	}
	/**
	 * @param gameIssue the gameIssue to set
	 */
	public void setGameIssue(String gameIssue) {
		this.gameIssue = gameIssue;
	}
	/**
	 * @return the drawCode
	 */
	public String getDrawCode() {
		return drawCode;
	}
	/**
	 * @param drawCode the drawCode to set
	 */
	public void setDrawCode(String drawCode) {
		this.drawCode = drawCode;
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
	/**
	 * @return the totalSales
	 */
	public Integer getTotalSales() {
		return totalSales;
	}
	/**
	 * @param totalSales the totalSales to set
	 */
	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
	}
	/**
	 * @return the totalBonus
	 */
	public Integer getTotalBonus() {
		return totalBonus;
	}
	/**
	 * @param totalBonus the totalBonus to set
	 */
	public void setTotalBonus(Integer totalBonus) {
		this.totalBonus = totalBonus;
	}
	/**
	 * @return the poolBonus
	 */
	public Integer getPoolBonus() {
		return poolBonus;
	}
	/**
	 * @param poolBonus the poolBonus to set
	 */
	public void setPoolBonus(Integer poolBonus) {
		this.poolBonus = poolBonus;
	}
	
	
	
	
	
}
