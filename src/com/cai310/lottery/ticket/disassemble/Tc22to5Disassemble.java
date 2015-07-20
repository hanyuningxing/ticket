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
import com.cai310.lottery.support.tc22to5.Tc22to5CompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.ZcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.ticket.common.CombinationNumberUtil;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class Tc22to5Disassemble extends AbstractDisassemble {
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
	/**
	 * @param printData
	 * @return
	 * @throws Exception 
	 */
	@Override
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime) throws Exception{
		//胆码与方案内容分割符
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		CombinationNumberUtil<String> work = null;
		TicketDTO ticketNew = null;
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		List<Tc22to5CompoundContent> tc22to5BetList = new ArrayList<Tc22to5CompoundContent>();
		Boolean breakFlag =false;
		for (String content : contents) {
			Tc22to5CompoundContent tc22to5CompoundContent = JsonUtil.getObject4JsonString(content,Tc22to5CompoundContent.class);
			if(null!=tc22to5CompoundContent.getDanList()||!tc22to5CompoundContent.getDanList().isEmpty()){
				breakFlag =true;
			}
			tc22to5BetList.add(tc22to5CompoundContent);
		}
		if(breakFlag){
			for (Tc22to5CompoundContent tc22to5CompoundContent : tc22to5BetList) {
				work = new CombinationNumberUtil<String>(5,tc22to5CompoundContent.getDanList(),tc22to5CompoundContent.getBetList(), -1,-1);
				List<String> bet = Lists.newArrayList();	
				for (List<String> betList : work.getResultList()) {
					bet.add(StringUtils.join(betList, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}
				ticketNew = createTicketDTONew(printData,createTime,bet);
				resultList.addAll(this.doSingle(ticketNew));
			}
			return resultList;
		}else{
			return super.doCompoundCommonNumber(printData, createTime);

		}
	}
	static int unitsMoney = 2;
	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, Date createTime, List<String> contentList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
			TicketDTO ticketNew = new TicketDTO();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			String content = StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			ticketNew.setContent(content);
			int units = contentList.size();
			ticketNew.setUnits(units);
			ticketNew.setSchemeCost(units*unitsMoney*ticketNew.getMultiple());
			ticketNew.setCreateTime(createTime);
			ticketNew.setMode(SalesMode.SINGLE);
			return ticketNew;
	}
	
}
