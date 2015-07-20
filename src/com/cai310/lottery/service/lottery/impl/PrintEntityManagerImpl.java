package com.cai310.lottery.service.lottery.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.dao.lottery.PrintInterfaceDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;

/**
 * 打印相关实体管理实现类.
 * 
 */
@Service("printEntityManagerImpl")
@Transactional
public class PrintEntityManagerImpl implements PrintEntityManager {

	@Autowired
	protected PrintInterfaceDao printInterfaceDao;

	public PrintInterface savePrintInterface(PrintInterface entity) {
		return printInterfaceDao.save(entity);
	}
	
	public PrintInterface getPrintInterfaceById(Long id){
		return printInterfaceDao.get(id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PrintInterface> findPrintInterfaceFromIndex(final Long index){
		return (List<PrintInterface>)printInterfaceDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.gt("id", index));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.QUERY_MAXRESULTS); 
				return criteria.list();
			}
		}); 
	}
	
	////cyy-c 修改出票拆单读法
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PrintInterface> findUnDisassemblePrintInterface(){
		return (List<PrintInterface>)printInterfaceDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printState", PrintInterfaceState.UNDISASSEMBLE));
				criteria.add(Restrictions.gt("createTime", DateUtil.getdecDateOfMinute(new Date(),60*12)));
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}
	
	////cyy-c 
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PrintInterface findPrintInterfaceBy(final String schemeNumber,final Lottery lotteryType){
		return (PrintInterface)printInterfaceDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("schemeNumber",schemeNumber));
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.addOrder(Order.desc("id"));
				if(null!=criteria.list()&&!criteria.list().isEmpty()){
					return criteria.list().get(0);
				}
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findDisassembledNoSynPrintInterface(){
		return (List<Long>)printInterfaceDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				ProjectionList propList = Projections.projectionList();
				propList.add(Projections.property("id"), "id");
				criteria.setProjection(propList);
				criteria.add(Restrictions.eq("printState", PrintInterfaceState.DISASSEMBLED));
				criteria.add(Restrictions.eq("ticketFinsh", Boolean.FALSE));
				criteria.add(Restrictions.gt("createTime", DateUtil.getdecDateOfMinute(new Date(),60*24*10)));//查找最早10天前
				criteria.addOrder(Order.asc("id"));
				criteria.setMaxResults(TicketConstant.QUERY_MAXRESULTS); 
				return criteria.list();
			}
		});
	}

}
