package com.cai310.lottery.support.jczq;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.support.Item;

/**
 * 竞彩足球-胜平负玩法选项
 * 
 */
public enum ItemSPF implements Item {
	/** 胜 */
	WIN("3", "胜"),

	/** 平 */
	DRAW("1", "平"),

	/** 负 */
	LOSE("0", "负");

	private final String value;
	private final String text;

	private ItemSPF(String value, String text) {
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
	public static ItemSPF valueOfValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ItemSPF type : ItemSPF.values()) {
				if (type.getValue().equals(value))
					return type;
			}
		}
		return null;
	}
}
