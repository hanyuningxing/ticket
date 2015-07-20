package com.cai310.lottery.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {
	public static final int SCALE = 2;
	public static final int ROUND_MODE = BigDecimal.ROUND_DOWN;
	public static final int MULTIPLE = (int) Math.pow(10, BigDecimalUtil.SCALE);

	/**
	 * 先将两个金额进行处理，保留两位小数，舍去之后的小数， 然后两个金额相乘，相乘的结果保留两位小数，舍去之后的小数
	 * 
	 * @param money1 金额1
	 * @param money2 金额2
	 * @return 两数相乘的结果，保留两位小数，舍去之后的小数
	 */
	public static BigDecimal multiply(BigDecimal money1, BigDecimal money2) {
		return round(round(money1).multiply(round(money2)));
	}

	/**
	 * 先将两个金额进行处理，保留两位小数，舍去之后的小数， 然后两个金额相除，相除的结果保留两位小数，舍去之后的小数
	 * 
	 * @param money1 金额1
	 * @param money2 金额2
	 * @return 两数相除的结果，保留两位小数，舍去之后的小数
	 */
	public static BigDecimal divide(BigDecimal money1, BigDecimal money2) {
		return round(money1).divide(round(money2), SCALE, ROUND_MODE);
	}

	/**
	 * 保留两位小数，舍去之后的小数
	 * 
	 * @param money 金额
	 * @return 处理后结果
	 */
	public static BigDecimal round(BigDecimal money) {
		return money.setScale(SCALE, ROUND_MODE);
	}

	public static BigDecimal valueOf(long money) {
		return BigDecimal.valueOf(money);
	}

	/**
	 * 保留两位小数，舍去之后的小数
	 * 
	 * @param money 金额
	 */
	public static BigDecimal valueOf(double money) {
		return round(BigDecimal.valueOf(money));
	}

	/**
	 * 保留两位小数，舍去之后的小数
	 * 
	 * @param money 金额
	 */
	public static BigDecimal valueOf(float money) {
		return valueOf(String.valueOf(money));
	}

	/**
	 * 保留两位小数，舍去之后的小数
	 * 
	 * @param money 金额
	 */
	public static BigDecimal valueOf(String money) {
		return round(BigDecimal.valueOf(Double.valueOf(money)));
	}
	
	/**
	 * 保留两位小数，舍去之后的小数
	 * 123.01 to 12301
	 * @param money 金额
	 */
	public static int intValueOfToMoney(String money) {
		BigDecimal bigDecimal = round(BigDecimal.valueOf(Double.valueOf(money)));
		return (int) (bigDecimal.doubleValue()*100);
	}
}
