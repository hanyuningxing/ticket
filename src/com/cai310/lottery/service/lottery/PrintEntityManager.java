package com.cai310.lottery.service.lottery;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;

/**
 * 打印相关实体管理接口.
 * 
 */
public interface PrintEntityManager {

	/**
	 * 保存打印接口信息
	 * 
	 * @param entity 打印接口信息
	 * @return 保存后的打印接口信息
	 */
	PrintInterface savePrintInterface(PrintInterface entity);
	
	/**
	 * 获取打印接口表实体
	 * @param id
	 * @return
	 */
	PrintInterface getPrintInterfaceById(Long id);
	
	/**
	 * 获取id号大于index的方案接口表数据
	 * @param index
	 * @return 方案接口表数据集合
	 */
	List<PrintInterface> findPrintInterfaceFromIndex(final Long index);
	
    PrintInterface findPrintInterfaceBy(final String schemeNumber,final Lottery lotteryType);
    ////cyy-c 修改出票拆单读法
	/**
	 * 获取没有拆票的票数据
	 * @return 方案接口表数据集合
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PrintInterface> findUnDisassemblePrintInterface();
	
	/**
	 * 获取已拆单未同步的记录
	 * @return
	 */
	public List<Long> findDisassembledNoSynPrintInterface();
}
