package com.cai310.lottery.ticket.protocol.localnew.utils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.ItemBQQSPF;
import com.cai310.lottery.support.dczc.ItemSXDS;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.ItemZJQS;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.utils.JsonUtil;

public  class DCZCCPUtil extends CPUtil{
	/**
	 *  
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	@Override
	public String getCpLotteryId(Lottery lottery,Byte betType){
		return "30011";
	}
	@Override
	public String getCpBetType(Ticket ticket){
		Byte betType = ticket.getBetType();
		PlayType playType = PlayType.values()[betType];
		if(PlayType.SPF.equals(playType)){
			return "0";
		}else if(PlayType.ZJQS.equals(playType)){
			return "1";
		}else if(PlayType.SXDS.equals(playType)){
			return "2";
		}else if(PlayType.BF.equals(playType)){
			return "3";
		}else if(PlayType.BQQSPF.equals(playType)){
			return "4";
		}
		return null;
	}
	@Override
	public String getCpPlayType(Ticket ticket){
		if(null==ticket.getMode())return null;
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			return "1";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			return "0";
		}
		return null;
	}
	@Override
	public String getBetContent(Ticket ticket){
		Map<String, Object> map = JsonUtil.getMap4Json(ticket.getContent());
		return String.valueOf(map.get("content"));
	}
	@Override
	public String putMatchInfo(Ticket ticket){
		Map<String, Object> map = JsonUtil.getMap4Json(ticket.getContent());
		return String.valueOf(map.get("matchInfo"));
	}
	@Override
	public String getSpecialFlag(Ticket ticket) {
		Byte betType = ticket.getBetType();
		PlayType playType = PlayType.values()[betType];
		Map<String, Object> map = JsonUtil.getMap4Json(ticket.getContent());
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(map.get("passType")))];
		return passType.ordinal()+"";
	}
	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return ticket.getPeriodNumber();
	}
	
}
