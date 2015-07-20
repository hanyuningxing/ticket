package com.cai310.lottery.task.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItem;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItem;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.CpResultVisitor;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.CpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.DltCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.El11to5CpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.JclqCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.JczqCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.LczcCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.PlCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.QueryPVisitor;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.SczcCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.SevenCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.SfzcCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.SscCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.SsqCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.Weflare3dCpdyjUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.jclq.JclqSpUtil;
import com.cai310.lottery.ticket.protocol.cpdyj.utils.jczq.JczqSpUtil;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.utils.JsonUtil;

public class TicketTransactionTask_cpdyj {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	public void runTask_query() throws Exception {
		logger.info("出票查询任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.CPDYJ);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
//				Lottery lottery = lotterySupporter.getLotteryType();
//				List<Ticket> ticketList = ticketEntityManager.findSendedNoResponseTicketByLottery(TicketSupporter.CPDYJ,lottery);
//				if(ticketList==null || ticketList.isEmpty())continue;
//				CpdyjUtil cpUtil = null;
//					for (Ticket ticket : ticketList) {
//						try{
//							if(lottery.equals(Lottery.JCZQ)){
//								cpUtil = new JczqCpdyjUtil(ticket);
//							}else if(lottery.equals(Lottery.JCLQ)){
//								cpUtil = new JclqCpdyjUtil(ticket);
//							}else if(lottery.equals(Lottery.WELFARE3D)){
//								cpUtil = new Weflare3dCpdyjUtil();
//							}else if(lottery.equals(Lottery.DLT)){
//								cpUtil = new DltCpdyjUtil();
//							}else if(lottery.equals(Lottery.SSQ)){
//								cpUtil = new SsqCpdyjUtil();
//							}else if(lottery.equals(Lottery.SFZC)){
//								cpUtil = new SfzcCpdyjUtil();
//							}else if(lottery.equals(Lottery.SCZC)){
//								cpUtil = new SczcCpdyjUtil();
//							}else if(lottery.equals(Lottery.LCZC)){
//								cpUtil = new LczcCpdyjUtil();
//							}else if(lottery.equals(Lottery.EL11TO5)){
//								cpUtil = new El11to5CpdyjUtil();
//							}else if(lottery.equals(Lottery.SSC)){
//								cpUtil = new SscCpdyjUtil();
//							}else if(lottery.equals(Lottery.PL)){
//								cpUtil = new PlCpdyjUtil();
//							}else if(lottery.equals(Lottery.SEVEN)){
//								cpUtil = new SevenCpdyjUtil();
//							}else{
//								logger.error("找不到彩种所对应的出票");
//								continue;
//							}
//							queryTicket(cpUtil.confirmTicket(ticket),ticket);
//						}catch (Exception e) {
//							logger.error("彩票｛"+ticket.getId()+"｝查询出错"+e.getMessage());
//							continue;
//						}
//					}
			}catch (Exception e) {
				logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝发票出错"+e.getMessage());
				continue;
			}
		}
		
	} 
	
	public void runTask() throws Exception {
		logger.info("出票交易任务执行...");
		List<LotterySupporter> lotterySupporters = lotterySupporterEntityManager.findLotteryBySupporter(TicketSupporter.CPDYJ);
		if(lotterySupporters==null || lotterySupporters.isEmpty())return;
		
		for (LotterySupporter lotterySupporter : lotterySupporters) {
			try{
//				Lottery lottery = lotterySupporter.getLotteryType();
//				List<Ticket> ticketList = ticketEntityManager.findTicket(lottery);
//				if(ticketList==null || ticketList.isEmpty())continue;
//				CpdyjUtil cpUtil = null;
//				for (Ticket ticket : ticketList) {
//					try{
//						if(lottery.equals(Lottery.JCZQ)){
//							cpUtil = new JczqCpdyjUtil(ticket);
//						}else if(lottery.equals(Lottery.JCLQ)){
//							cpUtil = new JclqCpdyjUtil(ticket);
//						}else if(lottery.equals(Lottery.WELFARE3D)){
//							cpUtil = new Weflare3dCpdyjUtil();
//						}else if(lottery.equals(Lottery.DLT)){
//							cpUtil = new DltCpdyjUtil();
//						}else if(lottery.equals(Lottery.SSQ)){
//							cpUtil = new SsqCpdyjUtil();
//						}else if(lottery.equals(Lottery.SFZC)){
//							cpUtil = new SfzcCpdyjUtil();
//						}else if(lottery.equals(Lottery.SCZC)){
//							cpUtil = new SczcCpdyjUtil();
//						}else if(lottery.equals(Lottery.LCZC)){
//							cpUtil = new LczcCpdyjUtil();
//						}else if(lottery.equals(Lottery.EL11TO5)){
//							cpUtil = new El11to5CpdyjUtil();
//						}else if(lottery.equals(Lottery.SSC)){
//							cpUtil = new SscCpdyjUtil();
//						}else if(lottery.equals(Lottery.PL)){
//							cpUtil = new PlCpdyjUtil();
//						}else if(lottery.equals(Lottery.SEVEN)){
//							cpUtil = new SevenCpdyjUtil();
//						}else{
//							logger.error("找不到彩种所对应的出票");
//							continue;
//						}
//						sendTicket(cpUtil.sendTicket(ticket),ticket);
//						}catch (Exception e) {
//							logger.error("彩票｛"+ticket.getId()+"｝发票出错"+e.getMessage());
//							continue;
//						}
//					}
			}catch (Exception e) {
				logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝发票出错"+e.getMessage());
				continue;
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
	}
	
	
	/**
	 * 处理彩票
	 * @param
	 * @param 
	 * @return
	 * @throws IOException
	 */
	private void sendTicket(CpResultVisitor cpResultVisitor,Ticket ticket) throws IOException{
		if(cpResultVisitor.getIsSuccess()){
			logger.error(TicketSupporter.CPDYJ.getSupporterName()+"｛"+ticket.getId()+"｝发送票成功，返回代码："+cpResultVisitor.getResult());
			    ticket.setSended(Boolean.TRUE);
				ticket.setSendTime(new Date());
				ticket.setStateCode("0");
				ticket.setStateModifyTime(new Date());
				ticket.setOrderNo(cpResultVisitor.getOrderId());
				ticket.setTicketSupporter(TicketSupporter.CPDYJ);
				ticketEntityManager.saveTicket(ticket);
		}else{
			ticket.setSendTime(new Date());
			ticketEntityManager.saveTicket(ticket);
			logger.error(TicketSupporter.CPDYJ.getSupporterName()+"｛"+ticket.getId()+"｝发送票出错，返回代码："+cpResultVisitor.getResult());
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
			logger.error(TicketSupporter.CPDYJ.getSupporterName()+"查票票成功，返回代码："+queryPVisitor.getOrderId());
			ticket.setStateCode("1");
			ticket.setStateModifyTime(new Date());
			//ticket.setRemoteTicketId(queryPVisitor.getTicketCode());		
		    if(ticket.getLotteryType().equals(Lottery.JCZQ)){
		    	@SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JczqMatchItem.class);
				JczqPrintItemObj jczqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JczqPrintItemObj.class, classMap);
				if(null!=jczqPrintItemObj){
			    	JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
			    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
						jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
						String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
						if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
					}
				}

		   }else if(ticket.getLotteryType().equals(Lottery.JCLQ)){
			    @SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JclqMatchItem.class);
				JclqPrintItemObj jclqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JclqPrintItemObj.class, classMap);
				if(null!=jclqPrintItemObj){
			    	JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp_test(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
			    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
						jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
						String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
						if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
					}
				}
						
		   }
		   ticketEntityManager.saveTicket(ticket);
		}
	}
}
