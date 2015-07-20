package com.cai310.lottery.support;

import java.util.List;

import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.PlayType;

public class JcPrintContent {
	private int ticketIndex;
	private int schemeTypeOrdinal;
	private int passModeOrdinal;
	private int playTypeOrdinal;
	private String content;
	private String ticketContent;
	private long passType;
	private List<JczqMatchItem> selectList;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getPassType() {
		return passType;
	}

	public void setPassType(long passType) {
		this.passType = passType;
	}

	public String getTicketContent() {
		return ticketContent;
	}

	public void setTicketContent(String ticketContent) {
		this.ticketContent = ticketContent;
	}

	public List<JczqMatchItem> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<JczqMatchItem> selectList) {
		this.selectList = selectList;
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
}
