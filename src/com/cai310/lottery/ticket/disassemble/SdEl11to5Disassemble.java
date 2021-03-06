package com.cai310.lottery.ticket.disassemble;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.sdel11to5.SdEl11to5CompoundContent;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.ticket.common.StringOfListUtil;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.lottery.ticket.protocol.DanTuoDataConver;
import com.cai310.utils.JsonUtil;

public class SdEl11to5Disassemble extends AbstractDisassemble {
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
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		SdEl11to5PlayType playType = SdEl11to5PlayType.values()[printData.getBetType()];
	
		for(String content:contents){
			List<String> resultNewConverList = new ArrayList<String>();
			SdEl11to5CompoundContent  compoundContent = JsonUtil.getObject4JsonString(content, SdEl11to5CompoundContent.class);
			if(printData.getBetType()==SdEl11to5PlayType.RandomEight.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomTwo.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomThree.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomFour.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomFive.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomSix.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomSeven.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.RandomSeven.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.ForeTwoGroup.ordinal()
					||printData.getBetType()==SdEl11to5PlayType.ForeThreeGroup.ordinal()){
				List<String> betDanList = StringOfListUtil.format(compoundContent.getBetDanList());
				List<String> betList = StringOfListUtil.format(compoundContent.getBetList());
				DanTuoDataConver bb = new DanTuoDataConver(playType.getLineLimit(), betDanList, betList);
				List<List<String>> resultConverList = bb.getResultList();
				for (List<String> bets : resultConverList) {
					resultNewConverList.add(StringUtils.join(bets, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}
				ticketNew = createTicketDTONew(printData, resultNewConverList);
				if (ticketNew.getUnits() > getMaxUnitsKeno()) {
					resultList.addAll(doSingle(ticketNew));
				} else {
					resultList.add(ticketNew);
				}
			}else{
				if(null==compoundContent.getBetDanList()||compoundContent.getBetDanList().isEmpty()){
					//没胆拖 直接转为复试
					int units = Integer.valueOf(JsonUtil.getStringValueByJsonStr(content, "units")).intValue();
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setContent("[" + content + "]");
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setCreateTime(createTime);
					resultList.addAll(this.doMultiple(ticketNew));
				}else{
				   //设胆转单式
					List<String> betDanList = StringOfListUtil.format(compoundContent.getBetDanList());
					List<String> betList = StringOfListUtil.format(compoundContent.getBetList());
					DanTuoDataConver bb = new DanTuoDataConver(playType.getLineLimit(), betDanList, betList);
					List<List<String>> resultConverList = bb.getResultList();
					for (List<String> bets : resultConverList) {
						resultNewConverList.add(StringUtils.join(bets, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
					}
					ticketNew = createTicketDTONew(printData, resultNewConverList);
					if (ticketNew.getUnits() > getMaxUnitsKeno()) {
						resultList.addAll(doSingle(ticketNew));
					} else {
						resultList.add(ticketNew);
					}
				}
			}
		}
		return resultList;
	}
	private TicketDTO createTicketDTONew(PrintInterfaceDTO printData, List<String> contentList) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		String content = StringUtils.join(contentList,Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
		int units = contentList.size();
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);
		ticketNew.setId(null);
		ticketNew.setPrintinterfaceId(printData.getId());
		ticketNew.setContent(content);
		ticketNew.setUnits(units);
		ticketNew.setMultiple(printData.getMultiple());
		ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
		ticketNew.setCreateTime(new Date());
		ticketNew.setMode(SalesMode.SINGLE);
		return ticketNew;
	}
	
}
