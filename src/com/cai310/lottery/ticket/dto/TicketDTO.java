package com.cai310.lottery.ticket.dto;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.ticket.common.TicketSupporter;


public class TicketDTO{

	/** 创建时间 */
	private Date createTime;
	
	private Long id;

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
	/* ----------- getter and setter method ----------- */

	/**
	 * @return the printinterfaceId
	 */
	public Long getPrintinterfaceId() {
		return printinterfaceId;
	}

	/**
	 * @param printinterfaceId the printinterfaceId to set
	 */
	public void setPrintinterfaceId(Long printinterfaceId) {
		this.printinterfaceId = printinterfaceId;
	}

	/**
	 * @return {@link #officialEndTime}
	 */
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
	 * @return the sended
	 */
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
    
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
    
	public String getWonDetail() {
		return wonDetail;
	}

	public void setWonDetail(String wonDetail) {
		this.wonDetail = wonDetail;
	}
	
	public Integer getTicketIndex() {
		return ticketIndex;
	}

	public void setTicketIndex(Integer ticketIndex) {
		this.ticketIndex = ticketIndex;
	}
	
	public Double getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(Double totalPrize) {
		this.totalPrize = totalPrize;
	}
	
	public Double getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	public void setTotalPrizeAfterTax(Double totalPrizeAfterTax) {
		this.totalPrizeAfterTax = totalPrizeAfterTax;
	}
	
	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public void setTicket_synchroned(Boolean ticket_synchroned) {
		this.ticket_synchroned = ticket_synchroned;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getTicket_synchroned() {
		return ticket_synchroned;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
	
}
