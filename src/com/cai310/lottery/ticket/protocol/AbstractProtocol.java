package com.cai310.lottery.ticket.protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.ticket.protocol.response.dto.SeqOddsInfo;
import com.cai310.lottery.ticket.protocol.support.MessageSendDTO;
import com.cai310.lottery.ticket.protocol.support.UrlUtil;
import com.cai310.utils.JsonUtil;

public abstract class AbstractProtocol implements Protocol {
	public Integer unitsMoney = 2;// 单注金额
	protected Lottery lottery;// 玩法类型
	protected Byte betType;// 子玩法类型
	protected String gameIssue;// 玩法期号
	protected String ticketId;// 彩票ID
	protected List<Ticket> ticketList;// 票集合
	protected List<Long> ticketIdList;// 票Id集合
	protected Long incremenIndex;// 递增流水号

	public static final String CONTENT_BLANK_SPACE = " ";
	public static final String CONTENT_SPACE = "";


	/** 商户ID */
	public abstract String getAgentId();

	/** 商户密码 */
	protected abstract String getAgentPwd();

	/** 交易提交地址 */
	protected abstract String getTransactionUrl();

	/** 组合发送信息消息头 */
	protected abstract String composeMessageHead(String messageBody);

	/** 组合发送信息消息体 */
	protected abstract String composeTicketBody();

	/** 组合发送信息消息体 */
	protected abstract String composeMessageBody();

	/** 组合发送信息消息体 */
	protected abstract String composeMessage();

	/** 交易类型键 */
	protected abstract String getTypeKey();

	/** 发送信息消息体键 */
	protected abstract String getMessageKey();

	/** 交易类型 */
	protected abstract int getType();

	/** 出票允许的最大倍投 */
	protected abstract int getMaxMultiple();

	/** 高频彩单个方案最大允许注数 */
	protected abstract int getMaxUnitsKeno();

	/** 普通彩单个方案最大允许注数 */
	protected abstract int getMaxUnitsCommon();

	/** message简单发送 */
	protected abstract boolean simpleSend();

	/** message编码 */
	protected String getEncoding() {
		return "utf-8";
	}

	/**
	 * 构造信息发送传输体
	 * 
	 * @return 传输实体 MessageSendDTO
	 * @throws IOException
	 */
	public String messageSend() throws IOException {
		return UrlUtil.httpClientUtils(this.bulidMessageSendDto());
	}

	/**
	 * 构造信息
	 * 
	 * @return 传输实体 MessageSendDTO
	 * @throws IOException
	 */
	public MessageSendDTO bulidMessageSendDto() {
		MessageSendDTO dto = new MessageSendDTO();
		dto.setSimpleSend(simpleSend());
		dto.setTypekey(this.getTypeKey());
		dto.setType(this.getType());
		dto.setMessageKey(this.getMessageKey());
		dto.setTransationUrl(this.getTransactionUrl());
		// 获取相应信息内容
		String message = this.composeMessage();
		dto.setMessage(message);
		dto.setEncoding(this.getEncoding());
		return dto;
	}

	@Override
	public List<Ticket> disassembleData(PrintInterface printData) throws Exception {
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
	protected List<Ticket> doCompound(PrintInterface printData, Date createTime) throws Exception {
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		List<Ticket> resultList = new ArrayList<Ticket>();
		List<String> singleContents = new ArrayList<String>();// 复式一注的转为单式出票的内容集合
		List<String> compoundContents = new ArrayList<String>();// 复式内容集合
		int units = 0;
		int compoundTotalUnits = 0;
		int singleTotalUnits = 0;
		for (String content : contents) {
			units = Integer.valueOf(JsonUtil.getStringValueByJsonStr(content, "units")).intValue();
			if (units == 1) {
				singleContents.add(content);
				singleTotalUnits++;
			} else {
				compoundContents.add(content);
				compoundTotalUnits += units;
			}
		}
		// 单式内容
		if (!singleContents.isEmpty()) {
			Ticket ticketNew = new Ticket();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setContent("[" + StringUtils.join(singleContents, ",") + "]");
			ticketNew.setUnits(singleTotalUnits);
			ticketNew.setSchemeCost(singleTotalUnits * unitsMoney * ticketNew.getMultiple());
			ticketNew.setCreateTime(createTime);
			resultList.addAll(this.doMultiple(ticketNew));
		}

		// 复式内容
		if (!compoundContents.isEmpty()) {
			Ticket ticketNew = new Ticket();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setContent("[" + StringUtils.join(compoundContents, ",") + "]");
			ticketNew.setUnits(compoundTotalUnits);
			ticketNew.setSchemeCost(compoundTotalUnits * unitsMoney * ticketNew.getMultiple());
			ticketNew.setCreateTime(createTime);
			resultList.addAll(this.doMultiple(ticketNew));
		}

		return resultList;
	}

	/**
	 * 单式内容拆单操作 可由子类覆写做相应的格式化操作
	 */
	protected List<Ticket> doSingle(PrintInterface printData, Date createTime) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<Ticket> resultList = new ArrayList<Ticket>();
		Ticket ticketNew = null;
		int maxUnits = Lottery.isKeno(printData.getLotteryType()) ? this.getMaxUnitsKeno() : this.getMaxUnitsCommon();
		if (printData.getUnits() > maxUnits) {
			String[] arr = printData.getContent().split("(\r\n|\n)+");
			int units = 0;
			List<String> contentList = new ArrayList<String>();
			for (String line : arr) {
				contentList.add(line);
				units++;
				if (units == maxUnits) {
					ticketNew = new Ticket();
					PropertyUtils.copyProperties(ticketNew, printData);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(printData.getId());
					ticketNew.setCreateTime(createTime);
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
					resultList.addAll(this.doMultiple(ticketNew));
					units = 0;// 计数复位
					contentList.clear();// 重置
				}
			}
			if (units > 0) {
				ticketNew = new Ticket();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(printData.getId());
				ticketNew.setCreateTime(createTime);
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
				resultList.addAll(this.doMultiple(ticketNew));
			}
		} else {
			ticketNew = new Ticket();
			PropertyUtils.copyProperties(ticketNew, printData);
			ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setCreateTime(createTime);
			resultList.addAll(this.doMultiple(ticketNew));
		}
		return resultList;
	}

