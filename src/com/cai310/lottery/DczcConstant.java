package com.cai310.lottery;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class DczcConstant {
	public static final String KEY = "dczc";

	/** 复式普通过关模式提前截止时间，单位分钟 */
	public static int COMPOUND_AHEAD_END_NORMAL_PASS_MODE = 15;

	/** 复式多选过关模式提前截止时间，单位分钟 */
	public static int COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE = 15;//cyy-c 2011-4-12 赛前20分  zhuhui2011-0617 15分钟

	/** 单式提前截止时间，单位分钟 */
	public static int SINGLE_AHEAD_END = 15;//cyy-c 2011-4-12 赛前20分 zhuhui2011-0617 15分钟

	public static final int[] AHEAD_END_ARR = { COMPOUND_AHEAD_END_NORMAL_PASS_MODE,
			COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE };

	public static final String[] PASS_TEXT = { "单场", "二关", "三关", "四关", "五关", "六关", "七关", "八关", "九关", "十关", "十一关",
			"十二关", "十三关", "十四关", "十五关" };

	/** 取消比赛的SP值 */
	public static final double CANCEL_MATCH_RESULT_SP = 1.0d;

	/** SP值 格式化对象 */
	public static final NumberFormat SP_FORMAT = new DecimalFormat("0.000000");

	/** 金额 格式化对象 */
	public static final NumberFormat MONEY_FORMAT = new DecimalFormat("0.00");

	/** 返奖率 */
	public static final double PRIZE_RETURN_RATE = 0.65d;
}
