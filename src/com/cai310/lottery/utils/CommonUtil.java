package com.cai310.lottery.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

public class CommonUtil {

	/**
	 * 获取用于页面倒计时的参数
	 * 
	 * @param date
	 *            结束时间
	 * @return
	 */
	public static Map<String, Integer> getTimerMap(Date date) {
		if (date == null)
			return null;

		long leave = date.getTime() - System.currentTimeMillis();
		if (leave <= 0)
			return null;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("day", Integer.valueOf((int) (leave / DateUtils.MILLIS_PER_DAY)));
		leave = leave % DateUtils.MILLIS_PER_DAY;
		map.put("hour", Integer.valueOf((int) (leave / DateUtils.MILLIS_PER_HOUR)));
		leave = leave % DateUtils.MILLIS_PER_HOUR;
		map.put("minute", Integer.valueOf((int) (leave / DateUtils.MILLIS_PER_MINUTE)));
		leave = leave % DateUtils.MILLIS_PER_MINUTE;
		map.put("second", Integer.valueOf((int) (leave / DateUtils.MILLIS_PER_SECOND)));
		return map;
	}

	public static final String combinePath(String base, String child) {
		base = base.trim();
		child = child.trim();
		if (base.endsWith("/") || base.endsWith("\\")) {
			if (child.startsWith("/") || child.startsWith("\\"))
				return base + child.substring(1);
			else
				return base + child;
		} else {
			if (child.startsWith("/") || child.startsWith("\\"))
				return base + child;
			else
				return base + '/' + child;
		}
	}

	/**
	 * 对奖金进行特定的舍入操作
	 * 
	 * @param prize
	 *            奖金
	 * @return 进行舍入操作后的奖金
	 */
	public static double roundPrize(double prize) {
		BigDecimal decimal = new BigDecimal(prize);
		decimal = decimal.setScale(3, BigDecimal.ROUND_DOWN);// 保留三位小数，后面的舍去
		decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);// 保留两位小数，1.235=1.23，1.236=1.24
		return decimal.doubleValue();
	}
}
