package com.cai310.lottery.ticket.disassemble;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.ticket.common.CombinationNumberUtil;
import com.cai310.lottery.ticket.common.CombinationUtil;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.lottery.ticket.dataconver.Welfare3dDataConver;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class Welfare3DDisassemble extends AbstractDisassemble {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected int getMaxMultiple() {
		return 50;
	}

	@Override
	protected int getMaxUnitsKeno() {
		return 5;
	}

	@Override
	protected int getMaxUnitsCommon() {
		return 5;
	}

	@Override
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime)
			throws Exception {
		if ( printData.getBetType() == Welfare3dPlayType.Group3Kuadu.ordinal()// 福彩3D组三跨度需要分解为单式组选3
				|| printData.getBetType() == Welfare3dPlayType.Group6Kuadu.ordinal()// 福彩3D组六跨度需要分解为单式组选6
				|| printData.getBetType() == Welfare3dPlayType.DirectKuadu.ordinal()
				||printData.getBetType() == Welfare3dPlayType.GroupSum.ordinal()){ // 福彩3D直选跨度转为直选单式
			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			List<TicketDTO> ticketList = Welfare3dDataConver.converData(printData, createTime);
			for (TicketDTO ticket : ticketList) {
				resultList.addAll(this.doSingle(ticket));
			}
			return resultList;
		}else if(printData.getBetType() == Welfare3dPlayType.DirectSum.ordinal()){//
			String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
			Welfare3dCompoundContent welfare3dCompoundContent = null;
			Welfare3dCompoundContent lineContent = new Welfare3dCompoundContent();
			List<String> directSum = null;	
			TicketDTO ticketNew = null;
			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				directSum = welfare3dCompoundContent.getDirectSumList();
				for (String num : directSum){
					List<String> directSumList = new ArrayList<String>();
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 27)
						throw new DataException("方案内容不正确,号码在[00-27]之间.");
					Integer units =  Welfare3dConstant.UNITS_DIRECT_SUM[number];
					directSumList.add(num);
					lineContent.setUnits(units);
					lineContent.setDirectSumList(directSumList);
					
					
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setContent(JSONArray.fromObject(lineContent).toString());
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units*unitsMoney*ticketNew.getMultiple());
					ticketNew.setCreateTime(createTime);
					ticketNew.setMode(SalesMode.COMPOUND);
					ticketNew.setBetType((byte)Welfare3dPlayType.DirectSum.ordinal());
					resultList.addAll(this.doMultiple(ticketNew));
				}
			}
			return resultList;
		}else if(printData.getBetType() == Welfare3dPlayType.Group3.ordinal()){//
			String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
			Welfare3dCompoundContent welfare3dCompoundContent = null;
			List<String> group3 = null;	
			List<String> bet = Lists.newArrayList();	
			TicketDTO ticketNew = null;
			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			CombinationNumberUtil<String> work = null;
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				group3 = welfare3dCompoundContent.getGroup3List();
				work = new CombinationNumberUtil<String>(2, new ArrayList() , group3, -1,-1);
				for (List<String> betList : work.getResultList()) {
					bet.add(betList.get(0)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+betList.get(0)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+betList.get(1));
					bet.add(betList.get(1)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+betList.get(1)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+betList.get(0));
				}
			}
			ticketNew = createTicketDTONew(printData,createTime,bet,Welfare3dPlayType.Group3);
			resultList.addAll(this.doSingle(ticketNew));
			return resultList;
		}else if(printData.getBetType() == Welfare3dPlayType.Group6.ordinal()){//
			String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
			Welfare3dCompoundContent welfare3dCompoundContent = null;
			List<String> group6 = null;	
			List<String> bet = Lists.newArrayList();	
			TicketDTO ticketNew = null;
			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			CombinationNumberUtil<String> work = null;
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				group6 = welfare3dCompoundContent.getGroup6List();
				work = new CombinationNumberUtil<String>(3, new ArrayList() , group6, -1,-1);
				for (List<String> betList : work.getResultList()) {
					bet.add(betList.get(0)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+betList.get(1)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+betList.get(2));
				}
			}
			ticketNew = createTicketDTONew(printData,createTime,bet,Welfare3dPlayType.Group6);
			resultList.addAll(this.doSingle(ticketNew));
			return resultList;
		}else {
			return super.doCompoundCommonNumber(printData, createTime);
		}
	}
	/**
	 * 
	 * @param printData
	 * @param createTime
	 * @param contentList
	 * @param playType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	static int unitsMoney = 2;
	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, Date createTime, List<String> contentList,Welfare3dPlayType playType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
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
		ticketNew.setBetType((byte)playType.ordinal());
		return ticketNew;
	}
	

	
}
