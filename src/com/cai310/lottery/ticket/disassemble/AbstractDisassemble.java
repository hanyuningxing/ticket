package com.cai310.lottery.ticket.disassemble;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqPrintContent;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqPrintContent;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public abstract class AbstractDisassemble{
	public Integer unitsMoney = 2;// 单注金额
	protected Lottery lottery;// 玩法类型
	protected Byte betType;// 子玩法类型
	protected String gameIssue;// 玩法期号
	protected String ticketId;// 彩票ID
	protected List<TicketDTO> ticketList;// 票集合
	protected List<Long> ticketIdList;// 票Id集合
	protected Long incremenIndex;// 递增流水号
	/** 出票允许的最大倍投 */
	protected abstract int getMaxMultiple();
	
	/** 高频彩单个方案最大允许注数 */
	protected abstract int getMaxUnitsKeno();

	/** 普通彩单个方案最大允许注数 */
	protected abstract int getMaxUnitsCommon();
	public List<TicketDTO> disassembleData(PrintInterfaceDTO printData) throws Exception {
		// 普通玩法，一个1003请求最多包含100方案（scheme），每个方案中最多包含1000注号码；
		// 高频玩法，一个1003请求最多包含10个方案，每个方案中最多包含100注号码
		// 从接口表投注内容中分解出单注内容

		// 大乐透追加,重置单注金额
		if (printData.getLotteryType() == Lottery.DLT
				&& printData.getBetType() == DltPlayType.GeneralAdditional.ordinal()) {
			unitsMoney = 3;
		}
		Date createTime = new Date();
		if (printData.getMode() == SalesMode.COMPOUND) {// 复式
			return this.doCompound(printData, createTime);
		} else {// 单式
			return this.doSingle(printData, createTime);
		}
	}

	/**
	 * 复式内容拆单操作
	 * 
	 * @throws DataException
	 */
	protected List<TicketDTO> doCompoundCommonNumber(PrintInterfaceDTO printData, Date createTime) throws Exception {
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		for (String content : contents) {
			int units = Integer.valueOf(JsonUtil.getStringValueByJsonStr(content, "units")).intValue();
			TicketDTO ticketNew = new TicketDTO();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setContent("[" + content + "]");
			ticketNew.setUnits(units);
			ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
			ticketNew.setCreateTime(createTime);
			resultList.addAll(this.doMultiple(ticketNew));
		}
		return resultList;
	}
	/**
	 * 单式内容拆单操作 可由子类覆写做相应的格式化操作
	 */
	protected List<TicketDTO> doSingle(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, Exception {
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		int maxUnits = Lottery.isKeno(printData.getLotteryType()) ? this.getMaxUnitsKeno() : this.getMaxUnitsCommon();
		if (printData.getUnits() > maxUnits) {
			String[] arr = printData.getContent().split("(\r\n|\n)+");
			int units = 0;
			List<String> contentList = new ArrayList<String>();
			for (String line : arr) {
				contentList.add(line);
				units++;
				if (units == maxUnits) {
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setCreateTime(createTime);
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setContent(StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT));
					resultList.addAll(this.doMultiple(ticketNew));
					units = 0;// 计数复位
					contentList.clear();// 重置
				}
			}
			if (units > 0) {
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(printData.getId());
				ticketNew.setCreateTime(createTime);
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT));
				resultList.addAll(this.doMultiple(ticketNew));
			}
		} else {
			List<String> contentList = new ArrayList<String>();
			String[] arr = printData.getContent().split("(\r\n|\n)+");
			for (String line : arr) {
				contentList.add(line);
			}
			ticketNew = new TicketDTO();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setCreateTime(createTime);
			ticketNew.setContent(StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT));
			resultList.addAll(this.doMultiple(ticketNew));
		}
		return resultList;
	}
	public List<TicketDTO> doSingle(TicketDTO ticket) throws IllegalAccessException, InvocationTargetException,
		NoSuchMethodException, Exception{
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		int maxUnits = Lottery.isKeno(ticket.getLotteryType()) ? this.getMaxUnitsKeno() : this.getMaxUnitsCommon();
		if (ticket.getUnits() > maxUnits) {
			String[] arr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			int units = 0;
			List<String> contentList = new ArrayList<String>();
			for (String line : arr) {
				contentList.add(line);
				units++;
				if (units == maxUnits) {
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, ticket);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(ticket.getPrintinterfaceId());
					ticketNew.setCreateTime(ticket.getCreateTime());
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setContent(StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT));
					resultList.addAll(this.doMultiple(ticketNew));
					units = 0;// 计数复位
					contentList.clear();// 重置
				}
			}
			if (units > 0) {
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, ticket);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(ticket.getPrintinterfaceId());
				ticketNew.setCreateTime(ticket.getCreateTime());
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(StringUtils.join(contentList,Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT));
				resultList.addAll(this.doMultiple(ticketNew));
			}
		} else {
			resultList.addAll(this.doMultiple(ticket));
		}
		return resultList;
	}
	/**
	 * 复式内容拆单操作
	 * @throws DataException 
	 */
	protected abstract List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime) throws Exception;
	/**
	 * 倍数拆单操作,拆成最大允许的倍投
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	protected List<TicketDTO> doMultiple(TicketDTO ticket) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<TicketDTO> ticketList = new ArrayList<TicketDTO>();
		int multiple = ticket.getMultiple();
		if (multiple > this.getMaxMultiple()) {
			TicketDTO ticketNew = null;
			int times = multiple / this.getMaxMultiple();
			for (int i = 0; i < times; i++) {
				ticketNew = new TicketDTO();// 组合新单
				PropertyUtils.copyProperties(ticketNew, ticket);
				ticketNew.setMultiple(this.getMaxMultiple());
				ticketNew.setSchemeCost(this.getMaxMultiple() * unitsMoney * ticketNew.getUnits());
				ticketList.add(ticketNew);
			}
			int remainMultiple = multiple % this.getMaxMultiple();
			if (remainMultiple > 0) {
				ticketNew = new TicketDTO();// 组合新单
				PropertyUtils.copyProperties(ticketNew, ticket);
				ticketNew.setMultiple(remainMultiple);
				ticketNew.setSchemeCost(remainMultiple * unitsMoney * ticketNew.getUnits());
				ticketList.add(ticketNew);
			}
		} else {
			ticketList.add(ticket);
		}
		return ticketList;
	}
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}

}
