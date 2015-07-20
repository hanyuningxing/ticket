package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sdel11to5.SdEl11to5CompoundContent;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.utils.JsonUtil;

public class SdEl11to5LiangCaiUtil extends LiangCaiUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	public SdEl11to5LiangCaiUtil(Ticket ticket) {
		super(ticket);
	}
	public SdEl11to5LiangCaiUtil(){
		super();
	}

	@Override
	public String getLotIssue(Ticket ticket) {
		return ticket.getPeriodNumber();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		SdEl11to5PlayType sdEl11to5PlayType = SdEl11to5PlayType.values()[ticket.getBetType()];
		if(null==sdEl11to5PlayType)throw new DataException("拆票投注方式错误");
		String betTypeStr;//大赢家玩法
		if(sdEl11to5PlayType.equals(SdEl11to5PlayType.NormalOne)){
			betTypeStr = "R1";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomTwo)){
			betTypeStr = "R2";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomThree)){
			betTypeStr = "R3";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomFour)){
			betTypeStr = "R4";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomFive)){
			betTypeStr = "R5";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomSix)){
			betTypeStr = "R6";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomSeven)){
			betTypeStr = "R7";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.RandomEight)){
			betTypeStr = "R8";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoGroup)){
			betTypeStr = "Z2";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeGroup)){
			betTypeStr = "Z3";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)){
			betTypeStr = "Q2";
		}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)){
			betTypeStr = "Q3";
		}else{
			throw new DataException("拆票投注方式错误");
		}
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				betCode.append(betTypeStr).append("|");
				betCode.append(ticketStr);
				betCode.append(";");
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SdEl11to5CompoundContent sdEl11to5CompoundContent = JsonUtil.getObject4JsonString(content,SdEl11to5CompoundContent.class);
				betCode.append(betTypeStr).append("|");
				if(sdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)){
					betCode.append(StringUtils.join(formatBetNum(sdEl11to5CompoundContent.getBet1List(),TWO_NF),Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)).append(",");
					betCode.append(StringUtils.join(formatBetNum(sdEl11to5CompoundContent.getBet2List(),TWO_NF),Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}else if(sdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)){
					betCode.append(StringUtils.join(formatBetNum(sdEl11to5CompoundContent.getBet1List(),TWO_NF),Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)).append(",");
					betCode.append(StringUtils.join(formatBetNum(sdEl11to5CompoundContent.getBet2List(),TWO_NF),Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)).append(",");
					betCode.append(StringUtils.join(formatBetNum(sdEl11to5CompoundContent.getBet3List(),TWO_NF),Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}else{
					betCode.append(StringUtils.join(formatBetNum(sdEl11to5CompoundContent.getBetList(),TWO_NF),Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
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
