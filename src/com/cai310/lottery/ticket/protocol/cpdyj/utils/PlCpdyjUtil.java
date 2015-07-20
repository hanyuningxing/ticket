package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.JsonUtil;

public class PlCpdyjUtil extends CpdyjUtil {
	public PlCpdyjUtil(Ticket ticket) {
		super(ticket);
	}
	public PlCpdyjUtil(){
		super();
	}

	@Override
	public String getLotIssue(Ticket ticket) {
		return ticket.getPeriodNumber();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()];
		if(null==plPlayType)throw new DataException("拆票投注方式错误");
		String betTypeStr;//大赢家玩法
		if(plPlayType.equals(PlPlayType.P3Direct)){
			betTypeStr = "1";
		}else if(plPlayType.equals(PlPlayType.Group3)){
			betTypeStr = "6";
		}else if(plPlayType.equals(PlPlayType.Group6)){
			betTypeStr = "6";
		}else if(plPlayType.equals(PlPlayType.DirectSum)){
			betTypeStr = "S1";
		}else if(plPlayType.equals(PlPlayType.GroupSum)){
			betTypeStr = "S9";
		}else if(plPlayType.equals(PlPlayType.P5Direct)){
			betTypeStr = "";
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
				PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content,PlCompoundContent.class);
				betCode.append(betTypeStr).append("|");
				if (ticket.getBetType() == PlPlayType.P3Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(plCompoundContent.getArea1List(), "")).append(",");
					betCode.append(StringUtils.join(plCompoundContent.getArea2List(), "")).append(",");
					betCode.append(StringUtils.join(plCompoundContent.getArea3List(), ""));
				}else if (ticket.getBetType() == PlPlayType.Group3.ordinal()) {// 组三复式
					betCode.append(StringUtils.join(plCompoundContent.getGroup3List(),","));
				}else if (ticket.getBetType() == PlPlayType.Group6.ordinal()) {
					betCode.append(StringUtils.join(plCompoundContent.getGroup6List(),","));
				}else if (ticket.getBetType() == PlPlayType.DirectSum.ordinal()) {
					betCode.append(StringUtils.join(plCompoundContent.getDirectSumList(),","));
				}else if (ticket.getBetType() == PlPlayType.GroupSum.ordinal()) {
					betCode.append(StringUtils.join(plCompoundContent.getGroupSumList(),","));
				}else if (ticket.getBetType() == PlPlayType.P5Direct.ordinal()) {
					betCode.append(StringUtils.join(plCompoundContent.getArea1List(), "")).append(",");
					betCode.append(StringUtils.join(plCompoundContent.getArea2List(), "")).append(",");
					betCode.append(StringUtils.join(plCompoundContent.getArea3List(), "")).append(",");
					betCode.append(StringUtils.join(plCompoundContent.getArea4List(), "")).append(",");
					betCode.append(StringUtils.join(plCompoundContent.getArea5List(), ""));
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
