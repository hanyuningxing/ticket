package com.cai310.lottery.entity.lottery.ticket;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

/**
 * PrintQueue entity. @author MyEclipse Persistence Tools
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PRINT_DETAIL")
@Entity
public class PrintDetail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String schemeNum;
	private Short platformType;
	private Integer platformId;
	private Short betType;
	private String issueNumber;
	private Date endSaleTime;
	private Short lotteryType;
	private String passType;
	private String betContent;
	private Integer multiple;
	private Integer betUnits;
	private Double betCost;
	private Integer printerId;
	private Boolean ifRequest;
	private Date rqTime;
	private Date insertTime;
	private Integer organigerId;
	private Short sendState;
	private Integer printAgentId;
	private Integer printAgentState;
	private String printAgentMessage;
	private Date printTime;
	private Short state;
	/** 总奖金 */
	private Double prize;
	/** 税后奖金 */
	private Double returnPrize;

	// Constructors

	/** default constructor */
	public PrintDetail() {
	}

	// Property accessors
	@Id
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getSchemeNum() {
		return this.schemeNum;
	}

	public void setSchemeNum(String schemeNum) {
		this.schemeNum = schemeNum;
	}

	@Column
	public Short getPlatformType() {
		return this.platformType;
	}

	public void setPlatformType(Short platformType) {
		this.platformType = platformType;
	}

	@Column
	public Integer getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	@Column
	public Short getBetType() {
		return this.betType;
	}

	public void setBetType(Short betType) {
		this.betType = betType;
	}

	@Column
	public String getIssueNumber() {
		return this.issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	@Column
	public Date getEndSaleTime() {
		return this.endSaleTime;
	}

	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}

	@Column
	public Short getLotteryType() {
		return this.lotteryType;
	}

	public void setLotteryType(Short lotteryType) {
		this.lotteryType = lotteryType;
	}

	@Column
	public String getPassType() {
		return this.passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	@Lob
	@Column
	public String getBetContent() {
		return this.betContent;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	@Column
	public Integer getMultiple() {
		return this.multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	@Column
	public Integer getBetUnits() {
		return this.betUnits;
	}

	public void setBetUnits(Integer betUnits) {
		this.betUnits = betUnits;
	}

	
	@Column
	public Double getBetCost() {
		return this.betCost;
	}

	public void setBetCost(Double betCost) {
		this.betCost = betCost;
	}

	@Column
	public Integer getPrinterId() {
		return this.printerId;
	}

	public void setPrinterId(Integer printerId) {
		this.printerId = printerId;
	}

	@Column
	public Boolean getIfRequest() {
		return this.ifRequest;
	}

	public void setIfRequest(Boolean ifRequest) {
		this.ifRequest = ifRequest;
	}

	@Column
	public Date getRqTime() {
		return this.rqTime;
	}

	public void setRqTime(Date rqTime) {
		this.rqTime = rqTime;
	}

	@Column
	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column
	public Integer getOrganigerId() {
		return this.organigerId;
	}

	public void setOrganigerId(Integer organigerId) {
		this.organigerId = organigerId;
	}

	@Column
	public Short getSendState() {
		return this.sendState;
	}

	public void setSendState(Short sendState) {
		this.sendState = sendState;
	}

	@Column
	public Integer getPrintAgentId() {
		return this.printAgentId;
	}

	public void setPrintAgentId(Integer printAgentId) {
		this.printAgentId = printAgentId;
	}

	@Column
	public Integer getPrintAgentState() {
		return this.printAgentState;
	}

	public void setPrintAgentState(Integer printAgentState) {
		this.printAgentState = printAgentState;
	}

	@Column
	public String getPrintAgentMessage() {
		return this.printAgentMessage;
	}

	public void setPrintAgentMessage(String printAgentMessage) {
		this.printAgentMessage = printAgentMessage;
	}

	/**
	 * @return the printTime
	 */
	@Column
	public Date getPrintTime() {
		return printTime;
	}

	/**
	 * @param printTime the printTime to set
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	/**
	 * @return the state
	 */
	@Column
	public Short getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Short state) {
		this.state = state;
	}

	/**
	 * @return the prize
	 */
	
	@Column
	public Double getPrize() {
		return prize;
	}

	/**
	 * @param prize the prize to set
	 */
	public void setPrize(Double prize) {
		this.prize = prize;
	}

	/**
	 * @return the returnPrize
	 */
	
	@Column
	public Double getReturnPrize() {
		return returnPrize;
	}

	/**
	 * @param returnPrize the returnPrize to set
	 */
	public void setReturnPrize(Double returnPrize) {
		this.returnPrize = returnPrize;
	}

	@Transient
	public String getStateString() {
		if (null == this.state)
			return "";
		if (Short.valueOf("0").equals(this.state)) {
			return "过期";
		} else if (Short.valueOf("1").equals(this.state)) {
			return "正常出票";
		} else if (Short.valueOf("2").equals(this.state)) {
			return "撤销";
		} else if (Short.valueOf("3").equals(this.state)) {
			return "限号";
		}
		return "";
	}
}