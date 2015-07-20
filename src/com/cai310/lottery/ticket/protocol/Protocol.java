package com.cai310.lottery.ticket.protocol;

import java.io.IOException;
import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.ticket.common.TypeTransaction;
import com.cai310.lottery.ticket.protocol.response.dto.IssueInfo;
import com.cai310.lottery.ticket.protocol.response.dto.SeqOddsInfo;

public interface Protocol {
	
	String getAgentId();
	/**
	 * 分解数据，将数据转化为相应出票商可发送的结构
	 * @return 分解后的数据列表
	 */
	List<Ticket> disassembleData(PrintInterface printData) throws Exception;
	
	/**
	 * 发送交易信息
	 * @return xml响应内容
	 */
	String messageSend()throws IOException;	
		
	/**
	 * 设置彩票类型
	 * @param lottery
	 */
	void setLottery(Lottery lottery);
	
	/**
	 * 设置子玩法类型
	 * @param betType
	 */
	void setBetType(Byte betType);
	
	/**
	 * 设置操作期号
	 * @param gameIssue
	 */
	void setGameIssue(String gameIssue);
	
	/**
	 * 协议递增流水号
	 * @param incremenIndex
	 */
	void setIncremenIndex(Long incremenIndex);
	
	/**
	 * 设置彩票ID
	 * @param ticketId
	 */
	void setTicketId(String ticketId);
	
	/**
	 * 设置发送票集合
	 * @param ticketList
	 */
	void setTicketList(List<Ticket> ticketList);
	
	/**
	 * 设置查询的票Id集合
	 * @param ticketIdList
	 */
	void setTicketIdList(List<Long> ticketIdList);
	
	/**
	 * 设置交易类型
	 * @param typeTransaction
	 */
	void setTypeTransaction(TypeTransaction typeTransaction);
	
	/**
	 * 解析返回期数据信息解析
	 * @param responseMessage
	 */
	List<IssueInfo> parseResponseIssue(String responseMessage);
	
	/**
	 * 获取开奖公告
	 */
	String parseResponseIssueResult(String responseMessage);
	
	/**
	 * 解析场次赔率
	 * @param responseMessage
	 * @return
	 */
	public List<SeqOddsInfo> parseResponseDczcOdds(String responseMessage);
	
}
