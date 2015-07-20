package com.cai310.lottery.entity.lottery.ticket;

import java.io.Serializable;
import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.ticket.common.TicketSupporter;

public class TicketForm implements Serializable{

	private static final long serialVersionUID = -1245834066315462657L;

	/** 拆分后的方案id */
	private Long id;

	/** 创建时间 */
	private Date createTime;

	/** 方案号 */
	private String schemeNumber;

	/** 彩种 */
	private Lottery lotteryType;

	/** 期号 */
	private String periodNumber;

	/** 投注方式 */
	private SalesMode mode;

	/** 方案金额 */
	private Integer schemeCost;

	/** 是否出票成功 */
	private boolean ticketFinsh;

	/** 返回的状态码 */
	private String stateCode;

	/** 电子票号 */
	private String remoteTicketId;
	
	/** 出票方提供状态的更新时间*/
	private Date stateModifyTime;
	
	/** 返回的状态码信息*/
	private String stateCodeMessage;
	
	/** 发送时间 */
	private Date sendTime;	
	
	private TicketSupporter ticketSupporter;	

	/** 暂停出票*/
	private boolean pause;
	/* ----------- getter and setter method ----------- */

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public SalesMode getMode() {
		return mode;
	}

	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public Integer getSchemeCost() {
		return schemeCost;
	}

	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	public boolean isTicketFinsh() {
		return ticketFinsh;
	}

	public void setTicketFinsh(boolean ticketFinsh) {
		this.ticketFinsh = ticketFinsh;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getRemoteTicketId() {
		return remoteTicketId;
	}

	public void setRemoteTicketId(String remoteTicketId) {
		this.remoteTicketId = remoteTicketId;
	}

	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	public String getStateCodeMessage() {
		return stateCodeMessage;
	}

	public void setStateCodeMessage(String stateCodeMessage) {
		this.stateCodeMessage = stateCodeMessage;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}
	
	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
}
