package com.cai310.lottery.ticket.disassemble;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.JsonUtil;

public class SevenDisassemble extends AbstractDisassemble {
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
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData,
			Date createTime) throws Exception {
		return super.doCompoundCommonNumber(printData, createTime);
	}
	

	
}
