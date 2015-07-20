package com.cai310.lottery.support.zc;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;

/**
 * 足彩玩法类型(14场、9场)
 * 
 */
public enum PlayType {
	/** 14场胜负彩 */
	SFZC14("14场胜负彩"),

	/** 任选九场 */
	SFZC9("任选九场");

	private final String text;

	private PlayType(String text) {
		this.text = text;
	}

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}

	public static PlayType valueOfName(String name) {
		if (StringUtils.isNotBlank(name)) {
			for (PlayType type : PlayType.values()) {
				if (type.name().equals(name))
					return type;
			}
		}
		return null;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
