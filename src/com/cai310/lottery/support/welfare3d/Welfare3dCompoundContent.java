package com.cai310.lottery.support.welfare3d;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Welfare3dCompoundContent {
	/** 注数 */
	private Integer units;

	/** 百位号码 */
	private List<String> area1List;

	/** 十位号码 */
	private List<String> area2List;

	/** 个位号码 */
	private List<String> area3List;

	/** 组三号码 */
	private List<String> group3List;

	/** 组六号码 */
	private List<String> group6List;

	/** 直选和值号码 */
	private List<String> directSumList;

	/** 组选和值号码 */
	private List<String> groupSumList;
	
	/** 包串号码 */
	private List<String> baoChuanList;
	
	/** 跨度号码 */
	private List<String> directKuaduList;
	
	/** 跨度号码 */
	private List<String> g3KuaduList;
	
	/** 跨度号码 */
	private List<String> g6KuaduList;

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

	public List<String> getBaoChuanList() {
		return baoChuanList;
	}

	public void setBaoChuanList(List<String> baoChuanList) {
		this.baoChuanList = baoChuanList;
	}

	public List<String> getDirectKuaduList() {
		return directKuaduList;
	}

	public void setDirectKuaduList(List<String> directKuaduList) {
		this.directKuaduList = directKuaduList;
	}

	public List<String> getG3KuaduList() {
		return g3KuaduList;
	}

	public void setG3KuaduList(List<String> g3KuaduList) {
		this.g3KuaduList = g3KuaduList;
	}

	public List<String> getG6KuaduList() {
		return g6KuaduList;
	}

	public void setG6KuaduList(List<String> g6KuaduList) {
		this.g6KuaduList = g6KuaduList;
	}
}
