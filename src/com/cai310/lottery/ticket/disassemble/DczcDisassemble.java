package com.cai310.lottery.ticket.disassemble;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.ticket.common.CombinationUtil;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.lottery.ticket.disassemble.dczc.DczcContentConver;
import com.cai310.lottery.ticket.disassemble.dczc.DczcPrintItemObj;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
public class DczcDisassemble extends AbstractDisassemble {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected int getMaxMultiple() {
		return 99;
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
		PlayType playType = PlayType.values()[printData.getBetType()];
		Map<String, Object> map = JsonUtil.getMap4Json(printData.getContent());
		PassMode passMode = PassMode.values()[Integer.valueOf(String.valueOf(map.get("passMode")))];
		Integer danMinHit = Integer.valueOf(String.valueOf(map.get("danMinHit")));
		Integer danMaxHit = Integer.valueOf(String.valueOf(map.get("danMaxHit")));
		Integer mixPtValue = Integer.valueOf(String.valueOf(map.get("passType"))).intValue();
		String[] contents = JsonUtil.getStringArray4Json(String.valueOf(map.get("items")));
		final List<DczcMatchItem> danCorrectList = new ArrayList<DczcMatchItem>();
		final List<DczcMatchItem> unDanCorrectList = new ArrayList<DczcMatchItem>();
		for (String content : contents) {
			map = JsonUtil.getMap4Json(content);
			DczcMatchItem item = new DczcMatchItem();
			item.setLineId(Integer.valueOf(String.valueOf(map.get("lineId"))).intValue()+1);
			item.setDan(Boolean.valueOf(String.valueOf(map.get("isDan"))));
			item.setValue(Integer.valueOf(String.valueOf(map.get("value"))).intValue());
			if (item.isDan())
				danCorrectList.add(item);
			else
				unDanCorrectList.add(item);
		}

		List<PassType> passTypes = PassType.getPassTypes(mixPtValue);
		int matchCount = 0;
		CombinationUtil<DczcMatchItem> work = null;
		List<List<DczcMatchItem>> combList = null;
		String content = null;
		Map<String, Object> contentMap = null;
		TicketDTO ticketNew = null;			
		for (PassType passType : passTypes) {
			matchCount = passType.getMatchCount();
			work = new CombinationUtil<DczcMatchItem>(matchCount, danCorrectList, unDanCorrectList, danMinHit,
					danMaxHit);
			combList = work.getResultList();
			for (List<DczcMatchItem> itemList : combList) {
				//组合新单
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				//内容转换为出票格式
				//计算注数
				int units = 0;
				for(int passMatch:passType.getPassMatchs()){
					units+=UnitsCountUtils.countUnits(passMatch, new ArrayList<DczcMatchItem>(), itemList);
				}
				DczcPrintItemObj dczcPrintItemObj = new DczcPrintItemObj();
				dczcPrintItemObj.setFirstEndTime(DateUtil.dateToStr(printData.getOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
				dczcPrintItemObj.setBetUnits(units);
				dczcPrintItemObj.setItems(itemList);
				dczcPrintItemObj.setMultiple(printData.getMultiple());
				dczcPrintItemObj.setTicketIndex(combList.indexOf(itemList));
				dczcPrintItemObj.setPassTypeOrdinal(passType.ordinal());
				dczcPrintItemObj.setPassModeOrdinal(passMode.ordinal());
				dczcPrintItemObj.setPlayTypeOrdinal(playType.ordinal());
				ticketNew.setTicketIndex(combList.indexOf(itemList));
				ticketNew.setBetType(Byte.valueOf(String.valueOf(playType.ordinal())));
				ticketNew.setContent(JsonUtil.getJsonString4JavaPOJO(dczcPrintItemObj));
				ticketNew.setId(null);
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setPrintinterfaceId(printData.getId());
				ticketNew.setCreateTime(createTime);
				resultList.addAll(this.doMultiple(ticketNew));

			}
		}
		return resultList;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected List<TicketDTO> doSingle(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
			Map<String, Object> contentMap = null;
			Map<String, Object> map = null;
			String[] contents = null;
			Integer mixPtValue = null;
			TicketDTO ticketNew = null;
			String content = null;
			List<TicketDTO> resultList = new ArrayList<TicketDTO>();
			PlayType playType = PlayType.values()[printData.getBetType()];
			map = JsonUtil.getMap4Json(printData.getContent());
			contents = String.valueOf(map.get("content")).split(",");
			mixPtValue = Integer.valueOf(String.valueOf(map.get("passType"))).intValue();
			PassType passType = PassType.getPassTypes(mixPtValue).get(0);
			String[] matchs = JsonUtil.getStringArray4Json(String.valueOf(map.get("matchs")));
			List<String> lineIds = new ArrayList<String>();
			for (String match : matchs) {
				map = JsonUtil.getMap4Json(match);
				lineIds.add(String.valueOf(map.get("lineId")));
			}
			int maxUnits = Lottery.isKeno(printData.getLotteryType()) ? this.getMaxUnitsKeno() : this.getMaxUnitsCommon();
			List<String> contentList = new ArrayList<String>();
			int units = 0;
			for (String line : contents) {
				contentList.add(line);
				units++;
				if (units == maxUnits) {
					content = DczcContentConver.itemsToStrSimple(contentList, lineIds, playType);
					contentMap = new HashMap<String, Object>();
					contentMap.put("content", content);
					contentMap.put("passType", passType.ordinal());
					//组合新单
					
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setCreateTime(createTime);
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setContent(JsonUtil.getJsonString4JavaPOJO(contentMap));
					resultList.addAll(this.doMultiple(ticketNew));
					contentList.clear();// 重置
					units = 0;// 计数复位
				}
			}
			if (units > 0) {
				content = DczcContentConver.itemsToStrSimple(contentList, lineIds, playType);
				contentMap = new HashMap<String, Object>();
				contentMap.put("content", content);
				contentMap.put("passType", passType.ordinal());
				//组合新单
				
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(printData.getId());
				ticketNew.setCreateTime(createTime);
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(JsonUtil.getJsonString4JavaPOJO(contentMap));
				resultList.addAll(this.doMultiple(ticketNew));
			}
			return resultList;
	}
}
