package com.cai310.lottery.support;

import java.util.List;

public interface PrizeWork {
	String getWonDetail();

	String getPrizeDetail();
	
	List<PrintWonItem>  getPrintWonItemList();

	double getTotalPrize();

	double getTotalPrizeAfterTax();

	boolean isWon();
}
