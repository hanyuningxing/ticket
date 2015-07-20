package com.cai310.lottery.ticket.protocol.support.jclq;

import java.util.List;
public  class JclqSpItemObj{
	private int index;
	private List <JclqSpItem> awardList;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<JclqSpItem> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<JclqSpItem> awardList) {
		this.awardList = awardList;
	}
	
}
