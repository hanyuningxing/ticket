package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DczcPasscountWork implements Serializable {
	private static final long serialVersionUID = -5425385793316608633L;

	/** 各个中奖组合中奖信息的分隔符 */
	public static final String lineSeq = "\r\n";

	// ===================================================================

	/** 存放中奖的组合的MAP */
	protected Map<String, CombinationBean> combinationMap = new HashMap<String, CombinationBean>();// 存放中奖的组合
	/** 是否中奖 */
	protected boolean won;
	
	/** 选中的场次*/
	protected int betCount;
	
	/** 全中的注数 */
	protected int wonCount;
	
	protected int multiple=1;

	// ===================================================================

	protected void handle(PassType passType, List<DczcMatchItem> combList) {
		handleCombinationMap(passType, combList);
	}
	
	protected void handleCombinationMap(PassType passType, List<DczcMatchItem> combList) {
		CombinationBean combBean = new CombinationBean();
		combBean.setPassTypeOrdinal(passType.ordinal());
		combBean.setItems(combList);
		combinationMap.put(combBean.generateKey(), combBean);
	}

	// ===================================================================

	public Map<String, CombinationBean> getCombinationMap() {
		return combinationMap;
	}

	public boolean isWon() {
		return won;
	}

	/**
	 * @return the betCount
	 */
	public int getBetCount() {
		return betCount;
	}

	/**
	 * @param betCount the betCount to set
	 */
	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	/**
	 * @return the wonCount
	 */
	public int getWonCount() {
		return wonCount;
	}

	/**
	 * @param wonCount the wonCount to set
	 */
	public void setWonCount(int wonCount) {
		this.wonCount = wonCount;
	}

	/**
	 * @param won the won to set
	 */
	public void setWon(boolean won) {
		this.won = won;
	}

	/**
	 * @return the multiple
	 */
	public int getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
}
