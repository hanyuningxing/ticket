package com.cai310.lottery.ticket.protocol.localnew.utils;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.klpk.KlpkCompoundContent;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.utils.JsonUtil;

public class KLPKCPUtil extends CPUtil{

	private static final String UNITSPILTCODE = "+";
	private static final String SPILTCODE = ",";
	
	
	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "10571";
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return ticket.getPeriodNumber().substring(2);
	}

	@Override
	public String getCpBetType(Ticket ticket) throws DataException {
		KlpkPlayType klpkPlayType=KlpkPlayType.values()[ticket.getBetType()];
		if(null==klpkPlayType)throw new DataException("拆票投注方式错误");
		if(klpkPlayType.equals(KlpkPlayType.RandomOne)){
			return "0";
		}else if(klpkPlayType.equals(KlpkPlayType.RandomTwo)){
			return "1";
		}else if(klpkPlayType.equals(KlpkPlayType.RandomThree)){
			return "2";
		}else if(klpkPlayType.equals(KlpkPlayType.RandomFour)){
			return "3";
		}else if(klpkPlayType.equals(KlpkPlayType.RandomFive)){
			return "4";
		}else if(klpkPlayType.equals(KlpkPlayType.RandomSix)){
			return "5";
		}else if(klpkPlayType.equals(KlpkPlayType.SAME)){
			return "6";
		}else if(klpkPlayType.equals(KlpkPlayType.SHUN)){
			return "8";
		}else if(klpkPlayType.equals(KlpkPlayType.DUI)){
			return "10";
		}else{
			return null;
		}
	}
	
	@Override
	public String getCpPlayType(Ticket ticket) {
		if(null==ticket.getMode())return null;
		KlpkPlayType klpkPlayType=KlpkPlayType.values()[ticket.getBetType()];
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			if(klpkPlayType.equals(KlpkPlayType.RandomOne)){
				return "0";
			}else {
				return "1";
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(klpkPlayType.equals(KlpkPlayType.RandomOne)){
				return "0";
			}else if(klpkPlayType.equals(KlpkPlayType.DUI)||klpkPlayType.equals(KlpkPlayType.SHUN)){//豹子
				if(ticket.getUnits()==1){
					return "1";
				}else {
					return "0";
				}
			}else if(klpkPlayType.equals(KlpkPlayType.RandomTwo)||klpkPlayType.equals(KlpkPlayType.RandomThree)
					||klpkPlayType.equals(KlpkPlayType.RandomFour)||klpkPlayType.equals(KlpkPlayType.RandomFive)
					||klpkPlayType.equals(KlpkPlayType.RandomSix)){
				if(ticket.getUnits()==1){
					return "1";
				}else{
					return "2";
				}
			}else{
				return "1";
			}
		}
		return null;
	}

	@Override
	public String getBetContent(Ticket ticket) throws DataException {
		StringBuffer betCode = new StringBuffer();
		KlpkPlayType klpkPlayType=KlpkPlayType.values()[ticket.getBetType()];
		if(null==klpkPlayType)throw new DataException("拆票投注方式错误");
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				betCode.append(ticketStr.trim().replace(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER, SPILTCODE));
				betCode.append(UNITSPILTCODE);
			}
			betCode = betCode.delete(betCode.length()-UNITSPILTCODE.length(), betCode.length());
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			
			for (String content : contents) {
				KlpkCompoundContent klpkCompoundContent=JsonUtil.getObject4JsonString(content, KlpkCompoundContent.class);
				if(klpkPlayType.equals(KlpkPlayType.DUIBAO)||klpkPlayType.equals(KlpkPlayType.SHUNBAO)||klpkPlayType.equals(KlpkPlayType.SAMEBAO)){
					betCode.append("00");
				}else {
					betCode.append(StringUtils.join(formatBetNum(klpkCompoundContent.getBetList(),TWO_NF),SPILTCODE));
				}
				betCode.append(UNITSPILTCODE);
			}
			betCode = betCode.delete(betCode.length()-UNITSPILTCODE.length(), betCode.length());
		}else{
			throw new DataException("拆票单复式错误");
		}
		return betCode.toString();
	}

	@Override
	public String getSpecialFlag(Ticket ticket) {
		return "";
	}
	
}
