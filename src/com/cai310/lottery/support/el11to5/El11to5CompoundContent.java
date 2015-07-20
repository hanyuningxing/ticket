package com.cai310.lottery.support.el11to5;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class El11to5CompoundContent {
	/** 注数 */
	private Integer units;

	/** 除选二直选，前三直选外投注号码 */
	private List<String> betList;
	/**胆码,选二直选，前三直选,任选一没有胆拖功能**/
	private List<String> betDanList;

	/** 选二直选(前二)，前三直选投注号码 */
	private List<String> bet1List;
	private List<String> bet2List;
	private List<String> bet3List;

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the betList
	 */
	public List<String> getBetList() {
		return betList;
	}

	/**
	 * @param betList the betList to set
	 */
	public void setBetList(List<String> betList) {
		this.betList = betList;
	}

	/**
	 * @return the bet1List
	 */
	public List<String> getBet1List() {
		return bet1List;
	}

	/**
	 * @param bet1List the bet1List to set
	 */
	public void setBet1List(List<String> bet1List) {
		this.bet1List = bet1List;
	}

	/**
	 * @return the bet2List
	 */
	public List<String> getBet2List() {
		return bet2List;
	}

	/**
	 * @param bet2List the bet2List to set
	 */
	public void setBet2List(List<String> bet2List) {
		this.bet2List = bet2List;
	}

	/**
	 * @return the bet3List
	 */
	public List<String> getBet3List() {
		return bet3List;
	}

	/**
	 * @param bet3List the bet3List to set
	 */
	public void setBet3List(List<String> bet3List) {
		this.bet3List = bet3List;
	}

	/**
	 * @return the betDanList
	 */
	public List<String> getBetDanList() {
		return betDanList;
	}

	/**
	 * @param betDanList the betDanList to set
	 */
	public void setBetDanList(List<String> betDanList) {
		this.betDanList = betDanList;
	}

}
