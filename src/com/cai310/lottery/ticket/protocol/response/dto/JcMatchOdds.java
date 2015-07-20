package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.PlayType;


public class JcMatchOdds {
	private String matchKey;
	private Map<String, Double> options;
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public Map<String, Double> getOptions() {
		return options;
	}
	public void setOptions(Map<String, Double> options) {
		this.options = options;
	}
	
}
