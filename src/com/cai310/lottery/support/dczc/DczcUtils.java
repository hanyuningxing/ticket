package com.cai310.lottery.support.dczc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.SalesMode;

public final class DczcUtils {

	public static int getAheadEndMinute(SalesMode salesMode, PassMode passMode) {
		switch (salesMode) {
		case COMPOUND:
			switch (passMode) {
			case MULTIPLE:
				return DczcConstant.COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE;
			case NORMAL:
				return DczcConstant.COMPOUND_AHEAD_END_NORMAL_PASS_MODE;
			default:
				throw new RuntimeException("过关模式不合法.");
			}
		case SINGLE:
			return DczcConstant.SINGLE_AHEAD_END;
		default:
			throw new RuntimeException("投注方式不合法.");
		}
	}

	public static Date getMatchEndTime(SalesMode salesMode, PassMode passMode) {
		int aheadEndMinute = getAheadEndMinute(salesMode, passMode);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, aheadEndMinute);
		return calendar.getTime(); // endTime为当前时间加上提前时间
	}

	public static String genExtraPrizeMsg(Map<String, CombinationBean> combinationMap) {
		if (combinationMap == null || combinationMap.isEmpty())
			return null;

		NumberFormat format = new DecimalFormat("0.00");
		StringBuilder sb = new StringBuilder();
		final char c = ':';
		final char c2 = '_';
		final char c3 = ';';
		for (CombinationBean bean : combinationMap.values()) {
			sb.append(bean.generateKey()).append(c).append(format.format(bean.getPrize())).append(c2).append(
					format.format(bean.getPrizeAfterTax())).append(c3);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
