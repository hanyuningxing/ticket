package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqPrintContent;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqPrintContent;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.common.TypeTransaction;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItem;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItem;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.yuecai.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.yuecai.DczcYuecaiUtil;
import com.cai310.lottery.ticket.protocol.yuecai.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.yuecai.YuecaiUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class TicketTransactionTask_yuecai {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	List<TicketQuery> ticketQuerys = new ArrayList<TicketQuery>();
	public void runTask_query() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.YUECAI);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				Lottery lottery = lotterySupporter.getLotteryType();
				List<Long> ticketList = ticketEntityManager.findSendedNoResponseTicketByLottery(TicketSupporter.YUECAI,lottery);
				if(ticketList==null || ticketList.isEmpty())continue;
				YuecaiUtil cpUtil = null;
				if(lottery.equals(Lottery.DCZC)){
					cpUtil = new DczcYuecaiUtil();
				}else{
					logger.error("找不到彩种所对应的出票");
					continue;
				}
				List<Ticket> ticketListTemp = Lists.newArrayList();
				for (Long id:ticketList) {
					Ticket ticket = ticketEntityManager.getTicket(id);
					ticketListTemp.add(ticket);
					if(ticketListTemp.size()%TicketConstant.QUERY_MAXRESULTS==0){
						///够最大查询数
						query_method(cpUtil,ticketListTemp);
						ticketListTemp.clear();
					}
				}
				if(ticketListTemp.size()>0){
					query_method(cpUtil,ticketListTemp);
					ticketListTemp.clear();
				}
			}catch (Exception e) {
				logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝发票出错"+e.getMessage());
				continue;
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
		
	} 
	public void query_method(YuecaiUtil cpUtil,List<Ticket> ticketList){
		try{
			List<QueryPVisitor> queryPVisitorList = cpUtil.confirmTicket(ticketList);
			Ticket ticket = null;
			for (QueryPVisitor queryPVisitor : queryPVisitorList) {
				try{
					ticket = new Ticket();
					ticket.setId(Long.valueOf(queryPVisitor.getOrderId()));
					queryTicket(queryPVisitor,ticket);
				}catch (Exception e) {
					logger.error("彩票｛"+ticket.getId()+"｝查询出错"+e.getMessage());
					continue;
				}
			}
		}catch (Exception e) {
			logger.error("彩票查询出错"+e.getMessage());
		}
	}
	public void runTask() throws Exception {
		logger.info("出票交易任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.YUECAI);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				 Lottery lottery = lotterySupporter.getLotteryType();
				 List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.YUECAI,lottery);
				 if(ticketList==null || ticketList.isEmpty())continue;
				 YuecaiUtil cpUtil = null;
				 Ticket ticket;
				 if(lottery.equals(Lottery.DCZC)){
						cpUtil = new DczcYuecaiUtil();
				 }else{
						logger.error("找不到彩种所对应的出票");
						continue;
				 }
				 List<Ticket> ticketListTemp = Lists.newArrayList();
				 try{
					 for (Long id : ticketList) {
								ticket=ticketEntityManager.getTicket(id);
								ticketListTemp.add(ticket);
								if(lottery.equals(Lottery.JCZQ)){
									if(ticketListTemp.size()%1==0){
										///够最大查询数
										send_method(cpUtil,ticketListTemp);
										ticketListTemp.clear();
									}
								}else if(lottery.equals(Lottery.JCLQ)){
									if(ticketListTemp.size()%1==0){
										///够最大查询数
										send_method(cpUtil,ticketListTemp);
										ticketListTemp.clear();
									}
								}else{
									if(ticketListTemp.size()%1==0){
										///够最大查询数
										send_method(cpUtil,ticketListTemp);
										ticketListTemp.clear();
									}
								}
								
							
					 }
					 if(ticketListTemp.size()>0){
						send_method(cpUtil,ticketListTemp);
					 	ticketListTemp.clear();
					 }
				 }catch (Exception e) {
						logger.error("彩票发票出错"+e.getMessage());
						continue;
				 }
			}catch (Exception e) {
				logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝发票出错"+e.getMessage());
				continue;
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
	}
	public void send_method(YuecaiUtil cpUtil,List<Ticket> ticketList){
		try{
			List<CpResultVisitor> cpResultVisitorrList = cpUtil.sendTicket(ticketList);
			Ticket ticket = null;
			for (CpResultVisitor cpResultVisitor : cpResultVisitorrList) {
				 
				ticket = ticketEntityManager.getTicket(Long.valueOf(cpResultVisitor.getOrderId()));
				if(cpResultVisitor.getIsSuccess()){
					    logger.error(TicketSupporter.YUECAI.getSupporterName()+"{"+ticket.getId()+"}发送票成功，返回代码："+cpResultVisitor.getResult());
					    ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setTicketSupporter(TicketSupporter.YUECAI);
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(cpResultVisitor.getOrderId());
						ticketEntityManager.saveTicket(ticket);
				}else{
					
						ticket.setSendTime(new Date());
						ticketEntityManager.saveTicket(ticket);
						logger.error(TicketSupporter.YUECAI.getSupporterName()+"{"+ticket.getId()+"}发送票出错，返回代码："+cpResultVisitor.getResult());
				}
			}
		}catch (Exception e) {
			logger.error("彩票查询出错"+e.getMessage());
		}
	}
	/**
	 * 查询彩票
	 * @param
	 * @param 
	 * @return
	 * @throws IOException
	 * @throws DataException 
	 */
	private void queryTicket(QueryPVisitor queryPVisitor,Ticket ticket) throws IOException, DataException{
		if(queryPVisitor.getIsSuccess()){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.YUECAI.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.YUECAI.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("1");
			ticket.setTicketSupporter(TicketSupporter.YUECAI);
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
			ticketEntityManager.saveTicket(ticket);
			
		}else if("-1".equals(queryPVisitor.getResultId())){
			///撤单
			ticket = ticketEntityManager.getTicket(ticket.getId());
			System.out.println(TicketSupporter.YUECAI.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("2");
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
			ticket.setStateModifyTime(new Date());
			ticketEntityManager.saveTicket(ticket);
		}
	}
}
