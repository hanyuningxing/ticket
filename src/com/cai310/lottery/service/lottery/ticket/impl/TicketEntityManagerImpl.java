package com.cai310.lottery.service.lottery.ticket.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.dao.lottery.ticket.ResetTicketSupporterLogDao;
import com.cai310.lottery.dao.lottery.ticket.TicketDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.PrintInterfaceIndex;
import com.cai310.lottery.entity.lottery.ticket.ResetTicketSupporterLog;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.ticket.PrintInterfaceIndexEntityManager;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;

/**
 * 标准出票格式的ticket实体操作类
 * 
 * @author jack
 * 
 */
@Service("ticketEntityManager")
@Transactional
public class TicketEntityManagerImpl implements TicketEntityManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketDao ticketDao;
	
	@Autowired
	protected ResetTicketSupporterLogDao resetTicketSupporterDao;

	@Autowired
	protected PrintInterfaceIndexEntityManager printInterfaceIndexEntityManager;
	
	@Autowired
	protected PrintEntityManager printEntityManager;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Ticket getTicket(Long id) {
		return this.ticketDao.get(id);
	}

	@Override
	public Ticket saveTicket(Ticket entity) {
		return this.ticketDao.save(entity);
	}

	@Override
	public Ticket updateTicket(Ticket entity) {
		return this.ticketDao.save(entity);
	}
	
	@Override
	public void pauseSend(Ticket ticket,String adminName){
		this.doPauseSend(ticket.getId(),adminName);
	}
	
	@Override
	public void pauseSendOfBatch(List<Long> ticketIds,String adminName){
		if(ticketIds==null){return;}
		for(Long ticketId:ticketIds){
			this.doPauseSend(ticketId,adminName);
		}
	}
	
	/**
	 * 暂停发送票
	 * @param ticketId
	 * @param adminName
	 */
	private void doPauseSend(Long ticketId,String adminName){
		Ticket ticket = this.ticketDao.get(ticketId);
		if(ticket!=null && !ticket.isPause()){
			ticket.setPause(true);
			ticket.setPauseOperTime(new Date());
			this.ticketDao.save(ticket);
			//保存暂停发送日志
			ResetTicketSupporterLog rtsLog = new ResetTicketSupporterLog();
			rtsLog.setTicketId(ticket.getId());
			rtsLog.setOperName(adminName);
			rtsLog.setTicketSupporter(ticket.getTicketSupporter());
			rtsLog.setResetTikcetSupporter(null);
			rtsLog.setSchemeNumber(ticket.getSchemeNumber());
			rtsLog.setSendTime(ticket.getSendTime());
			rtsLog.setStateCode(ticket.getStateCode());
			rtsLog.setStateModifyTime(ticket.getStateModifyTime());
			rtsLog.setRemark("暂停送票");
			resetTicketSupporterDao.save(rtsLog);
		}
	}
	
	@Override
	public void resetTicketSupporter(Ticket ticket,TicketSupporter ticketSupporter,String adminName){
		this.doResetTicketSupporter(ticket.getId(), ticketSupporter, adminName);
	}
	
	@Override
	public void resetTicketSupporterOfBatch(List<Long> ticketIds,TicketSupporter ticketSupporter,String adminName){
		for(Long ticketId : ticketIds){
			this.doResetTicketSupporter(ticketId, ticketSupporter, adminName);
		}		
	}
	
	private void doResetTicketSupporter(Long ticketId,TicketSupporter ticketSupporter,String adminName){
		Ticket ticket = this.ticketDao.get(ticketId);
		if(ticket!=null && !"1".equals(ticket.getStateCode())){
			//保存切换日志
			ResetTicketSupporterLog rtsLog = new ResetTicketSupporterLog();
			rtsLog.setTicketId(ticket.getId());
			rtsLog.setOperName(adminName);
			rtsLog.setTicketSupporter(ticket.getTicketSupporter());
			rtsLog.setResetTikcetSupporter(ticketSupporter);
			rtsLog.setSchemeNumber(ticket.getSchemeNumber());
			rtsLog.setSendTime(ticket.getSendTime());
			rtsLog.setStateCode(ticket.getStateCode());
			rtsLog.setStateModifyTime(ticket.getStateModifyTime());
			rtsLog.setRemark("切换出票商");
			resetTicketSupporterDao.save(rtsLog);

			//重置票状态为新出票商
			ticket.setTicketSupporter(ticketSupporter);
			ticket.setSended(false);
			ticket.setSendTime(null);
			ticket.setStateCode(null);
			ticket.setStateCodeMessage(null);
			ticket.setStateModifyTime(null);
			ticket.setPause(false);
			this.ticketDao.save(ticket);
		}
	}

	@Override
	public void saveTickets(PrintInterfaceIndex printInterfaceIndex, List<Ticket> ticketList, TicketSupporter ticketSupporter) {
		for (Ticket ticket : ticketList) {
			ticket.setTicketSupporter(ticketSupporter);
			this.ticketDao.save(ticket);
		}
		// 操作成功需要将当前操作的接口表ID记录回数据库
		printInterfaceIndexEntityManager.saveIndex(printInterfaceIndex);
	}

	@Override
	public void saveTickets(PrintInterface printInterface, List<Ticket> ticketList, TicketSupporter ticketSupporter) {
		for (Ticket ticket : ticketList) {
			ticket.setTicketSupporter(ticketSupporter);
			this.ticketDao.save(ticket);
		}
		// 操作成功需要将当前操作的接口表设为已拆票记录回数据库
		printInterface.setPrintState(PrintInterfaceState.DISASSEMBLED);
		printEntityManager.savePrintInterface(printInterface);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ticket> findTicket(final List<TicketQuery> ticketQueryS,final TicketSupporter ticketSupporter){
		if(ticketQueryS.isEmpty())return null;
		return (List<Ticket>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("sended", Boolean.FALSE));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				Disjunction disjun = Restrictions.disjunction();
				for(TicketQuery tq:ticketQueryS){
					if(Lottery.JCZQ.equals(tq.getLotteryType())){
						disjun.add(Restrictions.and(Restrictions.eq("lotteryType", tq.getLotteryType()), Restrictions.gt("officialEndTime", new Date())));
					}else if(Lottery.JCLQ.equals(tq.getLotteryType())){
						disjun.add(Restrictions.and(Restrictions.eq("lotteryType", tq.getLotteryType()), Restrictions.gt("officialEndTime", new Date())));
					}else{
						disjun.add(Restrictions.and(Restrictions.eq("lotteryType", tq.getLotteryType()), Restrictions.eq("periodNumber", tq.getIssueNumber())));
					}
				}	
				criteria.add(disjun);
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ticket> findTicket(final TicketQuery ticketQuery){
		return (List<Ticket>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("sended", Boolean.FALSE));
				criteria.add(Restrictions.eq("lotteryType", ticketQuery.getLotteryType()));
				if(ticketQuery.getLotteryType().equals(Lottery.DCZC)) {
					criteria.add(Restrictions.eq("betType", ticketQuery.getBetType()));
				}
				criteria.add(Restrictions.eq("periodNumber", ticketQuery.getIssueNumber()));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSendedNoResponseTicket(final TicketSupporter ticketSupporter){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.or(Restrictions.eq("stateCode", "0"),Restrictions.isNull("stateCode")));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.gt("sendTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				criteria.addOrder(Order.asc("sendTime"));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSendedNoUpdatePrizeTicket(final TicketSupporter ticketSupporter){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.eq("checked", Boolean.TRUE));
				criteria.add(Restrictions.eq("synchroned", Boolean.TRUE));
				criteria.add(Restrictions.or(Restrictions.eq("ticket_synchroned", Boolean.FALSE),Restrictions.isNull("ticket_synchroned")));
				criteria.add(Restrictions.eq("won", Boolean.TRUE));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSendedNoResponseTicketByLottery(final TicketSupporter ticketSupporter ,final Lottery lottery){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.or(Restrictions.eq("stateCode", "0"),Restrictions.isNull("stateCode")));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.eq("lotteryType", lottery));
				criteria.add(Restrictions.gt("sendTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				criteria.addOrder(Order.asc("ticketIndex"));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ticket> findTicketsOfReset(final TicketSupporter ticketSupporter){
		return (List<Ticket>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.eq("sended", Boolean.FALSE));
				criteria.add(Restrictions.eq("pause", Boolean.FALSE));
				criteria.add(Restrictions.gt("officialEndTime", new Date()));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findTicket(final TicketSupporter ticketSupporter,final Lottery lottery){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.isNull("ticketSupporter"));
				criteria.add(Restrictions.eq("sended", Boolean.FALSE));
				criteria.add(Restrictions.eq("pause", Boolean.FALSE));
				Disjunction disjun = Restrictions.disjunction();
				disjun.add(Restrictions.and(Restrictions.eq("lotteryType", lottery), Restrictions.gt("officialEndTime", new Date())));
				criteria.add(disjun);
				//criteria.addOrder(Order.asc("officialEndTime"));//安截止时间
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findUnreturnAwardTicket(final TicketSupporter ticketSupporter,final Lottery lottery){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
//				criteria.add(Restrictions.eq("award_synchroned", Boolean.FALSE));
				criteria.add(Restrictions.or(Restrictions.eq("award_synchroned", Boolean.FALSE),Restrictions.and(Restrictions.gt("prize", Double.valueOf(0)),Restrictions.eq("returnAward", Boolean.FALSE))));
				criteria.add(Restrictions.eq("lotteryType", lottery));
				criteria.add(Restrictions.lt("officialEndTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS/100); 
				return criteria.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findNoTicketCodeTicketByLottery(final TicketSupporter ticketSupporter ,final Lottery lottery){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.or(Restrictions.isNull("remoteTicketId"),Restrictions.eq("remoteTicketId","")));
				criteria.add(Restrictions.eq("stateCode", "1"));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.eq("lotteryType", lottery));
				criteria.add(Restrictions.gt("sendTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findTicket_synchronedTicketByLottery(final TicketSupporter ticketSupporter ,final Lottery lottery){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("won", Boolean.TRUE));
				criteria.add(Restrictions.eq("checked", Boolean.TRUE));
				criteria.add(Restrictions.eq("lotteryType", lottery));
				criteria.add(Restrictions.eq("ticket_synchroned",  Boolean.FALSE));
				criteria.add(Restrictions.gt("stateModifyTime", DateUtil.getdecDateOfMinute(new Date(),24*60)));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountSuccessByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("stateCode", "1"));//成功出票状态
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountPrintByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("stateCode", "0"));//委托出票状态
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountNoSendByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.FALSE));
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountFailedByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("stateCode", "2"));
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findPrintinterfaceIdByStateModifyTime(){
		return (List<Long>) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				ProjectionList propList = Projections.projectionList();
				propList.add(Projections.property("printinterfaceId"), "printinterfaceId");
				criteria.setProjection(propList);
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				criteria.add(Restrictions.eq("synchroned", Boolean.FALSE));//未同步
				criteria.add(Restrictions.gt("stateModifyTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));//前6小时
				return criteria.list();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findTicketByPrintInterfaceId(final Long printinterfaceId){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				return criteria.list();
			}
		});		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findTicketIdByPrintInterfaceId(final Long printinterfaceId){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				ProjectionList propList = Projections.projectionList();
				propList.add(Projections.property("id"), "id");
				criteria.setProjection(propList);
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				return criteria.list();
			}
		});		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ticket> findTicketByOrderNo(final String orderNo){
		return (List<Ticket>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("orderNo", orderNo));
				return criteria.list();
			}
		});		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ticket> findTicketByIds(final List<Long> ids){
		if(null==ids||ids.isEmpty())return null;
		return (List<Ticket>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.in("id", ids));
				criteria.addOrder(Order.asc("id"));
				return criteria.list();
			}
		});		
	}
	
	/**
	 * 根据出票商，彩种查询票数
	 * @param ticketSupporter
	 * @param lottery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ticket> findResponseTicketByLottery(final TicketSupporter ticketSupporter ,final Lottery lottery){
		return (List<Ticket>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.or(Restrictions.eq("stateCode", "0"),Restrictions.isNull("stateCode")));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.add(Restrictions.eq("lotteryType", lottery));
				criteria.add(Restrictions.gt("sendTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.LIMIT_QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
}
