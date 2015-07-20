package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlContentBeanBuilder;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.JsonUtil;

public class PLCPUtil extends CPUtil {
	/**
	 * 
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	private static final String NUMCODE = ",";
	private static final String CONTENTCONNECTCODE = "-";
	private static final String DANSPLITCODE = ":";
	private static final String SINGSPILTCODE = "+";

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "10053";
	}

	@Override
	public String getCpBetType(Ticket ticket) throws DataException {
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()];
		if (null == plPlayType)
			throw new DataException("拆票投注方式错误");
		if (plPlayType.equals(PlPlayType.P3Direct)) {
			return "0";
		} else if (plPlayType.equals(PlPlayType.P5Direct)) {
			return "1";
		} else if (plPlayType.equals(PlPlayType.Group3)) {
			if (SalesMode.SINGLE.equals(ticket.getMode())) {
				return "0";
			} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
				return "2";
			} else {
				return null;
			}
		} else if (plPlayType.equals(PlPlayType.Group6)) {
			if (SalesMode.SINGLE.equals(ticket.getMode())) {
				return "0";
			} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
				if (ticket.getUnits() == 1) {
					return "0";
				} else {
					return "3";
				}
			} else {
				return null;
			}
		} else if (plPlayType.equals(PlPlayType.DirectSum)) {
			return "4";
		} else {
			return null;
		}
	}

	@Override
	public String getCpPlayType(Ticket ticket) {
		if (ticket.getMode() == null)
			return null;
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()];
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			if (plPlayType.equals(PlPlayType.Group3) || plPlayType.equals(PlPlayType.Group6)) {
				return "6";
			} else {
				return "1";
			}
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (plPlayType.equals(PlPlayType.P3Direct) || plPlayType.equals(PlPlayType.P5Direct)) {
				if (ticket.getUnits() == 1) {
					return "1";
				} else {
					return "5";
				}
			} else if (plPlayType.equals(PlPlayType.Group6)) {
				if (ticket.getUnits() == 1) {
					return "6";
				} else {
					return "0";
				}
			} else {
				return "0";
			}
		}
		return null;
	}

	@Override
	public String getBetContent(Ticket ticket) throws DataException {
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()];
		StringBuffer sb = new StringBuffer();
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			String[] arr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (int i = 0; i < arr.length; i++) {
				String[] str = arr[i].split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for (String st : str) {
					int num = Integer.valueOf(st);
					sb.append(num);
				}
				sb.append(SINGSPILTCODE);
			}
			return sb.substring(0, sb.length() - SINGSPILTCODE.length());
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (plPlayType.equals(PlPlayType.P3Direct)) {
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content, PlCompoundContent.class);
					if (ticket.getUnits() == 1) {
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), ""));
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), ""));
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), ""));
					} else {
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), ""));
					}
				}
			} else if (plPlayType.equals(PlPlayType.P5Direct)) {
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content, PlCompoundContent.class);
					if (ticket.getUnits() == 1) {
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), ""));
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), ""));
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), ""));
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea4List(),ONE_NF), ""));
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea5List(),ONE_NF), ""));
					} else {
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea4List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getArea5List(),ONE_NF), ""));
					}
				}
			} else if (plPlayType.equals(PlPlayType.DirectSum)) {
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content, PlCompoundContent.class);
					sb.append(StringUtils.join(formatBetNum(plCompoundContent.getDirectSumList(), TWO_NF), ","));
				}
			} else if (plPlayType.equals(PlPlayType.Group3)) {
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content, PlCompoundContent.class);
					sb.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup3List(),ONE_NF), NUMCODE));
				}
			} else if (plPlayType.equals(PlPlayType.Group6)) {
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content, PlCompoundContent.class);
					if (ticket.getUnits() == 1) {
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup6List(),ONE_NF), ""));
					} else {
						sb.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup6List(),ONE_NF), NUMCODE));
					}
				}
			}
		} else {
			throw new DataException("拆票单复式错误");
		}
		return sb.toString();
	}

	@Override
	public String getSpecialFlag(Ticket ticket) {
		return "";
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return null;
	}

}
