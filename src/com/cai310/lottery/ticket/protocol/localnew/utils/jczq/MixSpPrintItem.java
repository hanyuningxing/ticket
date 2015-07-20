package com.cai310.lottery.ticket.protocol.localnew.utils.jczq;

import java.util.List;

public  class MixSpPrintItem{
	private String Line;
	private Integer BetType;
	private Double Extra;
	private List<MixSpItemValue> Choices;
	public String getLine() {
		return Line;
	}
	public void setLine(String line) {
		Line = line;
	}

	public List<MixSpItemValue> getChoices() {
		return Choices;
	}
	public void setChoices(List<MixSpItemValue> choices) {
		Choices = choices;
	}
	public Integer getBetType() {
		return BetType;
	}
	public void setBetType(Integer BetType) {
		this.BetType = BetType;
	}
	public Double getExtra() {
		return Extra;
	}
	public void setExtra(Double extra) {
		Extra = extra;
	}
	
	
}
