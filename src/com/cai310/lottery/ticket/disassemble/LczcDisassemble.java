package com.cai310.lottery.ticket.disassemble;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.ticket.dataconver.LczcDataConver;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;

public class LczcDisassemble extends AbstractDisassemble {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected int getMaxMultiple() {
		return 99;
	}

	@Override
	protected int getMaxUnitsKeno() {
		return 3;
	}

	@Override
	protected int getMaxUnitsCommon() {
		return 3;
	}

	@Override
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime)
			throws Exception {
		LczcDataConver lczcConver = new LczcDataConver();
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		List<TicketDTO> ticketList = lczcConver.converData(printData, createTime);
		for(TicketDTO ticket:ticketList){
			resultList.addAll(this.doMultiple(ticket));
		}	
		return resultList;
	}
}
