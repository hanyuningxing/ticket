package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.liangcai.utils.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.liangcai.utils.DczcLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.DltLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.JclqLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.JczqLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.KlpkLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.LczcLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.LiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.PlLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SczcLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SdEl11to5LiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SevenLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SevenStarLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SfzcLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SscLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.SsqLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.Weflare3dLiangCaiUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.jclq.JclqSpUtil;
import com.cai310.lottery.ticket.protocol.liangcai.utils.jczq.JczqSpUtil;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TicketTransactionTask_liang {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	
	/**
	 * 根据彩种取对应的出票格式解析器
	 * @param lottery
	 * @return
	 */
	private LiangCaiUtil findCpUtilForLottery(Lottery lottery){
		LiangCaiUtil cpUtil = null;
		if(lottery.equals(Lottery.SSQ)){
			cpUtil = new SsqLiangCaiUtil();
		}else if(lottery.equals(Lottery.DLT)){
			cpUtil = new DltLiangCaiUtil();
		}else if(lottery.equals(Lottery.WELFARE3D)){
			cpUtil = new Weflare3dLiangCaiUtil();
		}else if(lottery.equals(Lottery.SFZC)){
			cpUtil = new SfzcLiangCaiUtil();
		}else if(lottery.equals(Lottery.SCZC)){
			cpUtil = new SczcLiangCaiUtil();
		}else if(lottery.equals(Lottery.LCZC)){
			cpUtil = new LczcLiangCaiUtil();
		}else if(lottery.equals(Lottery.PL)){
			cpUtil = new PlLiangCaiUtil();
		}else if(lottery.equals(Lottery.SDEL11TO5)){
			cpUtil = new SdEl11to5LiangCaiUtil();
		}else if(lottery.equals(Lottery.DCZC)){
			cpUtil = new DczcLiangCaiUtil();
		}else if(lottery.equals(Lottery.SSC)){
			cpUtil = new SscLiangCaiUtil();
		}else if(lottery.equals(Lottery.SEVEN)){
			cpUtil = new SevenLiangCaiUtil();
		}else if(lottery.equals(Lottery.JCZQ)){
			cpUtil = new JczqLiangCaiUtil();
		}else if(lottery.equals(Lottery.JCLQ)){
			cpUtil = new JclqLiangCaiUtil();
		}else if(lottery.equals(Lottery.KLPK)){
			cpUtil=new KlpkLiangCaiUtil();
		}else if(lottery.equals(Lottery.SEVENSTAR)){//新增
			cpUtil=new SevenStarLiangCaiUtil();
		}else{
			logger.error(TicketSupporter.LIANG.getSupporterName()+":没有对应的<"+lottery.getLotteryName()+">彩种出票格式解析器！");
		}
		return cpUtil;
	}
	
	
	/******华丽丽分割线********************************************************************************************************************/
	
	
	public void runTask() throws Exception {
		logger.info(TicketSupporter.LIANG.getSupporterName()+"出票交易任务执行...");
		
		List<Ticket> ticketListTemp = Lists.newArrayList();
		
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.LIANG);
		if(lotterySupporters!=null && !lotterySupporters.isEmpty()){
			for (LotterySupporter lotterySupporter : lotterySupporters) {
				try{
					 Lottery lottery = lotterySupporter.getLotteryType();
					 List<Long> ticketList = ticketEntityManager.findTicket(TicketSupporter.LIANG,lottery);
					 if(ticketList==null || ticketList.isEmpty())continue;
					 LiangCaiUtil cpUtil = findCpUtilForLottery(lottery);
					 if(cpUtil==null)continue;
					 
					 Ticket ticket;
					 try{
						 for (Long id : ticketList) {
							ticket=ticketEntityManager.getTicket(id);
							if(ticket.getTicketSupporter()!=null)continue;
							ticketListTemp.add(ticket);
							if(ticketListTemp.size()%TicketConstant.SEND_MAXRESULTS==0){
								///够最大送票数
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
		
		
		//人工切换出票 
		//1查人工切换的票  2按玩法分组  3按玩法发送
		List<Ticket> ticketsOfReset = ticketEntityManager.findTicketsOfReset(TicketSupporter.LIANG);
		if(ticketsOfReset==null || ticketsOfReset.isEmpty())return;
		Map<Lottery,List<Ticket>> ticketsOfLottery = Maps.newHashMap();
		Lottery lotteryType=null;
		for(Ticket ticket : ticketsOfReset){
			lotteryType = ticket.getLotteryType();
			List<Ticket> tickets = ticketsOfLottery.get(lotteryType);
			if(tickets==null){
				tickets = Lists.newArrayList();
				tickets.add(ticket);
				ticketsOfLottery.put(lotteryType, tickets);
			}else{
				tickets.add(ticket);
			}
		}		
		for (Map.Entry<Lottery,List<Ticket>> entry : ticketsOfLottery.entrySet()) {
			Lottery lottery = entry.getKey();
			try{
				LiangCaiUtil cpUtil = findCpUtilForLottery(lottery);
				if(cpUtil==null)continue;
				List<Ticket> ticketsOfReset_lottery = entry.getValue();
			    try{
					for (Ticket ticket : ticketsOfReset_lottery) {
						//重新查询票以防被临时重置其他出票方
						ticket=ticketEntityManager.getTicket(ticket.getId());
						if(!TicketSupporter.LIANG.equals(ticket.getTicketSupporter())){
							continue;
						}
						ticketListTemp.add(ticket);
						if(ticketListTemp.size()%TicketConstant.SEND_MAXRESULTS==0){
							///够最大送票数
							send_method(cpUtil,ticketListTemp);
						    ticketListTemp.clear();
						}
					}
					if(ticketListTemp.size()>0){
						send_method(cpUtil,ticketListTemp);
						ticketListTemp.clear();
					}
				}catch (Exception e) {
					logger.error(TicketSupporter.LIANG.getSupporterName()+"彩票发票出错"+e.getMessage());
					continue;
				}
			}catch (Exception e) {
				logger.error(TicketSupporter.LIANG.getSupporterName()+"玩法｛"+lottery.getLotteryName()+"｝发票出错"+e.getMessage());
				continue;
			}
		}
				
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
	}
	
	public void send_method(LiangCaiUtil cpUtil,List<Ticket> ticketList){
		try{
			//批量送票
			CpResultVisitor cpResultVisitor = cpUtil.sendTicket(ticketList);
			Map<String,Boolean> orderArr = cpResultVisitor.getResultMap();
			Ticket ticket;
			for (Map.Entry<String,Boolean> m: orderArr.entrySet()) {
				try{
					if(m.getValue()){
						ticket=ticketEntityManager.getTicket(Long.valueOf(m.getKey()));
						logger.error(TicketSupporter.LIANG.getSupporterName()+"｛"+ticket.getId()+"｝发送票成功，返回代码："+m.getValue());
					    ticket.setSended(Boolean.TRUE);
						ticket.setSendTime(new Date());
						ticket.setStateCode("0");
						ticket.setStateModifyTime(new Date());
						ticket.setOrderNo(m.getKey());
						ticket.setTicketSupporter(TicketSupporter.LIANG);
						ticketEntityManager.saveTicket(ticket);
					}else{
						ticket=ticketEntityManager.getTicket(Long.valueOf(m.getKey()));
						ticket.setSendTime(new Date());
						ticketEntityManager.saveTicket(ticket);
						logger.error(TicketSupporter.LIANG.getSupporterName()+"｛"+ticket.getId()+"｝发送票出错，返回代码："+m.getValue());
					}
				}catch(Exception e){
					logger.error(TicketSupporter.LIANG.getSupporterName()+"｛"+m.getKey()+"｝发送票出错，返回代码："+m.getValue());
					continue;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(TicketSupporter.LIANG.getSupporterName()+"彩票查询出错"+e.getMessage());
		}
	}
	
	
	
	/********************************************************************************************************************************************/
	/*********华丽丽分割线************************************************************************************************************************/
	/********************************************************************************************************************************************/

	
	
	public void runTask_query() throws Exception {
		logger.info(TicketSupporter.LIANG.getSupporterName()+"出票查询任务执行...");
		
		List<Long> ticketList = ticketEntityManager.findSendedNoResponseTicket(TicketSupporter.LIANG);
		if(ticketList==null || ticketList.isEmpty())return;
		
		List<Long> ticketListTemp = Lists.newArrayList();
		for (Long ticketId:ticketList) {
			ticketListTemp.add(ticketId);
			if(ticketListTemp.size()%TicketConstant.QUERY_MAXRESULTS==0){
				///够最大查询数
				query_method(ticketListTemp);
				ticketListTemp.clear();
			}
		}
		if(ticketListTemp.size()>0){
			query_method(ticketListTemp);
			ticketListTemp.clear();
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.error(""+Thread.currentThread()+"sleep出错！");
		}
	}
	
	private void query_method(List<Long> ticketList){
		try{
			Ticket ticket = null;
			for (Long id : ticketList) {
				try{
					ticket = ticketEntityManager.getTicket(id);
					QueryPVisitor queryPVisitor;
					if(ticket.getLotteryType().equals(Lottery.JCZQ)){
						 queryPVisitor = LiangCaiUtil.confirmTicket_jc(ticket);
					}else{
						 queryPVisitor = LiangCaiUtil.confirmTicket(ticket);
					}
					queryTicket(queryPVisitor,ticket);
				}catch (Exception e) {
					logger.error(TicketSupporter.LIANG.getSupporterName()+"彩票｛"+ticket.getId()+"｝出票查询出错"+e.getMessage());
					continue;
				}
			}
		}catch (Exception e) {
			logger.error(TicketSupporter.LIANG.getSupporterName()+"出票查询出错"+e.getMessage());
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
		ticket = ticketEntityManager.getTicket(ticket.getId());
		if(ticket.getTicketSupporter()!=TicketSupporter.LIANG){
			return;
		}
		if(queryPVisitor.getIsSuccess()){
			logger.error(TicketSupporter.LIANG.getSupporterName()+"出票查询成功，返回代码："+queryPVisitor.getOrderId());
			ticket.setStateCode("1");
			ticket.setStateModifyTime(new Date());
			if(null!=queryPVisitor.getAwards()&&queryPVisitor.getAwards().indexOf("_")!=-1){
				ticket.setRemoteTicketId(queryPVisitor.getAwards().split("_")[0]);	
			}
			//ticket.setRemoteTicketId(queryPVisitor.getTicketCode());		
		    if(ticket.getLotteryType().equals(Lottery.JCZQ)){
				Map<String,Object> classMap = Maps.newHashMap();
				classMap.put("items", JczqMatchItem.class);
				JczqPrintItemObj jczqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JczqPrintItemObj.class, classMap);
				if(null!=jczqPrintItemObj){
					if(!com.cai310.lottery.support.jczq.PassType.P1.equals(
							 com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(""+jczqPrintItemObj.getPassType())])){
				    	JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(ticket.getRemoteTicketId());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						//单关
						try{
//							if(com.cai310.lottery.support.jczq.PlayType.BF.equals(
//							com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])
//						||com.cai310.lottery.support.jczq.PlayType.SPF.equals(
//								com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])){
							JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
					    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
								jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
								jcMatchOddsList.setTicketCode(ticket.getRemoteTicketId());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
//					}else{
//						JcMatchOddsList jcMatchOddsList = new JcMatchOddsList();
//						jcMatchOddsList.setTicketCode(ticket.getRemoteTicketId());
//						jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
//						String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
//						if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);
//					}
						}catch(Exception e){
							//单关可能会获取失败
							logger.error(TicketSupporter.LIANG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}

		   }else if(ticket.getLotteryType().equals(Lottery.JCLQ)){
			    Map<String,Object> classMap = Maps.newHashMap();
				classMap.put("items", JclqMatchItem.class);
				JclqPrintItemObj jclqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JclqPrintItemObj.class, classMap);
				if(null!=jclqPrintItemObj){
					if(!com.cai310.lottery.support.jclq.PassType.P1.equals(
							 com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf(""+jclqPrintItemObj.getPassType())])){
				    	JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						//单关
						try{
//							if(com.cai310.lottery.support.jclq.PlayType.SFC.equals(
//									com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf(""+jclqPrintItemObj.getPlayTypeOrdinal())])){
									JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
							    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
										jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
										jcMatchOddsList.setTicketCode(ticket.getRemoteTicketId());
										String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
										if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);
									}
//							}else{
//								JcMatchOddsList jcMatchOddsList = new JcMatchOddsList();
//								jcMatchOddsList.setTicketCode(ticket.getRemoteTicketId());
//								jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
//								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
//								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);
//							}
						}catch(Exception e){
							//单关可能会获取失败
							logger.error(TicketSupporter.LIANG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}						
		   }
		   ticketEntityManager.saveTicket(ticket);
		}
	}
}
