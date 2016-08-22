package com.cai310.lottery.ticket.protocol.bd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.utils.JsonUtil;

public class DczcBeiDanUtil extends BeiDanUtil {
	private static final String matchSplitStr="$";
    private static final String betSplitStr="^";
    private static final String passSplitStr="~";
//    private static final String areaSplitStr="|";
	public DczcBeiDanUtil(Ticket ticket) {
		super(ticket);
	}
	public DczcBeiDanUtil(){
		super();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		//{"betUnits":9,"firstEndTime":"2015-10-29 00:25:00","items":[{"dan":false,"lineId":55,"value":7},{"dan":false,"lineId":57,"value":7}],"multiple":1,"passModeOrdinal":0,"passTypeOrdinal":1,"playTypeOrdinal":0,"ticketIndex":0}
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
		int itemValue = 0;//场次选择值
//		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
		StringBuffer sb = new StringBuffer();
		sb.append(passType.getTvalue()).append(passSplitStr);
		StringBuffer betSb = new StringBuffer();
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			String lineId = String.valueOf(map.get("lineId"));
			betSb.append(lineId).append(matchSplitStr);
			itemValue = Integer.valueOf(String.valueOf(map.get("value")));
			betSb.append(itemValue).append(betSplitStr);
		}
		betSb = betSb.delete(betSb.length() - betSplitStr.length(), betSb.length());
		sb.append(betSb);
		
		return sb.toString();
	}
	
	@Override
	public Integer getLastMatchNum(Ticket ticket) throws DataException{
		//{"betUnits":9,"firstEndTime":"2015-10-29 00:25:00","items":[{"dan":false,"lineId":55,"value":7},{"dan":false,"lineId":57,"value":7}],"multiple":1,"passModeOrdinal":0,"passTypeOrdinal":1,"playTypeOrdinal":0,"ticketIndex":0}
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
		int itemValue = 0;//场次选择值
		List<Integer> list = new ArrayList<Integer>();
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			list.add(Integer.parseInt(String.valueOf(map.get("lineId"))));
		}
		Collections.reverse(list);
		return list.get(0);
	}

	public static void main(String[] args) throws DataException {
		Ticket ticket = new Ticket();
//胜平负
//		ticket.setContent("{'betUnits':9,'firstEndTime':'2015-10-28 00:55:00','items':[{'dan':false,'lineId':1,'value':7},{'dan':false,'lineId':2,'value':7}],'multiple':1,'passModeOrdinal':0,'passTypeOrdinal':1,'playTypeOrdinal':0,'ticketIndex':0}");
//进球数		
//		ticket.setContent("{'betUnits':1,'firstEndTime':'2015-10-28 00:55:00','items':[{'dan':false,'lineId':2,'value':1},{'dan':false,'lineId':8,'value':128},{'dan':false,'lineId':37,'value':128}],'multiple':1,'passModeOrdinal':0,'passTypeOrdinal':3,'playTypeOrdinal':1,'ticketIndex':0}");
//上下单双过关		
//		ticket.setContent("{'betUnits':1,'firstEndTime':'2015-10-28 00:55:00','items':[{'dan':false,'lineId':1,'value':1},{'dan':false,'lineId':2,'value':2},{'dan':false,'lineId':3,'value':4},{'dan':false,'lineId':4,'value':8}],'multiple':1,'passModeOrdinal':0,'passTypeOrdinal':6,'playTypeOrdinal':2,'ticketIndex':0}");
//比分		
//		ticket.setContent("{'betUnits':1,'firstEndTime':'2015-10-28 01:55:00','items':[{'dan':false,'lineId':7,'value':8},{'dan':false,'lineId':36,'value':65536}],'multiple':1,'passModeOrdinal':0,'passTypeOrdinal':1,'playTypeOrdinal':3,'ticketIndex':0}");
		
		ticket.setContent("{'betUnits':2,'firstEndTime':'2015-10-28 03:25:00','items':[{'dan':false,'lineId':17,'value':32},{'dan':false,'lineId':38,'value':288}],'multiple':1,'passModeOrdinal':0,'passTypeOrdinal':1,'playTypeOrdinal':4,'ticketIndex':0}");
		ticket.setMode(SalesMode.COMPOUND);
		DczcBeiDanUtil db = new DczcBeiDanUtil();
		System.out.println(db.getLotCode(ticket));
		System.out.println("151202".substring(1));
	}
}
