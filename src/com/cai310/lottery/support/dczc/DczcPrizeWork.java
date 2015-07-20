package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DczcPrizeWork implements Serializable {
	private static final long serialVersionUID = -5425385793316608633L;

	/** 各个中奖组合中奖信息的分隔符 */
	public static final String lineSeq = "\r\n";

	// ===================================================================

	/** 中奖详情 */
	protected StringBuilder wonDetail = new StringBuilder();

	/** 奖金详情 */
	protected StringBuilder prizeDetail = new StringBuilder();

	/** 存放中奖的组合的MAP */
	protected Map<String, CombinationBean> combinationMap = new HashMap<String, CombinationBean>();// 存放中奖的组合

	/** 税前总奖金 */
	protected double totalPrize = 0.0d;

	/** 税后总奖金 */
	protected double totalPrizeAfterTax = 0.0d;

	/** 是否中奖 */
	protected boolean won;

	// ===================================================================

	/** 开奖结果 */
	protected final Map<Integer, DczcResult> resultMap;

	protected DczcPrizeWork(Map<Integer, DczcResult> resultMap) {
		super();
		this.resultMap = resultMap;
	}

	// ===================================================================

	protected void handle(PassType passType, List<DczcMatchItem> combList) {
		DczcPrizeItem prizeItem = new DczcPrizeItem(resultMap, combList);
		wonDetail.append(prizeItem.getLineWonDetail()).append(lineSeq);
		prizeDetail.append(prizeItem.getLinePrizeDetail()).append(lineSeq);
		totalPrize += prizeItem.getPrize();
		totalPrizeAfterTax += prizeItem.getPrizeAfterTax();

		handleCombinationMap(passType, combList, prizeItem);
	}
	
	protected void handleCombinationMap(PassType passType, List<DczcMatchItem> combList, DczcPrizeItem prizeItem) {
		CombinationBean combBean = new CombinationBean();
		combBean.setPassTypeOrdinal(passType.ordinal());
		combBean.setItems(combList);
		combBean.setPrize(prizeItem.getPrize());
		combBean.setPrizeAfterTax(prizeItem.getPrizeAfterTax());
		combinationMap.put(combBean.generateKey(), combBean);
	}

	// ===================================================================

	public StringBuilder getWonDetail() {
		return wonDetail;
	}

	public StringBuilder getPrizeDetail() {
		return prizeDetail;
	}

	public Map<String, CombinationBean> getCombinationMap() {
		return combinationMap;
	}

	public double getTotalPrize() {
		return totalPrize;
	}

	public double getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	public boolean isWon() {
		return won;
	}
}
