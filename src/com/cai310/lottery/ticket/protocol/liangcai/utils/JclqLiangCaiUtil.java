package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.util.Set;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Sets;

public class JclqLiangCaiUtil extends LiangCaiUtil {
	private static final String matchSplitStr=",";
    private static final String betSplitStr="/";
    private static final String passSplitStr="*";
    private static final String areaSplitStr="|";
	public JclqLiangCaiUtil(Ticket ticket) {
		super(ticket);
	}
	public JclqLiangCaiUtil(){
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
		//混合过关
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
		if(PlayType.MIX.equals(playType)){
				StringBuffer sb = new StringBuffer();
				sb.append("HH").append(areaSplitStr);
				StringBuffer betSb = new StringBuffer();
				for (String itemStr : items) {
					map = JsonUtil.getMap4Json(itemStr);
					String matchKey = String.valueOf(map.get("matchKey"));//格式：20120306-305 要改为120306305
					matchKey = matchKey.replaceAll("-", "");
					matchKey = matchKey.substring(2,matchKey.length());
					PlayType itemPlayType = PlayType.valueOfName(String.valueOf(map.get("playType")));
					itemValue = Integer.valueOf(String.valueOf(map.get("value")));
					String playTypeStr = itemPlayType.name();
					betSb.append(playTypeStr).append(">").append(matchKey+"=");
				switch (itemPlayType) {
				case SF:
					for (ItemSF type : ItemSF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betSb.append(type.getValue()).append(betSplitStr);
						}
					}
					break;
				case RFSF:
					for (ItemRFSF type : ItemRFSF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betSb.append(type.getValue()).append(betSplitStr);
						}
					}
					break;
				case SFC:
					for (ItemSFC type : ItemSFC.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betSb.append(type.getCpdyjValue()).append(betSplitStr);
						}
					}
					break;
				case DXF:
					for (ItemDXF type : ItemDXF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							if("1".equals(type.getValue())){
								betSb.append("3").append(betSplitStr);
							}else{
								betSb.append(type.getValue()).append(betSplitStr);
							}
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
		}else{
			String playTypeStr = playType.name();
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
				case SF:
					for (ItemSF type : ItemSF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betSb.append(type.getValue()).append(betSplitStr);
						}
					}
					break;
				case RFSF:
					for (ItemRFSF type : ItemRFSF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betSb.append(type.getValue()).append(betSplitStr);
						}
					}
					break;
				case SFC:
					for (ItemSFC type : ItemSFC.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							betSb.append(type.getCpdyjValue()).append(betSplitStr);
						}
					}
					break;
				case DXF:
					for (ItemDXF type : ItemDXF.values()) {
						int v = 1 << type.ordinal();
						if ((itemValue & v) > 0) {
							if("1".equals(type.getValue())){
								betSb.append("3").append(betSplitStr);
							}else{
								betSb.append(type.getValue()).append(betSplitStr);
							}
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
