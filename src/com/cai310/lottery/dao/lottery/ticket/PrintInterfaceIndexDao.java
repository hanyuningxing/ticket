package com.cai310.lottery.dao.lottery.ticket;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.ticket.PrintInterfaceIndex;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 接口表当前格式转换索引记录Dao
 * @author jack
 *
 */
@Repository
public class PrintInterfaceIndexDao extends HibernateDao<PrintInterfaceIndex, Long> {

}