	public List<Ticket> doSingle(Ticket ticket) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<Ticket> resultList = new ArrayList<Ticket>();
		Ticket ticketNew = null;
		int maxUnits = Lottery.isKeno(ticket.getLotteryType()) ? this.getMaxUnitsKeno() : this.getMaxUnitsCommon();
		if (ticket.getUnits() > maxUnits) {
			String[] arr = ticket.getContent().split("(\r\n|\n)+");
			int units = 0;
			List<String> contentList = new ArrayList<String>();
			for (String line : arr) {
				contentList.add(line);
				units++;
				if (units == maxUnits) {
					ticketNew = new Ticket();
					PropertyUtils.copyProperties(ticketNew, ticket);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(ticket.getPrintinterfaceId());
					ticketNew.setCreateTime(ticket.getCreateTime());
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
					resultList.addAll(this.doMultiple(ticketNew));
					units = 0;// 计数复位
					contentList.clear();// 重置
				}
			}
			if (units > 0) {
				ticketNew = new Ticket();
				PropertyUtils.copyProperties(ticketNew, ticket);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(ticket.getPrintinterfaceId());
				ticketNew.setCreateTime(ticket.getCreateTime());
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
				resultList.addAll(this.doMultiple(ticketNew));
			}
		} else {
			resultList.addAll(this.doMultiple(ticketNew));
		}
		return resultList;
	}

	/**
	 * 倍数拆单操作,拆成最大允许的倍投
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	protected List<Ticket> doMultiple(Ticket ticket) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		int multiple = ticket.getMultiple();
		if (multiple > this.getMaxMultiple()) {
			Ticket ticketNew = null;
			int times = multiple / this.getMaxMultiple();
			for (int i = 0; i < times; i++) {
				ticketNew = new Ticket();// 组合新单
				PropertyUtils.copyProperties(ticketNew, ticket);
				ticketNew.setMultiple(this.getMaxMultiple());
				ticketNew.setSchemeCost(this.getMaxMultiple() * unitsMoney * ticketNew.getUnits());
				ticketList.add(ticketNew);
			}
			int remainMultiple = multiple % this.getMaxMultiple();
			if (remainMultiple > 0) {
				ticketNew = new Ticket();// 组合新单
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

	/**
	 * 获取元素集合
	 * 
	 * @param responseMessage
	 * @param elementFlag
	 * @return List<String>
	 */
	protected List<String> getXmlElements(String responseMessage, String elementFlag) {
		List<String> returnStrs = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<" + elementFlag + ">(.+?)</" + elementFlag + ">");
		Matcher m = pattern.matcher(responseMessage);
		while (m.find()) {
			returnStrs.add(m.group(1));
		}
		return returnStrs;
	}

	/**
	 * 获取元素
	 * 
	 * @param responseMessage
	 * @param elementFlag
	 * @return
	 */
	protected String getXmlElement(String responseMessage, String elementFlag) {
		Pattern pattern = Pattern.compile("<" + elementFlag + ">(.+?)</" + elementFlag + ">");
		Matcher m = pattern.matcher(responseMessage);
		String returnValue = "";
		if (m.find()) {
			returnValue = m.group(1);
		}
		return returnValue;
	}

	/**
	 * 从返回串中获取xml
	 * 
	 * @param responseMessage
	 * @return
	 */
	protected String getResponseXML(String responseMessage) {
		if (responseMessage == null || "".equals(responseMessage)) {
			return "<?xml version=\"1.0\" encoding=\"GBK\"?></message>";
		}
		int flagIndex = responseMessage.indexOf("<");
		if (flagIndex != -1) {
			responseMessage = responseMessage.substring(flagIndex, responseMessage.length());
		}
		return responseMessage;
	}

	/**
	 * @param lottery
	 *            the lottery to set
	 */
	@Override
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/**
	 * @param gameIssue
	 *            the gameIssue to set
	 */
	@Override
	public void setGameIssue(String gameIssue) {
		this.gameIssue = gameIssue;
	}

	/**
	 * @param ticketList
	 *            the ticketList to set
	 */
	@Override
	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	/**
	 * 解析场次赔率
	 * 
	 * @param responseMessage
	 * @return
	 */
	@Override
	public List<SeqOddsInfo> parseResponseDczcOdds(String responseMessage) {
		return null;
	}

	/*
	 * @see
	 * com.cai310.ticket.protocol.Protocol#setTicketIdList(java.util.List)
	 */
	@Override
	public void setTicketIdList(List<Long> ticketIdList) {
		this.ticketIdList = ticketIdList;
	}

	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @param ticketId
	 *            the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @param betType
	 *            the betType to set
	 */
	@Override
	public void setBetType(Byte betType) {
		this.betType = betType;
	}

	/**
	 * @param incremenIndex
	 *            the incremenIndex to set
	 */
	public void setIncremenIndex(Long incremenIndex) {
		this.incremenIndex = incremenIndex;
	}

}
