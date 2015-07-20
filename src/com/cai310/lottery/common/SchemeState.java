package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案状态类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SchemeState {
	/** 未满 */
	UNFULL("未满"),

	/** 满员 */
	FULL("满员"),

	/** 成功 */
	SUCCESS("成功"),

	/** 撤销 */
	CANCEL("撤销"),

	/** 退款 */
	REFUNDMENT("退款");

	/** 状态名称 */
	private final String stateName;

	/** 终止状态归类 **/
	public final static SchemeState[] finalStatuss = { SUCCESS, CANCEL };

	/** 方案不成功的状态 */
	public final static SchemeState[] UNSUCCESS = { CANCEL, REFUNDMENT };

	/**
	 * @param state {@link #stateName}
	 */
	private SchemeState(String state) {
		this.stateName = state;
	}
	/**
	 * @param ordinal 
	 * @return SalesMode
	 */
	public static SchemeState valueOfOrdinal(Integer ordinal) {
		if (null!=ordinal) {
			for (SchemeState l : SchemeState.values()) {
				if (ordinal.equals(l.ordinal()))return l;
			}
		}
		return null;
	}
	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
