package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.protocol.bd.utils.BeiDanUtil;
import com.cai310.lottery.ticket.protocol.bd.utils.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.bd.utils.DczcBeiDanUtil;
import com.cai310.lottery.ticket.protocol.bd.utils.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.localnew.utils.CPUtil;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TicketTransactionTask_bd {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	List<TicketQuery> ticketQuerys = new ArrayList<TicketQuery>();
	
	public void runTask_query() throws Exception {
		logger.error("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.BEIDAN);
		List<Ticket> ticketListTemp = Lists.newArrayList();
		if (lotterySupporters != null && !lotterySupporters.isEmpty()) {
			for (LotterySupporter lotterySupporter : lotterySupporters) {
				try{
					Lottery lottery = lotterySupporter.getLotteryType();
					List<Long> ticketList = ticketEntityManager.findSendedNoResponseTicketByLottery(TicketSupporter.BEIDAN,lottery);
					if(ticketList==null || ticketList.isEmpty())continue;
					BeiDanUtil cpUtil = null;
					if(lottery.equals(Lottery.DCZC)){
						cpUtil = new DczcBeiDanUtil();
					}else{
						logger.error("找不到彩种所对应的出票");
						continue;
					}
					for (Long id:ticketList) {
						Ticket ticket = ticketEntityManager.getTicket(id);
						ticketListTemp.add(ticket);
//						if(ticketListTemp.size()%TicketConstant.QUERY_MAXRESULTS==0){
							///够最大查询数
							query_method(cpUtil,ticketListTemp);
							ticketListTemp.clear();
//						}
							break;
					}
//					if(ticketListTemp.size()>0){
//						query_method(cpUtil,ticketListTemp);
//						ticketListTemp.clear();
//					}
				}catch (Exception e) {
					logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝发票出错"+e.getMessage());
					continue;
				}
			}
		}
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
		
	} 
	public void query_method(BeiDanUtil cpUtil,List<Ticket> ticketList){
		try{
			for (Ticket tk : ticketList) {
				QueryPVisitor queryPVisitor = cpUtil.confirmTicket(tk);
				Ticket ticket = null;
				try{
					ticket = new Ticket();
					ticket.setId(tk.getId());
					queryTicket(queryPVisitor,ticket);
				}catch (Exception e) {
					logger.error("彩票｛"+ticket.getId()+"｝查询出错"+e.getMessage());
					continue;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("彩票查询出错"+e.getMessage());
		}
	}
	public void runTask() throws Exception {
		logger.error("出票交易任务执行...");
		long time = System.currentTimeMillis();
		List<Ticket> ticketListTemp = Lists.newArrayList();
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.BEIDAN);
		if(lotterySupporters!=null && !lotterySupporters.isEmpty()){
		
			for (LotterySupporter lotterySupporter : lotterySupporters) {
				try{
					 Lottery lottery = lotterySupporter.getLotteryType();
					 List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.BEIDAN,lottery);
					 logger.error("待出票"+ticketList.size()+"个-查询耗时"+(System.currentTimeMillis()-time)/1000+"秒");
					 if(ticketList==null || ticketList.isEmpty())continue;
					 BeiDanUtil cpUtil = null;
					 Ticket ticket;
					 if(lottery.equals(Lottery.DCZC)){
							cpUtil = new DczcBeiDanUtil();
					 }else{
							logger.error("找不到彩种所对应的出票");
							continue;
					 }
					 try{
						 for (Long id : ticketList) {
									ticket=ticketEntityManager.getTicket(id);
									ticketListTemp.add(ticket);
									if(ticketListTemp.size()%1==0){
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
		}
		logger.error("出票交易任务结束，耗时"+(System.currentTimeMillis()-time)/1000+"秒");
		// 人工切换出票 没有建立索引查询耗时
		// 1查人工切换的票 2按玩法分组 3按玩法发送
		/*List<Ticket> ticketsOfReset = ticketEntityManager.findTicketsOfReset(TicketSupporter.BEIDAN);
		if (ticketsOfReset == null || ticketsOfReset.isEmpty()){
			logger.error("人工切换出票，耗时"+(System.currentTimeMillis()-time)/1000+"秒");
			return;
		}
		Map<Lottery, List<Ticket>> ticketsOfLottery = Maps.newHashMap();
		Lottery lotteryType = null;
		for (Ticket ticket : ticketsOfReset) {
			lotteryType = ticket.getLotteryType();
			List<Ticket> tickets = ticketsOfLottery.get(lotteryType);
			if (tickets == null) {
				tickets = Lists.newArrayList();
				tickets.add(ticket);
				ticketsOfLottery.put(lotteryType, tickets);
			} else {
				tickets.add(ticket);
			}
		}
		for (Map.Entry<Lottery, List<Ticket>> entry : ticketsOfLottery.entrySet()) {
			Lottery lottery = entry.getKey();
			try {
				BeiDanUtil cpUtil = null;
				if(lottery.equals(Lottery.DCZC)){
					cpUtil = new DczcBeiDanUtil();
				}else{
					logger.error("找不到彩种所对应的出票");
					continue;
				}
				List<Ticket> ticketsOfReset_lottery = entry.getValue();
				try {
					for (Ticket ticket : ticketsOfReset_lottery) {
						// 重新查询票以防被临时重置其他出票方
						ticket = ticketEntityManager.getTicket(ticket.getId());
						if (!TicketSupporter.BEIDAN.equals(ticket.getTicketSupporter())) {
							continue;
						}
						ticketListTemp.add(ticket);
						if (ticketListTemp.size() % TicketConstant.SEND_MAXRESULTS == 0) {
							// /够最大送票数
							send_method(cpUtil, ticketListTemp);
							ticketListTemp.clear();
						}
					}
					if (ticketListTemp.size() > 0) {
						send_method(cpUtil, ticketListTemp);
						ticketListTemp.clear();
					}
				} catch (Exception e) {
					logger.error(TicketSupporter.BEIDAN.getSupporterName() + "彩票发票出错" + e.getMessage());
					continue;
				}
			} catch (Exception e) {
				logger.error(TicketSupporter.BEIDAN.getSupporterName() + "玩法｛" + lottery.getLotteryName() + "｝发票出错" + e.getMessage());
				continue;
			}
		}
		logger.error("出票交易任务结束，耗时"+(System.currentTimeMillis()-time)/1000+"秒");*/
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
	}
	
	public void runQueryAwardTask() throws Exception {
		logger.error("返奖查询任务执行-----------------------------------------------...");
		try{
			 List<Long> ticketList = ticketEntityManager.findUnreturnAwardTicket(TicketSupporter.BEIDAN,Lottery.DCZC);
			 if(ticketList==null || ticketList.isEmpty()){
				 logger.error("返奖查询数据为空");
				 return; 
			 }
			 logger.error("返奖查询个数："+ticketList.size());
			 BeiDanUtil cpUtil = new DczcBeiDanUtil();
			 Ticket ticket;
			 List<Ticket> ticketListTemp = Lists.newArrayList();
			 try{
				 for (Long id : ticketList) {
							ticket=ticketEntityManager.getTicket(id);
							ticketListTemp.add(ticket);
							if(ticketListTemp.size()%1==0){
								///够最大查询数
								update_method(cpUtil,ticketListTemp);
								ticketListTemp.clear();
							}
							
						
				 }
				 if(ticketListTemp.size()>0){
					 update_method(cpUtil,ticketListTemp);
				 	ticketListTemp.clear();
				 }
			 }catch (Exception e) {
					logger.error("彩票返奖查询出错"+e.getMessage());
			 }
		}catch (Exception e) {
			logger.error("玩法｛"+Lottery.DCZC.getLotteryName()+"｝返奖查询出错"+e.getMessage());
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
		logger.error("返奖查询任务结束----------------------------------------------。。。");
	}
	
	public void send_method(BeiDanUtil cpUtil,List<Ticket> ticketList){
		try{
			for (Ticket tk : ticketList) {
				CpResultVisitor cpResultVisitor = cpUtil.sendTicket(tk);
				Ticket ticket = null;
				ticket = ticketEntityManager.getTicket(Long.valueOf(cpResultVisitor.getOrderId()));
				if(cpResultVisitor.getIsSuccess()){
					    logger.error(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}发送票成功，返回代码："+cpResultVisitor.getResult());
					    ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setTicketSupporter(TicketSupporter.BEIDAN);
						//北单出票成功后有可能先通知到了已出票statcode变成1，然后线程在执行保存 
						if(StringUtil.isEmpty(ticket.getStateCode())){
							ticket.setStateCode("0");
						}
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(cpResultVisitor.getOrderId());
						ticket.setIssueId(cpResultVisitor.getIssueId());
						ticketEntityManager.saveTicket(ticket);
				}else{
					String result = "";
					if(cpResultVisitor.getResult().indexOf(",")>-1){
						String[] cpr = cpResultVisitor.getResult().split(",");
						if(cpr.length>0)
						result = cpResultVisitor.getResult().split(",")[0];
					}
					if(result.equals("1063")){
						///如果返回已有此订单
						ticket = ticketEntityManager.getTicket(ticket.getId());
						ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						if(StringUtil.isEmpty(ticket.getStateCode())){
							ticket.setStateCode("0");
						}
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(cpResultVisitor.getOrderId());
						ticket.setTicketSupporter(TicketSupporter.BEIDAN);
						ticketEntityManager.saveTicket(ticket);
//					}else if(result.equals("1016")){
//						///截止
//						ticket = ticketEntityManager.getTicket(ticket.getId());
//						ticket.setStateCode("2");
//						ticket.setStateModifyTime(new Date());
//						ticket.setTicketSupporter(TicketSupporter.BEIDAN);
//						ticketEntityManager.saveTicket(ticket);
					}else{
						ticket.setSendTime(new Date());
						ticketEntityManager.saveTicket(ticket);
						logger.error(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}发送票出错，返回代码："+cpResultVisitor.getResult());
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("彩票查询出错"+e.getMessage());
		}
	}
	public void update_method(BeiDanUtil cpUtil,List<Ticket> ticketList){
		try{
			for (Ticket tk : ticketList) {
				CpResultVisitor cpResultVisitor = cpUtil.updateTicket(tk);
				Ticket ticket = null;
				if(cpResultVisitor.getIsSuccess()){
					ticket = ticketEntityManager.getTicket(Long.valueOf(cpResultVisitor.getOrderId()));
					if(!StringUtil.isEmpty(cpResultVisitor.getDetail())){
						if(!StringUtil.isEmpty(cpResultVisitor.getDetail())){
							logger.error(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}返奖更新成功");
							ticket.setReturnAward(cpResultVisitor.isReturnAward());
							ticket.setReturnAwardTime(new Date());
							ticket.setReturnMoney(cpResultVisitor.getReturnMoney());
							ticket.setPrize(cpResultVisitor.getPrize());
							ticket.setTax(cpResultVisitor.getTax());
							ticket.setTotalPrize(cpResultVisitor.getPrize());
							ticket.setTotalPrizeAfterTax(cpResultVisitor.getPrize()-cpResultVisitor.getTax());
							ticket.setDetail(cpResultVisitor.getDetail());
							ticket.setAward_synchroned(true);
							if(StringUtil.isEmpty(ticket.getIssueId())&&!StringUtil.isEmpty(tk.getIssueId())){
								ticket.setIssueId(tk.getIssueId());
							}
							ticketEntityManager.saveTicket(ticket);
						}
					}else{
						ticket.setAward_synchroned(true);
						ticketEntityManager.saveTicket(ticket);
					}
					
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("返奖更新出错"+e.getMessage());
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
			logger.debug(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("1");
			ticket.setTicketSupporter(TicketSupporter.BEIDAN);
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy/MM/dd hh:mm:ss"));
			ticketEntityManager.saveTicket(ticket);
			logger.error(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"} 出票成功");
		}else if(!"1002".equals(queryPVisitor.getResultId())&&!"1003".equals(queryPVisitor.getResultId())&&!"1005".equals(queryPVisitor.getResultId())&&!"1012".equals(queryPVisitor.getResultId())){
			///撤单
			ticket = ticketEntityManager.getTicket(ticket.getId());
			System.out.println(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId()+"--"+queryPVisitor.getStatus());
			ticket.setStateCode("2");
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy/MM/dd hh:mm:ss"));
			ticket.setStateModifyTime(new Date());
			ticketEntityManager.saveTicket(ticket);
		}else{
			System.out.println(TicketSupporter.BEIDAN.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId()+"--"+queryPVisitor.getStatus());
		}
	}
}
