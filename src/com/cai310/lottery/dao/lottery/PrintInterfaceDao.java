package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 打印接口DAO
 * 
 */
@Repository
public class PrintInterfaceDao extends HibernateDao<PrintInterface, Long> {

}
