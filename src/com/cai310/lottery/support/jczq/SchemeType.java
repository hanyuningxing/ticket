package com.cai310.lottery.support.jczq;

import com.cai310.lottery.Constant;


public enum SchemeType {
	/** 单关 */
	SINGLE("单关"),

	/** 普通过关 */
	SIMPLE_PASS("普通过关"),

	/** 多选过关 */
	MULTIPLE_PASS("多选过关");

	private final String text;

	private SchemeType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
