package com.cai310.lottery.ticket.protocol.response.dto;

import java.io.Serializable;
import java.util.Date;

public class IssueInfo implements Serializable{
	
	private static final long serialVersionUID = 642385720031784355L;
	
	/**玩法编号*/
	private String game;
	/**奖期号*/
	private String gameIssue;
	/**官方奖期开启时间，格式： yyyy-MM-dd HH:mm:ss*/
	private Date startTime;
	/**官方奖期截止时间，格式同startTime*/
	private Date endTime;
	/**奖期状态：0-未开启，1-开启，2-暂停，3-截止，4-开号（得到开奖号码），5-返奖（可以进行销量和返奖查询），6-期结*/
	private String state;
	/**开奖号码，默认值：-1。号码以‘，’隔开，有特别号码的以‘|’隔开，胆拖以‘*’隔开*/
	private String drawCode;
	/**商户本期销售金额*/
	private String salesMoney;
	/**商户本期中奖金额*/
	private String bonusMoney;	
	/**响应代码*/
	private String responseCode;
	
	private Date singleEndTime;
	private Date compoundEndTime;
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
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @return the salesMoney
	 */
	public String getSalesMoney() {
		return salesMoney;
	}
	/**
	 * @param salesMoney the salesMoney to set
	 */
	public void setSalesMoney(String salesMoney) {
		this.salesMoney = salesMoney;
	}
	/**
	 * @return the bonusMoney
	 */
	public String getBonusMoney() {
		return bonusMoney;
	}
	/**
	 * @param bonusMoney the bonusMoney to set
	 */
	public void setBonusMoney(String bonusMoney) {
		this.bonusMoney = bonusMoney;
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
	
	public Date getSingleEndTime() {
		return singleEndTime;
	}
	public void setSingleEndTime(Date singleEndTime) {
		this.singleEndTime = singleEndTime;
	}
	public Date getCompoundEndTime() {
		return compoundEndTime;
	}
	public void setCompoundEndTime(Date compoundEndTime) {
		this.compoundEndTime = compoundEndTime;
	}
	
}
