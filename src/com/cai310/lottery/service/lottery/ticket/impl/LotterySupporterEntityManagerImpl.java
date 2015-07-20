package com.cai310.lottery.service.lottery.ticket.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.ticket.LotterySupporterDao;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.PrintInterfaceIndexEntityManager;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

/**
 * 彩种出票商设置实体操作类
 * 
 * @author jack
 * 
 */
@Service("lotterySupporterEntityManager")
@Transactional
public class LotterySupporterEntityManagerImpl implements LotterySupporterEntityManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected LotterySupporterDao lotterySupporterDao;
 
	@Autowired
	protected PrintInterfaceIndexEntityManager printInterfaceIndexEntityManager;
	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#getLotterySupporter(java.lang.Long)
	 */
	@Override
	public LotterySupporter getLotterySupporter(Long id) {
		return this.lotterySupporterDao.get(id);
	}

	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#saveLotterySupporter(com.cai310.lottery.entity.lottery.ticket.LotterySupporter)
	 */
	@Override
	public LotterySupporter saveLotterySupporter(LotterySupporter entity) {
		return this.lotterySupporterDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#updateLotterySupporter(com.cai310.lottery.entity.lottery.ticket.LotterySupporter)
	 */
	@Override
	public LotterySupporter updateLotterySupporter(LotterySupporter entity) {
		return this.lotterySupporterDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#saveLotterySupporters(java.util.List)
	 */
	@Override
	public void saveLotterySupporters(List<LotterySupporter> lotterySupporterList) {
		for (LotterySupporter lotterySupporter : lotterySupporterList) {
			this.lotterySupporterDao.save(lotterySupporter);
		}
	}
		
	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#findLotterySupporter()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<LotterySupporter> findLotterySupporter(){
		return (List<LotterySupporter>)lotterySupporterDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.addOrder(Order.asc("id"));
				return criteria.list();
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#findLotteryBySupporter(com.cai310.lottery.ticket.common.TicketSupporter)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<LotterySupporter> findLotteryBySupporter(final TicketSupporter ticketSupporter){
		return (List<LotterySupporter>)lotterySupporterDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("usable", Boolean.TRUE));
				criteria.add(Restrictions.eq("ticketSupporter", ticketSupporter));
				criteria.addOrder(Order.asc("id"));
				return criteria.list();
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.service.lottery.ticket.impl.LotterySupporterEntityManage#findSupporterByLottery(com.cai310.lottery.common.Lottery)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public LotterySupporter findSupporterByLottery(final Lottery lotteryType){
		return (LotterySupporter)lotterySupporterDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.addOrder(Order.asc("id"));
				List<LotterySupporter> lsList = criteria.list();
				LotterySupporter ls= null;
				if(lsList!=null&&!lsList.isEmpty()){
					ls = lsList.get(0);
				}
				return ls;
			}
		});
	}
}
