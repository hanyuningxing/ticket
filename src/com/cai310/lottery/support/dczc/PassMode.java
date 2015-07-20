package com.cai310.lottery.support.dczc;

import com.cai310.lottery.Constant;

public enum PassMode {
	NORMAL("普通过关"), MULTIPLE("多选过关");

	private final String text;

	private PassMode(String text) {
		this.text = text;
	}

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
