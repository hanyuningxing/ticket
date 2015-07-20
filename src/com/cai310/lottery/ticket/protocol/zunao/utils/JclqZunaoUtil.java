package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Sets;

public class JclqZunaoUtil extends ZunaoUtil {
	private static final String matchSplitStr="/";
    private static final String betSplitStr=",";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public JclqZunaoUtil(Ticket ticket) {
		super(ticket);
	}
	public JclqZunaoUtil(){
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
                if(PlayType.SFC.equals(itemPlayType)){
    				betCode.append("FC").append("@").append(ticket_matchKey+":[");
                }else{
                	betCode.append(itemPlayType.name()).append("@").append(ticket_matchKey+":[");
                }
					//混合过关
				switch (itemPlayType) {
				case SF:
					for (ItemSF type : ItemSF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							if(type.equals(ItemSF.WIN)){
								betCode.append("主胜").append(betSplitStr);
							}else if(type.equals(ItemSF.LOSE)){
								betCode.append("客胜").append(betSplitStr);
							}else{
								throw new RuntimeException("玩法不正确.");
							}
						}
					}
					break;
				case RFSF:
					for (ItemRFSF type : ItemRFSF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							if(type.equals(ItemRFSF.SF_WIN)){
								betCode.append("主胜").append(betSplitStr);
							}else if(type.equals(ItemRFSF.SF_LOSE)){
								betCode.append("客胜").append(betSplitStr);
							}else{
								throw new RuntimeException("玩法不正确.");
							}
						}
					}
					break;
				case SFC:
					for (ItemSFC type : ItemSFC.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							String value = type.name().replace("HOME","胜");
							value = value.replace("GUEST","负");
							value = value.replace("26", "26+");
							value = value.replace("_", "-");
							betCode.append(value).append(betSplitStr);
						}
					}
					break;
				case DXF:
					for (ItemDXF type : ItemDXF.values()) {
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
			
			
					betCode.append(ticket_matchKey+":[");
					itemValue = Integer.valueOf(String.valueOf(map.get("value")));
					switch (playType) {
					case SF:
						for (ItemSF type : ItemSF.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
								if(type.equals(ItemSF.WIN)){
									betCode.append("主胜").append(betSplitStr);
								}else if(type.equals(ItemSF.LOSE)){
									betCode.append("客胜").append(betSplitStr);
								}else{
									throw new RuntimeException("玩法不正确.");
								}
							}
						}
						break;
					case RFSF:
						for (ItemRFSF type : ItemRFSF.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
								if(type.equals(ItemRFSF.SF_WIN)){
									betCode.append("主胜").append(betSplitStr);
								}else if(type.equals(ItemRFSF.SF_LOSE)){
									betCode.append("客胜").append(betSplitStr);
								}else{
									throw new RuntimeException("玩法不正确.");
								}
							}
						}
						break;
					case SFC:
						for (ItemSFC type : ItemSFC.values()) {
							int v = 1 << type.ordinal();
							if ((itemValue & v) > 0) {
								String value = type.name().replace("HOME","胜");
								value = value.replace("GUEST","负");
								value = value.replace("26", "26+");
								value = value.replace("_", "-");
								betCode.append(value).append(betSplitStr);
							}
						}
						break;
					case DXF:
						for (ItemDXF type : ItemDXF.values()) {
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
			    case SF:
					return "JCSF";
				case RFSF:
					return "JCRFSF";
				case SFC:
					return "JCFC";
				case DXF:
					return "JCDXF";
						default:
							throw new RuntimeException("玩法不正确.");
				}
			}else{
				return "JCLQFH";
			}
		}
}
