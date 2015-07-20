package com.cai310.lottery.support.tc22to5;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Tc22to5CompoundContent implements Serializable {
	private static final long serialVersionUID = -2262153302752654987L;

	/** 注数 */
	private Integer units;

	/** 胆码 */
	private List<String> danList;

	/** 号码 */
	private List<String> betList;


	/**
	 * @return {@link #units}
	 */
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the {@link #units} to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return {@link #danList}
	 */
	public List<String> getDanList() {
		return danList;
	}

	/**
	 * @param danList the {@link #danList} to set
	 */
	public void setDanList(List<String> danList) {
		this.danList = danList;
	}

	/**
	 * @return {@link #betList}
	 */
	public List<String> getBetList() {
		return betList;
	}

	/**
	 * @param betList the {@link #betList} to set
	 */
	public void setBetList(List<String> betList) {
		this.betList = betList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
