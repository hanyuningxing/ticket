package com.cai310.lottery.ticket.dto;
import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.common.SalesMode;

/**
 * 打印接口实体DTO.
 * 
 */
public class PrintInterfaceDTO {

	private Long id;
	/** 发起人ID */
	private Long sponsorId;

	/** 方案号 */
	private String schemeNumber;

	/** 彩种 */
	private Lottery lotteryType;

	/** 期号 */
	private String periodNumber;

	/** 投注方式 */
	private SalesMode mode;

	/** 玩法类型 */
	private Byte betType;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/** 方案内容 */
	private String content;

	/** 额外的扩展信息 */
	private String extension;

	/** 是否出票成功 */
	private boolean ticketFinsh;
	/** 出票状态 */
	private PrintInterfaceState printState;
	
	private Date officialEndTime;

	/* ----------- getter and setter method ----------- */

	/**
	 * @return {@link #sponsorId}
	 */
	public Long getSponsorId() {
		return sponsorId;
	}

	/**
	 * @param sponsorId the {@link #sponsorId} to set
	 */
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return {@link #schemeNumber}
	 */
	public String getSchemeNumber() {
		return schemeNumber;
	}

	/**
	 * @param schemeNumber the {@link #schemeNumber} to set
	 */
	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return {@link #lotteryType}
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return {@link #mode}
	 */
	public SalesMode getMode() {
		return mode;
	}

	/**
	 * @param mode the {@link #mode} to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	/**
	 * @return {@link #periodNumber}
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return {@link #betType}
	 */
	
	public Byte getBetType() {
		return betType;
	}

	/**
	 * @param betType the {@link #betType} to set
	 */
	public void setBetType(Byte betType) {
		this.betType = betType;
	}

	/**
	 * @return {@link #units}
	 */
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the {@link #units} to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return {@link #multiple}
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the {@link #multiple} to set
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return {@link #schemeCost}
	 */
	
	public Integer getSchemeCost() {
		return schemeCost;
	}

	/**
	 * @param schemeCost the {@link #schemeCost} to set
	 */
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	/**
	 * @return {@link #extension}
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the {@link #extension} to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the ticketFinsh
	 */
	public boolean isTicketFinsh() {
		return ticketFinsh;
	}

	/**
	 * @param ticketFinsh the ticketFinsh to set
	 */
	public void setTicketFinsh(boolean ticketFinsh) {
		this.ticketFinsh = ticketFinsh;
	}
	/**
	 * @return {@link #mode}
	 */
	public PrintInterfaceState getPrintState() {
		return printState;
	}

	public void setPrintState(PrintInterfaceState printState) {
		this.printState = printState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOfficialEndTime() {
		return officialEndTime;
	}

	public void setOfficialEndTime(Date officialEndTime) {
		this.officialEndTime = officialEndTime;
	}

}
