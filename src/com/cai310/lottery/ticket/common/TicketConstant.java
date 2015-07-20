package com.cai310.lottery.ticket.common;

/**
 * 出票常量类
 * @author jack
 *
 */
public class TicketConstant {

	//作用于表：LOTU_PRINT_INTERFACE_INDEX
	public static final Long PRINTINTERFASE_INDEX_ID = 1L;//出票接口表索引ID
	
	public static final Long BOHAN_INDEX_ID = 2L;//博涵出票方自增流水号ID
	
	public static final Long CAITONG_INDEX_ID = 3L;//彩通出票方自增流水号ID
	
	public static int QUERY_MAXRESULTS = 50;//查询返回的最大记录数
	
	public static int SEND_MAXRESULTS = 50;//出票的最大记录数
	
	public static int QUERY_MIN = 24*60;//回查分钟数
	
	public static int LIMIT_QUERY_MAXRESULTS = 100000;//查询返回的最大记录数
}
