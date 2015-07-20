package com.cai310.lottery.ticket.dto;
public class JclqMatchItemDTO{
	private static final long serialVersionUID = -2542287020185175888L;

	/** 场次标识 */
	private String matchKey;

	/** 选择的内容 */
	private String value;

	/** 是否设胆 */
	private boolean dan;

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isDan() {
		return dan;
	}

	public void setDan(boolean dan) {
		this.dan = dan;
	}
	
}
