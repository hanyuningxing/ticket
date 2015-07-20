package com.cai310.lottery.service.lottery.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.ticket.PrintInterfaceIndexDao;
import com.cai310.lottery.entity.lottery.ticket.PrintInterfaceIndex;

/**
 * 接口表当前格式转换索引操作类
 * @author jack
 *
 */
@Service("printInterfaceIndexEntityManager")
@Transactional
public class PrintInterfaceIndexEntityManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected PrintInterfaceIndexDao printInterfaceIndexDao;
	
	public PrintInterfaceIndex saveIndex(PrintInterfaceIndex entity){
		return printInterfaceIndexDao.save(entity);
	}
	
	/**
	 * 获取上次接口表格式转换的索引
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long getPrintInterfaceIndex(Long id) {
		PrintInterfaceIndex printIndex = this.printInterfaceIndexDao.get(id);
		if(printIndex!=null && printIndex.getCurrentIndex()!=null){
			return printIndex.getCurrentIndex();
		}
		return 0L;
	}
	
	/**
	 * 获取博涵出票公司信息自增流水号
	 * @param id
	 * @return
	 */
	public synchronized Long getBohanIncremenIndex(Long id) {
		PrintInterfaceIndex printIndex = this.printInterfaceIndexDao.get(id);
		if(printIndex!=null && printIndex.getCurrentIndex()!=null){
			Long index = printIndex.getCurrentIndex()+1>99999999?1L:printIndex.getCurrentIndex();
			printIndex.setCurrentIndex(index+1);
			printInterfaceIndexDao.save(printIndex);
		}
		return printIndex.getCurrentIndex();
	}
	
	public synchronized Long getCaitongIncremenIndex(Long id) {
		PrintInterfaceIndex printIndex = this.printInterfaceIndexDao.get(id);
		if(printIndex!=null && printIndex.getCurrentIndex()!=null){
			Long index = printIndex.getCurrentIndex()+1>99999999?1L:printIndex.getCurrentIndex();
			printIndex.setCurrentIndex(index+1);
			printInterfaceIndexDao.save(printIndex);
		}
		return printIndex.getCurrentIndex();
	}
}
