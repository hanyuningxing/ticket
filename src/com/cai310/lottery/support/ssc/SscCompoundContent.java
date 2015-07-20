package com.cai310.lottery.support.ssc;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SscCompoundContent {
	/** 注数 */
	private Integer units;

	/** 万位号码 */
	private List<String> area1List;    //五星标准

	/** 千位号码 */
	private List<String> area2List;    //五星  标准 

	/** 百位号码 */
	private List<String> area3List;    //五星 三星 标准 

	/** 十位号码 */
	private List<String> area4List;    //五星 三星 二星   标准  大小双单

	/** 个位号码 */
	private List<String> area5List;    //五星 三星 二星 一星  标准  大小双单
	
	/** 三星组三  单复式 号码 */
	private List<String> group3List;

	/** 三星组六   复式  号码 */
	private List<String> group6List;
	
	/** 两星组选  号码 */
	private List<String> groupTwoList;

	/** 2星直选和值 、3星直选和值  */
	private List<String> directSumList;

	/** 2星组选和值 、3星组选和值*/
	private List<String> groupSumList;

	private List<String> betList;
	
	/**3星组三胆拖、3星组六胆拖、2星组三胆拖、2星组六胆拖  胆码 **/
	private List<String> betDanList;
	
	

	public List<String> getGroupTwoList() {
		return groupTwoList;
	}

	public void setGroupTwoList(List<String> groupTwoList) {
		this.groupTwoList = groupTwoList;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public List<String> getArea1List() {
		return area1List;
	}

	public void setArea1List(List<String> area1List) {
		this.area1List = area1List;
	}

	public List<String> getArea2List() {
		return area2List;
	}

	public void setArea2List(List<String> area2List) {
		this.area2List = area2List;
	}

	public List<String> getArea3List() {
		return area3List;
	}

	public void setArea3List(List<String> area3List) {
		this.area3List = area3List;
	}

	public List<String> getGroup3List() {
		return group3List;
	}

	public void setGroup3List(List<String> group3List) {
		this.group3List = group3List;
	}

	public List<String> getGroup6List() {
		return group6List;
	}

	public void setGroup6List(List<String> group6List) {
		this.group6List = group6List;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<String> getDirectSumList() {
		return directSumList;
	}

	public void setDirectSumList(List<String> directSumList) {
		this.directSumList = directSumList;
	}

	public List<String> getGroupSumList() {
		return groupSumList;
	}

	public void setGroupSumList(List<String> groupSumList) {
		this.groupSumList = groupSumList;
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
}
