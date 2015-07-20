package com.cai310.lottery.support;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cai310.lottery.utils.BigDecimalUtil;

public class PrizeItem implements Serializable {
	private static final long serialVersionUID = -6318863003314825427L;

	public static final int PRIZE_TAX_MIN_MONEY = 10000;
	public static final double TAX_RATE = 0.2d;

	private WinItem winItem;
	private BigDecimal unitPrize;

	public PrizeItem(WinItem winItem, BigDecimal unitPrize) {
		super();
		this.winItem = winItem;
		this.unitPrize = unitPrize;
	}

	public WinItem getWinItem() {
		return winItem;
	}

	public void setWinItem(WinItem winItem) {
		this.winItem = winItem;
	}

	public BigDecimal getUnitPrize() {
		return unitPrize;
	}

	public BigDecimal getUnitPrizeAfterTax() {
		if (unitPrize.doubleValue() >= PRIZE_TAX_MIN_MONEY) {
			return BigDecimalUtil.multiply(unitPrize, BigDecimalUtil.valueOf(1 - TAX_RATE));
		}
		return unitPrize;
	}

	public void setUnitPrize(BigDecimal unitPrize) {
		this.unitPrize = unitPrize;
	}

}
