package com.cai310.lottery.common;

/**
 * 彩票的类别
 * 
 */
public enum LotteryCategory {
	/** 数字彩 */
	NUMBER("数字彩"),
	/** 单场足彩 */
	DCZC("单场足彩"),
	/** 高频彩 */
	FREQUENT("高频彩"),
	/** 足彩 */
	ZC("足彩"),

	/** 竞彩 */
	JC("竞彩");

	/** 彩票分类名称 */
	private final String categoryName;

	/**
	 * @param categoryName {@link #categoryName}
	 */
	private LotteryCategory(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return {@link #categoryName}
	 */
	public String getCategoryName() {
		return categoryName;
	}
}
