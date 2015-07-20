package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.util.List;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.JsonUtil;

public class PlLiangCaiUtil extends LiangCaiUtil {
	public PlLiangCaiUtil(Ticket ticket) {
		super(ticket);
	}

	public PlLiangCaiUtil() {
	}

	public String getLotIssue(Ticket ticket) {
		return "20" + ticket.getPeriodNumber();
	}

	public String getLotCode(Ticket ticket) throws DataException {
		StringBuffer betCode = new StringBuffer();
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()
				.byteValue()];
		if (plPlayType == null)
			throw new DataException("拆票投注方式错误");
		String betTypeStr;
		if (plPlayType.equals(PlPlayType.P3Direct)) {
			betTypeStr = "1|";
		} else {
			if (plPlayType.equals(PlPlayType.Group3)) {
				if (SalesMode.SINGLE.equals(ticket.getMode()))
					betTypeStr = "6|";
				else
					betTypeStr = "F3|";
			} else {
				if (plPlayType.equals(PlPlayType.Group6)) {
					if (SalesMode.SINGLE.equals(ticket.getMode()))
						betTypeStr = "6|";
					else
						betTypeStr = "F6|";
				} else {
					if (plPlayType.equals(PlPlayType.DirectSum)) {
						betTypeStr = "S1|";
					} else {
						if (plPlayType.equals(PlPlayType.GroupSum)) {
							betTypeStr = "S9|";
						} else {
							if (plPlayType.equals(PlPlayType.P5Direct))
								betTypeStr = "";
							else
								throw new DataException("拆票投注方式错误");
						}
					}
				}
			}
		}
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			String[] ticketArr = ticket.getContent().split(",");
			for (String ticketStr : ticketArr) {
				betCode.append(betTypeStr);
				for (String num : ticketStr.split(" ")) {
					betCode.append(Integer.valueOf(num)).append(",");
				}
				betCode = betCode.delete(betCode.length() - ",".length(),
						betCode.length());
				betCode.append(";");
			}
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			String[] contents = JsonUtil.getStringArray4Json(ticket
					.getContent());
			for (String content : contents) {
				PlCompoundContent plCompoundContent = (PlCompoundContent) JsonUtil
						.getObject4JsonString(content, PlCompoundContent.class);
				betCode.append(betTypeStr);

				if (ticket.getBetType().byteValue() == PlPlayType.P3Direct
						.ordinal()) {
					betCode.append(
							zeroLeadStringListJoin(plCompoundContent
									.getArea1List(),"")).append(",");
					betCode.append(
							zeroLeadStringListJoin(plCompoundContent
									.getArea2List(),"")).append(",");
					betCode.append(zeroLeadStringListJoin(plCompoundContent
							.getArea3List(),""));
				} else if (ticket.getBetType().byteValue() == PlPlayType.Group3
						.ordinal()) {
					betCode.append(zeroLeadStringListJoin(plCompoundContent
							.getGroup3List(),""));
				} else if (ticket.getBetType().byteValue() == PlPlayType.Group6
						.ordinal()) {
					betCode.append(zeroLeadStringListJoin(plCompoundContent
							.getGroup6List(),""));
				} else if (ticket.getBetType().byteValue() == PlPlayType.DirectSum
						.ordinal()) {
					betCode.append(zeroLeadStringListJoin(plCompoundContent
							.getDirectSumList(),","));
				} else if (ticket.getBetType().byteValue() == PlPlayType.GroupSum
						.ordinal()) {
					betCode.append(zeroLeadStringListJoin(plCompoundContent
							.getGroupSumList(),","));
				} else if (ticket.getBetType().byteValue() == PlPlayType.P5Direct
						.ordinal()) {
					betCode.append(
							zeroLeadStringListJoin(plCompoundContent
									.getArea1List(),"")).append(",");
					betCode.append(
							zeroLeadStringListJoin(plCompoundContent
									.getArea2List(),"")).append(",");
					betCode.append(
							zeroLeadStringListJoin(plCompoundContent
									.getArea3List(),"")).append(",");
					betCode.append(
							zeroLeadStringListJoin(plCompoundContent
									.getArea4List(),"")).append(",");
					betCode.append(zeroLeadStringListJoin(plCompoundContent
							.getArea5List(),""));
				} else {
					throw new DataException("拆票投注方式错误");
				}
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