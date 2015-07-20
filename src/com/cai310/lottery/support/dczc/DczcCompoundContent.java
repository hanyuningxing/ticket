package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.List;

/**
 * 单场足彩复式内容Bean
 * 
 */
public class DczcCompoundContent implements Serializable {
	private static final long serialVersionUID = -1902401847461051686L;

	/** 选择的场次内容 */
	private List<DczcMatchItem> items;

	/** 胆码最小命中数 */
	private Integer danMinHit;

	/** 胆码最大命中数 */
	private Integer danMaxHit;

	/**
	 * @return {@link #items}
	 */
	public List<DczcMatchItem> getItems() {
		return items;
	}

	/**
	 * @param items the {@link #items} to set
	 */
	public void setItems(List<DczcMatchItem> items) {
		this.items = items;
	}

	/**
	 * @return {@link #danMinHit}
	 */
	public Integer getDanMinHit() {
		return danMinHit;
	}

	/**
	 * @param danMinHit the {@link #danMinHit} to set
	 */
	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	/**
	 * @return {@link #danMaxHit}
	 */
	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	/**
	 * @param danMaxHit the {@link #danMaxHit} to set
	 */
	public void setDanMaxHit(Integer danMaxHit) {
		this.danMaxHit = danMaxHit;
	}

}
