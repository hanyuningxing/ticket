package com.cai310.lottery.ticket.common;

public enum TypeTransaction {
	
	JQTZXY("奖期通知响应"),
	JQCX("奖期查询"),
	FALDWT_KENO("高频方案落地委托"),
	DCPLCX("单场赔率查询"),
	FALDWT("方案落地委托"),
	LDJGTZXY("落地结果通知响应"),
	FAJGCX("方案结果查询"),
	FAJGCX_KENO("方案结果查询"),
	FJTZXY("返奖通知响应"),
	DQZJCX("单期中奖查询"),
	FZZJCX("方案中奖查询"),
	KJGGCX("开奖公告查询"),
	SHZHCX("商户账户查询"),
	SJTBCX("时间同步查询"),
	FALDWT_JC("竞彩落地结果通知响应"),
	FAJGCX_JC("方案结果查询"),
	JQCX_JC("竞彩奖期查询"),
	CPSP_JC("竞彩出票SP查询"),
	ZZSP_JC("竞彩最终SP查询"); 
	
	/**交易说明*/
	private final String text;

	private TypeTransaction(String text) {
		this.text = text;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
