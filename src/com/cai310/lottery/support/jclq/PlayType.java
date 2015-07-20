package com.cai310.lottery.support.jclq;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.support.Item;

/**
 * 竞彩篮球玩法类型
 * 
 */
public enum PlayType {
	/** 胜负 */
	SF("胜负", ItemSF.values(), 8),

	/** 让分胜负 */
	RFSF("让分胜负", ItemRFSF.values(), 8),

	/** 胜分差 */
	SFC("胜分差", ItemSFC.values(), 3),

	/** 大小分 */
	DXF("大小分", ItemDXF.values(), 8),
	/** 混合串 */
	MIX("混合串", null ,3);
	/** 玩法名称 */
	private final String text;

	/** 此玩法所能选择的最大场次数目 */
	private final int maxMatchSize;

	private final Item[] allItems;

	private PlayType(String text, Item[] allItems, int maxMatchSize) {
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
	public Item getItemByItemValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (Item item : this.allItems) {
				if(item.getValue().trim().equalsIgnoreCase(value.trim())){
					return item;
				}
			}
		}
		return null;
	}
}
