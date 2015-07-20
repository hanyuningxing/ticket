package com.cai310.lottery.ticket.protocol.rlyg;

import java.util.Date;
import java.util.Map;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class JczqRlygUtil extends RlygUtil {
	private static final String matchSplitStr="/";
    private static final String betSplitStr=",";
	public JczqRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public JczqRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
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
			
			
			betSb.append(arr[0]+JczqUtil.getMatchDayOfWeek(Integer.valueOf(arr[0]))+arr[1]+":[");
			itemValue = Integer.valueOf(String.valueOf(map.get("value")));
			switch (playType) {
			case SPF:
				for (ItemSPF type : ItemSPF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						betSb.append(type.getValue()).append(betSplitStr);
					}
				}
				break;
			case JQS:
				for (ItemJQS type : ItemJQS.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						betSb.append(type.getValue()).append(betSplitStr);
					}
				}
				break;
			case BF:
				for (ItemBF type : ItemBF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						if(ItemBF.WIN_OTHER.equals(type)){
							betSb.append("90").append(betSplitStr);
						}else if(ItemBF.DRAW_OTHER.equals(type)){
							betSb.append("99").append(betSplitStr);
						}else if(ItemBF.LOSE_OTHER.equals(type)){
							betSb.append("09").append(betSplitStr);
						}else{
							betSb.append(type.getValue()).append(betSplitStr);
						}
					}
				}
				break;
			case BQQ:
				for (ItemBQQ type : ItemBQQ.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						betSb.append(type.getValue()).append(betSplitStr);
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
		return Lottery.JCZQ;
	}

}
