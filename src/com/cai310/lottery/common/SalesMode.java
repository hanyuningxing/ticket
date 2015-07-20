package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案投注的方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SalesMode {
	COMPOUND("复式"), SINGLE("单式");

	/** 方案投注的方式名称 */
	private final String modeName;

	/**
	 * @param modeName {@link #modeName}
	 */
	private SalesMode(String modeName) {
		this.modeName = modeName;
	}

	/**
	 * @return {@link #modeName}
	 */
	public String getModeName() {
		return modeName;
	}
	/**
	 * @param ordinal 
	 * @return SalesMode
	 */
	public static SalesMode valueOfOrdinal(Integer ordinal) {
		if (null!=ordinal) {
			for (SalesMode l : SalesMode.values()) {
				if (ordinal.equals(l.ordinal()))return l;
			}
		}
		return null;
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
