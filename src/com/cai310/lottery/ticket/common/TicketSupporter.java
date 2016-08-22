package com.cai310.lottery.ticket.common;

import com.cai310.lottery.Constant;

/**
 * 出票商枚举定义
 * @author jack
 *
 */
public enum TicketSupporter {
	HONGBO("鸿博公司"),
	TICAI("体彩公司"),
	RLYG("睿朗阳光公司"),
	BOHAN("博涵公司"),
	BOZHONG("博众公司"),
	CAITONG("彩通公司"),
	CAI310("CAI310"),
	CPDYJ("彩票大赢家"),
	ZUNAO("尊傲"),
	TIANJ("天津"),
	LIANG("量彩"),
	YUECAI("粤彩"),//11
	WIN310("本地"),//12热点
	ZHONG("中彩"),//中彩
	QIU310("鸿"),
	LOCAL("本地自动"),
	BEIDAN("北单");
	private final String supporterName;
	
	private TicketSupporter(String supporterName) {
		this.supporterName = supporterName;
	}

	/**
	 * @return the supporterName
	 */
	public String getSupporterName() {
		return supporterName;
	}
	
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
