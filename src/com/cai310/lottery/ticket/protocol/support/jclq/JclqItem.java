package com.cai310.lottery.ticket.protocol.support.jclq;

public  class JclqItem{
	private String value;
	private Double award=0d;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Double getAward() {
		return award;
	}
	public void setAward(Double award) {
		this.award = award;
	}
}
