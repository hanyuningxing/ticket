package com.cai310.lottery.ticket.disassemble.jclq;

import java.util.List;

import com.cai310.lottery.support.jclq.JclqMatchItem;
public  class JclqPrintItemObj{
	private int ticketIndex;
	private int schemeTypeOrdinal;
	private int passModeOrdinal;
	private int playTypeOrdinal;
	private long passTypeOrdinal;
	private long passType;
	private String firstEndTime;
	private String firstMatchTime;
	private Integer betUnits;
	private List <JclqMatchItem> items;
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
	public List<JclqMatchItem> getItems() {
		return items;
	}
	public void setItems(List<JclqMatchItem> items) {
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
	public long getPassTypeOrdinal() {
		return passTypeOrdinal;
	}
	public void setPassTypeOrdinal(long passTypeOrdinal) {
		this.passTypeOrdinal = passTypeOrdinal;
	}
	public long getPassType() {
		return passType;
	}
	public void setPassType(long passType) {
		this.passType = passType;
	}
	public String getFirstMatchTime() {
		return firstMatchTime;
	}
	public void setFirstMatchTime(String firstMatchTime) {
		this.firstMatchTime = firstMatchTime;
	}
}
