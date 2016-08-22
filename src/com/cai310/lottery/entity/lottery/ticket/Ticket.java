package com.cai310.lottery.entity.lottery.ticket;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.ticket.common.TicketSupporter;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TICKET")
@Entity
public class Ticket extends IdEntity{

	private static final long serialVersionUID = 2560091269922347708L;
	
	/** 创建时间 */
	private Date createTime;	
	
	/** 官方截止时间 */
	private Date officialEndTime;

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
	
	/** 接口表记录ID */
	private Long printinterfaceId;	 
	
	/** 是否发送*/
	private boolean sended;
	
	/** 发送时间 */
	private Date sendTime;	
	
	/** 返回的状态码*/
	private String stateCode;
	
	/** 返回的状态码信息*/
	private String stateCodeMessage;
	
	/**电子票号*/
	private String remoteTicketId;
	
	/** 出票商 */
	private TicketSupporter ticketSupporter;
	
	/** 出票方提供状态的更新时间*/
	private Date stateModifyTime;
	
	/** 订单号 */
	private String orderNo;
	
	/** 是否已同步*/
	private boolean synchroned;
	
	/** 是否已同步出票中奖*/
	private boolean checked;
	
	private boolean won;
	
	/** 中奖金额*/                          
	private Double totalPrize;        
	
	/** 税后金额*/                          
	private Double totalPrizeAfterTax;
	                                    
	/** 中奖详情*/                          
	private String wonDetail;
	
	/** 票拆票的序列*/
	private Integer ticketIndex;
	
	/** 远程同步*/
	private Boolean ticket_synchroned;
	
	/** 暂停出票*/
	private boolean pause;
	
	/** 执行暂停操作时间*/
	private Date pauseOperTime;
	
	private Date ticketTime;
	
	/** 中奖总金额 来自第三方出票*/                          
	private Double prize;        
	/** 税金 来自第三方出票*/                          
	private Double tax;        
	
	/** 退票金额 来自第三方出票*/                          
	private Double returnMoney;
	                                    
	/** 中奖详情 来自第三方出票*/                          
	private String detail;
	
	/** 是否返奖 来自第三方出票*/
	private boolean returnAward;
	/** 是否获取中奖 来自第三方出票*/
	private boolean award_synchroned;
	
	/** 返奖时间 来自第三方出票*/
	private Date returnAwardTime;
	
	/** 期次对应id 来自第三方出票*/
	private String issueId;
	/* ----------- getter and setter method ----------- */

	/**
	 * @return the printinterfaceId
	 */
	@Column(nullable = false)
	public Long getPrintinterfaceId() {
		return printinterfaceId;
	}

	/**
	 * @param printinterfaceId the printinterfaceId to set
	 */
	public void setPrintinterfaceId(Long printinterfaceId) {
		this.printinterfaceId = printinterfaceId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return {@link #officialEndTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getOfficialEndTime() {
		return officialEndTime;
	}

	/**
	 * @param officialEndTime the {@link #officialEndTime} to set
	 */
	public void setOfficialEndTime(Date officialEndTime) {
		this.officialEndTime = officialEndTime;
	}

	/**
	 * @return {@link #sponsorId}
	 */
	@Column(nullable = false)
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
	@Column(nullable = false, length = 20)
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
	@Lob
	@Column(nullable = false, updatable = false)
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
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
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
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesMode"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "sales_mode", nullable = false, updatable = false)
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
	@Column(nullable = false, updatable = false, length = 20)
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
	@Column
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
	@Column(nullable = false, updatable = false)
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
	@Column(nullable = false, updatable = false)
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
	@Column(nullable = false, updatable = false)
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
	@Column(length = 2000)
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
	@Column(name="ticketFinsh")
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
	 * @return the sended
	 */
	@Column(name="sended")
	public boolean isSended() {
		return sended;
	}

	/**
	 * @param sended the sended to set
	 */
	public void setSended(boolean sended) {
		this.sended = sended;
	}

	/**
	 * @return the sendTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the stateCode
	 */
	@Column
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the stateCodeMessage
	 */
	@Column
	public String getStateCodeMessage() {
		return stateCodeMessage;
	}

	/**
	 * @param stateCodeMessage the stateCodeMessage to set
	 */
	public void setStateCodeMessage(String stateCodeMessage) {
		this.stateCodeMessage = stateCodeMessage;
	}

	/**
	 * @return the ticketSupporter
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.ticket.common.TicketSupporter"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column
	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	/**
	 * @param ticketSupporter the ticketSupporter to set
	 */
	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}

