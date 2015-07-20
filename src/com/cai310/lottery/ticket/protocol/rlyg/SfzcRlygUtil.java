package com.cai310.lottery.ticket.protocol.rlyg;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class SfzcRlygUtil extends RlygUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	private static final String NUMCODE = " ";
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = "&amp;";
	public SfzcRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public SfzcRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		String betType = "";
		StringBuffer betCode = new StringBuffer();
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			betType="01";
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String line : ticketArr) {
				if (line.length() != (ZcUtils.getMatchCount(ticket.getLotteryType())))
					throw new DataException("单式选项不符");
				char bet;// 投注内容及结果存放变量
				for (int i = 0; i < line.length(); i++) {
					bet = line.charAt(i);
					betCode.append(bet).append(NUMSPLITCODE);
				}
				betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
				betCode.append(UNITSPLITCODE);
			}
			betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
		    	betType="01";
		    }else{
			    betType="02";
		    }
			String[] betArr = ticket.getContent().split(String.valueOf(ZcUtils.getContentSpiltCode()));
			if (betArr.length != (ZcUtils.getMatchCount(ticket.getLotteryType())))
				throw new DataException("单式选项不符");
			for (String bet : betArr) {
				for (int i = 0; i < bet.length(); i++) {
					char num = bet.charAt(i);
					betCode.append(num).append(NUMCODE);
				}
				betCode = betCode.delete(betCode.length()-NUMCODE.length(), betCode.length());
				betCode.append(NUMSPLITCODE);
			}
			betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
		}else{
			throw new DataException("拆票单复式错误");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<ticket seq=\""+ticket.getId()+"\">00-"+betType+"-"+betCode.toString().replaceAll("#", "_")+"-"+ticket.getMultiple()+"-"+ticket.getSchemeCost()+"</ticket>");
		map.put("betValue", sb.toString());
		map.put("issue", ""+ticket.getPeriodNumber());
		return map;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SFZC;
	}

}
