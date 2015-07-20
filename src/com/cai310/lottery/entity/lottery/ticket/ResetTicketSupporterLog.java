package com.cai310.lottery.entity.lottery.ticket;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.ticket.common.TicketSupporter;

/**
 * 重置出票商操作日志实体
 * @author jack
 *
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "RESET_TICKETSUPPORTER_LOG")
@Entity
public class ResetTicketSupporterLog extends IdEntity implements CreateMarkable{

	private static final long serialVersionUID = 2184623327945756784L;
	
	/** 票ID*/
	private Long ticketId;
	/** 所属方案号 */
	private String schemeNumber;
	/** 返回的状态码*/
	private String stateCode;
	/** 返回的状态信息*/
	private String stateCodeMessage;
	/** 操作员*/
	private String operName;
	/** 原出票商*/
	private TicketSupporter ticketSupporter;
	/** 切换后的出票商*/
	private TicketSupporter resetTikcetSupporter;
	/** 发送的时间*/
	private Date sendTime;	
	/** 状态修改时间*/
	private Date stateModifyTime;
	/** 备注*/
	private String remark;
	/** 创建时间*/
	private Date createTime;

	@Column(nullable = false)
	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	@Column(nullable = false, length = 20)
	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	@Column
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Column
	public String getStateCodeMessage() {
		return stateCodeMessage;
	}

	public void setStateCodeMessage(String stateCodeMessage) {
		this.stateCodeMessage = stateCodeMessage;
	}

	@Column(nullable = false,updatable = false)
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
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

	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}

	@Column
	public TicketSupporter getResetTikcetSupporter() {
		return resetTikcetSupporter;
	}

	public void setResetTikcetSupporter(TicketSupporter resetTikcetSupporter) {
		this.resetTikcetSupporter = resetTikcetSupporter;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	@Column(length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
