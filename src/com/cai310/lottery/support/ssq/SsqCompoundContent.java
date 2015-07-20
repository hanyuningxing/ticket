package com.cai310.lottery.support.ssq;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SsqCompoundContent implements Serializable {
	private static final long serialVersionUID = -2262153302752654987L;

	/** 注数 */
	private Integer units;

	/** 红球胆码 */
	private List<String> redDanList;

	/** 红球号码 */
	private List<String> redList;

	/** 蓝球号码 */
	private List<String> blueList;

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
	 * @return {@link #redDanList}
	 */
	public List<String> getRedDanList() {
		return redDanList;
	}

	/**
	 * @param redDanList the {@link #redDanList} to set
	 */
	public void setRedDanList(List<String> redDanList) {
		this.redDanList = redDanList;
	}

	/**
	 * @return {@link #redList}
	 */
	public List<String> getRedList() {
		return redList;
	}

	/**
	 * @param redList the {@link #redList} to set
	 */
	public void setRedList(List<String> redList) {
		this.redList = redList;
	}

	/**
	 * @return {@link #blueList}
	 */
	public List<String> getBlueList() {
		return blueList;
	}

	/**
	 * @param blueList the {@link #blueList} to set
	 */
	public void setBlueList(List<String> blueList) {
		this.blueList = blueList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
