package com.cai310.lottery.support.dczc;

import java.util.ArrayList;
import java.util.List;

import com.cai310.utils.MathUtils;

public enum PassType {
	/** 单关 */
	P1(new int[] { 1 }) {
	},

	/** 2串1 */
	P2_1(new int[] { 2 }) {
	},

	/** 2串3 */
	P2_3(new int[] { 1, 2 }) {
	},

	/** 3串1 */
	P3_1(new int[] { 3 }) {
	},

	/** 3串4 */
	P3_4(new int[] { 2, 3 }) {
	},

	/** 3串7 */
	P3_7(new int[] { 1, 2, 3 }) {
	},

	/** 4串1 */
	P4_1(new int[] { 4 }) {
	},

	/** 4串5 */
	P4_5(new int[] { 3, 4 }) {
	},

	/** 4串11 */
	P4_11(new int[] { 2, 3, 4 }) {
	},

	/** 4串15 */
	P4_15(new int[] { 1, 2, 3, 4 }) {
	},

	/** 5串1 */
	P5_1(new int[] { 5 }) {
	},

	/** 5串6 */
	P5_6(new int[] { 4, 5 }) {
	},

	/** 5串16 */
	P5_16(new int[] { 3, 4, 5 }) {
	},

	/** 5串26 */
	P5_26(new int[] { 2, 3, 4, 5 }) {
	},

	/** 5串31 */
	P5_31(new int[] { 1, 2, 3, 4, 5 }) {
	},

	/** 6串1 */
	P6_1(new int[] { 6 }) {
	},

	/** 6串7 */
	P6_7(new int[] { 5, 6 }) {
	},

	/** 6串22 */
	P6_22(new int[] { 4, 5, 6 }) {
	},

	/** 6串42 */
	P6_42(new int[] { 3, 4, 5, 6 }) {
	},

	/** 6串57 */
	P6_57(new int[] { 2, 3, 4, 5, 6 }) {
	},

	/** 6串63 */
	P6_63(new int[] { 1, 2, 3, 4, 5, 6 }) {
	},

	/** 7串1 */
	P7_1(new int[] { 7 }) {
	},

	/** 8串1 */
	P8_1(new int[] { 8 }) {
	},

	/** 9串1 */
	P9_1(new int[] { 9 }) {
	},

	/** 10串1 */
	P10_1(new int[] { 10 }) {
	},

	/** 11串1 */
	P11_1(new int[] { 11 }) {
	},

	/** 12串1 */
	P12_1(new int[] { 12 }) {
	},

	/** 13串1 */
	P13_1(new int[] { 13 }) {
	},

	/** 14串1 */
	P14_1(new int[] { 14 }) {
	},

	/** 15串1 */
	P15_1(new int[] { 15 }) {
	};

	// =========================================================================

	/** 单选注数 */
	private final int units;

	/** 选择的场次数目 */
	private final int matchCount;

	/** 过关的场次数目数组 */
	private final int[] passMatchs;

	/** 过关方式名称 */
	private final String text;

	/** 过关方式值，用一个二进制位表示 */
	private final int value;

	// =========================================================================

	/**
	 * 单场过关方式
	 * 
	 * @param passMatchs 过关的场次数目数组
	 */
	private PassType(int[] passMatchs) {
		this(-1, passMatchs);
	}

	/**
	 * 单场过关方式
	 * 
	 * @param index 用二进制的第index位表示该过关方式
	 * @param passMatchs 过关的场次数目数组
	 */
	private PassType(int index, int[] passMatchs) {
		this(index, passMatchs[passMatchs.length - 1], passMatchs);
	}

	/**
	 * 单场过关方式
	 * 
	 * @param index 用二进制的第index位表示该过关方式
	 * @param matchCount 选择的场次数目
	 * @param passMatchs 过关的场次数目数组
	 */
	private PassType(int index, int matchCount, int[] passMatchs) {
		if (index < 0)
			index = this.ordinal();

		this.value = 1 << index;

		this.passMatchs = passMatchs;

		this.matchCount = matchCount;

		int units = 0;
		for (int passMatch : passMatchs) {
			units += MathUtils.comp(passMatch, this.matchCount);
		}
		this.units = units;

		if (this.matchCount == 1) {
			this.text = "单关";
		} else {
			this.text = this.matchCount + "串" + this.units;
		}
	}

	// =========================================================================

	public int getUnits() {
		return units;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public int[] getPassMatchs() {
		return passMatchs;
	}

	public String getText() {
		return text;
	}

	public int getValue() {
		return value;
	}

	// =========================================================================

	/**
	 * 是否选择了该过关方式
	 * 
	 * @param mixPtValue 过关方式值（多个过关方式的组合值）
	 * @return 是或否
	 */
	public boolean isSelect(int mixPtValue) {
		return (mixPtValue & getValue()) != 0;
	}

	/**
	 * 
	 * @return 是否是多选过关模式可用的过关方式
	 */
	public boolean isForMultipleMode() {
		return this.passMatchs.length == 1;
	}

	// =========================================================================

	/**
	 * 根据类型字符串获取过关方式类型
	 * 
	 * @param typeName 类型字符串
	 * @return 过关方式类型
	 */
	public static PassType valueOfType(String typeName) {
		if (typeName != null && !typeName.trim().equals("")) {
			for (PassType spPassType : values()) {
				if (spPassType.toString().equalsIgnoreCase(typeName)) {
					return spPassType;
				}
			}
		}
		return null;
	}

	/**
	 * 根据类型值获取过关方式类型
	 * 
	 * @param value 类型值
	 * @return 过关方式类型
	 */
	public static PassType getPassType(int value) {
		for (PassType spPassType : values()) {
			if (spPassType.value == value) {
				return spPassType;
			}
		}
		return null;
	}

	/**
	 * 根据多个过关方式的组合值获取过关方式类型集合
	 * 
	 * @param mixPtValue 多个过关方式的组合值
	 * @return 过关方式类型集合
	 */
	public static List<PassType> getPassTypes(int mixPtValue) {
		List<PassType> sets = new ArrayList<PassType>();
		for (PassType spPassType : values()) {
			if ((spPassType.value & mixPtValue) != 0) {
				sets.add(spPassType);
			}
		}
		return sets.isEmpty() ? null : sets;
	}

	/**
	 * 获取过关方式字符串
	 * 
	 * @param mixPtValue 多个过关方式的组合值
	 * @return 过关方式字符串
	 */
	public static String getPassTypeText(int mixPtValue) {
		StringBuffer sb = new StringBuffer();
		if (mixPtValue < 1 || mixPtValue > (1 << values().length)) {
			return null;
		}
		String separator = ",";
		for (PassType pt : values()) {
			if (pt.isSelect(mixPtValue)) {
				sb.append(pt.text).append(separator);
			}
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - separator.length(), sb.length());
		}
		return sb.toString();
	}
}
