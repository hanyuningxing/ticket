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
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.rlyg.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.rlyg.DltRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.JclqRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.JczqRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.LczcRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.PlRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.rlyg.RlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.SczcRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.SdEl11to5RlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.SevenRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.SfzcRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.SscRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.SsqRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.Welfare3dRlygUtil;
import com.cai310.lottery.ticket.protocol.rlyg.jclq.JclqSpUtil;
import com.cai310.lottery.ticket.protocol.rlyg.jczq.JczqSpUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class TicketTransactionTask_rlyg {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public static int QUERY_MAXRESULTS = 1;//查询返回的最大记录数
	public static int SEND_MAXRESULTS = 1;//出票的最大记录数
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	List<TicketQuery> ticketQuerys = new ArrayList<TicketQuery>();
	public void runTask_query() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.RLYG);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				Lottery lottery = lotterySupporter.getLotteryType();
				List<Long> ticketList = ticketEntityManager.findSendedNoResponseTicketByLottery(TicketSupporter.RLYG,lottery);
				if(ticketList==null || ticketList.isEmpty())continue;
				RlygUtil cpUtil = null;
				 if(lottery.equals(Lottery.JCZQ)){
						cpUtil = new JczqRlygUtil();
				 }else if(lottery.equals(Lottery.JCLQ)){
						cpUtil = new JclqRlygUtil();
				 }else if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqRlygUtil();
				 }else if(lottery.equals(Lottery.DLT)){
						cpUtil = new DltRlygUtil();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Welfare3dRlygUtil();
				 }else if(lottery.equals(Lottery.LCZC)){
						cpUtil = new LczcRlygUtil();
				 }else if(lottery.equals(Lottery.PL)){
						cpUtil = new PlRlygUtil();
				 }else if(lottery.equals(Lottery.SCZC)){
						cpUtil = new SczcRlygUtil();
				 }else if(lottery.equals(Lottery.SFZC)){
						cpUtil = new SfzcRlygUtil();
				 }else if(lottery.equals(Lottery.SDEL11TO5)){
						cpUtil = new SdEl11to5RlygUtil();
				 }else if(lottery.equals(Lottery.SSC)){
						cpUtil = new SscRlygUtil();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenRlygUtil();
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
	public void query_method(RlygUtil cpUtil,List<Long> ticketList){
		try{
			Ticket ticket = null;
			for (Long id : ticketList) {
				try{
					ticket = new Ticket();
					ticket.setId(id);
					ticket.setLotteryType(cpUtil.getLottery());
					QueryPVisitor queryPVisitor = cpUtil.confirmTicket(ticket);
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
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.RLYG);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				 Lottery lottery = lotterySupporter.getLotteryType();
				 List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.RLYG,lottery);
				 if(ticketList==null || ticketList.isEmpty())continue;
				 RlygUtil cpUtil = null;
				 Ticket ticket;
				 if(lottery.equals(Lottery.JCZQ)){
						cpUtil = new JczqRlygUtil();
				 }else if(lottery.equals(Lottery.JCLQ)){
						cpUtil = new JclqRlygUtil();
				 }else if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqRlygUtil();
				 }else if(lottery.equals(Lottery.DLT)){
						cpUtil = new DltRlygUtil();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Welfare3dRlygUtil();
				 }else if(lottery.equals(Lottery.LCZC)){
						cpUtil = new LczcRlygUtil();
				 }else if(lottery.equals(Lottery.PL)){
						cpUtil = new PlRlygUtil();
				 }else if(lottery.equals(Lottery.SCZC)){
						cpUtil = new SczcRlygUtil();
				 }else if(lottery.equals(Lottery.SFZC)){
						cpUtil = new SfzcRlygUtil();
				 }else if(lottery.equals(Lottery.SDEL11TO5)){
						cpUtil = new SdEl11to5RlygUtil();
				 }else if(lottery.equals(Lottery.SSC)){
						cpUtil = new SscRlygUtil();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenRlygUtil();
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
	public void send_method(RlygUtil cpUtil,List<Ticket> ticketList){
		try{
			CpResultVisitor cpResultVisitor = cpUtil.sendTicket(ticketList);
			logger.error(TicketSupporter.RLYG.getSupporterName()+"{"+ticketList.toString()+"}发送票成功，返回代码："+cpResultVisitor.getResult());
			if(cpResultVisitor.getIsSuccess()){
			    for (Ticket ticket : ticketList) {
				    ticket.setSended(Boolean.TRUE);
					ticket.setSendTime(new Date());
					ticket.setTicketSupporter(TicketSupporter.RLYG);
					ticket.setStateCode("0");
					ticket.setStateModifyTime(new Date());
					ticket.setOrderNo(null);
					ticketEntityManager.saveTicket(ticket);
			    }
			}else{
				if("305".equals(cpResultVisitor.getResult())){
					///如果返回已有此订单
					 for (Ticket ticket : ticketList) {
						ticket = ticketEntityManager.getTicket(ticket.getId());
						ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(null);
						ticket.setTicketSupporter(TicketSupporter.RLYG);
						ticketEntityManager.saveTicket(ticket);
					 }
				}else{
					 for (Ticket ticket : ticketList) {
						ticket.setSendTime(new Date());
						ticket.setStateCodeMessage(cpResultVisitor.getResult());
						ticketEntityManager.saveTicket(ticket);
					 }
					
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
		if(queryPVisitor.getIsSuccess()){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("1");
			ticket.setTicketSupporter(TicketSupporter.RLYG);
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
		    if(ticket.getLotteryType().equals(Lottery.JCZQ)){
		    	@SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JczqMatchItem.class);
				JczqPrintItemObj jczqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JczqPrintItemObj.class, classMap);
				if(null!=jczqPrintItemObj){
					if(!com.cai310.lottery.support.jczq.PassType.P1.equals(
							 com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(""+jczqPrintItemObj.getPassType())])){
						JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jczqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						//单关
						try{
							if(com.cai310.lottery.support.jczq.PlayType.BF.equals(
									com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])){
									JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jczqPrintItemObj,ticket);
							    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
										jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
										jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
										String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
										if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
									}
							}else{
								JcMatchOddsList jcMatchOddsList =new JcMatchOddsList();
								jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
						}catch(Exception e){
							//单关可能会获取失败
							logger.error(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}

		   }else if(ticket.getLotteryType().equals(Lottery.JCLQ)){
			    @SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JclqMatchItem.class);
				JclqPrintItemObj jclqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JclqPrintItemObj.class, classMap);
				if(null!=jclqPrintItemObj){
					if(!com.cai310.lottery.support.jclq.PassType.P1.equals(
							 com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf(""+jclqPrintItemObj.getPassType())])){
				    	JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jclqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						try{
							if(com.cai310.lottery.support.jclq.PlayType.SFC.equals(
									com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf(""+jclqPrintItemObj.getPlayTypeOrdinal())])){
								JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jclqPrintItemObj,ticket);
						    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
									jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
									jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
									String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
									if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
								}
							}else{
								JcMatchOddsList jcMatchOddsList =new JcMatchOddsList();
								jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
						}catch(Exception e){
							//单关可能会获取失败	
							logger.error(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}
						
		   }
		   ticketEntityManager.saveTicket(ticket);
		}else{
			if("N".equals(queryPVisitor.getResultId())){
				///撤单
				ticket = ticketEntityManager.getTicket(ticket.getId());
				System.out.println(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
				ticket.setStateCode("2");
				ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
				ticket.setStateModifyTime(new Date());
				ticketEntityManager.saveTicket(ticket);
			}
		}
	}
	
	public void runTask_query_remoteTicketId() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.RLYG);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				Lottery lottery = lotterySupporter.getLotteryType();
				List<Long> ticketList = ticketEntityManager.findNoTicketCodeTicketByLottery(TicketSupporter.RLYG,lottery);
				if(ticketList==null || ticketList.isEmpty())continue;
				RlygUtil cpUtil = null;
					if(lottery.equals(Lottery.JCZQ)){
						cpUtil = new JczqRlygUtil();
				 }else if(lottery.equals(Lottery.JCLQ)){
						cpUtil = new JclqRlygUtil();
				 }else if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqRlygUtil();
				 }else if(lottery.equals(Lottery.DLT)){
						cpUtil = new DltRlygUtil();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Welfare3dRlygUtil();
				 }else if(lottery.equals(Lottery.LCZC)){
						cpUtil = new LczcRlygUtil();
				 }else if(lottery.equals(Lottery.PL)){
						cpUtil = new PlRlygUtil();
				 }else if(lottery.equals(Lottery.SCZC)){
						cpUtil = new SczcRlygUtil();
				 }else if(lottery.equals(Lottery.SFZC)){
						cpUtil = new SfzcRlygUtil();
				 }else if(lottery.equals(Lottery.SDEL11TO5)){
						cpUtil = new SdEl11to5RlygUtil();
				 }else if(lottery.equals(Lottery.SSC)){
						cpUtil = new SscRlygUtil();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenRlygUtil();
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
	public void query_remoteTicket_method(RlygUtil cpUtil,List<Long> ticketList){
		try{
			Ticket ticket = null;
			for (Long id : ticketList) {
				try{
					ticket = new Ticket();
					ticket.setId(id);
					ticket.setLotteryType(cpUtil.getLottery());
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
		if(queryPVisitor.getIsSuccess()){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticketEntityManager.saveTicket(ticket);
		}
	}
}
