package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Sets;

public class JczqZunaoUtil extends ZunaoUtil {
	private static final String matchSplitStr="/";
    private static final String betSplitStr=",";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public JczqZunaoUtil(Ticket ticket) {
		super(ticket);
	}
	public JczqZunaoUtil(){
		super();
	}

	
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
		int itemValue = 0;//场次选择值
		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
		String betType = passType.name();
		if(PassType.P1.equals(passType)){
			betType = "P1_1";
		}
		StringBuffer betCode = new StringBuffer();
		if(PlayType.MIX.equals(playType)){
			Set<PlayType> playTypeSet = Sets.newHashSet(); 
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				//混合过关
				if(PlayType.MIX.equals(playType)){
					playTypeSet.add( PlayType.valueOfName(String.valueOf(map.get("playType"))));
				}
			}
			if(playTypeSet.size()==1){
				for (PlayType temp : playTypeSet) {
					playType = temp;
				}
			}
		}
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			String matchKey = String.valueOf(map.get("matchKey"));//格式：20120306-305 要改为1-305
			Integer week = JclqUtil.getMatchWeek(matchKey);
			String lineId = JclqUtil.getLineId(matchKey);
			String ticket_matchKey = week+"-"+lineId;
			//混合过关
			if(PlayType.MIX.equals(playType)){
				PlayType itemPlayType = PlayType.valueOfName(String.valueOf(map.get("playType")));
				itemValue = Integer.valueOf(String.valueOf(map.get("value")));
					//混合过关
				switch (itemPlayType) {
				case SPF:
						betCode.append("SPF@").append(ticket_matchKey+":[");
						for (ItemSPF type : ItemSPF.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
								betCode.append(type.getText()).append(betSplitStr);
							}
						}
						break;
					case JQS:
						betCode.append("JQS@").append(ticket_matchKey+":[");
						for (ItemJQS type : ItemJQS.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
								betCode.append(type.getText()).append(betSplitStr);
							}
						}
						break;
					case BF:
						betCode.append("BF@").append(ticket_matchKey+":[");
						for (ItemBF type : ItemBF.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
									betCode.append(type.getText()).append(betSplitStr);
							}
						}
						break;
					case BQQ:
						betCode.append("BQC@").append(ticket_matchKey+":[");
						for (ItemBQQ type : ItemBQQ.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
								betCode.append(type.getText().substring(0,1)+"-"+type.getText().substring(1,2)).append(betSplitStr);
							}
						}
						break;
					default:
						throw new RuntimeException("玩法不正确.");
				}
				betCode = betCode.delete(betCode.length() - betSplitStr.length(), betCode.length());
				betCode.append("]");
				betCode.append(matchSplitStr);
                 
			}else{
				betCode.append(ticket_matchKey+":[");
				itemValue = Integer.valueOf(String.valueOf(map.get("value")));
				switch (playType) {
				case SPF:
					for (ItemSPF type : ItemSPF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case JQS:
					for (ItemJQS type : ItemJQS.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case BF:
					for (ItemBF type : ItemBF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
								betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case BQQ:
					for (ItemBQQ type : ItemBQQ.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText().substring(0,1)+"-"+type.getText().substring(1,2)).append(betSplitStr);
						}
					}
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}
				betCode = betCode.delete(betCode.length() - betSplitStr.length(), betCode.length());
				betCode.append("]");
				betCode.append(matchSplitStr);
			}
		}
		betCode = betCode.delete(betCode.length() - matchSplitStr.length(), betCode.length());
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\" issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney=\""+ticket.getSchemeCost()+"\" isAppend=\"0\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}
	@Override
	public String getMixPlayString(Ticket ticket) {
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
			Set<PlayType> playTypeSet = Sets.newHashSet(); 
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				//混合过关
				playTypeSet.add( PlayType.valueOfName(String.valueOf(map.get("playType"))));
			}
			if(playTypeSet.size()==1){
				PlayType playType = null;
				for (PlayType temp : playTypeSet) {
					playType = temp;
				}
			    switch (playType) {
						case SPF:
							return "JCSPF";
						case JQS:
							return "JCJQS";
						case BF:
							return "JCBF";
						case BQQ:
							return "JCBQC";
						default:
							throw new RuntimeException("玩法不正确.");
				}
			}else{
				return "JCZQFH";
			}
		}
}
