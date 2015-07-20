package com.cai310.lottery.support.dczc;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.support.Item;

/**
 * 单场足彩-总进球数玩法选项
 * 
 */
public enum ItemZJQS implements Item {
	/** 0进球 */
	S0("0", "0"),

	/** 1进球 */
	S1("1", "1"),

	/** 2进球 */
	S2("2", "2"),

	/** 3进球 */
	S3("3", "3"),

	/** 4进球 */
	S4("4", "4"),

	/** 5进球 */
	S5("5", "5"),

	/** 6进球 */
	S6("6", "6"),

	/** 7个以上进球 */
	S7("7", "7+");

	private final String value;
	private final String text;

	private ItemZJQS(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	/**
	 * 根据值获取对应的类型,找不到对应的类型返回null.
	 */
	public static ItemZJQS valueOfValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ItemZJQS type : ItemZJQS.values()) {
				if (type.getValue().equals(value))
					return type;
			}
		}
		return null;
	}
}
