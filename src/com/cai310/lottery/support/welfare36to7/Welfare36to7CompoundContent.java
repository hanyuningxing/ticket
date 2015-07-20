package com.cai310.lottery.support.welfare36to7;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Welfare36to7CompoundContent {
	/** 注数 */
	private Integer units;

	/** 36选7号码 */
	private List<String> welfare36to7List;

	/** 好彩1号码 */
	private List<String> haocai1List;

	/** 好彩2号码 */
	private List<String> haocai2List;

	/** 好彩3号码 */
	private List<String> haocai3List;

	/** 生肖号码 */
	private List<String> zodiacList;

	/** 季节号码 */
	private List<String> seasonList;

	/** 方位号码 */
	private List<String> azimuthList;

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
	 * @return the welfare36to7List
	 */
	public List<String> getWelfare36to7List() {
		return welfare36to7List;
	}

	/**
	 * @param welfare36to7List the welfare36to7List to set
	 */
	public void setWelfare36to7List(List<String> welfare36to7List) {
		this.welfare36to7List = welfare36to7List;
	}

	/**
	 * @return the zodiacList
	 */
	public List<String> getZodiacList() {
		return zodiacList;
	}

	/**
	 * @param zodiacList the zodiacList to set
	 */
	public void setZodiacList(List<String> zodiacList) {
		this.zodiacList = zodiacList;
	}

	/**
	 * @return the seasonList
	 */
	public List<String> getSeasonList() {
		return seasonList;
	}

	/**
	 * @param seasonList the seasonList to set
	 */
	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}

	/**
	 * @return the azimuthList
	 */
	public List<String> getAzimuthList() {
		return azimuthList;
	}

	/**
	 * @param azimuthList the azimuthList to set
	 */
	public void setAzimuthList(List<String> azimuthList) {
		this.azimuthList = azimuthList;
	}

	/**
	 * @return the haocai1List
	 */
	public List<String> getHaocai1List() {
		return haocai1List;
	}

	/**
	 * @param haocai1List the haocai1List to set
	 */
	public void setHaocai1List(List<String> haocai1List) {
		this.haocai1List = haocai1List;
	}

	/**
	 * @return the haocai2List
	 */
	public List<String> getHaocai2List() {
		return haocai2List;
	}

	/**
	 * @param haocai2List the haocai2List to set
	 */
	public void setHaocai2List(List<String> haocai2List) {
		this.haocai2List = haocai2List;
	}

	/**
	 * @return the haocai3List
	 */
	public List<String> getHaocai3List() {
		return haocai3List;
	}

	/**
	 * @param haocai3List the haocai3List to set
	 */
	public void setHaocai3List(List<String> haocai3List) {
		this.haocai3List = haocai3List;
	}

}
