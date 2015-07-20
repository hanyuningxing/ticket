package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案分享类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum ShareType {
	/** 合买 */
	TOGETHER("合买 "),
	/** 自购 */
	SELF("自购");

	/** 类型名称 */
	private final String shareName;

	/**
	 * @param shareName {@link #shareName}
	 */
	private ShareType(String shareName) {
		this.shareName = shareName;
	}

	/**
	 * @return {@link #shareName}
	 */
	public String getShareName() {
		return shareName;
	}
	/**
	 * @param ordinal 
	 * @return SalesMode
	 */
	public static ShareType valueOfOrdinal(Integer ordinal) {
		if (null!=ordinal) {
			for (ShareType l : ShareType.values()) {
				if (ordinal.equals(l.ordinal()))return l;
			}
		}
		return null;
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
