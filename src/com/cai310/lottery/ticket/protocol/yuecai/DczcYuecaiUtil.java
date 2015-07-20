package com.cai310.lottery.ticket.protocol.yuecai;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

public class DczcYuecaiUtil extends YuecaiUtil {
	private static final String matchSplitStr="/";
	private static final String splitStr="~";
    private static final String betSplitStr=",";
    private static final String areaSplitStr="$";
	private static final NumberFormat NO = new DecimalFormat("000000000000");
	public DczcYuecaiUtil(Ticket ticket) {
		super(ticket);
	}
	public DczcYuecaiUtil(){
		super();
	}
	@Override
	public String BetContent(Ticket ticket) throws DataException{
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			///{"content":"13:[上+双]/19:[上+单]/23:[下+双]/39:[下+双]/43:[上+双]/9:[上+单];13:[上+双]/19:[上+单]/23:[下+双]/39:[下+双]/43:[下+双]/9:[上+单];13:[上+双]/19:[上+双]/23:[上+单]/39:[下+双]/43:[上+单]/9:[下+双];13:[上+双]/19:[上+双]/23:[上+单]/39:[下+双]/43:[下+双]/9:[上+单];13:[上+双]/19:[上+双]/23:[下+双]/39:[下+双]/43:[上+单]/9:[上+单]","passType":15}
			map = JsonUtil.getMap4Json(ticket.getContent());
			String content = String.valueOf(map.get("content"));
			int itemValue = 0;//场次选择值
			PlayType playType = PlayType.values()[ticket.getBetType()];
			PassType passType = PassType.values()[Integer.valueOf(String.valueOf(map.get("passType")))];
			String betType = passType.name();
			if(PassType.P1.equals(passType)){
				betType = "P1_1";
			}
			StringBuffer betXML = new StringBuffer();
			betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\" issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney=\""+ticket.getSchemeCost()+"\" isAppend=\"0\">");
			betXML.append("<betContent>"+content+"</betContent>");
			betXML.append("</ticket>");
			return betXML.toString();
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(getTicketContentMap(ticket).get("items")));
			int itemValue = 0;//场次选择值
			PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
			PassType passType = PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
			
			StringBuffer betCode = new StringBuffer();
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				String lineId = String.valueOf(map.get("lineId"));
				betCode.append(Integer.valueOf(lineId)).append(splitStr).append("[");
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
							betCode.append(type.getText().substring(0,1)+"-"+type.getText().substring(1,2)).append(betSplitStr);
						}
					}
					break;
				case SXDS:
					for (ItemSXDS type : ItemSXDS.values()) {
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
				betCode.append(splitStr).append("0");
				betCode.append(matchSplitStr);
			}
			betCode = betCode.delete(betCode.length() - matchSplitStr.length(), betCode.length());
			betCode.append(areaSplitStr).append(passType.getText()).append(splitStr)
							.append(ticket.getMultiple()).append(splitStr).append(ticket.getUnits()*ticket.getMultiple()).append("@").append(ticket.getId());
			
			return betCode.toString();
		}else{
			throw new DataException("拆票单复式错误");
		}
		
	}
	@Override
	public String WareIssue(Ticket ticket) throws DataException {
		return ticket.getPeriodNumber().substring(1);
	}
}
