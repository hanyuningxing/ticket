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
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class PlRlygUtil extends RlygUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = "&amp;";
	public PlRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public PlRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		StringBuffer betCode = new StringBuffer();
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()];
		if(null==plPlayType)throw new DataException("拆票投注方式错误");
		String playType;
		String betType;//玩法
		if(plPlayType.equals(PlPlayType.P3Direct)){
			playType = "01";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				betType = "02";
			}
		}else if(plPlayType.equals(PlPlayType.Group3)){
			playType = "03";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				betType = "02";
			}
		}else if(plPlayType.equals(PlPlayType.Group6)){
			playType = "04";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				betType = "02";
			}
		}else if(plPlayType.equals(PlPlayType.P5Direct)){
			playType = "00";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				betType = "02";
			}
		}else if(plPlayType.equals(PlPlayType.DirectSum)){
			playType = "01";
			betType = "04";
		}else{
			throw new DataException("拆票投注方式错误");
		}
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
					betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
				}
				betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
				betCode.append(UNITSPLITCODE);
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content,PlCompoundContent.class);
				if (ticket.getBetType() == PlPlayType.P5Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), " ")).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), " ")).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), " ")).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea4List(),ONE_NF), " ")).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea5List(),ONE_NF), " "));
				}else if (ticket.getBetType() == PlPlayType.P3Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), " ")).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), " ")).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), " "));
				}else if (ticket.getBetType() == PlPlayType.Group3.ordinal()) {// 组三复式
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup3List(),ONE_NF),NUMSPLITCODE));
				}else if (ticket.getBetType() == PlPlayType.Group6.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup6List(),ONE_NF),NUMSPLITCODE));
				}else if (ticket.getBetType() == PlPlayType.DirectSum.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getDirectSumList(),ONE_NF),NUMSPLITCODE));
				}else{
					throw new DataException("拆票投注方式错误");
				}
				betCode.append(UNITSPLITCODE);
			}
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
		StringBuffer sb = new StringBuffer();
		
		sb.append("<ticket seq=\""+ticket.getId()+"\">"+playType+"-"+betType+"-"+betCode.toString()+"-"+ticket.getMultiple()+"-"+ticket.getSchemeCost()+"</ticket>");
		map.put("betValue", sb.toString());
		map.put("issue", ""+ticket.getPeriodNumber());
		return map;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.PL;
	}

}
