package com.cai310.lottery.ticket.protocol.localnew.utils.jclq;

import java.util.List;

import com.cai310.lottery.ticket.protocol.localnew.utils.jclq.SpItemValue;

public class SpPrintItem {
	private String Line;
	private Double Extra;//让球或总分
	private List<SpItemValue> Choices;
	public String getLine() {
		return Line;
	}
	public void setLine(String line) {
		Line = line;
	}

	public List<SpItemValue> getChoices() {
		return Choices;
	}
	public void setChoices(List<SpItemValue> choices) {
		Choices = choices;
	}
	public Double getExtra() {
		return Extra;
	}
	public void setExtra(Double extra) {
		Extra = extra;
	}
}
