package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.utils.JsonUtil;

public class Weflare3dCpdyjUtil extends CpdyjUtil {
	public Weflare3dCpdyjUtil(Ticket ticket) {
		super(ticket);
	}
	public Weflare3dCpdyjUtil(){
		super();
	}

	@Override
	public String getLotIssue(Ticket ticket) {
		return ticket.getPeriodNumber();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		Welfare3dPlayType welfare3dPlayType = Welfare3dPlayType.values()[ticket.getBetType()];
		if(null==welfare3dPlayType)throw new DataException("拆票投注方式错误");
		String betTypeStr;//大赢家玩法
		if(welfare3dPlayType.equals(Welfare3dPlayType.Direct)){
			betTypeStr = "1";
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.Group3)){
			betTypeStr = "6";
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.Group6)){
			betTypeStr = "6";
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.DirectSum)){
			betTypeStr = "S1";
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.GroupSum)){
			betTypeStr = "S9";
		}else{
			throw new DataException("拆票投注方式错误");
		}
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				betCode.append(betTypeStr).append("|");
				for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
					betCode.append(Integer.valueOf(num)).append(",");
				}
				betCode = betCode.delete(betCode.length()-",".length(), betCode.length());
				betCode.append(";");
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				Welfare3dCompoundContent welfare3dCompoundContent = JsonUtil.getObject4JsonString(content,Welfare3dCompoundContent.class);
				betCode.append(betTypeStr).append("|");
				if (ticket.getBetType() == Welfare3dPlayType.Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(welfare3dCompoundContent.getArea1List(), "")).append(",");
					betCode.append(StringUtils.join(welfare3dCompoundContent.getArea2List(), "")).append(",");
					betCode.append(StringUtils.join(welfare3dCompoundContent.getArea3List(), ""));
				}else if (ticket.getBetType() == Welfare3dPlayType.Group3.ordinal()) {// 组三复式
					betCode.append(StringUtils.join(welfare3dCompoundContent.getGroup3List(),","));
				}else if (ticket.getBetType() == Welfare3dPlayType.Group6.ordinal()) {
					betCode.append(StringUtils.join(welfare3dCompoundContent.getGroup6List(),","));
				}else if (ticket.getBetType() == Welfare3dPlayType.DirectSum.ordinal()) {
					betCode.append(StringUtils.join(welfare3dCompoundContent.getDirectSumList(),","));
				}else if (ticket.getBetType() == Welfare3dPlayType.GroupSum.ordinal()) {
					betCode.append(StringUtils.join(welfare3dCompoundContent.getGroupSumList(),","));
				}else{
					throw new DataException("拆票投注方式错误");
				}
				betCode.append(";");
			}
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-";".length(), betCode.length());
		return betCode.toString();
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
