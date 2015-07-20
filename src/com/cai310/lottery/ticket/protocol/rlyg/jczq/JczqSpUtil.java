package com.cai310.lottery.ticket.protocol.rlyg.jczq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsListObj;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public  class JczqSpUtil{
    ////3.250-3.550A4.500
	////201206144051:[1]/201206144052:[1,0]|06150714$1$4
	public static JcMatchOddsList parseResponseSp(String responseMessage,String betValue,JczqPrintItemObj jczqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
		JczqSpItemObj jczqSpItemObj = new JczqSpItemObj();
		jczqSpItemObj.setIndex(ticket.getTicketIndex());
		if(StringUtils.isNotBlank(betValue)){
			if(betValue.indexOf("|")!=-1){
				//3.250-3.550A4.500
				String[] spArr = responseMessage.split("-");
				String[] arr = betValue.split("\\|");
				String  betStr = arr[0];
				String[] betArr = betStr.split("/");
				List<JczqItem> options  = null;
				JczqItem jczqItem = null;
				JczqSpItem jczqSpItem  = null;
				List <JczqSpItem> awardList = Lists.newArrayList();
				int pos = 0;
				for (String str : betArr) {
					//201206144052:[1,0]
					String sp = spArr[pos];
					//3.550A4.500
					pos++;
					String[] match_spArr = sp.split("A");
					
					jczqSpItem = new JczqSpItem();
					String[] matchArr=str.split(":");
					String matchId = matchArr[0];
					jczqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,8)));
					jczqSpItem.setLineId(Integer.valueOf(matchId.substring(9)));
					String temp = matchArr[1].replace("[", "");
					temp = temp.replace("]", "");
					String[] matchSpArr =temp.split(",");
					options  = Lists.newArrayList();
					int pos_sp = 0;
					for (String matchSp : matchSpArr) {
						String matchSpValue = match_spArr[pos_sp];
						pos_sp++;
						jczqItem = new JczqItem();
						jczqItem.setValue(matchSp.trim());
						jczqItem.setAward(Double.valueOf(matchSpValue));
						options.add(jczqItem);
					}
					jczqSpItem.setOptions(options);
					jczqSpItem.setReferenceValue(null);
					awardList.add(jczqSpItem);
				}
				jczqSpItemObj.setAwardList(awardList);
			}
		}
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JczqSpItem jczqSpItem:jczqSpItemObj.getAwardList()){
			String matchKey = jczqSpItem.getIntTime()+"-"+JczqUtil.formatLineId(jczqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JczqItem jczqItem : jczqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
				}else{
					awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
				}
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JczqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
				switch (playType) {
				case SPF:
					for (ItemSPF type : ItemSPF.values()) {
						if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
					}
					break;
				case JQS:
					for (ItemJQS type : ItemJQS.values()) {
						if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
					}
					break;
				case BF:
					for (ItemBF type : ItemBF.values()) {
						if("90".equals(betValue)){
							value = ItemBF.WIN_OTHER.getValue();
						}else if("99".equals(betValue)){
							value = ItemBF.DRAW_OTHER.getValue();
						}else if("09".equals(betValue)){
							value = ItemBF.LOSE_OTHER.getValue();
						}else if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
						
					}
					break;
				case BQQ:
					for (ItemBQQ type : ItemBQQ.values()) {
						if(type.getValue().equals(betValue)){
							value = type.getValue();
						}
					}
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}
		return value;
	}
}
