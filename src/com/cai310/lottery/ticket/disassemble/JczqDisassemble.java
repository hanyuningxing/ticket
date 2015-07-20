package com.cai310.lottery.ticket.disassemble;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqSingleContent;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.support.jczq.TicketItemSingle;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class JczqDisassemble extends AbstractDisassemble {
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
		PlayType playType = PlayType.values()[printData.getBetType()];
		
		Map<String, Object> map = JsonUtil.getMap4Json(printData.getContent());
		String ticketContent = String.valueOf(map.get("ticketContent"));
		PassMode passMode = PassMode.values()[Integer.valueOf(String.valueOf(map.get("passModeOrdinal")))];
		SchemeType schemeType = SchemeType.values()[Integer.valueOf(String.valueOf(map.get("schemeTypeOrdinal")))];
		String firstMatchTime = String.valueOf(String.valueOf(map.get("firstMatchTime")));
		List<TicketItem> ticketItemList = JczqUtil.getTicketList(ticketContent);
		Map<String, Object> content = JsonUtil.getMap4Json(String.valueOf(map.get("content")));
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(content.get("items")));
		final List<JczqMatchItem> correctList = new ArrayList<JczqMatchItem>();
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			JczqMatchItem item = new JczqMatchItem();
			item.setMatchKey(String.valueOf(map.get("matchKey")));
			item.setDan(Boolean.valueOf(String.valueOf(map.get("dan"))));
			item.setValue(Integer.valueOf(String.valueOf(map.get("value"))).intValue());
			//混合过关
			if(PlayType.MIX.equals(playType)){
				PlayType itemPlayType = PlayType.valueOfName(String.valueOf(map.get("playType")));
				item.setPlayType(itemPlayType);
			}
			correctList.add(item);
		}
		TicketDTO ticketNew = null;		
		for (TicketItem ticketItem : ticketItemList) {
			    PassType passType = ticketItem.getPassType();
				int units = 0;
				List<JczqMatchItem> selectList = JczqUtil.getSelectByTicketItem(ticketItem,correctList);
				//计算注数
				for(int passMatch:passType.getPassMatchs()){
					units+=UnitsCountUtils.countUnits(passMatch, new ArrayList<JczqMatchItem>(), selectList);
				}
				//组合新单
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				List<String> matchKeyList = Lists.newArrayList();
				for (JczqMatchItem item : selectList) {
					matchKeyList.add(item.getMatchKey());
				}
				JczqPrintItemObj jczqPrintItemObj = new JczqPrintItemObj();
				jczqPrintItemObj.setFirstEndTime(DateUtil.dateToStr(printData.getOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
				////////朗日阳光要求计算第一场比赛的时间
				jczqPrintItemObj.setFirstMatchTime("");
				////////朗日阳光要求计算第一场比赛的时间
				jczqPrintItemObj.setBetUnits(units);
				jczqPrintItemObj.setItems(selectList);
				jczqPrintItemObj.setPassType(passType.ordinal());
				jczqPrintItemObj.setMultiple(ticketItem.getMultiple());
				jczqPrintItemObj.setTicketIndex(ticketItemList.indexOf(ticketItem));
				jczqPrintItemObj.setPassTypeOrdinal(passType.ordinal());
				jczqPrintItemObj.setPassModeOrdinal(passMode.ordinal());
				jczqPrintItemObj.setSchemeTypeOrdinal(schemeType.ordinal());
				jczqPrintItemObj.setPlayTypeOrdinal(playType.ordinal());
				ticketNew.setTicketIndex(ticketItemList.indexOf(ticketItem));
				ticketNew.setBetType(Byte.valueOf(String.valueOf(playType.ordinal())));
				ticketNew.setContent(JsonUtil.getJsonString4JavaPOJO(jczqPrintItemObj));
				ticketNew.setMultiple(ticketItem.getMultiple());
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketItem.getMultiple());
				ticketNew.setPrintinterfaceId(printData.getId());
				ticketNew.setCreateTime(createTime);
				resultList.add(ticketNew);///因为竞彩已经拆好了票
		}
		return resultList;
	}
	
	
	@Override
	protected List<TicketDTO> doSingle(PrintInterfaceDTO printData, Date createTime)throws Exception{
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		PlayType playType = PlayType.values()[printData.getBetType()];
		Map<String, Object> map = JsonUtil.getMap4Json(printData.getContent());
		PassMode passMode = PassMode.values()[Integer.valueOf(String.valueOf(map.get("passModeOrdinal")))];
		SchemeType schemeType = SchemeType.values()[Integer.valueOf(String.valueOf(map.get("schemeTypeOrdinal")))];
		String firstMatchTime = String.valueOf(String.valueOf(map.get("firstMatchTime")));

		JczqSingleContent singleContent = JsonUtil.getObject4JsonString(String.valueOf(map.get("content")), JczqSingleContent.class);
		String[] contentArr = singleContent.converContent2Arr();
		List<String> matchKeys = singleContent.getMatchkeys();
		List<String> playTypes = singleContent.getPlayTypes();
		boolean optimize = singleContent.isOptimize();//优化方案
		
		String ticketContent = String.valueOf(map.get("ticketContent"));//转化后的出票格式
		List<TicketItemSingle> ticketItemList = JczqUtil.getSingleTicketList(ticketContent);
		
		TicketDTO ticketNew = null;
		for(TicketItemSingle ticketItem : ticketItemList){
			PassType passType = ticketItem.getPassType();
			List<JczqMatchItem> selectList = Lists.newArrayList();
			int contentIndex = ticketItem.getIndex();
			String content = contentArr[contentIndex];
			String[] ordinalArr = content.split(",");
			
			//优化方案(playType与投注内容对应)
			String[] playTypeArr = null;
			if(optimize){
				playTypeArr = playTypes.get(contentIndex).split(",");
			}
			
			int units = 0;
			int selectedCount = 0;
			for(int i=0;i<ordinalArr.length;i++){
				String ordinalStr = ordinalArr[i];
				if("#".equals(ordinalStr)){
					continue;
				}else{
					selectedCount++;
				}
				int ordinal = Integer.parseInt(ordinalStr);
				JczqMatchItem matchItem = new JczqMatchItem();
				if(optimize){//优化方案(playType与投注内容对应)
					matchItem.setPlayType(PlayType.valueOfName(playTypeArr[i]));
				}else if(PlayType.MIX.equals(playType)){
					matchItem.setPlayType(PlayType.valueOfName(playTypes.get(i)));
				}else{
					matchItem.setPlayType(playType);
				}
				matchItem.setValue(1 << ordinal);
				matchItem.setMatchKey(matchKeys.get(i));
				selectList.add(matchItem);
			}
			//计算注数
			for(int passMatch:passType.getPassMatchs()){
				units+=UnitsCountUtils.countUnits(selectedCount,passMatch);
			}
			
			//组合新单
			ticketNew = new TicketDTO();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			JczqPrintItemObj jczqPrintItemObj = new JczqPrintItemObj();
			jczqPrintItemObj.setFirstEndTime(DateUtil.dateToStr(printData.getOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
			////////朗日阳光要求计算第一场比赛的时间
			jczqPrintItemObj.setFirstMatchTime("");
			////////朗日阳光要求计算第一场比赛的时间
			jczqPrintItemObj.setBetUnits(units);
			jczqPrintItemObj.setItems(selectList);
			jczqPrintItemObj.setPassType(passType.ordinal());
			jczqPrintItemObj.setMultiple(ticketItem.getMultiple());
			jczqPrintItemObj.setTicketIndex(ticketItemList.indexOf(ticketItem));
			jczqPrintItemObj.setPassTypeOrdinal(passType.ordinal());
			jczqPrintItemObj.setPassModeOrdinal(passMode.ordinal());
			jczqPrintItemObj.setSchemeTypeOrdinal(schemeType.ordinal());
			jczqPrintItemObj.setPlayTypeOrdinal(playType.ordinal());
			ticketNew.setTicketIndex(ticketItemList.indexOf(ticketItem));
			ticketNew.setBetType(Byte.valueOf(String.valueOf(playType.ordinal())));
			ticketNew.setContent(JsonUtil.getJsonString4JavaPOJO(jczqPrintItemObj));
			ticketNew.setMultiple(ticketItem.getMultiple());
			ticketNew.setUnits(units);
			ticketNew.setSchemeCost(units * unitsMoney * ticketItem.getMultiple());
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setCreateTime(createTime);
			resultList.add(ticketNew);///因为竞彩已经拆好了票
		}
		
		return resultList;
	}
	
}
