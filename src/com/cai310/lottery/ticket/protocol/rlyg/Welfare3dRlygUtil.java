package com.cai310.lottery.ticket.protocol.rlyg;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
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
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Welfare3dRlygUtil extends RlygUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	private static final String NUMCODE = " ";
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = "&amp;";
	public Welfare3dRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public Welfare3dRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		StringBuffer betCode = new StringBuffer();
		Welfare3dPlayType welfare3dPlayType = Welfare3dPlayType.values()[ticket.getBetType()];
		if(null==welfare3dPlayType)throw new DataException("拆票投注方式错误");
		String playType;//玩法
		String betType;//投注玩法
		if(welfare3dPlayType.equals(Welfare3dPlayType.Direct)){
			playType = "01";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				betType = "02";
			}
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.Group3)){
			playType = "02";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				throw new DataException("拆票投注方式错误");
			}
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.Group6)){
			playType = "03";
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				throw new DataException("拆票投注方式错误");
			}
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
				Welfare3dCompoundContent welfare3dCompoundContent = JsonUtil.getObject4JsonString(content,Welfare3dCompoundContent.class);
				if (ticket.getBetType() == Welfare3dPlayType.Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea1List(),ONE_NF), NUMCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea2List(),ONE_NF), NUMCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea3List(),ONE_NF), NUMCODE));
				}else if (ticket.getBetType() == Welfare3dPlayType.Group3.ordinal()) {// 组三复式
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getGroup3List(),ONE_NF),NUMSPLITCODE));
				}else if (ticket.getBetType() == Welfare3dPlayType.Group6.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getGroup6List(),ONE_NF),NUMSPLITCODE));
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
		return Lottery.WELFARE3D;
	}

}
