package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.PlayType;


public class JcMatchOddsList {
	
	/**拆票序号*/
	private Integer ticketIndex;
	private String ticketCode;
	private List<JcMatchOdds> jcMatchOdds;

	
	public Integer getTicketIndex() {
		return ticketIndex;
	}
	public void setTicketIndex(Integer ticketIndex) {
		this.ticketIndex = ticketIndex;
	}
	public List<JcMatchOdds> getJcMatchOdds() {
		return jcMatchOdds;
	}
	public void setJcMatchOdds(List<JcMatchOdds> jcMatchOdds) {
		this.jcMatchOdds = jcMatchOdds;
	}
	public String getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}
}
