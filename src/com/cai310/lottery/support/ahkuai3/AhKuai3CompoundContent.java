package com.cai310.lottery.support.ahkuai3;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AhKuai3CompoundContent {
	/** 注数 */
	private Integer units;

	/** 投注号码 */
	private List<String> betList;
	
	/**二同号单选*/
	private List<String> disList;
	
	/**胆码,选二不同号，三不同号有胆拖功能**/
	private List<String> betDanList;
	
	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}
	
	public List<String> getBetList() {
		return betList;
	}

	public void setBetList(List<String> betList) {
		this.betList = betList;
	}

	public List<String> getBetDanList() {
		return betDanList;
	}

	public void setBetDanList(List<String> betDanList) {
		this.betDanList = betDanList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<String> getDisList() {
		return disList;
	}

	public void setDisList(List<String> disList) {
		this.disList = disList;
	}
}
