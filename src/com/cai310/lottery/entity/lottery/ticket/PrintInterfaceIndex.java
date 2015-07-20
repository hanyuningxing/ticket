package com.cai310.lottery.entity.lottery.ticket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.entity.IdEntity;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PRINT_INTERFACE_INDEX")
@Entity
public class PrintInterfaceIndex extends IdEntity{

	private static final long serialVersionUID = -8503465099310475303L;
	
	public PrintInterfaceIndex(){}
	
	public PrintInterfaceIndex(Long id){
		this.setId(id);
	}

	/** 当前接口表操作索引*/
	private Long currentIndex;

	/**
	 * @return the currentIndex
	 */
	@Column
	public Long getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * @param currentIndex the currentIndex to set
	 */
	public void setCurrentIndex(Long currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
}
