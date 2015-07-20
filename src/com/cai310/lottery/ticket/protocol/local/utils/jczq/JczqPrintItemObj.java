package com.cai310.lottery.ticket.protocol.local.utils.jczq;

import java.util.List;
public  class JczqPrintItemObj{
	private int ticketIndex;
	private int schemeTypeOrdinal;
	private int passModeOrdinal;
	private int playTypeOrdinal;
	private long passType;
	private String firstEndTime;
	private Integer betUnits;
	private List <JczqPrintItem> items;
	private Integer multiple;
	public String getFirstEndTime() {
		return firstEndTime;
	}
	public void setFirstEndTime(String firstEndTime) {
		this.firstEndTime = firstEndTime;
	}
	public Integer getBetUnits() {
		return betUnits;
	}
	public void setBetUnits(Integer betUnits) {
		this.betUnits = betUnits;
	}
	public List<JczqPrintItem> getItems() {
		return items;
	}
	public void setItems(List<JczqPrintItem> items) {
		this.items = items;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public int getSchemeTypeOrdinal() {
		return schemeTypeOrdinal;
	}
	public void setSchemeTypeOrdinal(int schemeTypeOrdinal) {
		this.schemeTypeOrdinal = schemeTypeOrdinal;
	}
	public int getPassModeOrdinal() {
		return passModeOrdinal;
	}
	public void setPassModeOrdinal(int passModeOrdinal) {
		this.passModeOrdinal = passModeOrdinal;
	}
	public int getPlayTypeOrdinal() {
		return playTypeOrdinal;
	}
	public void setPlayTypeOrdinal(int playTypeOrdinal) {
		this.playTypeOrdinal = playTypeOrdinal;
	}
	public int getTicketIndex() {
		return ticketIndex;
	}
	public void setTicketIndex(int ticketIndex) {
		this.ticketIndex = ticketIndex;
	}
	public long getPassType() {
		return passType;
	}
	public void setPassType(long passType) {
		this.passType = passType;
	}
}
