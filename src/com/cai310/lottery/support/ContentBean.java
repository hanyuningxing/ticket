package com.cai310.lottery.support;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ContentBean {
	/** 注数 */
	private Integer units;

	/** 内容 */
	private String content;

	/**
	 * @param units {@link #units}
	 * @param content {@link #content}
	 */
	public ContentBean(Integer units, String content) {
		super();
		this.units = units;
		this.content = content;
	}

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
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
