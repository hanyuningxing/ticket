package com.cai310.lottery.ticket.disassemble;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.ticket.dataconver.PlDataConver;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.JsonUtil;

public class PlDisassemble extends AbstractDisassemble {
	@Override
	protected int getMaxMultiple() {
		return 10;
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
		if ( printData.getBetType() == PlPlayType.P3Group3Kuadu.ordinal()// 组三跨度需要分解为单式组选3
				|| printData.getBetType() == PlPlayType.P3Group6Kuadu.ordinal()// 组六跨度需要分解为单式组选6
				|| printData.getBetType() == PlPlayType.P3DirectKuadu.ordinal()
				||printData.getBetType() == PlPlayType.GroupSum.ordinal()){ // 直选跨度转为直选单式

			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			List<TicketDTO> ticketList = PlDataConver.converData(printData, createTime);
			for (TicketDTO ticket : ticketList) {
				resultList.addAll(this.doSingle(ticket));
			}
			return resultList;
		} else if(printData.getBetType() == PlPlayType.DirectSum.ordinal()){//
			String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
			PlCompoundContent plCompoundContent = null;
			PlCompoundContent lineContent = new PlCompoundContent();
			List<String> directSum = null;	
			TicketDTO ticketNew = null;
			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			for(String content:contents){
				plCompoundContent = JsonUtil.getObject4JsonString(content, PlCompoundContent.class);
				directSum = plCompoundContent.getDirectSumList();
//				List<String> directSumList = new ArrayList<String>();
				Integer unit=0;
				for (String num : directSum){
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 27)
						throw new DataException("方案内容不正确,号码在[00-27]之间.");
					Integer units =  PlConstant.UNITS_DIRECT_SUM[number];
					unit+=units;
				}
//					directSumList.add(num);
					lineContent.setUnits(unit);
					lineContent.setDirectSumList(directSum);
					
					
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setContent(JSONArray.fromObject(lineContent).toString());
					ticketNew.setUnits(unit);
					ticketNew.setSchemeCost(unit*unitsMoney*ticketNew.getMultiple());
					ticketNew.setCreateTime(createTime);
					ticketNew.setMode(SalesMode.COMPOUND);
					ticketNew.setBetType((byte)PlPlayType.DirectSum.ordinal());
					resultList.addAll(this.doMultiple(ticketNew));
			}
			return resultList;
		}else {
			return super.doCompoundCommonNumber(printData, createTime);
		}
	}
	

	
}
