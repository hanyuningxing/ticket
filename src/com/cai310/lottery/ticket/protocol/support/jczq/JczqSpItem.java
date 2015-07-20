package com.cai310.lottery.ticket.protocol.support.jczq;

import java.util.List;

import com.cai310.lottery.support.jczq.PlayType;
public  class JczqSpItem{
	
	private List <JczqItem> options;
	private Double referenceValue;
	private Integer intTime;
	private Integer lineId;
	/** 混合配 */
	private PlayType playType;
	public List<JczqItem> getOptions() {
		return options;
	}
	public void setOptions(List<JczqItem> options) {
		this.options = options;
	}
	public Integer getIntTime() {
		return intTime;
	}
	public void setIntTime(Integer intTime) {
		this.intTime = intTime;
	}
	public Integer getLineId() {
		return lineId;
	}
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	public Double getReferenceValue() {
		return referenceValue;
	}
	public void setReferenceValue(Double referenceValue) {
		this.referenceValue = referenceValue;
	}
	public PlayType getPlayType() {
		return playType;
	}
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	
	
}
