package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.util.List;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sevenstar.SevenstarCompoundContent;
import com.cai310.utils.JsonUtil;

public class SevenStarLiangCaiUtil extends LiangCaiUtil {
	public SevenStarLiangCaiUtil(Ticket ticket) {
		super(ticket);
	}

	public SevenStarLiangCaiUtil() {
	}

	public String getLotIssue(Ticket ticket) {
		return "20" + ticket.getPeriodNumber();
	}

	public String getLotCode(Ticket ticket) throws DataException {
		StringBuffer betCode = new StringBuffer();
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
		      String[] ticketArr = ticket.getContent().split(",");
		      for (String ticketStr : ticketArr) {
		        String[] numArr = ticketStr.split(" ");
		        for (int i = 0; i < numArr.length; i++) {
		          betCode.append(Integer.valueOf(numArr[i])).append(",");
		        }
		        betCode = betCode.delete(betCode.length() - ",".length(), betCode.length());
		        betCode.append(";");
		      }
		      
		    } else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
		      String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
		      for (String content : contents) {
		        SevenstarCompoundContent sevenstarCompoundContent = (SevenstarCompoundContent)JsonUtil.getObject4JsonString(content, SevenstarCompoundContent.class);
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea1List(), "")).append(",");
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea2List(), "")).append(",");
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea3List(), "")).append(",");
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea4List(), "")).append(",");
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea5List(), "")).append(",");
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea6List(), "")).append(",");
		        betCode.append(zeroLeadStringListJoin(sevenstarCompoundContent.getArea7List(), ""));
		        betCode.append(";");
		      }
		    } else {
		      throw new DataException("拆票单复式错误");
		    }
		betCode = betCode.delete(betCode.length() - ";".length(),
				betCode.length());
		return betCode.toString();
	}

	public String getAttach(Ticket ticket) {
		return "";
	}

	public String getOneMoney(Ticket ticket) {
		return "2";
	}

	public String zeroLeadStringListJoin(List<String> list, String spliter) {
		StringBuffer sb = new StringBuffer();
		for (String num : list) {
			sb.append(Integer.valueOf(num)).append(spliter);
		}
		sb.setLength(sb.length()-spliter.length());
		return sb.toString();
	}
}