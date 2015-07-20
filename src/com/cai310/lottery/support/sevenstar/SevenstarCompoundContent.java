package com.cai310.lottery.support.sevenstar;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SevenstarCompoundContent {
	/** 注数 */
	private Integer units;

	/** 第一位号码 */
	private List<String> area1List;

	/** 第二位号码 */
	private List<String> area2List;

	/** 第三位号码 */
	private List<String> area3List;

	/** 第四位号码 */
	private List<String> area4List;

	/** 第五位号码 */
	private List<String> area5List;

	/** 第六位号码 */
	private List<String> area6List;

	/** 第七位号码 */
	private List<String> area7List;

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
	 * @return the area1List
	 */
	public List<String> getArea1List() {
		return area1List;
	}

	/**
	 * @param area1List the area1List to set
	 */
	public void setArea1List(List<String> area1List) {
		this.area1List = area1List;
	}

	/**
	 * @return the area2List
	 */
	public List<String> getArea2List() {
		return area2List;
	}

	/**
	 * @param area2List the area2List to set
	 */
	public void setArea2List(List<String> area2List) {
		this.area2List = area2List;
	}

	/**
	 * @return the area3List
	 */
	public List<String> getArea3List() {
		return area3List;
	}

	/**
	 * @param area3List the area3List to set
	 */
	public void setArea3List(List<String> area3List) {
		this.area3List = area3List;
	}

	/**
	 * @return the area4List
	 */
	public List<String> getArea4List() {
		return area4List;
	}

	/**
	 * @param area4List the area4List to set
	 */
	public void setArea4List(List<String> area4List) {
		this.area4List = area4List;
	}

	/**
	 * @return the area5List
	 */
	public List<String> getArea5List() {
		return area5List;
	}

	/**
	 * @param area5List the area5List to set
	 */
	public void setArea5List(List<String> area5List) {
		this.area5List = area5List;
	}

	/**
	 * @return the area6List
	 */
	public List<String> getArea6List() {
		return area6List;
	}

	/**
	 * @param area6List the area6List to set
	 */
	public void setArea6List(List<String> area6List) {
		this.area6List = area6List;
	}

	/**
	 * @return the area7List
	 */
	public List<String> getArea7List() {
		return area7List;
	}

	/**
	 * @param area7List the area7List to set
	 */
	public void setArea7List(List<String> area7List) {
		this.area7List = area7List;
	}
}
