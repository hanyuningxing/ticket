package com.cai310.lottery.support.dczc;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.support.Item;

/**
 * 单场足彩玩法类型
 * 
 */
public enum PlayType {
	/** 让球胜平负 */
	SPF("让球胜平负", 15, ItemSPF.values()),

	/** 总进球数 */
	ZJQS("总进球数", 6, ItemZJQS.values()),

	/** 上下单双 */
	SXDS("上下单双", 6, ItemSXDS.values()),

	/** 比分 */
	BF("比分", 3, ItemBF.values()),

	/** 半全场胜平负 */
	BQQSPF("半全场胜平负", 6, ItemBQQSPF.values());

	/** 玩法名称 */
	private final String text;

	/** 此玩法所能选择的最大场次数目 */
	private final int maxMatchSize;

	private final Item[] allItems;

	private PlayType(String text, int maxMatchSize, Item[] allItems) {
		this.text = text;
		this.maxMatchSize = maxMatchSize;
		this.allItems = allItems;
	}

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return {@link #maxMatchSize}
	 */
	public int getMaxMatchSize() {
		return maxMatchSize;
	}

	/**
	 * @return {@link #allItems}
	 */
	public Item[] getAllItems() {
		return allItems;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	public static PlayType valueOfName(String name) {
		if (StringUtils.isNotBlank(name)) {
			for (PlayType type : PlayType.values()) {
				if (type.name().equals(name))
					return type;
			}
		}
		return null;
	}
}
