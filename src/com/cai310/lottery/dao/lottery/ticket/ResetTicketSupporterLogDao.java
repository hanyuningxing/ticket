package com.cai310.lottery.dao.lottery.ticket;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.ticket.ResetTicketSupporterLog;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 重置出票商日志DAO
 * @author jack
 *
 */
@Repository
public class ResetTicketSupporterLogDao  extends HibernateDao<ResetTicketSupporterLog, Long> {

}
