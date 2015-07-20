package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.List;

public class BonusInfo {
	/**玩法编号*/
	private String game;
	/**奖期号*/
	private String gameIssue;
	/**摇奖号码*/
	private String drawCode;
	/**商户该期中奖总额*/
	private Integer bonusMoney;
	/**返奖详细*/
	private List<BonusDetailInfo> bonusDetails;	
	/**响应代码*/
	private String responseCode;
	
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
	 * @return the bonusMoney
	 */
	public Integer getBonusMoney() {
		return bonusMoney;
	}
	/**
	 * @param bonusMoney the bonusMoney to set
	 */
	public void setBonusMoney(Integer bonusMoney) {
		this.bonusMoney = bonusMoney;
	}
	
	/**
	 * @return the bonusDetail
	 */
	public List<BonusDetailInfo> getBonusDetails() {
		return bonusDetails;
	}
	/**
	 * @param bonusDetail the bonusDetail to set
	 */
	public void setBonusDetails(List<BonusDetailInfo> bonusDetails) {
		this.bonusDetails = bonusDetails;
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
