package com.cai310.lottery.dao.lottery.ticket;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 标准出票格式DAO
 * 
 */
@Repository
public class LotterySupporterDao extends HibernateDao<LotterySupporter, Long> {
}
