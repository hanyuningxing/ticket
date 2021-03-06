package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.localnew.utils.CPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.CpResult;
import com.cai310.lottery.ticket.protocol.localnew.utils.CpTicket;
import com.cai310.lottery.ticket.protocol.localnew.utils.DLTCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.EL11TO5CPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.JCLQCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.JCZQCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.KLPKCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.LCZCCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.PLCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.localnew.utils.QueryTicket;
import com.cai310.lottery.ticket.protocol.localnew.utils.SCZCCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.SFZCCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.SdEl11to5CPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.SevenStarCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.SsqCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.Welfare3dCPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.XjEl11to5CPUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.jclq.JclqSpUtil;
import com.cai310.lottery.ticket.protocol.localnew.utils.jczq.JczqSpUtil;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TicketTransactionTask_cai310 {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;

	/**
	 * 根据彩种取对应的出票格式解析器
	 * 
	 * @param lottery
	 * @return
	 */
	private CPUtil findCpUtilForLottery(Lottery lottery){
		CPUtil cpUtil = null;
		if(lottery.equals(Lottery.JCZQ)){
			cpUtil = new JCZQCPUtil();
		}else if(lottery.equals(Lottery.JCLQ)){
			cpUtil = new JCLQCPUtil();
		}else if(lottery.equals(Lottery.SFZC)){
			cpUtil = new SFZCCPUtil();
		}else if(lottery.equals(Lottery.SCZC)){
			cpUtil = new SCZCCPUtil();
		}else if(lottery.equals(Lottery.LCZC)){
			cpUtil = new LCZCCPUtil();
		}else if(lottery.equals(Lottery.EL11TO5)){
			cpUtil = new EL11TO5CPUtil();
		}else if(lottery.equals(Lottery.DLT)){
			cpUtil=new DLTCPUtil();
		}else if(lottery.equals(Lottery.PL)){
			cpUtil=new PLCPUtil();
		}else if(lottery.equals(Lottery.SEVENSTAR)){
			cpUtil=new SevenStarCPUtil();
		}else if(lottery.equals(Lottery.SSQ)){
			cpUtil=new SsqCPUtil();
		}else if(lottery.equals(Lottery.WELFARE3D)){
			cpUtil=new Welfare3dCPUtil();
		}else if (lottery.equals(Lottery.KLPK)) {
			cpUtil = new KLPKCPUtil();
		}else if(lottery.equals(Lottery.SDEL11TO5)){
			cpUtil=new SdEl11to5CPUtil();
		}else if(lottery.equals(Lottery.XJEL11TO5)){
			cpUtil=new XjEl11to5CPUtil();
		}else{
			logger.error(TicketSupporter.CAI310.getSupporterName()+":没有对应的<"+lottery.getLotteryName()+">彩种出票格式解析器！");
		}
		return cpUtil;
	}

	/****** 华丽丽分割线 ********************************************************************************************************************/

	public void runTask() throws Exception {
		logger.error(TicketSupporter.CAI310.getSupporterName() + "出票交易任务执行...");

		List<Ticket> ticketListTemp = Lists.newArrayList();

		// 系统默认出票
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.CAI310);
		if (lotterySupporters != null && !lotterySupporters.isEmpty()) {
			for (LotterySupporter lotterySupporter : lotterySupporters) {
				try {
					Lottery lottery = lotterySupporter.getLotteryType();
					List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.CAI310, lottery);
					logger.error(TicketSupporter.CAI310.getSupporterName() + "出票交易任务查询...");
					if (ticketList == null || ticketList.isEmpty())
						continue;
					logger.error("-------共有待出票数量---------"+ticketList.size());
					//BD
//					if(lottery==Lottery.DCZC){
//						System.out.println("-----------------Lottery.DCZC send-----------------------");
//						String current = DateUtil.dateToStrHHmmss(new Date());
//						if("010000".compareTo(current)<=0&&"090000".compareTo(current)>=0){
//							continue;
//						}
//						
//						for (Long id : ticketList) {
//							Calendar calendar = Calendar.getInstance();
//							calendar.setTime(new Date());
//							calendar.add(Calendar.SECOND, 5);
//							
//							Ticket ticket = this.ticketEntityManager.getTicket(id);
//							ticket.setSended(Boolean.TRUE);
//							ticket.setSendTime(new Date());
//							ticket.setTicketSupporter(TicketSupporter.CAI310);
//							ticket.setStateCode("0");
//							ticket.setStateModifyTime(new Date());
//							ticket.setOrderNo(UUID.randomUUID().toString());
//							
//							ticket.setStateCode("1");
//							ticket.setStateModifyTime(new Date());
//							ticket.setRemoteTicketId(UUID.randomUUID().toString());
//							ticket.setTicketTime(calendar.getTime());
//							
//							ticketEntityManager.saveTicket(ticket);
//						}
//						continue;
//					}
					CPUtil cpUtil = findCpUtilForLottery(lottery);
					if (cpUtil == null)
						continue;
					Ticket ticket;
					try {
						for (Long id : ticketList) {
							ticket = ticketEntityManager.getTicket(id);
							if (ticket.getTicketSupporter() != null)
								continue;
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
						logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票发票出错" + e.getMessage());
						continue;
					}
				} catch (Exception e) {
					logger.error(TicketSupporter.CAI310.getSupporterName() + "玩法｛" + lotterySupporter.getLotteryType().getLotteryName() + "｝发票出错" + e.getMessage());
					continue;
				}
			}
		}
		logger.error("-------待出票结束---------");
		// 人工切换出票
		// 1查人工切换的票 2按玩法分组 3按玩法发送
		List<Ticket> ticketsOfReset = ticketEntityManager.findTicketsOfReset(TicketSupporter.CAI310);
		if (ticketsOfReset == null || ticketsOfReset.isEmpty())
			return;
		Map<Lottery, List<Ticket>> ticketsOfLottery = Maps.newHashMap();
		Lottery lotteryType = null;
		for (Ticket ticket : ticketsOfReset) {
			lotteryType = ticket.getLotteryType();
			
			//BD
//			if(lotteryType==Lottery.DCZC){
//				System.out.println("-----------------Lottery.DCZC send2-----------------------");
//				String current = DateUtil.dateToStrHHmmss(new Date());
//				if("010000".compareTo(current)<=0&&"090000".compareTo(current)>=0){
//					continue;
//				}
//				
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(new Date());
//				calendar.add(Calendar.SECOND, 5);
//				
//				ticket.setSended(Boolean.TRUE);
//				ticket.setSendTime(new Date());
//				ticket.setTicketSupporter(TicketSupporter.CAI310);
//				ticket.setStateCode("0");
//				ticket.setStateModifyTime(new Date());
//				ticket.setOrderNo(UUID.randomUUID().toString());
//				
//				ticket.setStateCode("1");
//				ticket.setStateModifyTime(new Date());
//				ticket.setRemoteTicketId(UUID.randomUUID().toString());
//				ticket.setTicketTime(calendar.getTime());
//				
//				ticketEntityManager.saveTicket(ticket);
//				continue;
//			}
			
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
				CPUtil cpUtil = findCpUtilForLottery(lottery);
				if (cpUtil == null)
					continue;
				List<Ticket> ticketsOfReset_lottery = entry.getValue();
				try {
					for (Ticket ticket : ticketsOfReset_lottery) {
						// 重新查询票以防被临时重置其他出票方
						ticket = ticketEntityManager.getTicket(ticket.getId());
						if (!TicketSupporter.CAI310.equals(ticket.getTicketSupporter())) {
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
					logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票发票出错" + e.getMessage());
					continue;
				}
			} catch (Exception e) {
				logger.error(TicketSupporter.CAI310.getSupporterName() + "玩法｛" + lottery.getLotteryName() + "｝发票出错" + e.getMessage());
				continue;
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error("" + Thread.currentThread() + "sleep出错！");
		}
		logger.error(TicketSupporter.CAI310.getSupporterName() + "出票交易任务执行结束...");
	}

	/**
	 * 发送彩票
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws IOException
	 */
	public void send_method(CPUtil cpUtil, List<Ticket> ticketList) {
		try {
			// 批量送票
			CpResult cpResult = cpUtil.sendTicket(ticketList);
			for (CpTicket cpTicket : cpResult.getData()) {
				try {
					Ticket ticket = this.ticketEntityManager.getTicket(Long.valueOf(cpTicket.getTicketId()));
					if (cpTicket.getIsSuccess()) {
						logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}发送票成功，返回代码：" + cpTicket.getCode());
						ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setTicketSupporter(TicketSupporter.CAI310);
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(""+cpTicket.getTicketId());
						ticketEntityManager.saveTicket(ticket);
					} else {
						ticket.setSendTime(new Date());
						ticket.setStateCodeMessage(""+cpTicket.getCode());
						ticketEntityManager.saveTicket(ticket);
						logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}发送票出错，返回代码：" + cpTicket.getCode());
					}
				} catch (Exception e) {
					logger.error(TicketSupporter.CAI310.getSupporterName() + "发送彩票出现错误[ResultId]" + cpTicket.getCode() + "[OrderId]" + cpTicket.getTicketId() + e.getMessage());
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票查询出错" + e.getMessage());
		}
	}

	/********************************************************************************************************************************************/
	/********* 华丽丽分割线 ************************************************************************************************************************/
	/********************************************************************************************************************************************/

	public void runTask_query() throws Exception {
		logger.error(TicketSupporter.CAI310.getSupporterName() + "出票查询任务执行...");

		List<Long> ticketList = ticketEntityManager.findSendedNoResponseTicket(TicketSupporter.CAI310);
		if (ticketList == null || ticketList.isEmpty())
			return;
 		
		List<Long> ticketListTemp = Lists.newArrayList();
		for (Long id : ticketList) {
			ticketListTemp.add(id);
			if (ticketListTemp.size() % TicketConstant.QUERY_MAXRESULTS == 0) {
				// /够最大查询数
				query_method(ticketListTemp);
				ticketListTemp.clear();
			}
		}
		if (ticketListTemp.size() > 0) {
			query_method(ticketListTemp);
			ticketListTemp.clear();
		}

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error("" + Thread.currentThread() + "sleep出错！");
		}
	}

	public void query_method(List<Long> ticketList) {
		try {
			QueryPVisitor queryPVisitor = CPUtil.confirmTicket(ticketList);
			Ticket ticket = null;
			for (QueryTicket queryTicket : queryPVisitor.getData()) {
				try {
					ticket = new Ticket();
					ticket.setId(Long.valueOf(queryTicket.getTicketId()));
					queryTicket(queryTicket, ticket);
				} catch (Exception e) {
					logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票｛" + ticket.getId() + "｝出票查询出错" + e.getMessage());
					continue;
				}
			}
		} catch (Exception e) {
			logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票查询出错" + e.getMessage());
		}
	}

	/**
	 * 查询彩票
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws IOException
	 */
	private void queryTicket(QueryTicket queryTicket, Ticket ticket) throws IOException {
		ticket = ticketEntityManager.getTicket(ticket.getId());
		if (ticket.getTicketSupporter() != TicketSupporter.CAI310) {
			return;
		}
		if (queryTicket.getIsSuccess()) {
			logger.debug(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}出票查询成功，返回代码：" + queryTicket.getTicketState());
			System.out.println(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}出票查询成功，返回代码：" + queryTicket.getTicketState());
			ticket.setStateCode("1");
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryTicket.getTicketCode());
			ticket.setTicketTime(StringUtils.isBlank(queryTicket.getOperateTime()) ? null : DateUtil.strToDate(queryTicket.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
			if (ticket.getLotteryType().equals(Lottery.JCZQ)) {
				Map<String, Object> classMap = Maps.newHashMap();
				classMap.put("items", JczqMatchItem.class);
				JczqPrintItemObj jczqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JczqPrintItemObj.class, classMap);
				if (null != jczqPrintItemObj) {
					if (!com.cai310.lottery.support.jczq.PassType.P1.equals(com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf("" + jczqPrintItemObj.getPassType())])) {
						JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryTicket, jczqPrintItemObj, ticket);
						if (null != jcMatchOddsList && null != jcMatchOddsList.getJcMatchOdds() && !jcMatchOddsList.getJcMatchOdds().isEmpty()) {
							jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryTicket.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if (StringUtils.isNotBlank(awardString))
								ticket.setExtension(awardString);
						}
					} else {
						// 单关
						try {
//							if (com.cai310.lottery.support.jczq.PlayType.BF.equals(com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf("" + jczqPrintItemObj.getPlayTypeOrdinal())])) {
								JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryTicket, jczqPrintItemObj, ticket);
								if (null != jcMatchOddsList && null != jcMatchOddsList.getJcMatchOdds() && !jcMatchOddsList.getJcMatchOdds().isEmpty()) {
									jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
									jcMatchOddsList.setTicketCode(queryTicket.getTicketCode());
									String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
									if (StringUtils.isNotBlank(awardString))
										ticket.setExtension(awardString);
								}
//							} else {
//								JcMatchOddsList jcMatchOddsList = new JcMatchOddsList();
//								jcMatchOddsList.setTicketCode(queryTicket.getTicketCode());
//								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
//								if (StringUtils.isNotBlank(awardString))
//									ticket.setExtension(awardString);
//							}
						} catch (Exception e) {
							// 单关可能会获取失败
							logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}单关可能会获取失败");
						}
					}
				}

			} 
			else if (ticket.getLotteryType().equals(Lottery.JCLQ)) {
				Map<String, Object> classMap = Maps.newHashMap();
				classMap.put("items", JclqMatchItem.class);
				JclqPrintItemObj jclqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JclqPrintItemObj.class, classMap);
				if (null != jclqPrintItemObj) {
					if (!com.cai310.lottery.support.jclq.PassType.P1.equals(com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf("" + jclqPrintItemObj.getPassType())])) {
						JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryTicket, jclqPrintItemObj, ticket);
						if (null != jcMatchOddsList && null != jcMatchOddsList.getJcMatchOdds() && !jcMatchOddsList.getJcMatchOdds().isEmpty()) {
							jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryTicket.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if (StringUtils.isNotBlank(awardString))
								ticket.setExtension(awardString);
						}
					} else {
						try {
//							if (com.cai310.lottery.support.jclq.PlayType.SFC.equals(com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf("" + jclqPrintItemObj.getPlayTypeOrdinal())])) {
								JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryTicket, jclqPrintItemObj, ticket);
								if (null != jcMatchOddsList && null != jcMatchOddsList.getJcMatchOdds() && !jcMatchOddsList.getJcMatchOdds().isEmpty()) {
									jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
									jcMatchOddsList.setTicketCode(queryTicket.getTicketCode());
									String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
									if (StringUtils.isNotBlank(awardString))
										ticket.setExtension(awardString);
								}
//							} else {
//								JcMatchOddsList jcMatchOddsList = new JcMatchOddsList();
//								jcMatchOddsList.setTicketCode(queryTicket.getTicketCode());
//								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
//								if (StringUtils.isNotBlank(awardString))
//									ticket.setExtension(awardString);
//							}
						} catch (Exception e) {
							// 单关可能会获取失败
							logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}单关可能会获取失败");
						}
					}
				}

			}
			ticketEntityManager.saveTicket(ticket);
		} else {
			if (2<=queryTicket.getTicketState() && 6>=queryTicket.getTicketState()) {
				// /撤单
				// /撤单
				ticket = ticketEntityManager.getTicket(ticket.getId());
				System.out.println(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}查票票成功，返回代码：" + queryTicket.getTicketState());
				ticket.setStateCode("2");
				ticket.setTicketTime(StringUtils.isBlank(queryTicket.getOperateTime()) ? null : DateUtil.strToDate(queryTicket.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
				ticket.setStateModifyTime(new Date());
				ticketEntityManager.saveTicket(ticket);
			}
		}
	}
	/********************************************************************************************************************************************/
	/********* 华丽丽分割线 ************************************************************************************************************************/
	/********************************************************************************************************************************************/
	public void runTask_update() throws Exception {
		logger.error(TicketSupporter.CAI310.getSupporterName() + "奖金更新任务执行...");
		List<Long> ticketList = ticketEntityManager.findSendedNoUpdatePrizeTicket(TicketSupporter.CAI310);
		if (ticketList == null || ticketList.isEmpty())
			return;

		List<Ticket> ticketListTemp = Lists.newArrayList();
		Ticket ticket;
		for (Long id : ticketList) {
			ticket=ticketEntityManager.getTicket(id);
			ticketListTemp.add(ticket);
			if (ticketListTemp.size() % TicketConstant.QUERY_MAXRESULTS == 0) {
				// /够最大查询数
				update_method(ticketListTemp);
				ticketListTemp.clear();
			}
		}
		if (ticketListTemp.size() > 0) {
			update_method(ticketListTemp);
			ticketListTemp.clear();
		}

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error("" + Thread.currentThread() + "sleep出错！");
		}
	}
	
	public void update_method(List<Ticket> ticketList) {
		try {
			CpResult cpResult = CPUtil.updatePrizeTicket(ticketList);
			for (CpTicket cpTicket : cpResult.getData()) {
				try {
					Ticket ticket = this.ticketEntityManager.getTicket(Long.valueOf(cpTicket.getTicketId()));
					if (cpTicket.getIsSuccess()) {
						logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}更新奖金成功，返回代码：" + cpTicket.getCode());
						ticket.setStateModifyTime(new Date());
						ticket.setTicket_synchroned(Boolean.TRUE);
						ticketEntityManager.saveTicket(ticket);
					} else {
						ticket.setStateCodeMessage(""+cpTicket.getCode());
						ticket.setTicket_synchroned(Boolean.FALSE);
						ticketEntityManager.saveTicket(ticket);
						logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}更新奖金出错，返回代码：" + cpTicket.getCode());
					}
				} catch (Exception e) {
					logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票｛" + cpTicket.getTicketId() + "｝更新奖金出错" + e.getMessage());
					continue;
				}
			}
		} catch (Exception e) {
			logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票查询出错" + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws DataException, IOException, DocumentException {
		List<Ticket> newTicketList = Lists.newArrayList();
		Ticket ticket = new Ticket();
		ticket.setId(10038L);
		newTicketList.add(ticket);
		ticket = new Ticket();
		ticket.setId(10032L);
		newTicketList.add(ticket);
		CPUtil cpUtil = new JCZQCPUtil();
		// List<QueryPVisitor> queryPVisitorList =
		// cpUtil.confirmTicket(newTicketList);
		// for (QueryPVisitor queryPVisitor : queryPVisitorList) {
		//
		// }
	}
}
