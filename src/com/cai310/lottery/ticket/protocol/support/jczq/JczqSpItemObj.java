package com.cai310.lottery.ticket.protocol.support.jczq;

import java.util.List;
public  class JczqSpItemObj{
	private int index;
	private List <JczqSpItem> awardList;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<JczqSpItem> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<JczqSpItem> awardList) {
		this.awardList = awardList;
	}
	
}
