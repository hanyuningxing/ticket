package com.cai310.lottery.ticket.protocol.localnew.utils.jczq;

public  class JczqPrintMixItem extends JczqPrintItem{
	
	private Integer[] Choices;

	private Integer BetType;
	
	public Integer getBetType() {
		return BetType;
	}

	public void setBetType(Integer betType) {
		BetType = betType;
	}

	public Integer[] getChoices() {
		return Choices;
	}

	public void setChoices(Integer[] choices) {
		Choices = choices;
	}

	
}
