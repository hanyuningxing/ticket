package com.cai310.lottery.ticket.protocol.win310.utils;

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

public class JczqWin310Util extends Win310Util {
	private static final String matchSplitStr="/";
    private static final String betSplitStr=",";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public JczqWin310Util(Ticket ticket) {
		super(ticket);
	}
	public JczqWin310Util(){
		super();
	}

	
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
		int itemValue = 0;//场次选择值
		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
		String comRemark= passType.getText();
		String betType= null;
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
			Integer matchDate = Integer.valueOf(matchKey.split("-")[0]);
			String lineId = JclqUtil.getLineId(matchKey);
			String ticket_matchKey = JclqUtil.getDayOfWeekStr(matchDate)+lineId;
			//混合过关
			if(PlayType.MIX.equals(playType)){
				betType="6";
				PlayType itemPlayType = PlayType.valueOfName(String.valueOf(map.get("playType")));
				itemValue = Integer.valueOf(String.valueOf(map.get("value")));
				betCode.append(ticket_matchKey+"[");
				switch (itemPlayType) {
				case SPF:
					for (ItemSPF type : ItemSPF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case RQSPF:
					for (ItemSPF type : ItemSPF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append("让球"+type.getText()).append(betSplitStr);
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
							betCode.append(type.getText()).append(betSplitStr);
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
				betCode.append(ticket_matchKey+"[");
				itemValue = Integer.valueOf(String.valueOf(map.get("value")));
				switch (playType) {
				case SPF:
					betType="0";
					for (ItemSPF type : ItemSPF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case RQSPF:
					betType="7";
					for (ItemSPF type : ItemSPF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append("让球"+type.getText()).append(betSplitStr);
						}
					}
					break;
				case JQS:
					betType="2";
					for (ItemJQS type : ItemJQS.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case BF:
					betType="1";
					for (ItemBF type : ItemBF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
								betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case BQQ:
					betType="3";
					for (ItemBQQ type : ItemBQQ.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
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
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\" schemeNum=\"\"  playType=\""+0+"\"" +
						" comRemark=\""+comRemark+"\" betTime=\""+System.currentTimeMillis()+"\" issueNumber=\"\"" +
						" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betCost=\""+ticket.getSchemeCost()+"\">");
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
					    case RQSPF:
							return "JCSPF";
						case SPF:
							return "JCBRQSPF";
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
