package com.cai310.lottery.entity.lottery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.common.SalesMode;

/**
 * 打印接口实体.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PRINT_INTERFACE")
@Entity
public class PrintInterface extends IdEntity implements CreateMarkable {
	private static final long serialVersionUID = -7326135257804625938L;

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
	/** 出票状态 */
	private PrintInterfaceState printState;

	/* ----------- getter and setter method ----------- */

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
	@Column(updatable = false, length = 1000)
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
	@Column(name = "ticketFinsh")
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
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PrintInterfaceState"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "printState", nullable = false)
	public PrintInterfaceState getPrintState() {
		return printState;
	}

	public void setPrintState(PrintInterfaceState printState) {
		this.printState = printState;
	}

}
