package com.cai310.lottery.support.klpk;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class KlpkCompoundContent {
	/** 注数 */
	private Integer units;

	/** 除选二直选，前三直选外投注号码 */
	private List<String> betList;
	/**胆码,选二直选，前三直选,任选一没有胆拖功能**/
	private List<String> betDanList;

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
