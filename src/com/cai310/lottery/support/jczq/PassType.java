package com.cai310.lottery.support.jczq;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.utils.MathUtils;
import com.google.common.collect.Lists;

/**
 * 竞彩足球过关方式
 * 
 */
public enum PassType {
	/** 单关 */
	P1(1, new int[] { 1 },"01") {
	},

	/** 2串1 */
	P2_1(2, new int[] { 2 },"02") {
	},

	/** 3串1 */
	P3_1(3, new int[] { 3 },"02") {
	},

	/** 3串3 */
	P3_3(3, new int[] { 2 },"03") {
	},

	/** 3串4 */
	P3_4(3, new int[] { 2, 3 },"04") {
	},

	/** 4串1 */
	P4_1(4, new int[] { 4 },"02") {
	},

	/** 4串4 */
	P4_4(4, new int[] { 3 },"03") {
	},

	/** 4串5 */
	P4_5(4, new int[] { 3, 4 },"04") {
	},

	/** 4串6 */
	P4_6(4, new int[] { 2 },"05") {
	},

	/** 4串11 */
	P4_11(4, new int[] { 2, 3, 4 },"06") {
	},

	/** 5串1 */
	P5_1(5, new int[] { 5 },"02") {
	},

	/** 5串5 */
	P5_5(5, new int[] { 4 },"03") {
	},

	/** 5串6 */
	P5_6(5, new int[] { 4, 5 },"04") {
	},

	/** 5串10 */
	P5_10(5, new int[] { 2 },"05") {
	},

	/** 5串16 */
	P5_16(5, new int[] { 3, 4, 5 },"06") {
	},

	/** 5串20 */
	P5_20(5, new int[] { 2, 3 },"07") {
	},

	/** 5串26 */
	P5_26(5, new int[] { 2, 3, 4, 5 },"08") {
	},

	/** 6串1 */
	P6_1(6, new int[] { 6 },"02") {
	},

	/** 6串6 */
	P6_6(6, new int[] { 5 },"03") {
	},

	/** 6串7 */
	P6_7(6, new int[] { 5, 6 },"04") {
	},

	/** 6串15 */
	P6_15(6, new int[] { 2 },"05") {
	},

	/** 6串20 */
	P6_20(6, new int[] { 3 },"06") {
	},

	/** 6串22 */
	P6_22(6, new int[] { 4, 5, 6 },"07") {
	},

	/** 6串35 */
	P6_35(6, new int[] { 2, 3 },"08") {
	},

	/** 6串42 */
	P6_42(6, new int[] { 3, 4, 5, 6 },"09") {
	},

	/** 6串50 */
	P6_50(6, new int[] { 2, 3, 4 },"10") {
	},

	/** 6串57 */
	P6_57(6, new int[] { 2, 3, 4, 5, 6 },"11") {
	},
	/** 7串1 */
	P7_1(7,new int[] { 7 },"02") {
	},
	/** 7串7 */
	P7_7(7,new int[] { 6 },"03") {
	},
	/** 7串8 */
	P7_8(7,new int[] { 6 , 7 },"04") {
	},
	/** 7串21 */
	P7_21(7,new int[] { 5 },"05") {
	},
	/** 7串35 */
	P7_35(7,new int[] { 4 },"06") {
	},
	/** 7串120 */
	P7_120(7,new int[] { 2, 3, 4, 5, 6 ,7 },"07") {
	},
	/** 8串1 */
	P8_1(8,new int[] { 8 },"02") {
	},
	/** 8串8 */
	P8_8(8,new int[] { 7 },"03") {
	},
	/** 8串9 */
	P8_9(8,new int[] { 7 ,8 },"04") {
	},
	/** 8串1 */
	P8_28(8,new int[] { 6 },"05") {
	},
	/** 8串1 */
	P8_56(8,new int[] { 5 },"06") {
	},
	/** 8串70 */
	P8_70(8,new int[] { 4 },"07") {
	},
	/** 8串247 */
	P8_247(8,new int[] { 2, 3, 4, 5, 6 ,7,8  },"08") {
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
	private final long value;
	
	/** 出票商过关代号名称 */
	private final String cpText;

	// =========================================================================

	/**
	 * 单场过关方式
	 * 
	 * @param index
	 *            用二进制的第index位表示该过关方式
	 * @param matchCount
	 *            选择的场次数目
	 * @param passMatchs
	 *            过关的场次数目数组
	 */
	private PassType(int matchCount, int[] passMatchs,String cpText) {
		int index = this.ordinal();

		this.value = 1l << index;

		this.passMatchs = passMatchs;

		this.matchCount = matchCount;

		int units = 0;
		for (int passMatch : passMatchs) {
			units += MathUtils.comp(passMatch, this.matchCount);
		}
		this.units = units;
        this.cpText = cpText;
		if (this.matchCount == 1) {
			this.text = "单关";
		} else {
			this.text = this.matchCount + "x" + this.units;
		}
	}

	public static List<PassType> findPassTypes(int pass, int maxMatchSize) {
		List<PassType> list = new ArrayList<PassType>();
		for (PassType passType : PassType.values()) {
			if (passType.getMatchCount() <= maxMatchSize && passType.getPassMatchs().length == 1
					&& passType.getPassMatchs()[0] == pass) {
				list.add(passType);
			}
		}
		return list;
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

	public long getValue() {
		return value;
	}

	// =========================================================================

	/**
	 * 是否选择了该过关方式
	 * 
	 * @param mixPtValue
	 *            过关方式值（多个过关方式的组合值）
	 * @return 是或否
	 */
	public boolean isSelect(long mixPtValue) {
		return (mixPtValue & getValue()) != 0;
	}

	// =========================================================================

	/**
	 * 根据类型字符串获取过关方式类型
	 * 
	 * @param typeName
	 *            类型字符串
	 * @return 过关方式类型
	 */
	public static PassType valueOfType(String typeName) {
		if (StringUtils.isNotBlank(typeName)) {
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
	 * @param value
	 *            类型值
	 * @return 过关方式类型
	 */
	public static PassType getSpPassType(long value) {
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
	 * @param mixPtValue
	 *            多个过关方式的组合值
	 * @return 过关方式类型集合
	 */
	public static List<PassType> getPassTypes(long mixPtValue) {
		List<PassType> sets = Lists.newArrayList();
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
	 * @param mixPtValue
	 *            多个过关方式的组合值
	 * @return 过关方式字符串
	 */
	public static String getPassTypeText(long mixPtValue) {
		StringBuffer sb = new StringBuffer();
		if (mixPtValue < 1 || mixPtValue > (1l << values().length)) {
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

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	public String getCpText() {
		return cpText;
	}

}
