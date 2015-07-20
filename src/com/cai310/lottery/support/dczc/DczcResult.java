package com.cai310.lottery.support.dczc;

import java.io.Serializable;

import com.cai310.lottery.support.Item;

/**
 * 单场足彩开奖结果对象
 * 
 */
public class DczcResult implements Serializable {
	private static final long serialVersionUID = 3493928665282431719L;

	/** 场次序号 */
	private Integer lineId;

	/** 开奖结果 */
	private Item resultItem;

	/** 开奖SP值 */
	private Double resultSp;

	/** 比赛是否 取消 */
	private boolean cancel;

	/**
	 * @return {@link #lineId}
	 */
	public Integer getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the {@link #lineId} to set
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return {@link #resultItem}
	 */
	public Item getResultItem() {
		return resultItem;
	}

	/**
	 * @param resultItem the {@link #resultItem} to set
	 */
	public void setResultItem(Item resultItem) {
		this.resultItem = resultItem;
	}

	/**
	 * @return {@link #resultSp}
	 */
	public Double getResultSp() {
		return resultSp;
	}

	/**
	 * @param resultSp the {@link #resultSp} to set
	 */
	public void setResultSp(Double resultSp) {
		this.resultSp = resultSp;
	}

	/**
	 * @return {@link #cancel}
	 */
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * @param cancel the {@link #cancel} to set
	 */
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
}
