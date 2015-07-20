package com.cai310.lottery.ticket.protocol.win310.utils;

import java.util.Map;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
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

public class DczcWin310Util extends Win310Util {
	private static final String matchSplitStr="/";
    private static final String betSplitStr=",";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public DczcWin310Util(Ticket ticket) {
		super(ticket);
	}
	public DczcWin310Util(){
		super();
	}
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		String betType=null;
		String content=null;
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			Map<String, Object> map = JsonUtil.getMap4Json(ticket.getContent());
			PassType passType = PassType.values()[Integer.valueOf(String.valueOf(map.get("passType")))];
			betType= String.valueOf(passType.ordinal());
			content= String.valueOf(map.get("content"));
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
			int itemValue = 0;//场次选择值
			PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
			PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
			betType= String.valueOf(passType.ordinal());
			StringBuffer betCode = new StringBuffer();
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				String lineId = String.valueOf(map.get("lineId"));
				betCode.append(lineId+":[");
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
				case ZJQS:
					for (ItemZJQS type : ItemZJQS.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betCode.append(type.getText()).append(betSplitStr);
						}
					}
					break;
				case SXDS:
					for (ItemSXDS type : ItemSXDS.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							if(ItemSXDS.DOWN_EVEN.equals(type)){
								betCode.append("下+双").append(betSplitStr);
							}else if(ItemSXDS.DOWN_ODD.equals(type)){
								betCode.append("下+单").append(betSplitStr);
							}else if(ItemSXDS.UP_EVEN.equals(type)){
								betCode.append("上+双").append(betSplitStr);
							}else if(ItemSXDS.UP_ODD.equals(type)){
								betCode.append("上+单").append(betSplitStr);
							}
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
				case BQQSPF:
					for (ItemBQQSPF type : ItemBQQSPF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							if(ItemBQQSPF.DRAW_DRAW.equals(type)){
								betCode.append("平-平").append(betSplitStr);
							}else if(ItemBQQSPF.DRAW_LOSE.equals(type)){
								betCode.append("平-负").append(betSplitStr);
							}else if(ItemBQQSPF.DRAW_WIN.equals(type)){
								betCode.append("平-负").append(betSplitStr);
							}else if(ItemBQQSPF.LOSE_DRAW.equals(type)){
								betCode.append("负-平").append(betSplitStr);
							}else if(ItemBQQSPF.LOSE_LOSE.equals(type)){
								betCode.append("负-负").append(betSplitStr);
							}else if(ItemBQQSPF.LOSE_WIN.equals(type)){
								betCode.append("负-胜").append(betSplitStr);
							}else if(ItemBQQSPF.WIN_DRAW.equals(type)){
								betCode.append("胜-平").append(betSplitStr);
							}else if(ItemBQQSPF.WIN_LOSE.equals(type)){
								betCode.append("胜-负").append(betSplitStr);
							}else if(ItemBQQSPF.WIN_WIN.equals(type)){
								betCode.append("胜-胜").append(betSplitStr);
							}
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
			betCode = betCode.delete(betCode.length() - matchSplitStr.length(), betCode.length());
			content=betCode.toString();
		}
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\"" + ticket.getId() + "\" betType=\"" + betType + "\" comRemark=\"\"  playType=\"" + 0 + "\" schemeNum=\"" + ticket.getSchemeNumber()
				+ "\"  issueNumber=\"" + ticket.getPeriodNumber() + "\" betUnits=\"" + ticket.getUnits() + "\"   multiple=\"" + ticket.getMultiple() + "\" betCost =\"" + ticket.getSchemeCost()
				+ "\" betTime=\"" + System.currentTimeMillis() + "\">");
		betXML.append("<betContent>" + content + "</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
		
	}
}
