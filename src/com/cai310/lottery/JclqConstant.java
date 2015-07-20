package com.cai310.lottery;

public final class JclqConstant {
	public static final String KEY = "jclq";
	
	/** 非星期六日截止的时间 */
	public static final String UNWEEKEND_EDN_TIME = "22:20";
	
	/** 星期六日截止的时间 */
	public static final String WEEKEND_EDN_TIME = "0:20";

	/** 场次提前截止的时间，单位（分钟） */
	public static final int MATCH_AHEAD_TIME = 20;

	/** 单关允许选择最大的场次 */
	public static final int SINGLE_MAX_MATCH_SIZE = 8;

	/** 出票机一张票允许的最高金额 */
	public static final int TICKET_MAX_BETCOST = 20000;

	/** 出票机一张票允许的最高倍数 */
	public static final int TICKET_MAX_MULTIPLE = 99;

	public static final String REFERENCE_VALUE_KEY = "referenceValue";

	/** 出票单关允许最大的场次数目 */
	public static final int TICKET_SINGLE_MAX_MATCH_SIZE = 8;

	public static Integer getMaxPrize(int pass) {
		switch (pass) {
		case 2:
		case 3:
			return 200000;
		case 4:
		case 5:
			return 500000;
		case 6:
			return 1000000;
		}
		return null;
	}
}
