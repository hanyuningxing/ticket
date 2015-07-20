package com.cai310.lottery.ticket.protocol.local.utils.jczq;

import java.util.List;
public  class JczqPrintItem{
	
	private List <JczqItem> options;
	private Integer concede = 0;
	private Boolean shedan;
	private Integer intTime;
	private Integer lineId;
	private String homeName;
	private String guestName;
	private String matchTime;
	private Integer subBetType;
	public List<JczqItem> getOptions() {
		return options;
	}
	public void setOptions(List<JczqItem> options) {
		this.options = options;
	}
	public Integer getConcede() {
		return concede;
	}
	public void setConcede(Integer concede) {
		this.concede = concede;
	}
	public Boolean getShedan() {
		return shedan;
	}
	public void setShedan(Boolean shedan) {
		this.shedan = shedan;
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
	public String getHomeName() {
		return homeName;
	}
	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public Integer getSubBetType() {
		return subBetType;
	}
	public void setSubBetType(Integer subBetType) {
		this.subBetType = subBetType;
	}
	
}
