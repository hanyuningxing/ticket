package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
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
import com.cai310.lottery.ticket.protocol.win310.utils.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.win310.utils.DczcWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.DltWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.JclqWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.JczqWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.LczcWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.PlWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.win310.utils.SczcWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.SdEl11to5Win310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.SevenWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.SevenstarWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.SfzcWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.SscWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.SsqWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.Tc22to5Win310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.Weflare3dWin310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.Win310Util;
import com.cai310.lottery.ticket.protocol.win310.utils.jclq.JclqSpUtil;
import com.cai310.lottery.ticket.protocol.win310.utils.jczq.JczqSpUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class TicketTransactionTask_win310 {
protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	List<TicketQuery> ticketQuerys = new ArrayList<TicketQuery>();
	public void runTask_query() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.WIN310);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				Lottery lottery = lotterySupporter.getLotteryType();
				List<Ticket> ticketList=ticketEntityManager.findResponseTicketByLottery(TicketSupporter.WIN310, lottery);
				if(ticketList==null || ticketList.isEmpty())continue;
				if(lottery.getCategory().equals(LotteryCategory.JC)){
					Calendar c = Calendar.getInstance();
					Date now = new Date();
					now = DateUtils.addMinutes(now, 1);///把截至时间往后1分钟
					c.setTime(now);
					int week = c.get(Calendar.DAY_OF_WEEK);
					if (week == Calendar.SUNDAY||week == Calendar.MONDAY) {
						int hour = c.get(Calendar.HOUR_OF_DAY);
						if(hour<9&&hour>=1){
							continue;
						}
					}else if (lottery.equals(Lottery.JCLQ)&&(week == Calendar.WEDNESDAY||week == Calendar.THURSDAY)) {
						int hour = c.get(Calendar.HOUR_OF_DAY);
						if(hour<7){
							continue;
						}
						
					}else{
						int hour = c.get(Calendar.HOUR_OF_DAY);
						if(hour<9){
							continue;
						}
					}
				}
				Win310Util cpUtil = null;
				if(lottery.equals(Lottery.SSQ)){
					cpUtil = new SsqWin310Util();
				}else if(lottery.equals(Lottery.DLT)){
					cpUtil = new DltWin310Util();
				}else if(lottery.equals(Lottery.WELFARE3D)){
					cpUtil = new Weflare3dWin310Util();
				}else if(lottery.equals(Lottery.SFZC)){
					cpUtil = new SfzcWin310Util();
				}else if(lottery.equals(Lottery.SCZC)){
					cpUtil = new SczcWin310Util();
				}else if(lottery.equals(Lottery.LCZC)){
					cpUtil = new LczcWin310Util();
				}else if(lottery.equals(Lottery.PL)){
					cpUtil = new PlWin310Util();
				}else if(lottery.equals(Lottery.SDEL11TO5)){
					cpUtil = new SdEl11to5Win310Util();
				}else if(lottery.equals(Lottery.DCZC)){
					cpUtil = new DczcWin310Util();
				}else if(lottery.equals(Lottery.SSC)){
					cpUtil = new SscWin310Util();
				}else if(lottery.equals(Lottery.SEVEN)){
					cpUtil = new SevenWin310Util();
				}else if(lottery.equals(Lottery.SEVENSTAR)){
					cpUtil = new SevenstarWin310Util();
				}else if(lottery.equals(Lottery.TC22TO5)){
					cpUtil = new Tc22to5Win310Util();
				}else if(lottery.equals(Lottery.JCZQ)){
					cpUtil = new JczqWin310Util();
				}else if(lottery.equals(Lottery.JCLQ)){
					cpUtil = new JclqWin310Util();
				}else{
					logger.error("找不到彩种所对应的出票");
					continue;
				}
				List<Ticket> ticketListTemp = Lists.newArrayList();
				for (Ticket ticket:ticketList) {
					ticketListTemp.add(ticket);
					if(ticketListTemp.size()%TicketConstant.QUERY_MAXRESULTS==0){
						///够最大查询数
						query_method(cpUtil,ticketListTemp,lottery);
						ticketListTemp.clear();
					}
				}
				if(ticketListTemp.size()>0){
					query_method(cpUtil,ticketListTemp,lottery);
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
	public void query_method(Win310Util cpUtil,List<Ticket> ticketList,Lottery lottery){
		try{
			List<QueryPVisitor> queryPVisitorList = cpUtil.confirmTicket(ticketList,lottery);
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
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.WIN310);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
				 Lottery lottery = lotterySupporter.getLotteryType();
				 List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.WIN310,lottery);
				 if(ticketList==null || ticketList.isEmpty())continue;
				 Win310Util cpUtil = null;
				 Ticket ticket;
				 if(lottery.equals(Lottery.SSQ)){
						cpUtil = new SsqWin310Util();
				 }else if(lottery.equals(Lottery.DLT)){
						cpUtil = new DltWin310Util();
				 }else if(lottery.equals(Lottery.WELFARE3D)){
						cpUtil = new Weflare3dWin310Util();
				 }else if(lottery.equals(Lottery.SFZC)){
						cpUtil = new SfzcWin310Util();
				 }else if(lottery.equals(Lottery.SCZC)){
						cpUtil = new SczcWin310Util();
				 }else if(lottery.equals(Lottery.LCZC)){
						cpUtil = new LczcWin310Util();
				 }else if(lottery.equals(Lottery.PL)){
						cpUtil = new PlWin310Util();
				 }else if(lottery.equals(Lottery.SDEL11TO5)){
						cpUtil = new SdEl11to5Win310Util();
				 }else if(lottery.equals(Lottery.DCZC)){
						cpUtil = new DczcWin310Util();
				 }else if(lottery.equals(Lottery.SSC)){
						cpUtil = new SscWin310Util();
				 }else if(lottery.equals(Lottery.SEVEN)){
						cpUtil = new SevenWin310Util();
				 }else if(lottery.equals(Lottery.SEVENSTAR)){
						cpUtil = new SevenstarWin310Util();
				 }else if(lottery.equals(Lottery.TC22TO5)){
						cpUtil = new Tc22to5Win310Util();
				 }else if(lottery.equals(Lottery.JCZQ)){
						cpUtil = new JczqWin310Util();
				 }else if(lottery.equals(Lottery.JCLQ)){
						cpUtil = new JclqWin310Util();
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
									if(ticketListTemp.size()%TicketConstant.SEND_MAXRESULTS==0){
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
	public void send_method(Win310Util cpUtil,List<Ticket> ticketList){
		try{
			List<CpResultVisitor> cpResultVisitorrList = cpUtil.sendTicket(ticketList);
			Ticket ticket = null;
			for (CpResultVisitor cpResultVisitor : cpResultVisitorrList) {
				 
				ticket = ticketEntityManager.getTicket(Long.valueOf(cpResultVisitor.getOrderId()));
				if(cpResultVisitor.getIsSuccess()){
					    logger.error(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}发送票成功，返回代码："+cpResultVisitor.getResult());
					    ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setTicketSupporter(TicketSupporter.WIN310);
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(cpResultVisitor.getOrderId());
						ticketEntityManager.saveTicket(ticket);
				}else{
					if("9009".equals(cpResultVisitor.getResult())){
						///如果返回已有此订单
						ticket = ticketEntityManager.getTicket(ticket.getId());
						ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(cpResultVisitor.getOrderId());
						ticket.setTicketSupporter(TicketSupporter.WIN310);
						ticketEntityManager.saveTicket(ticket);
					}else{
						ticket.setSendTime(new Date());
						ticketEntityManager.saveTicket(ticket);
						logger.error(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}发送票出错，返回代码："+cpResultVisitor.getResult());
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
	 * @throws DataException 
	 */
	private void queryTicket(QueryPVisitor queryPVisitor,Ticket ticket) throws IOException, DataException{
		if(queryPVisitor.getIsSuccess()){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("1");
			ticket.setTicketSupporter(TicketSupporter.WIN310);
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
						JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
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
									JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
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
							logger.error(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
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
				    	JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
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
								JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
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
							logger.error(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}
						
		   }
		   ticketEntityManager.saveTicket(ticket);
		}else{
			if("000".equals(queryPVisitor.getResultId())||"004".equals(queryPVisitor.getResultId())){
				///撤单
				ticket = ticketEntityManager.getTicket(ticket.getId());
				System.out.println(TicketSupporter.WIN310.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
				ticket.setStateCode("2");
				ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
				ticket.setStateModifyTime(new Date());
				ticketEntityManager.saveTicket(ticket);
			}
		}
	}
}
