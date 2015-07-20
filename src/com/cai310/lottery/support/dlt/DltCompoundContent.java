package com.cai310.lottery.support.dlt;

import java.util.List;

public class DltCompoundContent {
	/** 注数 */
	private Integer units;

	/** 红球胆码 */
	private List<String> redDanList;

	/** 红球号码 */
	private List<String> redList;

	/** 蓝球号码 */
	private List<String> blueList;

	/** 蓝球胆码 */
	private List<String> blueDanList;

	/**
	 * @return {@link #units}
	 */

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return the redDanList
	 */
	public List<String> getRedDanList() {
		return redDanList;
	}

	/**
	 * @param redDanList the redDanList to set
	 */
	public void setRedDanList(List<String> redDanList) {
		this.redDanList = redDanList;
	}

	/**
	 * @return the redList
	 */
	public List<String> getRedList() {
		return redList;
	}

	/**
	 * @param redList the redList to set
	 */
	public void setRedList(List<String> redList) {
		this.redList = redList;
	}

	/**
	 * @return the blueList
	 */
	public List<String> getBlueList() {
		return blueList;
	}

	/**
	 * @param blueList the blueList to set
	 */
	public void setBlueList(List<String> blueList) {
		this.blueList = blueList;
	}

	/**
	 * @return the blueDanList
	 */
	public List<String> getBlueDanList() {
		return blueDanList;
	}

	/**
	 * @param blueDanList the blueDanList to set
	 */
	public void setBlueDanList(List<String> blueDanList) {
		this.blueDanList = blueDanList;
	}
}
