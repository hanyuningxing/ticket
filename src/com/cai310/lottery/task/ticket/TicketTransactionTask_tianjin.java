package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.protocol.tianjin.util.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.tianjin.util.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.tianjin.util.SevenTianjinUtil;
import com.cai310.lottery.ticket.protocol.tianjin.util.SsqTianjinUtil;
import com.cai310.lottery.ticket.protocol.tianjin.util.TianjinUtil;
import com.cai310.lottery.ticket.protocol.tianjin.util.Weflare3dTianjinUtil;

import com.google.common.collect.Lists;

public class TicketTransactionTask_tianjin {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public static int QUERY_MAXRESULTS = 1;//查询返回的最大记录数
	public static int SEND_MAXRESULTS = 1;//出票的最大记录数
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	List<TicketQuery> ticketQuerys = new ArrayList<TicketQuery>();
	
	public void runTask() throws Exception {
		logger.info("出票交易任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.TIANJ);
		
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				 Lottery lottery = lotterySupporter.getLotteryType();
				 List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.TIANJ,lottery);
				 if(ticketList==null || ticketList.isEmpty())continue;
				 TianjinUtil cpUtil = null;
				 Ticket ticket;
				 if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqTianjinUtil();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Weflare3dTianjinUtil();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenTianjinUtil();
				 }else{
						logger.error("找不到彩种所对应的出票");
						continue;
				 }
				 List<Ticket> ticketListTemp = Lists.newArrayList();
				 try{
					 for (Long id : ticketList) {
								ticket=ticketEntityManager.getTicket(id);
								ticketListTemp.add(ticket);
								if(ticketListTemp.size()%SEND_MAXRESULTS==0){
									///够最大查询数
									send_method(cpUtil,ticketListTemp);
									ticketListTemp.clear();
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
	public void send_method(TianjinUtil cpUtil,List<Ticket> ticketList){
		try{
			for (Ticket ticket : ticketList){
				CpResultVisitor cpResultVisitor = cpUtil.sendTicket(ticket);
				if(cpResultVisitor.getIsSuccess()&&ticket.getSchemeCost().equals(Integer.parseInt(cpResultVisitor.getSchemeCost()))){
					logger.error(TicketSupporter.TIANJ.getSupporterName()+"｛"+ticket.getId()+"｝发送票成功，返回代码："+cpResultVisitor.getResult());
					    ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(cpResultVisitor.getOrderId());
						ticket.setTicketSupporter(TicketSupporter.TIANJ);
						ticketEntityManager.saveTicket(ticket);
				}else{
					ticket.setSendTime(new Date());
					ticketEntityManager.saveTicket(ticket);
					logger.error(TicketSupporter.TIANJ.getSupporterName()+"｛"+ticket.getId()+"｝发送票出错，返回代码："+cpResultVisitor.getResult());
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("彩票查询出错"+e.getMessage());
		}
	}
	
	public void runTask_query() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.TIANJ);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				Lottery lottery = lotterySupporter.getLotteryType();
				List<Long> ticketList = ticketEntityManager.findSendedNoResponseTicketByLottery(TicketSupporter.TIANJ,lottery);
				if(ticketList==null || ticketList.isEmpty())continue;
				TianjinUtil cpUtil = null;
				 if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqTianjinUtil();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Weflare3dTianjinUtil();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenTianjinUtil();
				 }else{
						logger.error("找不到彩种所对应的出票");
						continue;
				 }
				List<Long> ticketListTemp = Lists.newArrayList();
				for (Long id:ticketList) {
					ticketListTemp.add(id);
					if(ticketListTemp.size()%QUERY_MAXRESULTS==0){
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
	public void query_method(TianjinUtil tUtil,List<Long> ticketList){
		try{
			Ticket ticket = null;
			for (Long id : ticketList) {
				try{
					ticket = ticketEntityManager.getTicket(id);
					
				//	ticket.setLotteryType(tUtil.getLottery());
					QueryPVisitor queryPVisitor = tUtil.confirmTicket(ticket);
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
	
	/**
	 * 查询彩票
	 * @param
	 * @param 
	 * @return
	 * @throws IOException
	 */
	private void queryTicket(QueryPVisitor queryPVisitor,Ticket ticket) throws IOException{
		if(queryPVisitor.getIsSuccess()&&queryPVisitor.getOrderId()!=null&&!"".equals(queryPVisitor.getOrderId())){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.TIANJ.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor);
			System.out.println(TicketSupporter.TIANJ.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor);
			ticket.setStateCode("1");
			ticket.setTicketSupporter(TicketSupporter.TIANJ);
			ticket.setStateModifyTime(new Date());
			//ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticketEntityManager.saveTicket(ticket);
		}
	}
	
	public void runTask_query_remoteTicketId() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.TIANJ);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				Lottery lottery = lotterySupporter.getLotteryType();
				List<Long> ticketList = ticketEntityManager.findNoTicketCodeTicketByLottery(TicketSupporter.TIANJ,lottery);
				if(ticketList==null || ticketList.isEmpty())continue;
				TianjinUtil cpUtil = null;
				if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqTianjinUtil();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Weflare3dTianjinUtil();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenTianjinUtil();
				 }else{
						logger.error("找不到彩种所对应的出票");
						continue;
				 }
				List<Long> ticketListTemp = Lists.newArrayList();
				for (Long id:ticketList) {
					ticketListTemp.add(id);
					if(ticketListTemp.size()%QUERY_MAXRESULTS==0){
						///够最大查询数
						query_remoteTicket_method(cpUtil,ticketListTemp);
						ticketListTemp.clear();
					}
				}
				if(ticketListTemp.size()>0){
					query_remoteTicket_method(cpUtil,ticketListTemp);
					ticketListTemp.clear();
				}
			}catch (Exception e) {
				logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝查票出错"+e.getMessage());
				continue;
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
		
	} 
	public void query_remoteTicket_method(TianjinUtil cpUtil,List<Long> ticketList){
		try{
			Ticket ticket = null;
			for (Long id : ticketList) {
				try{
					ticket = ticketEntityManager.getTicket(id);
					//ticket.setLotteryType(cpUtil.getLottery());
					QueryPVisitor queryPVisitor = cpUtil.confirmTicket(ticket);
					queryTicketRemoteTicketId(queryPVisitor,ticket);
				}catch (Exception e) {
					logger.error("彩票｛"+ticket.getId()+"｝查询出错"+e.getMessage());
					continue;
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
	 */
	private void queryTicketRemoteTicketId(QueryPVisitor queryPVisitor,Ticket ticket) throws IOException{
		if(queryPVisitor.getIsSuccess()&&!"".equals(queryPVisitor.getRemoteCode())){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.TIANJ.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor);
			System.out.println(TicketSupporter.TIANJ.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor);
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getRemoteCode());
			ticketEntityManager.saveTicket(ticket);
		}
	}
}
