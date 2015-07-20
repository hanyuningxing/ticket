package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.ItemBQQSPF;
import com.cai310.lottery.support.dczc.ItemSXDS;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.ItemZJQS;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.utils.JsonUtil;

public class DczcCpdyjUtil extends CpdyjUtil {
	private static final String matchSplitStr=",";
    private static final String betSplitStr="/";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public DczcCpdyjUtil(Ticket ticket) {
		super(ticket);
	}
	public DczcCpdyjUtil(){
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
		if(PlayType.SPF.equals(playType)){
			playTypeStr = "SPF";
		}else if(PlayType.ZJQS.equals(playType)){
			playTypeStr = "JQS";
		}else if(PlayType.BF.equals(playType)){
			playTypeStr = "CBF";
		}else if(PlayType.BQQSPF.equals(playType)){
			playTypeStr = "BQC";
		}else if(PlayType.SXDS.equals(playType)){
			playTypeStr = "SXP";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(playTypeStr).append(areaSplitStr);
		StringBuffer betSb = new StringBuffer();
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			String lineId = String.valueOf(map.get("lineId"));
			betSb.append(lineId+"=");
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
			case ZJQS:
				for (ItemZJQS type : ItemZJQS.values()) {
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
							betSb.append("胜其他").append(betSplitStr);
						}else if(ItemBF.DRAW_OTHER.equals(type)){
							betSb.append("平其他").append(betSplitStr);
						}else if(ItemBF.LOSE_OTHER.equals(type)){
							betSb.append("负其他").append(betSplitStr);
						}else{
							betSb.append(type.getText()).append(betSplitStr);
						}
					}
				}
				break;
			case BQQSPF:
				for (ItemBQQSPF type : ItemBQQSPF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						betSb.append(type.getValue().substring(0,1)+"-"+type.getValue().substring(1,2)).append(betSplitStr);
					}
				}
				break;
			case SXDS:
				for (ItemSXDS type : ItemSXDS.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						betSb.append(type.getText()).append(betSplitStr);
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
		String text;
		if (passType.getMatchCount() == 1) {
			text = "单关";
		} else {
			text = passType.getMatchCount() + passSplitStr + passType.getUnits();
		}
		sb.append(text);
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
