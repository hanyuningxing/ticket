package com.cai310.lottery.ticket.disassemble;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.lottery.ticket.dataconver.SSCDataConver;
import com.cai310.utils.JsonUtil;

public class SscDisassemble extends AbstractDisassemble {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected int getMaxMultiple() {
		return 50;
	}

	@Override
	protected int getMaxUnitsKeno() {
		return 1;
	}

	@Override
	protected int getMaxUnitsCommon() {
		return 1;
	}

	@Override
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime)
			throws Exception {
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		if (printData.getBetType() == SscPlayType.GroupThreeSum.ordinal()
				|| printData.getBetType() == SscPlayType.BigSmallDoubleSingle.ordinal()
				|| printData.getBetType() == SscPlayType.DirectTwoSum.ordinal()
				|| printData.getBetType() == SscPlayType.DirectThreeSum.ordinal()
				|| printData.getBetType() == SscPlayType.DirectOne.ordinal()
				|| printData.getBetType() == SscPlayType.AllFive.ordinal()
				|| printData.getBetType() == SscPlayType.ThreeGroup3.ordinal()
				|| printData.getBetType() == SscPlayType.ThreeGroup6.ordinal()
				|| printData.getBetType() == SscPlayType.GroupTwo.ordinal()) {
			for (TicketDTO ticket : SSCDataConver.converData(printData, createTime)) {
				if (ticket.getUnits() > getMaxUnitsKeno()) {
					resultList.addAll(doSingle(ticket));
				} else {
					resultList.addAll(this.doMultiple(ticket));
				}
			}
		}else if (printData.getBetType() == SscPlayType.GroupTwoSum.ordinal()) {
			for (TicketDTO ticket : SSCDataConver.converData(printData, createTime)) {
					resultList.addAll(this.doMultiple(ticket));
			}
		} else {
			  resultList.addAll(this.doCompoundCommonNumber(printData, createTime));
		}
		return resultList;
	}
	
	
	
}
