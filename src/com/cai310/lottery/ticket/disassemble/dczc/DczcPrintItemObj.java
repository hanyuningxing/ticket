package com.cai310.lottery.ticket.disassemble.dczc;

import java.util.List;

import com.cai310.lottery.support.dczc.DczcMatchItem;
public  class DczcPrintItemObj{
	private int ticketIndex;
	private int passModeOrdinal;
	private int playTypeOrdinal;
	private long passTypeOrdinal;
	private String firstEndTime;
	private Integer betUnits;
	private List <DczcMatchItem> items;
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
	public List<DczcMatchItem> getItems() {
		return items;
	}
	public void setItems(List<DczcMatchItem> items) {
		this.items = items;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
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
}
