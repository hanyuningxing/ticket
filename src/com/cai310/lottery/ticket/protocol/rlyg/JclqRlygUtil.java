package com.cai310.lottery.ticket.protocol.rlyg;

import java.util.Date;
import java.util.Map;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class JclqRlygUtil extends RlygUtil {
	private static final String matchSplitStr="/";
    private static final String betSplitStr=",";
	public JclqRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public JclqRlygUtil(){
		super();
	}
	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
		int itemValue = 0;//场次选择值
		Map<String, String> map = Maps.newHashMap();
		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
		
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
		
		
		Date firstMatchTime = DateUtil.strToDate(String.valueOf(getTicketContentMap(ticket).get("firstMatchTime")),"yyyy-MM-dd HH:mm:ss");
		
		StringBuffer sb = new StringBuffer();
		StringBuffer betSb = new StringBuffer();
		
		Long issue = 0L;
		////比赛内容开
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			String matchKey = String.valueOf(map.get("matchKey"));//格式：20120306-305 要改为20120306305
			String[] arr =  matchKey.split("-");
			String matchDate = arr[0];
			Integer week = JclqUtil.getMatchDayOfWeek(Integer.valueOf(arr[0]));
			String lineId = arr[1];
			Long ticket_matchKey = Long.valueOf(matchDate+week+lineId);
			if(ticket_matchKey>issue){
				issue = ticket_matchKey;
			}
			
			betSb.append(arr[0]+JclqUtil.getMatchDayOfWeek(Integer.valueOf(arr[0]))+arr[1]+":[");
			itemValue = Integer.valueOf(String.valueOf(map.get("value")));
			switch (playType) {
			case SF:
				for (ItemSF type : ItemSF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						if(type.equals(ItemSF.WIN)){
							betSb.append("1").append(betSplitStr);
						}else if(type.equals(ItemSF.LOSE)){
							betSb.append("2").append(betSplitStr);
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
							betSb.append("1").append(betSplitStr);
						}else if(type.equals(ItemRFSF.SF_LOSE)){
							betSb.append("2").append(betSplitStr);
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
						//暂用彩票大赢家的格式
						betSb.append(type.getCpdyjValue()).append(betSplitStr);
					}
				}
				break;
			case DXF:
				for (ItemDXF type : ItemDXF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						if(type.equals(ItemDXF.LARGE)){
							betSb.append("1").append(betSplitStr);
						}else if(type.equals(ItemDXF.LITTLE)){
							betSb.append("2").append(betSplitStr);
						}else{
							throw new RuntimeException("玩法不正确.");
						}
					}
				}
				break;
			default:
				throw new RuntimeException("玩法不正确.");
			}
			betSb = betSb.delete(betSb.length() - betSplitStr.length(), betSb.length());
			betSb.append("]"+matchSplitStr);
		}
		betSb = betSb.delete(betSb.length() - matchSplitStr.length(), betSb.length());
		////比赛内容完
		if(passType.P1.equals(passType)){
			sb.append("<ticket seq=\""+ticket.getId()+"\">01-"+getPassTypeStr(passType.ordinal()+1)+"-"+betSb.toString()+"|"+DateUtil.dateToStr(firstMatchTime,"MMddHHmm")+"-"+ticket.getMultiple()+"-"+ticket.getSchemeCost()+"</ticket>");
		}else{
			sb.append("<ticket seq=\""+ticket.getId()+"\">02-"+getPassTypeStr(passType.ordinal()+1)+"-"+betSb.toString()+"|"+DateUtil.dateToStr(firstMatchTime,"MMddHHmm")+"-"+ticket.getMultiple()+"-"+ticket.getSchemeCost()+"</ticket>");
		}
		map.put("betValue", sb.toString());
		map.put("issue", ""+issue);
		return map;
	}
	 public String getPassTypeStr(Integer pos){
	    	if(pos<10){
	    		return "0"+pos;
	    	}else{
	    		return ""+pos;
	    	}
	    }
	@Override
	public Lottery getLottery() {
		return Lottery.JCLQ;
	}

}
