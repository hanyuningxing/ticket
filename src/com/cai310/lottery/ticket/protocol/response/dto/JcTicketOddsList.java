package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.PlayType;


public class JcTicketOddsList {
	
	/**方案编号*/
	private Long schemeId;
	private List<JcMatchOddsList> awardList;
	
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
