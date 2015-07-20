package com.cai310.lottery.ticket.protocol.rlyg.jclq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsListObj;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public  class JclqSpUtil{
    ////3.250-3.550A4.500
	////201206144051:[1]/201206144052:[1,0]|06150714$1$4
	public static JcMatchOddsList parseResponseSp(String responseMessage,String betValue,JclqPrintItemObj jclqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jclqPrintItemObj.getPlayTypeOrdinal()];
		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		jclqSpItemObj.setIndex(ticket.getTicketIndex());
		if(StringUtils.isNotBlank(betValue)){
			if(betValue.indexOf("|")!=-1){
				//3.250-3.550A4.500
				//1.810-1.780@-8.5B-1.5
				String[] spArr;
				String[] referenceValueArr;
				if(responseMessage.indexOf("@")!=-1){
					String[] temp=responseMessage.split("@");
					spArr = temp[0].split("-");
					referenceValueArr = temp[1].split("B");
				}else{
					spArr = responseMessage.split("-");
					referenceValueArr = null;
				}
				String[] arr = betValue.split("\\|");
				String  betStr = arr[0];
				String[] betArr = betStr.split("/");
				List<JclqItem> options  = null;
				JclqItem jclqItem = null;
				JclqSpItem jclqSpItem  = null;
				List <JclqSpItem> awardList = Lists.newArrayList();
				int pos = 0;
				for (String str : betArr) {
					//201206144052:[1,0]
					String sp = spArr[pos];
					//3.550A4.500
					String[] match_spArr = sp.split("A");
					
					jclqSpItem = new JclqSpItem();
					String[] matchArr=str.split(":");
					String matchId = matchArr[0];
					jclqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,8)));
					jclqSpItem.setLineId(Integer.valueOf(matchId.substring(9)));
					
					if(null!=referenceValueArr){
						String referenceString = referenceValueArr[pos];
						if(StringUtils.isNotBlank(referenceString)){
							jclqSpItem.setReferenceValue(Double.valueOf(referenceString));
						}else{
							jclqSpItem.setReferenceValue(Double.valueOf(0));
						}
					}else{
						jclqSpItem.setReferenceValue(Double.valueOf(0));
					}
					String temp = matchArr[1].replace("[", "");
					temp = temp.replace("]", "");
					String[] matchSpArr =temp.split(",");
					options  = Lists.newArrayList();
					int pos_sp = 0;
					for (String matchSp : matchSpArr) {
						String matchSpValue = match_spArr[pos_sp];
						pos_sp++;
						jclqItem = new JclqItem();
						jclqItem.setValue(matchSp.trim());
						jclqItem.setAward(Double.valueOf(matchSpValue));
						options.add(jclqItem);
					}
					jclqSpItem.setOptions(options);
					awardList.add(jclqSpItem);
					pos++;
				}
				jclqSpItemObj.setAwardList(awardList);
			}
		}
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
			String matchKey = jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}else{
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}
			}
			if(null==awardMap||awardMap.isEmpty()){
				awardMap = Maps.newHashMap();
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
			}else{
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
		
				switch (playType) {
				case SF:
					for (ItemSF type : ItemSF.values()) {
						if(betValue.equals("1")){
							value = ItemSF.WIN.getValue();
						}else if(betValue.equals("2")){
							value = ItemSF.LOSE.getValue();
						}
					}
					break;
				case RFSF:
					for (ItemRFSF type : ItemRFSF.values()) {
						if(betValue.equals("1")){
							value = ItemRFSF.SF_WIN.getValue();
						}else if(betValue.equals("2")){
							value = ItemRFSF.SF_LOSE.getValue();
						}
					}
					break;
				case SFC:
					for (ItemSFC type : ItemSFC.values()) {
						if(betValue.equals(type.getCpdyjValue())){
							value = type.getValue();
						}
					}
					break;
				case DXF:
					for (ItemDXF type : ItemDXF.values()) {
						if(betValue.equals("1")){
							value = ItemDXF.LARGE.getValue();
						}else if(betValue.equals("2")){
							value = ItemDXF.LITTLE.getValue();
						}
					}
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}
				return value;
	}
}
