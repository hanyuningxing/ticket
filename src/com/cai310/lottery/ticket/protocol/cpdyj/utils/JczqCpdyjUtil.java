package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.utils.JsonUtil;

public class JczqCpdyjUtil extends CpdyjUtil {
	private static final String matchSplitStr=",";
    private static final String betSplitStr="/";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public JczqCpdyjUtil(Ticket ticket) {
		super(ticket);
	}
	public JczqCpdyjUtil(){
		super();
	}
	@Override
	public String getLotIssue(Ticket ticket) {
		return "0";
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
		int itemValue = 0;//场次选择值
		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
		String playTypeStr = playType.name();
		if(PlayType.BF.equals(playType)){
			playTypeStr = "CBF";
		}else if(PlayType.BQQ.equals(playType)){
			playTypeStr = "BQC";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(playTypeStr).append(areaSplitStr);
		StringBuffer betSb = new StringBuffer();
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			String matchKey = String.valueOf(map.get("matchKey"));//格式：20120306-305 要改为120306305
			matchKey = matchKey.replaceAll("-", "");
			matchKey = matchKey.substring(2,matchKey.length());
			betSb.append(matchKey+"=");
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
							betSb.append("9:0").append(betSplitStr);
						}else if(ItemBF.DRAW_OTHER.equals(type)){
							betSb.append("9:9").append(betSplitStr);
						}else if(ItemBF.LOSE_OTHER.equals(type)){
							betSb.append("0:9").append(betSplitStr);
						}else{
							betSb.append(type.getText()).append(betSplitStr);
						}
					}
				}
				break;
			case BQQ:
				for (ItemBQQ type : ItemBQQ.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						betSb.append(type.getValue().substring(0,1)+"-"+type.getValue().substring(1,2)).append(betSplitStr);
					}
				}
				break;
			default:
				throw new RuntimeException("玩法不正确.");
			}
			betSb = betSb.delete(betSb.length() - betSplitStr.length(), betSb.length());
			betSb.append(matchSplitStr);
		}
		betSb = betSb.delete(betSb.length() - matchSplitStr.length(), betSb.length());
		sb.append(betSb).append(areaSplitStr);
		sb.append(passType.getMatchCount()+passSplitStr+passType.getUnits());
		return sb.toString();
	}

	@Override
	public String getAttach(Ticket ticket) {
		return "";
	}

	@Override
	public String getOneMoney(Ticket ticket) {
		return "2";
	}

}
