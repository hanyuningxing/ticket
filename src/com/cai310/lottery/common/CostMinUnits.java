package com.cai310.lottery.common;

/**
 * 金额的最小单位类型
 * 
 */
public enum CostMinUnits {
	/** 元 */
	YUAN("元", 0),

	/** 角 */
	ANGLE("角", 1),

	/** 分 */
	SUB("分", 2);

	/** 名称 */
	private final String typeName;

	/** 以元为基本单位,精确到小数点后几位 */
	private final int scale;

	/**
	 * @param typeName {@link #typeName}
	 * @param scale {@link #scale}
	 */
	private CostMinUnits(String typeName, int scale) {
		this.typeName = typeName;
		this.scale = scale;
	}

	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @return {@link #scale}
	 */
	public int getScale() {
		return scale;
	}

}
