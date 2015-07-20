package com.cai310.lottery.support.seven;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SevenCompoundContent {
	/** 注数 */
	private Integer units;

	/** 红球胆码 */
	private List<String> danList;

	/** 红球号码 */
	private List<String> ballList;

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

	public List<String> getDanList() {
		return danList;
	}

	public void setDanList(List<String> danList) {
		this.danList = danList;
	}

	public List<String> getBallList() {
		return ballList;
	}

	public void setBallList(List<String> ballList) {
		this.ballList = ballList;
	}
}
