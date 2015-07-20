package com.cai310.lottery.ticket.disassemble;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.support.qyh.QyhCompoundContent;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.ticket.common.StringOfListUtil;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.lottery.ticket.dataconver.QyhDataConverCaitong;
import com.cai310.utils.JsonUtil;

public class QyhDisassemble extends AbstractDisassemble {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected int getMaxMultiple() {
		return 99;
	}

	@Override
	protected int getMaxUnitsKeno() {
		return 5;
	}

	@Override
	protected int getMaxUnitsCommon() {
		return 5;
	}

	@Override
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime)
			throws Exception {
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		List<String> betDanList = null;
		List<String> betList = null;
		List<String> bet1List = null;
		List<String> bet2List = null;
		List<String> bet3List = null;
		QyhPlayType playType = QyhPlayType.values()[printData.getBetType()];

		for (String compoundContent : contents) {
			QyhCompoundContent qyhCompoundContent = JsonUtil.getObject4JsonString(compoundContent,
					QyhCompoundContent.class);

			betDanList = StringOfListUtil.format(qyhCompoundContent.getBetDanList());
			betList = StringOfListUtil.format(qyhCompoundContent.getBetList());

			bet1List = StringOfListUtil.format(qyhCompoundContent.getBet1List());
			bet2List = StringOfListUtil.format(qyhCompoundContent.getBet2List());
			bet3List = StringOfListUtil.format(qyhCompoundContent.getBet3List());

			// 顺选1 2 3
			if (playType == QyhPlayType.DirectOne||playType == QyhPlayType.DirectTwo||playType == QyhPlayType.DirectThree) {
				List<String> resultNewConverList = new ArrayList<String>();
				if (playType == QyhPlayType.DirectOne) {
					for (String bet1 : betList) {
						resultNewConverList.add(bet1);
					}
				}
				if (playType == QyhPlayType.DirectTwo) {
					for (String bet1 : bet1List) {
						for (String bet2 : bet2List) {
							resultNewConverList.add(bet1 + " " + bet2);
						}
					}
				}

				if (playType == QyhPlayType.DirectThree) {
					for (String bet1 : bet1List) {
						for (String bet2 : bet2List) {
							for (String bet3 : bet3List) {
								resultNewConverList.add(bet1 + " " + bet2 + " " + bet3);
							}
						}
					}
				}

				TicketDTO ticketNew = createTicketDTONew(printData, createTime, resultNewConverList, playType);
				if (ticketNew.getUnits() > getMaxUnitsKeno()) {
					resultList.addAll(doSingle(ticketNew));
				} else {
					resultList.add(ticketNew);
				}
			} else {
				if (betDanList.isEmpty()) {
					int units = Integer.valueOf(JsonUtil.getStringValueByJsonStr(compoundContent, "units")).intValue();
					TicketDTO ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setContent("[" + compoundContent + "]");
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setCreateTime(createTime);
					resultList.addAll(this.doMultiple(ticketNew));
				} else {
					QyhDataConverCaitong bb = new QyhDataConverCaitong(playType.getLineLimit(), betDanList, betList);
					List<List<String>> resultConverList = bb.getResultList();
					TicketDTO ticketNew = null;
					List<String> resultNewConverList = new ArrayList<String>();
					for (List<String> bets : resultConverList) {
						resultNewConverList.add(StringUtils.join(bets, " "));
					}
					if (playType == QyhPlayType.RandomSeven || playType == QyhPlayType.RandomEight
							|| playType == QyhPlayType.RandomNine || playType == QyhPlayType.RandomTen) {
						for (String line : resultNewConverList) {
							ticketNew = new TicketDTO();
							PropertyUtils.copyProperties(ticketNew, printData);
							ticketNew.setId(null);
							ticketNew.setPrintinterfaceId(printData.getId());
							ticketNew.setCreateTime(createTime);
							ticketNew.setUnits(1);
							ticketNew.setSchemeCost(1 * unitsMoney * ticketNew.getMultiple());
							ticketNew.setContent(line);
							ticketNew.setMode(SalesMode.SINGLE);
							resultList.addAll(this.doMultiple(ticketNew));
						}
					} else {
						ticketNew = createTicketDTONew(printData, createTime, resultNewConverList, playType);
						if (ticketNew.getUnits() > getMaxUnitsKeno()) {
							resultList.addAll(doSingle(ticketNew));
						} else {
							resultList.add(ticketNew);
						}
					}
				}
			}
		}
		return resultList;
	}
	
	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, Date createTime, List<String> contentList,
			QyhPlayType playType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String content = StringUtils.join(contentList, "\r\n");
		int units = contentList.size();
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);
		ticketNew.setId(null);
		ticketNew.setPrintinterfaceId(printData.getId());
		ticketNew.setContent(content);
		ticketNew.setUnits(units);
		ticketNew.setSchemeCost(units * 2 * ticketNew.getMultiple());
		ticketNew.setCreateTime(createTime);
		ticketNew.setMode(SalesMode.SINGLE);
		ticketNew.setBetType(playType.getBetTypeValue());
		return ticketNew;

	}
	// 任选 7 8 9 10 单复式 都是 一票一注
	@Override
	protected List<TicketDTO> doSingle(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, Exception {
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		if (printData.getBetType() == QyhPlayType.RandomSeven.ordinal()
				|| printData.getBetType() == QyhPlayType.RandomEight.ordinal()
				|| printData.getBetType() == QyhPlayType.RandomNine.ordinal()
				|| printData.getBetType() == QyhPlayType.RandomTen.ordinal()) {
			String[] arr = printData.getContent().split("(\r\n|\n)+");
			List<String> contentList = new ArrayList<String>();
			for (String line : arr) {
				contentList.add(line);
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(printData.getId());
				ticketNew.setCreateTime(createTime);
				ticketNew.setUnits(1);
				ticketNew.setSchemeCost(1 * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
				resultList.addAll(this.doMultiple(ticketNew));
				contentList.clear();// 重置
			}
			return resultList;
		} else {
			return super.doSingle(printData, createTime);
		}

	}
	
}
