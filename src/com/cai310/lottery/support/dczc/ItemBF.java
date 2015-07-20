package com.cai310.lottery.support.dczc;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.support.Item;

/**
 * 单场足彩-比分玩法选项
 * 
 */
public enum ItemBF implements Item {
	/** 1:0 */
	WIN10("10", "1:0"),

	/** 2:0 */
	WIN20("20", "2:0"),

	/** 2:1 */
	WIN21("21", "2:1"),

	/** 3:0 */
	WIN30("30", "3:0"),

	/** 3:1 */
	WIN31("31", "3:1"),

	/** 3:2 */
	WIN32("32", "3:2"),

	/** 4:0 */
	WIN40("40", "4:0"),

	/** 4:1 */
	WIN41("41", "4:1"),

	/** 4:2 */
	WIN42("42", "4:2"),

	/** 胜其他 */
	WIN_OTHER("WW", "胜其他"),

	/** 0:0 */
	DRAW00("00", "0:0"),

	/** 1:1 */
	DRAW11("11", "1:1"),

	/** 2:2 */
	DRAW22("22", "2:2"),

	/** 3:3 */
	DRAW33("33", "3:3"),

	/** 平其他 */
	DRAW_OTHER("DD", "平其他"),

	/** 0:1 */
	LOSE01("01", "0:1"),

	/** 0:2 */
	LOSE02("02", "0:2"),

	/** 1:2 */
	LOSE12("12", "1:2"),

	/** 0:3 */
	LOSE03("03", "0:3"),

	/** 1:3 */
	LOSE13("13", "1:3"),

	/** 2:3 */
	LOSE23("23", "2:3"),

	/** 0:4 */
	LOSE04("04", "0:4"),

	/** 4:1 */
	LOSE14("14", "1:4"),

	/** 2:4 */
	LOSE24("24", "2:4"),

	/** 负其他 */
	LOSE_OTHER("LL", "负其他");

	private final String value;
	private final String text;

	public static final ItemBF[] WINS = { WIN10, WIN20, WIN21, WIN30, WIN31, WIN32, WIN40, WIN41, WIN42, WIN_OTHER };
	public static final ItemBF[] DRAWS = { DRAW00, DRAW11, DRAW22, DRAW33, DRAW_OTHER };
	public static final ItemBF[] LOSES = { LOSE01, LOSE02, LOSE12, LOSE03, LOSE13, LOSE23, LOSE04, LOSE14, LOSE24,
			LOSE_OTHER };

	private ItemBF(String value, String text) {
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
	public static ItemBF valueOfValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ItemBF type : ItemBF.values()) {
				if (type.getValue().equals(value))
					return type;
			}
		}
		return null;
	}
}
