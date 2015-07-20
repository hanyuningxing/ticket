package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.List;

public  class JcMatchOddsListObj{
	private List<JcMatchOddsList> awardList;
	private Long schemeId;

	public Long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public List<JcMatchOddsList> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<JcMatchOddsList> awardList) {
		this.awardList = awardList;
	}
}