	/**
	 * @return the remoteTicketId
	 */
	@Column
	public String getRemoteTicketId() {
		return remoteTicketId;
	}

	/**
	 * @param remoteTicketId the remoteTicketId to set
	 */
	public void setRemoteTicketId(String remoteTicketId) {
		this.remoteTicketId = remoteTicketId;
	}

	/**
	 * @return the stateModifyTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	/**
	 * @param stateModifyTime the lastModifyTime to set
	 */
	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	/**
	 * @return the orderNo
	 */
	@Column
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the synchroned
	 */
	@Column
	public boolean isSynchroned() {
		return synchroned;
	}

	/**
	 * @param 出票方提供状态的更新时间
	 */
	public void setSynchroned(boolean synchroned) {
		this.synchroned = synchroned;
	}

	/**
	 * 对应前台方案的出票状态
	 * @return
	 */
    @Transient
	public SchemePrintState getTicketState(){
    	if(this.stateCode==null){
    		return SchemePrintState.UNPRINT;
    	}
		if("0".equals(this.stateCode)){
			return SchemePrintState.PRINT;
		}else if("1".equals(this.stateCode)){
			return SchemePrintState.SUCCESS;
		}else{
			return SchemePrintState.FAILED;
		}
	}
    
    /**
	 * 是否高频彩种
	 * @return
	 */
    @Transient
	public boolean isKeno(){
		return Lottery.isKeno(this.getLotteryType());
	}
    @Column
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
    @Column
	public String getWonDetail() {
		return wonDetail;
	}

	public void setWonDetail(String wonDetail) {
		this.wonDetail = wonDetail;
	}
	@Column
	public Integer getTicketIndex() {
		return ticketIndex;
	}

	public void setTicketIndex(Integer ticketIndex) {
		this.ticketIndex = ticketIndex;
	}
	@Column
	public Double getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(Double totalPrize) {
		this.totalPrize = totalPrize;
	}
	@Column
	public Double getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	public void setTotalPrizeAfterTax(Double totalPrizeAfterTax) {
		this.totalPrizeAfterTax = totalPrizeAfterTax;
	}
	@Column
	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}
	@Column(columnDefinition ="bit(1) default 0")
	public Boolean isTicket_synchroned() {
		return ticket_synchroned;
	}

	public void setTicket_synchroned(Boolean ticket_synchroned) {
		this.ticket_synchroned = ticket_synchroned;
	}	
	
	@Column
	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getPauseOperTime() {
		return pauseOperTime;
	}

	public void setPauseOperTime(Date pauseOperTime) {
		this.pauseOperTime = pauseOperTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(Date ticketTime) {
		this.ticketTime = ticketTime;
	}
	@Column
	public Double getPrize() {
		return prize;
	}

	public void setPrize(Double prize) {
		this.prize = prize;
	}
	@Column
	public Double getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}
	@Column
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Column(columnDefinition ="bit(1) default 0")
	public boolean isReturnAward() {
		return returnAward;
	}

	public void setReturnAward(boolean returnAward) {
		this.returnAward = returnAward;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getReturnAwardTime() {
		return returnAwardTime;
	}

	public void setReturnAwardTime(Date returnAwardTime) {
		this.returnAwardTime = returnAwardTime;
	}
	@Column
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	@Column
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}
	@Column(columnDefinition ="bit(1) default 0")
	public boolean isAward_synchroned() {
		return award_synchroned;
	}

	public void setAward_synchroned(boolean award_synchroned) {
		this.award_synchroned = award_synchroned;
	}
    
    
	
}
